package com.darkempire.anjifx.util;

import com.darkempire.anji.annotation.AnjiUtil;
import com.darkempire.internal.anji.AnjiInternal;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by siredvin on 28.02.14.
 */
@AnjiUtil
public final class AnjiImageCache {
    private AnjiImageCache() {
    }

    private static List<Image> imageList = new ArrayList<>();

    public static int addImage(Image image) {
        int index = imageList.size();
        imageList.add(image);
        return index;
    }

    public static Image getImage(int index) {
        return imageList.get(index);
    }

    @AnjiInternal
    public static void anji_clear() {
        imageList.clear();
    }
}
