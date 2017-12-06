package com.boussaingault.maxime.moodtracker.activities;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.boussaingault.maxime.moodtracker.R;
import com.boussaingault.maxime.moodtracker.models.DatabaseManager;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import static com.github.mikephil.charting.utils.Utils.convertDpToPixel;

/**
 * Created by Maxime Boussaingault on 20/11/2017.
 */

public class PieChartHistoryActivity extends AppCompatActivity {

    private SeekBar mSeekBar;
    private PieChart mPieChart;
    private TextView mTextViewMin;
    private TextView mTextViewMax;
    private TextView mTextViewCurrent;
    private DatabaseManager mDatabaseManager;
    private String[] moods = {"Sad", "Disappointed", "Normal", "Happy", "Super Happy"};
    private String[] moodsFR = {"Triste", "Déçu", "Normal", "Joyeux", "Super Joyeux"};
    private int maxDays = 0;
    private static final int MIN = 7;
    private int daysNumber = 7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pie_chart_history);

        // Wire widgets
        mSeekBar = findViewById(R.id.activity_piechart_history_seek_bar);
        mPieChart = findViewById(R.id.activity_piechart_history_pie_chart);
        mTextViewMin = findViewById(R.id.activity_piechart_history_seek_bar_min_value);
        mTextViewMax = findViewById(R.id.activity_piechart_history_seek_bar_max_value);
        mTextViewCurrent = findViewById(R.id.activity_piechart_history_seek_bar_current_value);

        mDatabaseManager = new DatabaseManager(this); // Open database
        maxDays = mDatabaseManager.isHistory(); // Get the count of entries using isHistory() method
        mDatabaseManager.close();

        if(maxDays != 0) { // If there is an history
            // Initialize the display
            mSeekBar.setVisibility(View.VISIBLE); // GONE by default
            if (maxDays <= 7) {
                mTextViewMin.setText("1");
                mTextViewMax.setText(String.valueOf(maxDays));
                if (maxDays == 1)
                    mTextViewCurrent.setText(MessageFormat.format("{0} jour", maxDays));
                else
                    mTextViewCurrent.setText(MessageFormat.format("{0} jours", maxDays));
                mSeekBar.setMax(maxDays);
                mSeekBar.setProgress(maxDays);
                // SeekBar locked the first week
                mSeekBar.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        return true;
                    }
                });
            } else {
                mTextViewMin.setText(String.valueOf(MIN));
                mTextViewMax.setText(String.valueOf(maxDays));
                mTextViewCurrent.setText(MessageFormat.format("{0} jours", daysNumber));
            }
            setData(); // Call piechart setData() method
        }

        mSeekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    mSeekBar.setMax(maxDays - MIN); // subtract the minimal value (7)
                    daysNumber = progress + MIN; // Add the minimum value (7)
                    mTextViewCurrent.setText(MessageFormat.format("{0} jours", daysNumber));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }

            // Called when user release the seekbar
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                setData();  // update piechart data with the new range of the history
                mPieChart.notifyDataSetChanged(); // Notify that the data set changed
                mPieChart.invalidate(); // Refresh the pie chart
            }
        });

        mPieChart.setUsePercentValues(true); // transform values in percent values
        mPieChart.setDescription(null);
        mPieChart.setExtraOffsets(10,10,10,10);

        mPieChart.setHoleColor(android.R.color.darker_gray);
        mPieChart.setTransparentCircleAlpha(0);
        mPieChart.setHoleRadius(25);

        mPieChart.setDrawEntryLabels(false);    // hide labels (moods)

        /*      Configure the legend        */
        Legend l = mPieChart.getLegend();
        l.setTextSize(14);
        // Positioning legend to the bottom left of the screen
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setXOffset(10);
        l.setYOffset(15);

        mPieChart.setTouchEnabled(false); // disable the touch on the pie chart (to disable rotation)

        /*      Text when no data to show       */
        mPieChart.getPaint(Chart.PAINT_INFO).setTextSize(convertDpToPixel(16));
        mPieChart.setNoDataText("Pas encore d'historique? Revenez demain!");
        mPieChart.setNoDataTextColor(Color.BLACK);

        System.out.println("PieChartHistoryActivity::onCreate()");
    }

    // Method to count number of each mood and set the pie chart data
    private void setData() {
        mDatabaseManager = new DatabaseManager(this);
        List<PieEntry> entries = new ArrayList<>();
        for (int i = 0; i < moods.length; i++) {
                entries.add(new PieEntry((float) mDatabaseManager.countMoods(moods[i], daysNumber), moodsFR[i]));
        }
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
        dataSet.setSliceSpace(3);

        // Create pie data object
        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentNoDecimalFormatter());
        data.setValueTextSize(20);
        mPieChart.setData(data);

        // refresh pie chart
        mPieChart.invalidate();
    }

    // Method to remove decimal
    public class PercentNoDecimalFormatter implements IValueFormatter {
        private DecimalFormat mFormat;

        public PercentNoDecimalFormatter() {
            mFormat = new DecimalFormat("###,###,###"); // use no decimal
        }

        @Override
        public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
            if (value > 0)
                return mFormat.format(value) + "%";
            else return "";
        }
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
