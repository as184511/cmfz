package com.baizhi.service;

import com.baizhi.entity.Album;

import java.util.List;
import java.util.Map;


public interface AlbumService {
    public List<Album> selfSelectAll();

    //查询所有专辑名称
    public List<Album> selectAlbum();

    public void insert(Album album);

    //专辑详情
    public Map detailAlbum(String albumId);
}
