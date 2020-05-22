package com.itba.cripto.Interfaces;

public interface EncriptionMode {

    public String encrypt(String strToEncrypt, String secret, String scheme);
    public byte[] decrypt(byte[] strToDecrypt, String secret, String scheme);
}
