package com.itba.cripto.Interfaces;

import com.itba.cripto.Models.Image;

public interface AlgoritmosEsteganografiado {

    public byte[] hide(byte[] img, byte[] file);

    public byte[] looking(byte[] img);
}
