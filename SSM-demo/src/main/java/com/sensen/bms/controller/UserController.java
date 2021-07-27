package com.sensen.bms.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sensen.bms.bean.Msg;
import com.sensen.bms.bean.User;
import com.sensen.bms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * URI:
 * /user/{id}   GET 查询
 * /user    POST 保存
 * /user/{id}   PUT 修改
 * /user/{id}   DELETE 删除
 */
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

    /**
     * 用户保存
     * 要支持JSR303校验
     * 需要导入Hibernate-Validator
     * 然后在实体类字段和接口参数 加注解
     * @param user
     * @return
     */
    @RequestMapping(value = "/user",method = RequestMethod.POST)
    @ResponseBody
    public Msg saveUser(@Valid User user, BindingResult result){
        if(result.hasErrors()){
            Map<String,Object> map = new HashMap<>();
            List<FieldError> errors = result.getFieldErrors();
            for(FieldError fieldError : errors){
                System.out.println("错误的字段名：" + fieldError.getField());
                System.out.println("错误的信息：" + fieldError.getDefaultMessage());
                map.put(fieldError.getField(),fieldError.getDefaultMessage());
            }
            return Msg.error().add("errorFields",map);
        }else{

        }
        userService.saveUser(user);
        return Msg.success();
    }

    @RequestMapping(value = "/user/{id}",method = RequestMethod.GET)
    @ResponseBody
    public Msg getUser(@PathVariable("id") Integer id){
        User user = userService.getUser(id);
        return Msg.success().add("user",user);
    }

    /**
     * 检查用户名是否重复
     * @param username
     * @return
     */
    @ResponseBody
    @RequestMapping("/check")
    public Msg checkUser(@RequestParam("username") String username){
        //先判断用户名是否合法
        String regx = "(^[a-zA-Z0-9_-]{6,16}$)|(^[\u2E80-\u9FFF]{2,5})";
        //^匹配字符串开头，然后是小写字母，大写字母，0-9，以及_和-,$匹配字符串结尾，且长度为2-5;
        if (!username.matches(regx)){
            return Msg.error().add("va_msg","用户名必须是6-16位数字和字母的组合或者2-5位中文");
        }

        boolean result = userService.checkUser(username);
        if (result){
            return Msg.success();
        }else {
            return Msg.error().add("va_msg","用户名不可用");
        }
    }

    /**
     * 用户更新,id只是用来和method一起匹配url
     * 如果发送ajax为PUT形式的请求
     * 封装的对象user除了主键其他为null，数据封装不上
     * 而请求体中会有这些字段的数据，这样的话sql就为update user where uid = ...
     * 原因：
     * Tomcat：1）将请求体中的数据封装一个map
     *         2）request.getParameter("...")就是从这个map中取值
     *         3）SpringMVC封装对象时，会调用getParameter方法对对象中的属性赋值
     * Tomcat一看是PUT请求，不会封装请求体中的数据为map，从而对象属性出错，只有POST请求才会封装!!!
     *
     * 解决方案：
     * 我们要能支持直接发送PUT之类的请求，还要封装请求体中的数据
     * 配置上HttpPutFormContentFilter即可：将请求体中的数据解析包装成一个map，request被重新包装，getParameter被重写，即可从封装的map中取数据
     * @param user
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/user/{id}",method = RequestMethod.PUT)
    public Msg updateUser(User user){
        userService.updateUser(user);
        return Msg.success();
    }

    /**
     * 单个，批量删除整合
     * 批量删除：id-id-id
     * 单个删除：id
     * @param ids
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/user/{ids}",method = RequestMethod.DELETE)
    public Msg deleteUserById(@PathVariable("ids") String ids){
        if(ids.contains("-")){
            List<Integer> del_ids = new ArrayList<>();
            String[] str_ids = ids.split("-");
            for(String id : str_ids){
                del_ids.add(Integer.parseInt(id));
            }
            userService.deleteBatch(del_ids);
        }else {
            Integer id = Integer.parseInt(ids);
            userService.deleteUser(id);
        }
        return Msg.success();
    }




}
