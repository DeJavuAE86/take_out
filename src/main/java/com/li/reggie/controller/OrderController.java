package com.li.reggie.controller;

import com.li.reggie.domain.Orders;
import com.li.reggie.service.OrdersService;
import com.li.reggie.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrdersService ordersService;

    /**
     * 用户下单
     *
     * @param orders
     * @return
     */
    @PostMapping("/submit")
    public Result submit(@RequestBody Orders orders) {

        boolean flag = ordersService.addOrder(orders);

        if (!flag) {
            return Result.err("用户下单失败");
        }

        return Result.success("用户下单成功");
    }
}
