package com.darkempire.anjifx.scene;

import com.darkempire.anji.annotation.AnjiUtil;
import com.darkempire.anjifx.beans.property.AnjiStringProperty;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

/**
 * Create in 15:41
 * Created by siredvin on 16.12.13.
 */
@AnjiUtil
public final class AnjiComponentUtil {

    private AnjiComponentUtil() {
    }

    public static VBox createRoundedBox(AnjiStringProperty title, Region region) {
        VBox test = new VBox();
        test.getStyleClass().addAll("bounded-group-round");
        Label label = new Label();
        label.textProperty().bind(title);
        label.getStyleClass().add("propLabel");
        test.getChildren().addAll(label, region);
        return test;
    }

    public static void loadCss(Scene scene) {
        scene.getStylesheets().add("/com/darkempire/res/css/group.css");
    }
}
