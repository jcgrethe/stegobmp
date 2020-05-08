package Helpers.Factories;

import Helpers.*;
import Interfaces.AlgoritmosEsteganografiado;
import Interfaces.EncriptionBase;

import static Helpers.Constant.Constants.CosntantsValues.*;

public class AlgorithmsFactory {

    public AlgoritmosEsteganografiado Action(String type)
    {
        switch (type)
        {
            case LSB1: return new LSB1Helper();
            case LSB4: return new LSB4Helper();
            case LSBI: return new LSBIHelper();
            default: return null;
        }
    }
}
