package com.example.kevin.teachgraphicsanimation;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by kevin on 6/2/14.
 */
public class Fragment_CoverPage extends Fragment {
    public static Fragment_CoverPage newInstance(int position) {
        Fragment_CoverPage fragment = new Fragment_CoverPage();
        return fragment;
    }

    public Fragment_CoverPage() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_detail_menu, menu);
        getActivity().getActionBar().setTitle("Demo Page");
        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_coverpage, container, false);

        textView_result = (TextView) rootView.findViewById(R.id.textview_showresult);

        View.OnClickListener onClickListener = new View.OnClickListener(){
            @Override
            public void onClick(View view){
                int id = view.getId();
                Intent intent;

                switch (id){
                    case R.id.button1:
                        intent = new Intent(getActivity(), Activity_RemoteControl.class);
                        startActivity(intent);
                        break;
                    case R.id.button2:
                        intent = new Intent(getActivity(), Activity_Drawable.class);
                        startActivity(intent);
                        break;
                    case R.id.button3:
                        intent = new Intent(getActivity(), Activity_DragandDrop.class);
                        startActivity(intent);
                        break;
                    case R.id.button4:
                        intent = new Intent(getActivity(), Activity_Animation.class);
                        startActivity(intent);
                        break;
                    case R.id.button5:
                        break;
                    default:
                        break;
                }
            }
        };

        ((Button) rootView.findViewById(R.id.button1)).setOnClickListener(onClickListener);
        ((Button) rootView.findViewById(R.id.button2)).setOnClickListener(onClickListener);
        ((Button) rootView.findViewById(R.id.button3)).setOnClickListener(onClickListener);
        ((Button) rootView.findViewById(R.id.button4)).setOnClickListener(onClickListener);
        ((Button) rootView.findViewById(R.id.button5)).setOnClickListener(onClickListener);

        return rootView;

    }

    public void startRemoteControlActivity(){
        //Date date = new Date(System.currentTimeMillis());
        Intent i = new Intent(getActivity(), Activity_RemoteControl.class);
        //i.putExtra(MyDialogFragment_DatePicker.DATE_ARGS, date);
        startActivity(i);
        //getActivity().startActivityForResult(i, REQUEST_DATE);
        //startActivityForResult(i, REQUEST_DATE);
    }

    static final int PICK_CONTACT_REQUEST = 1;  // The request code
    public void startContactActivity(){
        Intent intent = new Intent(Intent.ACTION_PICK, Uri.parse("content://contacts"));
        intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
        startActivityForResult(intent, PICK_CONTACT_REQUEST);
    }

    private static final int REQUEST_DATE = 0;

    private TextView textView_result;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        Log.d("MovieDetailFragment", "onActivityResult");

        if (resultCode != Activity.RESULT_OK) return;
        if (requestCode == REQUEST_DATE) {
            //Date date = (Date) data.getSerializableExtra(MyDialogFragment_DatePicker.DATE_ARGS);
            //textView_result.setText(date.toString());
        }

    }
}

