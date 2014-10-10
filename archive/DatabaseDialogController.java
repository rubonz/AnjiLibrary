package com.darkempire.anjifx.dialog.database;

import com.darkempire.anji.log.Log;
import com.darkempire.anji.database.DatabaseAnalyzer;
import com.darkempire.anjifx.beans.property.AbstractAnjiProperty;
import com.darkempire.anjifx.dialog.DialogUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * Create in 15:15
 * Created by siredvin on 14.04.14.
 */
@Deprecated
public class DatabaseDialogController {
    @FXML
    private ResourceBundle resources;

    @FXML
    private Button cancelButton;

    @FXML
    private ListView<Object> listView;

    @FXML
    private Button addButton;

    @FXML
    private Button confirmButton;
    private Object result;
    private DatabaseAnalyzer analyzer;
    private DatabaseAnalyzer.DBAttribute_impl field;
    private DatabaseAnalyzer.DBTable_impl dbTable;
    private Stage stage;

    @FXML
    void addButtonAction(ActionEvent event) {
        try {
            Object o = dbTable.cl.newInstance();
            List<DatabaseAnalyzer.DBTable_impl> superTables = analyzer.getSuperTables(dbTable.id);
            if (superTables != null) {
                List<String> tableNames = superTables.stream().map(s -> analyzer.getName(s.name)).collect(Collectors.toList());
                String resultName = DialogUtil.selectDialog("Тип", tableNames, "Оберіть тип для додавання");
                DatabaseAnalyzer.DBTable_impl resultTable = superTables.get(tableNames.indexOf(resultName));
                if (resultTable == dbTable) {//Увага:порівняння за посиланням. Можна використовувати, оскільки таки штуки створюються лише раз
                    if (DialogUtil.createDatabaseRecordDialog(o, analyzer, dbTable.attributeList).show()) {
                        analyzer.insertIntoTable(dbTable, o);
                        refresh();
                    }
                } else {
                    Object nextO = resultTable.cl.newInstance();
                    DatabaseRecordEditDialog dialog = DialogUtil.createDatabaseRecordDialog(analyzer, new DatabaseRecordEditDialog.PackedObject(o, dbTable.attributeList),
                            new DatabaseRecordEditDialog.PackedObject(nextO, resultTable.attributeList));
                    if (dialog.show()) {
                        analyzer.insertTableAndSuper(resultTable, dbTable, nextO, o);
                        refresh();
                    }
                }
            } else {
                if (DialogUtil.createDatabaseRecordDialog(o, analyzer, dbTable.attributeList).show()) {
                    analyzer.insertIntoTable(dbTable, o);
                    refresh();
                }
            }
        } catch (SQLException | InstantiationException | IllegalAccessException e) {
            Log.error(Log.coreIndex, e);
        }
    }

    @FXML
    void confirmButtonAction(ActionEvent event) {
        result = listView.getSelectionModel().getSelectedItem();
        stage.close();
    }

    @FXML
    void cancelButtonAction(ActionEvent event) {
        result = null;
        stage.close();
    }

    @FXML
    void initialize() {
        assert cancelButton != null : "fx:id=\"cancelButton\" was not injected: check your FXML file 'databaseDialog.fxml'.";
        assert listView != null : "fx:id=\"listView\" was not injected: check your FXML file 'databaseDialog.fxml'.";
        assert addButton != null : "fx:id=\"addButton\" was not injected: check your FXML file 'databaseDialog.fxml'.";
        assert confirmButton != null : "fx:id=\"confirmButton\" was not injected: check your FXML file 'databaseDialog.fxml'.";
    }

    public Object getResult() {
        return result;
    }

    public void refresh() {
        listView.getItems().clear();
        try {
            Collection<AbstractAnjiProperty> collection = analyzer.getFields(field);
            for (AbstractAnjiProperty property : collection) {
                listView.getItems().add(property.getValue());
            }
        } catch (SQLException e) {
            Log.error(Log.coreIndex, e);
        }
    }

    public void setData(Stage stage, DatabaseAnalyzer analyzer, DatabaseAnalyzer.DBAttribute_impl field, DatabaseAnalyzer.DBTable_impl dbTable) {
        this.stage = stage;
        this.analyzer = analyzer;
        this.field = field;
        this.dbTable = dbTable;
        refresh();
    }
}
