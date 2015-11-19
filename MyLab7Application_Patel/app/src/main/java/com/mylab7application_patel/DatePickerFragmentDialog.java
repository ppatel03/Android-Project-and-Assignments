package com.mylab7application_patel;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class DatePickerFragmentDialog extends DialogFragment {
    public static final String ARGS_DATE = "argument_date";
    public static final String ARGS_THOUGHT = "argument_thought";
    public static final String ARGS_NAME = "argument_name";
    private TextView thoughtForTheDayText;
    private TextView nameText;

    private Date mDate;


    public DatePickerFragmentDialog() {
        // Required empty public constructor
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        mDate = (Date) getArguments().getSerializable(ARGS_DATE);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(mDate);

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);


        View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_date, null);
        DatePicker datePicker = (DatePicker) v.findViewById(R.id.datePicker);

        thoughtForTheDayText = (TextView) v.findViewById(R.id.thoughtText);
        nameText = (TextView) v.findViewById(R.id.nameText);


        datePicker.init(year, month, day, new DatePicker.OnDateChangedListener() {
            public void onDateChanged(DatePicker view, int year, int month, int day) {
                mDate = new GregorianCalendar(year, month, day).getTime();
                getArguments().putSerializable(ARGS_DATE, mDate);
            }
        });

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setView(v)
                .setTitle("Date Picker")
                .setMessage("Please select a date !")
                .setPositiveButton(R.string.ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                if (getTargetFragment() != null) {
                                    Intent i = new Intent();
                                    i.putExtra(ARGS_DATE, mDate);
                                    i.putExtra(ARGS_THOUGHT,thoughtForTheDayText.getText().toString());
                                    i.putExtra(ARGS_NAME,nameText.getText().toString());
                                    getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, i);
                                } else {
                                    Toast.makeText(getActivity(), "no need to return the results", Toast.LENGTH_LONG).show();
                                }
                            }

                        });

        return alertDialogBuilder.create();
    }

    public static DatePickerFragmentDialog newInstance(Date d) {
        DatePickerFragmentDialog fragment = new DatePickerFragmentDialog();
        Bundle args = new Bundle();
        args.putSerializable(ARGS_DATE, d);
        fragment.setArguments(args);
        return fragment;
    }




}
