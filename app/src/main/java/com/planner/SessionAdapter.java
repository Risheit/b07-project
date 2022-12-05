package com.planner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.models.Session;


import java.util.List;

//this class is used to put the inputs of a list of sessions into the table
public class SessionAdapter extends RecyclerView.Adapter<SessionAdapter.ViewHolder> {

    Context context;
    List<Session> sessionList;

    public SessionAdapter(Context context, List<Session> sessionList){
        this.context = context;
        this.sessionList = sessionList;
    }

    @NonNull
    @Override
    public SessionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_session_layout,parent,false);
        return new ViewHolder(view);
    }

    //for each session, a new row is made with the session date on the left side, and the
    //list of course codes separated by commas on the right side (i.e., fills the table)
    @Override
    public void onBindViewHolder(@NonNull SessionAdapter.ViewHolder holder, int position){
        if(sessionList != null && sessionList.size() > 0){
            Session s = sessionList.get(position);
            String sessionDate = s.getSeason() +  " " + s.getYear();
            holder.sessionPrime.setText(sessionDate);
            holder.coursePrime.setText(s.presentCourses());
        }
    }

    @Override
    public int getItemCount() {
        return sessionList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView sessionPrime, coursePrime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            sessionPrime = itemView.findViewById(R.id.sessionPrime);
            coursePrime = itemView.findViewById(R.id.coursePrime);
        }
    }
}
