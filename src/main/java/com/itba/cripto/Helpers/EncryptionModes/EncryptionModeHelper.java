package com.itba.cripto.Helpers.EncryptionModes;

import com.itba.cripto.Helpers.Constant.Constants;
import com.itba.cripto.Helpers.Factories.IVLenghtFactory;
import com.itba.cripto.Helpers.Factories.SchemeFactory;
import com.itba.cripto.Interfaces.EncriptionMode;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import static com.itba.cripto.Helpers.Constant.Constants.ConstantsValues.ECB;

public class EncryptionModeHelper implements EncriptionMode {


    private static final int KEY_IDX = 0;
    private static final int IV_IDX = 1;
    private final String mode;
    private SecretKeySpec secretKey;
    private IvParameterSpec iv;

    public EncryptionModeHelper(String mode) {
        this.mode = mode;
    }

    /**
     * Thanks go to Ola Bini for releasing this source on his blog. The source was obtained from <a
     * href="http://olabini.com/blog/tag/evp_bytestokey/">here</a> .
     * https://gist.github.com/luosong/5523434
     */
    public static byte[][] EVP_BytesToKey(int key_len, int iv_len, MessageDigest md, byte[] data, int count) {
        byte[][] both = new byte[2][];
        byte[] key = new byte[key_len];
        int key_ix = 0;
        byte[] iv = new byte[iv_len];
        int iv_ix = 0;
        both[0] = key;
        both[1] = iv;
        byte[] md_buf = null;
        int nkey = key_len;
        int niv = iv_len;
        int i = 0;
        if (data == null) {
            return both;
        }
        int addmd = 0;
        for (; ; ) {
            md.reset();
            if (addmd++ > 0) {
                md.update(md_buf);
            }
            md.update(data);
            md_buf = md.digest();
            for (i = 1; i < count; i++) {
                md.reset();
                md.update(md_buf);
                md_buf = md.digest();
            }
            i = 0;
            if (nkey > 0) {
                for (; ; ) {
                    if (nkey == 0)
                        break;
                    if (i == md_buf.length)
                        break;
                    key[key_ix++] = md_buf[i];
                    nkey--;
                    i++;
                }
            }
            if (niv > 0 && i != md_buf.length) {
                for (; ; ) {
                    if (niv == 0)
                        break;
                    if (i == md_buf.length)
                        break;
                    iv[iv_ix++] = md_buf[i];
                    niv--;
                    i++;
                }
            }
            if (nkey == 0 && niv == 0) {
                break;
            }
        }
        for (i = 0; i < md_buf.length; i++) {
            md_buf[i] = 0;
        }
        return both;
    }


    private void setKey(String myKey, String scheme) throws NoSuchAlgorithmException {
        int keyLenght = SchemeFactory.GetScheme(scheme);
        int ivLenght = IVLenghtFactory.GetIVLenght(scheme);
        byte[][] both = EVP_BytesToKey(keyLenght, ivLenght, MessageDigest.getInstance("SHA-256"), myKey.getBytes(), 1);
        this.iv = new IvParameterSpec(both[IV_IDX]);
        if (keyLenght == 8)
            secretKey = new SecretKeySpec(both[KEY_IDX], "DES");
        else
            secretKey = new SecretKeySpec(both[KEY_IDX], "AES");
    }

    @Override
    public String encrypt(String strToEncrypt, String secret, String scheme) {
        try {
            String mode;
            if (scheme.compareTo(Constants.ConstantsValues.DES) == 0)
                mode = String.format("DES/%s/NoPadding", this.mode);
            else
                mode = String.format("AES/%s/NoPadding", this.mode);
            setKey(secret, scheme);
            Cipher cipher = Cipher.getInstance(mode);
            if (this.mode.toLowerCase().compareTo(ECB) != 0)
                cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
            else
                cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
        } catch (Exception e) {
            System.out.println("Error while encrypting: " + e.toString());
        }
        return null;
    }

    @Override
    public byte[] decrypt(byte[] strToDecrypt, String secret, String scheme) {
        try {
            String mode;
            if (scheme.compareTo(Constants.ConstantsValues.DES) == 0)
                mode = String.format("DES/%s/NoPadding", this.mode);
            else
                mode = String.format("AES/%s/NoPadding", this.mode);
            setKey(secret, scheme);
            Cipher cipher = Cipher.getInstance(mode);
            if (this.mode.toLowerCase().compareTo(ECB) != 0)
                cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
            else
                cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return cipher.doFinal(strToDecrypt);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error while decrypting: " + e.toString());
        }

    }


}
