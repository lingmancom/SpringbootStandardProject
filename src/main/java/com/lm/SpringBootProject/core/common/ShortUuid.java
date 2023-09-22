package com.lm.SpringBootProject.core.common;

import cn.hutool.core.lang.UUID;

public class ShortUuid {

    public static String getShortUuid() {
        String uuid = UUID.randomUUID().toString().replace("-","").substring(0,8);
        return uuid;
    }
}
