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
import android.widget.Toast;

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
    String edit ="Edit";
    TextView name;
    private String title, color;
    private int min=25,short_break=5,long_break=15,Short_rest_step=4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        focus_time =findViewById(R.id.focus_time);
        short_rest =findViewById(R.id.short_break);
        long_rest =findViewById(R.id.long_break);
        create =findViewById(R.id.create);
        Bundle b = getIntent().getExtras();
        access.openDB();
        ArrayList<Activity_promo> arrayList = access.getall();
        access.closeDB();
        set =findViewById(R.id.set);
        name =findViewById(R.id.name);
        if (b!=null) {
            if (b.get("index") != null) {


                name.setText(arrayList.get(b.getInt("index")).getTitle());
                focus_time.setCurrentValue(arrayList.get(b.getInt("index")).getMin());
                long_rest.setCurrentValue(arrayList.get(b.getInt("index")).getLong_rest());
                short_rest.setCurrentValue(arrayList.get(b.getInt("index")).getShort_rest());
                set.setCurrentValue(arrayList.get(b.getInt("index")).getShort_rest_step());
                min = arrayList.get(b.getInt("index")).getMin();
                short_break = arrayList.get(b.getInt("index")).getShort_rest();
                long_break = arrayList.get(b.getInt("index")).getLong_rest();
                Short_rest_step = arrayList.get(b.getInt("index")).getShort_rest_step();
                create.setText(edit);
                getIntent().removeExtra("index");
            }
        }
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
                if (title.isEmpty()){
                    Toast.makeText(add.this, "please fill name", Toast.LENGTH_SHORT).show();
                }else {
                    Activity_promo test = new Activity_promo(title, min, short_break, long_break, Short_rest_step);
                    access.openDB();
                    if (create.getText().equals(edit)){
                        access.deleteBYid(arrayList.get(b.getInt("index")).getId());
                    }
                    access.addNewACTIVITY(test);
                    //ArrayList<Activity_promo> arrayList = access.getall();
                    access.closeDB();
                    Intent intent = new Intent(add.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent =new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}