package com.li.reggie.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.li.reggie.domain.Employee;
import com.li.reggie.utils.Result;

public interface EmployeeService {

    /**
     * 员工登陆
     *
     * @param employee
     * @return
     */
    Result<Employee> login(Employee employee);

    /**
     * 添加员工
     *
     * @param employee
     * @return
     */
    boolean addEmployee(Employee employee);

    /**
     * 分页条件查询
     *
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    Page<Employee> getEmployees(int page, int pageSize, String name);

    /**
     * 修改页面反查详情
     *
     * @param id
     * @return
     */
    Employee getEmployeeById(Long id);


    /**
     * 修改员工
     *
     * @param employee
     * @return
     */
    boolean updateEmployee(Employee employee);
}
