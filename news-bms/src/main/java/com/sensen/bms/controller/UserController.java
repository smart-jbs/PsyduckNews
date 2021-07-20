package com.sensen.bms.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sensen.bms.bean.Msg;
import com.sensen.bms.bean.User;
import com.sensen.bms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class UserController {
    @Autowired
    UserService userService;

    /**
     * 所有用户，分页查询，使用PageHelper:
     * 查询之前调用 PageHelper.startPage(pageNum,pageSize)传入页码和每页大小
     * 上述语句紧跟着的查询就是分页形式的
     * 查询后使用PageInfo包装结果并返回前端即可,下面第二个参数navigatePages表示连续显示的页数
     * 在前端可通过page.getNavigatepageNums()方法拿到
     * PageInfo中包括详细的分页信息，和查询数据
     * @return
     */
    /**
     * 以下方法只适用于B-S模式，因为方法转发的是页面
     * @param pn
     * @param model
     * @return
     */
//    @RequestMapping("/users")
    public String getUsersWithModel(@RequestParam(value = "pn",defaultValue = "1")Integer pn, Model model){
        PageHelper.startPage(pn,5);
        List<User> users = userService.getAll();
        PageInfo page = new PageInfo(users,5);
        model.addAttribute("pageInfo",page);
        return "list";  //在dispatcherServlet-servlet定义了前后缀
    }

    /**
     * 利用@RespinseBody注解，使其返回对象的json格式
     * 该注解需要导入jackson包
     * @param pn
     * @return
     */
    @RequestMapping("/users")
    @ResponseBody
    public Msg getUsers(@RequestParam(value = "pn",defaultValue = "1")Integer pn){
        PageHelper.startPage(pn,5);
        List<User> users = userService.getAll();
        PageInfo pageInfo = new PageInfo(users, 5);
        return Msg.success().add("pageInfo",pageInfo);
    }
}
