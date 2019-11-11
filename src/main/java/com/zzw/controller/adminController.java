package com.zzw.controller;

import com.alibaba.fastjson.JSON;
import com.zzw.entity.Ebook;
import com.zzw.entity.Sort;
import com.zzw.entity.User;
import com.zzw.service.BookService;
import com.zzw.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/administrator")
public class adminController {
    @Resource(name = "userService")
    private UserService userService;
    @Resource(name = "bookService")
    private BookService bookService;

    //所有用户管理页面
    @RequestMapping("/userManager.html")
    public String userManager(Map<String, Object> map) {
        List<User> userList = userService.getAllUser();
        map.put("userList", userList);
        return "page/userManager";
    }

    //用户添加页面
    @RequestMapping("/userAdd.html")
    public String userAdd() {
        return "page/userAdd";
    }

    //添加用户
    @RequestMapping("/userAdd")
    @ResponseBody
    public String addUser(User user) {
        if (userService.phoneExit(user.getPhone()) != null) {
            return "exist";
        }
        Integer result = userService.addUser(user);
        return "success";
    }

    //删除用户
    @PostMapping("/deleteUser")
    @ResponseBody
    public String deleteUser(@RequestParam(value = "uid", defaultValue = "0") int uid,
                             HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user.getUid() == uid) {
            return JSON.toJSONString("presentUser");
        }
        Integer result = userService.deleteUser(uid);
        return JSON.toJSONString("success");
    }

    //用户查询
    @RequestMapping("/userSearch")
    public String userSearch(@RequestParam(value = "uname", defaultValue = "") String uname,
                             @RequestParam(value = "phone", defaultValue = "") String phone,
                             Map<String, Object> map) {
        if ("".equals(uname) && "".equals(phone)) {
            return "redirect:/administrator/userManager.html";
        }
        List<User> userList = userService.userSearch(uname, phone);
        map.put("userList", userList);
        map.put("unameSearch", uname);
        map.put("phoneSearch", phone);
        return "page/userManager";
    }

    //书籍管理页面
    @RequestMapping("/bookManager.html")
    public String bookManager(Map<String, Object> map) {
        List<Ebook> ebookList = bookService.getAllBook();
        map.put("ebookList", ebookList);
        return "page/bookManager";
    }

    //上传书籍页面
    @RequestMapping("/upload.html")
    public String upload(Map<String, Object> map) {
        List<Sort> sortList = bookService.getAllSort();
        map.put("sortList", sortList);
        return "page/upload";
    }

    //书籍查询
    @RequestMapping("bookSearch")
    public String bookSearch(@RequestParam(value = "bookName", defaultValue = "") String bookName,
                             @RequestParam(value = "start", defaultValue = "") String start,
                             @RequestParam(value = "end", defaultValue = "") String end,
                             Map<String, Object> map) {
        if ("".equals(bookName) && "".equals(start) && "".equals(end)) {
            return "redirect:/administrator/bookManager.html";
        }
        List<Ebook> ebookList = bookService.bookSearch(bookName, start, end);
        map.put("ebookList", ebookList);
        map.put("bookName", bookName);
        map.put("startTime", start);
        map.put("endTime", end);
        return "page/bookManager";
    }

    /**
     * 删除书籍
     */
    @RequestMapping("/deleteBook")
    @ResponseBody
    public String deleteBook(@RequestParam(value = "eid", defaultValue = "0") Integer eid) {
        if (eid == 0) {
            return JSON.toJSONString("false");
        }
        Ebook book = bookService.getBookByID(eid);
        try {
            File txtFile = new File(ResourceUtils.getURL("classpath:").getPath() + "/static/book/txt/" + book.getTxtName());
            File coverFile = new File(ResourceUtils.getURL("classpath:").getPath() + "/static/book/images/" + book.getCoverName());
            if (txtFile.exists() && txtFile.isFile()) {
                txtFile.delete();
                System.out.println("成功删除书籍：" + book.getTxtName());
            }
            if (coverFile.exists() && coverFile.isFile()) {
                coverFile.delete();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return JSON.toJSONString("wrong");
        }
        Integer result = bookService.deleteBook(eid);
        return JSON.toJSONString("success");
    }

    //批量删除书籍
    @RequestMapping("deleteAllBook")
    @ResponseBody
    public String deleteAllBook(@RequestParam(value = "allID", defaultValue = "") int[] allID) {
        if ("".equals(allID)) {
            return JSON.toJSONString("false");
        }
        List<Ebook> ebooks = bookService.findBookFileName(allID);

        try {
            for (Ebook e : ebooks) {
                File txtFile = new File(ResourceUtils.getURL("classpath:").getPath() + "/static/book/txt/" + e.getTxtName());
                File coverFile = new File(ResourceUtils.getURL("classpath:").getPath() + "/static/book/images/" + e.getCoverName());
                if (txtFile.exists() && txtFile.isFile()) {
                    txtFile.delete();
                }
                if (coverFile.exists() && coverFile.isFile()) {
                    coverFile.delete();
                }
                System.out.println("成功删除书籍：" + e.getTxtName());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return JSON.toJSONString("wrong");
        }
        Integer result = bookService.deleteBooks(allID);
        System.out.println(result);

        return JSON.toJSONString("success");
    }

    @RequestMapping(value = "/faceRegister", method = RequestMethod.POST)
    @ResponseBody
    public String faceRegister(@RequestParam("faceInfo") String faceInfo, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null || user.getUserType() != 1) {
            return "false";
        }
        Integer result = userService.faceRegister(user.getUid(), faceInfo);
        if (result == 1) {
            return "success";
        } else {
            return "false";
        }
    }
}