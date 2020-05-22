package com.itba.cripto.Interfaces;

public interface SteganographyAlgorithm {

    byte[] hide(byte[] img, byte[] file);

    byte[] looking(byte[] img);
}
