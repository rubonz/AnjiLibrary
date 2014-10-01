package sample.anjifx;

import com.darkempire.anji.log.Log;
import com.darkempire.anjifx.beans.property.AnjiIntegerProperty;
import com.darkempire.anjifx.beans.property.AnjiLongProperty;
import com.darkempire.anjifx.dialog.DialogUtil;
import com.darkempire.internal.anji.LocalHolder;
import javafx.application.Application;
import javafx.scene.SnapshotParameters;
import javafx.scene.chart.LineChart;
import javafx.scene.image.WritableImage;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

/**
 * Created by siredvin on 24.09.14.
 */
public class LongFieldTest extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        AnjiIntegerProperty property = new AnjiIntegerProperty("Test", 20);
        Log.log(Log.debugIndex, 1);
        Log.log(Log.debugIndex, 2);
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Asdasdasd");
        Log.log(Log.debugIndex, 3);
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(LocalHolder.anji_resourceBundle.getString("anjifx.scene.charts.tabchart.pngimage"), "*.png"));
        Log.log(Log.debugIndex, 4);
        primaryStage.close();
        File f = chooser.showSaveDialog(primaryStage);
    }

    public static void main(String[] args) {
        LongFieldTest.launch(args);
    }
}
