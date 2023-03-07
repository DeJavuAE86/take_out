package com.li.reggie.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.li.reggie.domain.Orders;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrdersDao extends BaseMapper<Orders> {
}
