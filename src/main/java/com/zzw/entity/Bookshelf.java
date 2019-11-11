package com.zzw.entity;


public class Bookshelf {

  private long id;
  private String tSn;
  private long uid;
  private long chapternum;
  private String chapter;

  private String bookName;
  private String author;

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String gettSn() {
    return tSn;
  }

  public void settSn(String tSn) {
    this.tSn = tSn;
  }

  public long getUid() {
    return uid;
  }

  public void setUid(long uid) {
    this.uid = uid;
  }


  public long getChapternum() {
    return chapternum;
  }

  public void setChapternum(long chapternum) {
    this.chapternum = chapternum;
  }


  public String getChapter() {
    return chapter;
  }

  public void setChapter(String chapter) {
    this.chapter = chapter;
  }

}
