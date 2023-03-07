package com.li.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.li.reggie.dao.ShoppingCartDao;
import com.li.reggie.domain.ShoppingCart;
import com.li.reggie.service.ShoppingCartService;
import com.li.reggie.utils.BaseContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    @Autowired
    private ShoppingCartDao shoppingCartDao;

    /**
     * 加入购物车
     *
     * @param shoppingCart
     * @return
     */
    @Override
    public boolean addShoppingCart(ShoppingCart shoppingCart) {

        LambdaQueryWrapper<ShoppingCart> lqw = new LambdaQueryWrapper<ShoppingCart>();
        lqw.eq(ShoppingCart::getUserId, shoppingCart.getUserId());

        if (shoppingCart.getDishId() != null) {

            lqw.eq(ShoppingCart::getDishId, shoppingCart.getDishId());
        } else {

            lqw.eq(ShoppingCart::getSetmealId, shoppingCart.getSetmealId());
        }

        ShoppingCart newShoppingCart = shoppingCartDao.selectOne(lqw);

        int count = 0;
        if (newShoppingCart != null) {

            newShoppingCart.setNumber(newShoppingCart.getNumber() + 1);
            count = shoppingCartDao.updateById(newShoppingCart);
        } else {

            count = shoppingCartDao.insert(shoppingCart);
        }

        return count > 0;
    }

    /**
     * 移出购物车
     *
     * @param shoppingCart
     * @return
     */
    @Override
    public boolean subShoppingCart(ShoppingCart shoppingCart) {

        LambdaQueryWrapper<ShoppingCart> lqw = new LambdaQueryWrapper<ShoppingCart>();
        lqw.eq(ShoppingCart::getUserId, BaseContext.get());

        if (shoppingCart.getDishId() != null) {

            lqw.eq(ShoppingCart::getDishId, shoppingCart.getDishId());
        } else {

            lqw.eq(ShoppingCart::getSetmealId, shoppingCart.getSetmealId());
        }

        int count = 0;
        ShoppingCart newShoppingCart = shoppingCartDao.selectOne(lqw);
        if (newShoppingCart != null && newShoppingCart.getNumber() != 1) {

            newShoppingCart.setNumber(newShoppingCart.getNumber() - 1);
            count = shoppingCartDao.updateById(newShoppingCart);
        } else {

            count = shoppingCartDao.delete(lqw);
        }

        return count > 0;
    }

    /**
     * 清空购物车
     *
     * @return
     */
    @Override
    public boolean cleanShoppingCart() {

        LambdaQueryWrapper<ShoppingCart> lqw = new LambdaQueryWrapper<ShoppingCart>();
        lqw.eq(ShoppingCart::getUserId, BaseContext.get());

        return shoppingCartDao.delete(lqw) > 0;
    }


    /**
     * 查询购物车
     *
     * @return
     */
    @Override
    public List<ShoppingCart> getShoppingCartList() {

        LambdaQueryWrapper<ShoppingCart> lqw = new LambdaQueryWrapper<ShoppingCart>();
        lqw.eq(ShoppingCart::getUserId, BaseContext.get());

        List<ShoppingCart> shoppingCartList = shoppingCartDao.selectList(lqw);

        return shoppingCartList;
    }
}
