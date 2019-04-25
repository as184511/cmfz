package com.baizhi.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelCollection;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ExcelTarget(value = "aaa")
public class Album {//专辑表
    @Id
    private String id;

    @Excel(name = "标题", needMerge = true)
    private String title;

    @Excel(name = "数量", needMerge = true)
    private Integer amount;

    @Excel(name = "图片", type = 2, needMerge = true, width = 40, height = 20, imageType = 1)
    private String imgPath;

    @Excel(name = "分数", needMerge = true)
    private Integer score;

    @Excel(name = "作者 ", needMerge = true)
    private String author;

    @Excel(name = "播音", needMerge = true)
    private String boardcast;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JSONField(format = "yyyy-MM-dd")
    @Excel(name = "发布日期", format = "yyyy-MM-dd", needMerge = true, width = 30)
    private Date publishDate;

    @Excel(name = "简介", needMerge = true)
    private String brief;
    @Transient
    @ExcelCollection(name = "章节", orderNum = "5")
    private List<Chapter> children;
}
