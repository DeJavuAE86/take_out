package com.li.reggie.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.li.reggie.domain.Category;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CategoryDao extends BaseMapper<Category> {
}
