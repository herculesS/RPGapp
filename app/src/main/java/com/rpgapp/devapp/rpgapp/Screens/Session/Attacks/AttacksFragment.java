package com.rpgapp.devapp.rpgapp.Screens.Session.Attacks;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rpgapp.devapp.rpgapp.Model.Adventure;
import com.rpgapp.devapp.rpgapp.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AttacksFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AttacksFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ADVENTURE = "param1";
    private static final String CHAR_POSITION = "param2";
    private static final String SESSION_POSITION = "param3";


    private Adventure mAdventure;
    private int mCharPosition;
    private int mSessionPosition;
    private RecyclerView mAttacksListView;
    private AttacksAdapter mAttackAdapter;


    public AttacksFragment() {
        // Required empty public constructor
    }

    public static AttacksFragment newInstance(Adventure adventure, int char_position, int sessionPosition) {
        AttacksFragment fragment = new AttacksFragment();
        Bundle args = new Bundle();
        args.putSerializable(ADVENTURE, adventure);
        args.putInt(CHAR_POSITION, char_position);
        args.putInt(SESSION_POSITION, sessionPosition);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mAdventure = (Adventure) getArguments().getSerializable(ADVENTURE);
            mCharPosition = getArguments().getInt(CHAR_POSITION);
            mSessionPosition = getArguments().getInt(SESSION_POSITION);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_attacks, container, false);
        mAttacksListView = view.findViewById(R.id.attacks);
        mAttacksListView.setLayoutManager(new LinearLayoutManager(container.getContext()));
        mAttackAdapter = new AttacksAdapter(mAdventure, mCharPosition, mSessionPosition);
        mAttacksListView.setAdapter(mAttackAdapter);
        return view;
    }

}
