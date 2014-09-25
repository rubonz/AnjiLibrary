package sample.anjifx;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by siredvin on 25.09.14.
 */
public class TrayIconTest extends Application {
    private String testCss = TrayIconTest.class.getResource("/sample/res/test.css").toExternalForm();

    public static void main(String[] args) {
        TrayIconTest.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setScene(createScene());
        TrayIcon icon = new TrayIcon(Toolkit.getDefaultToolkit().createImage(TrayIconTest.class.getResource("/sample/res/cab_view.png")));
        icon.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                double x = e.getXOnScreen();
                double y = e.getYOnScreen();
                Platform.runLater(() -> {
                    primaryStage.setX(x);
                    primaryStage.setY(y);
                    primaryStage.setAlwaysOnTop(true);
                    primaryStage.show();
                });
            }
        });
        SystemTray.getSystemTray().add(icon);
    }

    public Scene createScene() {
        VBox pane = new VBox();
        pane.setPadding(new Insets(0, 0, 0, 0));
        pane.setAlignment(Pos.CENTER);
        pane.setFillWidth(true);
        MenuButton l1 = new MenuButton("test1"), l2 = new MenuButton("test2"), l3 = new MenuButton("testtest");
        configLabel(l1, 1);
        configLabel(l2, 2);
        configLabel(l3, 3);
        pane.getChildren().addAll(l1, l2, l3);
        return new Scene(pane);
    }

    public void configLabel(MenuButton l, int i) {
        l.setPickOnBounds(true);
        l.setMaxWidth(Double.POSITIVE_INFINITY);
        l.setAlignment(Pos.CENTER);
        l.setPopupSide(Side.LEFT);
        l.getItems().addAll(new MenuItem("1" + i), new MenuItem("2" + i));
        l.setOnMouseEntered(event -> l.show());
    }
}
