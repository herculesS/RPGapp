package com.rpgapp.devapp.rpgapp.Screens.AddPlayer;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.rpgapp.devapp.rpgapp.DataAccessManager.UserRequests.UserRequestManager;
import com.rpgapp.devapp.rpgapp.Model.Adventure;
import com.rpgapp.devapp.rpgapp.Model.User;
import com.rpgapp.devapp.rpgapp.R;

import java.util.ArrayList;

public class AddPlayerFragment extends Fragment implements UserRequestManager.OnUserSearchComplete {
    private static final String ADVENTURE = "param1";

    private Adventure mAdventure;
    private EditText mNameToSearch;
    private ImageView mSearchBTN;
    private RecyclerView mPlayerList;
    private PlayersToAddAdapter mPlayersToAddAdapter;


    public AddPlayerFragment() {
        // Required empty public constructor
    }


    public static AddPlayerFragment newInstance(Adventure adventure) {
        AddPlayerFragment fragment = new AddPlayerFragment();
        Bundle args = new Bundle();
        args.putSerializable(ADVENTURE, adventure);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mAdventure = (Adventure) getArguments().getSerializable(ADVENTURE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_player, container, false);
        mNameToSearch = view.findViewById(R.id.name_to_search);
        mSearchBTN = view.findViewById(R.id.search_icon);
        mPlayerList = view.findViewById(R.id.player_list);
        mPlayerList.setLayoutManager(new LinearLayoutManager(inflater.getContext()));
        mPlayersToAddAdapter = new PlayersToAddAdapter(null,mAdventure);
        mPlayerList.setAdapter(mPlayersToAddAdapter);

        mSearchBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = mNameToSearch.getText().toString().trim();

                    UserRequestManager.searchUserByName(name, AddPlayerFragment.this);

            }
        });
        
        return view;
    }

    @Override
    public void onUserSearchComplete(ArrayList<User> user) {
        Log.d("logsave", "here " + user.size());
        mPlayersToAddAdapter.updateList(user);
    }
}
