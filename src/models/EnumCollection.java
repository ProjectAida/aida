/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

/**
 *
 * @author Mike
 */
public class EnumCollection {
    // Enumurator for the Image Types
    private enum EnumImageType{
        TIFF, JPEG
    }
    // Enumurator for the T/F
    private enum EnumTrueFalse{
        FALSE, TRUE
    }
    
    public int GetIntOfImageType(String s){
        return EnumImageType.valueOf(s.toUpperCase()).ordinal();
    }
    public int GetIntOfTrueFalse(String s){
        return EnumTrueFalse.valueOf(s.toUpperCase()).ordinal();
    }
}
