package com.findmybusapplication.proxy;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.findmybusapplication.R;
import com.findmybusapplication.adapter.MyGridBaseAdapter;
import com.findmybusapplication.bean.Bus;
import com.findmybusapplication.bean.BusForLocation;
import com.findmybusapplication.bean.BusFormData;
import com.findmybusapplication.constants.CriteriaConstants;
import com.findmybusapplication.data.BusData;
import com.findmybusapplication.fragments.ViewPagerFragment;
import com.findmybusapplication.gpshandler.GPSDataHandler;
import com.findmybusapplication.helper.GenericHelper;

import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * Created by prashant on 4/20/2015.
 */
public class FrontPageViewProxy  implements View.OnClickListener,View.OnTouchListener, AdapterView.OnItemClickListener {
    FragmentActivity fragmentActivity;
    ViewPagerFragment.OnViewPagerButtonClickListener onViewPagerButtonClickListener;
    OnDatePickerEditTextListener onDatePickerEditTextListener;
    private EditText dataPickerEditText;
    private EditText timePickerEditText;
    private AutoCompleteTextView fromEditText;
    private AutoCompleteTextView toEditText;
    private RadioGroup criteriaRadioGroup;
    private String sortingCriteriaValue = CriteriaConstants.TIME_CRITERIA;
    private BusData busData;
    private MyGridBaseAdapter mySavedSearchGridBaseAdapter;
    private ImageView savedSearchPasteImage;


    public FrontPageViewProxy(FragmentActivity fragmentActivity,
                              ViewPagerFragment.OnViewPagerButtonClickListener onViewPagerButtonClickListener,
                              BusData busData){
        this.fragmentActivity = fragmentActivity;
        this.onViewPagerButtonClickListener = onViewPagerButtonClickListener;
        this.busData = busData;
    }




    public interface OnDatePickerEditTextListener{
        public void onDatePickerEditTextClick(View v);
    }

    public void setOnDatePickerEditTextListener(OnDatePickerEditTextListener onDatePickerEditTextListener){
        this.onDatePickerEditTextListener = onDatePickerEditTextListener;
    }

    /**
     *
     * @param inflater
     * @param container
     * @param option
     * @return
     */
    public View getViewFromOption(LayoutInflater inflater, ViewGroup container, int option){

        View rootView = null;

        switch(option){
            case 0 :
                rootView = inflater.inflate(R.layout.home_view, container, false);
                performHomePageViewPagerOperations(rootView);
                break;

            case 1 :
                rootView = inflater.inflate(R.layout.search_bus_view, container, false);
                performSearchBusViewPagerOperations( rootView);
                break;

            case 2 :
                rootView = inflater.inflate(R.layout.wait_for_me_view, container, false);
                break;

            case 3 :
                rootView = inflater.inflate(R.layout.saved_search_view, container, false);
                performSavedSearchViewPagerOperations(rootView);
                break;

            case 4 :
                rootView = inflater.inflate(R.layout.share_location_view, container, false);
                performShareLocationViewPagerOperations(rootView, inflater,  container);
                break;

            case 5 :
                rootView = inflater.inflate(R.layout.track_bus_location_view, container, false);
                performTrackLocationViewPagerOperations(rootView, inflater, container);
                break;

            case 6 :
                rootView = inflater.inflate(R.layout.wait_for_me_view, container, false);
                performWaitForMeViewPagerOperations(rootView, inflater, container);
                break;

          default:
                rootView = inflater.inflate(R.layout.home_view, container, false);

        }

        return rootView;

    }

    /**
     *
     * perform operations for Home Page View
     *
     * @param rootView
     */
    public void performHomePageViewPagerOperations(View rootView){

        ImageView searchBusButtonImage = (ImageView)rootView.findViewById(R.id.searchBusImage);
        searchBusButtonImage.setOnClickListener(this);

        ImageView nearestBusStopButtonImage = (ImageView)rootView.findViewById(R.id.waitRequestImage);
        nearestBusStopButtonImage.setOnClickListener(this);

        ImageView savedSearchButtonImage = (ImageView)rootView.findViewById(R.id.savedSearchImage);
        savedSearchButtonImage.setOnClickListener(this);

        ImageView shareLocationButtonImage = (ImageView)rootView.findViewById(R.id.shareLocationImage);
        shareLocationButtonImage.setOnClickListener(this);

        ImageView trackLocationButtonImage = (ImageView)rootView.findViewById(R.id.trackLocationImage);
        trackLocationButtonImage.setOnClickListener(this);

        ImageView waitForMeButtonImage = (ImageView)rootView.findViewById(R.id.waitForMeImage);
        waitForMeButtonImage.setOnClickListener(this);
    }

    /**
     *
     * perform operations for Bus Search View
     *
     * @param rootView
     */
    public void performSearchBusViewPagerOperations(View rootView){

        savedSearchPasteImage = (ImageView)rootView.findViewById(R.id.savedSearchPasteImage);
        savedSearchPasteImage.setOnClickListener(this);

        fromEditText = (AutoCompleteTextView) rootView.findViewById(R.id.fromEditTextView);
        toEditText = (AutoCompleteTextView) rootView.findViewById(R.id.toEditTextView);

        //setting the data for the dropdown
        Map<String,List<BusForLocation>> busLocationData = busData.getStopLocationMap();
        Set<String> locationSet = busLocationData.keySet();
        Object[] locationsArray = (Object[]) locationSet.toArray();
        ArrayAdapter adapter = new ArrayAdapter(this.fragmentActivity,
                android.R.layout.simple_spinner_dropdown_item,locationsArray);
        fromEditText.setAdapter(adapter);
        toEditText.setAdapter(adapter);

        dataPickerEditText = (EditText) rootView.findViewById(R.id.dateEditTextView);
        dataPickerEditText.setOnTouchListener(this);

        timePickerEditText = (EditText) rootView.findViewById(R.id.timeEditTextView);
        timePickerEditText.setText(GenericHelper.getCurrentTime());

        criteriaRadioGroup = (RadioGroup) rootView.findViewById(R.id.criteriaRadioGroup);
        criteriaRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                RadioButton rb=(RadioButton)group.findViewById(checkedId);
                sortingCriteriaValue = (String)rb.getText();
            }
        });

        ImageView searchBusesButtonImage = (ImageView) rootView.findViewById(R.id.submitBusSearchImage);
        searchBusesButtonImage.setOnClickListener(this);


    }


    /**
     *
     * @param rootView
     */
    public void performSavedSearchViewPagerOperations(View rootView){
        final GridView gridView = (GridView)rootView.findViewById(R.id.savedSearchGridView);
        mySavedSearchGridBaseAdapter = new MyGridBaseAdapter(this.fragmentActivity.getApplicationContext(),
                busData.getSavedBusFormDataList());
        gridView.setAdapter(mySavedSearchGridBaseAdapter);
        gridView.setOnItemClickListener(this);
    }

    /**
     *
     * @param rootView
     * @param inflater
     * @param container
     */
    public void performShareLocationViewPagerOperations(View rootView,LayoutInflater inflater, ViewGroup container){
        GPSDataHandler gpsDataHandler = new GPSDataHandler(this.fragmentActivity,busData);
        rootView = gpsDataHandler.getViewAndSetGPSLocationDetailsForShareLocation(inflater,container,rootView);
    }


    /**
     *
     * @param rootView
     * @param inflater
     * @param container
     */
    public void performWaitForMeViewPagerOperations(View rootView,LayoutInflater inflater, ViewGroup container){
        GPSDataHandler gpsDataHandler = new GPSDataHandler(this.fragmentActivity,busData);
        rootView = gpsDataHandler.getViewAndSetWaitForMeLocation(inflater,container,rootView);
    }

    /**
     *
     * @param rootView
     * @param inflater
     * @param container
     */
    public void performTrackLocationViewPagerOperations(View rootView,LayoutInflater inflater, ViewGroup container){
        GPSDataHandler gpsDataHandler = new GPSDataHandler(this.fragmentActivity,busData);
        rootView = gpsDataHandler.getViewAndTrackBusGPSForShareLocation(inflater, container, rootView);
    }



    @Override
    public void onClick(View v) {
        int id = v.getId();

        if(id == R.id.searchBusImage || id == R.id.waitRequestImage ||
                id == R.id.savedSearchImage || id == R.id.shareLocationImage ||
                id == R.id.trackLocationImage || id == R.id.waitForMeImage){
            this.onViewPagerButtonClickListener.OnViewPagerButtonClick(id,busData);
        }

        else if(id == R.id.submitBusSearchImage){

            List<Bus> selectedBusList = GenericHelper.getSelectedBusesFromInputs(fromEditText, toEditText,
                    dataPickerEditText,timePickerEditText,busData, sortingCriteriaValue);
            if(!selectedBusList.isEmpty()){
                busData.setSelectedBusList(selectedBusList);
                this.onViewPagerButtonClickListener.OnViewPagerButtonClick(id,busData);
            }
            else{
                //Show No Results Found Dialog Box
                new AlertDialog.Builder(this.fragmentActivity)
                        .setTitle("Search Failed")
                        .setMessage("No Bus Found")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Your code
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .show();
            }
        }

        else if(id == R.id.savedSearchPasteImage){
            Log.i("flag : ",busData.isSaveSearchEnabled()+"");
            Log.i("selected bus from data", ""+busData.getSelectedSavedFormData());

            if(busData.isSaveSearchEnabled() && busData.getSelectedSavedFormData() != null ){
                BusFormData busFormData  = busData.getSelectedSavedFormData();
                fromEditText.setText(busFormData.getFrom());
                toEditText.setText(busFormData.getTo());
                dataPickerEditText.setText(busFormData.getDate());
                timePickerEditText.setText(busFormData.getTime());
            }
        }

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int id = v.getId();
        if( id == R.id.dateEditTextView){
            if(event.getAction() == MotionEvent.ACTION_UP) {
                this.onDatePickerEditTextListener.onDatePickerEditTextClick(v);
                return true;
            }
        }
        return false;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(busData.getSavedBusFormDataList() != null &&
                !busData.getSavedBusFormDataList().isEmpty()){
            busData.setSelectedSavedFormData(busData.getSavedBusFormDataList().get(position));
            busData.setSaveSearchEnabled(true);
            this.onViewPagerButtonClickListener.OnViewPagerButtonClick(R.id.searchBusImage,busData);

        }
    }
}
