package com.baizhi.mapper;

import com.baizhi.entity.Article;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;


public interface ArticleMapper extends Mapper<Article> {
    List<Article> selectByMaster(int masterId);

    Article detailArticle(int id);

}
