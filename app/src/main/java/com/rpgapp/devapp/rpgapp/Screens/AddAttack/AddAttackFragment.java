package com.rpgapp.devapp.rpgapp.Screens.AddAttack;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.rpgapp.devapp.rpgapp.DataAccessManager.AdventureRequests.AdventureRequestManager;
import com.rpgapp.devapp.rpgapp.Model.Adventure;
import com.rpgapp.devapp.rpgapp.Model.Attack;
import com.rpgapp.devapp.rpgapp.R;
import com.rpgapp.devapp.rpgapp.Screens.AdventureDetails.AdventureDetailsFragment;
import com.rpgapp.devapp.rpgapp.Screens.Session.SessionFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddAttackFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddAttackFragment extends Fragment implements AdventureRequestManager.OnSaveAdventure {
    private static final String CHAR_POSITION = "param1";
    private static final String ADVENTURE = "param2";


    private Adventure mAdventure;
    private int mCharPosition;
    private EditText mNumberOfDice;
    private EditText mDiceFaces;
    private EditText mBonus;
    private View mBtnRoll;
    private EditText mAttackName;


    public AddAttackFragment() {
        // Required empty public constructor
    }

    public static AddAttackFragment newInstance(Adventure adventure, int char_position) {
        AddAttackFragment fragment = new AddAttackFragment();
        Bundle args = new Bundle();
        args.putInt(CHAR_POSITION, char_position);
        args.putSerializable(ADVENTURE, adventure);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mCharPosition = getArguments().getInt(CHAR_POSITION);
            mAdventure = (Adventure) getArguments().getSerializable(ADVENTURE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_attack, container, false);
        mNumberOfDice = view.findViewById(R.id.number_of_dice);
        mDiceFaces = view.findViewById(R.id.dice_faces);
        mBonus = view.findViewById(R.id.bonus);
        mAttackName = view.findViewById(R.id.attack_name);
        mBtnRoll = view.findViewById(R.id.roll_btn);

        mBtnRoll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userId = mAdventure.getCharacters().get(mCharPosition).getUserId();
                int numberOfDices = Integer.parseInt(mNumberOfDice.getText().toString().trim());
                int diceFaces = Integer.parseInt(mDiceFaces.getText().toString().trim());
                int bonus = Integer.parseInt(mBonus.getText().toString().trim());
                String name = mAttackName.getText().toString().trim();
                Attack attack = new Attack(diceFaces, numberOfDices, bonus, name, userId);
                mAdventure.getCharacters().get(mCharPosition).addAttack(attack);

                AdventureRequestManager.saveAdventure(mAdventure, AddAttackFragment.this);
            }
        });
        return view;
    }

    @Override
    public void onSaved() {
        AdventureDetailsFragment fragment = AdventureDetailsFragment.newInstance(mAdventure, AdventureDetailsFragment.PLAYER_FRAG);
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.container,fragment)
                .commit();
    }
}
