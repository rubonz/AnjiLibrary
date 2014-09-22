package com.darkempire.anjifx.monolog;

import com.darkempire.internal.Cache;
import com.darkempire.internal.anji.LocalHolder;
import com.darkempire.internal.anjifx.ImageCache;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;

import java.util.ResourceBundle;

/**
 * Created by siredvin on 07.12.13.
 */
public class ProgressMonologController<V> {
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // fx:id="actionLabel"
    private Label actionLabel; // Value injected by FXMLLoader

    @FXML // fx:id="cancelButton"
    private Button cancelButton; // Value injected by FXMLLoader

    @FXML // fx:id="imageView"
    private ImageView imageView; // Value injected by FXMLLoader

    @FXML // fx:id="messageLabel"
    private Label messageLabel; // Value injected by FXMLLoader

    @FXML // fx:id="okButton"
    private Button okButton; // Value injected by FXMLLoader

    @FXML // fx:id="progressBar"
    private ProgressBar progressBar; // Value injected by FXMLLoader

    private Task<V> task;
    private ProgressMonolog<V> monolog;
    private V value;
    private boolean autoclose;

    // Handler for Button[fx:id="cancelButton"] onAction
    @FXML
    void cancelAction(ActionEvent event) {
        if (MonologGeneratorPane.showQuestionDialog(LocalHolder.anji_resourceBundle.getString("anjifx.monolog.progress.question"))) {
            task.cancel();
            monolog.close();
        }
    }

    // Handler for Button[fx:id="okButton"] onAction
    @FXML
    void okAction(ActionEvent event) {
        value = task.getValue();
        monolog.close();
    }

    @FXML
        // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert actionLabel != null : "fx:id=\"actionLabel\" was not injected: check your FXML file 'progressDialog.fxml'.";
        assert cancelButton != null : "fx:id=\"cancelButton\" was not injected: check your FXML file 'progressDialog.fxml'.";
        assert imageView != null : "fx:id=\"imageView\" was not injected: check your FXML file 'progressDialog.fxml'.";
        assert messageLabel != null : "fx:id=\"messageLabel\" was not injected: check your FXML file 'progressDialog.fxml'.";
        assert okButton != null : "fx:id=\"okButton\" was not injected: check your FXML file 'progressDialog.fxml'.";
        assert progressBar != null : "fx:id=\"progressBar\" was not injected: check your FXML file 'progressDialog.fxml'.";

        // Initialize your logic here: all @FXML variables will have been injected
        imageView.setImage(Cache.getImage(ImageCache.ImageType.PROGRESS_ICON));
        autoclose = false;
    }

    public void setTask(ProgressMonolog<V> monolog, Task<V> task) {
        this.monolog = monolog;
        this.task = task;
        actionLabel.textProperty().bind(task.messageProperty());
        progressBar.progressProperty().bind(task.progressProperty());
        task.setOnSucceeded(event -> {
            okButton.setDisable(false);
            if (autoclose)
                monolog.close();
        });
    }

    public void start() {
        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }

    public V getValue() {
        return value;
    }

    public void setAutoclose(boolean autoclose) {
        this.autoclose = autoclose;
    }
}
