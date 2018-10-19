package com.rpgapp.devapp.rpgapp.Screens.Adventures;


import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.rpgapp.devapp.rpgapp.R;
import com.rpgapp.devapp.rpgapp.Screens.AddAdventure.AddAdventureFragment;

public class AdventuresFBonClick implements View.OnClickListener {
    private final FragmentManager mManager;
    private ImageView mFloatingActionButton;
    private AppCompatActivity mActivity;

    public AdventuresFBonClick(ImageView imageButton, AppCompatActivity activity) {
        mActivity = activity;
        mManager = mActivity.getSupportFragmentManager();
        mFloatingActionButton = imageButton;
        mFloatingActionButton.setImageResource(R.drawable.btn_create_adventure);
    }


    @Override
    public void onClick(View v) {
        if(mManager != null) {
            mManager.beginTransaction().
                    replace(R.id.container,AddAdventureFragment.newInstance()).
                    commit();
        }

    }
}
