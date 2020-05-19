package com.itba.cripto.Interfaces;

public interface EncriptionMode {

    public String encrypt(String strToEncrypt, String secret, String scheme);
    public String decrypt(String strToDecrypt, String secret, String scheme);
}
