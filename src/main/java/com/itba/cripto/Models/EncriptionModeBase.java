package com.itba.cripto.Models;

import com.itba.cripto.Interfaces.AlgoritmosEsteganografiado;
import com.itba.cripto.Interfaces.EncriptionMode;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public abstract class EncriptionModeBase {

    private AlgoritmosEsteganografiado Algorithm;
    private EncriptionMode Encripter;
}
