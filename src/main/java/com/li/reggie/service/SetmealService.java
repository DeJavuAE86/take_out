package com.li.reggie.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.li.reggie.domain.Setmeal;
import com.li.reggie.dto.SetmealDto;

import java.util.List;

public interface SetmealService {

    /**
     * 新增套餐
     *
     * @param setmealDto
     * @return
     */
    boolean addSetmeal(SetmealDto setmealDto);

    /**
     * 分页条件查询套餐
     *
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    Page<SetmealDto> getSetmealsByPage(int page, int pageSize, String name);

    /**
     * 根据id查询套餐详情
     *
     * @param id
     * @return
     */
    SetmealDto getSetmealById(Long id);

    /**
     * 修改套餐
     *
     * @param setmealDto
     * @return
     */
    boolean updateSetmeal(SetmealDto setmealDto);

    /**
     * 修改状态
     *
     * @param ids
     * @param status
     * @return
     */
    boolean updateStatus(List<Long> ids, Integer status);

    /**
     * 删除套餐
     *
     * @param ids
     * @return
     */
    boolean deleteSetmeal(List<Long> ids);


    /**
     * 根据分类id查询套餐详情
     *
     * @param categoryId
     * @param status
     * @return
     */
    List<Setmeal> getSetmealListByCategoryId(Long categoryId, Integer status);
}
