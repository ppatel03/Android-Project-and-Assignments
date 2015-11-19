package com.mylab8application.movie;

import android.content.Context;
import android.util.Log;

import com.mylab8application.utility.MyUtility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MovieDataJson implements Serializable {
    public static final String FILE_SERVER = "http://www.cis.syr.edu/~wedu/Teaching/android/Labs/json/";
    public static final String PATH_FOR_ALL_MOVIES_DATA = "movie.json";


    List<Map<String, ?>> moviesList;

    public List<Map<String, ?>> getMoviesList() {
        return moviesList;
    }

    public int getSize() {
        return moviesList.size();
    }

    public HashMap getItem(int i) {
        if (i >= 0 && i < moviesList.size()) {
            return (HashMap) moviesList.get(i);
        } else return null;
    }

    /**
     * My custom search Implementation
     *
     * @param query
     * @return
     */
    public int findFirst(String query) {
        int position = -1;
        if (query != null || query != "") {
            int index = 0;
            for (Map<String, ?> movieMap : moviesList) {
                String title = (String) movieMap.get("name");
                if (title != null && title != "" && title.toLowerCase().contains(query.toLowerCase())) {
                    position = index;
                    return position;
                }
                index++;
            }
        }


        return position;
    }



    public MovieDataJson(){
        moviesList = new ArrayList<Map<String,?>>();
    }

    public void loadMoviesFromJsonUrl(String path)  {
        try{
            String description = null;
            double rating = 0.0;
            String url = null;
            String name = null;
            String image = null;
            String id = null;
            JSONArray moviesJsonArray = null;
            JSONObject movieJsonObj = null;
            String jsonMoviesDataString = MyUtility.downloadJSON(FILE_SERVER+path);
            moviesJsonArray = new JSONArray(jsonMoviesDataString);
            for (int i = 0; i < moviesJsonArray.length(); i++) {
                movieJsonObj = (JSONObject) moviesJsonArray.get(i);
                if (movieJsonObj != null) {
                    name = (String) movieJsonObj.get("name");
                    rating = Double.parseDouble(movieJsonObj.get("rating").toString());
                    url = (String) movieJsonObj.get("url");
                    description = (String) movieJsonObj.get("description");
                    image = (String) movieJsonObj.get("image");
                    id = (String) movieJsonObj.get("id");
                }
                moviesList.add(createMovieForMoviesList(description,name,image,rating,url,id));

            }
        }catch (Exception e){
            Log.e("Exception : ", " fetching data from Json file" + e);
        }

    }

    public HashMap loadDetailMovieDataFromJsonUrl(String path)  {
        HashMap movie = new HashMap();

        try{
            String description = null;
            double rating = 0.0;
            String url = null;
            String name = null;
            String image = null;
            String id = null;
            String stars = null;
            String year = null;
            String length = null;
            String director = null;
            JSONArray moviesJsonArray = null;
            JSONObject movieJsonObj = null;
            String jsonMoviesDetailDataString = MyUtility.downloadJSON(FILE_SERVER+path);
            movieJsonObj = new JSONObject(jsonMoviesDetailDataString);

            if (movieJsonObj != null) {
                name = (String) movieJsonObj.get("name");
                year = (String) movieJsonObj.get("year");
                length = (String) movieJsonObj.get("length");
                rating = Double.parseDouble(movieJsonObj.get("rating").toString());
                director = (String) movieJsonObj.get("director");
                stars = (String) movieJsonObj.get("stars");
                url = (String) movieJsonObj.get("url");
                description = (String) movieJsonObj.get("description");
                id = (String) movieJsonObj.get("id");
                image = (String) movieJsonObj.get("image");


                movie = getMovieMapForMovieDetail(description,name,image,rating,url,id,stars,
                        year,length,director);

            }


        }catch (Exception e){
            Log.e("Exception : ", " fetching data from Json file" + e);
        }
        return movie;
    }


    private HashMap createMovieForMoviesList( String description, String name,String image, double rating,
                                              String url, String id) {
        HashMap movie = new HashMap();
        movie.put("image", image);
        movie.put("name", name);
        movie.put("description", description);
        movie.put("rating", rating);
        movie.put("url", url);
        movie.put("id", id);
        return movie;
    }


    private HashMap getMovieMapForMovieDetail( String description, String name,String image, double rating,
                                               String url, String id, String stars, String years, String length, String director) {
        HashMap movie = new HashMap();
        movie.put("image", image);
        movie.put("name", name);
        movie.put("description", description);
        movie.put("rating", rating);
        movie.put("url", url);
        movie.put("id", id);
        movie.put("stars", stars);
        movie.put("years", years);
        movie.put("length", length);
        movie.put("director", director);
        return movie;
    }

    public String loadMovieJSONFromAsset(Context context) {
        String json = null;
        try {
            InputStream is = context.getAssets().open("movie.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}
