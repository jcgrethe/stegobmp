package com.itba.cripto.Helpers.EncryptionModes;


import com.itba.cripto.Helpers.Constant.Constants;
import com.itba.cripto.Helpers.Factories.SchemeFactory;
import com.itba.cripto.Interfaces.EncriptionMode;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

public class ECBHelper implements EncriptionMode {

    private static byte[] key;
    private static SecretKeySpec secretKey;
    private static String Mode = Constants.ConstantsValues.ECB.toUpperCase();

    private void setKey(String myKey, String scheme)
    {
        MessageDigest sha = null;
        try {
            int keyLenght = SchemeFactory.GetScheme(scheme);
            key = myKey.getBytes("UTF-8");
            sha = MessageDigest.getInstance("SHA-256");
            key = sha.digest(key);
            key = Arrays.copyOf(key, keyLenght);
            if(keyLenght == 8)
                secretKey = new SecretKeySpec(key, "DES");
            else
                secretKey = new SecretKeySpec(key, "AES");
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String encrypt(String strToEncrypt, String secret, String scheme)
    {
        try
        {
            String mode;
            if(scheme.compareTo(Constants.ConstantsValues.DES) == 0)
                mode  = String.format("DES/%s/PKCS5PADDING",this.Mode);
            else
                mode  = String.format("AES/%s/PKCS5PADDING",this.Mode);
            setKey(secret,scheme);
            Cipher cipher = Cipher.getInstance(mode);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
        }
        catch (Exception e)
        {
            System.out.println("Error while encrypting: " + e.toString());
        }
        return null;
    }

    @Override
    public String decrypt(String strToDecrypt, String secret, String scheme)
    {
        try
        {
            String mode;
            if(scheme.compareTo(Constants.ConstantsValues.DES) == 0)
                mode  = String.format("DES/%s/PKCS5PADDING",this.Mode);
            else
                mode  = String.format("AES/%s/PKCS5PADDING",this.Mode);
            setKey(secret,scheme);
            Cipher cipher = Cipher.getInstance(mode);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
        }
        catch (Exception e)
        {
            System.out.println("Error while decrypting: " + e.toString());
        }
        return null;
    }


}
