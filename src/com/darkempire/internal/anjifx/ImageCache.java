package com.darkempire.internal.anjifx;

import com.darkempire.internal.Clearable;
import javafx.scene.image.Image;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by siredvin on 07.12.13.
 */
public class ImageCache implements Clearable {
    public static enum ImageType {
        MONOLOG_ACCEPT, MONOLOG_ERROR, MONOLOG_INFO, MONOLOG_QUESTION, PROGRESS_ICON;
        private static final String MONOLOG_ACCEPT_IMAGE = "/com/com.darkempire/res/image/Monolog-accept.jpg";
        private static final String MONOLOG_ERROR_IMAGE = "/com/com.darkempire/res/image/Monolog-error.jpg";
        private static final String MONOLOG_INFO_IMAGE = "/com/com.darkempire/res/image/Monolog-info.jpg";
        private static final String MONOLOG_QUESTION_IMAGE = "/com/com.darkempire/res/image/Monolog-question.jpg";
        private static final String PROGRESS_ICON_IMAGE = "/com/com.darkempire/res/image/Progress-icon.png";

        public String getPath() {
            String result = "";
            switch (this) {
                case MONOLOG_ACCEPT:
                    result = MONOLOG_ACCEPT_IMAGE;
                    break;
                case MONOLOG_ERROR:
                    result = MONOLOG_ERROR_IMAGE;
                    break;
                case MONOLOG_INFO:
                    result = MONOLOG_INFO_IMAGE;
                    break;
                case MONOLOG_QUESTION:
                    result = MONOLOG_QUESTION_IMAGE;
                    break;
                case PROGRESS_ICON:
                    result = PROGRESS_ICON_IMAGE;
                    break;
            }
            return result;
        }
    }

    private Map<ImageType, Image> imageMap;

    public ImageCache() {
        imageMap = new LinkedHashMap<>();
    }

    public Image getImage(ImageType type) {
        Image image = imageMap.get(type);
        if (image == null) {
            image = new Image(ImageCache.class.getResourceAsStream(type.getPath()));
            imageMap.put(type, image);
        }
        return image;
    }

    @Override
    public void clear() {
        imageMap.clear();
    }
}
