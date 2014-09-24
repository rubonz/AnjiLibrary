package com.darkempire.internal.anjifx;

import com.darkempire.internal.Clearable;
import com.darkempire.internal.anji.LocalHolder;
import javafx.fxml.FXMLLoader;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by siredvin on 07.12.13.
 */
public class FXMLCache implements Clearable {
    public static enum FXMLType {
        MONOLOG_ALERT, SIMPLE_PROPERTY, PROGRESS_MONOLOG, DATABASE_DIALOG, DATABASE_RECORD_DIALOG, MULTI_DATABASE_DIALOG;
        private static final String MONOLOG_ALERT_FXML = "/com/darkempire/res/alertDialog.fxml";
        private static final String SIMPLE_PROPERTY_FXML = "/com/darkempire/res/contentDialog.fxml";
        private static final String PROGRESS_MONOLOG_FXML = "/com/darkempire/res/progressDialog.fxml";
        private static final String DATABASE_DIALOG_FXML = "/com/darkempire/res/databaseDialog.fxml";
        private static final String DATABASE_RECORD_DIALOG_FXML = "/com/darkempire/res/databaseRecordDialog.fxml";
        private static final String MULTI_DATABASE_DIALOG_FXML = "/com/darkempire/res/multiDatabaseDialog.fxml";

        public String getPath() {
            String result = null;
            switch (this) {
                case MONOLOG_ALERT:
                    result = MONOLOG_ALERT_FXML;
                    break;
                case SIMPLE_PROPERTY:
                    result = SIMPLE_PROPERTY_FXML;
                    break;
                case PROGRESS_MONOLOG:
                    result = PROGRESS_MONOLOG_FXML;
                    break;
                case DATABASE_DIALOG:
                    result = DATABASE_DIALOG_FXML;
                    break;
                case DATABASE_RECORD_DIALOG:
                    result = DATABASE_RECORD_DIALOG_FXML;
                    break;
                case MULTI_DATABASE_DIALOG:
                    result = MULTI_DATABASE_DIALOG_FXML;
                    break;
            }
            return result;
        }
    }

    private Map<FXMLType, FXMLLoader> loaderHashMap;

    public FXMLCache() {
        loaderHashMap = new LinkedHashMap<>();
    }

    public FXMLLoader loadFXML(FXMLType type) {
        FXMLLoader loader = loaderHashMap.get(type);
        if (loader == null) {
            loader = new FXMLLoader();
            loader.setLocation(FXMLCache.class.getResource(type.getPath()));
            loader.setResources(LocalHolder.anji_resourceBundle);
            loader.setClassLoader(FXMLCache.class.getClassLoader());
            loaderHashMap.put(type, loader);
        }
        loader.setController(null);
        loader.setRoot(null);
        return loader;
    }

    @Override
    public void clear() {
        loaderHashMap.clear();
    }
}
