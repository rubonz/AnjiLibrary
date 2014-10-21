package com.darkempire.anjifx.scene.chart;

import com.darkempire.anji.annotation.AnjiExperimental;
import com.darkempire.anjifx.beans.property.AnjiColorProperty;
import com.darkempire.anjifx.dialog.DialogUtil;
import com.darkempire.anjifx.util.AnjiFXColorUtil;
import com.darkempire.anjifx.util.AnjiFXStringConverter;
import com.darkempire.internal.anji.LocalHolder;
import javafx.beans.InvalidationListener;
import javafx.beans.NamedArg;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.chart.Axis;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: siredvin
 * Date: 08.11.13
 * Time: 8:25
 * To change this template use File | Settings | File Templates.
 */
@AnjiExperimental
public class AnjiLineChart<X, Y> extends LineChart<X, Y> {
    private Map<Series, AnjiColorProperty> colorValues;
    private Map<Series, LabelListener> listenerMap;
    private InvalidationListener updateColor = observable -> updateStyle();

    public AnjiLineChart(@NamedArg("xAxis") Axis<X> xAxis, @NamedArg("yAxis") Axis<Y> yAxis) {
        super(xAxis, yAxis);
        colorValues = new HashMap<>();
        listenerMap = new HashMap<>();
        this.setCreateSymbols(false);
        getData().addListener((ListChangeListener<Series<X, Y>>) c -> {
            while (c.next()) {
                for (Series s : c.getAddedSubList()) {
                    Color color = AnjiFXColorUtil.randomColor();
                    AnjiColorProperty tempValue = new AnjiColorProperty(s, LocalHolder.anji_resourceBundle.getString("anjifx.scene.colorforchart") + s.getName(), color);
                    tempValue.addListener(updateColor);
                    colorValues.put(s, tempValue);
                }
                for (Series s : c.getRemoved()) {
                    colorValues.remove(s);
                    listenerMap.remove(s);
                }
            }
            updateLabel();
            updateStyle();
        });
    }

    public AnjiLineChart(Axis<X> xAxis, Axis<Y> yAxis, ObservableList<Series<X, Y>> serieses) {
        super(xAxis, yAxis, serieses);
    }

    public AnjiColorProperty lineStrokeColor(int index) {
        return colorValues.get(getData().get(index));
    }

    public void setColor(int index, Color color) {
        colorValues.get(getData().get(index)).setValue(color);
    }

    public void clearColor(int lineIndex) {
        colorValues.get(getData().get(lineIndex)).setValue(AnjiFXColorUtil.randomColor());
    }

    private void updateStyle() {
        requestChartLayout();
        int size = colorValues.size();
        for (int i = 0; i < size; i++) {
            Set<Node> nodeSet = lookupAll(".chart-series-line.series" + i);
            Color c = colorValues.get(getData().get(i)).getValue();
            if (c != null && nodeSet != null) {
                for (Node node : nodeSet) {
                    node.setStyle(String.format("-fx-stroke: %s;", AnjiFXStringConverter.colorToRGBString(c)));
                }
            }
        }
        int i = 0;
        Node legend = getLegend();//Може бути null тоді, коли getData().size==0
        if (legend != null) {
            for (Node node : legend.lookupAll(".label")) {
                Label label = (Label) node;
                Color temp = colorValues.get(getData().get(i)).getValue();
                if (temp.getOpacity() == 1) {
                    label.getGraphic().setStyle(String.format("-fx-background-color:%s;", AnjiFXStringConverter.colorToRGBString(temp)));
                } else {
                    temp = Color.color(temp.getRed(), temp.getGreen(), temp.getBlue(), 1);
                    label.getGraphic().setStyle(String.format("-fx-background-color:%s,white;", AnjiFXStringConverter.colorToRGBString(temp)));
                }
                i++;
            }
        }
    }

    public void hideSeries(int... indexes) {
        for (int i : indexes) {
            hideSeries(i);
        }
    }

    public void hideSeries(int index) {
        Series s = getData().get(index);
        AnjiColorProperty value = colorValues.get(s);
        if (value != null && value.getValue().getOpacity() > 0) {
            value.setOpticaly(0);
            listenerMap.get(s).hide();
        }
    }

    public void showSeries(int... indexes) {
        for (int i : indexes) {
            showSeries(i);
        }
    }

    public void showSeries(int index) {
        Series s = getData().get(index);
        AnjiColorProperty value = colorValues.get(s);
        if (value != null && value.getValue().getOpacity() < 1) {
            value.setOpticaly(1);
            listenerMap.get(s).show();
        }
    }

    private void hideSeries(Series index) {
        AnjiColorProperty value = colorValues.get(index);
        if (value != null) {
            value.setOpticaly(0);
        }
    }

    private void showSeries(Series index) {
        AnjiColorProperty value = colorValues.get(index);
        if (value != null) {
            value.setOpticaly(1);
        }
    }


    private void updateLabel() {
        int i = 0;
        Node legend = getLegend();
        if (legend != null) {//Може бути null тоді, коли getData().size==0
            for (Node node : legend.lookupAll(".label")) {
                Label label = (Label) node;
                if (label.getOnMouseClicked() == null) {
                    Series data = getData().get(i);
                    LabelListener value = new LabelListener(data);
                    listenerMap.put(data, value);
                    label.setOnMouseClicked(value);
                }
                i++;
            }
        }
    }

    private class LabelListener implements EventHandler<MouseEvent> {
        private Series number;
        private ObservableList<Data> savedData;

        public LabelListener(Series number) {
            this.number = number;
            savedData = null;
        }

        public void show() {
            showSeries(number);
            number.setData(savedData);
        }

        public void hide() {
            hideSeries(number);
            savedData = number.getData();
            number.setData(FXCollections.emptyObservableList());
        }

        @Override
        public void handle(MouseEvent event) {
            switch (event.getButton()) {
                case PRIMARY:
                    boolean dis = colorValues.get(number).getValue().getOpacity() == 0;
                    if (dis) {
                        show();
                    } else {
                        hide();
                    }
                    break;
                case SECONDARY:
                    DialogUtil.createDialog(LocalHolder.anji_resourceBundle.getString("anjifx.dialog.color.selectcolor"), colorValues.get(number)).show();
                    break;
            }
        }
    }
}