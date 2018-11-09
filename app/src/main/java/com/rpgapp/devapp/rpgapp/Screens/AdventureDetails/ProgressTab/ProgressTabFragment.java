package com.rpgapp.devapp.rpgapp.Screens.AdventureDetails.ProgressTab;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rpgapp.devapp.rpgapp.Model.Adventure;
import com.rpgapp.devapp.rpgapp.R;
import com.rpgapp.devapp.rpgapp.Screens.Adventures.MyAdventuresAdapter;
import com.rpgapp.devapp.rpgapp.Screens.Edit.EditFragment;
import com.rpgapp.devapp.rpgapp.Screens.Edit.EditIntent.EditAdventureDescriptionIntent;
import com.rpgapp.devapp.rpgapp.Screens.Edit.EditIntent.EditAdventureTitleIntent;

public class ProgressTabFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ADVENTURE = "param1";


    private Adventure mAdventure;
    private TextView mSummaryTextView;
    private RecyclerView mRecyclerView;


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
        mSummaryTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditFragment fragment = EditFragment.newInstance(new EditAdventureDescriptionIntent(mAdventure));
                getFragmentManager().
                        beginTransaction().
                        replace(R.id.container, fragment).
                        commit();
            }
        });
        
        mRecyclerView = view.findViewById(R.id.adventure_progress_sessions_list);

        if (mRecyclerView instanceof RecyclerView) {
            Context context = view.getContext();
            mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
            mRecyclerView.setAdapter(new SessionsAdapter(mAdventure.getSessions()));
        }



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
