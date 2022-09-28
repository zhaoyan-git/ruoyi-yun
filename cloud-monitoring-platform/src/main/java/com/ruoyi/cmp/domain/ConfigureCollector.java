package com.ruoyi.cmp.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 【请填写功能名称】对象 configure_collector
 * 
 * @author zy
 * @date 2022-09-06
 */
public class ConfigureCollector extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 厂商 */
    @Excel(name = "厂商")
    private Long deptId;

    /** 产品 */
    @Excel(name = "产品")
    private String product;

    /** 型号 */
    @Excel(name = "型号")
    private String model;

    /** 设备id */
    @Excel(name = "设备id")
    private Long equipmentId;

    /** 模块号 */
    @Excel(name = "模块号")
    private String moduleNo;

    /** 编号 */
    @Excel(name = "编号")
    private String no;

    public Long getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Long businessId) {
        this.businessId = businessId;
    }

    /** 采集策略 */
    @Excel(name = "采集策略")
    private Long csId;
    @Excel(name = "结构物Id")
    private Long businessId;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setDeptId(Long deptId) 
    {
        this.deptId = deptId;
    }

    public Long getDeptId() 
    {
        return deptId;
    }
    public void setProduct(String product) 
    {
        this.product = product;
    }

    public String getProduct() 
    {
        return product;
    }
    public void setModel(String model) 
    {
        this.model = model;
    }

    public String getModel() 
    {
        return model;
    }
    public void setEquipmentId(Long equipmentId) 
    {
        this.equipmentId = equipmentId;
    }

    public Long getEquipmentId() 
    {
        return equipmentId;
    }
    public void setModuleNo(String moduleNo) 
    {
        this.moduleNo = moduleNo;
    }

    public String getModuleNo() 
    {
        return moduleNo;
    }
    public void setNo(String no) 
    {
        this.no = no;
    }

    public String getNo() 
    {
        return no;
    }
    public void setCsId(Long csId) 
    {
        this.csId = csId;
    }

    public Long getCsId() 
    {
        return csId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("deptId", getDeptId())
            .append("product", getProduct())
            .append("model", getModel())
            .append("equipmentId", getEquipmentId())
            .append("moduleNo", getModuleNo())
            .append("no", getNo())
            .append("csId", getCsId())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
