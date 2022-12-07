package com.ruoyi.cmp.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.ArrayList;
import java.util.List;

/**
 * 【请填写功能名称】对象 structure
 *
 * @author zy
 * @date 2022-08-31
 */
public class Statistics extends BaseEntity {
    private static final long serialVersionUID = 1L;

    private String totalNum;
    private String projectNum;



    private String sNum;
    private String eNum;
    private Object projectArr;

    private ArrayList<Integer> dtuDataList;
    private  ArrayList<Integer> exceptionDataList;

    public ArrayList<Integer> getDtuDataList() {
        return dtuDataList;
    }

    public void setDtuDataList(ArrayList<Integer> dtuDataList) {
        this.dtuDataList = dtuDataList;
    }

    public ArrayList<Integer> getExceptionDataList() {
        return exceptionDataList;
    }

    public void setExceptionDataList(ArrayList<Integer> exceptionDataList) {
        this.exceptionDataList = exceptionDataList;
    }

    public String getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(String totalNum) {
        this.totalNum = totalNum;
    }

    public String getProjectNum() {
        return projectNum;
    }

    public void setProjectNum(String projectNum) {
        this.projectNum = projectNum;
    }

    public String getsNum() {
        return sNum;
    }

    public void setsNum(String sNum) {
        this.sNum = sNum;
    }

    public String geteNum() {
        return eNum;
    }

    public void seteNum(String eNum) {
        this.eNum = eNum;
    }

    public Object getProjectArr() {
        return projectArr;
    }

    public void setProjectArr(Object projectArr) {
        this.projectArr = projectArr;
    }




}
