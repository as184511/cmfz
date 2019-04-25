package com.baizhi.controller;

import com.baizhi.entity.Banner;
import com.baizhi.service.BannerService;
import com.baizhi.util.PathUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("banner")
public class BannerController {
    @Autowired
    BannerService bannerService;

    @RequestMapping("selectAll")
    public @ResponseBody
    Map selectAll(int page, int rows) {
        Map map = bannerService.selectAll(page, rows);

        return map;
    }

    @RequestMapping("addBanner")
    public void addBanner(Banner banner, HttpSession session, @RequestParam("photo") MultipartFile file) {
        //上传图片
        String projectName;    //项目名称
        projectName = session.getServletContext().getRealPath("/");
        //文件（图片）路径
        String filePath = projectName + PathUtil.getBannerPath();
        String fileName = file.getOriginalFilename();
        String newFileName = UUID.randomUUID() + fileName;
        File targetFile = new File(filePath, newFileName);
        System.out.println(targetFile + "路径qqqqqq");
        try {
            file.transferTo(targetFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //新文件名放入数据库
        banner.setImgPath(newFileName);
        bannerService.addBanner(banner);
    }

    @RequestMapping("delete")
    public void delete(int id) {
        bannerService.delete(id);
    }

    @RequestMapping("update")
    public void update(Banner banner, HttpSession session, @RequestParam("photo") MultipartFile file) {
        System.out.println(banner + "---------------");
        if (file == null) {
            bannerService.update(banner);
        } else {
            //上传图片
            String projectName;    //项目名称
            projectName = session.getServletContext().getRealPath("/");
            //文件（图片）路径
            String filePath = projectName + PathUtil.getBannerPath();
            String fileName = file.getOriginalFilename();
            String newFileName = UUID.randomUUID() + fileName;
            File targetFile = new File(filePath, newFileName);
            System.out.println(targetFile + "路径qqqqqq");
            try {
                file.transferTo(targetFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
            //新文件名放入数据库
            banner.setImgPath(newFileName);
            bannerService.update(banner);
        }

    }

    //导出Excel

    @RequestMapping("exportExcel")
    public void exportExcel(HttpServletResponse response, String fileName) {
        fileName = "轮播图";
        List<Banner> banners = bannerService.selfSelectAll();

        //创建工作簿
        Workbook workbook = new HSSFWorkbook();
        //创建字体
        Font font = workbook.createFont();
        font.setBold(true);
        font.setFontName("楷体");
        font.setItalic(true);
        font.setColor(Font.COLOR_RED);
        //创建日期格式
        DataFormat dataFormat = workbook.createDataFormat();
        short format = dataFormat.getFormat("yyyy年MM月dd日");
        //创建样式
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setFont(font);
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        //创建日期格式的样式
        CellStyle cellStyle1 = workbook.createCellStyle();
        cellStyle1.setDataFormat(format);
        //创建工作表
        Sheet sheet = workbook.createSheet("user");
        //第一个参数给那个列设置宽度  下标   第二个参数  宽度设置为多少  需要乘以256
        sheet.setColumnWidth(3, 20 * 256);
        //编号  名字   生日
        String[] strings = {"编号", "标题", "图片", "状态", "日期"};
        //创建行  参数第几行  下标
        Row row = sheet.createRow(0);
        for (int i = 0; i < strings.length; i++) {
            //创建单元格
            Cell cell = row.createCell(i);
            cell.setCellStyle(cellStyle);
            //单元格赋值
            cell.setCellValue(strings[i]);
        }
        for (int i = 0; i < banners.size(); i++) {
            Row row1 = sheet.createRow(i + 1);
            row1.createCell(0).setCellValue(banners.get(i).getId());
            row1.createCell(1).setCellValue(banners.get(i).getTitle());
            row1.createCell(2).setCellValue(banners.get(i).getImgPath());
            row1.createCell(3).setCellValue(banners.get(i).getStatus());
            Cell cell = row1.createCell(4);
            cell.setCellStyle(cellStyle1);
            cell.setCellValue(banners.get(i).getCreatDate());
        }
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
