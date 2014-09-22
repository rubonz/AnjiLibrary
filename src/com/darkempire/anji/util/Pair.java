package com.darkempire.anji.util;


/**
 * Created with IntelliJ IDEA.
 * User: siredvin
 * Date: 07.11.13
 * Time: 17:57
 * To change this template use File | Settings | File Templates.
 */
public class Pair<V1, V2> {
    private V1 v1;
    private V2 v2;

    public Pair(V1 v1, V2 v2) {
        this.v1 = v1;
        this.v2 = v2;
    }

    public V1 getFirst() {
        return v1;
    }

    public V2 getSecond() {
        return v2;
    }

    @Override
    public int hashCode() {
        return (v1.hashCode() << 8) + v2.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (o instanceof Pair) {
            Pair pair = (Pair) o;
            return (v1.equals(pair.v1) && v2.equals(pair.v2)) || (v1.equals(pair.v2) && v2.equals(pair.v1));
        }
        return false;
    }

    @Override
    public String toString() {
        return String.format("(%s,%s)", v1, v2);
    }
}
