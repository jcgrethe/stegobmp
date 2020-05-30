package com.itba.cripto;

import com.itba.cripto.Helpers.EncryptionModes.EncryptionModeHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;

public class test {


    @Test
    public void encryptCBC() {
        String mode = "cbc";
        EncryptionModeHelper encryptionModeHelper = new EncryptionModeHelper(mode);

        String secret = "misecretosjkdnkjasndkas";
        String pass = "mipasssss";
        byte[] data = encryptionModeHelper.encrypt(secret.getBytes(), pass, "aes128");

        EncryptionModeHelper decryptionModeHelper = new EncryptionModeHelper(mode);
        byte[] result = decryptionModeHelper.decrypt(data, pass, "aes128");
        assertEquals(secret, new String(result));
    }

    @Test
    public void encryptECB() {
        String mode = "ecb";
        EncryptionModeHelper encryptionModeHelper = new EncryptionModeHelper(mode);

        String secret = "misecresmadkmalksdto";
        String pass = "mipassss";
        byte[] data = encryptionModeHelper.encrypt(secret.getBytes(), pass, "aes128");

        EncryptionModeHelper decryptionModeHelper = new EncryptionModeHelper(mode);
        byte[] result = decryptionModeHelper.decrypt(data, pass, "aes128");
        assertEquals(secret, new String(result));
    }

    @Test
    public void encryptCFB() throws IOException {
        EncryptionModeHelper encryptionModeHelper = new EncryptionModeHelper("cfb");

        String secret = "misecreto";
        String pass = "mipass";
        byte[] data = encryptionModeHelper.encrypt(secret.getBytes(), pass, "aes128");

        EncryptionModeHelper decryptionModeHelper = new EncryptionModeHelper("cfb");
        byte[] result = decryptionModeHelper.decrypt(data, pass, "aes128");
        assertEquals(secret, new String(result));
    }

    @Test
    public void encryptOFB() {
        String mode = "ofb";
        EncryptionModeHelper encryptionModeHelper = new EncryptionModeHelper(mode);

        String secret = "misecreto";
        String pass = "mipass";
        byte[] data = encryptionModeHelper.encrypt(secret.getBytes(), pass, "aes128");

        EncryptionModeHelper decryptionModeHelper = new EncryptionModeHelper(mode);
        byte[] result = decryptionModeHelper.decrypt(data, pass, "aes128");
        assertEquals(secret, new String(result));
    }

}
