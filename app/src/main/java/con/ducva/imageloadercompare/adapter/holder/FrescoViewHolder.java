package con.ducva.imageloadercompare.adapter.holder;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.widget.RelativeLayout;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ImageDecodeOptions;
import com.facebook.imagepipeline.common.Priority;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.common.RotationOptions;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import butterknife.BindView;
import con.ducva.imageloadercompare.R;

/**
 * Created by HAL Team on 2/5/17.
 * Copyright © 2017 GMO Media Inc. All rights reserved.
 */
public class FrescoViewHolder extends BaseViewHolder {

    public static final int LAYOUT_ID = R.layout.item_drawee;

    @BindView(R.id.dv_image)
    SimpleDraweeView simpleDraweeView;

    public FrescoViewHolder(View itemView, Context context, RelativeLayout.LayoutParams layoutParams) {
        super(itemView, context, layoutParams);
        simpleDraweeView.setLayoutParams(layoutParams);
    }

    @Override
    public void fillData(String url) {
        super.fillData(url);
        ImagePipeline imagePipeline = Fresco.getImagePipeline();
        ImageRequest request = ImageRequestBuilder
                .newBuilderWithSource(Uri.parse(url))
                .setLocalThumbnailPreviewsEnabled(true)
                .setProgressiveRenderingEnabled(true)
                .setResizeOptions(new ResizeOptions(width, height))
                .setLowestPermittedRequestLevel(ImageRequest.RequestLevel.FULL_FETCH)
                .build();

//        imagePipeline.prefetchToBitmapCache(request, context);

        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setOldController(simpleDraweeView.getController())
                .setImageRequest(request)
                .build();
        simpleDraweeView.setController(controller);

//        simpleDraweeView.setImageURI(url);
    }

}
