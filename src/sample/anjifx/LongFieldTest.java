package sample.anjifx;

import com.darkempire.anjifx.beans.property.AnjiIntegerProperty;
import com.darkempire.anjifx.beans.property.AnjiLongProperty;
import com.darkempire.anjifx.dialog.DialogUtil;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Created by siredvin on 24.09.14.
 */
public class LongFieldTest extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        AnjiIntegerProperty property = new AnjiIntegerProperty("Test", 20);
        DialogUtil.createDialog(property).show();
        primaryStage.close();
    }

    public static void main(String[] args) {
        LongFieldTest.launch(args);
    }
}
