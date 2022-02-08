package com.proj.pomodoro;

import static com.proj.pomodoro.MainActivity.curent_set;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

public class break_activi extends AppCompatActivity {
    Handler customHandler = new Handler();
    long startTime,resttime;
    static long sum_break;
    TextView min_view,sec_view;
    CountDownTimer countDownTimer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_break);
        sec_view = findViewById(R.id.sec_view);
        min_view = findViewById(R.id.min_view);

        Log.i("curent_set", curent_set+"");
        if (curent_set > MainActivity.selected.getShort_rest_step()) {
            curent_set=1;
            starttimer(MainActivity.selected.getLong_rest());
            //curentSet.setText(curent_set + "");
        }else
            starttimer(MainActivity.selected.getShort_rest());
    }
    public void starttimer(int rest) {
        breakStart();
         countDownTimer = new CountDownTimer(rest * 60000, 1000) {

            public void onTick(long millisUntilFinished) {
                int min = (int) TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished);
                int sec = (int) (TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)));

                //Log.i("dorrr", min+"  "+sec);
                min_view.setText(min + "");
                sec_view.setText(sec + "");

            }

            public void onFinish() {
                breakStop();
                finish();
            }

        }.start();
    }

    public void breakStart() {
        startTime = SystemClock.uptimeMillis();
        customHandler.postDelayed(TimerThread, 0);
    }

    public void breakStop() {
        customHandler.removeCallbacks(TimerThread);
        sum_break+=resttime;
    }

    private Runnable TimerThread = new Runnable() {
        public void run() {
            resttime = SystemClock.uptimeMillis() - startTime;
            customHandler.postDelayed(this, 1000);
        }
    };
}