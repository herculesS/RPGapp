package com.rpgapp.devapp.rpgapp.Screens.Notifications;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rpgapp.devapp.rpgapp.Model.Notification;
import com.rpgapp.devapp.rpgapp.R;

import java.util.ArrayList;


public class Notifications extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String NOTIFICATIONS = "NOTIFICATIONS";

    // TODO: Rename and change types of parameters
    private ArrayList<Notification> mNotificationList;
    private RecyclerView mNotificationRecyclerView;


    public Notifications() {
        // Required empty public constructor
    }


    public static Notifications newInstance(ArrayList<Notification> notifications) {
        Notifications fragment = new Notifications();
        Bundle args = new Bundle();
        args.putSerializable(NOTIFICATIONS, notifications);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mNotificationList = (ArrayList<Notification>) getArguments().getSerializable(NOTIFICATIONS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notifications, container, false);
        mNotificationRecyclerView = view.findViewById(R.id.notification_list);
        mNotificationRecyclerView.setLayoutManager(new LinearLayoutManager(inflater.getContext()));
        mNotificationRecyclerView.setAdapter(new NotificationsListAdapter(mNotificationList, getFragmentManager()));
        return view;
    }

}
