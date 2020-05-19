package com.itba.cripto.Interfaces;

import com.itba.cripto.Models.Image;

public interface AlgoritmosEsteganografiado {

    public byte[] Hide(byte[] img, byte[] file);

    public byte[] Looking(byte[] img);
}
