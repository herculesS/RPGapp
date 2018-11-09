package com.rpgapp.devapp.rpgapp.Screens.AdventureDetails;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.rpgapp.devapp.rpgapp.R;

public class ProgressTabBtnOnClick implements View.OnClickListener {
    private ImageView mBtn;
    private AppCompatActivity mActivity;
    private FragmentManager mManager;

    public ProgressTabBtnOnClick(ImageView btn, AppCompatActivity activity) {
        mBtn = btn;
        mBtn.setVisibility(View.GONE);
        mActivity = activity;
        mManager = mActivity.getSupportFragmentManager();
        mBtn.setImageResource(R.drawable.btn_add_session);
    }

    @Override
    public void onClick(View v) {

    }
}
