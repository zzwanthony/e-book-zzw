package com.zzw.controller;

import com.alibaba.fastjson.JSON;
import com.zzw.entity.Ebook;
import com.zzw.entity.User;
import com.zzw.service.BookService;
import com.zzw.service.UserService;
import com.zzw.util.FaceUtil;
import com.zzw.util.TxtUtil;
import com.zzw.util.code.Captcha;
import com.zzw.util.code.GifCaptcha;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

/**
 * 无拦截的请求层
 */
@Controller
public class generalController {

    @Resource(name = "userService")
    private UserService userService;

    @Resource(name = "bookService")
    private BookService bookService;

    /**
     * 加入log4j日志
     */
    private static final Logger log = Logger.getLogger(generalController.class);

    /**
     * 首页请求
     */
    @RequestMapping("/")
    public String index(Map<String, Object> map) {
        List<Ebook> lists = userService.indexBook();
        map.put("newestBooks", lists);
        return "index";
    }

    //书籍目录界面
    @RequestMapping("/toCatalogue.html/{eid}")
    public String toCatalogue(@PathVariable("eid") String eid, Map<String, Object> map) {
        Integer eId = Integer.valueOf(eid);
        Ebook ebook = bookService.getBookByID(eId);

        Integer[] chapters = ebook.getChapters();
//        Long[] offsets = CommonUtil.LongsPath(ebook.getOffset());
        String[] titles = ebook.getTitles();
        Long[] nioOffsets = ebook.getNioOffsets();

        ebook.pathChapters(chapters, null, titles, nioOffsets);
        map.put("ebook", ebook);
        return "chapterPage";
    }
    //书籍目录界面
    @RequestMapping("/toCatalogue2.html/{txtSn}")
    public String toCatalogue2(@PathVariable("txtSn") String txtSn, Map<String, Object> map) {
        Ebook ebook = bookService.getBookByTxtsn(txtSn);

        Integer[] chapters = ebook.getChapters();
        String[] titles = ebook.getTitles();
        Long[] nioOffsets = ebook.getNioOffsets();

        ebook.pathChapters(chapters, null, titles, nioOffsets);
        map.put("ebook", ebook);
        return "chapterPage";
    }
    //进入阅读页面
    @RequestMapping("/readBookPage/{txtSn}/{index}")
    public String readerPage(@PathVariable("txtSn") String txtSn,
                             @PathVariable("index") Integer index,
                             Map<String, Object> map) {
        Ebook ebook = bookService.getBookByTxtsn(txtSn);
        Integer[] chapters = ebook.getChapters();
        Long[] nioOffsets = ebook.getNioOffsets();
        String[] titles = ebook.getTitles();
        File file = null;
        try {
            file = new File(ResourceUtils.getURL("classpath:").getPath() + "/static/book/txt/" + txtSn + ".txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Integer startOffset = 0;
        startOffset = Math.toIntExact(nioOffsets[index]);
        Integer endOffset = -1;
        if(index<titles.length-1){
            endOffset = Math.toIntExact(nioOffsets[index+1]);
        }
//        Long startOffset = 0l;
//        Long endOffset = nioOffsets[index];
//        if (index > 0) {
//            startOffset = nioOffsets[index - 1];
//        }
        String essay = TxtUtil.readStartToEndByNio(file, startOffset, endOffset, "GBK");
//        String essay1 = TxtUtil.readStartToEndByStream(file, startOffset, endOffset);
        map.put("title", titles[index]);
        map.put("essay", essay);
        map.put("currentChapter", index);
        map.put("txtSn", txtSn);
        map.put("eid", ebook.getEid());
        map.put("totalPage", titles.length - 1);
        map.put("bookName", ebook.getEname());

//        System.out.println("index:" + index + ",startOffset:" + startOffset + ",endOffset:" + endOffset + "**************************");
//
//        System.out.println("totalPage:" + (titles.length - 1) + ",essay:" +essay);
        return "readBookPage";
    }

    /**
     * 登入请求处理
     */
    @PostMapping("/login.do")
    @ResponseBody
    public String doLogin(@RequestParam(value = "username", defaultValue = "") String username,
                          @RequestParam(value = "password", defaultValue = "") String password,
                          HttpSession session) {
        if ("".equals(username) || "".equals(password)) {
            return JSON.toJSONString("no");
        }
        User user = userService.userLogin(username, password);
        if (user == null || user.getUname() == null) {
            return JSON.toJSONString("no");
        }
        session.setAttribute("user", user);
        return JSON.toJSONString("success");
    }

    /**
     * 脸部登录
     */
    @RequestMapping(value = "/faceLogin",method = RequestMethod.POST)
    @ResponseBody
    public String faceLogin(@RequestParam("faceInfo")String faceInfo, HttpSession session){
        System.out.println(faceInfo);
        List<User> adminList = userService.getAdminFace();
        if(adminList==null){
            return "false";
        }
        try {
            for (User user:adminList){
                Double result = FaceUtil.faceContrast(faceInfo,user.getFaceInfo());
                if(result>=90){
                    session.setAttribute("user", user);
                    return "success";
                }
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            System.out.println("人脸对比出错！！！！");
            return "error";
        }
        return "false";
    }


    /**
     * 生成随机验证码
     */
    // gif 验证码
    @RequestMapping("/common/captcha")
    public void captcha(HttpServletResponse response, HttpSession session) throws IOException {
        Captcha captcha = new GifCaptcha();
        captcha.out(response.getOutputStream());
        String text = captcha.text();
        System.out.println("***************生成随机验证码：" + text + "*******************");
        session.setAttribute("_captcha", text);
    }

    /**
     * 确认验证码是否正确
     */
    @GetMapping("/verifyCode.do/{phone}/{code}")
    @ResponseBody
    public String verifyCode(@PathVariable("phone") String phone,
                             @PathVariable("code") String code, HttpSession session) {
        String code2 = (String) session.getAttribute("_captcha");
        if (!code.equals(code2)) {
            return JSON.toJSONString("codeno");
        }
        String result = userService.phoneExit(phone);
        if (result == null || "".equals(result)) {
            return JSON.toJSONString("success");
        } else {
            return JSON.toJSONString("phoneno");
        }
    }

    /**
     * 注册请求处理
     */
    @PostMapping("/register.do")
    @ResponseBody
    public String doRegister(@RequestParam(value = "phone", defaultValue = "") String phone,
                             @RequestParam(value = "password", defaultValue = "") String password,
                             HttpSession session) {
        if ("".equals(phone) || "".equals(password)) {
            return JSON.toJSONString("no");
        }
        Integer result = userService.userRegister(phone, password);
        if (result == 1) {
            User user = userService.userLogin(phone, password);
            session.setAttribute("user", user);
        }
        return JSON.toJSONString("success");
    }

    /**
     * 退出登入请求
     */
    @GetMapping("/outLogin.do")
    public String outLogin(HttpSession session) {
        User user = (User) session.getAttribute("userSession");
        log.info("**********退出登入");
        //清除session
        session.invalidate();
        return "redirect:/";
    }
}
