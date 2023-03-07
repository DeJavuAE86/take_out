package com.li.reggie.service;

import com.li.reggie.domain.AddressBook;

import java.util.List;

public interface AddressBookService {

    /**
     * 新增地址
     *
     * @param addressBook
     * @return
     */
    boolean addAddressBook(AddressBook addressBook);


    /**
     * 修改地址
     *
     * @param addressBook
     * @return
     */
    boolean updateAddressBook(AddressBook addressBook);

    /**
     * 根据id查询地址
     *
     * @param id
     * @return
     */
    AddressBook getAddressBookById(Long id);

    /**
     * 查询全部地址
     *
     * @return
     */
    List<AddressBook> getAddressBookList();

    /**
     * 查询默认地址
     *
     * @return
     */
    AddressBook getDefault();

    /**
     * 设置默认地址
     *
     * @param addressBook
     * @return
     */
    boolean setDefault(AddressBook addressBook);
}
