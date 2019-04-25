package com.baizhi.service.impl;

import com.baizhi.entity.Article;
import com.baizhi.entity.User;
import com.baizhi.mapper.AlbumMapper;
import com.baizhi.mapper.ArticleMapper;
import com.baizhi.mapper.BannerMapper;
import com.baizhi.mapper.UserMapper;
import com.baizhi.service.FirstViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class FirstViewServiceImpl implements FirstViewService {

    @Autowired
    BannerMapper bannerMapper;
    @Autowired
    AlbumMapper albumMapper;
    @Autowired
    ArticleMapper articleMapper;
    @Autowired
    UserMapper userMapper;

    @Override
    public Object queryAll(String uid, String type, String sub_type) {

        if (uid == null || type == null) {
            return "参数不能为空";
        } else {
            if (type.equals("all")) {    //首页
                Map<String, Object> map = new HashMap<>();
                map.put("banner", bannerMapper.selectByStatus());
                map.put("album", albumMapper.selectAll());
                map.put("article", articleMapper.selectAll());
                return map;
            } else if (type.equals("wen")) {  //专辑
                Map<String, Object> map = new HashMap<>();
                map.put("album", albumMapper.selectAll());
                return map;
            } else {
                if (sub_type == null) {
                    return "思类型为空未查到数据";
                } else {      //文章
                    User user = userMapper.selectByPrimaryKey(uid);
                    Article article = new Article();
                    if (sub_type.equals("ssyj")) {
                        Map<String, Object> map = new HashMap<>();
                        article.setMasterId(user.getMasterId());
                        map.put("article", articleMapper.select(article));
                        return map;
                    } else {
                        Map<String, Object> map = new HashMap<>();
                        map.put("article", articleMapper.selectByMaster(user.getMasterId()));
                        return map;
                    }
                }
            }
        }
    }
}
