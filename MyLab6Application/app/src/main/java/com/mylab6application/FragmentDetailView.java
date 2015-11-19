package com.mylab6application;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.support.v7.widget.ShareActionProvider;
import android.widget.TextView;

import java.util.HashMap;


public class FragmentDetailView extends Fragment implements View.OnClickListener {
// TODO: Rename parameter arguments, choose names that match


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.detail_fragment_actionprovider,menu);

        MenuItem shareItem = menu.findItem(R.id.action_share);
        ShareActionProvider shareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(shareItem);
        Intent intentShare = new Intent(Intent.ACTION_SEND);
        intentShare.setType("text/plain");
        intentShare.putExtra(Intent.EXTRA_TEXT, (String) movieMap.get("name"));
        shareActionProvider.setShareIntent(intentShare);

        super.onCreateOptionsMenu(menu, inflater);
    }

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private  static final String ARGS_MOVIE = "argument_movie";
    private  static final String ARGS_LAYOUT = "argument_layout";
    private  static final String VIEW_PAGER = "view_pager";



    private int total = 0;

    private String textClickString1 = "The TextView is clicked  ";
    private String textClickString2 = " times ";
    private String finalOutputString=null;

    private HashMap<String,?> movieMap = null;


    public int getTotal(){
        return this.total;
    }

    public void setFinalOutputString(String finalOutputString){
        this.finalOutputString = finalOutputString;

    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * param ARGS_OPTION option 1.
     * @return A new instance of fragment AboutMeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment newInstance(HashMap<String, ?> movieMap, String type){
        Log.i("FragmentDetailView new Instance is loaded","FragmentDetailView new Instance is loaded");
        FragmentDetailView fragment = new FragmentDetailView();
        Bundle args = new Bundle();
        args.putSerializable(ARGS_MOVIE,movieMap);
        args.putString(ARGS_LAYOUT,type);
        fragment.setArguments(args);
        return fragment;
    }



    public FragmentDetailView() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        setHasOptionsMenu(true);
        if(getArguments() != null){
            movieMap = (HashMap<String,?>) getArguments().getSerializable(ARGS_MOVIE);
        }

        if(savedInstanceState != null){
            finalOutputString = savedInstanceState.getString("Total");
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i("FragmentDetailView onCreateView is called","FragmentDetailView onCreateView is called");

        setRetainInstance(true);
        View fragmentPagerView = inflater.inflate(R.layout.fragment_view_pager, container,false);

        //populate movie Data
        populateMovieData(fragmentPagerView);

        return fragmentPagerView;
    }

    private void populateMovieData(View view){
        String title = (String) movieMap.get("name");
        TextView titleView = (TextView) view.findViewById(R.id.movieTitle);
        titleView.setTextColor(Color.YELLOW);
        titleView.setText(title);


        String description = (String) movieMap.get("description");
        TextView descriptionView = (TextView) view.findViewById(R.id.movieDescription);
        descriptionView.setText(description);

        Integer imageResourceId = (Integer) movieMap.get("image");
        ImageView imageView = (ImageView) view.findViewById(R.id.movieImage);
        imageView.setImageResource(imageResourceId);

        String year = (String) movieMap.get("year");
        TextView yearView = (TextView) view.findViewById(R.id.movieYear);
        yearView.setText(year);

        String stars = (String) movieMap.get("stars");
        TextView starsView = (TextView) view.findViewById(R.id.movieStars);
        starsView.setText(stars);

        String director = (String) movieMap.get("director");
        TextView directorView = (TextView) view.findViewById(R.id.movieDirector);
        directorView.setText(director);

        Double rating = ((Double) movieMap.get("rating"))/2;
        RatingBar ratingBar = (RatingBar) view.findViewById(R.id.movieRatings);
        ratingBar.setRating(Float.parseFloat (rating.toString()));

        TextView textClickCountView = (TextView) view.findViewById(R.id.textClickCount);
        textClickCountView.setTextColor(Color.RED);

        String type = (String)getArguments().get(ARGS_LAYOUT);
        if(VIEW_PAGER.equalsIgnoreCase(type)){
            if(finalOutputString == null || finalOutputString == ""){
                textClickCountView.setText(textClickString1+" 0 "+textClickString2);
            }
            else{
                textClickCountView.setText(finalOutputString);
            }
        }


        textClickCountView.setOnClickListener( this);
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString("Total", finalOutputString);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id){
            case R.id.textClickCount :
                this.total++;
                TextView textClickCountView = (TextView)v.findViewById(id);
                setFinalOutputString(textClickString1+getTotal()+textClickString2);
                textClickCountView.setText(finalOutputString);
                break;

            default:
        }
    }
}
