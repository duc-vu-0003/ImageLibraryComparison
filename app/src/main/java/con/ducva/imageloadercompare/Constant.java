package con.ducva.imageloadercompare;

import com.facebook.common.util.ByteConstants;

/**
 * Created by HAL Team on 2/5/17.
 * Copyright © 2017 GMO Media Inc. All rights reserved.
 */
public class Constant {

    public enum TYPE {
        Glide, Fresco, Picasso, ImageLoader
    }

    private static final int MAX_HEAP_SIZE = (int) Runtime.getRuntime().maxMemory();

    public static final int MAX_DISK_CACHE_SIZE = 40 * ByteConstants.MB;
    public static final int MAX_MEMORY_CACHE_SIZE = MAX_HEAP_SIZE / 4;

}
