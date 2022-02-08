package com.proj.pomodoro;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.mohammedalaa.seekbar.OnRangeSeekBarChangeListener;
import com.mohammedalaa.seekbar.RangeSeekBarView;

import java.util.ArrayList;

public class add extends AppCompatActivity {
    RangeSeekBarView focus_time;
    RangeSeekBarView short_rest;
    RangeSeekBarView long_rest;
    RangeSeekBarView set;
    ActivityDataAccess access = new ActivityDataAccess(this);
    Button create;
    CheckBox red;
    CheckBox blue;
    CheckBox black;
    CheckBox green;
    CheckBox yellow;
    TextView name;
    private String title, color;
    private int min,short_break,long_break,Short_rest_step;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        focus_time =findViewById(R.id.focus_time);
        short_rest =findViewById(R.id.short_break);
        long_rest =findViewById(R.id.long_break);
        create =findViewById(R.id.create);
        set =findViewById(R.id.set);
        name =findViewById(R.id.name);
        focus_time.setOnRangeSeekBarViewChangeListener(new OnRangeSeekBarChangeListener() {
            @Override
            public void onProgressChanged(@Nullable RangeSeekBarView seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(@Nullable RangeSeekBarView seekBar, int progress) {

            }

            @Override
            public void onStopTrackingTouch(@Nullable RangeSeekBarView seekBar, int progress) {
                min = progress;
            }
        });
        long_rest.setOnRangeSeekBarViewChangeListener(new OnRangeSeekBarChangeListener() {
            @Override
            public void onProgressChanged(@Nullable RangeSeekBarView seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(@Nullable RangeSeekBarView seekBar, int progress) {

            }

            @Override
            public void onStopTrackingTouch(@Nullable RangeSeekBarView seekBar, int progress) {
                long_break=progress;
            }
        });
        short_rest.setOnRangeSeekBarViewChangeListener(new OnRangeSeekBarChangeListener() {
            @Override
            public void onProgressChanged(@Nullable RangeSeekBarView seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(@Nullable RangeSeekBarView seekBar, int progress) {

            }

            @Override
            public void onStopTrackingTouch(@Nullable RangeSeekBarView seekBar, int progress) {
                short_break=progress;
            }
        });
        set.setOnRangeSeekBarViewChangeListener(new OnRangeSeekBarChangeListener() {
            @Override
            public void onProgressChanged(@Nullable RangeSeekBarView seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(@Nullable RangeSeekBarView seekBar, int progress) {

            }

            @Override
            public void onStopTrackingTouch(@Nullable RangeSeekBarView seekBar, int progress) {
                Short_rest_step = progress;
            }
        });
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                title= name.getText().toString();
                Activity_promo test = new Activity_promo(title,min,short_break,long_break,Short_rest_step);
                access.openDB();
                access.addNewACTIVITY(test);
                //ArrayList<Activity_promo> arrayList = access.getall();
                access.closeDB();
                Intent intent = new Intent(add.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}