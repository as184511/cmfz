package com.baizhi.entity;

import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ExcelTarget(value = "aaa")
public class User {  //用户表
    @Id
    @KeySql(useGeneratedKeys = true)
    private Integer id;
    private String name;
    private String dharma;
    private Integer sex;
    private String province;
    private String city;
    private String sign;
    private Integer status;
    private String phone;
    private String password;
    private String salt;
    private String headImg;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JSONField(format = "yyyy-MM-dd")
    private Date createDate;
    private Integer masterId;

}
