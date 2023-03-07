package com.li.reggie.service;

import com.li.reggie.domain.ShoppingCart;

import java.util.List;

public interface ShoppingCartService {

    /**
     * 加入购物车
     *
     * @param shoppingCart
     * @return
     */
    boolean addShoppingCart(ShoppingCart shoppingCart);

    /**
     * 移出购物车
     *
     * @param shoppingCart
     * @return
     */
    boolean subShoppingCart(ShoppingCart shoppingCart);

    /**
     * 清空购物车
     *
     * @return
     */
    boolean cleanShoppingCart();

    /**
     * 查询购物车
     *
     * @return
     */
    List<ShoppingCart> getShoppingCartList();
}
