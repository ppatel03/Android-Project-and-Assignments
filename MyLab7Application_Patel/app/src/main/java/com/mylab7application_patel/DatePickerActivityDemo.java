package com.mylab7application_patel;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class DatePickerActivityDemo extends ActionBarActivity {
    private static final String ARGS_OPTION = "argument_option";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_picker_activity_demo);

        Intent intent = getIntent();
        Date date = (Date) intent.getSerializableExtra(DatePickerFragmentDialog.ARGS_DATE);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, PlaceholderFragment.newInstance(date))
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_date_picker_activity_demo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        private Date mDate;
        public static final String ARGS_DATE = "argument_date";
        public static final String ARGS_THOUGHT = "argument_thought";
        public static final String ARGS_NAME = "argument_name";
        private TextView thoughtForTheDayText;
        private TextView nameText;
        private Button cancelButton;
        private Button okButton;

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_date_picker_activity_demo, container, false);
            mDate = (Date) getArguments().getSerializable(DatePickerFragmentDialog.ARGS_DATE);

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(mDate);

            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePicker datePicker = (DatePicker) rootView.findViewById(R.id.datePickerActivity);

            thoughtForTheDayText = (TextView) rootView.findViewById(R.id.thoughtTextActivity);
            nameText = (TextView) rootView.findViewById(R.id.nameTextActivity);
            okButton = (Button) rootView.findViewById(R.id.okActivity);
            cancelButton = (Button) rootView.findViewById(R.id.cancelActivity);


            datePicker.init(year, month, day, new DatePicker.OnDateChangedListener() {
                public void onDateChanged(DatePicker view, int year, int month, int day) {
                    mDate = new GregorianCalendar(year, month, day).getTime();
                    getArguments().putSerializable(ARGS_DATE, mDate);
                }
            });

            okButton.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v){
                    Log.i("Placeholder Frag", "Reached setOnClickListener method of Placeholder Frag");
                    Intent intent = new Intent();
                    intent.putExtra(ARGS_DATE, mDate);
                    intent.putExtra(ARGS_THOUGHT,thoughtForTheDayText.getText().toString());
                    intent.putExtra(ARGS_NAME,nameText.getText().toString());
                    getActivity().setResult(RESULT_OK,intent);
                    getActivity().finish();
                }
            });

            cancelButton.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v){
                    Log.i(" of Placeholder Frag", "Reached setOnClickListener method of Placeholder Frag");
                    /*Intent intent = new Intent();
                    intent.putExtra(ARGS_DATE, mDate);
                    intent.putExtra(ARGS_THOUGHT,thoughtForTheDayText.getText().toString());
                    intent.putExtra(ARGS_NAME,nameText.getText().toString());
                    getActivity().setResult(RESULT_OK,intent);*/
                    getActivity().finish();
                }
            });


            return rootView;
        }

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         * <p/>
         * param ARGS_OPTION option 1.
         *
         * @return A new instance of fragment AboutMeFragment.
         */
        // TODO: Rename and change types and number of parameters
        public static PlaceholderFragment newInstance(Date date) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putSerializable(DatePickerFragmentDialog.ARGS_DATE, date);
            fragment.setArguments(args);
            return fragment;
        }
    }
}
