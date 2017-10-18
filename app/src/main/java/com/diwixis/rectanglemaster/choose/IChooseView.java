package com.diwixis.rectanglemaster.choose;

import android.net.Uri;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

/**
 * Created by Diwixis on 14.10.2017.
 */
@StateStrategyType(AddToEndSingleStrategy.class)
interface IChooseView extends MvpView {

    void addImage(Uri source);
}
