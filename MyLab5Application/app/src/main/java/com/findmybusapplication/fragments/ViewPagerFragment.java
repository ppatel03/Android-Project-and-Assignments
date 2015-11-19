package com.findmybusapplication.fragments;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.findmybusapplication.R;
import com.findmybusapplication.bean.Bus;
import com.findmybusapplication.bean.BusForLocation;
import com.findmybusapplication.constants.ArgumentConstants;
import com.findmybusapplication.constants.GenericConstants;
import com.findmybusapplication.data.BusData;
import com.findmybusapplication.dialog.DatePickerFragmentDialog;
import com.findmybusapplication.proxy.FrontPageViewProxy;
import com.google.android.gms.maps.MapFragment;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by prashant on 4/20/2015.
 */
public class ViewPagerFragment extends Fragment implements FrontPageViewProxy.OnDatePickerEditTextListener{
    OnViewPagerButtonClickListener onViewPagerButtonClickListener;
    private EditText datePickerEditText;

    /*
    @Override
    public void onDestroy() {
        super.onDestroy();

        Log.i("onDestroy", "-------------------------------Entered on onDestroy Method---------" +
                "---------------------------");
        MapFragment f1 = ((MapFragment) getActivity().getFragmentManager().findFragmentById(R.id.track_google_map));
        MapFragment f2 = ((MapFragment) getActivity().getFragmentManager().findFragmentById(R.id.share_google_map));
        MapFragment f3 = ((MapFragment) getActivity().getFragmentManager().findFragmentById(R.id.wait_google_map));

        if (f1 != null) {
            Log.i("onDestroy","track google map is deleted");
            getActivity().getFragmentManager().beginTransaction().remove(f1).commit();
        }
        if (f2 != null) {
            Log.i("onDestroy","share google map is deleted");
            getActivity().getFragmentManager().beginTransaction().remove(f2).commit();
        }
        if (f3 != null) {
            Log.i("onDestroy","wait google map is deleted");
            getActivity().getFragmentManager().beginTransaction().remove(f3).commit();
        }
        Log.i("onDestroy", "-----------------------------------Exiting on onDestroy Method-----" +
                "-------------------------------");

    }




    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i("onDestroyView", "-------------------------------Entered on onDestroyView Method---------" +
                "---------------------------");
        MapFragment f1 = ((MapFragment) getActivity().getFragmentManager().findFragmentById(R.id.track_google_map));
        MapFragment f2 = ((MapFragment) getActivity().getFragmentManager().findFragmentById(R.id.share_google_map));
        MapFragment f3 = ((MapFragment) getActivity().getFragmentManager().findFragmentById(R.id.wait_google_map));

        if (f1 != null) {
            Log.i("onDestroyView","track google map is deleted");
            getActivity().getFragmentManager().beginTransaction().remove(f1).commit();
        }
         if (f2 != null) {
            Log.i("onDestroyView","share google map is deleted");
            getActivity().getFragmentManager().beginTransaction().remove(f2).commit();
        }
         if (f3 != null) {
            Log.i("onDestroyView","wait google map is deleted");
            getActivity().getFragmentManager().beginTransaction().remove(f3).commit();
        }
        Log.i("onDestroyView", "-----------------------------------Exiting on onDestroyView Method-----" +
                "-------------------------------");


    }
    */

    public interface OnViewPagerButtonClickListener{
        public void OnViewPagerButtonClick(int id, BusData busData);
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * param ARGS_OPTION option 1.
     * @return A new instance of fragment AboutMeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment newInstance( int type, BusData busData){
        ViewPagerFragment fragment = new ViewPagerFragment();
        Bundle args = new Bundle();
        args.putInt(ArgumentConstants.ARGS_LAYOUT, type);
        args.putSerializable(ArgumentConstants.ARGS_BUS_DATA, busData);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        setRetainInstance(true);

        super.onCreate(savedInstanceState);

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,  Bundle savedInstanceState) {
        setRetainInstance(true);
        int option = getArguments().getInt(ArgumentConstants.ARGS_LAYOUT);
        BusData busData = (BusData)getArguments().getSerializable(ArgumentConstants.ARGS_BUS_DATA);

        FrontPageViewProxy frontPageViewProxy = new FrontPageViewProxy(getActivity(),
                onViewPagerButtonClickListener,busData);
        frontPageViewProxy.setOnDatePickerEditTextListener(this);

       // View rootView = inflater.inflate(R.layout.home_view, container, false);
       // performHomePageViewPagerOperations(rootView);

        return frontPageViewProxy.getViewFromOption(inflater,container,option);
        //return rootView;
    }





    @Override
    public void onAttach(Activity activity) {
        Log.i("ViewPagerFragment ", " inside onAttach");
        super.onAttach(activity);
        try {
            onViewPagerButtonClickListener = (OnViewPagerButtonClickListener) activity;
            Log.i("RecyclerViewFragment ", " mListener is " + (onViewPagerButtonClickListener));

        } catch (ClassCastException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDatePickerEditTextClick(View v) {
        int id = v.getId();
        switch(id){
            case R.id.dateEditTextView :
                this.datePickerEditText = (EditText)v;
                showDatePickerDialogGetResult();

                break;
            default:
        }
    }

    public void showDatePickerDialogGetResult(){
        Log.i("","inside showDatePickerDialogGetResult of DemoFragment");

        Date date = new Date(System.currentTimeMillis());
        DatePickerFragmentDialog datePickerFragmentDialog = DatePickerFragmentDialog.newInstance(date);
        Log.i("","After Call to datePicker");

        datePickerFragmentDialog.setTargetFragment(this,ArgumentConstants.REQUEST_DATE);
        Log.i("","After Call to setTargetFragment");

        datePickerFragmentDialog.show(getFragmentManager(),"DatePicker Dialog : Get Result");

        Log.i("","After Call to show");

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i("inside Activity Result", "inside Activity Result");

        if (resultCode != Activity.RESULT_OK)
            return;

        if (requestCode == ArgumentConstants.REQUEST_DATE) {
            Date date = (Date) data.getSerializableExtra(ArgumentConstants.ARGS_DATE);
            long milliseconds = date.getTime();
            java.sql.Date sqlDate = new java.sql.Date(milliseconds);
            datePickerEditText.setText(sqlDate.toString());
        }

    }



}
