package com.ruoyi.cmp.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 传感器对象 configure_sensor
 * 
 * @author zy
 * @date 2022-09-07
 */
public class ConfigureSensor extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 厂商 */
    @Excel(name = "厂商")
    private Long deptId;

    public boolean isBatchadd() {
        return batchadd;
    }

    public void setBatchadd(boolean batchadd) {
        this.batchadd = batchadd;
    }


    public Long getDtuId() {
        return dtuId;
    }

    public void setDtuId(Long dtuId) {
        this.dtuId = dtuId;
    }

    @Excel(name = "结构物Id")
    private Long businessId;
    /** 设备id */
    private Long equipmentId;

    private Long dtuId;

    public Long getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(Long equipmentId) {
        this.equipmentId = equipmentId;
    }

    /** 产品 */
    @Excel(name = "产品")
    private String product;

    /** 型号 */
    @Excel(name = "型号")
    private String model;
    private String deptName;

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    /** 模块号 */
    @Excel(name = "模块号")
    private String moduleNo;

    /** 通道号 */
    @Excel(name = "通道号")
    private String channelNo;

    /** 编号 */
    @Excel(name = "编号")
    private String no;

    /** 公式 */
    @Excel(name = "公式")
    private String formula;

    /** 表达式 */
    @Excel(name = "表达式")
    private String expression;

    /** 批量添加 */
    @Excel(name = "批量添加")
    private boolean batchadd;

    /** 模号号递增,通道号递增 */
    @Excel(name = "模号号递增,通道号递增")
    private String batchaddvalue;

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
    public void setBusinessId(Long businessId) 
    {
        this.businessId = businessId;
    }

    public Long getBusinessId() 
    {
        return businessId;
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
    public void setModuleNo(String moduleNo) 
    {
        this.moduleNo = moduleNo;
    }

    public String getModuleNo() 
    {
        return moduleNo;
    }

    public String getBatchaddvalue() {
        return batchaddvalue;
    }

    public void setBatchaddvalue(String batchaddvalue) {
        this.batchaddvalue = batchaddvalue;
    }

    public void setChannelNo(String channelNo)
    {
        this.channelNo = channelNo;
    }

    public String getChannelNo() 
    {
        return channelNo;
    }
    public void setNo(String no) 
    {
        this.no = no;
    }

    public String getNo() 
    {
        return no;
    }
    public void setFormula(String formula) 
    {
        this.formula = formula;
    }

    public String getFormula() 
    {
        return formula;
    }
    public void setExpression(String expression) 
    {
        this.expression = expression;
    }

    public String getExpression() 
    {
        return expression;
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("deptId", getDeptId())
            .append("businessId", getBusinessId())
            .append("product", getProduct())
            .append("model", getModel())
            .append("moduleNo", getModuleNo())
            .append("channelNo", getChannelNo())
            .append("no", getNo())
            .append("formula", getFormula())
            .append("expression", getExpression())

            .append("batchaddvalue", getBatchaddvalue())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
