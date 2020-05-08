package com.itba.cripto.Helpers.Factories;

import com.itba.cripto.Interfaces.EncriptionBase;
import com.itba.cripto.Helpers.EncriptionHelper;
import com.itba.cripto.Helpers.DecryptHelper;

import static com.itba.cripto.Helpers.Constant.Constants.CosntantsValues.*;



public class ActionFactory {

    public EncriptionBase Action(String type) {
        switch (type) {
            case ENCRYPT:
                return new EncriptionHelper();
            case DECRYPT:
                return new DecryptHelper();
            default:
                return null;
        }
    }
}
