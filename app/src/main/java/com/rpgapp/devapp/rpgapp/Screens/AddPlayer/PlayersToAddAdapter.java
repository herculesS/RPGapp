package com.rpgapp.devapp.rpgapp.Screens.AddPlayer;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.rpgapp.devapp.rpgapp.DataAccessManager.UserRequests.UserRequestManager;
import com.rpgapp.devapp.rpgapp.Model.Adventure;
import com.rpgapp.devapp.rpgapp.Model.Notification;
import com.rpgapp.devapp.rpgapp.Model.User;
import com.rpgapp.devapp.rpgapp.R;

import java.util.ArrayList;

public class PlayersToAddAdapter extends RecyclerView.Adapter<PlayersToAddAdapter.ViewHolder> implements UserRequestManager.OnSaveUser {
    private ArrayList<User> mUsers;
    private Adventure mAdventure;

    public PlayersToAddAdapter(ArrayList<User> users, Adventure adventure) {
        mUsers = users;
        mAdventure = adventure;
    }

    public void updateList(ArrayList<User> users) {
        mUsers = users;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.add_player_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        User user = mUsers.get(position);
        holder.mPlayerName.setText(user.getName());

        if (user.isNotificationAlreadySent(mAdventure.getId())) {
            holder.mActionBTN.setImageResource(R.drawable.notification_sent);
        } else if (mAdventure.isUserInThisAdventure(user.getId())) {
            holder.mActionBTN.setImageResource(R.drawable.ok_icon);
        } else {
            holder.mActionBTN.setImageResource(R.drawable.add_player_icon);
            holder.mActionBTN.setTag(position);
            holder.mActionBTN.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = (int) v.getTag();

                    Notification nt = new Notification();
                    nt.setAdventureId(mAdventure.getId());
                    nt.setAdventureName(mAdventure.getTitle());
                    mUsers.get(pos).addNotification(nt);
                    UserRequestManager.saveUser(mUsers.get(pos), PlayersToAddAdapter.this);

                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return mUsers != null ? mUsers.size() : 0;
    }

    @Override
    public void onUserSaved() {

        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView mPlayerName;
        public ImageView mActionBTN;

        public ViewHolder(View itemView) {
            super(itemView);
            mPlayerName = itemView.findViewById(R.id.player_name);
            mActionBTN = itemView.findViewById(R.id.action_btn);
        }
    }
}
