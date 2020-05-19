package com.itba.cripto.Helpers.EncryptionModes;

import com.itba.cripto.Helpers.Constant.Constants;
import com.itba.cripto.Helpers.Factories.IVLenghtFactory;
import com.itba.cripto.Helpers.Factories.SchemeFactory;
import com.itba.cripto.Interfaces.EncriptionMode;
import lombok.Builder;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

import static com.itba.cripto.Helpers.Constant.Constants.ConstantsValues.ECB;

public class EncryptionModeHelper implements EncriptionMode {


    private byte[] key;
    private SecretKeySpec secretKey;
    private IvParameterSpec iv;
    private final String mode;

    public EncryptionModeHelper(String mode){
        this.mode = mode;
    }

    private void setKey(String myKey, String scheme)
    {
        MessageDigest sha = null;
        try {
            int keyLenght = SchemeFactory.GetScheme(scheme);
            int ivLenght = IVLenghtFactory.GetIVLenght(scheme);
            key = myKey.getBytes("UTF-8");
            sha = MessageDigest.getInstance("SHA-256");
            key = sha.digest(key);
            key = Arrays.copyOf(key, keyLenght);
            int requiredLength = keyLenght + ivLenght;
            this.iv = new IvParameterSpec(Arrays.copyOfRange(key, keyLenght, requiredLength));
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
                mode  = String.format("DES/%s/PKCS5PADDING",this.mode);
            else
                mode  = String.format("AES/%s/PKCS5PADDING",this.mode);
            setKey(secret,scheme);
            Cipher cipher = Cipher.getInstance(mode);
            if(this.mode.toLowerCase().compareTo(ECB) != 0)
                cipher.init(Cipher.ENCRYPT_MODE, secretKey,iv);
            else
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
                mode  = String.format("DES/%s/PKCS5PADDING",this.mode);
            else
                mode  = String.format("AES/%s/PKCS5PADDING",this.mode);
            setKey(secret,scheme);
            Cipher cipher = Cipher.getInstance(mode);
            if(this.mode.toLowerCase().compareTo(ECB) != 0)
                cipher.init(Cipher.DECRYPT_MODE, secretKey,iv);
            else
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
