package com.li.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.li.reggie.dao.EmployeeDao;
import com.li.reggie.domain.Employee;
import com.li.reggie.service.EmployeeService;
import com.li.reggie.utils.Result;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeDao employeeDao;

    /**
     * 员工登陆
     *
     * @param employee
     * @return
     */
    @Override
    public Result<Employee> login(Employee employee) {

        //将获取到的密码进行md5加密处理
        String encryptionPassword = DigestUtils.md5DigestAsHex(employee.getPassword().getBytes());

        //查询用户是否存在
        LambdaQueryWrapper<Employee> lqw = new LambdaQueryWrapper<Employee>();
        lqw.eq(Employee::getUsername, employee.getUsername());
        employee = employeeDao.selectOne(lqw);

        if (employee == null) {
            return Result.err("用户不存在");
        }

        if (!employee.getPassword().equals(encryptionPassword)) {
            return Result.err("密码错误");
        }

        if (employee.getStatus() == 0) {
            return Result.err("该用户已被禁用");
        }

        return Result.success(employee);
    }

    /**
     * 添加员工
     *
     * @param employee
     * @return
     */
    @Override
    public boolean addEmployee(Employee employee) {

        //设置初始密码并将密码进行MD5加密处理
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));

        return employeeDao.insert(employee) > 0;
    }

    /**
     * 条件分页查询
     *
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @Override
    public Page<Employee> getEmployees(int page, int pageSize, String name) {

        //设置name条件
        LambdaQueryWrapper<Employee> lqw = new LambdaQueryWrapper<Employee>();
        lqw.like(StringUtils.isNotEmpty(name), Employee::getName, name);
        //根据更新时间排序
        lqw.orderByAsc(Employee::getUpdateTime);

        //查询数据库返回的数据封装在iPage里
        Page<Employee> iPage = new Page<Employee>(page, pageSize);
        employeeDao.selectPage(iPage, lqw);

        return iPage;
    }

    @Override
    public Employee getEmployeeById(Long id) {

        //设置id条件
        LambdaQueryWrapper<Employee> lqw = new LambdaQueryWrapper<Employee>();
        lqw.eq(Employee::getId, id);

        return employeeDao.selectOne(lqw);
    }

    @Override
    public boolean updateEmployee(Employee employee) {

        return employeeDao.updateById(employee) > 0;
    }
}
