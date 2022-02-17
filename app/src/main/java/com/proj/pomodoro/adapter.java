package com.proj.pomodoro;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class adapter extends RecyclerView.Adapter<adapter.holder> {
    private ArrayList<Activity_promo> promodoro;


    public adapter(ArrayList<Activity_promo> promodoro) {
        this.promodoro = promodoro;


    }

    @NonNull
    @Override
    public holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.i("fdfdfd","33");
        return new holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull holder holder, int position) {
        holder.set_note(promodoro.get(position));
        holder.layoutNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                Activity activity = (Activity) view.getContext();
//                activity.finish();
                MainActivity.selected=promodoro.get(position);
                MainActivity.curent_set = 1;
                MainActivity.init_timer(promodoro.get(position));
                //view.getContext().startActivity(intent);
            }
        });
        Log.i("fdfdfd",position+"");
    }

    @Override
    public int getItemCount() {
        Log.i("fdfdfd","getItemCount");
        return promodoro.size();
    }

    @Override
    public int getItemViewType(int position) {
        Log.i("fdfdfd","getItemViewType");

        return position;

    }
    public void n_() {
        promodoro = new ArrayList<Activity_promo>();

        //this.messages.add(new Message("salam"));
        notifyDataSetChanged();
        // to render the list we need to notify
    }

    static class holder extends RecyclerView.ViewHolder{
        TextView title  ,time;
        ConstraintLayout layoutNote;

        holder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            time = itemView.findViewById(R.id.time);
            layoutNote = itemView.findViewById(R.id.layoutitem);
        }
        void set_note(Activity_promo note){
            title.setText(note.getTitle());
            time.setText(note.getMin()+"");
        }
    }
}

