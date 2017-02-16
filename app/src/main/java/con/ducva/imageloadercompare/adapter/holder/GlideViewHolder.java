package con.ducva.imageloadercompare.adapter.holder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import butterknife.BindView;
import butterknife.ButterKnife;
import con.ducva.imageloadercompare.R;

/**
 * Created by HAL Team on 2/5/17.
 * Copyright © 2017 GMO Media Inc. All rights reserved.
 */
public class GlideViewHolder extends BaseViewHolder {

    public static final int LAYOUT_ID = R.layout.item_image_normal;

    @BindView(R.id.iv_image)
    ImageView imageView;

    public GlideViewHolder(View itemView, Context context, RelativeLayout.LayoutParams layoutParams) {
        super(itemView, context, layoutParams);
        imageView.setLayoutParams(layoutParams);
    }

    @Override
    public void fillData(String url) {
        super.fillData(url);
        if (width > 0 && height > 0) {
            Glide.with(context)
                    .load(url)
                    .override(width, height)
                    .crossFade()
                    .thumbnail(.5f)
                    .centerCrop()
                    .into(imageView);
        } else {
            Glide.with(context)
                    .load(url)
                    .crossFade()
                    .thumbnail(.5f)
                    .centerCrop()
                    .into(imageView);
        }
//        Glide.with(context).load(url).into(imageView);
    }

}
