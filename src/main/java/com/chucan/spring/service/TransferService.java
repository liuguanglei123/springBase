package com.chucan.spring.service;

/**
 * @Author: chucan
 * @CreatedDate: 2022-06-10-2:10
 * @Description:
 */
public interface TransferService {

    void transfer(String fromCardNo,String toCardNo,int money) throws Exception;

}
