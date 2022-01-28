package com.bjpowernode.crm.vo;

import java.util.List;

public class PaginationVo<T> {
    private int tatol;
    private List<T> dataList;

    public int getTatol() {
        return tatol;
    }

    public void setTatol(int tatol) {
        this.tatol = tatol;
    }

    public List<T> getDataList() {
        return dataList;
    }

    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
    }
}
