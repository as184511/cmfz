package com.baizhi.service.impl;

import com.baizhi.entity.Article;
import com.baizhi.mapper.ArticleMapper;
import com.baizhi.service.DetailArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DetailArticleServiceImpl implements DetailArticleService {

    @Autowired
    ArticleMapper articleMapper;

    @Override
    public Article detailArticle(int id) {
        Article article = articleMapper.detailArticle(id);
        return article;
    }
}
