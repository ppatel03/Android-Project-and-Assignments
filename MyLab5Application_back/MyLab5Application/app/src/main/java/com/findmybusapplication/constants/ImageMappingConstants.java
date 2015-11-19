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
    }};


}
