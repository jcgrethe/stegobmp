package com.itba.cripto.Helpers.Factories;

import Helpers.*;
import Interfaces.AlgoritmosEsteganografiado;
import Interfaces.EncriptionBase;

import static Helpers.Constant.Constants.CosntantsValues.*;

public class AlgorithmsFactory {

    public AlgoritmosEsteganografiado Action(String type)
    {
        if(type.compareTo(LSB1) == 0)
        {
            return new LSB1Helper();
        }
        else if(type.compareTo(LSB4) == 0)
        {
            return new LSB4Helper();
        }
        else if(type.compareTo(LSBI) == 0)
        {
            return new LSBIHelper();
        }
        else
            return null;
    }
}
