package com.baizhi.controller;

import com.baizhi.entity.Article;
import com.baizhi.service.DetailArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("detailArticle")
public class DetailArticleController {

    @Autowired
    DetailArticleService detailArticleService;

    @RequestMapping("detail")
    @ResponseBody
    public Object detail(Integer articleId, Integer uid) {
        if (uid == null) {
            return "参数不能为空";
        } else {
            Article article = detailArticleService.detailArticle(articleId);
            return article;
        }
    }
}
