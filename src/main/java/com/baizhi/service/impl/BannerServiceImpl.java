package com.baizhi.service.impl;

import com.baizhi.entity.Banner;
import com.baizhi.mapper.BannerMapper;
import com.baizhi.service.BannerService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BannerServiceImpl implements BannerService {

    @Autowired
    BannerMapper bannerMapper;

    @Override
    public Map selectAll(int page, int rows) {
        Map map = new HashMap();
        PageHelper.startPage(page, rows);
        PageInfo<Banner> pageInfo = new PageInfo<>(bannerMapper.selectAll());
        List<Banner> list = pageInfo.getList();

        long total = pageInfo.getTotal();
        map.put("total", total);
        map.put("rows", list);
        return map;

    }


    @Override
    public void addBanner(Banner banner) {
        bannerMapper.insertSelective(banner);
    }

    @Override
    public void delete(int id) {
        bannerMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void update(Banner banner) {
        bannerMapper.updateByPrimaryKeySelective(banner);
    }

    @Override
    public List<Banner> selfSelectAll() {
        List<Banner> banners = bannerMapper.selectAll();
        return banners;
    }
}
