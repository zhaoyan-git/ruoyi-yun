package com.ruoyi.cmp.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 【请填写功能名称】对象 configure_dtu
 *
 * @author zy
 * @date 2022-09-07
 */
public class ConfigureDtu extends BaseEntity {
    private static final long serialVersionUID = 1L;

    private Long id;
    /**
     * 厂商
     */
    @Excel(name = "厂商")
    private Long manufacturer;
    /**
     * 产品
     */
    @Excel(name = "产品")
    private String product;
    /**
     * 型号
     */
    @Excel(name = "型号")
    private String model;
    /**
     * 名称
     */
    @Excel(name = "名称")
    private String name;

    /**
     * 所属设备ID
     */
    @Excel(name = "结构物Id")
    private Long businessId;

    /**
     * SIM卡号
     */
    @Excel(name = "SIM卡号")
    private String sim;

    /**
     * 接口ID
     */
    @Excel(name = "接口ID")
    private String dtuNo;

    public boolean isCrc() {
        return crc;
    }

    public void setCrc(boolean crc) {
        this.crc = crc;
    }

    /**
     * CRC
     */
    @Excel(name = "CRC")
    private boolean crc;

    /**
     * 设备ID号
     */
    @Excel(name = "设备ID号")
    private Long equipmentId;

    public Long getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(Long equipmentId) {
        this.equipmentId = equipmentId;
    }

    /**
     * 单包大小(Byte)
     */
    @Excel(name = "单包大小(Byte)")
    private String pageSize;

    /**
     * 发送延时(ms)
     */
    @Excel(name = "发送延时(ms)")
    private String transmissionDelay;

    /**
     * 同步接收包组时间
     */
    @Excel(name = "同步接收包组时间")
    private String receivedPacket;

    public boolean isGroupPackage() {
        return groupPackage;
    }

    public void setGroupPackage(boolean groupPackage) {
        this.groupPackage = groupPackage;
    }

    /**
     * 码流日志
     */
    @Excel(name = "码流日志")
    private boolean streamLog;
    @Excel(name = "组包")
    private boolean groupPackage;

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    private String deptName;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setManufacturer(Long manufacturer) {
        this.manufacturer = manufacturer;
    }

    public Long getManufacturer() {
        return manufacturer;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getProduct() {
        return product;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getModel() {
        return model;
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

    public void setSim(String sim) {
        this.sim = sim;
    }

    public String getSim() {
        return sim;
    }

    public void setDtuNo(String dtuNo) {
        this.dtuNo = dtuNo;
    }

    public String getDtuNo() {
        return dtuNo;
    }



    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }

    public String getPageSize() {
        return pageSize;
    }

    public void setTransmissionDelay(String transmissionDelay) {
        this.transmissionDelay = transmissionDelay;
    }

    public String getTransmissionDelay() {
        return transmissionDelay;
    }

    public void setReceivedPacket(String receivedPacket) {
        this.receivedPacket = receivedPacket;
    }

    public String getReceivedPacket() {
        return receivedPacket;
    }


    public boolean isStreamLog() {
        return streamLog;
    }

    public void setStreamLog(boolean streamLog) {
        this.streamLog = streamLog;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("manufacturer", getManufacturer())
                .append("product", getProduct())
                .append("model", getModel())
                .append("name", getName())
                .append("businessId", getBusinessId())
                .append("sim", getSim())
                .append("dtuNo", getDtuNo())
                .append("equipmentId", getEquipmentId())
                .append("pageSize", getPageSize())
                .append("transmissionDelay", getTransmissionDelay())
                .append("receivedPacket", getReceivedPacket())
                .append("remark", getRemark())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .toString();
    }
}
