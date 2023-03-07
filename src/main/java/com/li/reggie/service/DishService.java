package com.li.reggie.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.li.reggie.domain.Dish;
import com.li.reggie.dto.DishDto;

import java.util.List;

public interface DishService {

    /**
     * 新增菜品
     *
     * @param dishDto
     * @return
     */
    boolean addDish(DishDto dishDto);

    /**
     * 修改菜品
     *
     * @param dishDto
     * @return
     */
    boolean updateDish(DishDto dishDto);

    /**
     * 分类查询菜品
     *
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    Page<DishDto> getDishesByPage(int page, int pageSize, String name);

    /**
     * 根据id查询菜品
     *
     * @param id
     * @return
     */
    DishDto getDishById(Long id);


    /**
     * 根据分类id查询菜品列表
     *
     * @param categoryId
     * @return
     */
    List<DishDto> getDishListByCategoryId(Long categoryId);
}
