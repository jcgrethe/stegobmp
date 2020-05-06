package Helpers.Factories;

import Helpers.DecryptHelper;
import Helpers.EncriptionHelper;
import Interfaces.EncriptionBase;

public class ActionFactory {

    private static final String ENCRYPT = "-embed";
    private static final String DECRYPT = "-extract";

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
