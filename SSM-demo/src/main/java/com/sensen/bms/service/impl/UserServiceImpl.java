package com.sensen.bms.service.impl;

import com.sensen.bms.bean.User;
import com.sensen.bms.bean.UserExample;
import com.sensen.bms.dao.UserMapper;
import com.sensen.bms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;

    @Override
    public List<User> getAll() {
        return userMapper.selectByExample(null);
    }

    @Override
    public void saveUser(User user) {
        userMapper.insertSelective(user);
    }

    @Override
    public User getUser(Integer id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public boolean checkUser(String username) {
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        criteria.andUserNameEqualTo(username);
        long count = userMapper.countByExample(example);
        return count == 0;
    }

    @Override
    public void updateUser(User user) {
        userMapper.updateByPrimaryKeySelective(user);//该方法更新参数对象必须有主键，其他字段可选
    }

    @Override
    public void deleteUser(Integer id) {
        userMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void deleteBatch(List<Integer> ids) {
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        //delete from user where uid in(1,2,3)
        criteria.andUidIn(ids);
        userMapper.deleteByExample(example);
    }
}
