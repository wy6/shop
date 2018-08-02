package com.zcib.util;

import java.io.InputStream;
import java.sql.*;
import java.util.*;

/**
 * Created by WY on 2017/12/6.
 */

public class JDBCUtils {
    private static String driver;
    private static String url;
    private static String name;
    private static String password;
    private static ThreadLocal<Connection> tl = new ThreadLocal<Connection>();

    static {
        try {
            Properties props = new Properties();
            InputStream in = JDBCUtils.class.getClassLoader().getResourceAsStream("dbConfig.properties");
            props.load(in);
            driver = props.getProperty("driver");
            url = props.getProperty("url");
            name = props.getProperty("name");
            password = props.getProperty("password");
            Class.forName(driver);
        } catch (Exception e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    // 获取数据库连接
    public static Connection getConnection() throws SQLException {
        Connection conn = tl.get();
        if(conn != null){
            return conn;
        }
        return DriverManager.getConnection(url, name, password);
    }

    // 释放连接
    public static void release(Connection conn, Statement stmt, ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        Connection transconn = tl.get();
        if(transconn == conn){
            return;
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    //开启事务
    public static void startTransaction() throws SQLException {

        Connection conn = tl.get();
        if(conn != null){
            throw new SQLException("事务已开启，请勿重复开启！");
        }
        conn = getConnection();
        conn.setAutoCommit(false);
        tl.set(conn);
    }

    //提交事务
    public static void commitTransaction() throws SQLException {
        Connection conn = tl.get();
        if(conn == null){
            throw new SQLException ("没有数据库连接，无法提交事务！");
        }
        conn.commit();
        conn.close();
        conn = null;
        tl.remove();
    }

    //回滚事务，关闭连接
    public static void rollbackTranscation() throws SQLException {

        Connection conn = tl.get();
        if (conn == null) {
            throw new SQLException("没有事务可以回滚！");
        }
        conn.rollback();
        conn.close();
        conn = null;
        tl.remove();
    }

    // 查询操作
    public static List<Map<String, Object>> select(String sql, Object ...params) {

        // 1.加载数据库驱动
        List<Map<String, Object>> list = null;
        Connection conn = null;
        PreparedStatement pre = null;
        ResultSet rs = null;
        try {
            // 2.获取数据库连接
            conn = JDBCUtils.getConnection();
            // 3.获取Statement
            pre = conn.prepareStatement(sql);
            // 设置参数值
            if (params != null) {
                for (int i = 0; i < params.length; i++) {
                    pre.setObject(i + 1, params[i]);
                }
            }
            rs = pre.executeQuery();
            if (rs != null) {
                list = RsToList(rs);
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        } finally {
            JDBCUtils.release(conn, pre, rs);
        }
        return list;
    }

    //将Resuleset对象转换成list
    private static List<Map<String, Object>> RsToList(ResultSet rs) throws SQLException {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        ResultSetMetaData metaData = rs.getMetaData();
        while (rs.next()) {
            Map<String, Object> map = new HashMap<String, Object>();
            for (int i = 1; i <= metaData.getColumnCount(); i++) {
                map.put(metaData.getColumnLabel(i), rs.getObject(i));
            }
            list.add(map);
        }
        return list;
    }

    // 添加操作,并增加主键值
    public static Object insert(String sql, Object ...params) {

        Connection conn = null;
        PreparedStatement pre = null;
        ResultSet rs = null;
        Object key = null;

        try {
            conn = JDBCUtils.getConnection();
            pre = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            if (params != null) {
                for (int i = 0; i < params.length; i++) {
                    pre.setObject(i + 1, params[i]);
                }
            }
            pre.executeUpdate();
            // 获取主键
            rs = pre.getGeneratedKeys();
            if (rs.next()) {
                key = rs.getInt(1);
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        } finally {
            JDBCUtils.release(conn, pre, rs);
        }
        return key;
    }

    // 更新操作，修改、删除数据
    public static void update(String sql, Object ...params) {
        Connection conn = null;
        PreparedStatement pre = null;
        try {
            conn = JDBCUtils.getConnection();
            pre = conn.prepareStatement(sql);
            if (params != null) {
                for (int i = 0; i < params.length; i++) {
                    pre.setObject(i + 1, params[i]);
                }
            }
           pre.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            release(conn, pre, null);
        }
    }

    private static void fillStatement(PreparedStatement pre, Object ...params) throws SQLException {
        if (params != null) {
            for (int i = 0; i < params.length; i++) {
                pre.setObject(i + 1, params[i]);
            }
        }
    }

    //插入、更新批处理操作
    public static int[] updateBatch(String sql, Object [][]params) {
        Connection conn = null;
        PreparedStatement pre = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            pre = conn.prepareStatement(sql);
            if (params != null) {
                for (int i = 0; i < params.length; i++) {
                    fillStatement(pre, params[i]);
                    pre.addBatch();
                }
            }
            return pre.executeBatch();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally{
            release(conn, pre, null);
        }

    }

    //插入批处理操作,并返回主键值
    public static <T> List<T> insertBatch(String sql, Object [][]params) {
        Connection conn = null;
        PreparedStatement pre = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            pre = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            if (params != null) {
                for (int i = 0; i < params.length; i++) {
                    fillStatement(pre, params[i]);
                    pre.addBatch();
                }
            }
            pre.executeBatch();
            rs = pre.getGeneratedKeys();
            List<T> list = new ArrayList<T>();
            while (rs.next()) {
                list.add((T) rs.getObject(1));
            }
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally{
            release(conn, pre, null);
        }

    }

    //返回单值操作
    public static <T> T selectScalar(String sql, Object... params) {

        Connection conn = null;
        PreparedStatement pre = null;
        ResultSet rs = null;
        T result = null;
        try {
            conn = getConnection();
            pre = conn.prepareStatement(sql);
            fillStatement(pre, params);
            rs = pre.executeQuery();
            if (rs.next()) {
                result = (T)rs.getObject(1);
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    //查找操作
    public static <T> List<T> select(Class<T> clazz,String sql,Object...params){
        /*
         * 1.获取数据库的连接
         * 2.获取Statement对象
         * 3.使用Statement对象方法executeQuery
         * 4.关闭连接
         */

        Connection conn = null;
        PreparedStatement pre = null;
        ResultSet rs = null;
        List<T> list = new ArrayList<T>();

        try {
            // 1.获取数据库的连接
            conn = getConnection();
            //2.1获取Statement对象
            pre = conn.prepareStatement(sql);
            fillStatement(pre, params);
            // 3.使用Statement对象方法executeQuery
            rs = pre.executeQuery();
            list = BeanUtils.BeanListHandler(rs, clazz);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally{
            release(conn, pre, rs);
        }

        return list;
    }

    //查找操作
    public static <T> T selectToBean(Class<T> clazz,String sql,Object...params){
        /*
         * 1.获取数据库的连接
         * 2.获取Statement对象
         * 3.使用Statement对象方法executeQuery
         * 4.关闭连接
         */

        Connection conn = null;
        PreparedStatement pre = null;
        ResultSet rs = null;
        T object = null;

        try {
            // 1.获取数据库的连接
            conn = getConnection();
            //2.1获取Statement对象
            pre = conn.prepareStatement(sql);
            fillStatement(pre, params);
            // 3.使用Statement对象方法executeQuery
            rs = pre.executeQuery();
            object = BeanUtils.BeanHandler(rs, clazz);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally{
            release(conn, pre, rs);
        }

        return object;
    }
}
