package com.zcib.domain;

import java.util.Date;

/**
 * Created by WY on 2018/1/10.
 */

public class Activate {
    private int activateid;//激活码id
    private String code;//激活码
    private Date expiredate;//激活码过期时间
    private int vipid;//所属vipid

    public int getActivateid() {
        return activateid;
    }

    public void setActivateid(int activateid) {
        this.activateid = activateid;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Date getExpiredate() {
        return expiredate;
    }

    public void setExpiredate(Date expiredate) {
        this.expiredate = expiredate;
    }

    public int getVipid() {
        return vipid;
    }

    public void setVipid(int vipid) {
        this.vipid = vipid;
    }

    @Override
    public String toString() {
        return "Activate{" +
                "activateid=" + activateid +
                ", code='" + code + '\'' +
                ", expiredate=" + expiredate +
                ", vipid=" + vipid +
                '}';
    }
}
