package com.darkempire.math.struct;

/**
 * Created with IntelliJ IDEA.
 * User: siredvin
 * Date: 06.11.13
 * Time: 14:50
 * To change this template use File | Settings | File Templates.
 */
public interface Calcable<T extends Calcable<T>> extends Assignable<T>, Modifable<T>, IDeepcopy<T> {
}
