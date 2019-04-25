package com.baizhi.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baizhi.entity.User;
import com.baizhi.entity.UserCount;
import com.baizhi.mapper.UserMapper;
import com.baizhi.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.goeasy.GoEasy;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;

    @Override
    public Map insert(String phone, String password) {
        User user = new User();
        user.setPhone(phone);
        if (userMapper.selectOne(user) != null) {
            Map map = new HashMap();
            map.put("error", "该号码已经注册过");
            return map;
        } else {
            //生成盐值
            String salt = UUID.randomUUID().toString().replace("-", "").substring(3, 7);
            //加密
            String pasw = DigestUtils.md5Hex(password + salt);
            user.setPassword(pasw);
            user.setSalt(salt);
            user.setCreateDate(new Date());
            userMapper.insertSelective(user);
            Map map = new HashMap();
            map.put("user", user);
            return map;
        }
    }

    @Override
    public void insert2(User user) {
        user.setCreateDate(new Date());
        userMapper.insertSelective(user);
    }

    @Override
    public Map login(String phone, String password) {
        User user = new User();
        user.setPhone(phone);
        //查询salt
        User selectOne = userMapper.selectOne(user);
        //加密
        String pasw = DigestUtils.md5Hex(password + selectOne.getSalt());
        user.setPassword(pasw);
        if (userMapper.selectOne(user) != null) {
            Map map = new HashMap();
            map.put("user", userMapper.selectOne(user));
            return map;
        } else {
            Map map = new HashMap();
            map.put("error", "密码错误");
            return map;
        }
    }


    @Override
    public Map selectAll(int page, int rows) {
        Map map = new HashMap();
        PageHelper.startPage(page, rows);
        PageInfo<User> pageInfo = new PageInfo<>(userMapper.selectAll());
        List<User> list = pageInfo.getList();

        long total = pageInfo.getTotal();
        map.put("total", total);
        map.put("rows", list);

        return map;
    }

    @Override
    public Map userAmount() {
        Map map = new HashMap();
        String[] time = new String[]{"一周", "两周", "三周"};
        int[] amount = {userMapper.userAmount(7), userMapper.userAmount(14), userMapper.userAmount(21)};
        map.put("time", time);
        map.put("amount", amount);
        return map;

    }

    @Override
    public void updateStatus(User user) {
        if (user.getStatus() == 0) {
            user.setStatus(1);
        } else {
            user.setStatus(0);
        }
        userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public Map userScatter(int sex) {
        Map map = new HashMap();
        List<UserCount> list = userMapper.userScatter(sex);
        map.put("data", list);
        return map;
    }
}
