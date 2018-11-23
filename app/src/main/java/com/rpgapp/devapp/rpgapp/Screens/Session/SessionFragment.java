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

import com.rpgapp.devapp.rpgapp.DataAccessManager.SessionRequests.SessionRequestManager;
import com.rpgapp.devapp.rpgapp.MainActivity;
import com.rpgapp.devapp.rpgapp.Model.Adventure;
import com.rpgapp.devapp.rpgapp.Model.Roll;
import com.rpgapp.devapp.rpgapp.Model.Session;
import com.rpgapp.devapp.rpgapp.Model.User;
import com.rpgapp.devapp.rpgapp.R;

public class SessionFragment extends Fragment implements SessionRequestManager.ObservesSession {
    private static final String ADVENTURE = "adventure";

    private Adventure mAdventure;
    private EditText mNumberOfDice;
    private EditText mDiceFaces;
    private EditText mBonus;
    private RecyclerView mRollsRecyclerView;
    private RollsAdapter mRollsAdapter;
    private View mBtnRoll;
    private User mUser;
    private Session mSession;


    public SessionFragment() {
        // Required empty public constructor
    }


    public static SessionFragment newInstance() {
        SessionFragment fragment = new SessionFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
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
        mRollsAdapter = new RollsAdapter(null,  mUser.getId());
        mRollsRecyclerView.setAdapter(mRollsAdapter);
        mBtnRoll = view.findViewById(R.id.roll_btn);
        SessionRequestManager.watchSession(this);
        mBtnRoll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int numberOfDices = Integer.parseInt(mNumberOfDice.getText().toString().trim());
                int diceFaces = Integer.parseInt(mDiceFaces.getText().toString().trim());
                int bonus = Integer.parseInt(mBonus.getText().toString().trim());
                Roll rl = new Roll(numberOfDices,diceFaces,bonus, mUser.getId());
                if(mSession!=null) {
                    mSession.addRoll(rl);
                    SessionRequestManager.updateSession(mSession);
                }

            }
        });
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
        if(mSession.getRolls()!= null) {
            mRollsRecyclerView.smoothScrollToPosition(mSession.getRolls().size()-1);
        }

    }
}
