package com.diwixis.rectanglemaster.choose;

import android.content.Intent;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.diwixis.rectanglemaster.R;
import com.diwixis.rectanglemaster.base.RectMasterApp;
import com.diwixis.rectanglemaster.base.BaseActivity;
import com.diwixis.rectanglemaster.redaction.RedactorActivity;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChooseActivity extends BaseActivity implements IChooseView{
    @InjectPresenter ChoosePresenter presenter;

    @BindView(R.id.choose_recycler) RecyclerView recycler;
    ChooseItemAdapter adapter;

    private String root;

    private ChooseItemAdapter.IOnItemClick clickListener =
            result -> RedactorActivity.startActivity(ChooseActivity.this, result);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        Point size = new Point();
        getWindowManager().getDefaultDisplay().getSize(size);

        recycler.setLayoutManager(new GridLayoutManager(this, 2));
        initAdapter(size.x);

        RectMasterApp.getInstance().getRectComponent().inject(this);
    }

    private void initAdapter(int w){
        root = Environment.getExternalStorageDirectory().getPath();
        adapter = new ChooseItemAdapter(w / 2);
        adapter.setOnClickListener(clickListener);
        getAllShownImagesPath();
        recycler.setAdapter(adapter);
    }

    public void getAllShownImagesPath() {
        File[] listFiles = new File(root + "/" + getString(R.string.app_folder)).listFiles();
        for(File filePath: listFiles) {
            Uri uri = Uri.fromFile(filePath);
            adapter.addImage(uri);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_choose;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_choose_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.add_item:
                Intent intent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 1);
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            switch (requestCode){
                case 1:
                    Uri source = data.getData();
                    presenter.copyImage(source, root, this);
                    break;
            }
        }
    }

    @Override
    public void addImage(Uri source){
        ChooseItemAdapter adapter = (ChooseItemAdapter) recycler.getAdapter();
        adapter.addImage(source);
        recycler.setAdapter(adapter);
    }
}
