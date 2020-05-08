package com.itba.cripto.Models;

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

    public int GetLengthFile(){ return this.LengthFile;}
    public String GetExtention(){ return this.Extention;}
    public String GetData(){ return this.Data;}

    public void SetLengthFile(int length){  this.LengthFile = length;}
    public void SetExtention(String extention){  this.Extention = extention;}
    public void SetData(String data){  this.Data = data;}

}
