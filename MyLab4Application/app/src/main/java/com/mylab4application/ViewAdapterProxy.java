package com.mylab4application;



import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import java.util.Map;

/**
 * Created by prashant on 2/5/2015.
 */
public class ViewAdapterProxy implements MyRecyclerAdapter.OnItemClickListener, View.OnClickListener{
     final FragmentActivity fragmentActivity;
     MyRecyclerAdapter myRecyclerAdapter;
     MovieData movieData;

    ViewAdapterProxy(FragmentActivity fragmentActivity, MovieData movieData){
        this.fragmentActivity = fragmentActivity;
        this.movieData = movieData;
    }


    public View getViewUsingBaseAdapter( LayoutInflater inflater, ViewGroup container){

        View rootView = null;
            rootView = inflater.inflate(R.layout.fragment_card_view,container,false);
            RecyclerView recyclerView = (RecyclerView)rootView.findViewById(R.id.recyclerView);
            recyclerView.setHasFixedSize(true);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.fragmentActivity);
            recyclerView.setLayoutManager(linearLayoutManager);
            myRecyclerAdapter = new MyRecyclerAdapter(fragmentActivity,movieData.getMoviesList());
            recyclerView.setAdapter(myRecyclerAdapter);
            Log.i("List adapter Attached", "List adapter Attached" + R.id.recyclerView);
            myRecyclerAdapter.setOnItemClickListener(this);

            ImageView selectAllImage = (ImageView) rootView.findViewById(R.id.selectAll);
            selectAllImage.setOnClickListener(this);

            //attaching listener to Clear All button
            ImageView clearAllImage = (ImageView) rootView.findViewById(R.id.clearAll);
            clearAllImage.setOnClickListener(this);

            //attaching listener to Delete button
            ImageView deleteAllImage = (ImageView) rootView.findViewById(R.id.delete);
            deleteAllImage.setOnClickListener(this);



        return rootView;
    }



    //@Override
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

    @Override
    public void onMyRecyclerItemClick(View view, int position) {
        Log.i("insider ItemClick","insider ItemClick");
        //do not know why it is final
        final HashMap<String, ?> movieMap = (HashMap<String, ?>) myRecyclerAdapter.getItemAtPosition(position);
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


    }

    @Override
    public void onMyRecyclerItemLongClick(View view, int position) {
         Map<String,?> movieMap = movieData.getItem(position);
         myRecyclerAdapter.addItemAtPosition(movieMap,position);
         myRecyclerAdapter.notifyItemInserted(position+1);
    }

    public void selectAllMovieItemsInCheckbox(View view){
        for(int i =0 ; i < myRecyclerAdapter.getItemCount(); i++){
            HashMap<String,Boolean> movieItem =(HashMap<String,Boolean>) myRecyclerAdapter.getItemAtPosition(i);
            movieItem.put("selection",true);
        }
        myRecyclerAdapter.notifyDataSetChanged();

    }

    public void clearAllMovieItemsInCheckbox(View view){
        for(int i =0 ; i < myRecyclerAdapter.getItemCount(); i++){
            HashMap<String,Boolean> movieItem =(HashMap<String,Boolean>) myRecyclerAdapter.getItemAtPosition(i);
            movieItem.put("selection",false);
        }
        myRecyclerAdapter.notifyDataSetChanged();

    }

    public void deleteSelectedMovieItemInCheckbox(View view){
        List<Integer> deletionIndexList = new ArrayList<>();

        for(int i =0 ; i < myRecyclerAdapter.getItemCount(); i++){
            HashMap<String,Boolean> movieItem =(HashMap<String,Boolean>) myRecyclerAdapter.getItemAtPosition(i);
            boolean isSelected = movieItem.get("selection");
            if(isSelected){
                deletionIndexList.add(i);
            }
        }
        myRecyclerAdapter.removeItemFromList(deletionIndexList);
        if(deletionIndexList.size() > 0){
            myRecyclerAdapter.notifyItemRangeRemoved(deletionIndexList.get(0),deletionIndexList.size());
        }


    }




}
