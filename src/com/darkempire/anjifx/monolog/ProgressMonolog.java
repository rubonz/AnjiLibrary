package com.darkempire.anjifx.monolog;

import com.darkempire.anji.log.Log;
import com.darkempire.anjifx.beans.property.AnjiBooleanProperty;
import com.darkempire.internal.Cache;
import com.darkempire.internal.anji.LocalHolder;
import com.darkempire.internal.anjifx.FXMLCache;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by siredvin on 07.12.13.
 */
public class ProgressMonolog<V> extends Stage {
    private ProgressMonologController<V> controller;
    private AnjiBooleanProperty autocloseProperty;

    public ProgressMonolog(Task<V> task) {
        FXMLLoader loader = Cache.createFXMLLoader(FXMLCache.FXMLType.PROGRESS_MONOLOG);
        setTitle(LocalHolder.anji_resourceBundle.getString("anjifx.monolog.progress.title"));
        try {
            setScene(new Scene(loader.load()));
        } catch (IOException e) {
            Log.error(Log.coreIndex, e);
        }
        controller = loader.getController();
        controller.setTask(this, task);
        autocloseProperty = new AnjiBooleanProperty(this, "autoclose", false);
        autocloseProperty.addListener(observable -> controller.setAutoclose(autocloseProperty.get()));
    }

    public ProgressMonologController getController() {
        return controller;
    }

    public void showRun() {
        controller.start();
        show();
    }

    public void showAndWaitRun() {
        controller.start();
        showAndWait();
    }

    public V getValue() {
        return controller.getValue();
    }

    public void setAutoclose(boolean autoclose) {
        autocloseProperty.setValue(autoclose);
    }

    public boolean isAutoclose() {
        return autocloseProperty.get();
    }

    public AnjiBooleanProperty autocloseProperty() {
        return autocloseProperty;
    }
}
