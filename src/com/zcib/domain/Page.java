package com.zcib.domain;

import java.util.List;
import java.util.Map;

/**
 * Created by WY on 2017/12/11.
 */

public class Page<T> {

    private int pageSize = 10;//页面显示的记录数
    private int totalSize;//记录总数
    private int currentPage;//当前页码
    private List<T> list;//分页的数据
    private int num = 6;//页码列表的个数

//    totalPage 总页数
//    startIndex 要读取记录的起始值
//    start 页码列表的起始值
//    end 页码列表的终止值


    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public Page(int currentPage, int totalSize) {

        this.totalSize = totalSize;
        setCurrentPage(currentPage);
    }

    public Page(int currentPage, int totalSize, int pageSize) {

        this.totalSize = totalSize;
        setCurrentPage(currentPage);
        this.pageSize = pageSize;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getTotalSize() {
        return totalSize;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        if(currentPage < 1)
            currentPage = 1;
        if(currentPage > getTotalPage())
            currentPage = getTotalPage();
        this.currentPage = currentPage;
    }

    public int getTotalPage() {

        return (totalSize % pageSize == 0) ? (totalSize / pageSize) : (totalSize / pageSize) + 1;
    }


    public int getStartIndex() {
        int startIndex = (currentPage - 1) * pageSize;
        return startIndex;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getStart() {
        int start = currentPage - num / 2 + 1;
        if (start < 1)
            start = 1;
        return start;
    }

    public int getEnd() {
        int end = getStart() + num - 1;
        if(end > getTotalPage())
            end = getTotalPage();
        return end;
    }

    @Override
    public String toString() {
        return "Page{" +
                "pageSize=" + pageSize +
                ", totalSize=" + totalSize +
                ", currentPage=" + currentPage +
                ", list=" + list +
                ", num=" + num +
                '}';
    }
}
