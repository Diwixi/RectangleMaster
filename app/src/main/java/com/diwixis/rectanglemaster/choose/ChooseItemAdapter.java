package com.diwixis.rectanglemaster.choose;

import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.diwixis.rectanglemaster.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Diwixis on 16.10.2017.
 */

class ChooseItemAdapter extends RecyclerView.Adapter<ChooseItemAdapter.ViewHolder>{
    private List<Uri> images = new ArrayList<>();
    private IOnItemClick click = null;
    private int width = 0;

    ChooseItemAdapter(int width) {
        this.width = width;
    }

    void addImage(Uri uri){
        images.add(uri);
    }

    void addImages(List<Uri> images) {
        this.images.addAll(images);
    }

    void setOnClickListener(IOnItemClick click){
        this.click = click;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_choose, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.imagePath = images.get(position);
        Glide.with(holder.image.getContext())
                .load(holder.imagePath)
                .override(width,width)
                .placeholder(R.drawable.image_launch)
                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        return images == null ? 0 : images.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
            View.OnLongClickListener, PopupMenu.OnMenuItemClickListener{
        @BindView(R.id.image_item) ImageView image;
        private Uri imagePath;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View view) {
            click.onItemClick(imagePath);
        }

        @Override
        public boolean onLongClick(View view) {
            PopupMenu popup = new PopupMenu(view.getContext(), view);
            popup.inflate(R.menu.popup_item_menu);
            popup.setOnMenuItemClickListener(ViewHolder.this);
            popup.show();
            return true;
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            switch (item.getItemId()){
                case R.id.delete: {
                    images.remove(imagePath);
                    notifyDataSetChanged();
                    break;
                }
                default:
                    break;
            }
            return true;
        }
    }

    interface IOnItemClick{
        void onItemClick(Uri imagePath);
    }
}
