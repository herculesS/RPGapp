package com.rpgapp.devapp.rpgapp.Screens.Session;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.rpgapp.devapp.rpgapp.DataAccessManager.AdventureRequests.AdventureRequestManager;
import com.rpgapp.devapp.rpgapp.DataAccessManager.SessionRequests.SessionManager;
import com.rpgapp.devapp.rpgapp.Model.Adventure;
import com.rpgapp.devapp.rpgapp.Model.Roll;
import com.rpgapp.devapp.rpgapp.Model.Session;
import com.rpgapp.devapp.rpgapp.Model.User;
import com.rpgapp.devapp.rpgapp.R;

import java.util.ArrayList;

public class RollsAdapter extends RecyclerView.Adapter<RollsAdapter.ViewHolder> implements SessionManager.ObservesSession, AdventureRequestManager.ObservesAdventure {
    private ArrayList<Roll> mRolls;
    private String mUserId;
    private Adventure mAdventure;
    private int mPosition;
    private final int SELF = 0;
    private final int OTHER = 1;

    public RollsAdapter(ArrayList<Roll> rolls, String userId, Adventure adventure, int position) {
        mRolls = rolls;
        mUserId = userId;
        mAdventure = adventure;
        mPosition = position;
        SessionManager.getInstance().watchSession(this,mAdventure,mPosition);
        AdventureRequestManager.watchAdventure(mAdventure.getId(), RollsAdapter.this);
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
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        Roll rl = mRolls.get(position);
        String formulaText = rl.getNumberOfDice()
                + " X d" + rl.getDiceFacesNumber()
                + " + " + rl.getBonus();
        holder.mRolledNumber.setText(Integer.toString(rl.getNumberRolled()));
        holder.mFormula.setText(formulaText);

        if (holder.mUserName != null) {
            CollectionReference usersCollection = FirebaseFirestore.getInstance().collection("Users");
            usersCollection
                    .document(mRolls.get(position).getUserId())
                    .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if(task.isSuccessful()) {
                        User user = task.getResult().toObject(User.class);
                        holder.mUserName.setText(user.getName());
                    }
                }
            });
        }

        if(rl.isWasCriticalSuccess()) {
            holder.mRolledNumber.setTextColor(Color.GREEN);
        } else if (rl.isWasCriticalFailure()) {
            holder.mRolledNumber.setTextColor(Color.RED);
        } else {
            holder.mRolledNumber.setTextColor(Color.BLACK);
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

    @Override
    public void OnSessionNotFound() {

    }

    @Override
    public void onChangeInAdventure(Adventure ad) {
        mAdventure=ad;
        mRolls = mAdventure.getSessions().get(mPosition).getRolls();
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mUserName;
        public TextView mRolledNumber;
        public TextView mFormula;

        public ViewHolder(View itemView) {
            super(itemView);
            mRolledNumber = itemView.findViewById(R.id.rolled_number);
            mFormula = itemView.findViewById(R.id.formula);
            mUserName = itemView.findViewById(R.id.user_name);
        }
    }
}
