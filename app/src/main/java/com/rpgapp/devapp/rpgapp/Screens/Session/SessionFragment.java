package com.rpgapp.devapp.rpgapp.Screens.Session;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.rpgapp.devapp.rpgapp.DataAccessManager.AdventureRequests.AdventureRequestManager;
import com.rpgapp.devapp.rpgapp.DataAccessManager.SessionRequests.SessionManager;
import com.rpgapp.devapp.rpgapp.MainActivity;
import com.rpgapp.devapp.rpgapp.Model.Adventure;
import com.rpgapp.devapp.rpgapp.Model.Roll;
import com.rpgapp.devapp.rpgapp.Model.Session;
import com.rpgapp.devapp.rpgapp.Model.User;
import com.rpgapp.devapp.rpgapp.R;
import com.rpgapp.devapp.rpgapp.Screens.Session.Attacks.AttacksFragment;

public class SessionFragment extends Fragment implements SessionManager.ObservesSession, AdventureRequestManager.OnSaveAdventure {
    private static final String ADVENTURE = "adventure";
    private static final String POSITION = "position";


    private Adventure mAdventure;
    private EditText mNumberOfDice;
    private EditText mDiceFaces;
    private EditText mBonus;
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
        mNumberOfDice = view.findViewById(R.id.number_of_dice);
        mDiceFaces = view.findViewById(R.id.dice_faces);
        mBonus = view.findViewById(R.id.bonus);
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

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mCombatOptions.getVisibility() == View.GONE) {
                    mCombatOptions.setVisibility(View.VISIBLE);
                } else {
                    mCombatOptions.setVisibility(View.GONE);
                }
            }
        });

        if(mCharacterPosition != -1){
            mAttacksFragment = AttacksFragment.newInstance(mAdventure, mCharacterPosition, mPosition);
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.combat_options,mAttacksFragment)
                    .commit();
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
}
