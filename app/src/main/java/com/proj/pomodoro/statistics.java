package com.proj.pomodoro;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class statistics extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        BarChart barChart = findViewById(R.id.BarChart);
        initializeBarChart(barChart);





        PieChart pieChart = findViewById(R.id.piechart);
        ArrayList<PieEntry> pieEntries = new ArrayList<>();
        String label = "type";
        Map<String, Integer> typeAmountMap = new HashMap<>();
        typeAmountMap.put("Toys", 200);
        typeAmountMap.put("Snacks", 230);
        typeAmountMap.put("Clothes", 100);
        typeAmountMap.put("Stationary", 20);
        typeAmountMap.put("Phone", 50);

        //initializing colors for the entries
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.parseColor("#304567"));
        colors.add(Color.parseColor("#309967"));
        colors.add(Color.parseColor("#476567"));
        colors.add(Color.parseColor("#890567"));
        colors.add(Color.parseColor("#a35567"));
        colors.add(Color.parseColor("#ff5f67"));
        colors.add(Color.parseColor("#3ca567"));

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
}