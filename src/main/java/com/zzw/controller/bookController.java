package com.zzw.controller;

import com.zzw.Exception.WebException;
import com.zzw.entity.Ebook;
import com.zzw.entity.Sort;
import com.zzw.entity.User;
import com.zzw.service.BookService;
import com.zzw.util.CommonUtil;
import com.zzw.util.TxtChapterMassage;
import com.zzw.util.TxtRead;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

/**
 * 电子书处理请求
 */
@Controller
@RequestMapping("/ebook")
public class bookController {

    @Resource(name = "bookService")
    private BookService bookService;

//    private static String userPath = System.getProperty("user.home");
    private static String userPath;

    static {
        try {
            userPath = ResourceUtils.getURL("classpath:").getPath() + "/static/book";
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 管理员上传电子书
     * 返回json类型
     */
    @PostMapping("/add")
    public String saveBook(Ebook book, @RequestParam(value = "txtFile") MultipartFile txtFile,
                           @RequestParam(value = "coverImg", required = false) MultipartFile coverImg,
                           HttpSession session, HttpServletRequest request) throws Exception {
        System.out.println(txtFile.getOriginalFilename() + "---" + coverImg.getOriginalFilename() + "----" + book.getSid());
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "noLogin";
        }
        book.setUid(user.getUid());
        book.setUploadData(new java.sql.Date(new java.util.Date().getTime()));

        book.setTxtSn(CommonUtil.randSn("txt"));
        //保存文件名到实体类
        book.setTxtName(book.getTxtSn() + ".txt");
        book.setCoverName(book.getTxtSn() + ".jpg");

        File txtFile1 = null;
//        File exitBook = new File(userPath + File.separator + "txt");
//        File exitImage = new File(userPath + File.separator+ "images");
//        if (!exitBook.exists()) {
//            exitBook.mkdir();
//            exitImage.mkdir();
//        }
        if (!txtFile.getOriginalFilename().matches(".*[.]txt")) {
            throw new WebException(1, "只支持txt文件");
        } else {
            //获取txt文件和封面文件
            String TxtPath = userPath + File.separator + "txt" + File.separator + book.getTxtSn() + ".txt";
//            txtFile1 = new File(ResourceUtils.getURL("classpath:").getPath() + "/static/book/txt/" + book.getTxtSn() + ".txt");
            txtFile1 = new File(TxtPath);
            System.out.println(TxtPath);
            //保存txtFile文件到txtFile1文件路径下
            txtFile.transferTo(txtFile1);
        }
        File coverFile = null;
        //上传了封面文件
        if (coverImg != null) {
            if (!coverImg.getOriginalFilename().matches(".*[.]jpg")) {
                throw new WebException(2, "不支持该图片作为书页面");
            }
            String imagesPath = userPath + File.separator + "images" + File.separator + book.getTxtSn() + ".jpg";
//          coverFile = new File(ResourceUtils.getURL("classpath:").getPath() + "/static/book/images/" + book.getTxtSn() + ".jpg");
            coverFile = new File(imagesPath);
            coverImg.transferTo(coverFile);
        }

        //使用nio读取txt文件（文件名，txtSn，编码格式）
        book = TxtRead.nioTxtChapterMsgLists(txtFile1, "GBK", book);
        if (book == null) {
            if (txtFile1 != null && txtFile1.exists() && txtFile1.isFile()) {
                txtFile1.delete();
                System.out.println("删除成功！书籍");
            }
            if (coverFile != null && coverFile.exists() && coverFile.isFile()) {
                coverFile.delete();
                System.out.println("删除成功！图片");
            }
            throw new WebException(3, "TXT电子书文件章节格式错误！！！");
        }

        System.out.println(book.toString());
        // 保存到数据库
        Integer result = bookService.saveBook(book);

        return "redirect:/administrator/bookManager.html";
    }

    /**
     * 去往章节页面，获取所有章节信息
     * 需要获取电子书表中的所有信息
     */
    @GetMapping("/getAllChapter/{txtSn}")
    public String getAllChapter(@PathVariable("txtSn") String txtSn, Map<String, Object> map) {
        //查找目录页面所有需要的信息
        Ebook ebook = bookService.getAllChapter(txtSn);
        map.put("ebook", ebook);
        return "chapterPage";
    }

    /**
     * 获取图书下一章的章节信息
     * 未使用
     *
     * @param txtSn txt图书编号
     * @param page  第几章节
     */
    @GetMapping("/readChapter/{txtSn}")
    public String chapterList(@PathVariable("txtSn") String txtSn,
                              @RequestParam(value = "page", defaultValue = "0") Integer page,
                              Map<String, Object> map) {
        TxtChapterMassage tcm = bookService.readChapter(txtSn, page);
        //返回页面信息：标题，第几章节数，文章内容，书名，txtSn
        map.put("title", tcm.getTitle());
        map.put("page", page);
        map.put("article", tcm.getArticle());
        map.put("ename", tcm.getEname());
        map.put("txtSn", tcm.getTxtSn());
        return "readBookPage";
    }

    /**
     * 根据书籍种类查询书籍
     */
    @RequestMapping("/sortBook/{sortName}")
    public String findBookBySort(@PathVariable(value = "sortName") String sortName, Map<String, Object> map) {
        Sort sort = bookService.findSort(sortName);
        if (sort == null) {
            return "redirect:/";
        }
        List<Ebook> books = bookService.findBookBySort((int) sort.getSid());
        map.put("books", books);
        map.put("keyword", sortName);
        return "referBook";
    }

    /**
     * 根据书名或者作者名查询书籍
     */
    @RequestMapping("/findBookByName")
    public String findBook(@RequestParam(value = "keyword", defaultValue = "") String keyword,
                           Map<String, Object> map) {
        if ("".equals(keyword)) {
            return "redirect:/";
        }
        List<Ebook> books = bookService.findBook(keyword);
        //返回查询到的书籍和关键字
        map.put("books", books);
        map.put("keyword", keyword);
        return "referBook";
    }

}
