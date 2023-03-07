package com.li.reggie.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.li.reggie.domain.Setmeal;
import com.li.reggie.dto.SetmealDto;
import com.li.reggie.service.SetmealService;
import com.li.reggie.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/setmeal")
public class SetmealController {

    @Autowired
    private SetmealService setmealService;


    /**
     * 新增套餐
     *
     * @param setmealDto
     * @return
     */
    @PostMapping
    public Result addSetmeal(@RequestBody SetmealDto setmealDto) {

        boolean flag = setmealService.addSetmeal(setmealDto);

        if (!flag) {
            return Result.err("新增失败");
        }

        return Result.success("新增成功");
    }


    /**
     * 分页条件查询套餐
     *
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public Result<Page<SetmealDto>> getSetmealsByPage(@RequestParam("page") int page, @RequestParam("pageSize") int pageSize, @RequestParam(value = "name", required = false) String name) {

        Page<SetmealDto> setmeals = setmealService.getSetmealsByPage(page, pageSize, name);

        return Result.success(setmeals);
    }


    /**
     * 修改套餐反显菜品详情
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result<SetmealDto> getSetmealById(@PathVariable Long id) {

        SetmealDto setmeal = setmealService.getSetmealById(id);

        return Result.success(setmeal);
    }


    /**
     * 新增套餐
     *
     * @param setmealDto
     * @return
     */
    @PutMapping
    public Result updateSetmeal(@RequestBody SetmealDto setmealDto) {

        boolean flag = setmealService.updateSetmeal(setmealDto);

        if (!flag) {
            return Result.err("新增失败");
        }

        return Result.success("新增成功");
    }


    @PostMapping("/status/{status}")
    public Result updateStatus(@PathVariable Integer status, @RequestParam("ids") List<Long> ids) {

        boolean flag = setmealService.updateStatus(ids, status);

        if (!flag) {
            return Result.err("修改失败");
        }

        return Result.success("修改成功");
    }


    /**
     * 删除套餐
     *
     * @param ids
     * @return
     */
    @DeleteMapping
    public Result deleteSetmeal(@RequestParam("ids") List<Long> ids) {

        boolean flag = setmealService.deleteSetmeal(ids);

        if (!flag) {
            return Result.err("删除失败");
        }

        return Result.success("删除成功");
    }


    /**
     * 根据分类id查询套餐详情
     *
     * @param categoryId
     * @param status
     * @return
     */
    @GetMapping("/list")
    public Result<List<Setmeal>> getSetmealListByCategoryId(@RequestParam("categoryId") Long categoryId, @RequestParam("status") Integer status) {

        List<Setmeal> setmealList = setmealService.getSetmealListByCategoryId(categoryId, status);

        return Result.success(setmealList);
    }
}
