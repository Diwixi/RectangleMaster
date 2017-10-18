package com.diwixis.rectanglemaster.redaction;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.diwixis.rectanglemaster.R;
import com.diwixis.rectanglemaster.base.RectMasterApp;
import com.diwixis.rectanglemaster.base.BaseActivity;

import butterknife.BindView;

/**
 * Created by Diwixis on 14.10.2017.
 */

public class RedactorActivity extends BaseActivity implements IRedactorView{
    @InjectPresenter RedactorPresenter presenter;

    @BindView(R.id.result) ImageView imageResult;
    @BindView(R.id.drawingpane) ImageView imageDrawingPane;

    public static void startActivity(Activity activity, Uri imagePath){
        Intent intent = new Intent(activity, RedactorActivity.class);
        intent.putExtra(activity.getString(R.string.image_path_key),  imagePath.toString());
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        imageResult.setOnTouchListener((v, event) -> presenter.drawRectangle(v, event));

        Uri source = Uri.parse(getIntent().getStringExtra(getString(R.string.image_path_key)));
        Point size = new Point();
        getWindowManager().getDefaultDisplay().getSize(size);

        presenter.showImage(source, this);

        RectMasterApp.getInstance().getRectComponent().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_redactor;
    }

    @Override
    public void invalidate(){
        imageDrawingPane.invalidate();
    }

    @Override
    public void setBitmap(Bitmap res, Bitmap draw){
        imageResult.setImageBitmap(res);
        imageDrawingPane.setImageBitmap(draw);
    }

    @Override
    public void showToast(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_redactor_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.save_image:
                presenter.saveImage();
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Attention!")
                .setMessage("All unsaved progress will be lost")
                .setPositiveButton("Cancel", (dialog, i) -> dialog.cancel())
                .setNegativeButton("Quit", (dialogInterface, i) -> super.onBackPressed()).create().show();
    }
}
