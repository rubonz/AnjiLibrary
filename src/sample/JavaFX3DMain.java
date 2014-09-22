package sample;

import com.darkempire.anji.log.Log;
import com.darkempire.anji.annotation.AnjiTest;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Line;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Created by siredvin on 30.06.14.
 */
@AnjiTest
public class JavaFX3DMain extends Application {

    public static class Xform extends Group {

        final Rotate rx = new Rotate(0, Rotate.X_AXIS);
        final Rotate ry = new Rotate(0, Rotate.Y_AXIS);
        final Rotate rz = new Rotate(0, Rotate.Z_AXIS);

        public Xform() {
            super();
            getTransforms().addAll(rz, ry, rx);
        }
    }

    public static class Xsphere extends Sphere {

        final Rotate rx = new Rotate(0, Rotate.X_AXIS);
        final Rotate ry = new Rotate(0, Rotate.Y_AXIS);
        final Rotate rz = new Rotate(0, Rotate.Z_AXIS);

        public Xsphere(double size, Color color) {
            super(size);
            setMaterial(new PhongMaterial(color));
            getTransforms().addAll(rz, ry, rx);
        }
    }

    private Timeline animation;

    public Parent createContent() {
        Xform sceneRoot = new Xform();

        Xform cube1X = new Xform();
        Xsphere cube1 = new Xsphere(40, new Color(1.0, 0.9, 0.0, 1.0));

        Xform cube1_1X = new Xform();
        Xform cube1_2X = new Xform();
        Xform cube1_3X = new Xform();
        Xform cube1_4X = new Xform();
        Xform cube1_5X = new Xform();
        Xform cube1_6X = new Xform();
        Xform cube1_7X = new Xform();
        Xform cube1_8X = new Xform();
        Xform cube1_9X = new Xform();

        Xsphere cube1_1 = new Xsphere(4, Color.RED);
        Xsphere cube1_2 = new Xsphere(5, Color.ORANGE);
        Xsphere cube1_3 = new Xsphere(6, Color.CORNFLOWERBLUE);
        Xsphere cube1_4 = new Xsphere(7, Color.DARKGREEN);
        Xsphere cube1_5 = new Xsphere(8, Color.BLUE);
        Xsphere cube1_6 = new Xsphere(9, Color.PURPLE);
        Xsphere cube1_7 = new Xsphere(10, Color.BLUEVIOLET);
        Xsphere cube1_8 = new Xsphere(11, Color.DARKGOLDENROD);
        Xsphere cube1_9 = new Xsphere(12, Color.KHAKI);

        sceneRoot.getChildren().add(cube1X);

        cube1X.getChildren().add(cube1);
        cube1X.getChildren().add(cube1_1X);
        cube1X.getChildren().add(cube1_2X);
        cube1X.getChildren().add(cube1_3X);
        cube1X.getChildren().add(cube1_4X);
        cube1X.getChildren().add(cube1_5X);
        cube1X.getChildren().add(cube1_6X);
        cube1X.getChildren().add(cube1_7X);
        cube1X.getChildren().add(cube1_8X);
        cube1X.getChildren().add(cube1_9X);

        cube1_1X.getChildren().add(cube1_1);
        cube1_2X.getChildren().add(cube1_2);
        cube1_3X.getChildren().add(cube1_3);
        cube1_4X.getChildren().add(cube1_4);
        cube1_5X.getChildren().add(cube1_5);
        cube1_6X.getChildren().add(cube1_6);
        cube1_7X.getChildren().add(cube1_7);
        cube1_8X.getChildren().add(cube1_8);
        cube1_9X.getChildren().add(cube1_9);

        cube1_1.setTranslateX(40.0);
        cube1_2.setTranslateX(60.0);
        cube1_3.setTranslateX(80.0);
        cube1_4.setTranslateX(100.0);
        cube1_5.setTranslateX(120.0);
        cube1_6.setTranslateX(140.0);
        cube1_7.setTranslateX(160.0);
        cube1_8.setTranslateX(180.0);
        cube1_9.setTranslateX(200.0);

        cube1_1X.rx.setAngle(30.0);
        cube1_2X.rz.setAngle(10.0);
        cube1_3X.rz.setAngle(50.0);
        cube1_4X.rz.setAngle(170.0);
        cube1_5X.rz.setAngle(60.0);
        cube1_6X.rz.setAngle(30.0);
        cube1_7X.rz.setAngle(120.0);
        cube1_8X.rz.setAngle(40.0);
        cube1_9X.rz.setAngle(-60.0);

        double endTime = 40000.0;
        Line l = new Line();
        l.setStartX(cube1.getTranslateX());
        l.setStartY(cube1.getTranslateY());
        l.setEndX(cube1_8.getTranslateX());
        l.setEndY(cube1_8.getTranslateY());
        cube1_8.rx.angleProperty().addListener(o -> {
            Point2D temp = cube1_8.localToParent(0, 0);
            Log.log(Log.debugIndex, temp);
            temp = cube1_8X.localToParent(temp);
            Log.log(Log.debugIndex, temp);
            l.setEndX(temp.getX());
            l.setEndY(temp.getY());
            //Log.log(Log.debugIndex,l);
        });
        cube1_8X.ry.angleProperty().addListener(o -> {
            Point2D temp = cube1_8.localToParent(0, 0);
            Log.log(Log.debugIndex, temp);
            temp = cube1_8X.localToParent(temp);
            Log.log(Log.debugIndex, temp);
            l.setEndX(temp.getX());
            l.setEndY(temp.getY());
            //Log.log(Log.debugIndex,l);
        });
        l.setStrokeWidth(5);
        cube1X.getChildren().add(l);
        Log.log(Log.debugIndex, l);
        // Animate
        animation = new Timeline();
        animation.getKeyFrames().addAll(new KeyFrame(Duration.ZERO,
                        new KeyValue(cube1X.ry.angleProperty(), 0.0),
                        new KeyValue(cube1X.rx.angleProperty(), 0.0),
                        new KeyValue(cube1_1X.ry.angleProperty(), 0.0),
                        new KeyValue(cube1_2X.ry.angleProperty(), 0.0),
                        new KeyValue(cube1_3X.ry.angleProperty(), 0.0),
                        new KeyValue(cube1_4X.ry.angleProperty(), 0.0),
                        new KeyValue(cube1_5X.ry.angleProperty(), 0.0),
                        new KeyValue(cube1_6X.ry.angleProperty(), 0.0),
                        new KeyValue(cube1_7X.ry.angleProperty(), 0.0),
                        new KeyValue(cube1_8X.ry.angleProperty(), 0.0),
                        new KeyValue(cube1_9X.ry.angleProperty(), 0.0),
                        new KeyValue(cube1_1.rx.angleProperty(), 0.0),
                        new KeyValue(cube1_2.rx.angleProperty(), 0.0),
                        new KeyValue(cube1_3.rx.angleProperty(), 0.0),
                        new KeyValue(cube1_4.rx.angleProperty(), 0.0),
                        new KeyValue(cube1_5.rx.angleProperty(), 0.0),
                        new KeyValue(cube1_6.rx.angleProperty(), 0.0),
                        new KeyValue(cube1_7.rx.angleProperty(), 0.0),
                        new KeyValue(cube1_8.rx.angleProperty(), 0.0),
                        new KeyValue(cube1_9.rx.angleProperty(), 0.0)),
                new KeyFrame(new Duration(endTime),
                        new KeyValue(cube1X.ry.angleProperty(), 360.0),
                        new KeyValue(cube1X.rx.angleProperty(), 360.0),
                        new KeyValue(cube1_1X.ry.angleProperty(), -2880.0),
                        new KeyValue(cube1_2X.ry.angleProperty(), -1440.0),
                        new KeyValue(cube1_3X.ry.angleProperty(), -1080.0),
                        new KeyValue(cube1_4X.ry.angleProperty(), -720.0),
                        new KeyValue(cube1_5X.ry.angleProperty(), 1440.0),
                        new KeyValue(cube1_6X.ry.angleProperty(), 1080.0),
                        new KeyValue(cube1_7X.ry.angleProperty(), -360.0),
                        new KeyValue(cube1_8X.ry.angleProperty(), -720.0),
                        new KeyValue(cube1_9X.ry.angleProperty(), -1080.0),
                        new KeyValue(cube1_1.rx.angleProperty(), 7200.0),
                        new KeyValue(cube1_2.rx.angleProperty(), -7200.0),
                        new KeyValue(cube1_3.rx.angleProperty(), 7200.0),
                        new KeyValue(cube1_4.rx.angleProperty(), -7200.0),
                        new KeyValue(cube1_5.rx.angleProperty(), 7200.0),
                        new KeyValue(cube1_6.rx.angleProperty(), -7200.0),
                        new KeyValue(cube1_7.rx.angleProperty(), 7200.0),
                        new KeyValue(cube1_8.rx.angleProperty(), -7200.0),
                        new KeyValue(cube1_9.rx.angleProperty(), 7200.0)));
        animation.setDelay(new Duration(5000));
        animation.setCycleCount(Timeline.INDEFINITE);

        PerspectiveCamera camera = new PerspectiveCamera(true);
        camera.setFarClip(1500);
        camera.getTransforms().add(new Translate(0, 0, -900));

        SubScene subScene = new SubScene(sceneRoot, 640, 480, true, SceneAntialiasing.BALANCED);
        subScene.setCamera(camera);

        return new Group(subScene);
    }

    public void play() {
        animation.play();
    }

    @Override
    public void stop() {
        animation.pause();
    }

    @Override
    public void init() {

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setScene(new Scene(createContent(), 640, 480, true, SceneAntialiasing.BALANCED));
        primaryStage.show();
        play();
    }

    /**
     * Java main for when running without JavaFX launcher
     */
    public static void main(String[] args) {
        launch(args);
    }
}
