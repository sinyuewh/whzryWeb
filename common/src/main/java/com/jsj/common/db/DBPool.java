package com.jsj.common.db;/**
 * Created by jinshouji on 2018/4/25.
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;

/**
 * @author ：jinshouji
 * @create ：2018-04-25 16:20
 * @Remark ：jdbc数据库连接池
 **/

public class DBPool {
    private static String driverClass = "com.mysql.jdbc.Driver";

    //声明MyPool对象时自动注册驱动
    static {
        try {
            Class.forName(driverClass);
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    //设置注册属性
    private String url = "jdbc:mysql://localhost:3306/asset?useUnicode=true&characterEncoding=UTF-8";
    //        private String url = "jdbc:mysql://36.111.197.67:3306/asset_test?useUnicode=true&characterEncoding=UTF-8";
    private String user = "root";
    private String password = "carpenters";
    private long startTime = System.currentTimeMillis();
    //设置连接池属性
    private int initSize = 5;
    private int maxSize = 8;
    //用LinkedList对象来保存connection对象
    private LinkedList<Connection> connList = new LinkedList<Connection>();
    //声明一个临时变量来计算连接对象的数量
    private int currentsize = 0;

    //构造方法，初始化连接池，并往里面添加连接对象
    public DBPool() {
        for (int i = 0; i < initSize; i++)

        {
            Connection connection = this.getConnection();
            connList.add(connection);
            currentsize++;
        }
    }

    //获取连接的方法
    private Connection getConnection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return conn;
    }

    public void init() {
        close(connList);
        currentsize = 0;
        connList = new LinkedList<Connection>();
        for (int i = 0; i < initSize; i++) {
            Connection connection = this.getConnection();
            connList.add(connection);
            currentsize++;
        }
    }

    //获取连接池中的一个连接对象
    public Connection getConnFromPool() {
        if ((System.currentTimeMillis() - startTime) > 28800000) {
            init();
        }
        startTime = System.currentTimeMillis();
        //当连接池还没空
        if (connList.size() > 0) {
            Connection connection = connList.getFirst();
            if (null == connection)
                connection = this.getConnection();
            connList.removeFirst();
            return connection;

        } else if (connList.size() == 0 && currentsize < 8) {
            //连接池被拿空，且连接数没有达到上限，创建新的连接
            currentsize++;
            connList.addLast(this.getConnection());

            Connection connection = connList.getFirst();
            connList.removeFirst();
            return connection;
        }

        throw new RuntimeException("连接数达到上限，请等待");
    }

    //把用完的连接放回连接池
    public void releaseConnection(Connection connection) {
        connList.addLast(connection);
    }

    private void close(LinkedList<Connection> list) {
        if (null != list && list.size() > 0) {
            try {
                for (Connection conn : list) {
                    if (null != conn) {
                        conn.close();
                    }
                }
            } catch (Exception e) {
                System.out.println("<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>");
                e.printStackTrace();
            }
        }
    }
}
