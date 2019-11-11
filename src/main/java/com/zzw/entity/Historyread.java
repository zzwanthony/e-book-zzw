package com.zzw.entity;

//历史记录表
public class Historyread {

  private long id;//
  private long uid;//用户id
  private long eid;//电子书id
  private String offset;//阅读到的章节及偏移量


  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }


  public long getUid() {
    return uid;
  }

  public void setUid(long uid) {
    this.uid = uid;
  }


  public long getEid() {
    return eid;
  }

  public void setEid(long eid) {
    this.eid = eid;
  }


  public String getOffset() {
    return offset;
  }

  public void setOffset(String offset) {
    this.offset = offset;
  }

}
