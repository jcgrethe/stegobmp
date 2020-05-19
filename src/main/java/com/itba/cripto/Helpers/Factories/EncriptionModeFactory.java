package com.itba.cripto.Helpers.Factories;


import com.itba.cripto.Helpers.EncryptionModes.*;
import com.itba.cripto.Interfaces.EncriptionMode;

import static com.itba.cripto.Helpers.Constant.Constants.ConstantsValues.*;

public class EncriptionModeFactory
{

    public static EncriptionMode Action(String mode)
    {
        switch (mode.toLowerCase())
        {
            case OFB: return new EncryptionModeHelper(OFB.toUpperCase());
            case CBC: return new EncryptionModeHelper(CBC.toUpperCase());
            case CFB: return new EncryptionModeHelper(CFB.toUpperCase());
            case ECB: return new EncryptionModeHelper(ECB.toUpperCase());
            default: throw new IllegalArgumentException("illegal mode");
        }
    }
}
