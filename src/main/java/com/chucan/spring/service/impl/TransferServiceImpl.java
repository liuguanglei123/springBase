package com.chucan.spring.service.impl;

import com.chucan.spring.dao.AccountDao;
import com.chucan.spring.factory.BeanFactory;
import com.chucan.spring.pojo.Account;
import com.chucan.spring.service.TransferService;

/**
 * @Author: chucan
 * @CreatedDate: 2022-06-10-2:10
 * @Description:
 */
public class TransferServiceImpl implements TransferService {

    private AccountDao accountDao = (AccountDao)BeanFactory.getBean("accountDao");

    @Override
    public void transfer(String fromCardNo, String toCardNo, int money) throws Exception {
        Account from = accountDao.queryAccountByCardNo(fromCardNo);
        Account to = accountDao.queryAccountByCardNo(toCardNo);

        from.setMoney(from.getMoney()-money);
        to.setMoney(to.getMoney()+money);

        accountDao.updateAccountByCardNo(to);
        int c = 1/0;
        accountDao.updateAccountByCardNo(from);
    }
}
