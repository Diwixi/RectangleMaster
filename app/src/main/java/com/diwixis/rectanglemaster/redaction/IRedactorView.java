package com.diwixis.rectanglemaster.redaction;

import android.graphics.Bitmap;

import com.arellomobile.mvp.MvpView;

/**
 * Created by Diwixis on 14.10.2017.
 */

public interface IRedactorView extends MvpView {

    void invalidate();

    void setBitmap(Bitmap res, Bitmap draw);

    void showToast(String s);
}
