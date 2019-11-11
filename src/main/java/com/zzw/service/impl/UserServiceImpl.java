package com.zzw.service.impl;

import com.zzw.dao.BookMapper;
import com.zzw.dao.UserMapper;
import com.zzw.entity.Bookshelf;
import com.zzw.entity.Ebook;
import com.zzw.entity.User;
import com.zzw.service.UserService;
import com.zzw.util.TxtUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 11723
 */
@Service("userService")
public class UserServiceImpl implements UserService{


    @Resource
    private BookMapper bookMapper;

    @Resource
    private UserMapper userMapper;

    //首页书籍
    @Override
    public List<Ebook> indexBook() {
        return bookMapper.indexBook();
    }

    @Override
    public User userLogin(String username, String password) {
        return userMapper.userLogin(username,password);
    }

    /**
     * 注册
     * @param phone
     * @param password
     * @return
     */
    @Override
    public Integer userRegister(String phone, String password) {
        User user = new User();
        user.setUname("书"+TxtUtil.getStringRandom(7));
        user.setPhone(phone);
        user.setPassword(password);
        user.setUserType(0);
        return userMapper.userAdd(user);
    }

    @Override
    public String phoneExit(String phone) {
        return userMapper.phoneExit(phone);
    }

    @Override
    public List<Bookshelf> getBookshelf(long uid) {
        return userMapper.getBookshelf(uid);
    }

    @Override
    public List<User> getAllUser() {
        return userMapper.getAllUser();
    }

    @Override
    public Integer modifyUser(User user) {
        return userMapper.modifyUser(user);
    }

    @Override
    public Integer modifyPassword(long uid, String newPassword) {
        return userMapper.modifyPassword(uid,newPassword);
    }

    @Override
    public Integer addUser(User user) {
        return userMapper.userAdd(user);
    }

    @Override
    public Integer deleteUser(int uid) {
        return userMapper.deleteUser(uid);
    }

    @Override
    public List<User> userSearch(String uname, String phone) {
        return userMapper.userSearch(uname,phone);
    }

    @Override
    public Bookshelf isExistShelf(long uid, String gettSn) {
        return userMapper.isExistShelf(uid,gettSn);
    }

    @Override
    public Integer collectBook(Bookshelf bookshelf) {
        return userMapper.collectBook(bookshelf);
    }

    @Override
    public Integer updateBookshelf(Bookshelf existShelf) {
        return userMapper.updateBookshelf(existShelf);
    }

    @Override
    public Integer deleteShelf(int id) {
        return userMapper.deleteShelf(id);
    }

    @Override
    public Integer deleteAllShelf(int[] ids) {
        return userMapper.deleteAllShelf(ids);
    }

    @Override
    public List<User> getAdminFace() {
        return userMapper.getAdminFace();
    }

    @Override
    public Integer faceRegister(long uid, String faceInfo) {
        return userMapper.faceRegister(uid, faceInfo);
    }
}
