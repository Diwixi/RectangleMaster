package com.diwixis.rectanglemaster.di;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Diwixis on 18.10.2017.
 */
@Module
public class RectAppModule {
    private final Context appContext;

    public RectAppModule(Context context) {
        appContext = context;
    }

    @Singleton
    @Provides
    Context providesContext() {
        return appContext;
    }
}
