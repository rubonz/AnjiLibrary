package com.darkempire.math.exception;

import com.darkempire.internal.anji.LocalHolder;

/**
 * Create in 8:24
 * Created by siredvin on 20.12.13.
 */
public class MatrixSizeException extends RuntimeException {
    public static final String MATRIX_IS_NOT_SQUARE = "math.struct.matrix.notsquare";
    public static final String MATRIX_SIZE_MISMATCH = "math.struct.matrix.sizemismatch";
    public static final String MATRIX_SIZE_MULTY_MISMATCH = "math.struct.matrix.multysizemismatch";
    private String key;

    public MatrixSizeException(String key) {
        super();
        this.key = key;
    }

    public MatrixSizeException(String key, String grap) {
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
