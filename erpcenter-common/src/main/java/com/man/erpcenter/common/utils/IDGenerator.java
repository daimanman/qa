package com.man.erpcenter.common.utils;

import java.util.UUID;

/**
 * id生成器
 * Created by cdr_c on 2017/3/13.
 */
public class IDGenerator {

    /**
     * 生成uui
     *
     * @return uuid
     */
    public static String uuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
