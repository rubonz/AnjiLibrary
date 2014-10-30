package sample.anjifx;

import com.darkempire.anjifx.scene.chart.AnjiLineChart;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
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
        chart.setCreateSymbols(true);
        chart.getData().add(new XYChart.Series<>("Temp1", FXCollections.observableArrayList(new XYChart.Data<Object, Object>(1, 1), new XYChart.Data<Object, Object>(2, 2), new XYChart.Data<Object, Object>(3, 4))));
        chart.setColor(0, Color.BLANCHEDALMOND);
        vBox.getChildren().add(chart);
        primaryStage.setScene(new Scene(vBox));
        primaryStage.show();
        ((XYChart.Series) chart.getData().get(0)).getData().addAll(new XYChart.Data<>(5, 5));
    }
}
