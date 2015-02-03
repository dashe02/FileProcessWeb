package com.fileprocess.mapper;

import com.fileprocess.model.User;
import com.fileprocess.orm.AbstractMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: hadoop
 * Date: 15-1-22
 * Time: 下午3:15
 * To change this template use File | Settings | File Templates.
 */
public interface UserMapper extends AbstractMapper<User>{
    public String findPasswordByName(@Param("name")String name);
    public List<User> getUserInfo();
    public Long getIdByName(@Param("name")String name);
    public void updateHeadPhotoById(@Param("id")Long id,@Param("headPhoto")String headPhoto);
    public int register(User user);
    public User findByName(@Param("name") String name);
}
