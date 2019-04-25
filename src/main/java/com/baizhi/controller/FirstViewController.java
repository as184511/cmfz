package com.baizhi.controller;

import com.baizhi.service.FirstViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("firstView")
public class FirstViewController {


    @Autowired
    FirstViewService firstViewService;

    @RequestMapping("queryAll")
    public Object queryAll(String uid, String type, String sub_type) {
        Object o = firstViewService.queryAll(uid, type, sub_type);
        return o;
    }
}
