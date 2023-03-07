package com.li.reggie.filter;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.li.reggie.utils.BaseContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Slf4j
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("开始进行添加公共字段填充");
        metaObject.setValue("createTime", LocalDateTime.now());
        metaObject.setValue("updateTime", LocalDateTime.now());
        metaObject.setValue("createUser", BaseContext.get());
        metaObject.setValue("updateUser", BaseContext.get());
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("开始进行修改公共字段填充");
        metaObject.setValue("updateTime", LocalDateTime.now());
        log.info("updateUser:" + BaseContext.get());
        metaObject.setValue("updateUser", BaseContext.get());
    }
}
