package com.itba.cripto.Models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileToEncrypt {

    private int LengthFile;
    private String Extention;
    private String Data;

    public FileToEncrypt(){}
    public FileToEncrypt(int length,String extention,String data)
    {
        this.LengthFile = length;
        this.Extention = extention;
        this.Data = data;
    }
}
