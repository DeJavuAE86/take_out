package com.li.reggie.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.li.reggie.domain.ShoppingCart;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ShoppingCartDao extends BaseMapper<ShoppingCart> {
}
