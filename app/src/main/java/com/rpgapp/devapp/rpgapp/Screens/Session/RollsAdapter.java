package com.rpgapp.devapp.rpgapp.Screens.Session;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rpgapp.devapp.rpgapp.DataAccessManager.SessionRequests.SessionRequestManager;
import com.rpgapp.devapp.rpgapp.Model.Roll;
import com.rpgapp.devapp.rpgapp.Model.Session;
import com.rpgapp.devapp.rpgapp.R;

import java.util.ArrayList;

public class RollsAdapter extends RecyclerView.Adapter<RollsAdapter.ViewHolder> implements SessionRequestManager.ObservesSession {
    private ArrayList<Roll> mRolls;
    private String mUserId;
    private final int SELF = 0;
    private final int OTHER = 1;

    public RollsAdapter(ArrayList<Roll> rolls, String userId) {
        mRolls = rolls;
        mUserId = userId;
        SessionRequestManager.watchSession(this);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == SELF) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.session_list_item_self, parent, false);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.session_list_item_common, parent, false);
        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Roll rl = mRolls.get(position);
        String formulaText = rl.getNumberOfDice()
                + " X d" + rl.getDiceFacesNumber()
                + " + " + rl.getBonus();
        holder.mRolledNumber.setText(Integer.toString(rl.getNumberRolled()));
        holder.mFormula.setText(formulaText);

        if(rl.isWasCriticalSuccess()) {
            holder.mRolledNumber.setTextColor(Color.GREEN);
        }
        if (rl.isWasCriticalFailure()) {
            holder.mRolledNumber.setTextColor(Color.RED);
        }
    }


    @Override
    public int getItemCount() {
        return mRolls == null ? 0 : mRolls.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (mRolls.get(position).getUserId().equals(mUserId)) {
            return SELF;
        } else {
            return OTHER;
        }
    }

    @Override
    public void OnChangeInSession(Session se) {
        mRolls = se.getRolls();
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mRolledNumber;
        public TextView mFormula;

        public ViewHolder(View itemView) {
            super(itemView);
            mRolledNumber = itemView.findViewById(R.id.rolled_number);
            mFormula = itemView.findViewById(R.id.formula);
        }
    }
}
