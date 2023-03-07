package com.li.reggie.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.li.reggie.domain.Category;

import java.util.List;

public interface CategoryService {

    /**
     * 添加分类
     *
     * @param category
     * @return
     */
    boolean addCategory(Category category);

    /**
     * 分页查询分类
     *
     * @param page
     * @param pageSize
     * @return
     */
    Page<Category> getCategoriesByPage(int page, int pageSize);

    /**
     * 删除分类
     *
     * @param id
     * @return
     */
    boolean deleteCategory(Long id);

    /**
     * 修改分类
     *
     * @param category
     * @return
     */
    boolean updateCategory(Category category);

    /**
     * 查询全部菜品或者套餐分类
     *
     * @param type
     * @return
     */
    List<Category> getCategories(Integer type);

    /**
     * 根据id查询分类
     * @param id
     * @return
     */
    Category getCategoryById(Long id);
}
