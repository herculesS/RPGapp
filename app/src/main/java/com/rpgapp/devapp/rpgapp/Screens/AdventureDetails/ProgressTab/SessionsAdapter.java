package com.rpgapp.devapp.rpgapp.Screens.AdventureDetails.ProgressTab;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rpgapp.devapp.rpgapp.Model.Adventure;
import com.rpgapp.devapp.rpgapp.Model.Session;
import com.rpgapp.devapp.rpgapp.R;
import com.rpgapp.devapp.rpgapp.Screens.Adventures.AdventuresFragment;
import com.rpgapp.devapp.rpgapp.Screens.Session.SessionFragment;
import com.rpgapp.devapp.rpgapp.Utils.Utils;

import java.util.ArrayList;

public class SessionsAdapter extends RecyclerView.Adapter<SessionsAdapter.ViewHolder> {

    private ArrayList<Session> mSessions;
    private FragmentManager mFm;
    private Adventure mAdventure;

    public SessionsAdapter(ArrayList<Session> sessions, FragmentManager fm, Adventure adventure) {
        mFm = fm;
        mAdventure = adventure;
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
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.mDateTextView.setText(Utils.formatSessionDateToDayMonth(mSessions.get(position).getDate()));
        holder.mSession = mSessions.get(position);
        holder.mTitleView.setText(mSessions.get(position).getTitle());
        holder.mSummaryTextView.setText(mSessions.get(position).getSummary());
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SessionFragment fragment = SessionFragment.newInstance(mAdventure, position);
                mFm.
                        beginTransaction().
                        replace(R.id.container, fragment).
                        commit();
            }
        });
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
        public View mView;

        public ViewHolder(View itemView) {

            super(itemView);
            Log.d("teste", "c");
            mDateTextView =  itemView.findViewById(R.id.session_list_view_item_date);
            mTitleView = itemView.findViewById(R.id.session_list_view_item_title);
            mSummaryTextView =  itemView.findViewById(R.id.session_list_view_item_summary);
            mView = itemView;

        }
    }
}
