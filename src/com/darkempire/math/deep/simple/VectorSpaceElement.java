package com.darkempire.math.deep.simple;

import com.darkempire.math.deep.FieldElement;
import com.darkempire.math.deep.LinearSpace;
import com.darkempire.math.deep.LinearSpaceElement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: siredvin
 * Date: 02.11.13
 * Time: 22:34
 * To change this template use File | Settings | File Templates.
 */
public class VectorSpaceElement<K extends FieldElement<K>> implements LinearSpaceElement<VectorSpaceElement<K>, K>, Iterable<K> {
    private List<K> vector;

    public VectorSpaceElement() {
        vector = new ArrayList<>();
    }

    public VectorSpaceElement(K... ks) {
        this();
        Collections.addAll(vector, ks);
    }

    public VectorSpaceElement(List<K> vector) {
        this.vector = vector;
    }

    public K get(int index) {
        return vector.get(index);
    }

    public void set(int index, K k) {
        vector.set(index, k);
    }

    public int size() {
        return vector.size();
    }

    @Override
    public VectorSpaceElement<K> add(VectorSpaceElement<K> vectorSpaceElement) {
        final int vsize = vectorSpaceElement.size();
        final int size = size();
        boolean bigger = size > vsize;
        if (bigger) {
            for (int i = 0; i < vsize; i++) {
                vector.get(i).add(vectorSpaceElement.get(i));
            }
        } else {
            for (int i = 0; i < size; i++) {
                vector.get(i).add(vectorSpaceElement.get(i));
            }
            for (int i = size; i < vsize; i++) {
                vector.add((K) vectorSpaceElement.get(i).clone());
            }
        }
        return this;
    }

    @Override
    public VectorSpaceElement<K> subtract(VectorSpaceElement<K> vectorSpaceElement) {
        final int vsize = vectorSpaceElement.size();
        final int size = size();
        boolean bigger = size > vsize;
        if (bigger) {
            for (int i = 0; i < vsize; i++) {
                vector.get(i).subtract(vectorSpaceElement.get(i));
            }
        } else {
            for (int i = 0; i < size; i++) {
                vector.get(i).subtract(vectorSpaceElement.get(i));
            }
            for (int i = size; i < vsize; i++) {
                vector.add(((K) vectorSpaceElement.get(i).clone()).negate());
            }
        }
        return this;
    }

    @Override
    public VectorSpaceElement<K> negate() {
        for (K k : vector) {
            k.negate();
        }
        return this;
    }

    @Override
    public VectorSpaceElement<K> multiply(K k) {
        for (K k1 : vector) {
            k1.multiply(k);
        }
        return this;
    }

    @Override
    public VectorSpaceElement<K> divide(K k) {
        for (K k1 : vector) {
            k1.divide(k);
        }
        return this;
    }

    @Override
    public LinearSpace<VectorSpaceElement<K>, K> getSpace() {
        return new VectorSpace<>();
    }

    @Override
    public VectorSpaceElement<K> clone() {
        ArrayList<K> list = new ArrayList<>();
        for (K k : vector) {
            list.add((K) k.clone());
        }
        return new VectorSpaceElement<>(list);
    }

    @Override
    public String toString() {
        final int size = vector.size();
        StringBuilder b = new StringBuilder();
        b.append('(');
        for (int i = 0; i < size; i++) {
            b.append(vector.get(i));
            if (i != size - 1)
                b.append(',');
        }
        b.append(')');
        return b.toString();
    }

    @Override
    public Iterator<K> iterator() {
        return vector.iterator();
    }
}
