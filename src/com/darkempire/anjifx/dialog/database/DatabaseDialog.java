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

/**
 * Create in 9:01
 * Created by siredvin on 15.04.14.
 */
@Deprecated
public class DatabaseDialog {
    private DatabaseAnalyzer analyzer;
    private DatabaseAnalyzer.DBAttribute_impl field;
    private DatabaseAnalyzer.DBTable_impl dbTable;
    private DatabaseDialogController controller;
    private String title;

    public DatabaseDialog(String title, DatabaseAnalyzer analyzer, DatabaseAnalyzer.DBAttribute_impl field, DatabaseAnalyzer.DBTable_impl dbTable) {
        this.analyzer = analyzer;
        this.field = field;
        this.dbTable = dbTable;
        this.title = title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void show() {
        Stage stage = new Stage();
        VBox pane = null;
        try {
            FXMLLoader loader = Cache.createFXMLLoader(FXMLCache.FXMLType.DATABASE_DIALOG);
            pane = loader.load();
            controller = loader.getController();
        } catch (IOException e) {
            Log.error(Log.coreIndex, e);
        }
        Scene scene = new Scene(pane);
        controller.setData(stage, analyzer, field, dbTable);
        stage.setScene(scene);
        stage.setTitle(title);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

    public Object getResult() {
        return controller.getResult();
    }
}
