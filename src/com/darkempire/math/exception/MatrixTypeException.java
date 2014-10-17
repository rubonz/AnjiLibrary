package com.darkempire.math.exception;

import com.darkempire.internal.anji.LocalHolder;

/**
 * Create in 8:24
 * Created by siredvin on 20.12.13.
 */
public class MatrixTypeException extends RuntimeException {
    public static final String MATRIX_IS_NOT_POSITIVE = "math.struct.matrix.notpositive";
    public static final String MATRIX_IS_NOT_DEFINITE = "math.struct.matrix.notdefinite";
    private String key;

    public MatrixTypeException(String key) {
        super();
        this.key = key;
    }

    public MatrixTypeException(String key, String grap) {
        super(grap);
        this.key = key;
    }

    @Override
    public String getMessage() {
        return getLocalizedMessage();
    }

    @Override
    public String getLocalizedMessage() {
        return LocalHolder.anji_resourceBundle.getString(key);
    }
}
