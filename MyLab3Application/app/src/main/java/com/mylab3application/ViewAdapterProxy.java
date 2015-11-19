package com.mylab3application;



import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by prashant on 2/5/2015.
 */
public class ViewAdapterProxy implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener,
        View.OnClickListener{
     final FragmentActivity fragmentActivity;
      MyBaseAdapter myBaseAdapter;
      MyGridBaseAdapter myGridBaseAdapter;



    ViewAdapterProxy(FragmentActivity fragmentActivity, MyBaseAdapter myBaseAdapter){
        this.fragmentActivity = fragmentActivity;
        this.myBaseAdapter = myBaseAdapter;
    }

    ViewAdapterProxy(FragmentActivity fragmentActivity, MyGridBaseAdapter myGridBaseAdapter){
        this.fragmentActivity = fragmentActivity;
        this.myGridBaseAdapter = myGridBaseAdapter;
    }

    public View  getViewUsingBaseAdapter( LayoutInflater inflater, ViewGroup container, int id){
        View rootView = null;

        if(id == R.id.movieListView){

            rootView = inflater.inflate(R.layout.fragment_list_view,container,false);
            final ListView listView = (ListView)rootView.findViewById(R.id.movieListView);
            listView.setAdapter(myBaseAdapter);
            Log.i("List adapter Attached", "List adapter Attached");
            //attach listeners to List View
            listView.setOnItemClickListener(this);
            listView.setOnItemLongClickListener(this);


            //attach Listener to checkBox
            CheckBox checkBox = (CheckBox) rootView.findViewById(R.id.movieCheckBox);


            //attaching listener to Select All button
            ImageView selectAllImage = (ImageView) rootView.findViewById(R.id.selectAll);
            selectAllImage.setOnClickListener(this);

            //attaching listener to Clear All button
            ImageView clearAllImage = (ImageView) rootView.findViewById(R.id.clearAll);
            clearAllImage.setOnClickListener(this);

            //attaching listener to Delete button
            ImageView deleteAllImage = (ImageView) rootView.findViewById(R.id.delete);
            deleteAllImage.setOnClickListener(this);
        }

        if(id == R.id.movieGridView){
            rootView = inflater.inflate(R.layout.fragment_grid_view,container,false);
            final GridView gridView = (GridView)rootView.findViewById(R.id.movieGridView);
            gridView.setAdapter(myGridBaseAdapter);
            //attach listeners to List View
            gridView.setOnItemClickListener(this);
            Log.i("Grid adapter Attached", "Grid adapter Attached");
        }
        return rootView;
    }


    @Override

    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.i("insider ItemClick","insider ItemClick");
        //do not know why it is final
        final HashMap<String, ?> movieMap = (HashMap<String, ?>) parent.getItemAtPosition(position);
        final String item =  (String) movieMap.get("name");

        //setting the Toast
        if(view.findViewById(R.id.movieTitle) != null){
            TextView movieTitle = (TextView) view.findViewById(R.id.movieTitle);
            String movieName = movieTitle.getText().toString();
            Toast.makeText(fragmentActivity," Selected : "+movieName, Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(fragmentActivity," Selected : "+item, Toast.LENGTH_SHORT).show();
        }


        //saving the state of the checkbox
        if(view.findViewById(R.id.movieCheckBox) != null){
            CheckBox checkBox = (CheckBox) view.findViewById(R.id.movieCheckBox);
            final HashMap<String,Boolean> booleanMovieMap = (HashMap<String,Boolean>) movieMap;
            booleanMovieMap.put("selection",!checkBox.isChecked());
            checkBox.setChecked(!checkBox.isChecked());
        }

    }

    @Override
    public void onClick(View view){
        int id = view.getId();

        switch(id){
            case R.id.selectAll :
                selectAllMovieItemsInCheckbox(view);
                break;

            case R.id.clearAll:
                clearAllMovieItemsInCheckbox(view);
                break;

            case R.id.delete :
                deleteSelectedMovieItemInCheckbox(view);

            default:
        }
    }

    public void selectAllMovieItemsInCheckbox(View view){
        for(int i =0 ; i < myBaseAdapter.getCount(); i++){
            HashMap<String,Boolean> movieItem =(HashMap<String,Boolean>) myBaseAdapter.getItem(i);
            movieItem.put("selection",true);
        }
        myBaseAdapter.notifyDataSetChanged();

    }

    public void clearAllMovieItemsInCheckbox(View view){
        for(int i =0 ; i < myBaseAdapter.getCount(); i++){
            HashMap<String,Boolean> movieItem =(HashMap<String,Boolean>) myBaseAdapter.getItem(i);
            movieItem.put("selection",false);
        }
        myBaseAdapter.notifyDataSetChanged();

    }

    public void deleteSelectedMovieItemInCheckbox(View view){
        List<Integer> deletionIndexList = new ArrayList<>();

        for(int i =0 ; i < myBaseAdapter.getCount(); i++){
            HashMap<String,Boolean> movieItem =(HashMap<String,Boolean>) myBaseAdapter.getItem(i);
            boolean isSelected = movieItem.get("selection");
            if(isSelected){
                deletionIndexList.add(i);
            }
        }
        myBaseAdapter.removeItemFromList(deletionIndexList);
        myBaseAdapter.notifyDataSetChanged();

    }



    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

        myBaseAdapter.duplicateMovieItemToNextPosition(position);
        myBaseAdapter.notifyDataSetChanged();

        return true;
    }
}
