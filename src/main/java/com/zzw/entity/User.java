package com.zzw.entity;

//用户表
public class User {

  private long uid;//用户id
  private String uname;//用户名
  private String phone;//用户手机号
  private String password;//登入密码
  private String sex;//性别
  private java.sql.Date birth;//生日
  private long userType;//用户类型（1：管理员，0：阅读者）
  private String bookRack;//书架
  private String likeBook;//已点赞书籍
  private String faceInfo;//管理员脸部信息

  public String getFaceInfo() {
    return faceInfo;
  }

  public void setFaceInfo(String faceInfo) {
    this.faceInfo = faceInfo;
  }

  public long getUid() {
    return uid;
  }

  public void setUid(long uid) {
    this.uid = uid;
  }


  public String getUname() {
    return uname;
  }

  public void setUname(String uname) {
    this.uname = uname;
  }


  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }


  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }


  public String getSex() {
    return sex;
  }

  public void setSex(String sex) {
    this.sex = sex;
  }


  public java.sql.Date getBirth() {
    return birth;
  }

  public void setBirth(java.sql.Date birth) {
    this.birth = birth;
  }


  public long getUserType() {
    return userType;
  }

  public void setUserType(long userType) {
    this.userType = userType;
  }

  public String getBookRack() {
    return bookRack;
  }

  public void setBookRack(String bookRack) {
    this.bookRack = bookRack;
  }

  public String getLikeBook() {
    return likeBook;
  }

  public void setLikeBook(String likeBook) {
    this.likeBook = likeBook;
  }

  @Override
  public String toString() {
    return "User{" +
            "uid=" + uid +
            ", uname='" + uname + '\'' +
            ", phone='" + phone + '\'' +
            ", password='" + password + '\'' +
            ", sex='" + sex + '\'' +
            ", birth=" + birth +
            ", userType=" + userType +
            ", bookRack='" + bookRack + '\'' +
            ", likeBook='" + likeBook + '\'' +
            '}';
  }
}
