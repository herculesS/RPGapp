package com.rpgapp.devapp.rpgapp.Screens.Notifications;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rpgapp.devapp.rpgapp.DataAccessManager.AdventureRequests.AdventureRequestManager;
import com.rpgapp.devapp.rpgapp.Model.Adventure;
import com.rpgapp.devapp.rpgapp.Model.Notification;
import com.rpgapp.devapp.rpgapp.R;
import com.rpgapp.devapp.rpgapp.Screens.AddCharacter.AddCharacter;
import com.rpgapp.devapp.rpgapp.Screens.Adventures.AdventuresFragment;

import java.util.ArrayList;
import java.util.HashMap;

public class NotificationsListAdapter extends RecyclerView.Adapter<NotificationsListAdapter.ViewHolder> implements AdventureRequestManager.OnAdventureLoaded {
    private ArrayList<Notification> mNotificationsList;
    private HashMap<Integer, Adventure> mAdventures = new HashMap<>();
    private FragmentManager mFragmentManager;

    public NotificationsListAdapter(ArrayList<Notification> notificationsList, FragmentManager fragmentManager) {
        mNotificationsList = notificationsList;
        mFragmentManager = fragmentManager;

        if (mNotificationsList != null && !mNotificationsList.isEmpty()) {
            for (Notification nt : mNotificationsList) {
                AdventureRequestManager.loadAdventure(nt.getAdventureId(), this);
            }
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.notification_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        if (mAdventures.containsKey(position)) {
            holder.mAdventureTitleTextView.setText(mAdventures.get(position).getTitle());
            holder.mNotificationCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Fragment fragment = AddCharacter.newInstance(mAdventures.get(position));
                    mFragmentManager.
                            beginTransaction().
                            replace(R.id.container, fragment).
                            commit();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mAdventures != null ? mAdventures.size() : 0;
    }

    @Override
    public void onAdventureLoaded(Adventure a) {
        for (Notification nt: mNotificationsList) {
            if(a.getId().equals(nt.getAdventureId()))  {
                mAdventures.put(mNotificationsList.indexOf(nt),a);
            }
        }
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final View mNotificationCard;
        public TextView mNotificationActionTextView;
        public TextView mAdventureTitleTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            mNotificationActionTextView = itemView.findViewById(R.id.notification_action);
            mAdventureTitleTextView = itemView.findViewById(R.id.adventure_name);
            mNotificationCard = itemView;
        }
    }
}
