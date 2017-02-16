package con.ducva.imageloadercompare.adapter.holder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.squareup.picasso.LruCache;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import con.ducva.imageloadercompare.Constant;
import con.ducva.imageloadercompare.R;

/**
 * Created by HAL Team on 2/6/17.
 * Copyright © 2017 GMO Media Inc. All rights reserved.
 */
public class PicassoViewHolder extends BaseViewHolder {

    public static final int LAYOUT_ID = R.layout.item_image_normal;

    @BindView(R.id.iv_image)
    ImageView imageView;

    public PicassoViewHolder(View itemView, Context context, RelativeLayout.LayoutParams layoutParams) {
        super(itemView, context, layoutParams);
        imageView.setLayoutParams(layoutParams);
    }

    @Override
    public void fillData(String url) {
        super.fillData(url);
        if (width > 0 && height > 0) {
            Picasso.with(context)
                    .load(url)
                    .resize(width, height)
                    .centerCrop()
                    .into(imageView);
        } else {
            Picasso.with(context)
                    .load(url)
                    .centerCrop()
                    .into(imageView);
        }

    }

}
