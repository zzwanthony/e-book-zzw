package com.zzw.entity;

//评论信息表
public class Comment {

  private long cid;//id
  private long uid;//评论用户id
  private long eid;//评论书籍id
  private String context;//评论内容


  public long getCid() {
    return cid;
  }

  public void setCid(long cid) {
    this.cid = cid;
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

  public String getContext() {
    return context;
  }

  public void setContext(String context) {
    this.context = context;
  }

}
