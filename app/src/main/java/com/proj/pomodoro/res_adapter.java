package com.proj.pomodoro;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.math.MathUtils;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class res_adapter extends RecyclerView.Adapter<res_adapter.res_holder> {
    private ArrayList<result_time> result;
    public static ArrayList<result_time> dirty_result;
    public res_adapter(ArrayList<result_time> result,ArrayList<result_time> dirty_result) {
        this.result = result;
        this.dirty_result = dirty_result;
    }

    @NonNull
    @Override
    public res_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.i("fdfdfd","33");
        return new res_holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row,parent,false));
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull res_holder holder, int position) {
        Log.i("255252",position+"");
        try {
            holder.set_note(result.get(position));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.ConstraintLayout.getVisibility()==View.VISIBLE){
                    holder.ConstraintLayout.setVisibility(View.GONE);
                }else {
                    holder.ConstraintLayout.setVisibility(View.VISIBLE);
                }
//                Activity activity = (Activity) view.getContext();
//                activity.finish();
                //MainActivity.selected=result.get(position);
                //MainActivity.curent_set = 1;
                //MainActivity.init_timer(result.get(position));
                //view.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        Log.i("fdfdfd","getItemCount");
        return result.size();
    }

    @Override
    public int getItemViewType(int position) {
        Log.i("fdfdfd","getItemViewType");

        return position;

    }
    public void n_() {
        result = new ArrayList<result_time>();

        //this.messages.add(new Message("salam"));
        notifyDataSetChanged();
        // to render the list we need to notify
    }

    static class res_holder extends RecyclerView.ViewHolder{
        TextView title  ,month,day,total_rest,total_focus, week;
        LinearLayout layout;
        ConstraintLayout ConstraintLayout;

        res_holder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.dit_title);
            day = itemView.findViewById(R.id.day);
            week = itemView.findViewById(R.id.week);
            month = itemView.findViewById(R.id.month);
            total_focus = itemView.findViewById(R.id.totalfocus);
            total_rest = itemView.findViewById(R.id.totalrest);
            layout = itemView.findViewById(R.id.dit_layout);
            ConstraintLayout = itemView.findViewById(R.id.expand);

        }
        @RequiresApi(api = Build.VERSION_CODES.O)
        void set_note(result_time res) throws ParseException {
            //title.setText(res.getTitle());
            //res.getDate();
            title.setText(res.getTitle());
            total_focus.setText((TimeUnit.MILLISECONDS.toMinutes(res.getTotal_focus_time())==0 ? 1:TimeUnit.MILLISECONDS.toMinutes((res.getTotal_focus_time())))+"M");
            if (res.getTotal_rest()>0)
                total_rest.setText((TimeUnit.MILLISECONDS.toMinutes(res.getTotal_rest())==0 ? 1:TimeUnit.MILLISECONDS.toMinutes(res.getTotal_rest()))+"M");
            //today

            Date date = new Date();
            String time_str = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault()).format(date);
            Date theday = new SimpleDateFormat("dd MMMM yyyy", Locale.ENGLISH).parse(time_str);
            //week ago
            Instant now = Instant.now(); //current date
            Instant before = now.minus(Duration.ofDays(7));
            date = Date.from(before);
            time_str = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault()).format(date);
            Date theweek = new SimpleDateFormat("dd MMMM yyyy", Locale.ENGLISH).parse(time_str);
            //month ago
            before = now.minus(Duration.ofDays(30));
            date = Date.from(before);
            time_str = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault()).format(date);
            Date themonth = new SimpleDateFormat("dd MMMM yyyy", Locale.ENGLISH).parse(time_str);

            int d_sum=0;
            int w_sum=0;
            int m_sum=0;
            for (result_time p:dirty_result) {
                if (p.getTitle().equals(res.getTitle())){
                    if (p.getDate().equals(theday)){
                        d_sum+=p.getTotal_focus_time();
                        Log.i("hthththf", p.getTotal_focus_time()+"yes ");
                    }
                    if (p.getDate().after(theweek)){
                        w_sum+=p.getTotal_focus_time();
                    }
                    if (p.getDate().after(themonth)){
                        m_sum+=p.getTotal_focus_time();
                    }
                }
                ///}
//                clean_map.put(p.getTitle(),new result_time(p.getTitle(),F_sum,R_sum,p.getDate()));
//                focus_title.put(p.getTitle(), F_sum);
//                rest_title.put(p.getTitle(), R_sum);
            }
            if (d_sum>0){
                day.setText((TimeUnit.MILLISECONDS.toMinutes(d_sum)==0 ? 1:TimeUnit.MILLISECONDS.toMinutes(d_sum))+"M");
            }else {
                day.setText(0+"M");
            }
            if (w_sum>0){
                week.setText((TimeUnit.MILLISECONDS.toMinutes(w_sum)==0 ? 1:TimeUnit.MILLISECONDS.toMinutes(w_sum))+"M");
            }else {
                week.setText(0+"M");
            }
            if (m_sum>0){
                month.setText((TimeUnit.MILLISECONDS.toMinutes(m_sum)==0 ? 1:TimeUnit.MILLISECONDS.toMinutes(m_sum))+"M");
            }else {
                month.setText(0+"M");
            }
            //time.setText(note.getMin()+"");
            ConstraintLayout.setVisibility(View.GONE);
        }

    }
}