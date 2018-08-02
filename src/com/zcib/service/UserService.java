package com.zcib.service;

import com.zcib.dao.ActivateDao;
import com.zcib.dao.UserDao;
import com.zcib.domain.Activate;
import com.zcib.domain.Page;
import com.zcib.domain.User;
import com.zcib.exception.UserException;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by WY on 2017/12/21.
 */

public class UserService {

    UserDao userDao = new UserDao();
    ActivateDao activateDao = new ActivateDao();

    public void regist(User user) {

        userDao.add(user);
    }

    public boolean usernameEx(String username) {
        return userDao.usernameEx(username);
    }

    public User login(String username, String password) throws UserException {

        /**
         * 1.先判断用户是否已经激活，再判断用户名是否存在，调用Dao层findByUsername方法；
         * 2.若存在，Dao层方法返回user对象；不存在，则抛出异常；
         * 3.用user对象的密码与传递来的密码进行匹配，判断密码是否正确。
         */
        User user = userDao.findByUsername(username);
        if (user == null)
            throw new UserException("用户名不存在！");
        if (!(user.getPassword().equals(password))) {
            throw new UserException("密码错误！");
        }
        return user;
    }

    //将激活码写入数据库
    public void addActivate(Activate activate) {
        //定义Activate对象，将值封装到该对象中
        activateDao.add(activate);
    }

    public void activate(String code) throws UserException {
        /**
         * 1.查看激活码是否正确，若正确获取vipid，并判断验证码是否过期
         * 2.根据vipid查询是否激活，若已激活抛出异常，若未激活则激活
         */
        Activate activate = activateDao.findByCode(code);
        if (activate == null) {
            throw new UserException("激活码错误！");
        }
        if (activate.getExpiredate().getTime() < new Date().getTime()) {
            throw new UserException("激活码已经过期！");
        }
        int vipid = activate.getVipid();
        if (userDao.findStatus(vipid)) {
            throw new UserException("不能重复激活！");
        }
        userDao.updateStatus(1, vipid);
    }

    public Page findAllUser(int currntPage, String status, String skey, String keywords) {
        int totalsize = userDao.findCount(skey, keywords);
        Page page = new Page(currntPage, totalsize);
        if (totalsize > 0) {
            List<Map<String, Object>> list = userDao.findAllUser(page.getStartIndex(), page.getPageSize(), status, skey, keywords);
            page.setList(list);
        }
        return page;
    }

    public Page findAllUser(int currntPage, String skey, String keywords) {
        return findAllUser(currntPage, null, skey, keywords);
    }
}
