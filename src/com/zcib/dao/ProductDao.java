package com.zcib.dao;

import com.sun.org.apache.xpath.internal.SourceTree;
import com.zcib.domain.Product;
import com.zcib.util.JDBCUtils;

import java.util.List;
import java.util.Map;

/**
 * Created by WY on 2017/12/18.
 */

public class ProductDao {

    public void add(Product product) {

        String sql = "insert into product values(null, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        Object params[] = {
                product.getName(),product.getPrice(),product.getMarkprice(),product.getQuality(),product.getHit(),
                product.getTime(),product.getPhoto(),product.getIntroduction(),product.getContent(),product.getCategoryid()
        };
        Number number = (Number)JDBCUtils.insert(sql, params);
        product.setProductid(number.intValue());
    }

    public int findCount() {

//        String sql = "select count(*) from product";
//        return ((Number)JDBCUtils.selectScalar(sql)).intValue();
        return findCount(null, null);
    }

    public int findCount(String skey, String keywords) {

        StringBuilder sql = new StringBuilder("select count(*) from product");
        if (skey != null && skey.trim().length() > 0 && keywords != null && keywords.trim().length() > 0) {
            sql.append(" where " + skey.toString() + " like \"%" + keywords + "%\"");
        }
        System.out.println("**ProductDao.findCount: skey=" + skey + "," + "keywords=" + keywords +"," + "查询结果个数" + ((Number)JDBCUtils.selectScalar(sql.toString())).intValue());
        return ((Number)JDBCUtils.selectScalar(sql.toString())).intValue();
    }

    public List<Map<String, Object>> findAll(int startIndex, int size) {

//        String sql = "select p.productid, p.content, p.hit, p.markprice, p.name, p.photo, p.price, p.categoryid, p.quality, p.time, p.introduction, c.name as cname from product as p,category as c where p.categoryid = c.categoryid limit ?, ?";
//        return JDBCUtils.select(sql, startIndex, size);
        return findAll(startIndex, size, null, null);
    }

    public List<Map<String, Object>> findAll(int startIndex, int size, String skey, String keywords) {

//        String sql = "select p.productid, p.content, p.hit, p.markprice, p.name, p.photo, p.price, p.categoryid, p.quality, p.time, p.introduction, c.name as cname from product as p,category as c where p.categoryid = c.categoryid";
//        StringBuilder str = new StringBuilder("");
//        if (skey != null && skey.trim().length() > 0 && keywords != null && keywords.trim().length() > 0) {
//            str.append(" and p." + skey + " like \"%" + keywords + "%\"");
//        }
//        System.out.println("**ProductDao.findAll: startIndex=" + startIndex + "," + "size=" + size + "," + "skey=" + skey + "," + "keywords=" + keywords + "," + JDBCUtils.select(sql + str.toString() + " limit ?, ?", startIndex, size));
//        return JDBCUtils.select(sql + str.toString() + " limit ?, ?", startIndex, size);
        return findAll(startIndex, size, skey, keywords, null, null);
    }

    public Map<String,Object> findById(int productid) {

        String sql = "select p.productid, p.content, p.hit, p.markprice, p.name, p.photo, p.price, p.categoryid, p.introduction, p.quality, p.time, c.name as cname from product as p, category as c where p.categoryid = c.categoryid and productid = ?";
        return JDBCUtils.select(sql, productid).get(0);
    }

    public void update(Product product) {

        String sql = "update product set name = ?, price = ?, markprice = ?, quality = ?, hit = ?, time = ?, photo = ?, introduction = ?, content = ?, categoryid= ? where productid =?";
        Object params[] = {
                product.getName(), product.getPrice(), product.getMarkprice(), product.getQuality(),
                product.getHit(), product.getTime(), product.getPhoto(), product.getIntroduction(),
                product.getContent(), product.getCategoryid(), product.getProductid(),
        };
        JDBCUtils.update(sql, params);
    }

    public void deleteById(int productid) {

        String sql = "delete from product where productid = ?";
        JDBCUtils.update(sql, productid);
    }

    public void deleteMore(String[] ids) {

        String sql = "delete from product where productid in(";
        StringBuilder str = new StringBuilder("");
        for (int i = 0; i < ids.length; i++) {
            if (i == ids.length - 1) {
                str.append("?)");
            } else {
                str.append("?,");
            }
        }
        JDBCUtils.update(sql + str.toString(), ids);
    }

    public List<Map<String,Object>> findAll(int startIndex, int size, String skey, String keywords, String sortkey, String sort) {

        String sql = "select p.productid, p.content, p.hit, p.markprice, p.name, p.photo, p.price, p.categoryid, p.quality, p.time, p.introduction, c.name as cname from product as p,category as c where p.categoryid = c.categoryid";
        StringBuilder str = new StringBuilder("");
        if (skey != null && skey.trim().length() > 0 && keywords != null && keywords.trim().length() > 0) {
            str.append(" and p." + skey + " like \"%" + keywords + "%\"");
        }
        if (sortkey != null && !(sortkey.trim().isEmpty()) && sort != null && !(sort.trim().isEmpty())) {
            str.append(" order by " + sortkey + " " + sort + " ");
        }
        System.out.println("**ProductDao.findAll: startIndex=" + startIndex + ",size=" + size + ",skey=" + skey + ",keywords=" + keywords + ",sortkey=" + sortkey + ",sort=" + sort + "\n");
        return JDBCUtils.select(sql + str.toString() + " limit ?, ?", startIndex, size);
    }

    public List<Map<String,Object>> findTotal() {
        String sql = "select productid,name,price,photo from product";
        return JDBCUtils.select(sql);
    }
}
