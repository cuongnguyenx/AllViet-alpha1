package com.example.ckmj.interactivemap;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;

/**
 * Created by CUONG on 7/24/2017.
 */

    public class App extends Application{

        private static Context mContext;
        private static Resources mResources;

        @Override
        public void onCreate() {
            super.onCreate();
            mResources= getResources();
            App.mContext = getApplicationContext();
        }

        public static Context getContext(){
            return App.mContext;
        }
    public static   Resources getmResources(){
        return App.mResources;
    }


}



