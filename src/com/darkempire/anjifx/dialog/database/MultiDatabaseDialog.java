package com.darkempire.anjifx.dialog.database;

import com.darkempire.anji.log.Log;
import com.darkempire.anji.database.DatabaseAnalyzer;
import com.darkempire.internal.Cache;
import com.darkempire.internal.anjifx.FXMLCache;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.function.Predicate;

/**
 * Create in 9:01
 * Created by siredvin on 15.04.14.
 */
public class MultiDatabaseDialog {
    private Scene scene;
    private boolean isBlockCancel;
    private MultiDatabaseDialogController controller;
    private String title;

    public MultiDatabaseDialog(String title, DatabaseAnalyzer analyzer, DatabaseAnalyzer.DBTable_impl dbTable, DatabaseAnalyzer.DBAttribute_impl fk, Collection<DatabaseAnalyzer.DBAttribute_impl> fields) {
        this.title = title;
        VBox pane = null;
        try {
            FXMLLoader loader = Cache.createFXMLLoader(FXMLCache.FXMLType.MULTI_DATABASE_DIALOG);
            pane = loader.load();
            controller = loader.getController();
        } catch (IOException e) {
            Log.error(Log.coreIndex, e);
        }
        scene = new Scene(pane);
        controller.setData(analyzer, dbTable, fk, fields);
        if (isBlockCancel) {
            controller.blockCancel();
        }
    }

    public MultiDatabaseDialog(String title, DatabaseAnalyzer analyzer, DatabaseAnalyzer.DBTable_impl dbTable, DatabaseAnalyzer.DBAttribute_impl... attribute) {
        this.title = title;
        VBox pane = null;
        try {
            FXMLLoader loader = Cache.createFXMLLoader(FXMLCache.FXMLType.MULTI_DATABASE_DIALOG);
            pane = loader.load();
            controller = loader.getController();
        } catch (IOException e) {
            Log.error(Log.coreIndex, e);
        }
        scene = new Scene(pane);
        controller.setData(analyzer, dbTable, attribute);
        if (isBlockCancel) {
            controller.blockCancel();
        }
    }

    public MultiDatabaseDialog(String title, DatabaseAnalyzer analyzer, DatabaseAnalyzer.DBTable_impl dbTable, Collection<DatabaseAnalyzer.DBAttribute_impl> fields) {
        this.title = title;
        VBox pane = null;
        try {
            FXMLLoader loader = Cache.createFXMLLoader(FXMLCache.FXMLType.MULTI_DATABASE_DIALOG);
            pane = loader.load();
            controller = loader.getController();
        } catch (IOException e) {
            Log.error(Log.coreIndex, e);
        }
        scene = new Scene(pane);
        controller.setData(analyzer, dbTable, fields);
        if (isBlockCancel) {
            controller.blockCancel();
        }
    }

    public MultiDatabaseDialog(String title, DatabaseAnalyzer analyzer, DatabaseAnalyzer.DBTable_impl dbTable, Collection<DatabaseAnalyzer.DBAttribute_impl> fields, Map<String, Predicate> filterMap) {
        this.title = title;
        VBox pane = null;
        try {
            FXMLLoader loader = Cache.createFXMLLoader(FXMLCache.FXMLType.MULTI_DATABASE_DIALOG);
            pane = loader.load();
            controller = loader.getController();
        } catch (IOException e) {
            Log.error(Log.coreIndex, e);
        }
        scene = new Scene(pane);
        controller.setData(analyzer, dbTable, fields, filterMap);
        if (isBlockCancel) {
            controller.blockCancel();
        }
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void show() {
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle(title);
        stage.initModality(Modality.APPLICATION_MODAL);
        controller.setStage(stage);
        stage.showAndWait();
    }

    public Object getResult() {
        return controller.getResult();
    }

    public void setEdit(boolean isEdit) {
        controller.setEdit(isEdit);
    }

    public void setView(boolean isView) {
        controller.setView(isView);
    }

    public void blockCancel() {
        isBlockCancel = true;
    }

    public boolean isBroken() {
        return controller.isBroken();
    }

    public void setSearchable(boolean searchable) {
        controller.setSearchable(searchable);
    }
}
