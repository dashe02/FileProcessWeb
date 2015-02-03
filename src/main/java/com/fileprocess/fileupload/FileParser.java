package com.fileprocess.fileupload;

import com.fileprocess.exception.MultipartRequestException;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.Locale;

/**
 * Created with IntelliJ IDEA.
 * User: hadoop
 * Date: 15-1-19
 * Time: 下午2:53
 * To change this template use File | Settings | File Templates.
 */
public class FileParser {

    private static final String UTF8 = "UTF-8";

    private static final long MEGA_SIZE = 1024l;

    private static final int MAX_FILE_NAME_LENGTH = 80;

    private static final int EMPTY_DATA_SIZE = 0;

     public FileInfo parseRequest(HttpServletRequest request,int maxSize) throws Exception{

         long byteMaxSize = MEGA_SIZE * MEGA_SIZE * maxSize;

         FileInfo fileInfo=null;

         List<FileItem> items=this.parseRequest(request);

         if(items==null){
             return fileInfo;
         }

         for(FileItem item:items){
             String fileItemName=item.getName();
             if(!item.isFormField()&&fileItemName!=null){
                 String fileName=FileUtil.getFileFullName(fileItemName);
                 this.fileValidate(fileName,item.getSize(),byteMaxSize);
                 InputStream inputStream=item.getInputStream();
                 String content=item.getString();
                 fileInfo=new FileInfo();
                 fileInfo.setInputStream(inputStream);
                 fileInfo.setName(fileName);
                 fileInfo.setContent(content);
             }else {

             }
         }
         return fileInfo;
     }
     private List<FileItem> parseRequest(HttpServletRequest request) throws Exception{
         FileItemFactory factory=new DiskFileItemFactory();
         ServletFileUpload upload=new ServletFileUpload(factory);
         return upload.parseRequest(request);
     }
    private void fileValidate(String fileName,
                              long itemSize, long byteMaxSize) {

        // 如果文件为空就停止处理
        if (itemSize <= EMPTY_DATA_SIZE) {
            throw new MultipartRequestException(fileName + "文件内容为空");
        }

        // 如果文件超过20m就停止处理
        if (itemSize > byteMaxSize) {
            throw new MultipartRequestException(fileName + "文件上传太大");
        }

        // 简单类型验证
       /* if (!fileService.isMagazineUploadable(fileName)) {
            throw new MultipartRequestException(fileName + "文件类型不允许");
        }*/

        // 简单类型验证
        if (fileName.length() > MAX_FILE_NAME_LENGTH) {
            throw new MultipartRequestException(fileName + "文件名称太长，不能超过80个字符");
        }
    }
    /**
     *
     * @param request
     * @param rootPath
     * @param maxSize
     * @return
     * @throws Exception
     */
    public MultipartRequestResult parse(HttpServletRequest request,
                                        String rootPath, int maxSize,FileService fileService)
            throws Exception {

        long byteMaxSize = MEGA_SIZE * MEGA_SIZE * maxSize;

        MultipartRequestResult result = new MultipartRequestResult();
        if (!ServletFileUpload.isMultipartContent(request)) {
            return result;
        }

        List<FileItem> items = this.parseRequest(request);

        if (items == null) {
            return result;
        }

        for (FileItem item : items) {
            String fileItemName = item.getName();
            if (!item.isFormField() && StringUtils.isNotBlank(fileItemName)) {
                String fileName = FileUtil.getFileFullName(fileItemName);

                this.fileValidate(fileName, item.getSize(),
                        byteMaxSize);

               File dest = fileService.getNewFile(
                        fileName.toLowerCase(Locale.CHINA), rootPath);
                /*File dest=new File(fileName.toLowerCase(Locale.CHINA),rootPath);*/
                item.write(dest);
                String filePath = dest.getPath().replace(File.separatorChar, '/');
                result.getFileInfos().add(new FileInfo(fileName, filePath));
            } else {
                result.getParams().put(item.getFieldName(),
                        item.getString(UTF8));
            }
        }
        return result;
    }
    private String parseFilePath(String destFileAbsolutePath,
                                 String dealedRootPath) {
        return StringUtils.substringAfter(destFileAbsolutePath, dealedRootPath);
    }
}
