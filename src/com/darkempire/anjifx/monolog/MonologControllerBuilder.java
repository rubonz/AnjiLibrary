package com.darkempire.anjifx.monolog;

import com.darkempire.anji.log.Log;
import com.darkempire.internal.Cache;
import com.darkempire.internal.anjifx.FXMLCache;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: siredvin
 * Date: 04.11.13
 * Time: 9:52
 * To change this template use File | Settings | File Templates.
 */
public class MonologControllerBuilder {
    private Image image;
    private String messageText;
    private String detailsText;
    private GridPane pane;
    private boolean isResult;

    private MonologControllerBuilder() {
        image = null;
        messageText = "";
        detailsText = "";
        isResult = false;
    }

    public MonologControllerBuilder setMessageText(String text) {
        messageText = text;
        return this;
    }

    public MonologControllerBuilder setDetailsText(String text) {
        detailsText = text;
        return this;
    }

    public MonologControllerBuilder setImage(Image image) {
        this.image = image;
        return this;
    }

    public MonologControllerBuilder setIsResult(boolean isResult) {
        this.isResult = isResult;
        return this;
    }

    public MonologController createMonologControler() {
        FXMLLoader loader = Cache.createFXMLLoader(FXMLCache.FXMLType.MONOLOG_ALERT);
        try {
            pane = loader.load();
        } catch (IOException e) {
            Log.error(Log.coreIndex, e);
        }
        MonologController controller = loader.getController();
        controller.setImage(image);
        controller.setIsResult(isResult);
        controller.setMessage(messageText);
        controller.setDetails(detailsText);
        return controller;
    }

    public GridPane getPane() {
        return pane;
    }

    public static MonologControllerBuilder createBuilder() {
        return new MonologControllerBuilder();
    }
}
