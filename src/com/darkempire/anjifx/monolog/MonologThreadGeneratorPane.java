package com.darkempire.anjifx.monolog;

import com.darkempire.anji.annotation.AnjiUtil;
import com.darkempire.internal.anji.LocalHolder;
import javafx.application.Platform;

/**
 * Created with IntelliJ IDEA.
 * User: siredvin
 * Date: 04.11.13
 * Time: 9:33
 * To change this template use File | Settings | File Templates.
 */
@AnjiUtil
public final class MonologThreadGeneratorPane {

    private static MonologControllerBuilder builder;

    static {
        builder = MonologControllerBuilder.createBuilder();
    }

    private MonologThreadGeneratorPane() {
    }

    public static void showErrorDialog(String title, String message, String details) {
        Platform.runLater(() -> MonologGeneratorPane.showErrorDialog(title, message, details));
    }

    public static void showErrorDialog(String message, String details) {
        showErrorDialog(LocalHolder.anji_resourceBundle.getString("anjifx.monolog.error.title"), message, details);
    }

    public static void showErrorDialog(String details) {
        showErrorDialog(LocalHolder.anji_resourceBundle.getString("anjifx.monolog.error.title"), LocalHolder.anji_resourceBundle.getString("anjifx.monolog.error.message"), details);
    }

    public static void showInfoDialog(String title, String message, String details) {
        Platform.runLater(() -> MonologGeneratorPane.showInfoDialog(title, message, details));
    }

    public static void showInfoDialog(String message, String details) {
        showInfoDialog(LocalHolder.anji_resourceBundle.getString("anjifx.monolog.info.title"), message, details);
    }

    public static void showInfoDialog(String details) {
        showInfoDialog(LocalHolder.anji_resourceBundle.getString("anjifx.monolog.info.title"), LocalHolder.anji_resourceBundle.getString("anjifx.monolog.info.message"), details);
    }

    public static void showAcceptDialog(String title, String message, String details) {
        Platform.runLater(() -> MonologGeneratorPane.showAcceptDialog(title, message, details));
    }

    public static void showAcceptDialog(String message, String details) {
        showAcceptDialog(LocalHolder.anji_resourceBundle.getString("anjifx.monolog.accept.title"), message, details);
    }

    public static void showAcceptDialog(String details) {
        showAcceptDialog(LocalHolder.anji_resourceBundle.getString("anjifx.monolog.accept.title"), LocalHolder.anji_resourceBundle.getString("anjifx.monolog.accept.message"), details);
    }
}
