package com.baizhi.controller;

import com.baizhi.entity.Article;
import com.baizhi.service.AlbumService;
import com.baizhi.service.DetailArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequestMapping("detailAlbum")
public class DetailAlbumController {

    @Autowired
    AlbumService albumService;

    @RequestMapping("detail")
    @ResponseBody
    public Object detail(String albumId, Integer uid) {
        if (uid == null) {
            return "参数不能为空";
        } else {
            Map map = albumService.detailAlbum(albumId);
            return map;
        }
    }
}
