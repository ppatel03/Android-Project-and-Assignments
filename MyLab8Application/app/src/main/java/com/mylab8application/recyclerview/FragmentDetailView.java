package com.mylab8application.recyclerview;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.mylab8application.R;
import com.mylab8application.asynctasks.MyDownloadMovieImageTask;

import java.util.HashMap;

import static com.mylab8application.utility.CacheHelper.mImgMemoryCache;


public class FragmentDetailView extends Fragment  {    // TODO: Rename parameter arguments, choose names that match


    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private  static final String ARGS_MOVIE = "argument_movie";
    private  static final String ARGS_LAYOUT = "argument_layout";
    private  static final String VIEW_PAGER = "view_pager";



    private HashMap<String,?> movieMap = null;




    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * param ARGS_OPTION option 1.
     * @return A new instance of fragment AboutMeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment newInstance(HashMap<String, ?> movieMap){
        FragmentDetailView fragment = new FragmentDetailView();
        Bundle args = new Bundle();
        args.putSerializable(ARGS_MOVIE, movieMap);
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

        if(getArguments() != null){
            movieMap = (HashMap<String,?>) getArguments().getSerializable(ARGS_MOVIE);
        }

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
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

        ImageView imageView = (ImageView) view.findViewById(R.id.movieImage);
        //async task to get movie image from the url
        String url = (String)movieMap.get("url");
        final Bitmap bitmap = mImgMemoryCache.get("url");
        if(bitmap != null){
            imageView.setImageBitmap(bitmap);
        }
        else{
            MyDownloadMovieImageTask task = new MyDownloadMovieImageTask(imageView);
            task.execute(new String[] {url});
        }

        String length = (String) movieMap.get("length");
        TextView lengthView = (TextView) view.findViewById(R.id.movieLength);
        lengthView.setText(length);

        String year = (String) movieMap.get("year");
        TextView yearView = (TextView) view.findViewById(R.id.movieYear);
        yearView.setText(year);

        String stars = (String) movieMap.get("stars");
        TextView starsView = (TextView) view.findViewById(R.id.movieStars);
        starsView.setText(stars);

        String director = (String) movieMap.get("director");
        TextView directorView = (TextView) view.findViewById(R.id.movieDirector);
        directorView.setText(director);

        if(movieMap.get("rating") != null){
            Double rating = ((Double) movieMap.get("rating"))/2;
            RatingBar ratingBar = (RatingBar) view.findViewById(R.id.movieRatings);
            ratingBar.setRating(Float.parseFloat (rating.toString()));
        }

    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }


}
