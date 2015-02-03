package com.fileprocess.conf;

import org.glassfish.jersey.internal.util.PropertiesHelper;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: hadoop
 * Date: 15-1-19
 * Time: 下午3:23
 * To change this template use File | Settings | File Templates.
 */
public class ConfigureParser {
    private static String configurePath="/conf.properties";
    public static Properties getPros(){
        Properties pros=new Properties();
        try{
            InputStream in=ConfigureParser.class.getClassLoader().getResourceAsStream(configurePath);
            pros.load(in);
        }catch (Exception e){
            e.printStackTrace();
        }
        return pros;
    }
    public static void main(String[] args){
        System.out.println(ConfigureParser.getPros().get("uploadPath").toString());
    }
}
