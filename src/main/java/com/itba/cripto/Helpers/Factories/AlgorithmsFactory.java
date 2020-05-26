package com.itba.cripto.Helpers.Factories;


import com.itba.cripto.Helpers.StegoAlghoritm.LSB1Helper;
import com.itba.cripto.Helpers.StegoAlghoritm.LSB4Helper;
import com.itba.cripto.Helpers.StegoAlghoritm.LSBIHelper;
import com.itba.cripto.Interfaces.SteganographyAlgorithm;

import static com.itba.cripto.Helpers.Constant.Constants.ConstantsValues.*;

public class AlgorithmsFactory {

    public static SteganographyAlgorithm type(String type) {
        switch (type) {
            case LSB1:
                return new LSB1Helper();
            case LSB4:
                return new LSB4Helper();
            case LSBI:
                return new LSBIHelper();
            default:
                throw new IllegalArgumentException("Invalid Steganography Algorithm");
        }
    }
}
