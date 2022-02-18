package com.proj.pomodoro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import dev.shreyaspatil.MaterialDialog.MaterialDialog;
import dev.shreyaspatil.MaterialDialog.interfaces.DialogInterface;

public class MainActivity extends AppCompatActivity  {
    Animation round;
    Animation alpha;
    Animation btn_anim;
    ImageView clock;
    static TextView min_view,sec_view ,title_text,total_set;
    TextView curentSet;
    ImageView imageView;
    Handler customHandler = new Handler();
    long startTime;
    static long focustime;
    static long sum_focus;
    static long sum_break;
    //long resttime = 0;
    static int curent_set=1;
    ImageView addpromo;
    Button startbtn;
    Button stopbtn;
    MaterialDialog mDialog;
    RecyclerView recyclerView_bottom;
    LottieAnimationView rest_anim;
    public static boolean saved =true;
    public static Activity_promo selected;
    ActivityDataAccess access = new ActivityDataAccess(this);
    ResultDataAccess result_access = new ResultDataAccess(this);
    adapter ada ;
    private boolean break_time=false;
    CountDownTimer countDownTimer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LinearLayout bottomSheet = findViewById(R.id.design_bottom_sheet);
        recyclerView_bottom = findViewById(R.id.recyclerView_bottom);
        min_view = findViewById(R.id.min_view);
        //rest_anim = findViewById(R.id.rest_anim);
        //rest_anim.setVisibility(View.GONE);
        clock = findViewById(R.id.clock);
        startbtn = findViewById(R.id.start);
        stopbtn = findViewById(R.id.stoptbtn);
        imageView = findViewById(R.id.imageView);
        Bundle b = getIntent().getExtras();
        if (b!=null){
            Log.i("qqqqqqqqqqq", "onCreate: ");
            if (b.get("break_time")!=null)
                break_time=b.getBoolean("break_time");
        }
        sec_view = findViewById(R.id.sec_view);
        addpromo = findViewById(R.id.addpromo);
        total_set = findViewById(R.id.total_set);
        curentSet = findViewById(R.id.curent_set);
        curentSet.setText(curent_set+"");
        title_text = findViewById(R.id.title_text);
        BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);


        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        if (sharedPref.getInt("first_time",-1)==-1){
            access.openDB();
            Activity_promo test = new Activity_promo("reading",25,4,15,4);
            access.addNewACTIVITY(test);
            SharedPreferences.Editor editor = sharedPref.edit();
            selected=test;
            init_timer(test);
            editor.putInt("first_time", 1);
            editor.apply();
            access.closeDB();
        }else {
            access.openDB();
            ArrayList<Activity_promo> arrayList = access.getall();
            access.closeDB();
            Log.i("1234111", arrayList.size()+"onCreate: ");
            selected=arrayList.get(0);
            init_timer(arrayList.get(0));
        }
        access.openDB();
        ArrayList<Activity_promo> arrayList = access.getall();
        access.closeDB();

        Log.i("TAG", arrayList.toString());


        if (break_time){
            //rest_anim.setVisibility(View.VISIBLE);
            clock.setVisibility(View.INVISIBLE);
            imageView.setVisibility(View.INVISIBLE);

        }
        round = AnimationUtils.loadAnimation(this,R.anim.round);
        alpha = AnimationUtils.loadAnimation(this,R.anim.alpha);
        btn_anim = AnimationUtils.loadAnimation(this,R.anim.btn);
        startbtn.startAnimation(btn_anim);
        imageView.startAnimation(alpha);
        clock.startAnimation(alpha);
        stopbtn.setVisibility(View.GONE);
        mDialog = new MaterialDialog.Builder(this)
                .setTitle("Short break...!")
                .setMessage("Enjoying a rest with a cup of tea")
                .setAnimation(R.raw.breath)
                .setCancelable(false)
                .setPositiveButton("Start", R.drawable.add_circle_24, new MaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        //breakStart();
                        break_time=true;
                        Intent intent =new Intent(MainActivity.this,break_activi.class);
                        //getIntent().putExtra("break_time",true);
                        startActivity(intent);
                        dialogInterface.dismiss();
//                        countDownTimer = new CountDownTimer(selected.getShort_rest()*60000, 1000) {
//
//                            public void onTick(long millisUntilFinished) {
//                                int min = (int) TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished);
//                                int sec = (int) (TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
//                                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)));
//                                //Log.i("dorrr", min+"  "+sec);
//                                //min_view.setText(min+"");
//                                //sec_view.setText(sec+"");
//
//                            }
//                            public void onFinish() {
//                                breakStop();
//                                dialogInterface.dismiss();
//                            }
//                        }.start();
                    }
                }).setNegativeButton("skip", R.drawable.add_circle_24, new MaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        if (curent_set > selected.getShort_rest_step())
                            curent_set=1;
                        curentSet.setText(curent_set+"");
                        dialogInterface.dismiss();
                    }
                })
                .build();
        ada = new adapter(arrayList);
        recyclerView_bottom.setLayoutManager(new StaggeredGridLayoutManager(4,StaggeredGridLayoutManager.VERTICAL));
        recyclerView_bottom.setAdapter(ada);
        startbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clock.startAnimation(round);
                stopbtn.setVisibility(View.VISIBLE);
                startbtn.setVisibility(View.GONE);//60000
                starttimer(selected,"focus");
                focusStart();
                saved=false;
            }
        });
        stopbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clock.clearAnimation();
                stopbtn.setVisibility(View.GONE);
                startbtn.setVisibility(View.VISIBLE);
                focusStop();
                saved=true;
                countDownTimer.cancel();
                result_access.openDB();
                result_access.addNewresult(new result_time(selected.getTitle(),focustime,break_activi.resttime,new Date()));
                focustime = 0 ;
                break_activi.resttime=0;
                result_access.closeDB();
            }
        });
        addpromo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,add.class);
                startActivity(intent);
                finish();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("ssssssssssss", "onRestart: ");
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.nav, menu);
        menu.getItem(0).setIcon(R.drawable.report);
        return true;
    }

//    public void showPopup(View v) {
//        PopupMenu popup = new PopupMenu(this, v);
//        popup.setOnMenuItemClickListener(this);
//        popup.inflate(R.menu.nav);
//        popup.show();
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        Log.i("TAG", "onMenuItemClick: ");
       switch (menuItem.getItemId()) {
            case R.id.statistics:

                Intent intent = new Intent(this,statistics.class);
                startActivity(intent);
              return true;
//            case R.id.creportitem:
//                //reportDialog.show();
//                return true;
//            case R.id.cclearhistoryitem:
//               // clehisDialog.show();
//                return true;
//            default:
//                return false;
        }
        return false;
    }
public void starttimer(Activity_promo activity_promo,String type){
    countDownTimer = new CountDownTimer(activity_promo.getMin()*60000, 1000) {

        public void onTick(long millisUntilFinished) {
            int min = (int) TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished);
            int sec = (int) (TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)));

            //Log.i("dorrr", min+"  "+sec);
            min_view.setText(min+"");
            sec_view.setText(sec+"");

        }

        public void onFinish() {
            int min = (int) TimeUnit.MILLISECONDS.toMinutes(activity_promo.getMin()*60000);
            int sec = (int) (TimeUnit.MILLISECONDS.toSeconds(activity_promo.getMin()*60000) -
                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(activity_promo.getMin()*60000)));
            focusStop();
            mDialog.show();
            curent_set++;
            curentSet.setText(curent_set+"");
            clock.clearAnimation();
            stopbtn.setVisibility(View.GONE);
            startbtn.setVisibility(View.VISIBLE);
            //save in db
            //Log.i("dorrr", min+"  "+sec);
            min_view.setText(min+"");
            sec_view.setText(sec+"");

        }

    }.start();
}
public static void init_timer(Activity_promo activity_promo){
    int min = (int) TimeUnit.MILLISECONDS.toMinutes(activity_promo.getMin()*60000);
    int sec = (int) (TimeUnit.MILLISECONDS.toSeconds(activity_promo.getMin()*60000) -
            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(activity_promo.getMin()*60000)));
    //Log.i("dorrr", min+"  "+sec);
    sum_focus = 0;
    sum_break=0;
    min_view.setText(min+"");
    total_set.setText(activity_promo.getShort_rest_step()+"");
    sec_view.setText(sec+"");
    title_text.setText(activity_promo.getTitle());

}

    public void focusStart() {
        startTime = SystemClock.uptimeMillis();
        customHandler.postDelayed(updateTimerThread, 0);
    }

    public void focusStop() {
        customHandler.removeCallbacks(updateTimerThread);
        sum_focus+=focustime;
    }

    private Runnable updateTimerThread = new Runnable() {
        public void run() {
            focustime = SystemClock.uptimeMillis() - startTime;

            Log.i("focustime", focustime+"sum: "+sum_focus);
            //min_view.setText(getDateFromMillis(timeInMilliseconds));
            customHandler.postDelayed(this, 1000);
        }
    };

    @Override
    protected void onStop() {
        super.onStop();


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!saved){
            result_access.openDB();
            result_access.addNewresult(new result_time(selected.getTitle(),focustime,break_activi.resttime,new Date()));
            result_access.closeDB();
        }
    }
}