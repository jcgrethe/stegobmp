package com.itba.cripto;

import com.itba.cripto.Helpers.EncryptionModes.EncryptionModeHelper;
import com.itba.cripto.Helpers.EncryptionModes.RC4ModeHelper;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.jupiter.api.Test;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class test {

    @Test
    public void encrypt() throws NoSuchPaddingException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
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

        testRC4();

    }


    private void test(String mode, String scheme){
        EncryptionModeHelper encryptionModeHelper = new EncryptionModeHelper(mode);
        ThreadLocalRandom random = ThreadLocalRandom.current();
        String secret = RandomStringUtils.randomAlphanumeric(random.nextInt(100, 20000));
        String pass = RandomStringUtils.randomAlphanumeric(random.nextInt(10, 200));
        byte[] data = encryptionModeHelper.encrypt(secret.getBytes(), pass, scheme);

        EncryptionModeHelper decryptionModeHelper = new EncryptionModeHelper(mode);

        byte[] result = decryptionModeHelper.decrypt(data, pass, scheme);
        assertEquals(secret, new String(result));
    }

    private void testRC4() throws IllegalBlockSizeException, NoSuchPaddingException, BadPaddingException, NoSuchAlgorithmException, InvalidKeyException, InvalidAlgorithmParameterException {
        RC4ModeHelper rc4 = new RC4ModeHelper();
        ThreadLocalRandom random = ThreadLocalRandom.current();
        String secret = RandomStringUtils.randomAlphanumeric(random.nextInt(100, 20000));
        String pass = RandomStringUtils.randomAlphanumeric(random.nextInt(5, 16));
        byte[] data = rc4.encrypt(secret.getBytes(), pass.getBytes());


        byte[] result = rc4.decrypt(data, pass.getBytes());
        assertEquals(secret, new String(result));
    }

}
