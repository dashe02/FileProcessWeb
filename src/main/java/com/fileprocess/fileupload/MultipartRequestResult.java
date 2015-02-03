package com.fileprocess.fileupload;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: hadoop
 * Date: 15-1-21
 * Time: 下午5:07
 * To change this template use File | Settings | File Templates.
 */
public class MultipartRequestResult {
    private Map<String, String> params = Maps.newHashMap();
    private List<FileInfo> fileInfos = Lists.newArrayList();

    public Map<String, String> getParams() {
        return params;
    }

    public List<FileInfo> getFileInfos() {
        return fileInfos;
    }
}
