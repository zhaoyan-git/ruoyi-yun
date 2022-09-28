package com.ruoyi.cmp.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 设备管理对象 equipment
 *
 * @author zy
 * @date 2022-09-04
 */
public class Equipment extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * $column.columnComment
     */
    private Long id;

    /**
     * 设备名称
     */
    @Excel(name = "设备名称")
    private String name;

    public String getIsPage() {
        return isPage;
    }

    public void setIsPage(String isPage) {
        this.isPage = isPage;
    }

    private String isPage;

    /**
     * 所属结构物ID
     */
    @Excel(name = "所属结构物ID")
    private Long businessId;

    public String getBusinessIdIsNull() {
        return businessIdIsNull;
    }

    public void setBusinessIdIsNull(String businessIdIsNull) {
        this.businessIdIsNull = businessIdIsNull;
    }

    /**
     * 型号
     */
    @Excel(name = "型号")
    private String model;

    private String businessIdIsNull;

    /**
     * 厂商
     */
    @Excel(name = "厂商")
    private Long manufacturer;

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    /**
     * 设备ID号
     */
    @Excel(name = "设备ID号")
    private String equipmentId;

    private String deptName;

    /**
     * SIM卡号
     */
    @Excel(name = "SIM卡号")
    private String sim;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setBusinessId(Long businessId) {
        this.businessId = businessId;
    }

    public Long getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(Long manufacturer) {
        this.manufacturer = manufacturer;
    }

    public Long getBusinessId() {
        return businessId;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getModel() {
        return model;
    }


    public void setEquipmentId(String equipmentId) {
        this.equipmentId = equipmentId;
    }

    public String getEquipmentId() {
        return equipmentId;
    }

    public void setSim(String sim) {
        this.sim = sim;
    }

    public String getSim() {
        return sim;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("name", getName())
                .append("businessId", getBusinessId())
                .append("model", getModel())
                .append("manufacturer", getManufacturer())
                .append("equipmentId", getEquipmentId())
                .append("sim", getSim())
                .append("remark", getRemark())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .toString();
    }
}
