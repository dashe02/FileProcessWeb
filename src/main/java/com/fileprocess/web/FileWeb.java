package com.fileprocess.web;

import com.fileprocess.conf.ConfigureParser;
import com.fileprocess.fileprocess.MergeFile;
import com.fileprocess.fileprocess.readselectedline.ReadSelectedLine;
import com.fileprocess.fileupload.*;
import com.fileprocess.util.JsonResultUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: hadoop
 * Date: 15-1-19
 * Time: 上午11:33
 * To change this template use File | Settings | File Templates.
 */
@Path("/file")
public class FileWeb {
    private FileParser fileParser=new FileParser();
    private static String status="1";
    private String uploadDir= ConfigureParser.getPros().get("uploadPath").toString();
    private String downloadDir=ConfigureParser.getPros().get("downloadPath").toString();
    private MergeFile mergeFile=new MergeFile();
    private FileUtil fileUtil=new FileUtil();
    @Produces(MediaType.APPLICATION_JSON+";charset=UTF-8")
    @Path("/upload")
    @POST
    public String upload(@Context HttpServletRequest request){
        List<Map<String,String>> list=new ArrayList<Map<String, String>>();
        if(request==null){
        return JsonResultUtils.getCodeAndMesByStringAsDefault(JsonResultUtils.Code.ERROR);
        }
        try{
        request.setCharacterEncoding("UTF-8");
        int uploadSize=1024;
        FileService fileService=new FileService("txt");
        fileParser.parse(request,uploadDir,uploadSize,fileService);
      /*  FileInfo fileInfo=new FileInfo();

        fileInfo=parseRequest(request,fileParser,uploadSize);
        if(fileInfo.getName()==null){
            //文件名为空
        }else{
          writeFile(fileInfo,uploadDir);
        }*/
        }catch (Exception e){
            e.printStackTrace();
        }
        return JsonResultUtils.getObjectResultByStringAsDefault(list, JsonResultUtils.Code.SUCCESS);
    }
    public FileInfo parseRequest(@Context HttpServletRequest request,FileParser fileParser,int uploadSize){
         FileInfo fileInfo=null;
         try{
             fileInfo=fileParser.parseRequest(request,uploadSize);
         }catch (Exception e){
             e.printStackTrace();
         }
        return fileInfo;
    }
    public String writeFile(FileInfo fileInfo,String uploadDir){
        if(fileInfo==null)
            return null;
        File f=new File(uploadDir);
        if(!f.exists()){
            f.mkdirs();
        }
        String filePath=uploadDir+"//"+fileInfo.getName();
        try{
        File file=new File(filePath);
        FileWriter fileWriter=new FileWriter(file);
        BufferedWriter fw=new BufferedWriter(fileWriter);
        fw.write(fileInfo.getContent());
        fw.close();
        fileWriter.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return filePath;
    }
    @Produces(MediaType.APPLICATION_JSON+";charset=UTF-8")
    @Path("/getFileStatus")
    @POST
    public String getFileStatus(){
        return JsonResultUtils.getObjectResultByStringAsDefault(status,JsonResultUtils.Code.SUCCESS);
    }
    @Produces(MediaType.APPLICATION_JSON+";charset=UTF-8")
    @Path("/changeStatus")
    @POST
    public String changeStatus(@FormParam("status") String s){
        status=s;
        return JsonResultUtils.getCodeAndMesByStringAsDefault(JsonResultUtils.Code.SUCCESS);
    }
    @Produces(MediaType.APPLICATION_JSON+";charset=UTF-8")
    @Path("/getUploadedFiles")
    @POST
    public String getUploadedFiles(){
        List<String> list=new ArrayList<String>();
        File f=new File(uploadDir);
        if(f.exists()){
            File[] files=f.listFiles();
            for(int i=0;i<files.length;i++){
                list.add(files[i].getName());
            }
        }
        return JsonResultUtils.getObjectResultByStringAsDefault(list,JsonResultUtils.Code.SUCCESS);
    }
    @Produces(MediaType.APPLICATION_JSON+";charset=UTF-8")
    @Path("/mergeFile")
    @POST
    public String mergeFile(@FormParam("text") String files,@FormParam("line") String line){   //合并文件
        if(files.contains(",")&&line.equals("0")){
        String[] fileArray=files.split(",");
        if(mergeFile.mergeFile(fileArray)){
            return JsonResultUtils.getCodeAndMesByStringAsDefault(JsonResultUtils.Code.SUCCESS);
        }else{
            return JsonResultUtils.getCodeAndMesByStringAsDefault(JsonResultUtils.Code.ERROR);
        }
        }else{
            return JsonResultUtils.getCodeAndMesByStringAsDefault(JsonResultUtils.Code.ERROR);
        }
    }
    @Produces(MediaType.APPLICATION_JSON+";charset=UTF-8")
    @Path("/dealFileByLine")
    @POST
    public String dealFileByLine(@FormParam("text") String files,@FormParam("line") String line){   //隔行处理文件
        if(files.contains(",")){
        String[] fileArray=files.split(",");
        try{
        if(ReadSelectedLine.execute(fileArray,Integer.parseInt(line))){
            return JsonResultUtils.getCodeAndMesByStringAsDefault(JsonResultUtils.Code.SUCCESS);
        }else{
            return JsonResultUtils.getCodeAndMesByStringAsDefault(JsonResultUtils.Code.ERROR);
        }
        }catch (Exception e){
            e.printStackTrace();
        }
        }else{
            return JsonResultUtils.getCodeAndMesByStringAsDefault(JsonResultUtils.Code.ERROR);
        }
        return null;
    }
    @Produces(MediaType.APPLICATION_JSON+";charset=UTF-8")
    @Path("/getDownloadFiles")
    @POST
    public String getDownloadFiles(){
        List<String> list=new ArrayList<String>();
        File f=new File(downloadDir);
        if(f.exists()){
            File[] files=f.listFiles();
            for(int i=0;i<files.length;i++){
                list.add(files[i].getName());
            }
        }
        return JsonResultUtils.getObjectResultByStringAsDefault(list,JsonResultUtils.Code.SUCCESS);
    }
    @Produces(MediaType.APPLICATION_JSON+";charset=UTF-8")
    @Path("/preDownloadFile")
    @POST
    public String preDownloadFile(@FormParam("text") String downloadFiles){
        List<Map<String,String>> list=new ArrayList<Map<String, String>>();
        if(downloadFiles.contains(",")){
            String[] downloadFileArray=downloadFiles.split(",");
            for(int i=0;i<downloadFileArray.length;i++){
              if(!downloadFileArray[i].equals("要下载的文件")){
                  String fileName=downloadDir+"//"+downloadFileArray[i];
                  String fileContent=fileUtil.readFile(fileName);
                  Map<String,String>m=new HashMap<String, String>();
                  m.put("id",String.valueOf(i));
                  m.put("name",downloadFileArray[i]);
                  m.put("content",fileContent);
                  list.add(m);
              }
            }
        }
        return JsonResultUtils.getObjectResultByStringAsDefault(list,JsonResultUtils.Code.SUCCESS);
    }
    @Produces(MediaType.APPLICATION_JSON+";charset=UTF-8")
    @Path("/downloadFile/{title}")
    @GET
    public void downloadFile(@PathParam("title") String downloadFile,@Context HttpServletResponse response){
        String fileName=downloadDir+"//"+downloadFile;
        String result =fileUtil.readFile(fileName);
        if(result!=""){
            try{
                response.setContentType("text/plain;charset=utf-8");
                response.setHeader("Location",downloadFile);
                response.setHeader("Content-Disposition","attachment;filename="+new String(downloadFile.getBytes("utf-8"),"ISO8859-1"));
                OutputStream outputStream = response.getOutputStream();
                outputStream.write(result.getBytes("utf-8"));
                outputStream.flush();
                outputStream.close();
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
    }


}
