package com.rpgapp.devapp.rpgapp.Screens.AddSession;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;

import com.rpgapp.devapp.rpgapp.DataAccessManager.AdventureRequestManager;
import com.rpgapp.devapp.rpgapp.Model.Adventure;
import com.rpgapp.devapp.rpgapp.Model.Session;
import com.rpgapp.devapp.rpgapp.R;
import com.rpgapp.devapp.rpgapp.Screens.AdventureDetails.AdventureDetailsFragment;
import com.rpgapp.devapp.rpgapp.Utils.BackableFragment;
import com.rpgapp.devapp.rpgapp.Utils.Utils;

import java.util.Calendar;


public class AddSessionFragment extends Fragment implements AdventureRequestManager.onSaveAdventure, BackableFragment{
    private static final String ADVENTURE = "param1";

    // TODO: Rename and change types of parameters
    private Adventure mAdventure;
    private ImageView mReadyBtn;
    private Calendar mDate = Calendar.getInstance();

    private Button mDatePickerBtn;
    private DatePickerDialog mDatePickerDialog;
    private EditText mSessionTitleEditText;

    public AddSessionFragment() {
        // Required empty public constructor
    }

    public static AddSessionFragment newInstance(Adventure ad) {
        AddSessionFragment fragment = new AddSessionFragment();
        Bundle args = new Bundle();
        args.putSerializable(ADVENTURE, ad);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mAdventure = (Adventure) getArguments().getSerializable(ADVENTURE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_session, container, false);
        mSessionTitleEditText = view.findViewById(R.id.create_session_editText);
        mReadyBtn = view.findViewById(R.id.criar_session_botao_pronto);
        mDatePickerBtn = view.findViewById(R.id.session_date_picker);

        mDatePickerBtn.setText(Utils.
                formatSessionDateToDayMonth(mDate.get(Calendar.DAY_OF_MONTH) + "/" + mDate.get(Calendar.MONTH)));

        mDatePickerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar newCalendar = mDate;
                if (mDatePickerDialog == null) {
                    mDatePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                            mDate.set(year, month, day);
                            mDatePickerBtn.setText(Utils.
                                    formatSessionDateToDayMonth(mDate.get(Calendar.DAY_OF_MONTH) + "/" + mDate.get(Calendar.MONTH)));
                        }
                    }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
                }
                mDatePickerDialog.show();
            }
        });

        mReadyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = mSessionTitleEditText.getText().toString().trim();
                String date = mDate.get(Calendar.DAY_OF_MONTH) + "/" +
                        mDate.get(Calendar.MONTH) + "/" + mDate.get(Calendar.YEAR);
                Session session = new Session();
                if (!title.equals("")) {
                    session.setTitle(title);
                    session.setDate(date);
                } else {
                    session.setTitle("No Title");
                    session.setDate(date);
                }

                mAdventure.addSession(session);

                AdventureRequestManager.saveAdventure(mAdventure, AddSessionFragment.this);

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
    public void onSaved() {
        AdventureDetailsFragment fragment = AdventureDetailsFragment.newInstance(mAdventure);
        getFragmentManager().
                beginTransaction().
                replace(R.id.container, fragment).
                commit();
    }

    @Override
    public void onBack() {
        AdventureDetailsFragment fragment = AdventureDetailsFragment.newInstance(mAdventure);
        getFragmentManager().
                beginTransaction().
                replace(R.id.container, fragment).
                commit();
    }
}
