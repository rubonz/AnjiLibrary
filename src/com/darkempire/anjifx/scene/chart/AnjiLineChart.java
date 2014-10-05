package com.darkempire.anjifx.scene.chart;

import com.darkempire.anjifx.beans.property.AnjiColorProperty;
import com.darkempire.anjifx.dialog.DialogUtil;
import com.darkempire.anjifx.util.AnjiFXStringConverter;
import com.darkempire.internal.anji.LocalHolder;
import javafx.beans.InvalidationListener;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.chart.Axis;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: siredvin
 * Date: 08.11.13
 * Time: 8:25
 * To change this template use File | Settings | File Templates.
 */
public class AnjiLineChart<X, Y> extends LineChart<X, Y> {
    private List<AnjiColorProperty> colorValues;
    private InvalidationListener updateColor = observable -> updateStyle();

    public AnjiLineChart(Axis<X> xAxis, Axis<Y> yAxis) {
        super(xAxis, yAxis);
        colorValues = new ArrayList<>();
        this.setCreateSymbols(false);
        getData().addListener((ListChangeListener<Series<X, Y>>) c -> {
            List<? extends Series<X, Y>> list = c.getList();
            int size = list.size();
            int listSize = colorValues.size();
            for (int i = listSize; i < size; i++) {
                Series<X, Y> series = list.get(i);
                AnjiColorProperty tempValue = new AnjiColorProperty(LocalHolder.anji_resourceBundle.getString("anjifx.scene.colorforchart") + series.getName(), Color.gray(Math.random() * 0.5 + 0.3));
                tempValue.addListener(updateColor);
                colorValues.add(tempValue);
            }
            updateLabel();
            updateStyle();
        });
    }

    public AnjiLineChart(Axis<X> xAxis, Axis<Y> yAxis, ObservableList<Series<X, Y>> serieses) {
        super(xAxis, yAxis, serieses);
    }

    public AnjiColorProperty lineStrokeColor(int index) {
        return colorValues.get(index);
    }

    public void setColor(int index, Color color) {
        colorValues.get(index).setValue(color);
    }

    public void clearColor(int lineIndex) {
        colorValues.get(lineIndex).setValue(Color.gray(Math.random() * 0.5 + 0.3));
    }

    private void updateStyle() {
        int size = colorValues.size();
        for (int i = 0; i < size; i++) {
            Set<Node> nodeSet = lookupAll(".chart-series-line.series" + i);
            Color c = colorValues.get(i).getValue();
            if (c != null && nodeSet != null) {
                for (Node node : nodeSet) {
                    node.setStyle(String.format("-fx-stroke: %s;", AnjiFXStringConverter.colorToRGBString(c)));
                }
            }
        }
        int i = 0;
        for (Node node : getLegend().lookupAll(".label")) {
            Label label = (Label) node;
            Color temp = colorValues.get(i).getValue();
            if (temp.getOpacity() == 1) {
                label.getGraphic().setStyle(String.format("-fx-background-color:%s;", AnjiFXStringConverter.colorToRGBString(temp)));
            } else {
                temp = Color.color(temp.getRed(), temp.getGreen(), temp.getBlue(), 1);
                label.getGraphic().setStyle(String.format("-fx-background-color:%s,white;", AnjiFXStringConverter.colorToRGBString(temp)));
            }
            i++;
        }
    }

    private void hideSeries(int index) {
        AnjiColorProperty value = colorValues.get(index);
        value.setOpticaly(0);
    }

    private void showSeries(int index) {
        AnjiColorProperty value = colorValues.get(index);
        value.setOpticaly(1);
    }


    private void updateLabel() {
        int i = 0;
        for (Node node : getLegend().lookupAll(".label")) {
            Label label = (Label) node;
            if (label.getOnMouseClicked() == null) {
                label.setOnMouseClicked(new LabelListener(i));
            }
            i++;
        }
    }

    private class LabelListener implements EventHandler<MouseEvent> {
        private int number;

        public LabelListener(int number) {
            this.number = number;
        }

        @Override
        public void handle(MouseEvent event) {
            switch (event.getButton()) {
                case PRIMARY:
                    boolean dis = colorValues.get(number).getValue().getOpacity() == 0;
                    if (dis) {
                        showSeries(number);
                    } else {
                        hideSeries(number);
                    }
                    break;
                case SECONDARY:
                    DialogUtil.createDialog(LocalHolder.anji_resourceBundle.getString("anjifx.dialog.color.selectcolor"), colorValues.get(number)).show();
                    break;
            }
        }
    }
}