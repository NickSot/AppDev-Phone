package com.example.ourwardrobe;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.Image;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.List;

public class ImageAdapter extends BaseAdapter {

    private List<Bitmap> mThumbIds;
    private Context mContext;

    public ImageAdapter(List<Bitmap> mThumbIds, Context mContext) {
        this.mContext = mContext;
        this.mThumbIds = mThumbIds;
    }

    @Override
    public int getCount() {
        return mThumbIds.size();
    }

    @Override
    public Object getItem(int position) {
        return mThumbIds.get(position);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView = (ImageView) convertView;

        if(imageView == null) {
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(525, 650));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }

        imageView.setImageBitmap(mThumbIds.get(position));

        return imageView;
    }
}
