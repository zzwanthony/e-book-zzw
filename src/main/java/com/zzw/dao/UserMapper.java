package com.zzw.dao;

import com.zzw.entity.Bookshelf;
import com.zzw.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {
    User userLogin(@Param("username") String username, @Param("password") String password);

    Integer userAdd(User user);

    String phoneExit(String phone);

    List<Bookshelf> getBookshelf(long uid);

    List<User> getAllUser();

    Integer modifyUser(User user);

    Integer modifyPassword(@Param("uid")long uid, @Param("password")String newPassword);

    Integer deleteUser(int uid);

    List<User> userSearch(@Param("uname") String uname,@Param("phone") String phone);

    //书架查询是否存在
    Bookshelf isExistShelf(@Param("uid")long uid,@Param("txtSn") String gettSn);
    //收藏书籍
    Integer collectBook(Bookshelf bookshelf);
    //已存在时，更改书架
    Integer updateBookshelf(Bookshelf existShelf);

    Integer deleteShelf(int id);

    Integer deleteAllShelf(@Param("ids") int[] ids);

    List<User> getAdminFace();

    Integer faceRegister(@Param("uid")long uid,@Param("faceInfo") String faceInfo);
}
