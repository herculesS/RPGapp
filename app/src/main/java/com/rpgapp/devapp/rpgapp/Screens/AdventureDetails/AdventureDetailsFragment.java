package com.rpgapp.devapp.rpgapp.Screens.AdventureDetails;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.rpgapp.devapp.rpgapp.Model.Adventure;
import com.rpgapp.devapp.rpgapp.R;
import com.rpgapp.devapp.rpgapp.Screens.AddSession.AddSessionFragment;
import com.rpgapp.devapp.rpgapp.Screens.AdventureDetails.ProgressTab.ProgressTabFragment;
import com.rpgapp.devapp.rpgapp.Screens.Adventures.AdventuresFragment;
import com.rpgapp.devapp.rpgapp.Screens.Edit.EditFragment;
import com.rpgapp.devapp.rpgapp.Screens.Edit.EditIntent.EditAdventureTitleIntent;
import com.rpgapp.devapp.rpgapp.Utils.BackableFragment;

/**
 * Fragment that displays the information of a adventure
 * @author hercules
 */
public class AdventureDetailsFragment extends Fragment implements BackableFragment {
    /**
     * Key to save/retrieve adventure information
     */
    private static final String ADVENTURE_ID = "adventure_id";
    /**
     * Adventure to be displayed
     */
    private Adventure mAdventure;
    /**
     * Listener of the fragement/activity that contains this fragment for interactions
     */
    private OnFragmentInteractionListener mListener;

    private TextView mAdventureTitleView;
    private ImageView mAddSessionBTN;

    public AdventureDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Create new instance of the fragment for the adventure passed.
     * @param adventure the adventure to be displayed
     * @return fragment with the detailed information of the adventure
     */
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
            mAdventure = (Adventure) getArguments().getSerializable(ADVENTURE_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_adventure_details, container, false);

        setViewElements(view);
        setFragments();

        return view;
    }

    private void setFragments() {
        ProgressTabFragment fragment = ProgressTabFragment.newInstance(mAdventure);
        getFragmentManager().beginTransaction().replace(R.id.adventure_players_container, fragment).commit();
    }

    private void setViewElements(View view) {
        mAdventureTitleView = view.findViewById(R.id.adventure_progress_adventure_title);
        mAddSessionBTN = view.findViewById(R.id.adventure_progress_btn_add_session);

        mAdventureTitleView.setText(mAdventure.getTitle());

        mAdventureTitleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditFragment fragment = EditFragment.newInstance(new EditAdventureTitleIntent(mAdventure));
                getFragmentManager().
                        beginTransaction().
                        replace(R.id.container, fragment).
                        commit();
            }
        });


        mAddSessionBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddSessionFragment fragment = AddSessionFragment.newInstance(mAdventure);
                getFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
            }
        });
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
                replace(R.id.container, new AdventuresFragment()).
                commit();
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
