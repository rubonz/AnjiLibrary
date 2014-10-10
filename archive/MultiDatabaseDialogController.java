package com.darkempire.anjifx.dialog.database;

import com.darkempire.anji.log.Log;
import com.darkempire.anji.database.DatabaseAnalyzer;
import com.darkempire.anjifx.beans.property.AbstractAnjiProperty;
import com.darkempire.anjifx.beans.property.AnjiIntegerProperty;
import com.darkempire.anjifx.beans.property.transform.ControlTransformProperty;
import com.darkempire.anjifx.beans.property.transform.ITransform;
import com.darkempire.anjifx.dialog.DialogUtil;
import com.darkempire.anjifx.scene.LongField;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Create in 15:15
 * Created by siredvin on 14.04.14.
 */
public class MultiDatabaseDialogController {

    @FXML
    private Button cancelButton;

    @FXML
    private ChoiceBox<String> attributeBox;

    @FXML
    private TableView<?> tableView;

    @FXML
    private Button addButton;

    @FXML
    private ChoiceBox<String> operationBox;

    @FXML
    private TextField paramTextBox;
    @FXML
    private DatePicker paramDateBox;
    @FXML
    private Button confirmButton;

    @FXML
    private HBox searchBox;

    private LongField paramIntBox;

    private Button searchButton;

    private Object result;

    private DatabaseAnalyzer analyzer;

    private DatabaseAnalyzer.DBTable_impl dbTable;

    private Stage stage;

    private Map<String, Predicate> filterMap;

    private boolean isBroken;

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
    void editButtonAction(ActionEvent event) {
        try {
            Object o = tableView.getSelectionModel().getSelectedItem();
            DatabaseAnalyzer.DBTable_impl superTable = analyzer.isHasSuperTable(dbTable);
            if (superTable != null && superTable != dbTable) {
                Object superO = analyzer.getObjectWithPrimaryKey(dbTable, superTable, o);
                DatabaseRecordEditDialog dialog = DialogUtil.createDatabaseRecordDialog(analyzer, new DatabaseRecordEditDialog.PackedObject(o, dbTable.attributeList),
                        new DatabaseRecordEditDialog.PackedObject(superO, superTable.attributeList));
                if (dialog.show()) {
                    analyzer.updateTable(superTable, superO);
                    analyzer.updateTable(dbTable, o);
                    refresh();
                }
            } else {
                if (DialogUtil.createDatabaseRecordDialog(o, analyzer, dbTable.attributeList).show()) {
                    analyzer.updateTable(dbTable, o);
                    refresh();
                }
            }
        } catch (SQLException | IllegalAccessException e) {
            Log.error(Log.coreIndex, e);
        }
    }

    @FXML
    void confirmButtonAction(ActionEvent event) {
        result = tableView.getSelectionModel().getSelectedItem();
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
        assert tableView != null : "fx:id=\"tableView\" was not injected: check your FXML file 'databaseDialog.fxml'.";
        assert addButton != null : "fx:id=\"addButton\" was not injected: check your FXML file 'databaseDialog.fxml'.";
        assert confirmButton != null : "fx:id=\"confirmButton\" was not injected: check your FXML file 'databaseDialog.fxml'.";
        operationBox.getItems().addAll("=", ">=", "<=", "<>", ">", "<");
        paramIntBox = new LongField();
        searchButton = new Button("Пошук");
        searchBox.getChildren().removeAll(paramDateBox);
        searchBox.getChildren().add(searchButton);
        searchButton.setOnAction(e -> refresh());
    }

    public void setEdit(boolean isEdit) {
        if (isEdit) {
            addButton.setText("Редагувати");
            addButton.setOnAction(this::editButtonAction);
        } else {
            addButton.setText("Додати");
            addButton.setOnAction(this::addButtonAction);
        }
    }

    public void setView(boolean isView) {
        addButton.setVisible(!isView);
    }

    public Object getResult() {
        return result;
    }

    @SuppressWarnings("unchecked")
    public void refresh() {
        List<Object> collection = null;
        try {
            if (searchBox.isVisible()) {
                String attributeName = attributeBox.getSelectionModel().getSelectedItem().toString();
                Object value = null;
                DatabaseAnalyzer.DBAttribute_impl dbAttribute_impl = dbTable.findAttributeByName(attributeName);
                switch (dbAttribute_impl.type) {
                    case "int4":
                        if (paramIntBox.getValue() != 0)
                            value = (int) paramIntBox.getValue();
                        break;
                    case "char":
                        if (!paramTextBox.getText().isEmpty())
                            value = paramTextBox.getText();
                        break;
                    case "date":
                        value = paramDateBox.getValue();
                        break;
                }
                if (value != null) {
                    collection = analyzer.getRecords(dbTable, attributeName, operationBox.getSelectionModel().getSelectedItem(), value);
                } else {
                    collection = analyzer.getRecords(dbTable);
                }
            } else {
                collection = analyzer.getRecords(dbTable);
            }
            if (filterMap != null) {
                Stream result = collection.stream();
                for (DatabaseAnalyzer.DBAttribute_impl dbAttribute : dbTable.attributeList) {
                    Predicate pr = filterMap.get(dbAttribute.id);
                    if (pr != null) {
                        result = result.filter(o -> {
                            try {
                                Object test = dbAttribute.field.get(o);
                                return pr.test(test);
                            } catch (IllegalAccessException e) {
                                Log.error(Log.coreIndex, e);
                            }
                            return false;
                        });
                    }
                }
                collection = (List<Object>) result.collect(Collectors.toList());
            }
        } catch (SQLException e) {
            Log.error(Log.coreIndex, e);
            isBroken = true;
            if (stage != null)
                stage.close();
        }
        if (collection == null) {
            isBroken = true;
            if (stage != null)
                stage.close();
            return;
        }
        tableView.setItems(FXCollections.observableList((List) collection));
    }

    public void setData(DatabaseAnalyzer analyzer, DatabaseAnalyzer.DBTable_impl dbTable, DatabaseAnalyzer.DBAttribute_impl fk, Collection<DatabaseAnalyzer.DBAttribute_impl> fields) {
        this.analyzer = analyzer;
        this.dbTable = dbTable;
        if (fk != null) {
            parseAttribute(fk);
        }
        if (fields != null) {
            fields.forEach(this::parseAttribute);
        }
        init();
        refresh();
    }

    public void setData(DatabaseAnalyzer analyzer, DatabaseAnalyzer.DBTable_impl dbTable, Collection<DatabaseAnalyzer.DBAttribute_impl> fields) {
        this.analyzer = analyzer;
        this.dbTable = dbTable;
        fields.forEach(this::parseAttribute);
        init();
        refresh();
    }

    public void setData(DatabaseAnalyzer analyzer, DatabaseAnalyzer.DBTable_impl dbTable, Collection<DatabaseAnalyzer.DBAttribute_impl> fields, Map<String, Predicate> filterMap) {
        this.analyzer = analyzer;
        this.dbTable = dbTable;
        fields.forEach(this::parseAttribute);
        this.filterMap = filterMap;
        init();
        refresh();
    }

    public void setData(DatabaseAnalyzer analyzer, DatabaseAnalyzer.DBTable_impl dbTable, DatabaseAnalyzer.DBAttribute_impl... attributes) {
        this.analyzer = analyzer;
        this.dbTable = dbTable;
        for (DatabaseAnalyzer.DBAttribute_impl dbAttribute : attributes) {
            parseAttribute(dbAttribute);
        }
        init();
        refresh();
    }

    @SuppressWarnings("unchecked")
    private void init() {
        attributeBox.getItems().addAll(dbTable.attributeList.stream().filter(attribute -> !attribute.type.equals("bool") && !attribute.isPrimaryKey).map(attribute -> attribute.name).collect(Collectors.toList()));
        attributeBox.getSelectionModel().selectedItemProperty().addListener((value, oldV, newV) -> {
            int size = searchBox.getChildren().size();
            switch (dbTable.findAttributeByName(newV).type) {
                case "int4":
                    searchBox.getChildren().remove(size - 2);
                    searchBox.getChildren().add(size - 2, paramIntBox);
                    break;
                case "date":
                    searchBox.getChildren().remove(size - 2);
                    searchBox.getChildren().add(size - 2, paramDateBox);
                    break;
                case "char":
                    searchBox.getChildren().remove(size - 2);
                    searchBox.getChildren().add(size - 2, paramTextBox);
                    break;
            }
        });
        attributeBox.setConverter(new StringAttributeConverter());
        attributeBox.getSelectionModel().selectFirst();
        operationBox.getSelectionModel().selectFirst();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void blockCancel() {
        cancelButton.setDisable(true);
    }

    public void unblockCancel() {
        cancelButton.setDisable(false);
    }

    @SuppressWarnings("unchecked")
    private void parseAttribute(DatabaseAnalyzer.DBAttribute_impl dbAttribute) {
        if (analyzer.isDomain(dbAttribute.id)) {
            TableColumn tableColumn = new TableColumn<>();
            tableColumn.setText(analyzer.getName(dbAttribute.name));
            tableColumn.setCellValueFactory(new MultiDatabaseDialog_impl_domain(dbAttribute.field, analyzer.getDomain(dbAttribute.id)));
            tableView.getColumns().add(tableColumn);
        } else {
            TableColumn tableColumn = new TableColumn<>();
            tableColumn.setText(analyzer.getName(dbAttribute.name));
            tableColumn.setCellValueFactory(new MultiDatabaseDialog_impl(dbAttribute.field));
            tableView.getColumns().add(tableColumn);
        }
    }

    public boolean isBroken() {
        return isBroken;
    }

    private static class MultiDatabaseDialog_impl implements Callback<TableColumn.CellDataFeatures, ObservableValue> {
        private Field field;

        private MultiDatabaseDialog_impl(Field field) {
            this.field = field;
        }

        @Override
        public ObservableValue call(TableColumn.CellDataFeatures cellDataFeatures) {
            AbstractAnjiProperty property = null;
            try {
                property = (AbstractAnjiProperty) field.get(cellDataFeatures.getValue());
            } catch (IllegalAccessException e) {
                Log.error(Log.coreIndex, e);
            }
            return property;
        }
    }

    private class MultiDatabaseDialog_impl_domain implements Callback<TableColumn.CellDataFeatures, ObservableValue> {
        private Field field;
        private List<String> stringList;

        private MultiDatabaseDialog_impl_domain(Field field, List<String> stringList) {
            this.field = field;
            this.stringList = stringList;
        }

        @Override
        public ObservableValue call(TableColumn.CellDataFeatures cellDataFeatures) {
            ObservableValue value = null;
            try {
                AnjiIntegerProperty property = (AnjiIntegerProperty) field.get(cellDataFeatures.getValue());
                value = new ControlTransformProperty<>(property, new MultiDatabaseDialog_transform(stringList));
            } catch (IllegalAccessException e) {
                Log.error(Log.coreIndex, e);
            }
            return value;
        }
    }

    public void setFilterMap(Map<String, Predicate> filterMap) {
        this.filterMap = filterMap;
        refresh();
    }

    /**
     * Клас переводу з домену у рядок
     * Нагадую, що домени нумеруються з 1
     */
    private static class MultiDatabaseDialog_transform implements ITransform<Integer, String> {
        private List<String> stringList;

        private MultiDatabaseDialog_transform(List<String> stringList) {
            this.stringList = stringList;
        }

        @Override
        public Integer value(String value) {
            return stringList.indexOf(value) + 1;
        }

        @Override
        public String key(Integer value) {
            return stringList.get(value - 1);
        }
    }

    private class StringAttributeConverter extends StringConverter<String> {
        private Map<String, String> reverseName;

        private StringAttributeConverter() {
            reverseName = new HashMap<>();
            for (DatabaseAnalyzer.DBAttribute_impl attribute : dbTable.attributeList) {
                reverseName.put(analyzer.getName(attribute.name), attribute.name);
            }
        }

        @Override
        public String toString(String s) {
            return analyzer.getName(s);
        }

        @Override
        public String fromString(String s) {
            return reverseName.get(s);
        }
    }

    public void setSearchable(boolean searchable) {
        searchBox.setVisible(searchable);
    }
}
