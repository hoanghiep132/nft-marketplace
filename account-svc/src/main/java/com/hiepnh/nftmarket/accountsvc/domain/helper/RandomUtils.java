package com.hiepnh.nftmarket.accountsvc.domain.helper;

import java.nio.charset.StandardCharsets;
import java.util.Random;

public class RandomUtils {

    public static String randomPassword(int length) {
        byte[] array = new byte[length];
        new Random().nextBytes(array);
        String password = new String(array, StandardCharsets.UTF_8);
        return password;
    }
}
