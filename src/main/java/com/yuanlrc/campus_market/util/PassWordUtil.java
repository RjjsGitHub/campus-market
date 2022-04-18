package com.yuanlrc.campus_market.util;

import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class PassWordUtil {

    /**
     *执行密码加密
     * @param pwd 原始密码
     * @return
     */
    public static String getEptPassword(String pwd, String salt){
        /**
         * 加密规则：
         * 使用UUID作为盐值，在原密码两侧拼接
         * 循环3次
         */
        for (int i = 0; i < 3; i++) {
            pwd = DigestUtils.md5DigestAsHex((salt + pwd + salt).getBytes()).toUpperCase();
        }
        return pwd;
    }

}
