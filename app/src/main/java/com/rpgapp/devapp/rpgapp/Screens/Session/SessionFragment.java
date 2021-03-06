package com.rpgapp.devapp.rpgapp.Screens.Session;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rpgapp.devapp.rpgapp.DataAccessManager.AdventureRequests.AdventureRequestManager;
import com.rpgapp.devapp.rpgapp.DataAccessManager.SessionRequests.SessionManager;
import com.rpgapp.devapp.rpgapp.MainActivity;
import com.rpgapp.devapp.rpgapp.Model.Adventure;
import com.rpgapp.devapp.rpgapp.Model.Session;
import com.rpgapp.devapp.rpgapp.Model.User;
import com.rpgapp.devapp.rpgapp.R;
import com.rpgapp.devapp.rpgapp.Screens.AdventureDetails.AdventureDetailsFragment;
import com.rpgapp.devapp.rpgapp.Screens.Session.Attacks.AttacksFragment;
import com.rpgapp.devapp.rpgapp.Utils.BackableFragment;

public class SessionFragment extends Fragment implements SessionManager.ObservesSession, AdventureRequestManager.OnSaveAdventure, BackableFragment, AdventureRequestManager.ObservesAdventure {
    private static final String ADVENTURE = "adventure";
    private static final String POSITION = "position";


    private Adventure mAdventure;
    private RecyclerView mRollsRecyclerView;
    private RollsAdapter mRollsAdapter;
    private View mBtnRoll;
    private User mUser;
    private Session mSession;
    private int mPosition;
    private AttacksFragment mAttacksFragment;
    private int mCharacterPosition;
    private View mCombatOptions;
    private View mAttackButton;
    private boolean mCombatOptionsOpen;
    private View mView;


    public SessionFragment() {
        // Required empty public constructor
    }


    public static SessionFragment newInstance(Adventure adventure, int position) {
        SessionFragment fragment = new SessionFragment();
        Bundle args = new Bundle();
        args.putSerializable(ADVENTURE, adventure);
        args.putInt(POSITION, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mAdventure = (Adventure) getArguments().getSerializable(ADVENTURE);
            mPosition = getArguments().getInt(POSITION);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_session, container, false);
        mView = view;
        AdventureRequestManager.watchAdventure(mAdventure.getId(),SessionFragment.this);
        mRollsRecyclerView = view.findViewById(R.id.roll_list);
        LinearLayoutManager llm = new LinearLayoutManager(container.getContext());
        llm.setReverseLayout(true);
        mRollsRecyclerView.setLayoutManager(llm);
        MainActivity main = (MainActivity) getActivity();
        main.hideActionBtn();
        mUser = main.getCurrentUser();
        mRollsAdapter = new RollsAdapter(null, mUser.getId(), mAdventure, mPosition);
        mRollsRecyclerView.setAdapter(mRollsAdapter);
        mCharacterPosition = mUser.getUserCharacterPosition(mAdventure);
        mCombatOptions = view.findViewById(R.id.combat_options);
        mCombatOptions.setVisibility(View.GONE);
        mAttackButton = view.findViewById(R.id.attack_button);
        mCombatOptionsOpen = false;

        mAttackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("teste", "" + mCharacterPosition);
                if(!mCombatOptionsOpen) {
                    mCombatOptions.setVisibility(View.VISIBLE);
                    mCombatOptionsOpen = true;
                    mRollsRecyclerView.animate().alpha(0.4f);

                } else {
                    mCombatOptions.setVisibility(View.GONE);
                    mCombatOptionsOpen = false;
                    mRollsRecyclerView.animate().alpha(1f);
                }
            }
        });

        if(mCharacterPosition != -1){
            Log.d("teste", "" + mCharacterPosition);
            mAttacksFragment = AttacksFragment.newInstance(mAdventure, mCharacterPosition, mPosition);
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.combat_options,mAttacksFragment)
                    .commit();
        }

        SessionManager.getInstance().watchSession(SessionFragment.this,mAdventure,mPosition);

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


    @Override
    public void OnChangeInSession(Session se) {
        mSession = se;
        if (mSession.getRolls() != null) {
            mRollsRecyclerView.smoothScrollToPosition(mSession.getRolls().size() - 1);
        }

    }

    @Override
    public void OnSessionNotFound() {

    }

    @Override
    public void onSaved() {

    }

    @Override
    public void onBack() {
        AdventureDetailsFragment fragment = AdventureDetailsFragment.newInstance(mAdventure, AdventureDetailsFragment.PROGRESS_FRAG);
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.container,fragment)
                .commit();
    }

    @Override
    public void onChangeInAdventure(Adventure ad) {
        mAdventure = ad;
    }
}
