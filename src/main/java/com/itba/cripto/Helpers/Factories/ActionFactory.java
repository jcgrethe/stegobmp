package com.itba.cripto.Helpers.Factories;

import Helpers.Constant.Constants;
import com.itba.cripto.Helpers.DecryptHelper;
import com.itba.cripto.Helpers.EncriptionHelper;
import Interfaces.EncriptionBase;

import static Helpers.Constant.Constants.CosntantsValues.DECRYPT;
import static Helpers.Constant.Constants.CosntantsValues.ENCRYPT;


public class ActionFactory {

    public EncriptionBase Action(String type)
    {
        if(type.compareTo(ENCRYPT) == 0)
        {
            return new EncriptionHelper();
        }
        else if(type.compareTo(DECRYPT) == 0)
        {
            return new DecryptHelper();
        }
        else
            return null;
    }
}
