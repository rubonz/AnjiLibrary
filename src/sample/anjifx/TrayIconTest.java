package sample.anjifx;

import com.darkempire.anji.log.Log;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
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
        Stage stage1 = new Stage();
        primaryStage.initStyle(StageStyle.UNDECORATED);
        ContextMenu menu = createMenu();
        Platform.setImplicitExit(false);
        Label l1 = new Label();
        VBox tempBox = new VBox();
        l1.setContextMenu(menu);
        tempBox.getChildren().add(l1);
        Scene value = new Scene(tempBox);
        stage1.setScene(value);
        stage1.setMaxWidth(0);
        stage1.setMaxHeight(0);
        stage1.setResizable(false);
        TrayIcon icon = new TrayIcon(Toolkit.getDefaultToolkit().createImage(TrayIconTest.class.getResource("/sample/res/cab_view.png")));
        icon.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                double x = e.getXOnScreen();
                double y = e.getYOnScreen();
                Platform.runLater(() -> {
                    menu.show(tempBox, x, y);
                });
            }
        });
        stage1.initStyle(StageStyle.TRANSPARENT);
        l1.setOpacity(0);
        stage1.setOpacity(0);
        tempBox.setOpacity(0);
        stage1.setX(-2);
        stage1.setY(-2);
        stage1.initOwner(primaryStage);
        primaryStage.show();
        primaryStage.hide();
        stage1.show();
        SystemTray.getSystemTray().add(icon);
    }

    public ContextMenu createMenu() {
        ContextMenu menu = new ContextMenu(new MenuItem("1"), new MenuItem("2"));
        //menu;
        return menu;
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
