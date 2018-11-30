package com.rpgapp.devapp.rpgapp.Screens.Session.Attacks;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rpgapp.devapp.rpgapp.DataAccessManager.AdventureRequests.AdventureRequestManager;
import com.rpgapp.devapp.rpgapp.Model.Adventure;
import com.rpgapp.devapp.rpgapp.Model.Attack;
import com.rpgapp.devapp.rpgapp.Model.Roll;
import com.rpgapp.devapp.rpgapp.R;

public class AttacksAdapter extends RecyclerView.Adapter<AttacksAdapter.ViewHolder> implements AdventureRequestManager.OnSaveAdventure, AdventureRequestManager.ObservesAdventure {

    private Adventure mAdventure;
    private int mCharPosition;
    private int mSessionPosition;

    public AttacksAdapter(Adventure adventure, int charPosition, int sessionPosition) {
        mAdventure = adventure;
        mCharPosition = charPosition;
        mSessionPosition = sessionPosition;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.attack_item, parent, false);
        AdventureRequestManager.watchAdventure(mAdventure.getId(), AttacksAdapter.this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Attack at = mAdventure.getCharacters().get(mCharPosition).getAttacks().get(position);
        holder.mAttackName.setText(at.getName());
        holder.mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Roll rl = new Roll(at);
                mAdventure.getSessions().get(mSessionPosition).addRoll(rl);
                AdventureRequestManager.saveAdventure(mAdventure, AttacksAdapter.this);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(mAdventure == null) {
            return 0;
        }
        if(mAdventure.getCharacters() == null) {
            return 0;
        }

        if(mAdventure.getCharacters().get(mCharPosition).getAttacks() == null) {
            return 0;
        }

        return mAdventure.getCharacters().get(mCharPosition).getAttacks().size();
    }

    @Override
    public void onSaved() {

    }

    @Override
    public void onChangeInAdventure(Adventure ad) {
        mAdventure = ad;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public View mButton;
        public TextView mAttackName;

        public ViewHolder(View itemView) {
            super(itemView);

            mAttackName = itemView.findViewById(R.id.attack_name);
            mButton = itemView.findViewById(R.id.button);
        }
    }

}
