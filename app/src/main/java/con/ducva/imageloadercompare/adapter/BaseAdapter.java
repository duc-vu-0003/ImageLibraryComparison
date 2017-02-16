package con.ducva.imageloadercompare.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.ImageSize;

import java.util.ArrayList;
import java.util.List;

import con.ducva.imageloadercompare.Constant;
import con.ducva.imageloadercompare.Utils;
import con.ducva.imageloadercompare.adapter.holder.BaseViewHolder;
import con.ducva.imageloadercompare.adapter.holder.FrescoViewHolder;
import con.ducva.imageloadercompare.adapter.holder.GlideViewHolder;
import con.ducva.imageloadercompare.adapter.holder.ImageLoaderViewHolder;
import con.ducva.imageloadercompare.adapter.holder.PicassoViewHolder;

/**
 * Created by HAL Team on 2/5/17.
 * Copyright © 2017 GMO Media Inc. All rights reserved.
 */
public class BaseAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private List<String> urls = new ArrayList<>();
    private Context context;
    private Constant.TYPE type;
    private RelativeLayout.LayoutParams layoutParams;

    //For UIL
    private ImageSize targetSize;
    private DisplayImageOptions options;

    public BaseAdapter(List<String> urls, Context context, Constant.TYPE type, int spanCount) {
        this.context = context;
        this.type = type;
        this.urls = urls;
        layoutParams = Utils.getImageLayoutParam(4f/3f, spanCount);

        targetSize = new ImageSize(layoutParams.width, layoutParams.height);
        options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .resetViewBeforeLoading(true)
                .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
    }

    public BaseAdapter(Context context, Constant.TYPE type, int spanCount) {
        this.context = context;
        this.type = type;
        layoutParams = Utils.getImageLayoutParam(4f/3f, spanCount);

        targetSize = new ImageSize(layoutParams.width, layoutParams.height);
        options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .resetViewBeforeLoading(true)
                .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
    }

    public void add(String url) {
        if (url != null && !url.isEmpty()) {
            urls.add(url);
        }
        notifyDataSetChanged();
    }

    public void addAll(List<String> urls) {
        if (urls != null && !urls.isEmpty()) {
            this.urls.addAll(urls);
        }
        notifyDataSetChanged();
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layoutId;
        View v;
        if (type == Constant.TYPE.Fresco) {
            layoutId = FrescoViewHolder.LAYOUT_ID;
            v = LayoutInflater.from(context)
                    .inflate(layoutId, parent, false);
            return new FrescoViewHolder(v, context, layoutParams);
        } else if (type == Constant.TYPE.Glide) {
            layoutId = GlideViewHolder.LAYOUT_ID;
            v = LayoutInflater.from(context)
                    .inflate(layoutId, parent, false);
            return new GlideViewHolder(v, context, layoutParams);
        } else if (type == Constant.TYPE.ImageLoader) {
            layoutId = ImageLoaderViewHolder.LAYOUT_ID;
            v = LayoutInflater.from(context)
                    .inflate(layoutId, parent, false);
            return new ImageLoaderViewHolder(v, context, layoutParams, targetSize, options);
        } else {
            layoutId = PicassoViewHolder.LAYOUT_ID;
            v = LayoutInflater.from(context)
                    .inflate(layoutId, parent, false);
            return new PicassoViewHolder(v, context, layoutParams);
        }
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.fillData(urls.get(position));
    }

    @Override
    public int getItemCount() {
        return urls == null ? 0 : urls.size();
    }

}
