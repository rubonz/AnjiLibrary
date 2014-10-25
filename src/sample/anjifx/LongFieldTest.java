package sample.anjifx;

import com.darkempire.anji.log.Log;
import com.darkempire.anjifx.beans.property.AnjiBoundedLongProperty;
import com.darkempire.anjifx.beans.property.AnjiEnumProperty;
import com.darkempire.anjifx.beans.property.AnjiIntegerProperty;
import com.darkempire.anjifx.beans.property.AnjiLongProperty;
import com.darkempire.anjifx.dialog.DialogUtil;
import com.darkempire.anjifx.monolog.MonologGeneratorPane;
import com.darkempire.anjifx.scene.LongSpinBox;
import com.darkempire.anjifx.scene.chart.AnjiLineChart;
import com.darkempire.internal.anji.LocalHolder;
import com.darkempire.math.struct.function.polynomial.IteratedPolynomialBuilder;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

/**
 * Created by siredvin on 24.09.14.
 */
public class LongFieldTest extends Application {

    public static void main(String[] args) {
        LongFieldTest.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        AnjiEnumProperty<IteratedPolynomialBuilder.PolynomialType> enumProperty = new AnjiEnumProperty<>("Test", IteratedPolynomialBuilder.PolynomialType.CHEBYSHEV_SHIFTED_FIRST_KIND);
        DialogUtil.createDialog(enumProperty).show();
        primaryStage.show();
        primaryStage.hide();
    }
}
