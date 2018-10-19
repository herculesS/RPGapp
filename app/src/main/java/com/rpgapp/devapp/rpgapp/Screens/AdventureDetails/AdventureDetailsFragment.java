package com.rpgapp.devapp.rpgapp.Screens.AdventureDetails;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.rpgapp.devapp.rpgapp.Model.Adventure;
import com.rpgapp.devapp.rpgapp.R;
import com.rpgapp.devapp.rpgapp.Screens.AdventureDetails.ProgressTab.ProgressTabFragment;
import com.rpgapp.devapp.rpgapp.Screens.Adventures.AdventuresFragment;
import com.rpgapp.devapp.rpgapp.Utils.BackableFragment;

public class AdventureDetailsFragment extends Fragment implements BackableFragment {
    private static final String ADVENTURE_ID = "adventure_id";

    // TODO: Rename and change types of parameters
    private Adventure mAdventure;

    private OnFragmentInteractionListener mListener;
    private TextView mAdventureTitleTextView;

    public AdventureDetailsFragment() {
        // Required empty public constructor
    }

    public static AdventureDetailsFragment newInstance(Adventure adventure) {
        AdventureDetailsFragment fragment = new AdventureDetailsFragment();
        Bundle args = new Bundle();
        args.putSerializable(ADVENTURE_ID, adventure);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mAdventure = (Adventure)getArguments().getSerializable(ADVENTURE_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        
       View view = inflater.inflate(R.layout.fragment_adventure_details, container, false);;
        mAdventureTitleTextView = view.findViewById(R.id.adventure_progress_adventure_title);
        mAdventureTitleTextView.setText(mAdventure.getTitle());

        ProgressTabFragment fragment = ProgressTabFragment.newInstance(mAdventure);
        getFragmentManager().beginTransaction().replace(R.id.adventure_players_container,fragment).commit();

        return view;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onBack() {
        getFragmentManager().
                beginTransaction().
                replace(R.id.container,new AdventuresFragment()).
                commit();
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
