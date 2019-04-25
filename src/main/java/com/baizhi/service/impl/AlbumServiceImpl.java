package com.baizhi.service.impl;

import com.baizhi.entity.Album;
import com.baizhi.entity.Chapter;
import com.baizhi.mapper.AlbumMapper;
import com.baizhi.mapper.ChapterMapper;
import com.baizhi.service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AlbumServiceImpl implements AlbumService {
    @Autowired
    AlbumMapper albumMapper;
    @Autowired
    ChapterMapper chapterMapper;

    @Override
    public List<Album> selfSelectAll() {
        List<Album> albums = albumMapper.selfSelectAll();
        return albums;
    }

    @Override
    public List<Album> selectAlbum() {
        return albumMapper.selectAll();
    }

    @Override
    public void insert(Album album) {
        albumMapper.insert(album);
    }

    @Override
    public Map detailAlbum(String albumId) {
        Map map = new HashMap();
        Album album = albumMapper.selectByPrimaryKey(albumId);
        map.put("introduction", album);
        Chapter chapter = new Chapter();
        chapter.setAlbum_id(albumId);
        map.put("list", chapterMapper.select(chapter));
        return map;
    }
}
