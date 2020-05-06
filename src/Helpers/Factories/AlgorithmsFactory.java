package Helpers.Factories;

import Helpers.*;
import Interfaces.AlgoritmosEsteganografiado;
import Interfaces.EncriptionBase;

public class AlgorithmsFactory {

    private static final String LSB1 = "LSB1";
    private static final String LSB4 = "LSB4";
    private static final String LSBI = "LSBI";

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
