package com.itba.cripto.Helpers.Factories;


import com.itba.cripto.Helpers.EncryptionModes.CBCHelper;
import com.itba.cripto.Helpers.EncryptionModes.CFBHelper;
import com.itba.cripto.Helpers.EncryptionModes.ECBHelper;
import com.itba.cripto.Helpers.EncryptionModes.OFBHelper;
import com.itba.cripto.Interfaces.EncriptionMode;

import static com.itba.cripto.Helpers.Constant.Constants.ConstantsValues.*;

public class EncriptionModeFactory
{

    public static EncriptionMode Action(String mode)
    {
        switch (mode.toLowerCase())
        {
            case OFB: return new OFBHelper();
            case CBC: return new CBCHelper();
            case CFB: return new CFBHelper();
            case ECB: return new ECBHelper();
            default: throw new IllegalArgumentException("illegal mode");
        }
    }
}
