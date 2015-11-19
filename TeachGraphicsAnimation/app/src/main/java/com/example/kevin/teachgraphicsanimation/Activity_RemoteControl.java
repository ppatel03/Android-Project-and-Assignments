package com.example.kevin.teachgraphicsanimation;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;


public class Activity_RemoteControl extends ActionBarActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);

        getFragmentManager().beginTransaction()
                .replace(R.id.container, RemoteControlFragment.newInstance(0))
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.remotecontrol, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_plain) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.container, RemoteControlFragment.newInstance(0))
                    .commit();
            return true;
        }
        if (id == R.id.action_fancy) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.container, RemoteControlFragment.newInstance(1))
                    .commit();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public static class RemoteControlFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static RemoteControlFragment newInstance(int sectionNumber) {
            RemoteControlFragment fragment = new RemoteControlFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public RemoteControlFragment() {
        }

        private TextView mSelectedTextView;
        private TextView mWorkingTextView;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            final View rootView;
            int choice = getArguments().getInt(ARG_SECTION_NUMBER);

            if (choice ==0)
                rootView = inflater.inflate(R.layout.fragment_remote_control_plain, container, false);
            else
                rootView = inflater.inflate(R.layout.fragment_remote_control, container, false);

            mSelectedTextView = (TextView) rootView.findViewById(R.id.fragment_remote_control_selectedTextView);
            mWorkingTextView = (TextView) rootView.findViewById(R.id.fragment_remote_control_workingTextView);

            View.OnClickListener numberButtonListener = new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TextView textView = (TextView) view;
                    String working = mWorkingTextView.getText().toString();
                    String text = textView.getText().toString();
                    if (working.equals("0")) {
                        mWorkingTextView.setText(text);
                    } else {
                        mWorkingTextView.setText(working + text);
                    }
                }
            };

            TableLayout tableLayout = (TableLayout) rootView.findViewById(R.id.fragment_remote_control_tableLayout);

            int number =1;
            for (int i=2; i < tableLayout.getChildCount() - 1; i++) {
                TableRow row = (TableRow)tableLayout.getChildAt(i);
                for (int j=0; j < row.getChildCount(); j++){
                    Button button = (Button)row.getChildAt(j);
                    button.setText(""+number);
                    button.setOnClickListener(numberButtonListener);
                    number ++;
                }
            }

            TableRow bottomRow = (TableRow) tableLayout.getChildAt(tableLayout.getChildCount() -1);
            Button deleteButton = (Button)bottomRow.getChildAt(0);
            deleteButton.setText("Delete");
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mWorkingTextView.setText("0");
                }
            });

            Button zeroButton = (Button) bottomRow.getChildAt(1);
            zeroButton.setText("0");
            zeroButton.setOnClickListener(numberButtonListener);

            Button enterButton = (Button) bottomRow.getChildAt(2);
            enterButton.setText("Enter");
            enterButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                  CharSequence working = mWorkingTextView.getText();
                    if (working.length() > 0)
                        mSelectedTextView.setText(working);
                    mWorkingTextView.setText("0");
                }
            });

            return rootView;
        }
    }

}
