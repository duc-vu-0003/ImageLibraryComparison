package con.ducva.imageloadercompare.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import con.ducva.imageloadercompare.R;

/**
 * Created by HAL Team on 2/6/17.
 * Copyright © 2017 GMO Media Inc. All rights reserved.
 */
public class StableAspectFrameLayout extends RelativeLayout {

    private int aspectWidth = 1;
    private int aspectHeight = 1;

    public StableAspectFrameLayout(Context context) {
        this(context, null, 0);
    }

    public StableAspectFrameLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StableAspectFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        extractCustomAttrs(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public StableAspectFrameLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        extractCustomAttrs(context, attrs);
    }

    private void extractCustomAttrs(Context context, AttributeSet attrs) {
        if (attrs == null) return;
        TypedArray a = context.getResources().obtainAttributes(attrs, R.styleable.StableAspectFrameLayout);
        try {
            aspectWidth = a.getInteger(R.styleable.StableAspectFrameLayout_aspect_width, 1);
            aspectHeight = a.getInteger(R.styleable.StableAspectFrameLayout_aspect_height, 1);
        } finally {
            a.recycle();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int newSpecWidth = MeasureSpec.makeMeasureSpec(getMeasuredWidth(), MeasureSpec.EXACTLY);
        int newH = Math.round(((float) getMeasuredWidth()) * aspectHeight / aspectWidth);
        int newSpecHeigh = MeasureSpec.makeMeasureSpec(newH, MeasureSpec.EXACTLY);
        super.onMeasure(newSpecWidth, newSpecHeigh);
    }

}
