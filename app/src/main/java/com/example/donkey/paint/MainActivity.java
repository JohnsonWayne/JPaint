package com.example.donkey.paint;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SeekBar;
import android.widget.TextView;

// Main activity class
public class MainActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {

    private PaintView paintView;
    private SeekBar SeekAlpha;
    private SeekBar SeekRed;
    private SeekBar SeekGreen;
    private SeekBar SeekBlue;
    // Default ARGB values
    private int Alpha = 255;
    private int Red = 100;
    private int Green = 167;
    private int Blue = 11;

    TextView ShowColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Get reference to the paintView
        paintView = (PaintView) findViewById(R.id.paintView);
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        paintView.init(metrics);

        // Get references to the SeekBars
        SeekAlpha = (SeekBar) findViewById(R.id.seekAlpha);
        SeekRed = (SeekBar) findViewById(R.id.seekRed);
        SeekGreen = (SeekBar) findViewById(R.id.seekGreen);
        SeekBlue = (SeekBar) findViewById(R.id.seekBlue);

        // Reference the TextView to update the colors text
        ShowColor = (TextView) findViewById(R.id.textView1);


        // Implement OnSeekBarChangeListener
        SeekAlpha.setOnSeekBarChangeListener(this);
        SeekRed.setOnSeekBarChangeListener(this);
        SeekGreen.setOnSeekBarChangeListener(this);
        SeekBlue.setOnSeekBarChangeListener(this);
    }

    // Create menu options in title bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // Menu Options Selection
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // To add more options later add new case referencing id in main.xml
        switch (item.getItemId()) {
            case R.id.clear:
                paintView.clear();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


    // Override methods for SeekBar color changers
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        // Get ARGB values
        Alpha = SeekAlpha.getProgress();
        Red = SeekRed.getProgress();
        Green = SeekGreen.getProgress();
        Blue = SeekBlue.getProgress();

        // Get id reference of the value being changed
        int id = seekBar.getId();

        // Get the value
        if (id == com.example.donkey.paint.R.id.seekAlpha) {
            Alpha = progress;
        } else if (id == com.example.donkey.paint.R.id.seekRed) {
            Red = progress;
        } else if (id == com.example.donkey.paint.R.id.seekGreen) {
            Green = progress;
        } else if (id == com.example.donkey.paint.R.id.seekBlue) {
            Blue = progress;
        }

        changeColor();


    }

    // Set the brush color and view background color
    private void changeColor() {
        //Build and show the new color in the view
        ShowColor.setBackgroundColor(Color.argb(Alpha, Red, Green, Blue));
        // Change the color of the brush
        paintView.setColor(Color.argb(Alpha, Red, Green, Blue));
    }


    // Not used
    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    // Not used
    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
