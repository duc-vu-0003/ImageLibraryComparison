package con.ducva.imageloadercompare.view;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by HAL Team on 2/6/17.
 * Copyright © 2017 GMO Media Inc. All rights reserved.
 */
public class PreCachingLayoutManager extends GridLayoutManager {

    private static final int DEFAULT_EXTRA_LAYOUT_SPACE = 600;
    private int extraLayoutSpace = -1;
    private Context context;

    public PreCachingLayoutManager(Context context, int spanCount) {
        super(context, spanCount);
        this.context = context;
    }

    public PreCachingLayoutManager(Context context, int spanCount,int extraLayoutSpace) {
        super(context, spanCount);
        this.context = context;
        this.extraLayoutSpace = extraLayoutSpace;
    }

    public PreCachingLayoutManager(Context context, int spanCount, int orientation, boolean reverseLayout) {
        super(context, spanCount, orientation, reverseLayout);
        this.context = context;
    }

    public void setExtraLayoutSpace(int extraLayoutSpace) {
        this.extraLayoutSpace = extraLayoutSpace;
    }

    @Override
    protected int getExtraLayoutSpace(RecyclerView.State state) {
        if (extraLayoutSpace > 0) {
            return extraLayoutSpace;
        }
        return DEFAULT_EXTRA_LAYOUT_SPACE;
    }

}
