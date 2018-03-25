package net.aulang.account.util;

import java.util.UUID;

public class StrUtils {

    public static String UUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

}
