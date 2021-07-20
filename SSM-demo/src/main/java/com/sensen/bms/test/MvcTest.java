package com.sensen.bms.test;

import com.github.pagehelper.PageInfo;
import com.sensen.bms.bean.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

/**
 * 使用Spring测试模块测试请求正确性
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml","file:src/main/webapp/WEB-INF/dispatcherServlet-servlet.xml"})
public class MvcTest {
    //传入SpringMVC的ioc
    @Autowired
    WebApplicationContext context;

    //虚拟mvc请求，获取处理结果
    MockMvc mockMvc;

    @Before
    public void initMockMvc(){
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void  testPage() throws Exception {
        //模拟请求拿到返回值
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/users")
                .param("pn","1"))
                .andReturn();
        //请求成功后，可以取出pageInfo进行验证
        MockHttpServletRequest request = result.getRequest();
        PageInfo attribute =(PageInfo) request.getAttribute("pageInfo");
        System.out.println("当前页码：" + attribute.getPageNum());
        System.out.println("总页码" + attribute.getPages());
        System.out.println("总记录数：" + attribute.getTotal());
        System.out.println("连续显示的页码：");
        int[] nums = attribute.getNavigatepageNums();
        for(int i : nums){
            System.out.print(" " + i);
        }

        //拿取数据
        List<User> list = attribute.getList();
        for(User user : list){
            System.out.println("ID:" + user.getUid());
            System.out.println("username:" + user.getUserName());
        }

    }

}
