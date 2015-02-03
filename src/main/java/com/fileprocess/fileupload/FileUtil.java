package com.fileprocess.fileupload;

import com.fileprocess.conf.ConfigureParser;

import java.io.BufferedReader;
import java.io.FileReader;

/**
 * Created with IntelliJ IDEA.
 * User: hadoop
 * Date: 15-1-19
 * Time: 下午3:01
 * To change this template use File | Settings | File Templates.
 */
public class FileUtil {
    public static String getFileFullName(String fileNameInputForPass) {
        String fileNameInput = fileNameInputForPass.replace("\\", "/");
        String fileNameOutput = fileNameInput.substring(
                fileNameInput.lastIndexOf('/') + 1, fileNameInput.length());

        return fileNameOutput;
    }
    public String readFile(String fileName){
       StringBuilder sb=new StringBuilder();
       try{
        FileReader fr=new FileReader(fileName);
        BufferedReader bf=new BufferedReader(fr);
        String line=null;
        while((line=bf.readLine())!=null){
           sb.append(line+"\n");
        }
        bf.close();
        fr.close();
    }catch (Exception e){
           e.printStackTrace();
    }
        return sb.toString();
    }
    public static String defaultPath() {
        return ConfigureParser.getPros().get("defaultPath").toString();
    }
    public static void main(String[] args){
        FileUtil fileUtil=new FileUtil();
        System.out.println(fileUtil.readFile("E://download//mergeFile_2015-01-20.txt"));
    }
}
