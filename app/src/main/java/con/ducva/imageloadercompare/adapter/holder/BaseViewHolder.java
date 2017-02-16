package con.ducva.imageloadercompare.adapter.holder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import butterknife.ButterKnife;
import con.ducva.imageloadercompare.ScreenHelper;

/**
 * Created by HAL Team on 2/5/17.
 * Copyright © 2017 GMO Media Inc. All rights reserved.
 */
public class BaseViewHolder extends RecyclerView.ViewHolder {

    protected Context context;
    protected RelativeLayout.LayoutParams layoutParams;
    protected int width;
    protected int height;

    public BaseViewHolder(View itemView, Context context) {
        super(itemView);
        this.context = context;
        ButterKnife.bind(this, itemView);
    }

    public BaseViewHolder(View itemView, Context context, RelativeLayout.LayoutParams layoutParams) {
        super(itemView);
        this.context = context;
        this.layoutParams = layoutParams;
        ButterKnife.bind(this, itemView);
        width = layoutParams.width;
        height = layoutParams.height;
    }

    public void fillData(String url) {
        //TODO: Need override
    }

    public void release() {
        //TODO: Need override
    }

}
