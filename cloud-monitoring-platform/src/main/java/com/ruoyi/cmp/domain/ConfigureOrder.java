package com.ruoyi.cmp.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 指令信息配置对象 configure_order
 *
 * @author xjs
 * @date 2022-11-26
 */
public class ConfigureOrder extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    private Long id;

    /**
     * 传感器id
     */
    @Excel(name = "传感器id")
    private Long sensorId;

    /**
     * 指令类型 03查询 06写入
     */
    @Excel(name = "指令类型 03查询 06写入")
    private String orderType;

    /**
     * 地址hi
     */
    @Excel(name = "地址hi")
    private String addressHi;

    /**
     * 地址lo
     */
    @Excel(name = "地址lo")
    private String addressLo;

    /**
     * 数量hi
     */
    @Excel(name = "数量hi")
    private String amountHi;

    public String getSlaveAddress() {
        return slaveAddress;
    }

    public void setSlaveAddress(String slaveAddress) {
        this.slaveAddress = slaveAddress;
    }

    /**
     * 数量lo
     */
    @Excel(name = "数量lo")
    private String amountLo;

    @Excel(name = "从站地址")
    private String slaveAddress;


    /**
     * 采集周期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "采集周期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date cycleTime;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setSensorId(Long sensorId) {
        this.sensorId = sensorId;
    }

    public Long getSensorId() {
        return sensorId;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setAddressHi(String addressHi) {
        this.addressHi = addressHi;
    }

    public String getAddressHi() {
        return addressHi;
    }

    public void setAddressLo(String addressLo) {
        this.addressLo = addressLo;
    }

    public String getAddressLo() {
        return addressLo;
    }

    public void setAmountHi(String amountHi) {
        this.amountHi = amountHi;
    }

    public String getAmountHi() {
        return amountHi;
    }

    public void setAmountLo(String amountLo) {
        this.amountLo = amountLo;
    }

    public String getAmountLo() {
        return amountLo;
    }

    public void setCycleTime(Date cycleTime) {
        this.cycleTime = cycleTime;
    }

    public Date getCycleTime() {
        return cycleTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("sensorId", getSensorId())
                .append("orderType", getOrderType())
                .append("addressHi", getAddressHi())
                .append("addressLo", getAddressLo())
                .append("amountHi", getAmountHi())
                .append("amountLo", getAmountLo())
                .append("cycleTime", getCycleTime())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .toString();
    }
}
