package com.itba.cripto.Helpers.Factories;

import com.itba.cripto.Helpers.EncryptionHelper;
import com.itba.cripto.Helpers.DecryptHelper;
import com.itba.cripto.Models.EncriptionModeBase;

import static com.itba.cripto.Helpers.Constant.Constants.ConstantsValues.*;



public class ActionFactory {

    public static EncriptionModeBase Action(String type) {
        switch (type) {
            case ENCRYPT:
                return new EncryptionHelper();
            case DECRYPT:
                return new DecryptHelper();
            default:
                throw new IllegalArgumentException();
        }
    }
}
