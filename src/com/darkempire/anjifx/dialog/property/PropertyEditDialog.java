package com.darkempire.anjifx.dialog.property;

import com.darkempire.anji.log.Log;
import com.darkempire.anjifx.beans.property.AbstractAnjiProperty;
import com.darkempire.internal.Cache;
import com.darkempire.internal.anjifx.FXMLCache;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: siredvin
 * Date: 20.09.13
 * Time: 12:48
 * To change this template use File | Settings | File Templates.
 */
public class PropertyEditDialog {
    private Iterable<AbstractAnjiProperty> content;
    private PropertyEditController controller;
    private String title;

    public PropertyEditDialog(String title, Iterable<AbstractAnjiProperty> content) {
        this.content = content;
        this.title = title;
    }

    public PropertyEditDialog(String title, AbstractAnjiProperty... content) {
        List<AbstractAnjiProperty> t = new ArrayList<>();
        Collections.addAll(t, content);
        this.content = t;
        this.title = title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void show() {
        Stage stage = new Stage();
        VBox pane = null;
        try {
            FXMLLoader loader = Cache.createFXMLLoader(FXMLCache.FXMLType.SIMPLE_PROPERTY);
            pane = loader.load();
            controller = loader.getController();
        } catch (IOException e) {
            Log.error(Log.coreIndex, e);
        }
        Scene scene = new Scene(pane);
        controller.setControl(content);
        stage.setScene(scene);
        stage.setTitle(title);
        controller.setStage(stage);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

    public Iterable<AbstractAnjiProperty> getContent() {
        return content;
    }
}