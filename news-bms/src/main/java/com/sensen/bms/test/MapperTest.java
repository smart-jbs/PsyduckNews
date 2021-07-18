package com.sensen.bms.test;

import com.sensen.bms.bean.Admin;
import com.sensen.bms.bean.User;
import com.sensen.bms.dao.AdminMapper;
import com.sensen.bms.dao.UserMapper;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.UUID;

/**
 * 测试Dao层功能
 * 推荐Spring的项目使用Spring的单元测试，可以自动注入需要的组件
 * 1、pom导入SpringTest模块
 * 2、使用 @ContextConfiguration注解,利用locations属性指定配置文件
 * 3、使用 @RunWith注解指定使用Spring单元测试模块来运行
 * 3、使用 @Autowired注入
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class MapperTest {
    /**
     * 测试Mapper
     */
    //自动注入↓
    @Autowired
    AdminMapper adminMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    SqlSession sqlSession;

    @Test
    public void testCRUD(){
        //原生注入↓
//        //1、创建SpringIOC容器
//        ApplicationContext ioc = new ClassPathXmlApplicationContext("applicationContext.xml");
//        //2、从容器中获取mapper
//        AdminMapper bean = ioc.getBean(AdminMapper.class);

        //插入
//        adminMapper.insertSelective(new Admin(null,"SuperAdmin","123456"));
//        adminMapper.insertSelective(new Admin(null,"SenSen","123456789"));
//        userMapper.insertSelective(new User(null,"妙蛙种子","123456"));

        //批量插入: 写新的方法，传入集合； 使用可以执行批量操作的sqlSession
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        for(int i = 0;i < 100;i++){
            String uid = UUID.randomUUID().toString().substring(0,5) + i;
            mapper.insertSelective(new User(null,uid,uid+"pwd"));
        }
    }
}
