package com.baizhi.mapper;

import com.baizhi.entity.Banner;

import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface BannerMapper extends Mapper<Banner> {
    //首页展示
    public List<Banner> selectByStatus();
}
