package com.itba.cripto;

import org.apache.commons.cli.*;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

/**
 * Hello world!
 *
 */

public class App 
{
    public static void main( String[] args ) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException {
        CommandLine cmd = getOptions(args);
        String msg = "HOLAAA!!!!";
        String key = "1234567891234567";
        String enc = encrypt(msg,key);
        System.out.println(enc);
        String dec = decrypt(enc,key);
        System.out.println(dec);

    }
    private static byte[] key;
    private static SecretKeySpec secretKey;

    public static void setKey(String myKey)
    {
        MessageDigest sha = null;
        try {
            key = myKey.getBytes("UTF-8");
            sha = MessageDigest.getInstance("SHA-1");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16);
            secretKey = new SecretKeySpec(key, "AES");
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public static String encrypt(String strToEncrypt, String secret)
    {
        try
        {
            setKey(secret);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
        }
        catch (Exception e)
        {
            System.out.println("Error while encrypting: " + e.toString());
        }
        return null;
    }

    public static String decrypt(String strToDecrypt, String secret)
    {
        try
        {
            setKey(secret);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
        }
        catch (Exception e)
        {
            System.out.println("Error while decrypting: " + e.toString());
        }
        return null;
    }



    private static CommandLine getOptions(String[] args){


        Options options = new Options();

        Option embed = new Option("embed", "embed", false, "embed");
        embed.setRequired(false);
        options.addOption(embed);
        Option extract = new Option("extract", "extract", false, "extract");
        extract.setRequired(false);
        options.addOption(extract);
        Option in = new Option("in", "in", true, "bitmap file");
        in.setRequired(false);
        options.addOption(in);
        Option p = new Option("p", "p", true, "bitmap file");
        p.setRequired(false);
        options.addOption(p);
        Option out = new Option("out", "out", true, "bitmap file");
        out.setRequired(false);
        options.addOption(out);
        Option steg = new Option("steg", "steg", true, "<LSB1 | LSB4 | LSBI>");
        steg.setRequired(false);
        options.addOption(steg);
        Option a = new Option("a", "a", true, "<aes128 | aes192 | aes256 | des>");
        a.setRequired(false);
        options.addOption(a);
        Option m = new Option("m", "m", true, " <ecb | cfb | ofb | cbc> ");
        m.setRequired(false);
        options.addOption(m);
        Option pass = new Option("pass", "pass", true, "password");
        pass.setRequired(false);
        options.addOption(pass);


        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd=null;

        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            formatter.printHelp("utility-name", options);

            System.exit(1);
        }
        return cmd;
    }
}
