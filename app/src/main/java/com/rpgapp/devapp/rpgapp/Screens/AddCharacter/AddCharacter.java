package com.rpgapp.devapp.rpgapp.Screens.AddCharacter;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.rpgapp.devapp.rpgapp.DataAccessManager.AdventureRequests.AdventureRequestManager;
import com.rpgapp.devapp.rpgapp.DataAccessManager.UserRequests.UserRequestManager;
import com.rpgapp.devapp.rpgapp.MainActivity;
import com.rpgapp.devapp.rpgapp.Model.Adventure;
import com.rpgapp.devapp.rpgapp.Model.Character;
import com.rpgapp.devapp.rpgapp.Model.Notification;
import com.rpgapp.devapp.rpgapp.Model.User;
import com.rpgapp.devapp.rpgapp.R;
import com.rpgapp.devapp.rpgapp.Screens.AdventureDetails.AdventureDetailsFragment;
import com.rpgapp.devapp.rpgapp.Screens.Adventures.AdventuresFragment;
import com.rpgapp.devapp.rpgapp.Screens.Notifications.Notifications;
import com.rpgapp.devapp.rpgapp.Utils.BackableFragment;



public class AddCharacter extends Fragment implements AdventureRequestManager.OnSaveAdventure, BackableFragment, UserRequestManager.OnSaveUser {
    private static final String ADVENTURE_ID = "adventure_id";

    private Adventure mAdventure;
    private ImageView mCloseBTN;
    private EditText mCharacterName;
    private EditText mCharacterSummary;
    private ImageView mReadyBTN;
    private User mUser;


    public AddCharacter() {
        // Required empty public constructor
    }

    public static AddCharacter newInstance(Adventure adventure) {
        AddCharacter fragment = new AddCharacter();
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
        View view = inflater.inflate(R.layout.fragment_add_character, container, false);
        mCloseBTN = view.findViewById(R.id.close_btn);
        mCharacterName = view.findViewById(R.id.edit_text_name_character);
        mCharacterSummary = view.findViewById(R.id.edit_text_summary_character);
        mReadyBTN = view.findViewById(R.id.ready_btn);

        FragmentActivity activity = getActivity();
        if(activity != null) {
            mUser = ((MainActivity) activity).getCurrentUser();
        }
        

        mReadyBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Character ch = new Character();
                ch.setName(mCharacterName.getText().toString().trim());
                ch.setSummary(mCharacterSummary.getText().toString().trim());
                ch.setUserId(mUser.getId());
                mAdventure.addCharacter(ch);
                AdventureRequestManager.saveAdventure(mAdventure,AddCharacter.this);
            }
        });
        
        
        return view;
    }


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
    public void onSaved() {
        MainActivity activity = (MainActivity) getActivity();
        User user = activity.getCurrentUser();
        for(Notification nt: user.getNotifications()) {
            if(nt.getAdventureId().equals(mAdventure.getId())){
                user.removeNotification(nt);
                UserRequestManager.saveUser(user, this);
            }
        }

    }

    @Override
    public void onBack() {
        MainActivity main = (MainActivity) getActivity();
        Notifications fragment = Notifications.newInstance(main.getCurrentUser().getNotifications());
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.container,fragment)
                .commit();
    }

    @Override
    public void onUserSaved() {
        AdventureDetailsFragment fragment = AdventureDetailsFragment.newInstance(mAdventure, AdventureDetailsFragment.PLAYER_FRAG);
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.container,fragment)
                .commit();
    }
}
