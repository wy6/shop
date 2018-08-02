package com.zcib.service;

import com.zcib.dao.CategoryDao;
import com.zcib.domain.Category;
import com.zcib.domain.Page;

import java.util.List;
import java.util.Map;

/**
 * Created by WY on 2017/12/6.
 */

public class CategoryService {

    private CategoryDao categoryDao = new CategoryDao();

    public void add(Category category) {
        categoryDao.add(category);
    }

    public Page findAll(int currentPage) {

        /**
         * 1.调用Dao层，获取记录总数
         * 2.创建Page对象
         * 3.调用Dao层获取数据
         * 4.返回Page对象
         */
        int totalSize = categoryDao.findCount();
        Page page = new Page(currentPage, totalSize);
        List<Map<String, Object>> list = categoryDao.findAll(page.getStartIndex(), page.getPageSize());
        page.setList(list);
        return page;
    }

    public void deleteById(int id) {
        categoryDao.deleteById(id);
    }

    public void deleteMore(String id[]) {
        categoryDao.deleteMore(id);
    }

    public Map<String, Object> findById(int id) {
        return categoryDao.findById(id);
    }

    public void update(Category category) {
        categoryDao.update(category);
    }

    public List<Map<String, Object>> findAll() {
        return categoryDao.findAll();
    }

}
