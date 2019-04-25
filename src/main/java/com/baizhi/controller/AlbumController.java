package com.baizhi.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.baizhi.entity.Album;
import com.baizhi.service.AlbumService;
import com.baizhi.util.PathUtil;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.URLEncoder;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("album")
public class AlbumController {
    @Autowired
    AlbumService albumService;

    @RequestMapping("selectAll")
    @ResponseBody
    public List selectAll() {
        List<Album> albums = albumService.selfSelectAll();

        return albums;
    }

    @RequestMapping("insert")
    @ResponseBody
    public void insert(Album album, HttpSession session, @RequestParam("photo") MultipartFile file) {
        //上传图片
        String projectName;    //项目名称
        projectName = session.getServletContext().getRealPath("/");
        //文件（图片）路径
        String filePath = projectName + PathUtil.getAlbumPath();
        String fileName = file.getOriginalFilename();
        String newFileName = UUID.randomUUID() + fileName;
        File targetFile = new File(filePath, newFileName);
        try {
            file.transferTo(targetFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //通过UUID生成id
        String uuid = String.valueOf(UUID.randomUUID());
        album.setId(uuid);
        //新文件名放入数据库
        album.setImgPath(newFileName);
        albumService.insert(album);
    }


    @RequestMapping("exportExcel")
    @ResponseBody
    public void exportExcel(HttpServletResponse response, String fileName, HttpSession session) {

        fileName = "专辑表";

        List<Album> albums = albumService.selfSelectAll();
        for (Album album : albums) {
            System.out.println(album);
        }
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("cmfz专辑数据", "专辑"),
                Album.class, albums);

        //设置响应的编码
        try {
            fileName = URLEncoder.encode(fileName, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //设置响应类型
        response.setContentType("application/ms-excel");
        response.addHeader("Content-Disposition", "attachment; filename=" + fileName + ".xls");
        try {
            OutputStream os = response.getOutputStream();
            workbook.write(os);
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
