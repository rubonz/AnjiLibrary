package com.darkempire.anjifx.util;

import com.darkempire.internal.anji.LocalHolder;
import com.darkempire.anji.annotation.AnjiUtil;
import com.darkempire.anjifx.scene.AnjiLineChart;
import com.darkempire.math.struct.geometry.geomerty2d.Point2D;
import com.darkempire.math.struct.result.Graphics2DResult;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;

/**
 * Created with IntelliJ IDEA.
 * User: siredvin
 * Date: 11.11.13
 * Time: 9:06
 * To change this template use File | Settings | File Templates.
 */
@AnjiUtil
public final class AnjiFXMathConverter {
    private AnjiFXMathConverter() {
    }

    public static AnjiLineChart createChart(String xAxisName, String yAxisName) {
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel(yAxisName);
        NumberAxis xAxis = new NumberAxis();
        xAxis.setLabel(xAxisName);
        return new AnjiLineChart(xAxis, yAxis);
    }

    public static AnjiLineChart addGraphics(AnjiLineChart chart, Graphics2DResult result) {
        ObservableList<XYChart.Data<Double, Double>> list = FXCollections.observableArrayList();
        for (Point2D point : result) {
            double x = point.getX();
            double y = point.getY();
            list.add(new XYChart.Data<>(x, y));
        }
        chart.getData().add(new LineChart.Series<>(result.getName(), list));
        return chart;
    }

    public static AnjiLineChart getGraphics(Iterable<Graphics2DResult> results) {
        AnjiLineChart anjiLineChart = createChart(LocalHolder.anji_resourceBundle.getString("anjifx.scene.charts.xaxis"), LocalHolder.anji_resourceBundle.getString("anjifx.scene.charts.yaxis"));
        for (Graphics2DResult result : results) {
            ObservableList<XYChart.Data<Double, Double>> list = FXCollections.observableArrayList();
            for (Point2D point : result) {
                double x = point.getX();
                double y = point.getY();
                list.add(new XYChart.Data<>(x, y));
            }
            anjiLineChart.getData().add(new LineChart.Series<>(result.getName(), list));
        }
        return anjiLineChart;
    }

    public static AnjiLineChart swapGraphics(AnjiLineChart chart, Graphics2DResult result) {
        chart.getData().clear();
        ObservableList<XYChart.Data<Double, Double>> list = FXCollections.observableArrayList();
        for (Point2D point : result) {
            double x = point.getX();
            double y = point.getY();
            list.add(new XYChart.Data<>(x, y));
        }
        chart.getData().add(new LineChart.Series<>(result.getName(), list));
        return chart;
    }

    public static AnjiLineChart getGraphics(Graphics2DResult... results) {
        AnjiLineChart anjiLineChart = createChart(LocalHolder.anji_resourceBundle.getString("anjifx.scene.charts.xaxis"), LocalHolder.anji_resourceBundle.getString("anjifx.scene.charts.yaxis"));
        for (Graphics2DResult result : results) {
            ObservableList<XYChart.Data<Double, Double>> list = FXCollections.observableArrayList();
            for (Point2D point : result) {
                double x = point.getX();
                double y = point.getY();
                list.add(new XYChart.Data<>(x, y));
            }
            anjiLineChart.getData().add(new LineChart.Series<>(result.getName(), list));
        }
        return anjiLineChart;
    }

    public static Path getPath(Graphics2DResult result) {
        Path path = new Path();
        path.getElements().add(getMoveTo(result.getPoint(0)));
        int size = result.size();
        for (int i = 1; i < size; i++) {
            path.getElements().add(getLineTo(result.getPoint(i)));
        }
        return path;
    }

    public static MoveTo getMoveTo(Point2D point2D) {
        return new MoveTo(point2D.getX(), point2D.getY());
    }

    public static LineTo getLineTo(Point2D point2D) {
        return new LineTo(point2D.getX(), point2D.getY());
    }
}
