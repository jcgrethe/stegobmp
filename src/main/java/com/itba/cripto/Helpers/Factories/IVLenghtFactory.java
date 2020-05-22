package com.itba.cripto.Helpers.Factories;

import static com.itba.cripto.Helpers.Constant.Constants.ConstantsValues.DES;

public class IVLenghtFactory {

    public static int GetIVLenght(String mode) {
        switch (mode) {
            case DES:
                return 8;
            default:
                return 16;
        }
    }
}
