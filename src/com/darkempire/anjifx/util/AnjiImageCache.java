package com.darkempire.anjifx.util;

import com.darkempire.anji.annotation.AnjiUtil;
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
        imageList.add(image);
        return imageList.size() - 1;
    }

    public static Image getImage(int index) {
        return imageList.get(index);
    }

    @Deprecated
    public static void anji_clear() {
        imageList.clear();
    }
}
