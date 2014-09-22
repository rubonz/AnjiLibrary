package com.darkempire.math.exception;

import com.darkempire.internal.anji.LocalHolder;

/**
 * Created with IntelliJ IDEA.
 * User: siredvin
 * Date: 07.11.13
 * Time: 8:08
 * To change this template use File | Settings | File Templates.
 */
public class SizeMissmatchException extends RuntimeException {
    public SizeMissmatchException() {
        super();
    }

    public SizeMissmatchException(String gripe) {
        super(gripe);
    }

    @Override
    public String getLocalizedMessage() {
        return LocalHolder.anji_resourceBundle.getString("math.geometrynd.dimentionmissmatch");
    }
}
