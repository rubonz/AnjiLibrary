package sample.anjifx;

import com.darkempire.anjifx.scene.chart.AnjiLineChart;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * Created by siredvin on 24.09.14.
 */
public class LongFieldTest extends Application {

    public static void main(String[] args) {
        LongFieldTest.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        VBox vBox = new VBox();
        AnjiLineChart chart = new AnjiLineChart(new NumberAxis(), new NumberAxis());
        chart.setBackgroundColor(Color.AQUA);
        vBox.getChildren().add(chart);
        primaryStage.setScene(new Scene(vBox));
        primaryStage.show();
    }
}
