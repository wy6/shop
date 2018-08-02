package com.zcib.dao;

import com.zcib.domain.Category;
import com.zcib.util.JDBCUtils;

import java.util.List;
import java.util.Map;

/**
 * Created by WY on 2017/12/6.
 */

public class CategoryDao {

    public void add(Category category) {
        String sql = "insert into category values(null, ?, ?)";
        Object params[] ={
                category.getName(),
                category.getSort(),
        };
        Number n = (Number)JDBCUtils.insert(sql, params);
        category.setCategoryid(n.intValue());
    }

    public List<Map<String, Object>> findAll(int start, int end) {
        String sql = "select * from category limit ?, ?";
        return JDBCUtils.select(sql, start, end);
    }

    public int findCount() {
        String sql = "select count(*) from category";
        return ((Number)JDBCUtils.selectScalar(sql)).intValue();
    }

    public void deleteById(int id) {
        String sql = "delete from category where categoryid = ?";
        JDBCUtils.update(sql, id);
    }

    public void deleteMore(String id[]) {

        String sql = "delete from category where categoryid in(";
        StringBuilder str = new StringBuilder("");
        for(int i = 0; i < id.length; i++) {
            if (i == id.length - 1)
                str.append("?)");
            else
                str.append("?, ");
        }
        JDBCUtils.update(sql+str.toString(), id);
    }

    public Map<String,Object> findById(int id) {

        String sql = "select * from category where categoryid = ?";
        return JDBCUtils.select(sql, id).get(0);

    }

    public void update(Category category) {
        String sql = "update category set name = ?, sort = ? where categoryid = ?";
        Object params[] = {
                category.getName(),category.getSort(), category.getCategoryid()
        };
        JDBCUtils.update(sql, params);
    }

    public List<Map<String,Object>> findAll() {

        String sql = "select * from category order by sort asc";
        return JDBCUtils.select(sql);
    }
}
