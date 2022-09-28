package com.ruoyi.cmp.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 采集策略对象 collection_strategy
 *
 * @author zy
 * @date 2022-09-06
 */
public class CollectionStrategy extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    private Long id;

    /**
     * 采集策略名称
     */
    @Excel(name = "采集策略名称")
    private String name;

    /**
     * 设备id
     */
    @Excel(name = "结构物Id")
    private Long businessId;
    @Excel(name = "设备id")
    private Long equipmentId;

    public Long getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(Long equipmentId) {
        this.equipmentId = equipmentId;
    }

    /**
     * 采集时间(默认为分钟)
     */
    @Excel(name = "采集时间(默认为分钟)")
    private Long svalue;

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

    public Long getBusinessId() {
        return businessId;
    }

    public void setSvalue(Long svalue) {
        this.svalue = svalue;
    }

    public Long getSvalue() {
        return svalue;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("name", getName())
                .append("businessId", getBusinessId())
                .append("svalue", getSvalue())
                .append("remark", getRemark())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .toString();
    }
}
