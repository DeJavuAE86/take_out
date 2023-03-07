package com.li.reggie.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.li.reggie.domain.OrderDetail;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderDetailDao extends BaseMapper<OrderDetail> {
}
