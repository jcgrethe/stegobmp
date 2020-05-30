package com.itba.cripto;

import com.itba.cripto.Helpers.EncryptionModes.EncryptionModeHelper;

import java.nio.charset.StandardCharsets;

public class Test {
    public static void main(String[] args){

        String hola = "hola";
        EncryptionModeHelper encryptionModeHelper = new EncryptionModeHelper("ofb");
        String fileEncrypted = encryptionModeHelper.encrypt(hola.getBytes(),"secreto","aes256");
        byte[] bytesResp = encryptionModeHelper.decrypt(fileEncrypted.getBytes(),"secreto","aes256");

        String hola2 = new String(bytesResp, StandardCharsets.UTF_8);

        System.out.println(hola);
        System.out.println(hola2);
    }
}
