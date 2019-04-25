package com.baizhi.controller;

import com.alibaba.fastjson.JSONObject;
import com.baizhi.entity.User;

import com.baizhi.service.UserService;
import io.goeasy.GoEasy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequestMapping("user")
public class UserController {
    @Autowired
    UserService userService;

    @RequestMapping("selectAll")
    @ResponseBody
    public Map selectAll(int page, int rows) {
        Map map = userService.selectAll(page, rows);
        return map;
    }

    @RequestMapping("register")
    @ResponseBody
    public Map register(String phone, String password) {
        Map register = userService.insert(phone, password);
        Map map = userService.userAmount();
        return register;

    }

    @RequestMapping("login")
    @ResponseBody
    public Map login(String phone, String password) {
        Map map = userService.login(phone, password);
        return map;

    }


    @RequestMapping("insert")
    @ResponseBody
    public void insert(User user) {
        userService.insert2(user);
        Map map = userService.userAmount();
        String s = JSONObject.toJSONString(map);
        GoEasy goEasy = new GoEasy("http://rest-hangzhou.goeasy.io", "BC-07858a5c21ad4fefaa551ffab72e40bb");
        goEasy.publish("cmfz", "s");
    }


    @RequestMapping("updateStatus")
    @ResponseBody
    public void updateStatus(User user) {
        userService.updateStatus(user);
    }

    @RequestMapping("userAmount")
    @ResponseBody
    public Map userAmount() {
        Map map = userService.userAmount();
        return map;
    }

    @RequestMapping("userScatter")
    @ResponseBody
    public Map userScatter(int sex) {
        Map map = userService.userScatter(sex);
        return map;
    }


    //服务端发送消息
    @RequestMapping("goEasySend")
    @ResponseBody
    public void send() {

        GoEasy goEasy = new GoEasy("http://rest-hangzhou.goeasy.io", "BC-07858a5c21ad4fefaa551ffab72e40bb");

        goEasy.publish("cmfz", "Hello, GoEasy!");
    }
}
