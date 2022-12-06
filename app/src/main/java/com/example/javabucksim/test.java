package com.example.javabucksim;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class test extends AppCompatActivity {

    private static final float BAR_SPACE = 0.05f;
    private static final float BAR_WIDTH = 0.15f;
    private static final float MAX_X_VALUE = 7f;

    // variable for our bar chart
    BarChart barChart;

    // variable for our bar data.
    BarData barData;

    // variable for our bar data set.
    BarDataSet barDataSet;

    // array list for storing entries.
    ArrayList barEntriesArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        // initializing variable for bar chart.
        barChart = findViewById(R.id.idBarChart);

        // calling method to get bar entries.
        getBarEntries();
        configureChartAppearance();

        // creating a new bar data set.
        barDataSet = new BarDataSet(barEntriesArrayList, "Inventory");

        // creating a new bar data and
        // passing our bar data set.
        barData = new BarData(barDataSet);

        // below line is to set data
        // to our bar chart.
        barChart.setData(barData);

        // adding color to our bar data set.
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);

        // setting text color.
        barDataSet.setValueTextColor(Color.BLACK);

        // setting text size
        barDataSet.setValueTextSize(16f);
        barChart.getDescription().setEnabled(false);
    }

    private void getBarEntries() {
        // creating a new array list
        barEntriesArrayList = new ArrayList<>();

        // adding new entry to our array list with bar
        // entry and passing x and y axis value to it.
        barEntriesArrayList.add(new BarEntry(1f, 168));
        barEntriesArrayList.add(new BarEntry(2f, 48));
        barEntriesArrayList.add(new BarEntry(3f, 85));
        barEntriesArrayList.add(new BarEntry(4f, 85));
        barEntriesArrayList.add(new BarEntry(5f, 167));
        barEntriesArrayList.add(new BarEntry(6f, 40));
        barEntriesArrayList.add(new BarEntry(6f, 85));
        barEntriesArrayList.add(new BarEntry(6f, 135));
        barEntriesArrayList.add(new BarEntry(6f, 85));
        barEntriesArrayList.add(new BarEntry(6f, 85));
        barEntriesArrayList.add(new BarEntry(6f, 85));
        barEntriesArrayList.add(new BarEntry(6f, 146));
        barEntriesArrayList.add(new BarEntry(6f, 85));
        barEntriesArrayList.add(new BarEntry(6f, 159));
        barEntriesArrayList.add(new BarEntry(6f, 85));
    }

    private void configureChartAppearance() {
        barChart.setPinchZoom(false);
        barChart.setDrawBarShadow(false);
        barChart.setDrawGridBackground(true);

        barChart.getDescription().setEnabled(false);

        String[] productName = new String[] {"chai", "blondeRoast", "capp", "coffee", "coldbrew", "cups", "darkRoast", "flavour", "juice", "latte", "lids",
                "matcha", "mediumRoast", "milk", "sleeves", "stoppers", "sugar", "tea"};

        XAxis xAxis = barChart.getXAxis();
        xAxis.setGranularity(1f);
        xAxis.setCenterAxisLabels(true);
        xAxis.setValueFormatter(new MyXAxisValueFormatter(productName));
        xAxis.setPosition(XAxis.XAxisPosition.BOTH_SIDED);




        YAxis leftAxis = barChart.getAxisLeft();
        leftAxis.setDrawGridLines(false);
        leftAxis.setAxisMinimum(0f);

        barChart.getAxisRight().setEnabled(false);
        barChart.getXAxis().setAxisMinimum(0);
        barChart.getXAxis().setAxisMaximum(MAX_X_VALUE);

        barChart.setFitBars(true);


    }

}

class MyXAxisValueFormatter extends ValueFormatter implements IAxisValueFormatter {
    private String[] mValues;
    public MyXAxisValueFormatter(String [] values) {
        this.mValues = values;
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        return mValues[(int) value];
    }
}