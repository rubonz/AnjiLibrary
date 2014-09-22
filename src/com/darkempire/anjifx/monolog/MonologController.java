package com.darkempire.anjifx.monolog;

import com.darkempire.anjifx.beans.property.AnjiBooleanProperty;
import javafx.beans.property.Property;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

/**
 * Created with IntelliJ IDEA.
 * User: siredvin
 * Date: 04.11.13
 * Time: 9:34
 * To change this template use File | Settings | File Templates.
 */
public class MonologController extends GridPane {

    @FXML // fx:id="actionParent"
    private HBox actionParent; // Value injected by FXMLLoader

    @FXML // fx:id="cancelButton"
    private Button cancelButton; // Value injected by FXMLLoader

    @FXML // fx:id="detailsLabel"
    private Label detailsLabel; // Value injected by FXMLLoader

    @FXML // fx:id="imageView"
    private ImageView imageView; // Value injected by FXMLLoader

    @FXML // fx:id="messageLabel"
    private Label messageLabel; // Value injected by FXMLLoader

    @FXML // fx:id="okButton"
    private Button okButton; // Value injected by FXMLLoader

    @FXML // fx:id="okParent"
    private HBox okParent; // Value injected by FXMLLoader

    private Stage stage;
    private boolean result;
    private AnjiBooleanProperty isResultProperty;

    @FXML
        // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert actionParent != null : "fx:id=\"actionParent\" was not injected: check your FXML file 'alertDialog.fxml'.";
        assert cancelButton != null : "fx:id=\"cancelButton\" was not injected: check your FXML file 'alertDialog.fxml'.";
        assert detailsLabel != null : "fx:id=\"detailsLabel\" was not injected: check your FXML file 'alertDialog.fxml'.";
        assert imageView != null : "fx:id=\"imageView\" was not injected: check your FXML file 'alertDialog.fxml'.";
        assert messageLabel != null : "fx:id=\"messageLabel\" was not injected: check your FXML file 'alertDialog.fxml'.";
        assert okButton != null : "fx:id=\"okButton\" was not injected: check your FXML file 'alertDialog.fxml'.";
        assert okParent != null : "fx:id=\"okParent\" was not injected: check your FXML file 'alertDialog.fxml'.";

        // Initialize your logic here: all @FXML variables will have been injected
        isResultProperty = new AnjiBooleanProperty("isResult", true);
        cancelButton.visibleProperty().bind(isResultProperty);
    }

    // Handler for Button[fx:id="cancelButton"] onAction

    @FXML
    private void cancelAction(ActionEvent event) {
        result = false;
        stage.close();
    }

    // Handler for Button[fx:id="okButton"] onAction
    @FXML
    private void okAction(ActionEvent event) {
        result = true;
        stage.close();
    }

    public void setImage(Image image) {
        imageView.setImage(image);
    }

    public void setIsResult(boolean result) {
        isResultProperty.setValue(result);
    }

    public boolean isResult() {
        return isResultProperty.getValue();
    }

    public Property<Boolean> isResultProperty() {
        return isResultProperty;
    }

    public boolean getResult() {
        return result;
    }

    public void setMessage(String text) {
        messageLabel.setText(text);
    }

    public void setDetails(String text) {
        detailsLabel.setText(text);
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void cancelButtonDefault() {
        cancelButton.setDefaultButton(true);
    }

    public void okButtonDefault() {
        okButton.setDefaultButton(true);
    }
}