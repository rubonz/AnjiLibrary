package com.darkempire.math.struct.fractal.lsystem;

import com.darkempire.math.struct.geometry.geomerty2d.Point2D;
import com.darkempire.math.struct.geometry.geomerty2d.Vector2D;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Stack;

/**
 * Created by siredvin on 29.09.14.
 */
public class SimpleLSystem2D {
    private static final int ENGLISH_SYMBOL_COUNT = 58;
    private static final int FIRST_ENGLISH_SYMBOL_INDEX = 65;
    private static final String SPECIAL_CHARACTER_STRING = "[]+-()&^{}";


    private Tortoise2D marker;
    private double angle;
    private String axiom;
    private ILSystem2DDrawer drawer;
    private IRule[] rules;
    private CharType[] types;

    private boolean saveMode;
    private String priviosResult;
    private int level;

    public SimpleLSystem2D(Tortoise2D marker, double angle, String axiom, ILSystem2DDrawer drawer) {
        this.marker = marker;
        this.angle = angle;
        this.axiom = axiom;
        this.drawer = drawer;
        rules = new IRule[ENGLISH_SYMBOL_COUNT];
        types = new CharType[ENGLISH_SYMBOL_COUNT];
        level = 0;
    }

    //region Геттери
    public boolean isSaveMode() {
        return saveMode;
    }

    public int getLevel() {
        return level;
    }

    public String getPriviosResult() {
        return priviosResult;
    }

    public IRule getRule(char k) {
        return rules[k - FIRST_ENGLISH_SYMBOL_INDEX];
    }

    public CharType getCharType(char k) {
        return types[k - FIRST_ENGLISH_SYMBOL_INDEX];
    }

    //endregion

    //region Сеттери
    public void setSaveMode(boolean saveMode) {
        this.saveMode = saveMode;
        if (saveMode) {
            priviosResult = axiom;
        } else {
            priviosResult = null;
        }
    }

    public SimpleLSystem2D setRule(IRule rule, char k) {
        rules[k - FIRST_ENGLISH_SYMBOL_INDEX] = rule;
        return this;
    }

    public SimpleLSystem2D setCharType(CharType type, char k) {
        types[k - FIRST_ENGLISH_SYMBOL_INDEX] = type;
        return this;
    }
    //endregion

    //region Створення символьного провідника системи
    public String createString(int level) {
        String result = generateLevelString(level, axiom);
        if (saveMode) {
            priviosResult = result;
        }
        return result;
    }

    private String generateLevelString(int level, String code) {
        if (level == 1)
            return generateFirstLevelString(code);
        StringBuilder builder = new StringBuilder();
        for (char k : code.toCharArray()) {
            if (SPECIAL_CHARACTER_STRING.indexOf(k) == -1) {
                builder.append(generateLevelString(level - 1, rules[k - FIRST_ENGLISH_SYMBOL_INDEX].getRule()));
            }
        }
        return builder.toString();
    }

    private String generateFirstLevelString(String code) {
        return code;
    }
    //endregion

    public void draw(int level) {
        drawLevel(level, axiom);
    }

    private void drawLevel(int level, String code) {
        if (level == 1)
            drawFirstLevel(code);
        for (char k : code.toCharArray()) {
            if (SPECIAL_CHARACTER_STRING.indexOf(k) == -1) {
                drawLevel(level - 1, rules[k - FIRST_ENGLISH_SYMBOL_INDEX].getRule());
            }
        }
    }

    private void drawFirstLevel(String code) {
        Deque<Point2D> positionStack = new ArrayDeque<>();
        Deque<Vector2D> moveStack = new ArrayDeque<>();
        for (char k : code.toCharArray()) {
            switch (k) {
                case '[':
                    positionStack.addLast(marker.getPosition().deepcopy());
                    moveStack.addLast(marker.getMoveVector().deepcopy());
                    break;
                case ']':
                    marker.setPosition(positionStack.pollLast());
                    marker.setMoveVector(moveStack.pollLast());
                    break;
                case '+':
                    marker.rotate(angle);
                    break;
                case '-':
                    marker.rotate(-angle);
                    break;
                case '&':
                    //Не підтримується
                    break;
                case '^':
                    //Не підтримується
                    break;
                case '{':
                    //Не підтримується
                    break;
                case '}':
                    //Не підтримується
                    break;
                case '(':
                    //Не підтримується
                    break;
                case ')':
                    //Не підтримується
                    break;
                default:
                    switch (types[k - FIRST_ENGLISH_SYMBOL_INDEX]) {
                        case MOVE:
                            drawer.move(marker.step());
                            break;
                        case JUMP:
                            drawer.jump(marker.step());
                            break;
                        case SKIP:
                            break;
                    }
                    break;
            }
        }
    }
}
