package com.ruoyi.cmp.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 传感器数据对象 configure_sensor_data
 * 
 * @author xjs
 * @date 2022-11-26
 */
public class ConfigureSensorData extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /**  */
    private Long id;

    /** 传感器id
 */
    @Excel(name = "传感器id")
    private Long sensorId;

    /** 原始数据 */
    @Excel(name = "原始数据")
    private String originalData;

    /** 温度数据 */
    @Excel(name = "温度数据")
    private String tempData;

    /** 位移数据 */
    @Excel(name = "位移数据")
    private String driftData;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setSensorId(Long sensorId) 
    {
        this.sensorId = sensorId;
    }

    public Long getSensorId() 
    {
        return sensorId;
    }
    public void setOriginalData(String originalData) 
    {
        this.originalData = originalData;
    }

    public String getOriginalData() 
    {
        return originalData;
    }
    public void setTempData(String tempData) 
    {
        this.tempData = tempData;
    }

    public String getTempData() 
    {
        return tempData;
    }
    public void setDriftData(String driftData) 
    {
        this.driftData = driftData;
    }

    public String getDriftData() 
    {
        return driftData;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("sensorId", getSensorId())
            .append("originalData", getOriginalData())
            .append("tempData", getTempData())
            .append("driftData", getDriftData())
            .append("createTime", getCreateTime())
            .toString();
    }
}
