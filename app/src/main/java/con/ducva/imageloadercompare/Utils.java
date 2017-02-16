package con.ducva.imageloadercompare;

import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.Locale;

/**
 * Created by HAL Team on 2/5/17.
 * Copyright © 2017 GMO Media Inc. All rights reserved.
 */
public class Utils {

    private static final int BYTES_IN_MEGABYTE = 1024 * 1024;

    public static void appendSize(StringBuilder sb, long bytes) {
        String value = String.format(Locale.getDefault(), "%.1f MB", (float) bytes / BYTES_IN_MEGABYTE);
        sb.append(value);
    }

    public static void appendTime(StringBuilder sb, String prefix, long timeMs, String suffix) {
        appendValue(sb, prefix, timeMs + " ms", suffix);
    }

    public static void appendNumber(StringBuilder sb, String prefix, long number, String suffix) {
        appendValue(sb, prefix, number + "", suffix);
    }

    public static void appendValue(StringBuilder sb, String prefix, String value, String suffix) {
        sb.append(prefix).append(value).append(suffix);
    }

    public static RelativeLayout.LayoutParams getImageLayoutParam(float radio, int spanCount) {
        final RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        float width = ScreenHelper.getScreenWidthInPx() / spanCount;
        float height = radio * width;
        layoutParams.height = Math.round(height);
        layoutParams.width = Math.round(width);
        return layoutParams;
    }

}
