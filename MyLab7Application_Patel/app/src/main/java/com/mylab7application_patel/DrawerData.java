package com.mylab7application_patel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by prashant on 3/10/2015.
 */
public class DrawerData implements Serializable {
    public static final int TYPE_1 = 1;
    public static final int TYPE_2 = 2;
    public static final int TYPE_3 = 3;
    public static final int TYPE_4 = 4;

    List<Map<String,?>> drawersList;

    public DrawerData(){
        String title;
        int type;
        drawersList = new ArrayList<Map<String,?>>();

        //add the header
        drawersList.add(createDrawer(null,R.drawable.header,TYPE_1));

        //adding the title and images
        drawersList.add(createDrawer("Demo Fragment",R.drawable.linkedin,TYPE_2));
        drawersList.add(createDrawer("List View",R.drawable.twitter_t,TYPE_2));
        drawersList.add(createDrawer("Grid View",R.drawable.fb,TYPE_2));

        //adding the divider
        drawersList.add(createDrawer(null,R.drawable.divider,TYPE_3));

        //adding only the title
        drawersList.add(createDrawer("Avatar Movie",-1,TYPE_4));
        drawersList.add(createDrawer("Titanic Movie",-1,TYPE_4));
        drawersList.add(createDrawer("Avengers Movie",-1,TYPE_4));


    }

    public List<Map<String, ?>> getDrawersList() {
        return drawersList;
    }

    public int getSize(){
        return drawersList.size();
    }

    public Map<String, ?> getItem(int i){
        if (i >=0 && i < drawersList.size()){
            return  drawersList.get(i);
        } else return null;
    }

    public boolean removeItem(int position){
        if (position >=0 && position < drawersList.size()){
            drawersList.remove(position);
            return true;
        }
        else{
            return false;
        }
    }

    private HashMap createDrawer(String title, int image, int type) {
        HashMap drawer = new HashMap();
        drawer.put("title", title);
        drawer.put("image",image);
        drawer.put("type",type);
        return drawer;
    }



}
