package com.darkempire.anjifx.monolog;

import com.darkempire.internal.Cache;
import com.darkempire.internal.anji.LocalHolder;
import com.darkempire.anji.annotation.AnjiUtil;
import com.darkempire.internal.anjifx.ImageCache;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Created with IntelliJ IDEA.
 * User: siredvin
 * Date: 04.11.13
 * Time: 9:33
 * To change this template use File | Settings | File Templates.
 */
@AnjiUtil
public final class MonologGeneratorPane {
    private static MonologControllerBuilder builder;

    static {
        builder = MonologControllerBuilder.createBuilder();
    }

    public static boolean showQuestionDialog(String title, String message, String details) {
        builder.setImage(Cache.getImage(ImageCache.ImageType.MONOLOG_QUESTION)).setDetailsText(details).setMessageText(message).setIsResult(true);
        MonologController controller = builder.createMonologControler();
        Scene test = new Scene(builder.getPane());
        Stage stage = new Stage();
        stage.setScene(test);
        stage.setTitle(title);
        stage.initModality(Modality.APPLICATION_MODAL);
        controller.setStage(stage);
        controller.cancelButtonDefault();
        stage.showAndWait();
        return controller.getResult();
    }

    public static boolean showQuestionDialog(String message, String details) {
        return showQuestionDialog(LocalHolder.anji_resourceBundle.getString("anjifx.monolog.question.title"), message, details);
    }

    public static boolean showQuestionDialog(String details) {
        return showQuestionDialog(LocalHolder.anji_resourceBundle.getString("anjifx.monolog.question.title"), LocalHolder.anji_resourceBundle.getString("anjifx.monolog.question.message"), details);
    }

    public static void showErrorDialog(String title, String message, String details) {
        builder.setImage(Cache.getImage(ImageCache.ImageType.MONOLOG_ERROR)).setDetailsText(details).setMessageText(message).setIsResult(false);
        MonologController controller = builder.createMonologControler();
        Scene test = new Scene(builder.getPane());
        Stage stage = new Stage();
        stage.setScene(test);
        stage.setTitle(title);
        controller.setStage(stage);
        controller.okButtonDefault();
        stage.showAndWait();
    }

    public static void showErrorDialog(String message, String details) {
        showErrorDialog(LocalHolder.anji_resourceBundle.getString("anjifx.monolog.error.title"), message, details);
    }

    public static void showErrorDialog(String details) {
        showErrorDialog(LocalHolder.anji_resourceBundle.getString("anjifx.monolog.error.title"), LocalHolder.anji_resourceBundle.getString("anjifx.monolog.error.message"), details);
    }

    public static void showInfoDialog(String title, String message, String details) {
        builder.setImage(Cache.getImage(ImageCache.ImageType.MONOLOG_INFO)).setDetailsText(details).setMessageText(message).setIsResult(false);
        MonologController controller = builder.createMonologControler();
        Scene test = new Scene(builder.getPane());
        Stage stage = new Stage();
        stage.setScene(test);
        stage.setTitle(title);
        stage.initModality(Modality.APPLICATION_MODAL);
        controller.setStage(stage);
        controller.okButtonDefault();
        stage.showAndWait();
    }

    public static void showInfoDialog(String message, String details) {
        showInfoDialog(LocalHolder.anji_resourceBundle.getString("anjifx.monolog.info.title"), message, details);
    }

    public static void showInfoDialog(String details) {
        showInfoDialog(LocalHolder.anji_resourceBundle.getString("anjifx.monolog.info.title"), LocalHolder.anji_resourceBundle.getString("anjifx.monolog.info.message"), details);
    }

    public static void showAcceptDialog(String title, String message, String details) {
        builder.setImage(Cache.getImage(ImageCache.ImageType.MONOLOG_ACCEPT)).setDetailsText(details).setMessageText(message).setIsResult(false);
        MonologController controller = builder.createMonologControler();
        Scene test = new Scene(builder.getPane());
        Stage stage = new Stage();
        stage.setScene(test);
        stage.setTitle(title);
        stage.initModality(Modality.APPLICATION_MODAL);
        controller.setStage(stage);
        controller.okButtonDefault();
        stage.showAndWait();
    }

    public static void showAcceptDialog(String message, String details) {
        showAcceptDialog(LocalHolder.anji_resourceBundle.getString("anjifx.monolog.accept.title"), message, details);
    }

    public static void showAcceptDialog(String details) {
        showAcceptDialog(LocalHolder.anji_resourceBundle.getString("anjifx.monolog.accept.title"), LocalHolder.anji_resourceBundle.getString("anjifx.monolog.accept.message"), details);
    }

    public static <V> Task<V> showProgressDialog(Task<V> task) {
        return showProgressDialog(task, false);
    }

    public static <V> Task<V> showProgressDialog(Task<V> task, boolean autoclose) {
        ProgressMonolog<V> progressMonolog = new ProgressMonolog<>(task);
        progressMonolog.setAutoclose(autoclose);
        progressMonolog.showRun();
        return task;
    }
}
