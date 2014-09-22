package com.darkempire.math.struct.fractal.lsystem;

import com.darkempire.internal.anji.LocalHolder;
import com.darkempire.math.struct.geometry.geomerty2d.Point2D;
import com.darkempire.math.struct.geometry.geomerty2d.Vector2D;


/**
 * Created with IntelliJ IDEA.
 * User: siredvin
 * Date: 02.11.13
 * Time: 22:14
 * To change this template use File | Settings | File Templates.
 */
public class SimpleLSystem2D implements ILSystem2D {
    //Кількість різних англійських букв
    private final static byte magicByte = 58;
    //Індекс першого числа
    private final static byte magicByteIndex = 65;

    private Point2D position;
    private Vector2D vector;
    private String name;
    private double angle;
    private String axiom;
    private ICharType[] charTypes;
    private IRule[] rules;

    public SimpleLSystem2D(String name, String axiom, Point2D position, Vector2D vector, double angle) {
        this.name = name;
        this.axiom = axiom;
        this.position = position;
        this.vector = vector;
        this.angle = angle;
        charTypes = new ICharType[magicByte];
        rules = new IRule[magicByte];
    }

    public void setRule(IRule rule, char k) {
        rules[k - magicByteIndex] = rule;
    }

    public void setCharType(ICharType charType, char k) {
        charTypes[k - magicByteIndex] = charType;
    }

    @Override
    public Point2D getPosition() {
        return position;
    }

    @Override
    public Vector2D getVector() {
        return vector;
    }

    @Override
    public double getAngle() {
        return angle;
    }

    @Override
    public String getAxiom() {
        return axiom;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public FractalDisplayType getDisplayType() {
        return FractalDisplayType.LSYSTEM;
    }

    @Override
    public CharType getCharType(char key) {
        CharType result = charTypes[key - magicByteIndex].getCharType();
        if (result == null)
            throw new ArrayIndexOutOfBoundsException(LocalHolder.anji_resourceBundle.getString("math.lsystem.chartypeindex") + ":" + key);
        return result;
    }

    @Override
    public String getRule(char key) {
        IRule result = rules[key - magicByteIndex];
        if (result == null || result.getRule() == null)
            throw new ArrayIndexOutOfBoundsException(LocalHolder.anji_resourceBundle.getString("math.lsystem.ruleindex") + ":" + key);
        return result.getRule();
    }

    @Override
    public LSystemType getLSystemType() {
        return LSystemType.SIMPLE2D;
    }
}
