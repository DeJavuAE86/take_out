package com.li.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.li.reggie.dao.AddressBookDao;
import com.li.reggie.domain.AddressBook;
import com.li.reggie.service.AddressBookService;
import com.li.reggie.utils.BaseContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AddressBookServiceImpl implements AddressBookService {

    @Autowired
    private AddressBookDao addressBookDao;

    /**
     * 新增地址
     *
     * @param addressBook
     * @return
     */
    @Override
    public boolean addAddressBook(AddressBook addressBook) {

        addressBook.setUserId(BaseContext.get());

        return addressBookDao.insert(addressBook) > 0;
    }

    /**
     * 修改地址
     * @param addressBook
     * @return
     */
    @Override
    public boolean updateAddressBook(AddressBook addressBook) {

        return addressBookDao.updateById(addressBook) > 0;
    }

    /**
     * 根据id查询地址
     *
     * @param id
     * @return
     */
    @Override
    public AddressBook getAddressBookById(Long id) {

        LambdaQueryWrapper<AddressBook> lqw = new LambdaQueryWrapper<AddressBook>();
        lqw.eq(AddressBook::getId, id);

        AddressBook addressBook = addressBookDao.selectOne(lqw);

        return addressBook;
    }

    /**
     * 查询全部地址
     *
     * @return
     */
    @Override
    public List<AddressBook> getAddressBookList() {

        LambdaQueryWrapper<AddressBook> lqw = new LambdaQueryWrapper<AddressBook>();
        lqw.eq(AddressBook::getUserId, BaseContext.get());

        List<AddressBook> addressBookList = addressBookDao.selectList(lqw);

        return addressBookList;
    }

    /**
     * 查询默认地址
     *
     * @return
     */
    @Override
    public AddressBook getDefault() {

        LambdaQueryWrapper<AddressBook> lqw = new LambdaQueryWrapper<AddressBook>();
        lqw.eq(AddressBook::getUserId, BaseContext.get());
        lqw.eq(AddressBook::getIsDefault, 1);

        AddressBook addressBook = addressBookDao.selectOne(lqw);

        return addressBook;
    }

    /**
     * 设置默认地址
     *
     * @param addressBook
     * @return
     */
    @Override
    @Transactional
    public boolean setDefault(AddressBook addressBook) {

        LambdaUpdateWrapper<AddressBook> lqw = new LambdaUpdateWrapper<AddressBook>();
        lqw.eq(AddressBook::getUserId, BaseContext.get());

        AddressBook notDefaultAddressBook = new AddressBook();
        notDefaultAddressBook.setIsDefault(0);

        addressBookDao.update(notDefaultAddressBook, lqw);

        addressBook.setIsDefault(1);
        addressBookDao.updateById(addressBook);

        return true;
    }


}
