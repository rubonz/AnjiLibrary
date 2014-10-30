package sample.anjifx;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.VBox;
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
        LineChart chart = new LineChart(new NumberAxis(), new NumberAxis());
        chart.setCreateSymbols(true);
        chart.getData().add(new XYChart.Series<>("Temp1", FXCollections.observableArrayList(new XYChart.Data<Object, Object>(1, 1), new XYChart.Data<Object, Object>(2, 2), new XYChart.Data<Object, Object>(3, 4))));
        vBox.getChildren().add(chart);
        primaryStage.setScene(new Scene(vBox));
        primaryStage.show();
        ((XYChart.Series) chart.getData().get(0)).getData().addAll(new XYChart.Data<>(5, 5));
        ObservableList<XYChart.Data> dataList = ((XYChart.Series) chart.getData().get(0)).getData();
        dataList.forEach(data -> {
            Node node = data.getNode();
            Tooltip tooltip = new Tooltip('(' + data.getXValue().toString() + ';' + data.getYValue().toString() + ')');
            Tooltip.install(node, tooltip);
        });
    }
}
