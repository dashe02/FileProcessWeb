package com.fileprocess.service;

import com.fileprocess.mapper.UserMapper;
import com.fileprocess.model.User;
import org.springframework.beans.factory.annotation.Autowired;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: hadoop
 * Date: 15-1-22
 * Time: 上午11:14
 * To change this template use File | Settings | File Templates.
 */
public class UserService {
    @Autowired
    private UserMapper mapper;
     public User getUserInfo(Long userId){
         User u=new User();
         u.setId(userId);
         return mapper.get(u);
     }
    public String findPasswordByName(String name){
        return mapper.findPasswordByName(name);
    }
    public Long getIdByName(String name){
        return mapper.getIdByName(name);
    }
    public void updateHeadPhotoById(Long id,String headPhoto){
        mapper.updateHeadPhotoById(id, headPhoto);
    }
    public int register(User user){
        return mapper.register(user);
    }
    public User findByName(String name){
        return mapper.findByName(name);
    }
}
