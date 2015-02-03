package com.fileprocess.web;

import com.fileprocess.conf.ConfigureParser;
import com.fileprocess.fileupload.FileParser;
import com.fileprocess.fileupload.FileService;
import com.fileprocess.fileupload.MultipartRequestResult;
import com.fileprocess.model.User;
import com.fileprocess.service.UserService;
import com.fileprocess.util.JsonMapper;
import com.fileprocess.util.JsonResultUtils;
import com.fileprocess.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.List;


/**
 * Created with IntelliJ IDEA.
 * User: hadoop
 * Date: 15-1-16
 * Time: 下午4:30
 * To change this template use File | Settings | File Templates.
 */
@Path("/user")
public class UserWeb {
    @Autowired
    private UserService userService;
    private String imageUploadDir= ConfigureParser.getPros().get("imageUploadPath").toString();
    private FileParser fileParser=new FileParser();
    @Context
    HttpServletRequest request;
    @Produces(MediaType.APPLICATION_JSON+";charset=UTF-8")
    @Path("/login")
    @POST
    public String login(@FormParam("userName")String userName,@FormParam("password")String password){
         String pass=userService.findPasswordByName(userName);
         if(password.equals(pass)){
              request.getSession().setAttribute("userName",userName);
              request.getSession().setAttribute("password",password);
              return JsonResultUtils.getObjectResultByStringAsDefault(userName,JsonResultUtils.Code.SUCCESS);
          }else{
           return JsonResultUtils.getCodeAndMesByStringAsDefault(JsonResultUtils.Code.ERROR);
          }
    }
    @Produces(MediaType.APPLICATION_JSON+";charset=UTF-8")
    @Path("/test")
    @GET
    public String test(){
        System.out.println("test");
        return JsonResultUtils.getCodeAndMesByStringAsDefault(JsonResultUtils.Code.SUCCESS);
    }
    @Produces(MediaType.APPLICATION_JSON+";charset=UTF-8")
    @Path("/getSession")
    @POST
    public String getSession(){
          if(request!=null&&request.getSession()!=null&&request.getSession().getAttribute("userName")!=null&&request.getSession().getAttribute("password")!=null){
          User user=new User();
          user.setName(request.getSession().getAttribute("userName").toString());
          user.setPassword(request.getSession().getAttribute("password").toString());
          return JsonResultUtils.getObjectResultByStringAsDefault(user,JsonResultUtils.Code.SUCCESS);
          }else{
          return JsonResultUtils.getCodeAndMesByStringAsDefault(JsonResultUtils.Code.ERROR);
          }
    }

    @Produces(MediaType.APPLICATION_JSON+";charset=UTF-8")
    @Path("/exit")
    @POST
    public String exit(){
        request.getSession().removeAttribute("userName");
        request.getSession().removeAttribute("password");
        return JsonResultUtils.getCodeAndMesByStringAsDefault(JsonResultUtils.Code.SUCCESS);
    }
    @Produces(MediaType.APPLICATION_JSON+";charset=UTF-8")
    @Path("/isExistUser")
    @POST
    public String isExistUser(@FormParam("userName")String userName,@FormParam("password")String password){
        User user=userService.findByName(userName);
        if(user!=null){
            return JsonResultUtils.getCodeAndMesByStringAsDefault(JsonResultUtils.Code.SUCCESS);
        }else{
            return JsonResultUtils.getCodeAndMesByStringAsDefault(JsonResultUtils.Code.ERROR);
        }
    }
    @Produces(MediaType.APPLICATION_JSON+";charset=UTF-8")
    @Path("/getUserInfo")
    @POST
    public String getUserInfo(){
        String name=this.request.getSession().getAttribute("userName").toString();
        Long userId=userService.getIdByName(name);
        User user=userService.getUserInfo(userId);
        return JsonResultUtils.getObjectResultByStringAsDefault(user, JsonResultUtils.Code.SUCCESS);
    }
    @Produces(MediaType.APPLICATION_JSON+";charset=UTF-8")
    @Path("/imageupload")
    @POST
    public String imageUpload(@Context HttpServletRequest request){
        if(request==null){
            return JsonResultUtils.getCodeAndMesByStringAsDefault(JsonResultUtils.Code.ERROR);
        }
        String imageUploadPath=null;
        try{
            request.setCharacterEncoding("UTF-8");
            int uploadMaxSize= 1024;
            FileService fileService=new FileService("jpg");
            MultipartRequestResult mrr=fileParser.parse(request,imageUploadDir,uploadMaxSize,fileService);
            String path=mrr.getFileInfos().get(0).getPath();
            imageUploadPath=StringUtil.removeString(path);
            String name=this.request.getSession().getAttribute("userName").toString();
            Long userId=userService.getIdByName(name);
            userService.updateHeadPhotoById(userId, imageUploadPath);
        }catch (Exception e){
            e.printStackTrace();
        }
        return JsonResultUtils.getObjectResultByStringAsDefault(imageUploadPath,JsonResultUtils.Code.SUCCESS);
    }
    @Produces(MediaType.APPLICATION_JSON+";charset=UTF-8")
    @Path("/register")
    @POST
    public String register(@FormParam("user")String userName,@FormParam("pass")String password){
        User user=new User();
        user.setName(userName);
        user.setPassword(password);
        int result=userService.register(user);
        if(result>0){
            return JsonResultUtils.getCodeAndMesByStringAsDefault(JsonResultUtils.Code.SUCCESS);
        }else{
            return JsonResultUtils.getCodeAndMesByStringAsDefault(JsonResultUtils.Code.ERROR);
        }
    }
}
