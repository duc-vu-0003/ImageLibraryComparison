package con.ducva.imageloadercompare.adapter.holder;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;

import butterknife.BindView;
import con.ducva.imageloadercompare.R;

/**
 * Created by HAL Team on 2/6/17.
 * Copyright © 2017 GMO Media Inc. All rights reserved.
 */
public class ImageLoaderViewHolder extends BaseViewHolder {

    public static final int LAYOUT_ID = R.layout.item_image_normal;

    @BindView(R.id.iv_image)
    ImageView imageView;

    private ImageSize targetSize;
    private DisplayImageOptions options;

    public ImageLoaderViewHolder(View itemView, Context context, RelativeLayout.LayoutParams layoutParams, ImageSize targetSize, DisplayImageOptions options) {
        super(itemView, context, layoutParams);
        imageView.setLayoutParams(layoutParams);
        this.targetSize = targetSize;
        this.options = options;
    }

    @Override
    public void fillData(String url) {
        super.fillData(url);
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.displayImage(url, new ImageViewAware(imageView), options, targetSize, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {

            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {

            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {

            }
        }, new ImageLoadingProgressListener() {
            @Override
            public void onProgressUpdate(String imageUri, View view, int current, int total) {

            }
        });
    }

}
