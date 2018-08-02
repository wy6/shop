package com.zcib.domain;

/**
 * Created by WY on 2017/12/6.
 */

public class Category {

    private int categoryid;
    private String name;
    private int sort;

    public int getCategoryid() {
        return categoryid;
    }

    public void setCategoryid(int categoryid) {
        this.categoryid = categoryid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    @Override
    public String toString() {
        return "Category{" +
                "categoryid=" + categoryid +
                ", name='" + name + '\'' +
                ", sort=" + sort +
                '}';
    }
}
