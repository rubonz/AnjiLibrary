package com.darkempire.anji.sql;

/**
 * Created with IntelliJ IDEA.
 * User: siredvin
 * Date: 02.12.13
 * Time: 10:57
 * To change this template use File | Settings | File Templates.
 */
@Deprecated
@FunctionalInterface
public interface ICompiler<K> {
    String convert(K k);
}
