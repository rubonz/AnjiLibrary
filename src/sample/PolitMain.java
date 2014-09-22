package sample;

import com.darkempire.anji.document.tex.TeXEventWriter;
import com.darkempire.math.MathMachine;
import com.darkempire.math.utils.ArrayUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Create in 8:48
 * Created by siredvin on 30.05.14.
 */
public class PolitMain {
    private Class cl = null;

    public static void main(String[] args) throws FileNotFoundException {
        TeXEventWriter out = new TeXEventWriter(System.out, MathMachine.numberFormat);
        out.openEnvironment("enumerate");
        int[] indexs = new int[]{1, 2, 3, 4};
        int[] answers = new int[20];
        File f = new File("/home/siredvin/Навчання/Гуманітарні предмети/Політологія/Додаткові завдання з політології/test_qu.txt");
        List<String> strings = new ArrayList<>();
        Scanner in = new Scanner(new FileInputStream(f));
        int i = 0;
        while (in.hasNext()) {
            String s = in.nextLine();
            if (!s.isEmpty()) {
                strings.add(s);
            } else {
                out.item();
                out.text(strings.get(0));
                ArrayUtils.suffle(indexs, 100);
                answers[i] = indexs[0];
                i++;
                out.openEnvironment("enumerate");
                out.item().text(strings.get(indexs[0]));
                out.item().text(strings.get(indexs[1]));
                out.item().text(strings.get(indexs[2]));
                out.item().text(strings.get(indexs[3]));
                out.closeEnvironment();
                strings.clear();
            }
        }
        out.item();
        out.text(strings.get(0));
        ArrayUtils.suffle(indexs, 100);
        answers[i] = indexs[0];
        out.openEnvironment("enumerate");
        out.item().text(strings.get(indexs[0]));
        out.item().text(strings.get(indexs[1]));
        out.item().text(strings.get(indexs[2]));
        out.item().text(strings.get(indexs[3]));
        out.closeEnvironment();
        strings.clear();
        out.closeEnvironment().newpage();
        out.text("Правильні віповіді:");
        out.openEnvironment("enumerate");
        for (int j = 0; j < 20; j++) {
            out.item().text(answers[j] + 1);
        }
        out.closeEnvironment();
    }
}