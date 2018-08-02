package com.zcib.domain;

/**
 * Created by WY on 2018/1/6.
 */

public class Address {

    private int addressid;
    private String addressname;
    private String province;
    private String city;
    private String area;
    private String postcode;
    private String receiver;
    private String phone;
    private int vipid;

    public int getAddressid() {
        return addressid;
    }

    public void setAddressid(int addressid) {
        this.addressid = addressid;
    }

    public String getAddressname() {
        return addressname;
    }

    public void setAddressname(String addressname) {
        this.addressname = addressname;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getVipid() {
        return vipid;
    }

    public void setVipid(int vip) {
        this.vipid = vipid;
    }

    @Override
    public String toString() {
        return "Address{" +
                "addressid=" + addressid +
                ", addressname='" + addressname + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", area='" + area + '\'' +
                ", postcode='" + postcode + '\'' +
                ", receiver='" + receiver + '\'' +
                ", phone='" + phone + '\'' +
                ", vipid=" + vipid +
                '}';
    }
}
