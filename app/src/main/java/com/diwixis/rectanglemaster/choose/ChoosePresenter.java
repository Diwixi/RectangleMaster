package com.diwixis.rectanglemaster.choose;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.diwixis.rectanglemaster.R;
import com.diwixis.rectanglemaster.base.RectMasterApp;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Diwixis on 14.10.2017.
 */
@InjectViewState
public class ChoosePresenter extends MvpPresenter<IChooseView>{

    ChoosePresenter() {
        RectMasterApp.getInstance().getRectComponent().inject(this);
    }

    void copyImage(Uri imageUri, String root, Context context, String folder){
        Glide.with(context)
                .load(imageUri)
                .asBitmap()
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        String[] splitPath = imageUri.getPath().split("/");
                        String newName = splitPath[splitPath.length-1] + System.currentTimeMillis() + "-rectangle.jpg";
                        checkFolder(root, folder);
                        String newImageName = root + "/" + context.getString(R.string.app_folder) + "/" + newName;
                        File newImage = new File(newImageName);
                        try {
                            if(!newImage.exists()) {
                                newImage.createNewFile();
                            }
                            OutputStream fos = new FileOutputStream(newImage);
                            resource.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                            fos.flush();
                            fos.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        getViewState().addImage(Uri.fromFile(newImage));
                    }
                });
    }

    public void checkFolder(String root, String folderName) {
        File folderForImage = new File(root + "/" + folderName);
        if (!folderForImage.exists()){
            folderForImage.mkdirs();
        }
    }

    List<Uri> getAllImageUri(String root, String folderName){
        File folderForImage = new File(root + "/" + folderName);
        File[] listFiles = folderForImage.listFiles();
        List<Uri> imageUriList = new ArrayList<>();
        for(File filePath: listFiles) {
            Uri uri = Uri.fromFile(filePath);
            imageUriList.add(uri);
        }
        return imageUriList;
    }
}
