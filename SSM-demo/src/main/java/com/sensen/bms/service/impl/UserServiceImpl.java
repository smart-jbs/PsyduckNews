package com.sensen.bms.service.impl;

import com.sensen.bms.bean.User;
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
}
