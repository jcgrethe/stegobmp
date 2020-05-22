package com.itba.cripto.Helpers.Factories;

import static com.itba.cripto.Helpers.Constant.Constants.ConstantsValues.*;

public class SchemeFactory {

    public static int GetScheme(String scheme) {
        switch (scheme) {
            case AES128:
                return 16;
            case AES192:
                return 24;
            case AES256:
                return 32;
            case DES:
                return 8;
            default:
                return -1;
        }
    }
}
