package com.darkempire.internal.anji;

import com.darkempire.anji.log.Log;
import com.darkempire.anji.annotation.AnjiUtil;
import com.darkempire.anjifx.util.AnjiImageCache;
import com.darkempire.internal.Cache;

import javax.annotation.PreDestroy;

/**
 * Created with IntelliJ IDEA.
 * User: siredvin
 * Date: 04.11.13
 * Time: 13:05
 * To change this template use File | Settings | File Templates.
 */
@AnjiUtil
@AnjiInternal
public final class Cleaner {
    private Cleaner() {
    }

    @AnjiInternal
    @PreDestroy
    public static void clear() {
        //Внутрушні штуки
        Log.anji_clear();
        Cache.anji_clear();
        //Утіліти
        AnjiImageCache.anji_clear();
    }
}
