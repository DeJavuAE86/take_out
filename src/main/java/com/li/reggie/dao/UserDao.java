package com.li.reggie.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.li.reggie.domain.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao extends BaseMapper<User> {
}
