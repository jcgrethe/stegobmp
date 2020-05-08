package Helpers.Factories;

import Helpers.*;
import Helpers.Constant.Constants;
import Interfaces.EncriptionBase;

import static Helpers.Constant.Constants.CosntantsValues.*;


public class ActionFactory {

    public EncriptionBase Action(String type)
    {
        switch (type)
        {
            case ENCRYPT: return new EncriptionHelper();
            case DECRYPT: return new DecryptHelper();
            default: return null;
        }
    }
}
