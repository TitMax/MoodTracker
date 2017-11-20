package com.boussaingault.maxime.moodtracker.activities;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.boussaingault.maxime.moodtracker.R;
import com.boussaingault.maxime.moodtracker.models.DatabaseManager;
import com.github.mikephil.charting.animation.Easing;
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

    private SeekBar mSeekBar;
    private PieChart mPieChart;
    private TextView mTextViewMin;
    private TextView mTextViewMax;
    private TextView mTextViewCurrent;
    private DatabaseManager mDatabaseManager;
    private String[] moods = {"Sad", "Disappointed", "Normal", "Happy", "Super Happy"};
    private String[] moodsFR = {"Triste", "Déçu", "Normal", "Heureux", "Super Heureux"};
    private int maxDays = 0;
    private int daysNumber = 7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pie_chart_history);

        mSeekBar = (SeekBar) findViewById(R.id.activity_piechart_history_seek_bar);
        mPieChart = (PieChart) findViewById(R.id.activity_piechart_history_pie_chart);
        mTextViewMin = (TextView) findViewById(R.id.activity_piechart_history_seek_bar_min_value);
        mTextViewMax = (TextView) findViewById(R.id.activity_piechart_history_seek_bar_max_value);
        mTextViewCurrent = (TextView) findViewById(R.id.activity_piechart_history_seek_bar_current_value);

        mDatabaseManager = new DatabaseManager(this);
        maxDays = mDatabaseManager.isHistory();

        if(maxDays != 0) {
            mSeekBar.setVisibility(View.VISIBLE);
            mTextViewMin.setText(String.valueOf(7));
            mTextViewMax.setText(String.valueOf(maxDays));
            mTextViewCurrent.setText(String.valueOf(daysNumber) + " jours");
            setData();
        } else
            mSeekBar.setVisibility(View.GONE);

        mSeekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                mSeekBar.setMax(maxDays - 7);
                daysNumber = progress + 7;
                mTextViewCurrent.setText(String.valueOf(daysNumber) + " jours");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                setData();
                mPieChart.notifyDataSetChanged();
                mPieChart.invalidate();
            }
        });

        mPieChart.setUsePercentValues(true);
        mPieChart.setDescription(null);
        mPieChart.setExtraOffsets(10,10,10,10);

        mPieChart.setHoleColor(android.R.color.darker_gray);
        mPieChart.setTransparentCircleAlpha(0);
        mPieChart.setHoleRadius(25);

        mPieChart.setDrawEntryLabels(false);

        Legend l = mPieChart.getLegend();
        l.setTextSize(14);
        // Positioning legend to the bottom left of the screen
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setXOffset(10);

        mPieChart.setTouchEnabled(false);

        // Text when no data to show
        mPieChart.getPaint(Chart.PAINT_INFO).setTextSize(convertDpToPixel(16));
        mPieChart.setNoDataText("Pas encore d'historique? Revenez demain!");
        mPieChart.setNoDataTextColor(Color.BLACK);

        mDatabaseManager.close();
    }

    private void setData() {
        List<PieEntry> entries = new ArrayList<>();
        for (int i = 0; i < moods.length; i++) {
            if (mDatabaseManager.countMoods(moods[i], daysNumber) > 0)
                entries.add(new PieEntry((float) mDatabaseManager.countMoods(moods[i], daysNumber), moodsFR[i]));
        }

        // Create pieDataSet
        PieDataSet dataSet = new PieDataSet(entries, "");

        // Adding colors
        int[] colors = {getResources().getColor(R.color.faded_red),
                getResources().getColor(R.color.warm_grey),
                getResources().getColor(R.color.cornflower_blue_65),
                getResources().getColor(R.color.light_sage),
                getResources().getColor(R.color.banana_yellow)};

        dataSet.setColors(colors);
        dataSet.setSliceSpace(3);

        // Create pie data object
        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(20);
        mPieChart.setData(data);

        // refresh pie chart
        mPieChart.invalidate();
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
