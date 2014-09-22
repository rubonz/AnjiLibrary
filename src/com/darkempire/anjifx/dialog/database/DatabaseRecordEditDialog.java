package com.darkempire.anjifx.dialog.database;

import com.darkempire.anji.log.Log;
import com.darkempire.anji.database.DatabaseAnalyzer;
import com.darkempire.anjifx.beans.property.AbstractAnjiProperty;
import com.darkempire.internal.Cache;
import com.darkempire.internal.anjifx.FXMLCache;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.function.Predicate;

/**
 * Created with IntelliJ IDEA.
 * User: siredvin
 * Date: 20.09.13
 * Time: 12:48
 * To change this template use File | Settings | File Templates.
 */
public class DatabaseRecordEditDialog {
    public static class PackedObject {
        public Object object;
        public List<DatabaseAnalyzer.DBAttribute_impl> attributes;

        public PackedObject(Object object, List<DatabaseAnalyzer.DBAttribute_impl> attributes) {
            this.object = object;
            this.attributes = attributes;
        }

    }

    private DatabaseRecordEditController controller;
    private String title;
    private Scene scene;

    public DatabaseRecordEditDialog(String title, DatabaseAnalyzer analyzer, List<DatabaseAnalyzer.DBAttribute_impl> contentID, Object o) {
        this.title = title;
        VBox pane = null;
        try {
            FXMLLoader loader = Cache.createFXMLLoader(FXMLCache.FXMLType.DATABASE_RECORD_DIALOG);
            pane = loader.load();
            controller = loader.getController();
        } catch (IOException e) {
            Log.error(Log.coreIndex, e);
        }
        scene = new Scene(pane);
        controller.setControl(contentID, analyzer, o);
    }

    public DatabaseRecordEditDialog(String title, DatabaseAnalyzer analyzer, PackedObject... objects) {
        this.title = title;
        VBox pane = null;
        try {
            FXMLLoader loader = Cache.createFXMLLoader(FXMLCache.FXMLType.DATABASE_RECORD_DIALOG);
            pane = loader.load();
            controller = loader.getController();
        } catch (IOException e) {
            Log.error(Log.coreIndex, e);
        }
        scene = new Scene(pane);
        controller.setControl(analyzer, objects);
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean show() {
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle(title);
        controller.setStage(stage);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
        return controller.getResult();
    }

    public Iterable<AbstractAnjiProperty> getContent() {
        return controller.getContent();
    }

    public void addFilter(String id, Predicate filter) {
        controller.addFilter(id, filter);
    }
}