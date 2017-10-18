package com.diwixis.rectanglemaster.di;

import com.diwixis.rectanglemaster.choose.ChooseActivity;
import com.diwixis.rectanglemaster.choose.ChoosePresenter;
import com.diwixis.rectanglemaster.redaction.RedactorActivity;
import com.diwixis.rectanglemaster.redaction.RedactorPresenter;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Diwixis on 18.10.2017.
 */
@Singleton
@Component(modules = {RectAppModule.class})
public interface RectAppComponent {
    void inject(ChooseActivity activity);
    void inject(RedactorActivity activity);
    void inject(ChoosePresenter presenter);
    void inject(RedactorPresenter presenter);
}
