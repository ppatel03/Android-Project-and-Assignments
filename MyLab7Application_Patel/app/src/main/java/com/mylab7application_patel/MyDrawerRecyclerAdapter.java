package com.mylab7application_patel;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

/**
 * Created by prashant on 2/12/2015.
 */

public class MyDrawerRecyclerAdapter extends RecyclerView.Adapter<MyDrawerRecyclerAdapter.MyViewHolder> {

    final List<Map<String, ?>> drawerList;
    private Context context;
    OnItemClickListener onItemClickListener;
    int mCurrentPosition = -1;

    public MyDrawerRecyclerAdapter(Context context, List<Map<String, ?>> drawerList) {
        this.context = context;
        this.drawerList = drawerList;
    }


    @Override
    public MyDrawerRecyclerAdapter.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {


        View v;
        switch (viewType) {
            case DrawerData.TYPE_1:
                v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.listview_1, viewGroup, false);
                break;

            case DrawerData.TYPE_2:
                v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.listview_2, viewGroup, false);
                break;
            case DrawerData.TYPE_3:
                v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.listview_3, viewGroup, false);
                break;
            case DrawerData.TYPE_4:
                v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.listview_4, viewGroup, false);
                break;
            default:
                v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.listview_1, viewGroup, false);

        }

        MyViewHolder viewHolder = new MyViewHolder(v, viewType);

        return viewHolder;
    }


    @Override
    public void onBindViewHolder(MyDrawerRecyclerAdapter.MyViewHolder myViewHolder, int position) {

        Map<String, ?> drawerMap = drawerList.get(position);
        myViewHolder.bindDrawerData(drawerMap, position);

    }

    @Override
    public int getItemViewType(int position) {
        Map<String, ?> item = drawerList.get(position);
        return (Integer) item.get("type");
    }

    @Override
    public int getItemCount() {
        return drawerList.size();
    }

    //interface inside the class
    public interface OnItemClickListener {
        void onMyRecyclerItemClick(View view, int position);

        void onMyRecyclerItemLongClick(View view, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    /*This is where inner are mostly used. this example perfectly demonstrate the use of inner classes.
     In the constructor itself it attaches the listener(onItemClickListener) which uses parent class's instance variables.
    Had there been no inner class, then this listener should have been extracted from MyRecyclerViewAdapter class and
    assigned to MyViewHolder constructor
    */
    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        TextView title;
        ImageView image;
        View v;

        // final MyRecyclerAdapter myRecyclerAdapter;


        MyViewHolder(View v, int viewType) {
            super(v);
            this.v = v;
            // this.myRecyclerAdapter = myRecyclerAdapter;
            title = (TextView) v.findViewById(R.id.drawerTitle);
            image = (ImageView) v.findViewById(R.id.drawerImage);
            v.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            mCurrentPosition = getPosition();
            if(mCurrentPosition != 0 && mCurrentPosition != 4){
                if (onItemClickListener != null) {
                    onItemClickListener.onMyRecyclerItemClick(v, getPosition());
                }
                notifyDataSetChanged();
            }

        }

        public void bindDrawerData(Map<String, ?> drawerMap, int position) {

            if (position == mCurrentPosition && position != 0 && position != 4) {
                v.setBackgroundColor(Color.LTGRAY);
            } else {
                v.setBackgroundColor(0x00000000);
            }

            if (drawerMap.get("title") != null && title != null) {
                title.setText((String) drawerMap.get("title"));
            }
            if ((Integer)drawerMap.get("image") != -1 && image != null) {
                image.setImageResource((Integer) drawerMap.get("image"));
            }
        }

        @Override
        public boolean onLongClick(View v) {
            if (onItemClickListener != null) {
                onItemClickListener.onMyRecyclerItemLongClick(v, getPosition());
            }
            return true;
        }

    }


}
