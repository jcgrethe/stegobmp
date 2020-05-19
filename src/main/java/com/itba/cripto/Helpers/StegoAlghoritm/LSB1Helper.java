package com.itba.cripto.Helpers.StegoAlghoritm;


import com.itba.cripto.Helpers.Utils.Covertions;
import com.itba.cripto.Interfaces.AlgoritmosEsteganografiado;
import com.itba.cripto.Models.Image;

public class LSB1Helper implements AlgoritmosEsteganografiado {



    public byte[] Hide(byte[] img, byte[] file)
    {

        return null;
    }

    public byte[] Looking(byte[] img){

        int i, j=0;
        byte[] resp = new byte[img.length];

        for(i = 0; i < img.length; i++)
        {
            byte[j++] = Covertions.GetBit(img[i],7);
        }


        return null;
    }

}
