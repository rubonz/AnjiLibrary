package com.darkempire.anji.document.tables;

import com.darkempire.anji.util.Pair;
import com.darkempire.math.struct.geometry.geomerty2d.Point2D;
import com.darkempire.math.struct.result.Graphics2DResult;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

/**
 * Created with IntelliJ IDEA.
 * User: siredvin
 * Date: 07.11.13
 * Time: 17:52
 * To change this template use File | Settings | File Templates.
 */
public class SimpleTableReader {
    private File table;
    private String name;
    private List<Pair<Double, Double>> pointList;
    private boolean isRead = false;

    public SimpleTableReader() {
        this.table = null;
        pointList = new ArrayList<>();
    }

    public SimpleTableReader(File table) {
        this.table = table;
        pointList = new ArrayList<>();
    }

    public void read() throws FileNotFoundException, UnsupportedEncodingException {
        if (table == null)
            throw new NullPointerException();
        Scanner in = new Scanner(new InputStreamReader(new FileInputStream(table), "UTF8"));
        name = in.nextLine();
        in.useLocale(Locale.ENGLISH);
        while (in.hasNextDouble()) {
            Pair<Double, Double> pair = new Pair<>(in.nextDouble(), in.nextDouble());
            pointList.add(pair);
        }
        isRead = true;
        in.close();
    }

    public Graphics2DResult convertToGraphics2DResult() {
        if (!isRead)
            return null;
        Graphics2DResult result = new Graphics2DResult(name);
        for (Pair<Double, Double> pair : pointList) {
            result.addPoint(new Point2D(pair.getFirst(), pair.getSecond()));
        }
        return result;
    }

    public void clear() {
        pointList.clear();
        isRead = false;
    }

    public void setFile(File f) {
        this.table = f;
        isRead = false;
    }

    public boolean isRead() {
        return isRead;
    }
}
