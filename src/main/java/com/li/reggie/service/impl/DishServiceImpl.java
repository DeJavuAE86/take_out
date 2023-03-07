package com.li.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.li.reggie.dao.DishDao;
import com.li.reggie.dao.DishFlavorDao;
import com.li.reggie.domain.Dish;
import com.li.reggie.domain.DishFlavor;
import com.li.reggie.dto.DishDto;
import com.li.reggie.service.CategoryService;
import com.li.reggie.service.DishService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class DishServiceImpl implements DishService {

    @Autowired
    private DishDao dishDao;

    @Autowired
    private DishFlavorDao dishFlavorDao;

    @Autowired
    private CategoryService categoryService;

    /**
     * 新增菜品
     *
     * @param dishDto
     * @return
     */
    @Override
    @Transactional
    public boolean addDish(DishDto dishDto) {

        int count1 = dishDao.insert(dishDto);

        int count2 = 0;
        for (DishFlavor flavor : dishDto.getFlavors()) {
            flavor.setDishId(dishDto.getId());
            count2 += dishFlavorDao.insert(flavor);
        }

        if (count1 == 1 && count2 == dishDto.getFlavors().size()) {

            return true;
        }

        return false;
    }

    /**
     * 修改菜品
     *
     * @param dishDto
     * @return
     */
    @Override
    @Transactional
    public boolean updateDish(DishDto dishDto) {

        int count1 = dishDao.updateById(dishDto);

        LambdaQueryWrapper<DishFlavor> lqw = new LambdaQueryWrapper<DishFlavor>();
        lqw.eq(DishFlavor::getDishId, dishDto.getId());

        dishFlavorDao.delete(lqw);

        int count2 = 0;
        for (DishFlavor flavor : dishDto.getFlavors()) {

            flavor.setDishId(dishDto.getId());
            count2 += dishFlavorDao.insert(flavor);
        }

        if (count1 == 1 && count2 == dishDto.getFlavors().size()) {

            return true;
        }

        return false;
    }

    /**
     * 分类查询菜品
     *
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @Override
    public Page<DishDto> getDishesByPage(int page, int pageSize, String name) {

        LambdaQueryWrapper<Dish> lqw = new LambdaQueryWrapper<Dish>();
        lqw.eq(name != null, Dish::getName, name);
        lqw.orderByAsc(Dish::getSort);

        Page<Dish> dishPage = new Page<Dish>(page, pageSize);
        dishDao.selectPage(dishPage, lqw);

        Page<DishDto> dishDtoPage = new Page<DishDto>();

        BeanUtils.copyProperties(dishPage, dishDtoPage, "records");

        List<DishDto> dishDtoRecords = new ArrayList<DishDto>();
        for (Dish dish : dishPage.getRecords()) {

            DishDto dishDto = new DishDto();
            BeanUtils.copyProperties(dish, dishDto);
            dishDto.setCategoryName(categoryService.getCategoryById(dish.getCategoryId()).getName());

            dishDtoRecords.add(dishDto);
        }

        dishDtoPage.setRecords(dishDtoRecords);

        return dishDtoPage;
    }

    /**
     * 根据id查询菜品
     *
     * @param id
     * @return
     */
    @Override
    public DishDto getDishById(Long id) {

        LambdaQueryWrapper<Dish> lqw1 = new LambdaQueryWrapper<Dish>();
        lqw1.eq(Dish::getId, id);

        Dish dish = dishDao.selectOne(lqw1);

        LambdaQueryWrapper<DishFlavor> lqw2 = new LambdaQueryWrapper<DishFlavor>();
        lqw2.eq(DishFlavor::getDishId, id);

        List<DishFlavor> flavors = dishFlavorDao.selectList(lqw2);

        DishDto dishDto = new DishDto();
        BeanUtils.copyProperties(dish, dishDto);
        dishDto.setFlavors(flavors);
        dishDto.setCategoryName(categoryService.getCategoryById(dish.getCategoryId()).getName());

        return dishDto;
    }


    /**
     * 根据分类id查询菜品列表
     *
     * @param categoryId
     * @return
     */
    @Override
    public List<DishDto> getDishListByCategoryId(Long categoryId) {

        LambdaQueryWrapper<Dish> lqw = new LambdaQueryWrapper<Dish>();
        lqw.eq(Dish::getCategoryId, categoryId);

        List<Dish> dishList = dishDao.selectList(lqw);

        List<DishDto> dishDtoList = new ArrayList<DishDto>();

        BeanUtils.copyProperties(dishList, dishDtoList);

        for (int i = 0; i < dishList.size(); i++) {

            DishDto dishDto = new DishDto();
            BeanUtils.copyProperties(dishList.get(i), dishDto);

            LambdaQueryWrapper<DishFlavor> dlqw = new LambdaQueryWrapper<DishFlavor>();
            dlqw.eq(DishFlavor::getDishId, dishDto.getId());

            List<DishFlavor> flavors = dishFlavorDao.selectList(dlqw);

            dishDto.setFlavors(flavors);

            dishDtoList.add(dishDto);
        }

        return dishDtoList;
    }
}
