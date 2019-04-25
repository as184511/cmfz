package com.baizhi.controller;

import com.baizhi.entity.Menu;
import com.baizhi.mapper.MenuMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("menu")
public class MenuController {
    @Autowired
    MenuMapper menuMapper;

    @RequestMapping("selectAll")
    @ResponseBody
    public List selectAll(ModelMap modelMap) {
        List<Menu> menus = menuMapper.selfselectAll();
        //modelMap.addAttribute("menus",menus);
        return menus;
    }
}
