package ru.mikhaildruzhinin.mcf.taskmanagement.user;

import io.quarkus.elytron.security.common.BcryptUtil;

public class UserUtils {

    public static String createPassword(String password) {
        return BcryptUtil.bcryptHash(password);
    }

    public static String createPassword() {
        return createPassword("password");
    }
}
