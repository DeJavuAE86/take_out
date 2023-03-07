package com.li.reggie.config;

import com.li.reggie.exception.CannotDeleteException;
import com.li.reggie.exception.CannotOrderException;
import com.li.reggie.utils.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

@RestControllerAdvice
public class ProjectExceptionAdvice {

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public Result doSQLIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException exception) {

        if(exception.getMessage().contains("Duplicate entry")) {
            String[] split = exception.getMessage().split(" ");
            String msg = split[2] + "已存在";
            return Result.err(msg);
        }

        return Result.err("未知错误");
    }

    @ExceptionHandler(CannotDeleteException.class)
    public Result doCannotDeleteException(CannotDeleteException exception) {

        return Result.err(exception.getMessage());
    }

    @ExceptionHandler(CannotOrderException.class)
    public Result doCannotOrderException(CannotOrderException exception) {

        return Result.err(exception.getMessage());
    }
}
