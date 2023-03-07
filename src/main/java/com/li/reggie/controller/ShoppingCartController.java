package com.li.reggie.controller;

import com.li.reggie.domain.ShoppingCart;
import com.li.reggie.service.ShoppingCartService;
import com.li.reggie.utils.BaseContext;
import com.li.reggie.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("shoppingCart")
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingService;

    /**
     * 加入购物车
     *
     * @param shoppingCart
     * @return
     */
    @PostMapping("/add")
    public Result addShoppingCart(@RequestBody ShoppingCart shoppingCart) {

        shoppingCart.setUserId(BaseContext.get());

        shoppingCart.setCreateTime(LocalDateTime.now());

        boolean flag = shoppingService.addShoppingCart(shoppingCart);

        if (!flag) {
            return Result.err("加入购物车失败");
        }

        return Result.success("加入购物车成功");
    }


    @PostMapping("/sub")
    public Result subShoppingCart(@RequestBody ShoppingCart shoppingCart) {

        boolean flag = shoppingService.subShoppingCart(shoppingCart);

        if (!flag) {
            return Result.err("移出购物车失败");
        }

        return Result.success("移出购物车成功");
    }


    @DeleteMapping("/clean")
    public Result cleanShoppingCart() {

        boolean flag = shoppingService.cleanShoppingCart();

        if (!flag) {
            return Result.err("清空购物车失败");
        }

        return Result.success("清空购物车成功");
    }


    @GetMapping("/list")
    public Result<List<ShoppingCart>> getShoppingCartList() {

        List<ShoppingCart> shoppingCartList = shoppingService.getShoppingCartList();

        return Result.success(shoppingCartList);
    }
}
