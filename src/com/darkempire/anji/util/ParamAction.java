package com.darkempire.anji.util;

/**
 * Created with IntelliJ IDEA.
 * User: siredvin
 * Date: 23.11.13
 * Time: 20:52
 * To change this template use File | Settings | File Templates.
 */
@FunctionalInterface
public interface ParamAction<T> {
    public void action(T param);
}
