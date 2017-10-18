package com.diwixis.rectanglemaster.redaction;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.diwixis.rectanglemaster.base.RectMasterApp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Diwixis on 14.10.2017.
 */
@InjectViewState
public class RedactorPresenter extends MvpPresenter<IRedactorView>{
    private RectanglePainter painter;
    private Uri imageUri;

    public RedactorPresenter() {
        RectMasterApp.getInstance().getRectComponent().inject(this);
    }

    boolean drawRectangle(View v, MotionEvent event) {
        int action = event.getAction();
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                painter.projectXY((ImageView) v, x, y);
                break;
            case MotionEvent.ACTION_MOVE:
                painter.drawOnRectProjectedBitMap((ImageView) v, x, y);
                getViewState().invalidate();
                break;
            case MotionEvent.ACTION_UP:
                painter.drawOnRectProjectedBitMap((ImageView) v, x, y);
                painter.finalizeDrawing();
                getViewState().invalidate();
                break;
        }
        return true;
    }

    void showImage(Uri source, Context context) {
        imageUri = source;
        try {
            painter = new RectanglePainter(context.getContentResolver().openInputStream(source));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        getViewState().setBitmap(painter.getBitmapMaster(), painter.getBitmapDrawing());
    }

    void saveImage(){
        File myDir = new File(imageUri.getPath());
        try {
            FileOutputStream out = new FileOutputStream(myDir);
            painter.getBitmapMaster().compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.close();
            out.flush();
            getViewState().showToast("Successful save image");
        } catch (IOException e) {
            getViewState().showToast("Failed to save image");
            e.printStackTrace();
        }
    }
}
