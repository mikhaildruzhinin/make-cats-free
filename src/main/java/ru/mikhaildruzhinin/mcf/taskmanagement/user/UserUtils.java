package ru.mikhaildruzhinin.mcf.taskmanagement.user;

import io.quarkus.elytron.security.common.BcryptUtil;

public class UserUtils {

    public static String createPassword() {
        return BcryptUtil.bcryptHash("password");
    }
}
