package com.darkempire.anjifx.dialog.property;

import com.darkempire.anji.util.AnjiDateUtil;
import com.darkempire.anjifx.beans.editor.Point2DFieldEditor;
import com.darkempire.anjifx.beans.editor.Vector2DFieldEditor;
import com.darkempire.anjifx.beans.property.*;
import com.darkempire.anjifx.beans.property.transform.Transform;
import com.darkempire.anjifx.beans.wrapper.AnjiWrapper;
import com.darkempire.anjifx.scene.DoubleField;
import com.darkempire.anjifx.scene.DoubleSlider;
import com.darkempire.anjifx.scene.LongField;
import com.darkempire.anjifx.scene.LongSlider;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: siredvin
 * Date: 20.09.13
 * Time: 13:33
 * To change this template use File | Settings | File Templates.
 */
public class PropertyEditController extends GridPane implements Initializable {
    @FXML
    private VBox propertiesPane;
    private Stage stage;
    private Set<AnjiWrapper> decoratorSet;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        assert propertiesPane != null;

        //To change body of implemented methods use File | Settings | File Templates.
    }

    protected final void setControl(Iterable<AbstractAnjiProperty> contents) {
        if (decoratorSet != null)
            throw new RuntimeException("Content already set");
        decoratorSet = new HashSet<>();
        for (AbstractAnjiProperty c : contents) {
            parseControl(c);
        }
    }

    protected final void setControl(AbstractAnjiProperty... contents) {
        if (decoratorSet != null)
            throw new RuntimeException("Content already set");
        decoratorSet = new HashSet<>();
        for (AbstractAnjiProperty c : contents) {
            parseControl(c);
        }
    }

    @SuppressWarnings("unchecked")
    protected void parseControl(AbstractAnjiProperty c) {
        Label l = new Label(c.getName());
        Node field = null;
        switch (c.getType()) {
            case STRING_TYPE:
                TextField textField = new TextField(c.getValue().toString());
                field = textField;
                AnjiStringProperty stringBean = (AnjiStringProperty) c;
                decoratorSet.add(AnjiWrapper.wrap(stringBean));
                stringBean.bind(textField.textProperty());
                break;
            case CHOOSE_TYPE:
                AnjiChooseStringProperty nameBean = (AnjiChooseStringProperty) c;
                ComboBox<String> comboBox = new ComboBox<>(FXCollections.observableArrayList(nameBean.getSet()));
                comboBox.getSelectionModel().selectFirst();
                decoratorSet.add(AnjiWrapper.wrap(nameBean));
                nameBean.bind(comboBox.valueProperty());
                field = comboBox;
                break;
            case FLOAT_TYPE:
                AnjiFloatProperty floatProperty = (AnjiFloatProperty) c;
                DoubleField text = new DoubleField(floatProperty.getValue());
                field = text;
                decoratorSet.add(AnjiWrapper.wrap(floatProperty));
                floatProperty.bind(Transform.transformDoubleToFloat(text.valueProperty()));
                break;
            case DOUBLE_TYPE:
                AnjiDoubleProperty doubleBean = (AnjiDoubleProperty) c;
                text = new DoubleField(doubleBean.getValue());
                field = text;
                decoratorSet.add(AnjiWrapper.wrap(doubleBean));
                doubleBean.bind(text.valueProperty());
                break;
            case BOOLEAN_TYPE:
                AnjiBooleanProperty booleanBean = (AnjiBooleanProperty) c;
                CheckBox booleanTextFieldEditor = new CheckBox();
                booleanTextFieldEditor.setSelected(booleanBean.getValue());
                decoratorSet.add(AnjiWrapper.wrap(booleanBean));
                field = booleanTextFieldEditor;
                booleanBean.bind(booleanTextFieldEditor.selectedProperty());
                break;
            case INT_TYPE:
                AnjiIntegerProperty integerProperty = (AnjiIntegerProperty) c;
                LongField longField = new LongField();
                longField.setValue(integerProperty.getValue().longValue());
                field = longField;
                decoratorSet.add(AnjiWrapper.wrap(integerProperty));
                integerProperty.bind(Transform.transformLongToInt(longField.valueProperty()));
                break;
            case LONG_TYPE:
                AnjiLongProperty longProperty = (AnjiLongProperty) c;
                longField = new LongField();
                longField.setValue(longProperty.getValue());
                field = longField;
                decoratorSet.add(AnjiWrapper.wrap(longProperty));
                longProperty.bind(longField.valueProperty());
                break;
            case POINT2D_TYPE:
                AnjiPoint2DProperty point2DBean = (AnjiPoint2DProperty) c;
                Point2DFieldEditor point2DFieldEditor = new Point2DFieldEditor(point2DBean.getValue());
                field = point2DFieldEditor;
                decoratorSet.add(AnjiWrapper.wrap(point2DBean));
                point2DBean.bind(point2DFieldEditor.valueProperty());
                break;
            case VECTOR2D_TYPE:
                AnjiVector2DProperty vector2DBean = (AnjiVector2DProperty) c;
                Vector2DFieldEditor vector2DFieldEditor = new Vector2DFieldEditor(vector2DBean.getValue());
                field = vector2DFieldEditor;
                decoratorSet.add(AnjiWrapper.wrap(vector2DBean));
                vector2DBean.bind(vector2DFieldEditor.valueProperty());
                break;
            case ENUM_TYPE:
                AnjiEnumProperty enumProperty = (AnjiEnumProperty) c;
                ComboBox enumComboBoxEditor = new ComboBox(FXCollections.observableArrayList(enumProperty));
                field = enumComboBoxEditor;
                decoratorSet.add(AnjiWrapper.wrap(enumProperty));
                enumProperty.bind(enumComboBoxEditor.valueProperty());
                break;
            case COLOR_TYPE:
                AnjiColorProperty colorProperty = (AnjiColorProperty) c;
                ColorPicker colorPicker = new ColorPicker(colorProperty.getValue());
                field = colorPicker;
                decoratorSet.add(AnjiWrapper.wrap(colorProperty));
                colorProperty.bind(colorPicker.valueProperty());
                break;
            case LONG_BOUNDED_TYPE:
                AnjiBoundedLongProperty boundedIntegerProperty = (AnjiBoundedLongProperty) c;
                LongSlider longSlider = new LongSlider(boundedIntegerProperty.getMinValue(), boundedIntegerProperty.getMaxValue(), boundedIntegerProperty.get());
                Label watchLabel = new Label();
                watchLabel.textProperty().bind(Bindings.convert(longSlider.longProperty()));
                HBox hBox = new HBox();
                hBox.setSpacing(5);
                hBox.getChildren().addAll(longSlider, watchLabel);
                field = hBox;
                decoratorSet.add(AnjiWrapper.wrap(boundedIntegerProperty));
                boundedIntegerProperty.bind(longSlider.longProperty());
                break;
            case DOUBLE_BOUNDED_TYPE:
                AnjiBoundedDoubleProperty boundedDoubleProperty = (AnjiBoundedDoubleProperty) c;
                DoubleSlider doubleSlider = new DoubleSlider(boundedDoubleProperty.getMinValue(), boundedDoubleProperty.getMaxValue(), boundedDoubleProperty.get());
                watchLabel = new Label();
                watchLabel.textProperty().bind(Bindings.convert(doubleSlider.doubleProperty()));
                hBox = new HBox();
                hBox.setSpacing(5);
                hBox.getChildren().addAll(doubleSlider, watchLabel);
                field = hBox;
                decoratorSet.add(AnjiWrapper.wrap(boundedDoubleProperty));
                boundedDoubleProperty.bind(doubleSlider.doubleProperty());
                break;
            case DATE_TYPE:
                AnjiDateProperty dateProperty = (AnjiDateProperty) c;
                DatePicker datePicker = new DatePicker();
                decoratorSet.add(AnjiWrapper.wrap(dateProperty));
                dateProperty.bind(datePicker.valueProperty());
                field = datePicker;
                break;
            case SQLDATE_TYPE:
                AnjiSQLDateProperty sqlDateProperty = (AnjiSQLDateProperty) c;
                datePicker = new DatePicker();
                datePicker.setValue(AnjiDateUtil.convertDateToLocalDate(sqlDateProperty.getValue()));
                field = datePicker;
                decoratorSet.add(AnjiWrapper.wrap(sqlDateProperty));
                sqlDateProperty.bind(Transform.transformDateToSQLDate(datePicker.valueProperty()));
                break;
            case RADIALGRADIENT_TYPE:
            case LINEARGRADIEN_TYPE:
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
                throw new UnsupportedOperationException(c.getType().toString());
        }
        HBox hBox = new HBox();
        hBox.setPadding(new Insets(5));
        hBox.setSpacing(5);
        hBox.getChildren().add(l);
        HBox.setHgrow(l, Priority.ALWAYS);
        hBox.getChildren().add(field);
        HBox.setHgrow(field, Priority.ALWAYS);
        hBox.setAlignment(Pos.CENTER);
        propertiesPane.getChildren().add(hBox);
    }

    public void setStage(Stage stage) {
        this.stage = stage;
        stage.setOnShown(windowEvent -> stage.sizeToScene());
    }

    @FXML
    private void onAddAction(ActionEvent event) {
        for (AnjiWrapper decorator : decoratorSet) {
            decorator.free();
        }
        decoratorSet.clear();
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
}
