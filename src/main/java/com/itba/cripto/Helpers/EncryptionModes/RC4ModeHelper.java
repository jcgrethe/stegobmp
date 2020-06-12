package com.itba.cripto.Helpers.EncryptionModes;

import lombok.NoArgsConstructor;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import static javax.crypto.Cipher.DECRYPT_MODE;
import static javax.crypto.Cipher.ENCRYPT_MODE;

@NoArgsConstructor
public class RC4ModeHelper {

    private Cipher getRC4Instance(byte[] key,int mode) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        Cipher rc4 = Cipher.getInstance("RC4");
        SecretKeySpec rc4Key = new SecretKeySpec(key, "RC4");
        rc4.init(mode, rc4Key);
        return rc4;
    }

    public byte[] encrypt(byte[] plaintext,byte[] key) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {
        Cipher rc4 = getRC4Instance(key,ENCRYPT_MODE);
        byte[] ciphertextBytes = rc4.doFinal(plaintext);
        return ciphertextBytes;
    }

    public byte[] decrypt(byte[] ciphertextBytes,byte[] key) throws InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {
        Cipher rc4 = getRC4Instance(key,DECRYPT_MODE);
        byte[] byteDecryptedText = rc4.doFinal(ciphertextBytes);
        return byteDecryptedText;
     }

}
