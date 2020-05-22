package com.itba.cripto.Models;

import com.itba.cripto.Interfaces.SteganographyAlgorithm;
import com.itba.cripto.Interfaces.EncriptionMode;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public abstract class EncriptionModeBase {

    private SteganographyAlgorithm algorithm;
    private EncriptionMode encrypter;
}
