package com.rpgapp.devapp.rpgapp.Screens.Edit.EditIntent;

import android.widget.EditText;
import android.widget.TextView;

import com.rpgapp.devapp.rpgapp.DataAccessManager.AdventureRequests.AdventureRequestManager.OnSaveAdventure;
import com.rpgapp.devapp.rpgapp.Model.Adventure;

import java.io.Serializable;


public abstract class EditFieldIntent implements Serializable {
    protected Adventure mAdventure;

     public abstract void setIntent(EditText fieldView, TextView fieldTypeLabel, TextView fieldLabel);

     public abstract void resolveIntent(OnSaveAdventure callback, String newFieldValue);

    protected void setLabels(int type_label, int field_label,
                             TextView fieldTypeLabel,TextView fieldLabel) {
        fieldTypeLabel.setText(type_label);
        fieldLabel.setText(field_label);
    }

    public Adventure getAdventure() {
        return mAdventure;
    }

    public void setAdventure(Adventure adventure) {
        mAdventure = adventure;
    }

}
