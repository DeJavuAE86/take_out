package com.li.reggie.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.li.reggie.domain.Dish;
import com.li.reggie.dto.DishDto;
import com.li.reggie.service.DishService;
import com.li.reggie.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("dish")
public class DishController {

    @Autowired
    private DishService dishService;

    /**
     * 新增菜品
     *
     * @param dishDto
     * @return
     */
    @PostMapping
    public Result addDish(@RequestBody DishDto dishDto) {

        boolean flag = dishService.addDish(dishDto);

        if (!flag) {
            return Result.err("新增失败");
        }

        return Result.success("新增成功");
    }


    /**
     * 修改菜品
     *
     * @param dishDto
     * @return
     */
    @PutMapping
    public Result updateDish(@RequestBody DishDto dishDto) {

        boolean flag = dishService.updateDish(dishDto);

        if (!flag) {
            return Result.err("修改失败");
        }

        return Result.success("修改成功");
    }


    /**
     * 分页查询菜品
     *
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public Result getDishesByPage(@RequestParam("page") int page, @RequestParam("pageSize") int pageSize, @RequestParam(value = "name", required = false) String name) {

        Page dishes = dishService.getDishesByPage(page, pageSize, name);

        return Result.success(dishes);
    }


    /**
     * 修改菜品反显菜品详情
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result<Dish> getDishById(@PathVariable Long id) {

        Dish dish = dishService.getDishById(id);

        return Result.success(dish);
    }

    /*
    @GetMapping("/list")
    public Result<List<Dish>> getDishListByCategoryId(@RequestParam("categoryId") Long categoryId) {

        List<Dish> dishList = dishService.getDishListByCategoryId(categoryId);

        return Result.success(dishList);
    }*/

    @GetMapping("/list")
    public Result<List<DishDto>> getDishListByCategoryId(@RequestParam("categoryId") Long categoryId) {

        List<DishDto> dishList = dishService.getDishListByCategoryId(categoryId);

        return Result.success(dishList);
    }
}
