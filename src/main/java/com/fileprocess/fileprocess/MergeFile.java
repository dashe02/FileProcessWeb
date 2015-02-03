package com.fileprocess.fileprocess;

import com.fileprocess.conf.ConfigureParser;
import com.fileprocess.util.StringUtil;

import java.io.*;
import java.util.Date;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: hadoop
 * Date: 15-1-20
 * Time: 下午1:59
 * To change this template use File | Settings | File Templates.
 */
public class MergeFile {
    private static String uploadDir=ConfigureParser.getPros().get("uploadPath").toString();
    private static String writeFileDir= ConfigureParser.getPros().get("downloadPath").toString();
    private static  String writeFile=writeFileDir+"//mergeFile_"+ StringUtil.parseDate(new Date())+".txt";
    public boolean mergeFile(String[] files){
            boolean flag=false;
            File f=new File(writeFileDir);
            if(!f.exists()){
               f.mkdirs();
             }
            try{
             FileWriter fw=new FileWriter(writeFile);
             BufferedWriter writer=new BufferedWriter(fw);
            for(int i=0;i<files.length;i++){
                   if(!files[i].equals("已上传文件")){
                       String fileName=uploadDir+"//"+files[i];
                       FileReader fr=new FileReader(fileName);
                       BufferedReader bf=new BufferedReader(fr);
                       String line=null;
                       while((line=bf.readLine())!=null){
                           writer.write(line);
                           writer.newLine();
                       }
                       bf.close();
                       fr.close();
                   }
            }
                writer.close();
                fw.close();
                flag=true;
            }catch (Exception e){
            e.printStackTrace();
        }
           return flag;
    }
}
