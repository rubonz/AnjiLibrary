package com.darkempire.math.struct.set;

import com.darkempire.anji.document.tex.TeXEventWriter;
import com.darkempire.math.MathMachine;

import java.util.Arrays;
import java.util.List;

/**
 * Created by siredvin on 05.10.14.
 */
//TODO:секціонувати
public class SimpleDoubleInterval extends DoubleInterval {
    private double start;
    private double end;
    private boolean includingStart;
    private boolean includingEnd;

    public SimpleDoubleInterval(double start, double end) {
        this.start = start;
        this.end = end;
        includingEnd = includingStart = true;
    }

    public SimpleDoubleInterval(double start, double end, boolean includingStart, boolean includingEnd) {
        this.start = start;
        this.end = end;
        this.includingStart = includingStart;
        this.includingEnd = includingEnd;
    }

    public double getStart(int index) {
        return start;
    }

    public double getStart() {
        return start;
    }

    public void setStart(int index, double start) {
        this.start = start;
    }

    public void setStart(double start) {
        this.start = start;
    }

    /*@Override
    public double getEnd(int index) {
        return end;
    }*/

    public double getEnd() {
        return end;
    }

    @Override
    public int getIntervalCount() {
        return 1;
    }

    @Override
    public boolean isSimple() {
        return true;
    }

    public void setEnd(int index, double end) {
        this.end = end;
    }

    public void setEnd(double end) {
        this.end = end;
    }

    /*@Override
    public boolean isClosedStart(int index) {
        return includingStart;
    }*/

    public boolean isClosedStart() {
        return includingStart;
    }

    /*@Override
    public void closeStart(int index, boolean value) {
        includingStart = value;
    }
*/
    public void closeStart(boolean value) {
        includingStart = value;
    }

    /*@Override
    public boolean isClosedEnd(int index) {
        return includingEnd;
    }*/

    public boolean isClosedEnd() {
        return includingEnd;
    }

    /*@Override
    public void closeEnd(int index, boolean value) {
        includingEnd = value;
    }*/

    public void closeEnd(boolean value) {
        includingEnd = value;
    }

   /* @Override
    public boolean isClosed(int index) {
        return includingStart && includingEnd;
    }*/

    public boolean isClosed() {
        return includingEnd && includingStart;
    }

    /*@Override
    public void close(int index, boolean value) {
        includingEnd = includingStart = value;
    }*/

    public void close(boolean value) {
        includingEnd = includingStart = value;
    }


    public boolean isContain(double value) {
        return (value > start && value < end) || (includingStart && value == start) || (includingEnd && value == end);
    }

    public boolean isInside(double value) {
        return (value > start && value < end);
    }

    public SimpleDoubleInterval boundStart(double value) {
        return new SimpleDoubleInterval(value, end);
    }

    public SimpleDoubleInterval boundEnd(double value) {
        return new SimpleDoubleInterval(start, value);
    }

    @Override
    public List<SimpleDoubleInterval> split() {
        return Arrays.asList(this);
    }

    @Override
    public boolean isIntersection(DoubleInterval interval) {
        return interval.isContain(start) || interval.isContain(end);
    }

    @Override
    public String toString() {
        return (includingStart ? '[' : '(') + MathMachine.numberFormat.format(start) + ';' + MathMachine.numberFormat.format(end) + (includingEnd ? ']' : ')');
    }

    @Override
    public void write(TeXEventWriter teXEventWriter) {
        teXEventWriter.append("\\left" + (includingStart ? '[' : '(') + MathMachine.numberFormat.format(start) + ';' + MathMachine.numberFormat.format(end) + "\\right" + (includingEnd ? ']' : ')'));
    }


    @Override
    public DoubleInterval union(DoubleInterval doubleInterval) {
        return null;//TODO:обробити
    }

    @Override
    public DoubleInterval intersection(DoubleInterval doubleInterval) {
        return doubleInterval.intersection(this);
    }

    @Override
    public DoubleInterval complement() {
        return null;//TODO:обробити
    }

    @Override
    public DoubleInterval setminus(DoubleInterval doubleInterval) {
        return null;//TODO:обробити
    }

    @Override
    public String toWolframInput(Object... params) {
        return start + "<=" + params[0].toString() + "<=" + end;
    }
}
