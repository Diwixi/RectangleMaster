package com.diwixis.rectanglemaster.base;

import android.app.Application;
import android.content.Context;

import com.diwixis.rectanglemaster.di.DaggerRectAppComponent;
import com.diwixis.rectanglemaster.di.RectAppComponent;
import com.diwixis.rectanglemaster.di.RectAppModule;

/**
 * Created by Diwixis on 14.10.2017.
 */

public class RectMasterApp extends Application {
    private static RectMasterApp instance = new RectMasterApp();
    private static RectAppComponent appComponent;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        getRectComponent();
    }

    public RectAppComponent getRectComponent(){
        if (appComponent == null) {
            appComponent = DaggerRectAppComponent.builder()
                    .rectAppModule(new RectAppModule(this))
                    .build();
        }
        return appComponent;
    }

    public static RectMasterApp getInstance() {
        return instance;
    }
}
