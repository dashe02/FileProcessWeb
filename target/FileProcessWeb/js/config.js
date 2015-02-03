/**
 * Created with IntelliJ IDEA.
 * User: hadoop
 * Date: 15-1-16
 * Time: 下午4:54
 * To change this template use File | Settings | File Templates.
 */
(function($){
    $.URL={
        "user":{
            "login":"rs/user/login",
            "getSession":"rs/user/getSession",
            "exit":"rs/user/exit",
            "isExistUser":"rs/user/isExistUser",
            "getUserInfo":"rs/user/getUserInfo",
            "register":"rs/user/register"
        },
        "file":{
            "upload":"rs/file/upload",
            "changeStatus":"rs/file/changeStatus",
            "getFileStatus":"rs/file/getFileStatus",
            "getUploadedFiles":"rs/file/getUploadedFiles",
            "mergeFile":"rs/file/mergeFile",
            "dealFileByLine":"rs/file/dealFileByLine",
            "getDownloadFiles":"rs/file/getDownloadFiles",
            "preDownloadFile":"rs/file/preDownloadFile",
            "downloadFile":"rs/file/downloadFile"
        }
    }
})(jQuery);