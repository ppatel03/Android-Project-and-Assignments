package com.findmybusapplication.constants;

import com.findmybusapplication.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by prashant on 4/22/2015.
 */
public class ImageMappingConstants extends GenericConstants {

    public static final Map<String,Integer> imageThumbnailLocationMap = new HashMap<String,Integer>(){{
        put("jamesstreet", R.drawable.jamesstreet);
        put("suwestcott",R.drawable.suwestcott);
        put("eastcampus",R.drawable.eastcampus);
    }};

    public static final Map<Integer,Integer> imageThumbnailFaceMap = new HashMap<Integer,Integer>(){{
        put(0, R.drawable.face1);
        put(1,R.drawable.face2);
        put(2,R.drawable.face3);
        put(3,R.drawable.face4);
        put(4,R.drawable.face5);
        put(5,R.drawable.face6);
    }};


}
