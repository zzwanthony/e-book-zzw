package com.zzw.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.List;

//电子书信息表
@Entity
@Table(indexes = @Index(name = "index_txtSn", columnList = "txtSn", unique = true))
public class Ebook implements Serializable {
    @Id
    @GeneratedValue
    private long eid;//电子书id
    private String ename;//书名
    private String author;//作者
    private String translator;//翻译人
    private String txtName;//txt文件名
    private String coverName;//封面图片文件名
    private long likeNum;//点赞人数
    private long collectNum;//收藏人数
    private long readerNum;//阅读人数
    private Date uploadData;//上传日期
    private String introduction;//简介
    private String title;//目录
    private String chapter;//章节下标
    private String nioOffset;//nio流操作时章节头的byte偏移量
    private String offset;
    private long uid;//上传用户id
    private long sid;//分类id
    private String txtSn;

    private String uname;

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public long getEid() {
        return eid;
    }

    public void setEid(long eid) {
        this.eid = eid;
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTranslator() {
        return translator;
    }

    public void setTranslator(String translator) {
        this.translator = translator;
    }

    public String getTxtName() {
        return txtName;
    }

    public void setTxtName(String txtName) {
        this.txtName = txtName;
    }

    public String getCoverName() {
        return coverName;
    }

    public void setCoverName(String coverName) {
        this.coverName = coverName;
    }

    public long getLikeNum() {
        return likeNum;
    }

    public void setLikeNum(long likeNum) {
        this.likeNum = likeNum;
    }

    public long getCollectNum() {
        return collectNum;
    }

    public void setCollectNum(long collectNum) {
        this.collectNum = collectNum;
    }

    public long getReaderNum() {
        return readerNum;
    }

    public void setReaderNum(long readerNum) {
        this.readerNum = readerNum;
    }

    public Date getUploadData() {
        return uploadData;
    }

    public void setUploadData(Date uploadData) {
        this.uploadData = uploadData;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getChapter() {
        return chapter;
    }

    public void setChapter(String chapter) {
        this.chapter = chapter;
    }

    public String getNioOffset() {
        return nioOffset;
    }

    public void setNioOffset(String nioOffset) {
        this.nioOffset = nioOffset;
    }

    public Long[] getNioOffsets() {
        if (getNioOffset() == null || getNioOffset().isEmpty()) return null;
        String[] chapters = getNioOffset().split(",");
        Long[] longs = new Long[chapters.length];
        for (int i = 0; i < chapters.length; i++) {
            longs[i] = new Long(chapters[i]);
        }
        return longs;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public long getSid() {
        return sid;
    }

    public void setSid(long sid) {
        this.sid = sid;
    }

    public String getTxtSn() {
        return txtSn;
    }

    public void setTxtSn(String txtSn) {
        this.txtSn = txtSn;
    }

    public String getOffset() {
        return offset;
    }

    public void setOffset(String offset) {
        this.offset = offset;
    }

    //不会被映射到表字段中
    @Transient
    private Integer[] chapters;
    @Transient
    private Long[] offsets;
    @Transient
    private String[] titles;
    @Transient
    private Long[] nioOffsets;

    public String[] getTitles() {
        return getTitle().split(",");
    }

    public void setTitles(String[] titles) {
        this.titles = titles;
    }

    public Integer[] getChapters() {
        String[] chapters = getChapter().split(",");
        Integer[] integers = new Integer[chapters.length];
        for (int i = 0; i < chapters.length; i++) {
            integers[i] = new Integer(chapters[i]);
        }
        return integers;
    }

    public Long[] getOffsets() {
        if (getOffset() == null || getOffset().isEmpty()) return null;
        String[] chapters = getOffset().split(",");
        Long[] longs = new Long[chapters.length];
        for (int i = 0; i < chapters.length; i++) {
            longs[i] = new Long(chapters[i]);
        }
        return longs;
    }

    public void addChapter(List chapter, List offset, List title, List nioOffset) {
        StringBuffer sb = new StringBuffer();
        for (Object cha : chapter) {
            sb.append(cha + ",");
        }
        sb.deleteCharAt(sb.length() - 1);
        String str1 = sb.toString();
        sb.delete(0, sb.length());
        if (offset != null) {
            for (Object off : offset) {
                sb.append(off + ",");
            }
            sb.deleteCharAt(sb.length() - 1);
        }
        String str2 = sb.toString();
        sb.delete(0, sb.length());
        for (Object tit : title) {
            sb.append(tit + ",");
        }
        sb.deleteCharAt(sb.length() - 1);
        String str3 = sb.toString();
        sb.delete(0, sb.length());
        if (nioOffset != null) {
            for (Object nio : nioOffset) {
                sb.append(nio + ",");
            }
            sb.deleteCharAt(sb.length() - 1);
        }
        String str4 = sb.toString();
        if (this.chapter == null) {
            this.chapter = str1;
            this.offset = str2;
            this.title = str3;
            this.nioOffset = str4;
        } else {
            this.chapter += str1;
            this.offset += str2;
            this.title += str3;
            this.nioOffset += str4;
        }
    }

    public void pathChapters(Integer[] chapters, Long[] offsets, String[] titles, Long[] nioOffsets) {
        this.chapters = chapters;
        this.offsets = offsets;
        this.titles = titles;
        this.nioOffsets = nioOffsets;
    }

    @Override
    public String toString() {
        return "Ebook{" +
                "eid=" + eid +
                ", ename='" + ename + '\'' +
                ", author='" + author + '\'' +
                ", translator='" + translator + '\'' +
                ", txtName='" + txtName + '\'' +
                ", coverName='" + coverName + '\'' +
                ", likeNum=" + likeNum +
                ", collectNum=" + collectNum +
                ", readerNum=" + readerNum +
                ", uploadData=" + uploadData +
                ", introduction='" + introduction + '\'' +
                ", uid=" + uid +
                ", sid=" + sid +
                ", txtSn='" + txtSn + '\'' +
                ", uname='" + uname + '\'' +
                '}';
    }
}
