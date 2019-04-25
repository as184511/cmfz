package com.baizhi.service;


import com.baizhi.entity.User;

import java.util.Map;

public interface UserService {
    public Map insert(String phone, String password);

    public void insert2(User user);

    public Map login(String phone, String password);

    public Map selectAll(int page, int rows);

    public Map userAmount();

    public void updateStatus(User user);

    public Map userScatter(int sex);
}
