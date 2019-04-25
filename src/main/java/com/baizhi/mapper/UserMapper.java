package com.baizhi.mapper;

import com.baizhi.entity.User;
import com.baizhi.entity.UserCount;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface UserMapper extends Mapper<User> {
    public int userAmount(int time);

    //查询用户分布
    public List<UserCount> userScatter(int sex);
}
