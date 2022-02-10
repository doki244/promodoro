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

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class break_activi extends AppCompatActivity {
    Handler customHandler = new Handler();
    long startTime;
    static long resttime;
    static long sum_break;
    TextView min_view,sec_view;
    CountDownTimer countDownTimer;
    ResultDataAccess result_access = new ResultDataAccess(this);
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

    @Override
    protected void onStop() {
        super.onStop();
//        if(!MainActivity.saved){
//            result_access.openDB();
//            result_access.addNewresult(new result_time(MainActivity.selected.getTitle(),MainActivity.focustime,break_activi.resttime,new Date()));
//            MainActivity.focustime = 0 ;
//            resttime=0;
//            result_access.closeDB();
//
//        }
    }
}