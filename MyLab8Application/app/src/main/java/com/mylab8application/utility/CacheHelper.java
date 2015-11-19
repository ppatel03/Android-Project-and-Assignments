package com.mylab8application.utility;

import android.graphics.Bitmap;
import android.util.LruCache;

/**
 * Created by prashant on 3/27/2015.
 */
public class CacheHelper {
    //cache to hold movie Images
    public static LruCache<String,Bitmap> mImgMemoryCache = null;


    public static void createCacheForImage(){
        if(mImgMemoryCache == null){
            final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);

            //use 1/8th of the available memory for this memory cache
            final int cacheSize = maxMemory / 8;

            mImgMemoryCache = new LruCache<String,Bitmap>(cacheSize){
                @Override
                protected int sizeOf(String key, Bitmap bitmap){
                    return bitmap.getByteCount()/1024;
                }
            };
        }
    }
}
