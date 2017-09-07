/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.util.*;
import java.io.*;
/**
 *
 * @author Mike
 */
public class ReadIni {
    private EnumCollection myEnums = new EnumCollection();
    // Members
    private int ImageType;
    private int NeedBinarizing;
    private int NeedBlurring;
    private int NeedConsolidating;
    
    // Getters
    public int GetImageType(){
        return this.ImageType;
    }
    public int GetNeedBinarizing(){
        return this.NeedBinarizing;
    }
    public int GetNeedBlurring(){
        return this.NeedBlurring;
    }
    public int GetNeedConsolidating(){
        return this.NeedConsolidating;
    }
    
    // Load Init Info
    public ReadIni(){
        Properties p = new Properties();
        try{
            p.load(new FileInputStream("/Users/Mike/NetBeansProjects/aida/user.ini"));
            // Load members' info from user.ini file
            this.ImageType = myEnums.GetIntOfImageType(p.getProperty("ImageType").toUpperCase());
            this.NeedBinarizing = myEnums.GetIntOfTrueFalse(p.getProperty("NeedBinarizing").toUpperCase());
            this.NeedBlurring = myEnums.GetIntOfTrueFalse(p.getProperty("NeedBlurring").toUpperCase());
            this.NeedConsolidating = myEnums.GetIntOfTrueFalse(p.getProperty("NeedConsolidating").toUpperCase());
        }
        catch (Exception e){
            System.out.println(e);
        }
    }
}
