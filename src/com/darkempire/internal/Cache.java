package com.darkempire.internal;

import com.darkempire.internal.anji.AnjiInternal;
import com.darkempire.anji.annotation.AnjiUtil;
import com.darkempire.internal.anjifx.FXMLCache;
import com.darkempire.internal.anjifx.ImageCache;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;

/**
 * Created with IntelliJ IDEA.
 * User: siredvin
 * Date: 06.12.13
 * Time: 14:18
 * To change this template use File | Settings | File Templates.
 */
@AnjiUtil
public final class Cache {
    private static final FXMLCache fxmlCache = new FXMLCache();
    private static final ImageCache imageCache = new ImageCache();

    private Cache() {
    }

    public static FXMLLoader createFXMLLoader(FXMLCache.FXMLType type) {
        return fxmlCache.loadFXML(type);
    }

    public static Image getImage(ImageCache.ImageType type) {
        return imageCache.getImage(type);
    }

    @AnjiInternal
    public static void anji_clear() {
        fxmlCache.clear();
        imageCache.clear();
    }
}
