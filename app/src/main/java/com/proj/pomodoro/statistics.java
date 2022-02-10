package com.proj.pomodoro;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class statistics extends AppCompatActivity {
    ResultDataAccess result_access = new ResultDataAccess(this);
    BarChart barChart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
         barChart = findViewById(R.id.BarChart);
        initializeBarChart(barChart);
        result_access.openDB();
        ArrayList<result_time> arrayList = result_access.getall();
        result_access.closeDB();
        GroupBarChart(arrayList);



        PieChart pieChart = findViewById(R.id.piechart);
        ArrayList<PieEntry> pieEntries = new ArrayList<>();
        String label = "type";
        Map<String, Integer> typeAmountMap = new HashMap<>();
        for (result_time p:arrayList) {
            int sum=0;
            for (int a = 0; a < arrayList.size(); a++) {
                if (p.getTitle().equals(arrayList.get(a).getTitle())){
                    sum+=arrayList.get(a).getTotal_focus_time();
                }
            }
            typeAmountMap.put(p.getTitle(), sum);
        }

        typeAmountMap.put("Snacks", 23000);
        Description c =new Description();
        c.setText("focus pieChart");
        pieChart.setDescription(c);
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.parseColor("#304567"));
        colors.add(Color.parseColor("#309967"));
        colors.add(Color.parseColor("#476567"));
        colors.add(Color.parseColor("#890567"));
        colors.add(Color.parseColor("#a35567"));
        colors.add(Color.parseColor("#ff5f67"));
        colors.add(Color.parseColor("#3ca567"));
        colors.add(Color.parseColor("#3a5537"));

        //input data and fit data into pie chart entry
        for (String type : typeAmountMap.keySet()) {
            pieEntries.add(new PieEntry(typeAmountMap.get(type).floatValue(), type));
        }
        //collecting the entries with label name
        PieDataSet pieDataSet = new PieDataSet(pieEntries, label);
        //setting text size of the value
        pieDataSet.setValueTextSize(12f);
        //providing color list for coloring different entries
        pieDataSet.setColors(colors);
        //grouping the data set from entry to chart
        PieData pieData = new PieData(pieDataSet);
        //showing the value of the entries, default true if not set
        pieData.setDrawValues(true);
        pieChart.setData(pieData);
        pieChart.invalidate();
    }



    private void initializeBarChart(BarChart barChart) {
        barChart.getDescription().setEnabled(false);

        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        barChart.setMaxVisibleValueCount(4);
        barChart.getXAxis().setDrawGridLines(false);
        // scaling can now only be done on x- and y-axis separately
        barChart.setPinchZoom(false);

        barChart.setDrawBarShadow(false);
        barChart.setDrawGridBackground(false);

        XAxis xAxis = barChart.getXAxis();
        xAxis.setDrawGridLines(false);

        barChart.getAxisLeft().setDrawGridLines(false);
        barChart.getAxisRight().setDrawGridLines(false);
        barChart.getAxisRight().setEnabled(false);
        barChart.getAxisLeft().setEnabled(true);
        barChart.getXAxis().setDrawGridLines(false);
        // add a nice and smooth animation
        barChart.animateY(1500);


        barChart.getLegend().setEnabled(false);

        barChart.getAxisRight().setDrawLabels(false);
        barChart.getAxisLeft().setDrawLabels(true);
        barChart.setTouchEnabled(false);
        barChart.setDoubleTapToZoomEnabled(false);
        barChart.getXAxis().setEnabled(true);
        barChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        barChart.invalidate();

    }
    public void GroupBarChart(ArrayList <result_time> arrayList){
        Description c =new Description();
        c.setText("barChart");
        barChart.setDescription(c);
        barChart.setDrawBarShadow(false);
        //barChart.getDescription().setEnabled(false);
        barChart.setPinchZoom(false);
        barChart.setDrawGridBackground(true);
        // empty labels so that the names are spread evenly
        HashSet<String> s = new HashSet<>();
        s.add("");
        //Map<String, Integer> map_focus_sum = new HashMap<>();
        HashSet<Float> map_focus_sum = new HashSet<>();
        HashSet<Float> map_rest_sum = new HashSet<>();
        for (result_time p:arrayList) {
            s.add(p.getTitle());
            int focus_sum=0;
            int rest_sum=0;
            for (int a = 0; a < arrayList.size(); a++) {
                if (p.getTitle().equals(arrayList.get(a).getTitle())){
                    focus_sum+=arrayList.get(a).getTotal_focus_time();
                    rest_sum+=arrayList.get(a).getTotal_rest();
                }

            }
            map_focus_sum.add((float)focus_sum);
            map_rest_sum.add((float)rest_sum);
        }
        s.add("");
        String labels[] = new String[s.size()+1];
        int pp=0;
        for(String ele:s){
            labels[pp++] = ele;

        }
        labels[pp++] = "";
        XAxis xAxis = barChart.getXAxis();
        xAxis.setCenterAxisLabels(true);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(true);
        xAxis.setGranularity(1f); // only intervals of 1 day
        xAxis.setTextColor(Color.BLACK);
        xAxis.setTextSize(12);
        xAxis.setAxisLineColor(Color.WHITE);
        xAxis.setAxisMinimum(1f);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));

        YAxis leftAxis = barChart.getAxisLeft();
        leftAxis.setTextColor(Color.BLACK);
        leftAxis.setTextSize(12);
        leftAxis.setAxisLineColor(Color.WHITE);
        leftAxis.setDrawGridLines(true);
        leftAxis.setGranularity(2);
        leftAxis.setLabelCount(6, false);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);

        barChart.getAxisRight().setEnabled(false);
        barChart.getLegend().setEnabled(false);
        float[] valOne = new float[map_focus_sum.size()];
        float[] valTwo = new float[map_focus_sum.size()];
        pp=0;
        for(Float ele:map_focus_sum){
            valOne[pp++] = ele;
            //valTwo[pp++] = ele;
            //Log.i("GroupBarChart", ele);
        }
        pp=0;
        for(Float ele:map_rest_sum){
            //valOne[pp] = ele;
            valTwo[pp++] = ele;
            Log.i("GroupBarChart", map_focus_sum.size()+"   "+map_rest_sum.size());

            //Log.i("GroupBarChart", ele);
        }
        float[] valThree = {5240, 6044,3142};
        //float[] valTwo = {60};

        ArrayList<BarEntry> barOne = new ArrayList<>();
        ArrayList<BarEntry> barTwo = new ArrayList<>();
        ArrayList<BarEntry> barThree = new ArrayList<>();
        for (int i = 0; i < valOne.length; i++) {
            barOne.add(new BarEntry(i, valOne[i]));
            barTwo.add(new BarEntry(i, valTwo[i]));
            barThree.add(new BarEntry(i, valThree[i]));
        }

        BarDataSet set1 = new BarDataSet(barOne, "focus");
        set1.setColor(Color.BLUE);

        //set1.setStackLabels(cpo);
        BarDataSet set2 = new BarDataSet(barTwo, "rest");
        set2.setColor(Color.MAGENTA);
        BarDataSet set3 = new BarDataSet(barThree, "barthree");
        set2.setColor(Color.GREEN);

        set1.setHighlightEnabled(false);
        set2.setHighlightEnabled(false);
        //set3.setHighlightEnabled(false);
        set1.setDrawValues(false);
        set2.setDrawValues(false);
        //set3.setDrawValues(false);

        ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
        dataSets.add(set1);
        dataSets.add(set2);
        //dataSets.add(set3);
        BarData data = new BarData(dataSets);
        float groupSpace = 0.35f;
        float barSpace = 0.02f;
        float barWidth = 0.3f;
        // (barSpace + barWidth) * 2 + groupSpace = 1
        data.setBarWidth(barWidth);
        // so that the entire chart is shown when scrolled from right to left
        xAxis.setAxisMaximum(labels.length - 1.1f);
        barChart.setData(data);
        barChart.setVerticalScrollBarEnabled(true);
        barChart.setScrollContainer(true);
        barChart.setHorizontalScrollBarEnabled(true);
        barChart.setScaleEnabled(true);
        barChart.setVisibleXRangeMaximum(6f);
        barChart.groupBars(1f, groupSpace, barSpace);
        barChart.invalidate();

    }
}