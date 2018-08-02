package com.zcib.dao;

import com.zcib.domain.User;
import com.zcib.util.JDBCUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by WY on 2017/12/21.
 */

public class UserDao {

    public void add(User user) {

        String sql = "insert into user value(null, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        Object params[] = {
                user.getUsername(), user.getPassword(), user.getSex(), user.getEmail(),
                user.getPhoto(), user.getScore(), user.getQuestion(), user.getAnswer(),
                user.getLastlogintime(), user.getStatus(),
        };

        int id = ((Number) JDBCUtils.insert(sql, params)).intValue();
        user.setVipid(id);
    }

    public User findByUsername(String username) {

        String sql = "select * from user where username = ?";
        User user = JDBCUtils.selectToBean(User.class, sql, username);
//        List<Map<String, Object>> list = JDBCUtils.select(sql, username);
//        User user = null;
//        if (list.size() > 0) {
//            Map<String, Object> map = list.get(0);
//            user = new User();
//            user.setUsername(username);
//            user.setVipid((Integer) map.get("vipid"));
//            user.setAnswer((String) map.get("answer"));
//            user.setLastlogintime((Date) map.get("lastlogintime"));
//            user.setPassword((String) map.get("password"));
//            user.setPhoto((String) map.get("photo"));
//            user.setQuestion((String) map.get("question"));
//            user.setScore((Long) map.get("score"));
//            user.setSex((String) map.get("sex"));
//        }
        return user;
    }

    //根据vipid查询用户激活状态
    public boolean findStatus(int vipid) {
        String sql = "select status from users where vipid = ?";
        return JDBCUtils.selectScalar(sql, vipid);
    }

    //更新用户激活状态
    public void updateStatus(int status, int vipid) {
        String sql = "update users set status = ? where vipid = ?";
        JDBCUtils.update(sql, vipid);
    }

    //查询用户
    public List<Map<String, Object>> findAllUser(int startIndex, int size) {
//        String sql = "select * from users where status = ?";
//        return JDBCUtils.select(sql, startIndex, size, status);
        return findAllUser(startIndex, size, null, null, null);
    }

    //查询用户
    public List<Map<String, Object>> findAllUser(int startIndex, int size, String status) {
//        String sql = "select * from users where status = ?";
//        return JDBCUtils.select(sql, startIndex, size, status);
        return findAllUser(startIndex, size, status, null, null);
    }

    public List<Map<String, Object>> findAllUser(int startIndex, int size, String skey, String keywords) {
        return findAllUser(startIndex, size, null, skey, keywords);
    }

    public List<Map<String, Object>> findAllUser(int startIndex, int size, String status, String skey, String keywords) {
        String sql = "select * from user";
        StringBuilder str = new StringBuilder("");
        if (status != null) {
            str.append(" where status = " + status);
        }
        if (status != null && skey != null && skey.trim().length() > 0 && keywords != null && keywords.trim().length() > 0) {
            str.append(" and " + skey + "like \"/%" + keywords + "%\"");
        }
        if (status == null && skey != null && skey.trim().length() > 0 && keywords != null && keywords.trim().length() > 0) {
            str.append(" where " + skey + "like \"/%" + keywords + "%\"");
        }
        System.out.println("**UserDao.findAllUser.76:"+"sql="+sql+str.toString()+",startIndex="+startIndex+",size="+size);
        List<Map<String, Object>> list = JDBCUtils.select(sql + str.toString() + " limit ?, ?;", startIndex, size);
        return list;
    }

    public int findCount(String skey, String keywords) {
        StringBuilder sql = new StringBuilder("select count(*) from user");
        if (skey != null && skey.trim().length() > 0 && keywords != null && keywords.trim().length() > 0) {
            sql.append(" where" + skey.toString() + " like \"%" + keywords + "%\"");
        }
        return ((Number)JDBCUtils.selectScalar(sql.toString())).intValue();
    }

    public boolean usernameEx(String username) {
        String sql = "select * from user where username = ?";
        List<Map<String, Object>> list = JDBCUtils.select(sql, username);
        boolean flag = true;
        if (list == null) {
            flag = false;
        }
        return flag;
    }
}
