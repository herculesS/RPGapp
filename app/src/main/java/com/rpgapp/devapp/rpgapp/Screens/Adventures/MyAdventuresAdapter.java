package com.rpgapp.devapp.rpgapp.Screens.Adventures;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rpgapp.devapp.rpgapp.DataAccessManager.AdventureRequests.AdventureRequestManager;
import com.rpgapp.devapp.rpgapp.Model.Adventure;
import com.rpgapp.devapp.rpgapp.Screens.Adventures.AdventuresFragment.OnListFragmentInteractionListener;
import com.rpgapp.devapp.rpgapp.R;
import com.rpgapp.devapp.rpgapp.Utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class MyAdventuresAdapter extends RecyclerView.Adapter<MyAdventuresAdapter.ViewHolder> implements AdventureRequestManager.OnAdventuresLoaded {

    private List<Adventure> mValues = new ArrayList<>();
    private AppCompatActivity mActivity;
    private final OnListFragmentInteractionListener mListener;
    private ProgressBar mProgressBar;

    public MyAdventuresAdapter(OnListFragmentInteractionListener listener, AppCompatActivity context) {
        mActivity = context;

        setLoadingBar();

        AdventureRequestManager.getAdventures(this);
        mListener = listener;

    }

    private void setLoadingBar() {
        mProgressBar = new ProgressBar(mActivity, null, android.R.attr.progressBarStyleLarge);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(100,100);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);

        RelativeLayout layout = mActivity.findViewById(R.id.container);
        layout.addView(mProgressBar,params);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_adventures, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mTitle.setText(mValues.get(position).getTitle());
        holder.mNextSession.setText(Utils.getCurrentDay());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    @Override
    public void onComplete(ArrayList<Adventure> adventures) {
        mValues = adventures;
        mProgressBar.setVisibility(View.GONE);

        notifyDataSetChanged();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mTitle;
        public final TextView mNextSession;
        public Adventure mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mTitle = (TextView) view.findViewById(R.id.title);
            mNextSession = (TextView) view.findViewById(R.id.next_session);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mNextSession.getText() + "'";
        }
    }
}
