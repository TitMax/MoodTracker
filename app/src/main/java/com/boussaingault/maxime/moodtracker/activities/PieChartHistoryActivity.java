package com.boussaingault.maxime.moodtracker.activities;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.boussaingault.maxime.moodtracker.R;
import com.boussaingault.maxime.moodtracker.models.DatabaseManager;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;

import java.util.ArrayList;
import java.util.List;

public class PieChartHistoryActivity extends AppCompatActivity {

    private DatabaseManager mDatabaseManager;
    private String[] moods = {"Sad", "Disappointed", "Normal", "Happy", "Super Happy"};
    private String[] moodsFR = {"Triste", "Déçu", "Normal", "Heureux", "Super Heureux"};
    private int daysNumber = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pie_chart_history);

        PieChart pieChart = (PieChart) findViewById(R.id.activity_piechart_history_pie_chart);

        mDatabaseManager = new DatabaseManager(this);

        List<PieEntry> entries = new ArrayList<>();
        for (int i = 0; i < moods.length; i++)
            entries.add(new PieEntry((float)mDatabaseManager.countMoods(moods[i], daysNumber), moodsFR[i]));
        mDatabaseManager.close();

        // Create pieDataSet
        PieDataSet dataSet = new PieDataSet(entries, "");

        // Adding colors
        int[] colors = {getResources().getColor(R.color.faded_red),
                getResources().getColor(R.color.warm_grey),
                getResources().getColor(R.color.cornflower_blue_65),
                getResources().getColor(R.color.light_sage),
                getResources().getColor(R.color.banana_yellow)};

        dataSet.setColors(colors);

        // Create pie data object
        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(20);
        pieChart.setData(data);
        pieChart.setUsePercentValues(true);
        pieChart.getLegend().setTextSize(12);
        pieChart.getLegend().setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        pieChart.getLegend().setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        pieChart.getLegend().setOrientation(Legend.LegendOrientation.VERTICAL);
        pieChart.getLegend().setYOffset(12);
        pieChart.setHoleColor(android.R.color.darker_gray);
        pieChart.setHoleRadius(25);
        pieChart.setTransparentCircleAlpha(0);
        pieChart.setDrawEntryLabels(false);
        pieChart.setDescription(null);
        pieChart.setNoDataText("Vous n'avez pas encore d'historique");
        pieChart.setTouchEnabled(false);
        // refresh pie chart
        pieChart.invalidate();
    }

}
