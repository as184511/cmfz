package com.baizhi.controller;

import com.baizhi.entity.Chapter;
import com.baizhi.service.ChapterService;
import com.baizhi.util.FileUtil;
import com.baizhi.util.PathUtil;
import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.MultimediaInfo;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.URLEncoder;
import java.util.UUID;

@Controller
@RequestMapping("chapter")
public class ChapterController {
    @Autowired
    ChapterService chapterService;

    @RequestMapping("insert")
    @ResponseBody
    public void insert(Chapter chapter, HttpSession session, @RequestParam("photo") MultipartFile file) {
        //上传章节
        String projectName;    //项目名称
        projectName = session.getServletContext().getRealPath("/");
        //文件（图片）路径
        String filePath = projectName + PathUtil.getChapterPath();
        //原文件名
        String fileName = file.getOriginalFilename();
        String newFileName = UUID.randomUUID() + fileName;
        File targetFile = new File(filePath, newFileName);
        try {
            file.transferTo(targetFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //文件大小
        String size = FileUtil.getPrintSize(file.getSize());
        //文件播放时长

        Encoder encoder = new Encoder();
        String duration = "";
        try {
            MultimediaInfo m = encoder.getInfo(targetFile);
            long ls = m.getDuration();
            String second = String.valueOf(ls / 1000);
            duration = second + "秒";

        } catch (Exception e) {
            e.printStackTrace();

        }

        //新文件名
        chapter.setLoadPath(newFileName);
        chapter.setSize(size);
        chapter.setDuration(duration);
        System.out.println(chapter + "章节==================");

        chapterService.isert(chapter);
    }

    //文件下载
    @RequestMapping("download")
    public void download(String title, String loadPath, HttpSession session, HttpServletResponse response) {

        //获取文件的路径
        String realPath = session.getServletContext().getRealPath("/") + PathUtil.getChapterPath();
        String filePath = realPath + "/" + loadPath;
        System.out.println(filePath);
        File file = new File(filePath);
        //修改下载时的名字
        String extension = FilenameUtils.getExtension(loadPath);
        String oldName = title + "." + extension;
        //下载
        //设置响应的编码
        String encode = null;
        try {
            encode = URLEncoder.encode(oldName, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //设置响应头
        response.setHeader("Content-Disposition", "attachment;fileName=" + encode);
        //设置响应类型
        response.setContentType("audio/mpeg");
        ServletOutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
            outputStream.write(FileUtils.readFileToByteArray(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
