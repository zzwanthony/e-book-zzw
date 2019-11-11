package com.zzw.service;

import com.zzw.entity.Ebook;
import com.zzw.entity.Sort;
import com.zzw.util.TxtChapterMassage;

import java.util.List;

public interface BookService {

    Integer saveBook(Ebook book);

    TxtChapterMassage readChapter(String txtSn, Integer page);

    Ebook getAllChapter(String txtSn);

    List<Ebook> findBook(String keyword);

    Integer deleteBook(Integer txtSn);

    List<Ebook> getAllBook();

    List<Sort> getAllSort();

    Ebook getBookByID(Integer eid);

    Ebook getBookByTxtsn(String txtSn);

    List<Ebook> findBookBySort(int sortID);

    Sort findSort(String sortName);

    List<Ebook> bookSearch(String bookName, String start, String end);

    Integer deleteBooks(int[] allID);

    List<Ebook> findBookFileName(int[] allID);
}
