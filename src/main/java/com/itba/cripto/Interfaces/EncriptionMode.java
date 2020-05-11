package com.itba.cripto.Interfaces;

public interface EncriptionMode {

    public String encrypt(String strToEncrypt, String secret);
    public String decrypt(String strToDecrypt, String secret);
}
