package com.rpgapp.devapp.rpgapp.Screens.Edit;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.rpgapp.devapp.rpgapp.DataAccessManager.AdventureRequests.AdventureRequestManager;
import com.rpgapp.devapp.rpgapp.Model.Adventure;
import com.rpgapp.devapp.rpgapp.R;
import com.rpgapp.devapp.rpgapp.Screens.AdventureDetails.AdventureDetailsFragment;
import com.rpgapp.devapp.rpgapp.Screens.Edit.EditIntent.EditFieldIntent;
import com.rpgapp.devapp.rpgapp.Utils.BackableFragment;

public class EditFragment extends Fragment implements AdventureRequestManager.OnSaveAdventure , BackableFragment {
    private static final String FIELD_TO_BE_EDITED = "FIELD_TO_BE_EDITED";

    private EditText mFieldView;
    private TextView mFieldTypeLabel;
    private TextView mFieldLabel;

    private ImageView mCloseBTN;
    private ImageView mReadyBTN;




    private Adventure mAdventure;
    private EditFieldIntent mEditFieldIntent;

    public EditFragment() {
        // Required empty public constructor
    }

    public static EditFragment newInstance(EditFieldIntent editFieldIntent) {
        EditFragment fragment = new EditFragment();
        Bundle args = new Bundle();
        args.putSerializable(FIELD_TO_BE_EDITED, editFieldIntent);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mEditFieldIntent = (EditFieldIntent) getArguments().getSerializable(FIELD_TO_BE_EDITED);
            mAdventure = mEditFieldIntent.getAdventure();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_edit, container, false);

        linkViewElements(view);
        setButtonListeners();

        mEditFieldIntent.setIntent(mFieldView,mFieldTypeLabel,mFieldLabel);

        return view;
    }



    /**
     * Create the behavior for button clicks
     */
    private void setButtonListeners() {
        if (mCloseBTN != null) {
            mCloseBTN.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
        if (mReadyBTN!= null) {
            mReadyBTN.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Log.d("issue", "Resolve");
                    mEditFieldIntent.resolveIntent(EditFragment.this, mFieldView.getText().toString().trim());
                }
            });
        }
    }

    /**
     * Link view elements to the controllers
     * @param view fragment content view
     */
    private void linkViewElements(View view) {
        mFieldLabel = view.findViewById(R.id.edit_field);
        mFieldTypeLabel = view.findViewById(R.id.edit_type);
        mFieldView = view.findViewById(R.id.edit_text_field);
        mCloseBTN = view.findViewById(R.id.close_btn);
        mReadyBTN = view.findViewById(R.id.ready_btn);

    }

    // TODO: Rename method, update argument and hook method into UI event
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
        AdventureDetailsFragment fragment = AdventureDetailsFragment.newInstance(mAdventure, AdventureDetailsFragment.PROGRESS_FRAG);
        getFragmentManager().
                beginTransaction().
                replace(R.id.container, fragment).
                commit();
    }

    @Override
    public void onBack() {
        AdventureDetailsFragment fragment = AdventureDetailsFragment.newInstance(mAdventure, AdventureDetailsFragment.PROGRESS_FRAG);
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.container,fragment)
                .commit();
    }
}
