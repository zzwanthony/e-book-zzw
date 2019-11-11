package com.zzw.util;

/**
 * @author 11723
 */
public class TxtChapterMassage {
    private String ename;
    private String txtSn;
    private Integer chapter;
    private Long offset;
    private Long nioOffset;
    private String title;
    private String article;

    public TxtChapterMassage() {}

    public TxtChapterMassage(String ename, String txtSn, Integer chapter, String title, String article) {
        this.ename = ename;
        this.txtSn = txtSn;
        this.chapter = chapter;
        this.title = title;
        this.article = article;
    }


    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public Long getNioOffset() {
        return nioOffset;
    }

    public void setNioOffset(Long nioOffset) {
        this.nioOffset = nioOffset;
    }

    public Integer getChapter() {
        return chapter;
    }

    public void setChapter(Integer chapter) {
        this.chapter = chapter;
    }

    public Long getOffset() {
        return offset;
    }

    public void setOffset(Long offset) {
        this.offset = offset;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTxtSn() {
        return txtSn;
    }

    public void setTxtSn(String txtSn) {
        this.txtSn = txtSn;
    }

    public String getArticle() {
        return article;
    }

    public void setArticle(String article) {
        this.article = article;
    }
}
