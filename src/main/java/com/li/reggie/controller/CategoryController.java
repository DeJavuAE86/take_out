package com.li.reggie.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.li.reggie.domain.Category;
import com.li.reggie.service.CategoryService;
import com.li.reggie.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestControllerAdvice
@RequestMapping("/category")
@Slf4j
public class CategoryController {

    @Autowired
    private CategoryService categoryService;


    /**
     * 新增分类
     *
     * @param category
     * @return
     */
    @PostMapping
    public Result addGetCategories(@RequestBody Category category) {

        log.info("category:" + category);

        boolean flag = categoryService.addCategory(category);

        if (flag) {
            return Result.success("添加成功");
        } else {
            return Result.err("添加失败");
        }
    }


    /**
     * 分页查询
     *
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("/page")
    public Result getCategoriesByPage(@RequestParam("page") int page, @RequestParam("pageSize") int pageSize) {

        Page iPage = categoryService.getCategoriesByPage(page, pageSize);

        return Result.success(iPage);
    }


    /**
     * 删除分类
     *
     * @param id
     * @return
     */
    @DeleteMapping
    public Result deleteCategory(@RequestParam("ids") Long id) {

        boolean flag = categoryService.deleteCategory(id);

        if (!flag) {
            return Result.err("删除失败");
        }

        return Result.success("删除成功");
    }


    /**
     * 修改分类
     *
     * @param category
     * @return
     */
    @PutMapping
    public Result updateCategory(@RequestBody Category category) {

        boolean flag = categoryService.updateCategory(category);

        if (!flag) {
            return Result.err("修改失败");
        }

        return Result.success("修改成功");
    }


    /**
     * 查询全部菜品或者套餐分类
     *
     * @param type
     * @return
     */
    @GetMapping("/list")
    public Result<List<Category>> getCategories(@RequestParam(value = "type", required = false) Integer type) {

        List<Category> categories = categoryService.getCategories(type);

        return Result.success(categories);
    }
}
