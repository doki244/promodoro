package com.proj.pomodoro;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import dev.shreyaspatil.MaterialDialog.AbstractDialog;
import dev.shreyaspatil.MaterialDialog.MaterialDialog;
import dev.shreyaspatil.MaterialDialog.interfaces.DialogInterface;

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
        holder.layoutNote.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                ActivityDataAccess access = new ActivityDataAccess(view.getContext());
                holder.mDialog = new MaterialDialog.Builder((Activity) view.getContext())
                        .setTitle("what do you want to do ?")
                        .setCancelable(true)
                        .setPositiveButton("Delete",  new MaterialDialog.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                                access.openDB();
                                if (promodoro.get(position).getId()!=1){
                                    if (access.deleteBYid(promodoro.get(position).getId())) {
                                        Toast.makeText(view.getContext(), "successful.", Toast.LENGTH_SHORT).show();
                                        MainActivity.selected=promodoro.get(1);
                                        MainActivity.curent_set = 1;
                                        MainActivity.init_timer(promodoro.get(1));
                                        promodoro = access.getall();
                                        notifyDataSetChanged();
                                    }else
                                        Toast.makeText(view.getContext(), "something is wrong.", Toast.LENGTH_SHORT).show();
                                }else
                                    Toast.makeText(view.getContext(), "cant delete default pomodoro.", Toast.LENGTH_SHORT).show();

                                access.closeDB();
                                dialogInterface.dismiss();
                            }
                        }).setNegativeButton("Edit", new MaterialDialog.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                                if (promodoro.get(position).getId()!=1) {
                                    ((Activity) view.getContext()).finish();
                                    Intent intent = new Intent(view.getContext(), add.class);
                                    intent.putExtra("index", position);
                                    ((Activity) view.getContext()).startActivity(intent);
                                }else
                                    Toast.makeText(view.getContext(), "cant edit default pomodoro.", Toast.LENGTH_SHORT).show();
                                dialogInterface.dismiss();
                            }
                        })
                        .build();
                holder.mDialog.show();
                return false;
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
        MaterialDialog mDialog;
        holder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            time = itemView.findViewById(R.id.time);
            layoutNote = itemView.findViewById(R.id.layoutitem);
            MaterialDialog mDialog;
        }
        void set_note(Activity_promo note){
            title.setText(note.getTitle());
            time.setText(note.getMin()+"");
        }
    }
}

