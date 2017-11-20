package com.boussaingault.maxime.moodtracker.activities;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.boussaingault.maxime.moodtracker.R;
import com.boussaingault.maxime.moodtracker.models.DatabaseManager;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;

import java.util.ArrayList;
import java.util.List;

import static com.github.mikephil.charting.utils.Utils.convertDpToPixel;

public class PieChartHistoryActivity extends AppCompatActivity {

    PieChart pieChart;
    private DatabaseManager mDatabaseManager;
    private String[] moods = {"Sad", "Disappointed", "Normal", "Happy", "Super Happy"};
    private String[] moodsFR = {"Triste", "Déçu", "Normal", "Heureux", "Super Heureux"};
    private int daysNumber = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pie_chart_history);

        pieChart = (PieChart) findViewById(R.id.activity_piechart_history_pie_chart);

        pieChart.setUsePercentValues(true);
        pieChart.setDescription(null);

        pieChart.setHoleColor(android.R.color.darker_gray);
        pieChart.setTransparentCircleAlpha(0);
        pieChart.setHoleRadius(25);

        pieChart.setDrawEntryLabels(false);

        Legend l = pieChart.getLegend();
        l.setTextSize(12);
        // Positioning legend to the bottom left of the screen
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setYOffset(12);

        mDatabaseManager = new DatabaseManager(this);
        if(mDatabaseManager.isHistory() != 0)
            setData();

        pieChart.setTouchEnabled(false);

        pieChart.getPaint(Chart.PAINT_INFO).setTextSize(convertDpToPixel(16));
        pieChart.setNoDataText("Pas encore d'historique? Revenez demain!");
        pieChart.setNoDataTextColor(Color.BLACK);
    }

    private void setData() {
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

        // refresh pie chart
        pieChart.invalidate();
    }

    @Override
    protected void onStart() {
        super.onStart();
        System.out.println("PieChartHistoryActivity::onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        System.out.println("PieChartHistoryActivity::onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        System.out.println("PieChartHistoryActivity::onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        System.out.println("PieChartHistoryActivity::onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.out.println("PieChartHistoryActivity::onDestroy()");
    }

}
