package com.rpgapp.devapp.rpgapp.Screens.AdventureDetails.ProgressTab;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rpgapp.devapp.rpgapp.Model.Session;
import com.rpgapp.devapp.rpgapp.R;
import com.rpgapp.devapp.rpgapp.Utils.Utils;

import java.util.ArrayList;

public class SessionsAdapter extends RecyclerView.Adapter<SessionsAdapter.ViewHolder> {

    private ArrayList<Session> mSessions;

    public SessionsAdapter(ArrayList<Session> sessions) {
        if(sessions == null) {
            mSessions = new ArrayList<>();
        } else {
            mSessions = sessions;
        }


    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sessions_list_item, parent, false);
        Log.d("teste", "cr");
        return new SessionsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.mDateTextView.setText(Utils.formatSessionDateToDayMonth(mSessions.get(position).getDate()));
        holder.mSession = mSessions.get(position);
        holder.mTitleView.setText(mSessions.get(position).getTitle());
        holder.mSummaryTextView.setText(mSessions.get(position).getSummary());
        Log.d("teste", "b");
    }

    @Override
    public int getItemCount() {
        Log.d("teste", "g");
        return mSessions.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public Session mSession;
        public TextView mDateTextView;
        public TextView mTitleView;
        public TextView mSummaryTextView;

        public ViewHolder(View itemView) {

            super(itemView);
            Log.d("teste", "c");
            mDateTextView =  itemView.findViewById(R.id.session_list_view_item_date);
            mTitleView = itemView.findViewById(R.id.session_list_view_item_title);
            mSummaryTextView =  itemView.findViewById(R.id.session_list_view_item_summary);

        }
    }
}
