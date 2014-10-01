package com.darkempire.anjifx.scene.chart;

import com.darkempire.anji.log.Log;
import com.darkempire.anji.util.ICreator;
import com.darkempire.anji.util.INameable;
import com.darkempire.anjifx.beans.property.AnjiChooseStringProperty;
import com.darkempire.anjifx.dialog.DialogUtil;
import com.darkempire.anjifx.monolog.MonologGeneratorPane;
import com.darkempire.internal.AnjiConsts;
import com.darkempire.internal.anji.LocalHolder;
import javafx.collections.FXCollections;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.Event;
import javafx.scene.SnapshotParameters;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.WritableImage;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static com.darkempire.internal.AnjiConsts.imgDir;

/**
 * Created by siredvin on 01.10.14.
 */
public class AnjiLineChartTabPane<R extends INameable> extends TabPane {
    //TODO:можливо, варто додати можливість закривати вкладки
    //Списки, що стосуються графіків
    private List<LineChart> chartList;
    private List<Function<R, XYChart.Series>> processorList;
    //Списки, що стосуються результатів графіків
    private List<String> seriesList;
    private List<R> resultList;
    //Обчислення
    private List<ICreator<R>> calcList;
    //Властивості
    private AnjiChooseStringProperty chooseProperty;

    static {
        AnjiConsts.checkDir(imgDir);
    }

    public AnjiLineChartTabPane() {
        super();
        chartList = new ArrayList<>();
        seriesList = new ArrayList<>();
        resultList = new ArrayList<>();
        processorList = new ArrayList<>();
        calcList = new ArrayList<>();
    }

    public void addCalc(ICreator<R> calc) {
        calcList.add(calc);
    }

    public void addCategoryNumberChart(String name, String categoryDescription, String numberDescription, Function<R, XYChart.Series> processor, String... categories) {
        CategoryAxis xAxis = new CategoryAxis(FXCollections.observableArrayList(categories));
        xAxis.setLabel(categoryDescription);
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel(numberDescription);
        LineChart chart = new LineChart(xAxis, yAxis);
        chartList.add(chart);
        processorList.add(processor);
        Tab tab = new Tab(name);
        tab.setOnCloseRequest(this::tabClosed);
        tab.setContent(chart);
        getTabs().add(tab);
    }

    public void addCategoryNumberChart(String name, String categoryDescription, String numberDescription, Function<R, XYChart.Series> processor, List<String> categories) {
        CategoryAxis xAxis = new CategoryAxis(FXCollections.observableArrayList(categories));
        xAxis.setLabel(categoryDescription);
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel(numberDescription);
        LineChart chart = new LineChart(xAxis, yAxis);
        chartList.add(chart);
        processorList.add(processor);
        Tab tab = new Tab(name);
        tab.setOnCloseRequest(this::tabClosed);
        tab.setContent(chart);
        getTabs().add(tab);
    }

    public void addNumberNumberChart(String name, String xDescription, String yDescription, Function<R, XYChart.Series> processor) {
        NumberAxis xAxis = new NumberAxis(), yAxis = new NumberAxis();
        xAxis.setLabel(xDescription);
        yAxis.setLabel(yDescription);
        LineChart chart = new LineChart(xAxis, yAxis);
        chart.setCreateSymbols(false);
        chartList.add(chart);
        processorList.add(processor);
        Tab tab = new Tab(name);
        tab.setOnCloseRequest(this::tabClosed);
        tab.setContent(chart);
        getTabs().add(tab);
    }

    public void calc() {
        calc(0);
    }

    public void calc(int index) {
        R result = calcList.get(index).calc();
        if (result == null)
            return;
        resultList.add(result);
        seriesList.add(result.getName());
        initResult(result);
    }

    private void initResult(R result) {
        int size = chartList.size();
        for (int i = 0; i < size; i++) {
            chartList.get(i).getData().add(processorList.get(i).apply(result));
        }
    }

    public void selectAndRemove() {
        if (chooseProperty == null) {
            if (seriesList.size() > 0) {
                chooseProperty = new AnjiChooseStringProperty(LocalHolder.anji_resourceBundle.getString("anjifx.scene.charts.tabchart.selectseries"), seriesList);
            } else {
                return;
            }
        }
        if (DialogUtil.createDialog(LocalHolder.anji_resourceBundle.getString("anjifx.scene.charts.tabchart.selectseriesremove"), chooseProperty).show()) {
            int deleteIndex = seriesList.indexOf(chooseProperty.getValue());
            seriesList.remove(deleteIndex);
            if (seriesList.size() > 0) {
                chooseProperty.wipe();
            } else {
                chooseProperty = null;
            }
            for (LineChart chart : chartList) {
                chart.getData().remove(deleteIndex);
            }
            resultList.remove(deleteIndex);
        }
    }

    public void removeAll() {
        if (MonologGeneratorPane.showQuestionDialog(LocalHolder.anji_resourceBundle.getString("anjifx.scene.charts.tabchart.accept"), LocalHolder.anji_resourceBundle.getString("anjifx.scene.charts.tabchart.accept11"), LocalHolder.anji_resourceBundle.getString("anjifx.scene.charts.tabchart.accept12"))) {
            if (MonologGeneratorPane.showQuestionDialog(LocalHolder.anji_resourceBundle.getString("anjifx.scene.charts.tabchart.accept"), LocalHolder.anji_resourceBundle.getString("anjifx.scene.charts.tabchart.accept21"), LocalHolder.anji_resourceBundle.getString("anjifx.scene.charts.tabchart.accept22"))) {
                chooseProperty = null;
                seriesList.clear();
                for (LineChart chart : chartList) {
                    chart.getData().clear();
                }
                resultList.clear();
                MonologGeneratorPane.showAcceptDialog(LocalHolder.anji_resourceBundle.getString("anjifx.scene.charts.tabchart.deleted"), LocalHolder.anji_resourceBundle.getString("anjifx.scene.charts.tabchart.result1"), LocalHolder.anji_resourceBundle.getString("anjifx.scene.charts.tabchart.result2"));
            }
        }
    }

    public void selectAndInfo() {
        if (chooseProperty == null) {
            if (seriesList.size() > 0) {
                chooseProperty = new AnjiChooseStringProperty(LocalHolder.anji_resourceBundle.getString("anjifx.scene.charts.tabchart.selectseries"), seriesList);
            } else {
                return;
            }
        }
        if (DialogUtil.createDialog(LocalHolder.anji_resourceBundle.getString("anjifx.scene.charts.tabchart.selectseriesinfo"), chooseProperty).show()) {
            int showIndex = seriesList.indexOf(chooseProperty.getValue());
            MonologGeneratorPane.showInfoDialog(LocalHolder.anji_resourceBundle.getString("anjifx.scene.charts.tabchart.seriesresult"), chooseProperty.getValue(), resultList.get(showIndex).toString());
        }
    }

    public void snapshotAll(String prefix) {
        String name = prefix + java.time.LocalDateTime.now().toString();
        try {
            int size = chartList.size();
            for (int i = 0; i < size; i++) {
                LineChart chart = chartList.get(i);
                String tabName = getTabs().get(i).getText();
                File f = new File(imgDir, name + tabName + ".png");
                WritableImage image = chart.snapshot(new SnapshotParameters(), null);
                ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", f);
            }
            MonologGeneratorPane.showAcceptDialog(LocalHolder.anji_resourceBundle.getString("anjifx.scene.charts.tabchart.imagessaved"));
        } catch (IOException e) {
            MonologGeneratorPane.showErrorDialog(e.getLocalizedMessage());
        }
    }

    public void snapshotCurrent(Stage stage) {
        if (stage == null)
            return;
        LineChart chart = chartList.get(getSelectionModel().getSelectedIndex());
        Log.log(Log.debugIndex, 1);
        WritableImage image = chart.snapshot(new SnapshotParameters(), null);
        Log.log(Log.debugIndex, 2);
        FileChooser chooser = new FileChooser();
        Log.log(Log.debugIndex, 3);
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(LocalHolder.anji_resourceBundle.getString("anjifx.scene.charts.tabchart.pngimage"), "*.png"));
        Log.log(Log.debugIndex, 4);
        File f = chooser.showSaveDialog(stage);
        Log.log(Log.debugIndex, 5);
        if (f != null) {
            try {
                Log.log(Log.debugIndex, 6);
                ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", f);
                Log.log(Log.debugIndex, 7);
            } catch (IOException e) {
                Log.err(Log.debugIndex, e);
            }
        }
    }

    public List<LineChart> getChartList() {
        return chartList;
    }

    private void tabClosed(Event event) {
        Tab tab = (Tab) event.getSource();
        int index = getTabs().indexOf(tab);
        chartList.remove(index);
        processorList.remove(index);
    }
}
