package com.zzw.service.impl;

import com.zzw.common.config.SystemConfig;
import com.zzw.dao.BookMapper;
import com.zzw.entity.Ebook;
import com.zzw.entity.Sort;
import com.zzw.service.BookService;
import com.zzw.util.TxtChapterMassage;
import com.zzw.util.TxtUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.util.List;

@Service("bookService")
public class BookServiceImpl implements BookService {

    @Resource
    private BookMapper bookMapper;


    @Override
    public Integer saveBook(Ebook book) {
        return bookMapper.saveBook(book);
    }

    @Override
    public TxtChapterMassage readChapter(String txtSn, Integer page) {
        Ebook ebook = bookMapper.readChapter(txtSn);
        //获取当前章节的标题
        String title = ebook.getTitle();
        //获取文件名
        File file = new File(SystemConfig.getTxtPath() + ebook.getTxtName());

        //获取章节内容开头和结尾的偏移量
        String[] offset = ebook.getOffset() == null ? null : ebook.getOffset().split(",");
        String[] nioOffset = ebook.getNioOffset() == null ? null : ebook.getOffset().split(",");

        Integer startOffset = 0;
        Integer endOffset = 0;
        if (endOffset != null) {
            endOffset = Integer.valueOf(nioOffset[page]);
        }
        if (offset != null) {
            startOffset = Integer.valueOf(offset[page]);
        } else if (page > 0) {
            startOffset = Integer.valueOf(nioOffset[page - 1]) + 1;
        }

        return new TxtChapterMassage(ebook.getEname(), txtSn, page, title, TxtUtil.readStartToEndByNio(file, startOffset, endOffset, "GBK"));
    }

    @Override
    public Ebook getAllChapter(String txtSn) {
        return bookMapper.getAllChapter(txtSn);
    }

    @Override
    public List<Ebook> findBook(String keyword) {
        return bookMapper.findBook(keyword);
    }

    @Override
    public Integer deleteBook(Integer txtSn) {
        return bookMapper.deleteBook(txtSn);
    }

    @Override
    public List<Ebook> getAllBook() {
        return bookMapper.getAllBook();
    }

    @Override
    public List<Sort> getAllSort() {
        return bookMapper.getAllSort();
    }

    @Override
    public Ebook getBookByID(Integer eid) {
        return bookMapper.getBookByID(eid);
    }

    @Override
    public Ebook getBookByTxtsn(String txtSn) {
        return bookMapper.getBookByTxtsn(txtSn);
    }

    @Override
    public List<Ebook> findBookBySort(int sortID) {
        return bookMapper.findBookBySort(sortID);

    }

    @Override
    public Sort findSort(String sortName) {
        return bookMapper.findSort(sortName);
    }

    @Override
    public List<Ebook> bookSearch(String bookName, String start, String end) {
        return bookMapper.bookSearch(bookName,start,end);
    }

    @Override
    public Integer deleteBooks(int[] allID) {
        return bookMapper.deleteBooks(allID);
    }

    @Override
    public List<Ebook> findBookFileName(int[] allID) {
        return bookMapper.findBookFileName(allID);
    }

}
