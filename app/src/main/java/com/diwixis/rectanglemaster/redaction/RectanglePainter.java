package com.diwixis.rectanglemaster.redaction;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.widget.ImageView;

import java.io.InputStream;

/**
 * Created by Diwixis on 17.10.2017.
 */

public class RectanglePainter {

    private Bitmap bitmapMaster;
    private Canvas canvasMaster;
    private Bitmap bitmapDrawing;
    private Canvas canvasDrawing;
    private Point startPt;

    RectanglePainter(InputStream is) {
        Bitmap tempBitmap =
                BitmapFactory.decodeStream(is);

        Bitmap.Config config;
        if(tempBitmap.getConfig() != null){
            config = tempBitmap.getConfig();
        }else{
            config = Bitmap.Config.ARGB_8888;
        }

        bitmapMaster = Bitmap.createBitmap(
                tempBitmap.getWidth(),
                tempBitmap.getHeight(),
                config);

        canvasMaster = new Canvas(bitmapMaster);
        canvasMaster.drawBitmap(tempBitmap, 0, 0, null);

        bitmapDrawing = Bitmap.createBitmap(
                tempBitmap.getWidth(),
                tempBitmap.getHeight(),
                config);

        canvasDrawing = new Canvas(bitmapDrawing);


    }

    void projectXY(ImageView iv, int x, int y){
        if(x >= 0 && y >= 0 && x <= iv.getWidth() && y <= iv.getHeight()){
            int projectedX = (int)((double)x * ((double)bitmapMaster.getWidth()/(double)iv.getWidth()));
            int projectedY = (int)((double)y * ((double)bitmapMaster.getHeight()/(double)iv.getHeight()));

            startPt = new Point(projectedX, projectedY);
        }
    }

    void drawOnRectProjectedBitMap(ImageView iv, int x, int y){
        if(x >= 0 && y >= 0 && x <= iv.getWidth() && y <= iv.getHeight()){
            int projectedX = (int)((double)x * ((double)bitmapMaster.getWidth()/(double)iv.getWidth()));
            int projectedY = (int)((double)y * ((double)bitmapMaster.getHeight()/(double)iv.getHeight()));

            canvasDrawing.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);

            Paint paint = new Paint();
            paint.setColor(Color.GREEN);
            paint.setAlpha(50);
            canvasDrawing.drawRect(startPt.x, startPt.y, projectedX, projectedY, paint);
        }
    }

    void finalizeDrawing(){
        canvasMaster.drawBitmap(bitmapDrawing, 0, 0, null);
    }

    Bitmap getBitmapMaster() {
        return bitmapMaster;
    }

    Bitmap getBitmapDrawing(){
        return bitmapDrawing;
    }
}
