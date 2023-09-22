package com.lm.SpringBootProject.core.utils;

import org.mindrot.jbcrypt.BCrypt;

public class BCryptUtils {
    public static String hashPwd(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }
    /*
     *
     * 使用正确密码验证密码是否正确
     *     password(用户传入的密码)
     *    encodedPassword 加密密码（数据库存储的密码）
     * */
    public static Boolean checkPwd(String password, String encodedPassword) {
        return BCrypt.checkpw(password, encodedPassword);
    }
}
