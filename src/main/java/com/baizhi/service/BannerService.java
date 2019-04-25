package com.baizhi.service;

import com.baizhi.entity.Banner;

import java.util.List;
import java.util.Map;

public interface BannerService {
    public Map selectAll(int page, int rows);

    public void addBanner(Banner banner);

    public void delete(int id);

    public void update(Banner banner);

    public List<Banner> selfSelectAll();
}
