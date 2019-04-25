package com.baizhi.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Chapter { //章节表
    @Id
    @Excel(name = "编号")
    private Integer id;

    @Excel(name = "标题")
    private String title;

    @Excel(name = "路径")
    private String loadPath;

    @Excel(name = "大小")
    private String size;

    @Excel(name = "时长")
    private String duration;
    @ExcelEntity(id = "absent")
    private String album_id;
}
