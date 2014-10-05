package com.darkempire.math.struct.set;

import com.darkempire.anji.document.tex.TeXEventWriter;
import com.darkempire.anji.log.Log;
import com.darkempire.anji.util.Util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by siredvin on 05.10.14.
 */
public class CombinateDoubleInterval extends DoubleInterval {
    public List<SimpleDoubleInterval> intervals;
    private int size;

    public CombinateDoubleInterval(List<SimpleDoubleInterval> intervals) {
        this.intervals = intervals;
        size = intervals.size();
        fullSimplify();
    }

    public CombinateDoubleInterval(SimpleDoubleInterval... intervals) {
        this.intervals = new ArrayList<>();
        Collections.addAll(this.intervals, intervals);
        size = intervals.length;
        fullSimplify();
    }

    @Override
    public double getStart() {
        return intervals.get(0).getStart();
    }

    @Override
    public void setStart(double value) {
        intervals.get(0).setStart(value);
    }

    @Override
    public double getEnd() {
        return intervals.get(size - 1).getEnd();
    }

    @Override
    public void setEnd(double value) {
        intervals.get(size - 1).setEnd(value);
    }

    @Override
    public boolean isClosedStart() {
        return intervals.get(0).isClosedStart();
    }

    @Override
    public void closeStart(boolean value) {
        intervals.get(0).closeStart(value);
    }

    @Override
    public boolean isClosedEnd() {
        return intervals.get(size - 1).isClosedEnd();
    }

    @Override
    public void closeEnd(boolean value) {
        intervals.get(size - 1).closeEnd(value);
    }

    @Override
    public boolean isClosed() {
        return intervals.stream().filter(SimpleDoubleInterval::isClosed).count() == size;
    }

    @Override
    public void close(boolean value) {
        intervals.stream().forEach(i -> i.closeEnd(value));
    }

    @Override
    public DoubleInterval boundStart(double value) {
        return intervals.get(0).boundStart(value);
    }

    @Override
    public DoubleInterval boundEnd(double value) {
        return intervals.get(size - 1).boundEnd(value);
    }

    @Override
    public List<SimpleDoubleInterval> split() {
        return intervals;
    }

    @Override
    public boolean isIntersection(DoubleInterval interval) {
        return false;//TODO:обробити
    }

    @Override
    public boolean isContain(double value) {
        return intervals.stream().filter(i -> i.isContain(value)).count() > 0;
    }

    @Override
    public int getIntervalCount() {
        return size;
    }

    @Override
    public boolean isSimple() {
        return false;
    }

    @Override
    public boolean isInside(double value) {
        return intervals.stream().filter(i -> i.isInside(value)).count() > 0;
    }

    @Override
    public void write(TeXEventWriter out) {

    }

    @Override
    public String toWolframInput(Object... params) {
        StringBuilder builder = new StringBuilder();
        for (SimpleDoubleInterval i : intervals) {
            builder.append(i.toWolframInput(params));
            builder.append(',');
        }
        Util.removeLastChar(builder);
        return builder.toString();
    }

    @Override
    public DoubleInterval union(DoubleInterval doubleInterval) {
        return null;//TODO:обробити
    }

    @Override
    public DoubleInterval intersection(DoubleInterval doubleInterval) {
        return null;//TODO:обробити
    }

    @Override
    public DoubleInterval complement() {
        return null;//TODO:обробити
    }

    @Override
    public DoubleInterval setminus(DoubleInterval doubleInterval) {
        return null;//TODO:обробити
    }

    private void fullSimplify() {
        int oldSize = 0;
        while (oldSize != size) {
            oldSize = size;
            simplify();
        }
    }

    private void simplify() {
        List<SimpleDoubleInterval> addList = new ArrayList<>();
        List<SimpleDoubleInterval> removeList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            for (int j = i + 1; j < size; j++) {
                SimpleDoubleInterval i1 = intervals.get(i);
                SimpleDoubleInterval j1 = intervals.get(j);
                if (i1.isIntersection(j1)) {
                    removeList.add(i1);
                    removeList.add(j1);
                    addList.add(new SimpleDoubleInterval(Math.min(i1.getStart(), j1.getStart()), Math.max(i1.getEnd(), j1.getEnd())));
                }
            }
        }
        if (removeList.size() > 0) {
            intervals.removeAll(removeList);
            intervals.addAll(addList);
            addList.clear();
            removeList.clear();
        }
        size = intervals.size();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (SimpleDoubleInterval d : intervals) {
            builder.append(d);
            builder.append('+');
        }
        Util.removeLastChar(builder);
        return builder.toString();
    }
}
