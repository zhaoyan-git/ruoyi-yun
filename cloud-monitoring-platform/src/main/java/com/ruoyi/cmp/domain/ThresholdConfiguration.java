package com.ruoyi.cmp.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * 阈值配置对象 threshold_configuration
 *
 * @author zy
 * @date 2022-11-23
 */
public class ThresholdConfiguration extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private Long id;

    /**
     * 结构物id
     */
    @Excel(name = "结构物id")
    private Long sid;

    /**
     * 结构物名称
     */
    @Excel(name = "结构物名称")
    private String sname;

    /**
     * 测点id
     */
    @Excel(name = "测点id")
    private Long mpid;

    /**
     * 测点名称
     */
    @Excel(name = "测点名称")
    private String mpname;

    /**
     * 监测因素id
     */
    @Excel(name = "监测因素id")
    private String mfid;

    /**
     * 监测因素名称
     */
    @Excel(name = "监测因素名称")
    private String mfname;

    /**
     * 监测项id
     */
    @Excel(name = "监测项id")
    private String miid;

    /**
     * 监测项名称
     */
    @Excel(name = "监测项名称")
    private String miname;

    /**
     * 一级阈值
     */
    @Excel(name = "一级阈值")
    private String lev1value;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    /**
     * 二级阈值
     */
    @Excel(name = "二级阈值")
    private String lev2value;

    public String getRateType() {
        return rateType;
    }

    public void setRateType(String rateType) {
        this.rateType = rateType;
    }


    public ArrayList<String> getRateTypeList() {
        return rateTypeList;
    }

    public void setRateTypeList(ArrayList<String> rateTypeList) {
        this.rateTypeList = rateTypeList;
    }

    private String type;



    private ArrayList<String> checkList;

    private String rateType;
    private ArrayList<String> rateTypeList;

    private LinkedHashMap<String,ArrayList<LinkedHashMap<String,String>>> dynamicValidateForm;



    /**
     * 三级阈值
     */
    @Excel(name = "三级阈值")
    private String lev3value;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setSid(Long sid) {
        this.sid = sid;
    }

    public Long getSid() {
        return sid;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public String getSname() {
        return sname;
    }

    public void setMpid(Long mpid) {
        this.mpid = mpid;
    }

    public LinkedHashMap<String, ArrayList<LinkedHashMap<String, String>>> getDynamicValidateForm() {
        return dynamicValidateForm;
    }

    public void setDynamicValidateForm(LinkedHashMap<String, ArrayList<LinkedHashMap<String, String>>> dynamicValidateForm) {
        this.dynamicValidateForm = dynamicValidateForm;
    }

    public ArrayList<String> getCheckList() {
        return checkList;
    }

    public void setCheckList(ArrayList<String> checkList) {
        this.checkList = checkList;
    }


    public Long getMpid() {
        return mpid;
    }

    public void setMpname(String mpname) {
        this.mpname = mpname;
    }

    public String getMpname() {
        return mpname;
    }

    public void setMfid(String mfid) {
        this.mfid = mfid;
    }

    public String getMfid() {
        return mfid;
    }

    public void setMfname(String mfname) {
        this.mfname = mfname;
    }

    public String getMfname() {
        return mfname;
    }

    public void setMiid(String miid) {
        this.miid = miid;
    }

    public String getMiid() {
        return miid;
    }

    public void setMiname(String miname) {
        this.miname = miname;
    }

    public String getMiname() {
        return miname;
    }

    public void setLev1value(String lev1value) {
        this.lev1value = lev1value;
    }

    public String getLev1value() {
        return lev1value;
    }

    public void setLev2value(String lev2value) {
        this.lev2value = lev2value;
    }

    public String getLev2value() {
        return lev2value;
    }

    public void setLev3value(String lev3value) {
        this.lev3value = lev3value;
    }

    public String getLev3value() {
        return lev3value;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("sid", getSid())
                .append("sname", getSname())
                .append("mpid", getMpid())
                .append("mpname", getMpname())
                .append("mfid", getMfid())
                .append("mfname", getMfname())
                .append("miid", getMiid())
                .append("miname", getMiname())
                .append("lev1value", getLev1value())
                .append("lev2value", getLev2value())
                .append("lev3value", getLev3value())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("remark", getRemark())
                .toString();
    }
}
