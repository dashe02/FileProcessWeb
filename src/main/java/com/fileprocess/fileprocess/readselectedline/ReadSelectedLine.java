package com.fileprocess.fileprocess.readselectedline;

import com.fileprocess.conf.ConfigureParser;
import com.fileprocess.util.StringUtil;

import java.io.*;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: hadoop
 * Date: 14-11-13
 * Time: 下午2:00
 * To change this template use File | Settings | File Templates.
 */
public class ReadSelectedLine {
    private static String readFileDir=ConfigureParser.getPros().get("uploadPath").toString();
    private static String writeFileDir= ConfigureParser.getPros().get("downloadPath").toString();
    private static  String writeFile=writeFileDir+"//readByLineFile_"+ StringUtil.parseDate(new Date())+".txt";
    // 读取文件指定行。
    static String readAppointedLineNumber(File sourceFile, int lineNumber)
            throws IOException {
        FileReader in = new FileReader(sourceFile);
        LineNumberReader reader = new LineNumberReader(in);
        String s = "";
        if (lineNumber <= 0 || lineNumber > getTotalLines(sourceFile)) {
            System.out.println("不在文件的行数范围(1至总行数)之内。");
            System.exit(0);
        }
        int lines = 0;
        String sb=new String();
        while (s != null) {
            lines++;
            s = reader.readLine();
            if((lines - lineNumber) == 0) {
                System.out.println(s);
                sb=s;
            }
        }
        reader.close();
        in.close();
        return sb;
    }
    // 文件内容的总行数。
    static int getTotalLines(File file) throws IOException {
        FileReader in = new FileReader(file);
        LineNumberReader reader = new LineNumberReader(in);
        String s = reader.readLine();
        int lines = 0;
        while (s != null) {
            lines++;
            s = reader.readLine();
        }
        reader.close();
        in.close();
        return lines;
    }
    public static void readSelectLines(String[] sourceFilePaths,String targetFilePath,int lineSpace) throws IOException{
        // 指定读取的行号
        //int lineNumber = 25;
        // 读取文件
        File targetFile = new File(targetFilePath);
        FileWriter out=new FileWriter(targetFile);
        BufferedWriter buf=new BufferedWriter(out);
        for(int i=0;i<sourceFilePaths.length;i++){
           if(!sourceFilePaths[i].equals("已上传文件")){
               String fileName=readFileDir+"//"+sourceFilePaths[i];
               File sourceFile = new File(fileName);
               int totalLine=getTotalLines(sourceFile);
               List<String> list=new ArrayList<String>();
               for(int lineNumber=lineSpace;lineNumber<=totalLine;lineNumber+=lineSpace){
                   String s=readAppointedLineNumber(sourceFile, lineNumber);
                   list.add(s);
               }
               Iterator it=list.iterator();
               while(it.hasNext()){
                   buf.write(it.next().toString());
                   buf.newLine();
               }
           }
        }
        buf.flush();
        buf.close();
        out.close();
    }
    public static boolean execute(String[] files,int line) throws IOException{
        File f=new File(writeFileDir);
        if(!f.exists()){
            f.mkdirs();
        }
            String targetFilePath=writeFile;
            int lineSpace=line;
            readSelectLines(files,targetFilePath,lineSpace);
        return true;
    }
}