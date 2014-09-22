package com.darkempire.anjifx.dialog.database;

import com.darkempire.anji.log.Log;
import com.darkempire.anji.database.DatabaseAnalyzer;
import com.darkempire.anji.util.AnjiDateUtil;
import com.darkempire.anjifx.beans.property.*;
import com.darkempire.anjifx.beans.property.transform.ITransform;
import com.darkempire.anjifx.beans.property.transform.Transform;
import com.darkempire.anjifx.beans.wrapper.AnjiWrapper;
import com.darkempire.anjifx.monolog.MonologGeneratorPane;
import com.darkempire.anjifx.scene.BindingButton;
import com.darkempire.anjifx.scene.LongField;
import javafx.beans.property.ReadOnlyProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.net.URL;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA.
 * User: siredvin
 * Date: 20.09.13
 * Time: 13:33
 * To change this template use File | Settings | File Templates.
 */
public class DatabaseRecordEditController extends GridPane implements Initializable {
    @FXML
    private VBox propertiesPane;
    private Stage stage;
    private Collection<Control> anullableList;
    private Map<Control, String> fkEditorList;
    private DatabaseAnalyzer analyzer;
    private List<DatabaseAnalyzer.DefaultChoose> defaultChooses;
    private boolean result;
    private Set<AnjiWrapper> decoratorSet;
    private Collection<AbstractAnjiProperty> content;
    private Map<String, Predicate> filterMap;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        assert propertiesPane != null;
        result = false;
        filterMap = new HashMap<>();
        //To change body of implemented methods use File | Settings | File Templates.
    }

    protected final void setControl(List<DatabaseAnalyzer.DBAttribute_impl> contents, DatabaseAnalyzer analyzer, Object o) {
        if (decoratorSet != null)
            throw new RuntimeException("Content already set");
        decoratorSet = new HashSet<>();
        this.anullableList = new ArrayList<>();
        this.analyzer = analyzer;
        this.fkEditorList = new HashMap<>();
        this.content = new ArrayList<>();
        this.defaultChooses = analyzer.getDefaultSelect(contents.get(0).table_id);
        propertiesPane.getChildren().addAll(contents.stream().filter(dbAttribute -> !dbAttribute.isPrimaryKey).map(s -> {
            try {
                return parseControl(s, o);
            } catch (IllegalAccessException e) {
                Log.error(Log.coreIndex, e);
            }
            return null;
        }).filter(hbox -> hbox != null).collect(Collectors.toList()));
    }

    protected final void setControl(DatabaseAnalyzer analyzer, DatabaseRecordEditDialog.PackedObject... packedObjects) {
        if (decoratorSet != null)
            throw new RuntimeException("Content already set");
        decoratorSet = new HashSet<>();
        this.anullableList = new ArrayList<>();
        this.analyzer = analyzer;
        this.fkEditorList = new HashMap<>();
        this.content = new ArrayList<>();
        this.defaultChooses = new ArrayList<>();
        for (DatabaseRecordEditDialog.PackedObject object : packedObjects) {
            Collection<DatabaseAnalyzer.DefaultChoose> collection = analyzer.getDefaultSelect(object.attributes.get(0).table_id);
            if (collection != null)
                defaultChooses.addAll(collection);
        }
        for (DatabaseRecordEditDialog.PackedObject o : packedObjects) {
            propertiesPane.getChildren().addAll(o.attributes.stream().filter(dbAttribute -> !dbAttribute.isPrimaryKey).map(s -> {
                try {
                    return parseControl(s, o.object);
                } catch (IllegalAccessException e) {
                    Log.error(Log.coreIndex, e);
                }
                return null;
            }).filter(hbox -> hbox != null).collect(Collectors.toList()));
        }
    }

    protected HBox parseControl(DatabaseAnalyzer.DBAttribute_impl dbAttribute, Object o) throws IllegalAccessException {
        AbstractAnjiProperty c = (AbstractAnjiProperty) dbAttribute.field.get(o);
        content.add(c);
        Label l = new Label(analyzer.getName(c.getName()));
        Control field = null;
        BindingButton selectButton = null;
        switch (c.getType()) {
            case STRING_TYPE:
                TextField textField = new TextField(c.getValue().toString());
                field = textField;
                if (dbAttribute.fk_id != null) {
                    //textField.focusedProperty().addListener(this::onTextFieldFocusAction);
                    selectButton = new BindingButton(field, "...");
                    selectButton.setOnAction(this::onTextButtonClick);
                    textField.setEditable(false);
                    fkEditorList.put(textField, dbAttribute.fk_id);
                    l.setTextFill(Color.RED);
                }
                if (!dbAttribute.isNullable) {
                    anullableList.add(textField);
                }
                AnjiStringProperty stringBean = (AnjiStringProperty) c;
                decoratorSet.add(AnjiWrapper.wrap(stringBean));
                stringBean.bind(textField.textProperty());
                break;
            case BOOLEAN_TYPE://Зовнішній ключ виникає лише у випадку нескінченної упоротості. Дійсно
                AnjiBooleanProperty booleanBean = (AnjiBooleanProperty) c;
                CheckBox booleanTextFieldEditor = new CheckBox();
                booleanTextFieldEditor.setSelected(booleanBean.getValue());
                decoratorSet.add(AnjiWrapper.wrap(booleanBean));
                field = booleanTextFieldEditor;
                booleanBean.bind(booleanTextFieldEditor.selectedProperty());
                break;
            case INT_TYPE:
                AnjiIntegerProperty integerProperty = (AnjiIntegerProperty) c;
                List<String> domain = analyzer.getDomain(dbAttribute.id);
                if (domain != null) {
                    DatabaseAnalyzer.DefaultChoose defaultChoose = null;
                    if (defaultChooses != null) {
                        defaultChooses.stream().filter(dc -> dc.attributeWithDomain == dbAttribute).findFirst().get();
                    }
                    if (defaultChoose != null) {
                        integerProperty.setValue(defaultChoose.defaultChoose);
                        return null;
                    } else {
                        ComboBox<String> comboBox = new ComboBox<>(FXCollections.observableArrayList(domain));
                        decoratorSet.add(AnjiWrapper.wrap(integerProperty));
                        comboBox.getSelectionModel().selectFirst();
                        comboBox.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> integerProperty.setValue(newValue.intValue() + 1));
                        field = comboBox;
                    }
                } else {
                    LongField longField = new LongField();
                    longField.setValue(integerProperty.getValue().longValue());
                    field = longField;
                    if (dbAttribute.fk_id != null) {
                        // longField.focusedProperty().addListener(this::onLongFieldFocusAction);
                        longField.setEditable(false);
                        selectButton = new BindingButton(field, "...");
                        selectButton.setOnAction(this::onLongButtonClick);
                        fkEditorList.put(longField, dbAttribute.fk_id);
                        l.setTextFill(Color.RED);
                    }
                    if (!dbAttribute.isNullable) {
                        anullableList.add(longField);
                    }
                    decoratorSet.add(AnjiWrapper.wrap(integerProperty));
                    integerProperty.bind(Transform.transformLongToInt(longField.valueProperty()));
                }
                break;
            case SQLDATE_TYPE://Зовнішній ключ виникає лише у випадку нескінченної упоротості. Дійсно
                AnjiSQLDateProperty sqlDateProperty = (AnjiSQLDateProperty) c;
                DatePicker datePicker = new DatePicker();
                datePicker.setValue(AnjiDateUtil.convertDateToLocalDate(sqlDateProperty.getValue()));
                field = datePicker;
                decoratorSet.add(AnjiWrapper.wrap(sqlDateProperty));
                sqlDateProperty.bind(Transform.transformDateToSQLDate(datePicker.valueProperty()));
                break;
            case DOUBLE_TYPE:
            case RADIALGRADIENT_TYPE:
            case LINEARGRADIEN_TYPE:
            case CHOOSE_TYPE:
            case LIST_TYPE:
            case MAP_TYPE:
            case TREE_TYPE:
            case OBJECT_TYPE:
            case FRACTION_TYPE:
            case LONGFRACTION_TYPE:
            case BIGFRACTION_TYPE:
            case FUZZYBOOL_TYPE:
            case POINT3D_TYPE:
            case VECTOR3D_TYPE:
            case COMPLEX_TYPE:
            case FIXEDPOINT_TYPE:
            case SET_TYPE:
            case DOUBLEVECTOR_TYPE:
            case POINT2D_TYPE:
            case VECTOR2D_TYPE:
            case COLOR_TYPE:
            case LONG_TYPE:
            case FLOAT_TYPE:
            case ENUM_TYPE:
            case LONG_BOUNDED_TYPE:
            case DOUBLE_BOUNDED_TYPE:
            case DATE_TYPE:
                throw new UnsupportedOperationException(c.getType().toString());
        }
        HBox hBox = new HBox();
        hBox.setPadding(new Insets(5));
        hBox.setSpacing(5);
        hBox.getChildren().add(l);
        HBox.setHgrow(l, Priority.ALWAYS);
        hBox.getChildren().add(field);
        HBox.setHgrow(field, Priority.ALWAYS);
        hBox.setAlignment(Pos.CENTER_LEFT);
        if (selectButton != null) {
            hBox.getChildren().add(selectButton);
            selectButton = null;
        }
        return hBox;
        //propertiesPane.getChildren().add(hBox);
    }

    public void setStage(Stage stage) {
        this.stage = stage;
        stage.setOnShown(windowEvent -> stage.sizeToScene());
    }

    @FXML
    private void onAddAction(ActionEvent event) {
        if (!checkFill()) {
            MonologGeneratorPane.showErrorDialog("Неправильні дані", "Один або кілька зовнішніх ключів не вибрано");
            return;
        }
        for (AnjiWrapper decorator : decoratorSet) {
            decorator.free();
        }
        decoratorSet.clear();
        result = true;
        stage.close();
    }

    @FXML
    private void onCancelAction(ActionEvent event) {
        for (AnjiWrapper decorator : decoratorSet) {
            decorator.backup();
        }
        decoratorSet.clear();
        stage.close();
    }

    private boolean checkFill() {
        for (Control c : anullableList) {
            if (c instanceof LongField) {
                if (((LongField) c).getValue() < 1)
                    return false;
            }
            if (c instanceof TextField) {
                if (((TextField) c).getText().isEmpty())
                    return false;
            }
        }
        return true;
    }

    @SuppressWarnings("unchecked")
    private void onTextFieldFocusAction(ObservableValue<? extends Boolean> observableValue, Boolean oldValue, Boolean newValue) {
        if (newValue) {
            TextField field = ((TextField) ((ReadOnlyProperty<Boolean>) observableValue).getBean());
            if (field.getText().isEmpty()) {
                DatabaseAnalyzer.DBAttribute_impl dbAttribute = analyzer.getAttributeById(fkEditorList.get(field));
                Object o = createMultiDatabaseDialog(dbAttribute);
                if (o != null) {
                    try {
                        field.setText(dbAttribute.field.get(o).toString());
                    } catch (IllegalAccessException e) {
                        Log.error(Log.coreIndex, e);
                    }
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    private void onLongFieldFocusAction(ObservableValue<? extends Boolean> observableValue, Boolean oldValue, Boolean newValue) {
        if (newValue) {
            LongField field = ((LongField) ((ReadOnlyProperty<Boolean>) observableValue).getBean());
            if (field.getValue() < 1) {
                DatabaseAnalyzer.DBAttribute_impl dbAttribute = analyzer.getAttributeById(fkEditorList.get(field));
                Object o = createMultiDatabaseDialog(dbAttribute);
                if (o != null) {
                    try {
                        field.setValue(((Number) ((AbstractAnjiProperty) dbAttribute.field.get(o)).getValue()).longValue());
                    } catch (IllegalAccessException e) {
                        Log.error(Log.coreIndex, e);
                    }
                }
            }
        }
    }

    private void onTextButtonClick(ActionEvent event) {
        TextField field = (TextField) ((BindingButton) event.getSource()).getBean();
        DatabaseAnalyzer.DBAttribute_impl dbAttribute = analyzer.getAttributeById(fkEditorList.get(field));
        Object o = createMultiDatabaseDialog(dbAttribute);
        if (o != null) {
            try {
                field.setText(dbAttribute.field.get(o).toString());
            } catch (IllegalAccessException e) {
                Log.error(Log.coreIndex, e);
            }
        }
    }

    private void onLongButtonClick(ActionEvent event) {
        LongField field = (LongField) ((BindingButton) event.getSource()).getBean();
        DatabaseAnalyzer.DBAttribute_impl dbAttribute = analyzer.getAttributeById(fkEditorList.get(field));
        Object o = createMultiDatabaseDialog(dbAttribute);
        if (o != null) {
            try {
                field.setValue(((Number) ((AbstractAnjiProperty) dbAttribute.field.get(o)).getValue()).longValue());
            } catch (IllegalAccessException e) {
                Log.error(Log.coreIndex, e);
            }
        }
    }

    private Object createMultiDatabaseDialog(DatabaseAnalyzer.DBAttribute_impl dbAttribute) {
        String title = "Оберіть запис з таблиці " + analyzer.getName(analyzer.getTable(dbAttribute.table_id).name);
        DatabaseAnalyzer.DBTable_impl dbTable = analyzer.getTable(dbAttribute.table_id);
        List<DatabaseAnalyzer.DBAttribute_impl> fkFields = analyzer.getFKFieldS(dbAttribute);
        MultiDatabaseDialog multiDatabaseDialog = new MultiDatabaseDialog(title, analyzer, dbTable, fkFields, filterMap);
        multiDatabaseDialog.show();
        return multiDatabaseDialog.getResult();
    }

    public void addFilter(String id, Predicate filter) {
        filterMap.put(id, filter);
    }

    public boolean getResult() {
        return result;
    }

    public Collection<AbstractAnjiProperty> getContent() {
        return content;
    }

    private class TemperantTransform implements ITransform<String, Integer> {
        private List<String> collection;

        private TemperantTransform(List<String> collection) {
            this.collection = collection;
        }

        @Override
        public String value(Integer value) {
            return collection.get(value);
        }

        @Override
        public Integer key(String value) {
            return collection.indexOf(value);
        }
    }
}
