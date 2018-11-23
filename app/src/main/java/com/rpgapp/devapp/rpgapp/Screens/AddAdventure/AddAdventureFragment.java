package com.rpgapp.devapp.rpgapp.Screens.AddAdventure;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.rpgapp.devapp.rpgapp.DataAccessManager.AdventureRequests.AdventureRequestManager;
import com.rpgapp.devapp.rpgapp.MainActivity;
import com.rpgapp.devapp.rpgapp.Model.Adventure;
import com.rpgapp.devapp.rpgapp.Model.DungeonMaster;
import com.rpgapp.devapp.rpgapp.Model.User;
import com.rpgapp.devapp.rpgapp.R;
import com.rpgapp.devapp.rpgapp.Screens.Adventures.AdventuresFragment;
import com.rpgapp.devapp.rpgapp.Utils.BackableFragment;


public class AddAdventureFragment extends Fragment implements AdventureRequestManager.OnAdventureAdded, BackableFragment {


    private OnFragmentInteractionListener mListener;
    private ImageView mReadyBtn;
    private ImageView mCloseBtn;
    private EditText mAdventureTitleEditText;
    private FragmentManager mManager;

    public AddAdventureFragment() {
        // Required empty public constructor
    }

    public static AddAdventureFragment newInstance() {
        AddAdventureFragment fragment = new AddAdventureFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_adventure, container, false);
        mAdventureTitleEditText = view.findViewById(R.id.adventure_name);
        mCloseBtn = view.findViewById(R.id.close_btn);
        mReadyBtn = view.findViewById(R.id.ready_btn);

        mReadyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity ac = (MainActivity) getActivity();
                if (ac != null) {
                    User ur = ac.getCurrentUser();
                    Adventure adventure = new Adventure();

                    adventure.setTitle(mAdventureTitleEditText.getText().toString());
                    adventure.setDescription("Default description");
                    DungeonMaster dm = new DungeonMaster();
                    dm.setUserId(ur.getId());
                    adventure.setDM(dm);
                    AdventureRequestManager.addAdventure(adventure, AddAdventureFragment.this);

                }
            }
        });
        return view;
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
    public void onAdded() {
        mManager = getFragmentManager();
        mManager.beginTransaction().
                replace(R.id.container, AdventuresFragment.newInstance()).
                commit();
    }

    @Override
    public void onBack() {
        mManager = getFragmentManager();
        mManager.beginTransaction().
                replace(R.id.container, AdventuresFragment.newInstance()).
                commit();
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
