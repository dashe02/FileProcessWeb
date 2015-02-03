package com.fileprocess.util;

import com.fileprocess.conf.ConfigureParser;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: hadoop
 * Date: 15-1-20
 * Time: 下午2:34
 * To change this template use File | Settings | File Templates.
 */
public class StringUtil {
     public static String parseDate(Date d){
         String result=null;
         SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd_HH_mm_ss");
         try{
             result=format.format(d);
         }catch (Exception e){
             e.printStackTrace();
         }
         return result;
     }
    public static String removeString(String s){
        String result=null;
        String removeString=ConfigureParser.getPros().get("basePath").toString();
        result=s.replace(removeString,"/");
        return result;
    }
    public static void main(String[] args){
        String s="/home/ibm/software/resource/fileprocess/imageupload/2015/01/23/1421982259820.jpg";
        System.out.println(removeString(s));
    }
}
