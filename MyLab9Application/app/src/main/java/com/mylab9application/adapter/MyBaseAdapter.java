package com.mylab9application.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import com.alexvasilkov.android.commons.adapters.ItemsAdapter;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mylab9application.R;
import com.mylab9application.asynctasks.MyDownloadMovieImageTask;
import com.mylab9application.listitem.ListItem;

import static com.mylab9application.utility.CacheHelper.mImgMemoryCache;

/**
 * Created by prashant on 2/5/2015.
 */
public class MyBaseAdapter extends ItemsAdapter<ListItem> implements View.OnClickListener{
    private  final Context context;
    private final List<Map<String,?>> moviesList;
    IFoldableItemListener foldableItemListener;

    public MyBaseAdapter(Context context, List<Map<String, ?>> moviesList){
        super(context);
        this.context = context;
        this.moviesList = moviesList;
    }

    public interface IFoldableItemListener{

        void onFoldableItemClick(View v, int position);
    }

    public void setIFoldableItemListener(IFoldableItemListener foldableItemListener){
        this.foldableItemListener = foldableItemListener;
    }

    public boolean isEnabled(int position){
        if(position == 0){
            return false;
        }
        else{
            return true;
        }
    }

    @Override
    public int getCount() {

        return moviesList.size();
    }

    public Object getItemAtPos(int position) {
        return moviesList.get(position);
    }

    @Override
    public ListItem getItem(long id) {
        return super.getItem(id);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public void removeItemFromList(List<Integer> deletionList){
        if(deletionList.size() >= this.moviesList.size()){
            this.moviesList.clear();
        }
        else{
            int count = 0;
            for(int position : deletionList){
                if(this.moviesList != null && this.moviesList.size() > position && this.isEnabled(position)){
                    this.moviesList.remove(position-(count++));
                }
            }

        }

    }

    public void duplicateMovieItemToNextPosition(int position){
        if(position < this.moviesList.size()){
            final HashMap<String, ?> movieMap = (HashMap<String, ?>) this.moviesList.get(position);
            HashMap<String, ?> duplicateMovieMap = movieMap;
            this.moviesList.add(position+1,duplicateMovieMap);
        }

    }

    @Override
    public void onClick(View v) {
        Log.i(" Yeahh I am clicked ", " Yeahh I am clicked : "+v.getTag());
        // int position = ((RecyclerView.ViewHolder)v.getTag()).getPosition();
        // Log.i(" Item clicked at ", " position "+position);
        Integer postion = (Integer)( v.getTag());
        foldableItemListener.onFoldableItemClick(v, postion);
    }




    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = null;
        MyViewHolder viewHolder = null;
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.list_item, parent, false);
            viewHolder = new MyViewHolder(parent);
            viewHolder.movieImage= (ImageView) rowView.findViewById(R.id.movieImage);
            viewHolder.movieTitle = (TextView) rowView.findViewById(R.id.movieTitle);
            viewHolder.movieDescription = (TextView) rowView.findViewById(R.id.movieDescription);
            viewHolder.movieRatings = (RatingBar) rowView.findViewById(R.id.movieRatings);

            rowView.setTag(viewHolder);

            viewHolder.movieImage.setOnClickListener(this);
            viewHolder.movieImage.setTag(position);

        }
        else{
            rowView = convertView;
            viewHolder = (MyViewHolder)rowView.getTag();
        }

            rowView.setBackgroundResource(R.drawable.blackbackground1);



        Map<String,?> movieMap = moviesList.get(position);
        if(movieMap != null){
            try{
                //async task to get movie image from the url
                String url = (String)movieMap.get("url");
                final Bitmap bitmap = mImgMemoryCache.get("url");
                if(bitmap != null){
                    viewHolder.movieImage.setImageBitmap(bitmap);
                }
                else{
                    MyDownloadMovieImageTask task = new MyDownloadMovieImageTask(viewHolder.movieImage);
                    task.execute(new String[] {url});
                }

                //setting the movie title from movie map
                String movieTitleString = (String) movieMap.get("name");
                viewHolder.movieTitle.setText(movieTitleString);
                viewHolder.movieTitle.setTextColor(Color.YELLOW);


                //setting the movie Description from movie map
                String movieDescriptionString = (String) movieMap.get("description");
                viewHolder.movieDescription.setText(movieDescriptionString);

                //setting the ratings from movie map
                Double rating = (Double)movieMap.get("rating");
                viewHolder.movieRatings.setRating((float) (rating / 2));

            }
            catch (ClassCastException e){
                Log.e("ClassCastException while setting value from Movie Map ", e.toString());
            }

        }
        return  rowView;
    }

    @Override
    protected View createView(ListItem listItem, int i, ViewGroup viewGroup, LayoutInflater inflater) {
        View rowView = null;
        MyViewHolder viewHolder = null;
        rowView = inflater.inflate(R.layout.list_item, viewGroup, false);
        viewHolder = new MyViewHolder(viewGroup);
        viewHolder.movieImage= (ImageView) rowView.findViewById(R.id.movieImage);
        viewHolder.movieTitle = (TextView) rowView.findViewById(R.id.movieTitle);
        viewHolder.movieDescription = (TextView) rowView.findViewById(R.id.movieDescription);
        viewHolder.movieRatings = (RatingBar) rowView.findViewById(R.id.movieRatings);

        rowView.setTag(viewHolder);
        viewHolder.movieImage.setOnClickListener(this);


        return rowView;
    }

    @Override
    protected void bindView(ListItem listItem, int i, View view) {
        MyViewHolder viewHolder = (MyViewHolder) view.getTag();
        viewHolder.movieDescription = listItem.movieDescription;
        viewHolder.movieImage = listItem.movieImage;
        viewHolder.movieRatings = listItem.movieRatings;
        viewHolder.movieTitle = listItem.movieTitle;

        Map<String,?> movieMap = moviesList.get(i);
        if(movieMap != null){
            try{
                //async task to get movie image from the url
                String url = (String)movieMap.get("url");
                final Bitmap bitmap = mImgMemoryCache.get("url");
                if(bitmap != null){
                    viewHolder.movieImage.setImageBitmap(bitmap);
                }
                else{
                    MyDownloadMovieImageTask task = new MyDownloadMovieImageTask(viewHolder.movieImage);
                    task.execute(new String[] {url});
                }

                //setting the movie title from movie map
                String movieTitleString = (String) movieMap.get("name");
                viewHolder.movieTitle.setText(movieTitleString);
                viewHolder.movieTitle.setTextColor(Color.YELLOW);


                //setting the movie Description from movie map
                String movieDescriptionString = (String) movieMap.get("description");
                viewHolder.movieDescription.setText(movieDescriptionString);

                //setting the ratings from movie map
                Double rating = (Double)movieMap.get("rating");
                viewHolder.movieRatings.setRating((float) (rating / 2));

            }
            catch (ClassCastException e){
                Log.e("ClassCastException while setting value from Movie Map ", e.toString());
            }

        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView movieTitle;
        TextView movieDescription;
        ImageView movieImage;
        RatingBar movieRatings;

        public MyViewHolder(View itemView) {
            super(itemView);
        }

        public int getItemPosition(){
            return  getPosition();
        }
    }


}
