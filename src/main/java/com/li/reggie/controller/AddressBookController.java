package com.li.reggie.controller;

import com.li.reggie.domain.AddressBook;
import com.li.reggie.service.AddressBookService;
import com.li.reggie.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/addressBook")
public class AddressBookController {

    @Autowired
    private AddressBookService addressBookService;

    @PostMapping
    public Result addAddressBook(@RequestBody AddressBook addressBook) {

        boolean flag = addressBookService.addAddressBook(addressBook);

        if (!flag) {
            return Result.err("新增地址失败");
        }

        return Result.success("新增地址成功");
    }


    @PutMapping
    public Result updateAddressBook(@RequestBody AddressBook addressBook) {

        boolean flag = addressBookService.updateAddressBook(addressBook);

        if (!flag) {
            return Result.err("修改地址失败");
        }

        return Result.success("修改地址成功");
    }


    @GetMapping("/{id}")
    public Result<AddressBook> getAddressBookById(@PathVariable Long id) {

        AddressBook addressBook = addressBookService.getAddressBookById(id);

        return Result.success(addressBook);
    }

    @GetMapping("/list")
    public Result<List<AddressBook>> getAddressBookList() {

        List<AddressBook> addressBookList = addressBookService.getAddressBookList();

        return Result.success(addressBookList);
    }

    @GetMapping("/default")
    public Result getDefault() {

        AddressBook addressBook = addressBookService.getDefault();

        if( addressBook != null ) {
            return Result.success(addressBook);
        }

        return Result.err("没有默认地址");
    }

    @PutMapping("/default")
    public Result setDefault(@RequestBody AddressBook addressBook) {

        boolean flag = addressBookService.setDefault(addressBook);

        if (!flag) {
            return Result.err("设置默认地址失败");
        }

        return Result.success("设置默认地址成功");
    }
}
