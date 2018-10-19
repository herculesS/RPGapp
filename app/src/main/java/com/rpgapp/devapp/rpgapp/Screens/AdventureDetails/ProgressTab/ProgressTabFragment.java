package com.rpgapp.devapp.rpgapp.Screens.AdventureDetails.ProgressTab;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rpgapp.devapp.rpgapp.Model.Adventure;
import com.rpgapp.devapp.rpgapp.R;

public class ProgressTabFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ADVENTURE = "param1";


    private Adventure mAdventure;
    private TextView mSummaryTextView;


    public ProgressTabFragment() {
        // Required empty public constructor
    }

    public static ProgressTabFragment newInstance(Adventure ad) {
        ProgressTabFragment fragment = new ProgressTabFragment();
        Bundle args = new Bundle();
        args.putSerializable(ADVENTURE, ad);
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
        View view = inflater.inflate(R.layout.fragment_progress_tab, container, false);

        mSummaryTextView = view.findViewById(R.id.adventure_progress_adventure_summary);
        mSummaryTextView.setText(mAdventure.getDescription());


        return view;
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


}
