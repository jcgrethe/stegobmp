package Helpers.Factories;

import Helpers.CBCHelper;
import Helpers.CFBHelper;
import Helpers.ECBHelper;
import Helpers.OFBHelper;
import Interfaces.EncriptionMode;

import static Helpers.Constant.Constants.CosntantsValues.*;

public class EncriptionModeFactory
{

    public EncriptionMode Action(String mode)
    {
        switch (mode)
        {
            case OFB: return new OFBHelper();
            case CBC: return new CBCHelper();
            case CFB: return new CFBHelper();
            case ECB: return new ECBHelper();
            default: return null;
        }
    }
}
