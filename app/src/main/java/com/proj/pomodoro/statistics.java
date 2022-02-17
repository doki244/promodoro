package com.proj.pomodoro;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

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
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class statistics extends AppCompatActivity {
    ResultDataAccess result_access = new ResultDataAccess(this);
    BarChart barChart;
    RecyclerView recyclerView;
    private static final int MAX_X_VALUE = 7;
    private static final int MAX_Y_VALUE = 50;
    private static final int MIN_Y_VALUE = 5;
    private static final int GROUPS = 3;
    private static final String GROUP_1_LABEL = "Group 1";
    private static final String GROUP_2_LABEL = "Group 2";
    private static final String GROUP_3_LABEL = "Group 3";
    private static final float BAR_SPACE = 0.05f;
    private static final float BAR_WIDTH = 0.2f;
    PieChart pieChart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
         //barChart = findViewById(R.id.BarChart);
        recyclerView =findViewById(R.id.details);

        //initializeBarChart(barChart);
        result_access.openDB();
        ArrayList<result_time> arrayList = result_access.getall();
        result_access.closeDB();
        ArrayList<result_time> clean = new ArrayList<>();
        Map<String,result_time> clean_map = new HashMap<>();
        Map<String, Integer> focus_title = new HashMap<>();
        Map<String, Integer> rest_title = new HashMap<>();
        for (result_time p:arrayList) {
            int F_sum=0;
            int R_sum=0;
            for (int a = 0; a < arrayList.size(); a++) {
                if (p.getTitle().equals(arrayList.get(a).getTitle())){
                    Log.i("ouuoyb", "1");
                    F_sum+=arrayList.get(a).getTotal_focus_time();
                    R_sum+=arrayList.get(a).getTotal_rest();
                }
            }
            clean_map.put(p.getTitle(),new result_time(p.getTitle(),F_sum,R_sum,p.getDate()));
            focus_title.put(p.getTitle(), F_sum);
            rest_title.put(p.getTitle(), R_sum);
        }
        for (String key:clean_map.keySet() ) {
            clean.add(new result_time(clean_map.get(key).getTitle(),clean_map.get(key).getTotal_focus_time(),clean_map.get(key).getTotal_rest(),clean_map.get(key).getDate()));
        }
        res_adapter ada = new res_adapter(clean,arrayList);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setAdapter(ada);
//        BarData data = createChartData(arrayList);
//        configureChartAppearance();
//        prepareChartData(data);
        //GroupBarChart(arrayList);
        pieChart = findViewById(R.id.piechart);
        pieChart(arrayList);

    }

    private void configureChartAppearance() {
        barChart.setPinchZoom(false);
        barChart.setDrawBarShadow(false);
        barChart.setDrawGridBackground(false);

        barChart.getDescription().setEnabled(false);

        XAxis xAxis = barChart.getXAxis();
        //xAxis.setGranularity(1f);
       // xAxis.setCenterAxisLabels(true);

        YAxis leftAxis = barChart.getAxisLeft();
        leftAxis.setDrawGridLines(false);
        leftAxis.setSpaceTop(35f);
        leftAxis.setAxisMinimum(0f);

        barChart.getAxisRight().setEnabled(false);

        barChart.getXAxis().setAxisMinimum(0);
        //barChart.getXAxis().setAxisMaximum(MAX_X_VALUE);
    }

    private BarData createChartData(ArrayList<result_time> arrayList) {
        Random u = new Random();
        HashSet<String> s = new HashSet<>();
        //s.add("");
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
        //s.add("");
        String labels[] = new String[s.size()+1];
        int pp=0;
        for(String ele:s){
            labels[pp++] = ele;

        }
        //labels[pp++] = "";
        XAxis xAxis = barChart.getXAxis();
        xAxis.setGranularity(1f);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new MyXAxisValueFormatter());
        ArrayList<BarEntry> values1 = new ArrayList<>();
        ArrayList<BarEntry> values2 = new ArrayList<>();
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
        for (int i = 0; i < map_focus_sum.size(); i++) {
            values1.add(new BarEntry(i,valOne[i]));
            values2.add(new BarEntry(i, valTwo[i]));
            //values3.add(new BarEntry(i, u.nextInt(6)));
        }

        BarDataSet set1 = new BarDataSet(values1, "focus");
        BarDataSet set2 = new BarDataSet(values2, "rest");
        //BarDataSet set3 = new BarDataSet(values3, GROUP_3_LABEL);

        set1.setColor(ColorTemplate.MATERIAL_COLORS[0]);
        set2.setColor(ColorTemplate.MATERIAL_COLORS[1]);
        //set3.setColor(ColorTemplate.MATERIAL_COLORS[2]);

        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);
        dataSets.add(set2);
        //dataSets.add(set3);

        BarData data = new BarData(dataSets);

        return data;
    }

    private void prepareChartData(BarData data) {
        barChart.setData(data);

        barChart.getBarData().setBarWidth(BAR_WIDTH);

        float groupSpace = 1f - ((BAR_SPACE + BAR_WIDTH) * GROUPS);
        barChart.groupBars(0, groupSpace, BAR_SPACE);

        barChart.invalidate();
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
        //barChart.setDrawBarShadow(false);
        //barChart.getDescription().setEnabled(false);
        //barChart.setPinchZoom(false);
       // barChart.setDrawGridBackground(true);
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
        //float barWidth = 0.3f;
        // (barSpace + barWidth) * 2 + groupSpace = 1
        //data.setBarWidth(barWidth);
        // so that the entire chart is shown when scrolled from right to left
        xAxis.setAxisMaximum(labels.length - 1.1f);
        barChart.setData(data);

        barChart.getBarData().setBarWidth(BAR_WIDTH);

        float group_Space = 1f - ((BAR_SPACE + BAR_WIDTH) * GROUPS);
        barChart.groupBars(0, group_Space, BAR_SPACE);
        barChart.invalidate();

    }

    public void pieChart(ArrayList <result_time> arrayList){
        ArrayList<PieEntry> pieEntries = new ArrayList<>();
        String label = "";
        Map<String, Integer> typeAmountMap = new HashMap<>();
        int total=0;
        for (result_time p:arrayList) {
            int sum=0;
            for (int a = 0; a < arrayList.size(); a++) {
                if (p.getTitle().equals(arrayList.get(a).getTitle())){
                    sum+=arrayList.get(a).getTotal_focus_time();
                }
            }

            typeAmountMap.put(p.getTitle(), sum);
        }

        //typeAmountMap.put("Snacks", 23000);
        Description c =new Description();
        c.setText("focus percent");
        //c.setPosition(640,705);
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
        for (String type : typeAmountMap.keySet()) {
            total+=typeAmountMap.get(type).floatValue();
        }
        //input data and fit data into pie chart entry
        for (String type : typeAmountMap.keySet()) {
            pieEntries.add(new PieEntry((float) calculatePercentage(typeAmountMap.get(type).floatValue(),total), type));
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
    public double calculatePercentage(double obtained, double total) {
        return (obtained * 100 / total);
    }
}
