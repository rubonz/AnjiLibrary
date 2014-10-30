package sample;

import com.darkempire.anji.log.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Create in 10:19
 * Created by siredvin on 22.04.14.
 */
public class Main2 {

    public static void main(String[] args) throws Exception {
        List<String> temp = new ArrayList<>();
        temp.add("Temp1");
        temp.add("Temp2");
        temp.add("Temp3");
        temp.add("Temp4");
        temp.add("Temp5");
        temp.add("Temp6");
        temp.add("Temp7");
        temp.add("Temp8");
        temp.add("Temp9");
        temp.add("Temp10");
        temp.add("Temp11");
        temp.add("Temp12");
        temp.add("Temp13");
        List<String> temp2 = new ArrayList<>(temp);
        List<String> temp1 = Collections.unmodifiableList(temp2.subList(0, 5));
        Log.log(Log.debugIndex, temp1.size());
    }
}
