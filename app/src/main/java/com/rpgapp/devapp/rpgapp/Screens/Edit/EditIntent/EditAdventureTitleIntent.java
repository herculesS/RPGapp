package com.rpgapp.devapp.rpgapp.Screens.Edit.EditIntent;

import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.rpgapp.devapp.rpgapp.DataAccessManager.AdventureRequests.AdventureRequestManager;
import com.rpgapp.devapp.rpgapp.Model.Adventure;
import com.rpgapp.devapp.rpgapp.R;

import java.io.Serializable;

public class EditAdventureTitleIntent extends EditFieldIntent implements Serializable {

    public EditAdventureTitleIntent(Adventure ad) {
        mAdventure = ad;

    }

    @Override
    public void setIntent(EditText fv, TextView ftl, TextView fl) {

        fv.setText(mAdventure.getTitle());
        setLabels(R.string.edit_adventure_label, R.string.edit_adventure_title_label,ftl,fl);

    }

    @Override
    public void resolveIntent(AdventureRequestManager.OnSaveAdventure callback, String newFieldValue) {
        mAdventure.setTitle(newFieldValue);
        AdventureRequestManager.saveAdventure(mAdventure, callback);
    }


}
