package com.mylab7application_patel;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.net.Uri;
import android.provider.ContactsContract;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;



import java.util.Date;


public class DemoFragment extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private  static final String ARGS_OPTION = "argument_option";
    private  static final int REQUEST_DATE = 0;
    private  static final int REQUEST_CONTACT = 1;


    private Button dialogButton;
    private Button activityButton;
    private Button contactButton;
    private TextView textView;
    private TextView thoughtTextView;
    private TextView nameTextView;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * param ARGS_OPTION option 1.
     * @return A new instance of fragment AboutMeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DemoFragment newInstance(int option){
        DemoFragment fragment = new DemoFragment();
        Bundle args = new Bundle();
        args.putInt(ARGS_OPTION, option);
        fragment.setArguments(args);
        return fragment;
    }


    public DemoFragment() {
        // Required empty public constructor
    }

    /**
     * Receive the result from a previous call to
     * {@link #startActivityForResult(android.content.Intent, int)}.  This follows the
     * related Activity API as described there in
     *
     * @param requestCode The integer request code originally supplied to
     *                    startActivityForResult(), allowing you to identify who this
     *                    result came from.
     * @param resultCode  The integer result code returned by the child activity
     *                    through its setResult().
     * @param data        An Intent, which can return result data to the caller
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i("inside Activity Result","inside Activity Result");

        if(resultCode != Activity.RESULT_OK)
            return;

        if(requestCode == REQUEST_DATE){
            Date date = (Date) data.getSerializableExtra(DatePickerFragmentDialog.ARGS_DATE);
            textView.setText(date.toString());
            String thoughtString =(String) data.getSerializableExtra(DatePickerFragmentDialog.ARGS_THOUGHT);
            thoughtTextView.setText(thoughtString);
            String nameString = (String) data.getSerializableExtra(DatePickerFragmentDialog.ARGS_NAME);
            nameTextView.setText(nameString);
        }
        else if (requestCode==REQUEST_CONTACT)
        {
            Uri contacturi=data.getData();
            String [] projection = {
                    ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER
            };
            Cursor cursor = getActivity().getContentResolver().query(contacturi,projection,null,null,null);
            cursor.moveToFirst();

            int column=cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
            String number =cursor.getString(column);
            int column1=cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
            String name =cursor.getString(column1);
            textView.setText("Phone Number : "+number+"\n Name :"+name);

            textView.startAnimation(AnimationUtils.loadAnimation(getActivity(),R.anim.abc_slide_in_bottom));
        }
    }


    public void showDatePickerDialogGetResult(){
        Log.i("inside showDatePickerDialogGetResult of DemoFragment","inside showDatePickerDialogGetResult of DemoFragment");

        Date date = new Date(System.currentTimeMillis());
        DatePickerFragmentDialog datePickerFragmentDialog = DatePickerFragmentDialog.newInstance(date);
        Log.i("After Call to datePicker","After Call to datePicker");

        datePickerFragmentDialog.setTargetFragment(this,REQUEST_DATE);
        Log.i("After Call to setTargetFragment","After Call to setTargetFragment");

        datePickerFragmentDialog.show(getFragmentManager(),"DatePicker Dialog : Get Result");

        Log.i("After Call to show","After Call to show");

    }

    public void showDatePickerActivityGetResult(){
        Log.i("inside showDatePickerActivityGetResult of DemoFragment","inside showDatePickerActivityGetResult of DemoFragment");
        Date date = new Date(System.currentTimeMillis());
        Intent intent = new Intent(getActivity(),DatePickerActivityDemo.class);
        intent.putExtra(DatePickerFragmentDialog.ARGS_DATE,date);
        startActivityForResult(intent,REQUEST_DATE);

    }


    public void showContactView(){
        Intent intent= new Intent(Intent.ACTION_PICK, Uri.parse("content://contacts"));
        intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
        startActivityForResult(intent,REQUEST_CONTACT);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View demoView = inflater.inflate(R.layout.fragment_demo, container,false);

        dialogButton = (Button) demoView.findViewById(R.id.dialogButton);
        activityButton = (Button) demoView.findViewById(R.id.activityButton);
        contactButton = (Button) demoView.findViewById(R.id.contactButton);
        textView = (TextView) demoView.findViewById(R.id.textView);
        thoughtTextView = (TextView) demoView.findViewById(R.id.thoughtTextView);
        nameTextView = (TextView) demoView.findViewById(R.id.nameTextView);

        dialogButton.setOnClickListener( this);
        activityButton.setOnClickListener(this);
        contactButton.setOnClickListener(this);

        return demoView;
    }


    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch(id){
            case R.id.dialogButton : showDatePickerDialogGetResult();
                break;

            case R.id.activityButton :
                showDatePickerActivityGetResult();
                break;

            case R.id.contactButton:
                showContactView();
                break;

            default:
                showDatePickerDialogGetResult();
        }
    }
}
