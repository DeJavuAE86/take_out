package com.li.reggie.dto;

import com.li.reggie.domain.Dish;
import com.li.reggie.domain.DishFlavor;
import lombok.Data;

import java.util.List;

@Data
public class DishDto extends Dish {

    private List<DishFlavor> flavors;

    private String categoryName;

    private Integer copies;
}
