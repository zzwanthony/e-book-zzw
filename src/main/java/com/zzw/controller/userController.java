package com.zzw.controller;

import com.alibaba.fastjson.JSON;
import com.zzw.entity.Bookshelf;
import com.zzw.entity.User;
import com.zzw.service.BookService;
import com.zzw.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * 管理员请求层
 */
@Controller
@RequestMapping("/readerUser")
public class userController {
    @Resource(name="userService")
    private UserService userService;
    @Resource(name="bookService")
    private BookService bookService;

    /**
     * 跳转个人中心
     */
    @GetMapping("/personal.do")
    public String jumpPersonal(HttpSession session){
        User user = (User) session.getAttribute("user");
        if(user==null)
            return "redirect:/";
        return "personPage";
    }

    /**
     * 跳转到用户信息页面
     */
    @RequestMapping("/userMassage.html")
    public String toUserMassage(){
        return "page/message";
    }

    //书架页面
    @RequestMapping("/BookShelf.html")
    public String toBookShelf(HttpSession session,Map<String, Object> map){
        //传入书架信息
        User user = (User) session.getAttribute("user");
        if(user == null){
            return "page/message";
        }
        List<Bookshelf> bookshelf = userService.getBookshelf(user.getUid());
        map.put("bookshelf",bookshelf);
        return "page/bookshelf";
    }
    //收藏书籍
    @RequestMapping("/collectBook")
    @ResponseBody
    public String collectBook(Bookshelf bookshelf, HttpSession session){
        User user = (User)session.getAttribute("user");
        Bookshelf existShelf = userService.isExistShelf(user.getUid(),bookshelf.gettSn());
        Integer result = 0;
        if(existShelf==null){
            System.out.println("书籍没有收藏记录！！！");
            bookshelf.setUid(user.getUid());
            result = userService.collectBook(bookshelf);
        }else{
            System.out.println("书籍有收藏记录！！！");
            existShelf.setChapter(bookshelf.getChapter());
            existShelf.setChapternum(bookshelf.getChapternum());
            result = userService.updateBookshelf(existShelf);
        }
        if(result == 1){
            return JSON.toJSONString("success");
        }else{
            return JSON.toJSONString("false");
        }
    }
    /**
     * 书架信息删除
     */
    @RequestMapping("deleteShelf")
    @ResponseBody
    public String deleteShelf(@RequestParam(value = "id",defaultValue = "0")int id){
        if(id==0){
            return JSON.toJSONString("idisnull");
        }
        Integer result = userService.deleteShelf(id);
        return JSON.toJSONString("success");
    }
    //批量删除书架信息
    @RequestMapping("deleteAllShelf")
    @ResponseBody
    public String deleteAllShelf(@RequestParam(value = "ids", defaultValue = "") int[] ids){
        if("".equals(ids)){
            return JSON.toJSONString("idisnull");
        }
        Integer result = userService.deleteAllShelf(ids);
        return JSON.toJSONString("success");
    }

    /**
     * 去往修改信息页面
     */
    @RequestMapping("/modifyPage.html")
    public String modifyPage(){
        return "page/modifyPage";
    }
    /**
     * 修改个人信息
     */
    @PostMapping("/modifyUser")
    @ResponseBody
    public String modifyUser(User user,HttpSession session){
        User u = (User)session.getAttribute("user");
        Integer result = userService.modifyUser(user);
        if(result == 1) {
            u.setUname(user.getUname());
            u.setSex(user.getSex());
            u.setBirth(user.getBirth());
            session.setAttribute("user",u);
            return "success";
        }
        else
            return "false";
    }
    //去往修改密码页面
    @RequestMapping("/pwdModify.html")
    public String pwdModify(){
        return "page/pwdModify";
    }

    //密码修改
    @RequestMapping("/pwdModify")
    @ResponseBody
    public String pwdM(@RequestParam(value = "oldPassword",defaultValue = "")String oldPassword,
                       @RequestParam(value = "newPassword",defaultValue = "")String newPassword,
                       HttpSession session){
        if("".equals(newPassword) || "".equals(oldPassword)){
            return "false";
        }
        User user = (User) session.getAttribute("user");
        if(!oldPassword.equals(user.getPassword())){
            return "oldFalse";
        }
        if(oldPassword.equals(newPassword)){
            return "repetition";
        }
        Integer result = userService.modifyPassword(user.getUid(),newPassword);
        session.invalidate();
        return "success";
    }
}
