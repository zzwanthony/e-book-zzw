package com.zzw.dao;

import com.zzw.entity.Ebook;
import com.zzw.entity.Sort;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BookMapper {

    Integer saveBook(Ebook book);

    Ebook readChapter(String txtSn);

    Ebook getAllChapter(String txtSn);

    List<Ebook> findBook(String keyword);

    List<Ebook> indexBook();

    Integer deleteBook(Integer txtSn);

    List<Ebook> getAllBook();

    List<Sort> getAllSort();

    Ebook getBookByID(Integer eid);

    Ebook getBookByTxtsn(String txtSn);

    List<Ebook> findBookBySort(int sortID);

    Sort findSort(String sortName);

    List<Ebook> bookSearch(@Param("bookName") String bookName,@Param("start") String start,@Param("end") String end);

    Integer deleteBooks(@Param("allID")int[] allID);

    List<Ebook> findBookFileName(@Param("allID")int[] allID);
}
