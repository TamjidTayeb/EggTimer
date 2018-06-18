package com.example.tamjid.eggtimer;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    SeekBar seekBar;
    TextView textView;
    boolean counterActive = false;
    Button controllerButton;
    CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //make fullscreen
        getSupportActionBar().hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        controllerButton = findViewById(R.id.button);

        seekBar = findViewById(R.id.seekBar);
        seekBar.setMax(600);
        seekBar.setProgress(30);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                updateTimer(i);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {


            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void resetTime() {
        textView.setText("0:00");
        seekBar.setProgress(0);
        seekBar.setEnabled(true);
        counterActive = false;
        controllerButton.setText("Go!");
        countDownTimer.cancel();
    }

    public void updateTimer(int secondsLeft) {
        int minutes = (int) secondsLeft/60;
        int seconds = secondsLeft-minutes*60;
        String secondsString = Integer.toString(seconds);
        if (seconds <10) secondsString = "0"+secondsString;

        textView = findViewById(R.id.time);
        textView.setText(Integer.toString(minutes)+":"+secondsString);
    }

    public void controlTimer(View view) {
        if (counterActive) {
            resetTime();
        } else {
            counterActive = true;
            seekBar.setEnabled(false);
            controllerButton.setText("Stop!");

            countDownTimer = new CountDownTimer(seekBar.getProgress() * 1000 + 100, 1000) {
                @Override
                public void onTick(long l) {
                    updateTimer((int) l/1000);

                }

                @Override
                public void onFinish() {
                    resetTime();
                    MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.airhorn);
                    mp.start();
                    Log.i("Finished", "Timer done");

                }
            }.start();
        }

    }
}
