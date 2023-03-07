package com.li.reggie.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.li.reggie.domain.Employee;
import com.li.reggie.service.EmployeeService;
import com.li.reggie.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    /**
     * 员工登陆
     *
     * @param request
     * @param employee
     * @return
     */
    @PostMapping("/login")
    public Result<Employee> login(HttpServletRequest request, @RequestBody Employee employee) {

        //调用employeeService查询用户
        Result<Employee> result = employeeService.login(employee);

        //若code=1，则登陆成功，将用户保存到session里
        if (result.getCode() == 1) {
            request.getSession().setAttribute("employee", result.getData().getId());
        }

        return result;
    }

    /**
     * 员工退出
     *
     * @param request
     * @return
     */
    @PostMapping("/logout")
    public Result logout(HttpServletRequest request) {

        //把用户在session的数据销毁
        request.getSession().removeAttribute("employee");

        return Result.success("退出成功");
    }


    /**
     * 添加员工
     *
     * @param employee
     * @return
     */
    @PostMapping
//    public Result addEmployee(HttpServletRequest request, @RequestBody Employee employee) {
    public Result addEmployee(HttpServletRequest request, @RequestBody Employee employee) {

        //获取创建人id
        Long createId = (Long) request.getSession().getAttribute("employee");

//        //设置创建人、更新人
//        employee.setCreateUser(createId);
//        employee.setUpdateUser(createId);
//
//        //设置创建时间、更新时间
//        employee.setCreateTime(LocalDateTime.now());
//        employee.setUpdateTime(LocalDateTime.now());

        boolean flag = employeeService.addEmployee(employee);

        if (flag) {
            return Result.success("添加成功");
        } else {
            return Result.err("添加失败");
        }
    }

    /**
     * 条件分页查询
     *
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    //RequestParam中required设置是否为必传属性，默认应该是true
    @GetMapping("/page")
    public Result<Page> pagination(@RequestParam("page") int page, @RequestParam("pageSize") int pageSize, @RequestParam(value = "name", required = false) String name) {

        Page iPage = employeeService.getEmployees(page, pageSize, name);

        return Result.success(iPage);
    }


    /**
     * 修改页面反查详情
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result<Employee> getEmployeeById(@PathVariable Long id) {

        Employee employee = employeeService.getEmployeeById(id);

        if (employee == null) {
            return Result.err("查询失败");
        }

        return Result.success(employee);
    }


    @PutMapping
//    public Result updateEmployee(HttpServletRequest request, @RequestBody Employee employee) {
    public Result updateEmployee(HttpServletRequest request, @RequestBody Employee employee) {

//        //设置更新人id
//        employee.setUpdateUser((Long) request.getSession().getAttribute("employee"));
//        //设置更新时间
//        employee.setUpdateTime(LocalDateTime.now());
        //调用业务层执行更新操作
        boolean flag = employeeService.updateEmployee(employee);

        if(!flag) {
            return Result.err("修改失败");
        }

        return Result.success("修改成功");
    }

}