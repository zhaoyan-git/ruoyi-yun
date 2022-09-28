package com.ruoyi.cmp.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 【请填写功能名称】对象 structure_monitoring_factors
 *
 * @author zy
 * @date 2022-09-08
 */
public class StructureMonitoringFactors extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * $column.columnComment
     */
    private Long id;

    /**
     * 结构物ID
     */
    @Excel(name = "结构物ID")
    private Long structureId;

    public void setMfId(String mfId) {
        this.mfId = mfId;
    }

    /**
     * 监测因素ID
     */
    @Excel(name = "监测因素ID")
    private String mfId;

    public String getMfId() {
        return mfId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setStructureId(Long structureId) {
        this.structureId = structureId;
    }

    public Long getStructureId() {
        return structureId;
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("structureId", getStructureId())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .toString();
    }
}
