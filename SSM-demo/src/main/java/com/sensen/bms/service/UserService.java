package com.sensen.bms.service;

import com.sensen.bms.bean.User;

import java.util.List;

public interface UserService {
    public List<User> getAll();
    public void saveUser(User user);
    public User getUser(Integer id);
    boolean checkUser(String username);
    void updateUser(User user);
    void deleteUser(Integer id);
    void deleteBatch(List<Integer> ids);
}
