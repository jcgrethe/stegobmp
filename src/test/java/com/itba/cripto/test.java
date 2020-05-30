package com.itba.cripto;

import com.itba.cripto.Helpers.EncryptionModes.EncryptionModeHelper;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class test {




    @Test
    public void encrypt() {
        test("cbc","aes128");
        test("cbc","aes192");
        test("cbc","aes256");
        test("cbc","des");

        test("ecb","aes128");
        test("ecb","aes192");
        test("ecb","aes256");
        test("ecb","des");

        test("cfb","aes128");
        test("cfb","aes192");
        test("cfb","aes256");
        test("cfb","des");

        test("ofb","aes128");
        test("ofb","aes192");
        test("ofb","aes256");
        test("ofb","des");
    }


    private void test(String mode, String scheme){
        EncryptionModeHelper encryptionModeHelper = new EncryptionModeHelper(mode);
        int randomNum = ThreadLocalRandom.current().nextInt(100, 20000);
        String secret = RandomStringUtils.randomAlphanumeric(randomNum);;
        String pass = "mipass";
        byte[] data = encryptionModeHelper.encrypt(secret.getBytes(), pass, scheme);

        EncryptionModeHelper decryptionModeHelper = new EncryptionModeHelper(mode);

        byte[] result = decryptionModeHelper.decrypt(data, pass, scheme);
        assertEquals(secret, new String(result));
    }

}
