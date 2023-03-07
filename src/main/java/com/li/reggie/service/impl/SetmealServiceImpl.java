package com.li.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.li.reggie.dao.SetmealDao;
import com.li.reggie.dao.SetmealDishDao;
import com.li.reggie.domain.Setmeal;
import com.li.reggie.domain.SetmealDish;
import com.li.reggie.dto.SetmealDto;
import com.li.reggie.exception.CannotDeleteException;
import com.li.reggie.service.CategoryService;
import com.li.reggie.service.SetmealService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class SetmealServiceImpl implements SetmealService {

    @Autowired
    private SetmealDao setmealDao;

    @Autowired
    private SetmealDishDao setmealDishDao;

    @Autowired
    private CategoryService categoryService;


    /**
     * 新增套餐
     *
     * @param setmealDto
     * @return
     */
    @Override
    @Transactional
    public boolean addSetmeal(SetmealDto setmealDto) {

        int count1 = setmealDao.insert(setmealDto);

        int count2 = 0;
        for (SetmealDish setmealDish : setmealDto.getSetmealDishes()) {

            setmealDish.setSetmealId(setmealDto.getId());
            count2 += setmealDishDao.insert(setmealDish);
        }

        if (count1 == 1 && count2 == setmealDto.getSetmealDishes().size()) {

            return true;
        }

        return false;
    }

    /**
     * 分页条件查询套餐
     *
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @Override
    public Page<SetmealDto> getSetmealsByPage(int page, int pageSize, String name) {

        LambdaQueryWrapper<Setmeal> lqw = new LambdaQueryWrapper<Setmeal>();
        lqw.eq(name != null, Setmeal::getName, name);

        Page<Setmeal> setmealPage = new Page<Setmeal>(page, pageSize);
        setmealDao.selectPage(setmealPage, lqw);

        Page<SetmealDto> setmealDtoPage = new Page<SetmealDto>();
        BeanUtils.copyProperties(setmealPage, setmealDtoPage, "records");

        List<SetmealDto> setmealDishes = new ArrayList<SetmealDto>();
        for (Setmeal setmeal : setmealPage.getRecords()) {

            SetmealDto setmealDto = new SetmealDto();
            BeanUtils.copyProperties(setmeal, setmealDto);

            setmealDto.setCategoryName(categoryService.getCategoryById(setmeal.getCategoryId()).getName());
            setmealDishes.add(setmealDto);
        }

        setmealDtoPage.setRecords(setmealDishes);

        return setmealDtoPage;
    }


    /**
     * 根据id查询套餐详情
     *
     * @param id
     * @return
     */
    @Override
    public SetmealDto getSetmealById(Long id) {

        LambdaQueryWrapper<Setmeal> lqw1 = new LambdaQueryWrapper<Setmeal>();
        lqw1.eq(Setmeal::getId, id);

        Setmeal setmeal = setmealDao.selectOne(lqw1);

        LambdaQueryWrapper<SetmealDish> lqw2 = new LambdaQueryWrapper<SetmealDish>();
        lqw2.eq(SetmealDish::getSetmealId, id);

        List<SetmealDish> setmealDishList = setmealDishDao.selectList(lqw2);

        SetmealDto setmealDto = new SetmealDto();
        BeanUtils.copyProperties(setmeal, setmealDto);
        setmealDto.setSetmealDishes(setmealDishList);

        return setmealDto;
    }


    /**
     * 修改套餐
     *
     * @param setmealDto
     * @return
     */
    @Override
    @Transactional
    public boolean updateSetmeal(SetmealDto setmealDto) {

        int count1 = setmealDao.updateById(setmealDto);

        LambdaQueryWrapper<SetmealDish> lqw = new LambdaQueryWrapper<SetmealDish>();
        lqw.eq(SetmealDish::getSetmealId, setmealDto.getId());

        setmealDishDao.delete(lqw);

        int count2 = 0;
        for (SetmealDish setmealDish : setmealDto.getSetmealDishes()) {

            setmealDish.setSetmealId(setmealDto.getId());
            count2 += setmealDishDao.insert(setmealDish);
        }


        if (count1 == 1 && count2 == setmealDto.getSetmealDishes().size()) {

            return true;
        }

        return false;
    }

    /**
     * 修改状态
     *
     * @param ids
     * @param status
     * @return
     */
    @Override
    public boolean updateStatus(List<Long> ids, Integer status) {

        Setmeal setmeal = new Setmeal();
        setmeal.setStatus(status);

        LambdaQueryWrapper<Setmeal> lqw = new LambdaQueryWrapper<Setmeal>();
        lqw.in(Setmeal::getId, ids);

        setmealDao.update(setmeal, lqw);

        return true;
    }


    /**
     * 删除套餐
     *
     * @param ids
     * @return
     */
    @Override
    @Transactional
    public boolean deleteSetmeal(List<Long> ids) {

        LambdaQueryWrapper<Setmeal> lqw1 = new LambdaQueryWrapper<Setmeal>();
        lqw1.eq(Setmeal::getStatus, 1);
        lqw1.in(Setmeal::getId, ids);
        Integer count = setmealDao.selectCount(lqw1);

        if (count > 0) {
            throw new CannotDeleteException("套餐还在启售中无法删除");
        }

        LambdaQueryWrapper<SetmealDish> lqw2 = new LambdaQueryWrapper<>();
        lqw2.in(SetmealDish::getSetmealId, ids);
        setmealDishDao.delete(lqw2);

        setmealDao.deleteBatchIds(ids);

        return true;
    }


    /**
     * 根据分类id查询套餐详情
     *
     * @param categoryId
     * @param status
     * @return
     */
    /*
    @Override
    public List<SetmealDto> getSetmealListByCategoryId(Long categoryId, Integer status) {

        LambdaQueryWrapper<Setmeal> lqw = new LambdaQueryWrapper<Setmeal>();
        lqw.eq(Setmeal::getCategoryId, categoryId);
        lqw.eq(Setmeal::getStatus, status);
        lqw.orderByAsc(Setmeal::getUpdateTime);

        List<Setmeal> setmealList = setmealDao.selectList(lqw);

        List<SetmealDto> setmealDtoList = new ArrayList<SetmealDto>();
        BeanUtils.copyProperties(setmealList,setmealDtoList);

        for (int i = 0; i < setmealList.size(); i++) {

            SetmealDto setmealDto = new SetmealDto();
            BeanUtils.copyProperties(setmealList.get(i), setmealDto);

            LambdaQueryWrapper<SetmealDish> dlqw = new LambdaQueryWrapper<SetmealDish>();
            dlqw.eq(SetmealDish::getSetmealId, setmealDto.getId());

            setmealDtoList.add(setmealDto);
        }

        return setmealDtoList;
    }*/


    /**
     * 根据分类id查询套餐详情
     *
     * @param categoryId
     * @param status
     * @return
     */
    @Override
    public List<Setmeal> getSetmealListByCategoryId(Long categoryId, Integer status) {

        LambdaQueryWrapper<Setmeal> lqw = new LambdaQueryWrapper<Setmeal>();
        lqw.eq(Setmeal::getCategoryId, categoryId);
        lqw.eq(Setmeal::getStatus, status);
        lqw.orderByAsc(Setmeal::getUpdateTime);

        List<Setmeal> setmealList = setmealDao.selectList(lqw);

        return setmealList;
    }
}
