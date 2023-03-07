package com.li.reggie.service;

import com.li.reggie.domain.Orders;

public interface OrdersService {

    /**
     * 保存订单
     *
     * @param orders
     * @return
     */
    boolean addOrder(Orders orders);
}
