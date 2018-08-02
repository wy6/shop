package com.zcib.service;

import com.zcib.dao.ProductDao;
import com.zcib.domain.Page;
import com.zcib.domain.Product;

import java.util.List;
import java.util.Map;

/**
 * Created by WY on 2017/12/18.
 */

public class ProductService {

    private ProductDao productDao = new ProductDao();

    public void add(Product product) {

        productDao.add(product);
    }

    public Page<Map<String , Object>> findAll(int currentPage, String skey, String keywords) {

        int totalSize = productDao.findCount(skey, keywords);
        Page<Map<String , Object>> page = new Page<>(currentPage, totalSize);
        if (totalSize > 0) {
            List<Map<String, Object>> list = productDao.findAll(page.getStartIndex(), page.getPageSize(), skey, keywords);
            page.setList(list);
        }
        return page;
    }

    public Map<String, Object> findById(int productid) {

        return productDao.findById(productid);
    }

    public void update(Product product) {

        productDao.update(product);
    }

    public void deleteById(int productid) {

        productDao.deleteById(productid);
    }

    public void deleteMore(String[] ids) {
        productDao.deleteMore(ids);
    }

    public List<Map<String, Object>> findIndex() {

        return productDao.findAll(0, 12);
    }

    public Page findAll(int currentPage, String skey, String keywords, String sortkey, String sort) {

        int totalSize = productDao.findCount(skey, keywords);
        Page page = new Page(currentPage, totalSize, 12);
        if (totalSize > 0) {
            List<Map<String, Object>> list = productDao.findAll(page.getStartIndex(), page.getPageSize(), skey, keywords, sortkey, sort);
            page.setList(list);
        }
        return page;
    }

    public List<Map<String, Object>> findTotal(){
        return productDao.findTotal();
    }
}
