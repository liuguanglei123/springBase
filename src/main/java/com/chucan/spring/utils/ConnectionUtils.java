package com.chucan.spring.utils;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @Author: Yeman
 * @CreatedDate: 2022-06-12-20:52
 * @Description:
 */
public class ConnectionUtils {

    private ThreadLocal<Connection> threadLocal = new ThreadLocal<>();

    /**
     * 从当前线程获取连接
     */
    public Connection getCurrentThreadConn() throws SQLException {
        /**
         * 判断当前线程中是否已经绑定连接，如果没有绑定，需要从连接池获取一个连接绑定到当前线程
         */
        Connection connection = null;
        if(threadLocal.get() == null){
            // 从连接池拿连接并绑定到线程
            connection = DruidUtils.getInstance().getConnection();
            // 绑定到当前线程
            threadLocal.set(connection);
        }

        return connection;
    }
}
