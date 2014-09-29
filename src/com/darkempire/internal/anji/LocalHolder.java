package com.darkempire.internal.anji;

import java.util.ResourceBundle;

/**
 * Created with IntelliJ IDEA.
 * User: siredvin
 * Date: 04.11.13
 * Time: 9:44
 * To change this template use File | Settings | File Templates.
 */
@AnjiInternal
public final class LocalHolder {
    private LocalHolder() {
    }

    @AnjiInternal
    public static final ResourceBundle anji_resourceBundle = ResourceBundle.getBundle("com/darkempire/res/anji");
}
