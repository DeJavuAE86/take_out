package com.li.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.li.reggie.dao.CategoryDao;
import com.li.reggie.dao.DishDao;
import com.li.reggie.dao.SetmealDao;
import com.li.reggie.domain.Category;
import com.li.reggie.domain.Dish;
import com.li.reggie.domain.Setmeal;
import com.li.reggie.exception.CannotDeleteException;
import com.li.reggie.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private DishDao dishDao;

    @Autowired
    private SetmealDao setmealDao;

    /**
     * 添加分类
     *
     * @param category
     * @return
     */
    @Override
    public boolean addCategory(Category category) {

        return categoryDao.insert(category) > 0;
    }

    /**
     * 分页查询分类
     *
     * @param page
     * @param pageSize
     * @return
     */
    @Override
    public Page<Category> getCategoriesByPage(int page, int pageSize) {

        LambdaQueryWrapper<Category> lqw = new LambdaQueryWrapper<Category>();
        lqw.orderByAsc(Category::getSort);

        Page<Category> iPage = new Page<Category>(page, pageSize);
        categoryDao.selectPage(iPage, lqw);

        return iPage;
    }

    /**
     * 删除分类
     *
     * @param id
     * @return
     */
    @Override
    public boolean deleteCategory(Long id) {

        LambdaQueryWrapper<Dish> lqw1 = new LambdaQueryWrapper<Dish>();
        lqw1.eq(Dish::getCategoryId, id);

        Integer count1 = dishDao.selectCount(lqw1);
        if (count1 > 0) {
            throw new CannotDeleteException("无法删除");
        }

        LambdaQueryWrapper<Setmeal> lqw2 = new LambdaQueryWrapper<Setmeal>();
        lqw2.eq(Setmeal::getCategoryId, id);

        Integer count2 = setmealDao.selectCount(lqw2);
        if (count2 > 0) {
            throw new CannotDeleteException("无法删除");
        }

        return categoryDao.deleteById(id) > 0;
    }

    /**
     * 修改分类
     *
     * @param category
     * @return
     */
    @Override
    public boolean updateCategory(Category category) {

        return categoryDao.updateById(category) > 0;
    }


    /**
     * 查询全部或者套餐分类
     *
     * @param type
     * @return
     */
    @Override
    public List<Category> getCategories(Integer type) {

        LambdaQueryWrapper<Category> lqw = new LambdaQueryWrapper<Category>();
        lqw.eq(type != null, Category::getType, type);

        List<Category> categories = categoryDao.selectList(lqw);

        return categories;
    }

    /**
     * 根据id查询分类
     *
     * @param id
     * @return
     */
    @Override
    public Category getCategoryById(Long id) {

        LambdaQueryWrapper<Category> lqw = new LambdaQueryWrapper<Category>();
        lqw.eq(Category::getId, id);

        Category category = categoryDao.selectOne(lqw);

        return category;
    }
}
