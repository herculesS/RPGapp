package com.rpgapp.devapp.rpgapp.Screens.Edit.EditIntent;

import android.widget.EditText;
import android.widget.TextView;

import com.rpgapp.devapp.rpgapp.DataAccessManager.AdventureRequests.AdventureRequestManager;
import com.rpgapp.devapp.rpgapp.Model.Adventure;
import com.rpgapp.devapp.rpgapp.R;

import java.io.Serializable;

public class EditAdventureDescriptionIntent extends EditFieldIntent implements Serializable {

    public EditAdventureDescriptionIntent(Adventure ad) {
        mAdventure = ad;

    }


    @Override
    public void setIntent(EditText fv, TextView ftl, TextView fl) {
        fv.setText(mAdventure.getDescription());
        setLabels(R.string.edit_adventure_label, R.string.edit_adventure_description_label,ftl,fl);
    }

    @Override
    public void resolveIntent(AdventureRequestManager.OnSaveAdventure callback, String newFieldValue) {
        mAdventure.setDescription(newFieldValue);
        AdventureRequestManager.saveAdventure(mAdventure, callback);
    }
}
