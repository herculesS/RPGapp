package com.rpgapp.devapp.rpgapp.Screens.AdventureDetails.PlayersTab;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rpgapp.devapp.rpgapp.Model.Adventure;
import com.rpgapp.devapp.rpgapp.R;
import com.rpgapp.devapp.rpgapp.Utils.BackableFragment;

public class PlayersTabFragment extends Fragment implements BackableFragment {
    private static final String ADVENTURE_ID = "adventure_id";

    private Adventure mAdventure;

    private RecyclerView mRecyclerView;

    public PlayersTabFragment() {
        // Required empty public constructor
    }

    public static PlayersTabFragment newInstance(Adventure adventure) {
        PlayersTabFragment fragment = new PlayersTabFragment();
        Bundle args = new Bundle();
        args.putSerializable(ADVENTURE_ID, adventure);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mAdventure = (Adventure) getArguments().getSerializable(ADVENTURE_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_players_tab, container, false);
        bindControllers(view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mRecyclerView.getContext()));
        mRecyclerView.setAdapter(new CharactersAdapter(mAdventure.getCharacters()));
        return view;
    }

    private void bindControllers(View view) {
        mRecyclerView = view.findViewById(R.id.recycler_view_characters);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onBack() {

    }


}
