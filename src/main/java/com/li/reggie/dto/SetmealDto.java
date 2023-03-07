package com.li.reggie.dto;

import com.li.reggie.domain.Setmeal;
import com.li.reggie.domain.SetmealDish;
import lombok.Data;

import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;

    private Integer copies;
}
