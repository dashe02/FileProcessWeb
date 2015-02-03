package com.fileprocess.fileupload;

import java.io.InputStream;

/**
 * Created with IntelliJ IDEA.
 * User: hadoop
 * Date: 15-1-19
 * Time: 下午2:52
 * To change this template use File | Settings | File Templates.
 */
public class FileInfo {

    private String name;

    private String path;

    private InputStream inputStream;

    private String content;

    public FileInfo(){

    }
    public FileInfo(String name, String path) {
        this.name = name;
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
