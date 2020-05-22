package com.itba.cripto.Helpers.Factories;


import com.itba.cripto.Helpers.EncryptionModes.EncryptionModeHelper;

import static com.itba.cripto.Helpers.Constant.Constants.ConstantsValues.*;

public class EncryptionModeFactory {

    public static EncryptionModeHelper Action(String mode) {
        switch (mode.toLowerCase()) {
            case OFB:
                return new EncryptionModeHelper(OFB.toUpperCase());
            case CBC:
                return new EncryptionModeHelper(CBC.toUpperCase());
            case CFB:
                return new EncryptionModeHelper(CFB.toUpperCase());
            case ECB:
                return new EncryptionModeHelper(ECB.toUpperCase());
            default:
                throw new IllegalArgumentException("illegal mode");
        }
    }
}
