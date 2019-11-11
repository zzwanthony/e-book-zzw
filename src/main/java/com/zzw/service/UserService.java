package com.zzw.service;

import com.zzw.entity.Bookshelf;
import com.zzw.entity.Ebook;
import com.zzw.entity.User;

import java.util.List;

/**
 * @author 11723
 */
public interface UserService{

    List<Ebook> indexBook();

    User userLogin(String username, String password);

    Integer userRegister(String phone, String password);

    String phoneExit(String phone);

    List<Bookshelf> getBookshelf(long uid);

    List<User> getAllUser();

    Integer modifyUser(User user);

    Integer modifyPassword(long uid, String newPassword);

    Integer addUser(User user);

    Integer deleteUser(int uid);

    List<User> userSearch(String uname, String phone);

    Bookshelf isExistShelf(long uid, String gettSn);

    Integer collectBook(Bookshelf bookshelf);

    Integer updateBookshelf(Bookshelf existShelf);

    Integer deleteShelf(int id);

    Integer deleteAllShelf(int[] ids);

    List<User> getAdminFace();

    Integer faceRegister(long uid, String faceInfo);
}
