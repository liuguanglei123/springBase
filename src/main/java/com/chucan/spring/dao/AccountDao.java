package com.chucan.spring.dao;

import com.chucan.spring.pojo.Account;

/**
 * @Author: chucan
 * @CreatedDate: 2022-06-10-2:07
 * @Description:
 */
public interface AccountDao {

    Account queryAccountByCardNo(String cardNo) throws Exception;

    int updateAccountByCardNo(Account account) throws Exception;
}
