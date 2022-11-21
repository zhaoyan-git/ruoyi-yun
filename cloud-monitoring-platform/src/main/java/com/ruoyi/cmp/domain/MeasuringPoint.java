package com.ruoyi.cmp.domain;

import com.ruoyi.common.core.domain.TreeSelect;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

import java.util.ArrayList;

/**
 * 【请填写功能名称】对象 measuring_point
 * 
 * @author zy
 * @date 2022-09-09
 */
public class MeasuringPoint extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 测点名称 */
    @Excel(name = "测点名称")
    private String name;

    /** 监测因素 */
    @Excel(name = "监测因素")
    private String mfId;

    /** 数据来源方式 */
    @Excel(name = "数据来源方式")
    private Long dataSourceTypeId;

    /** 设备 */
    @Excel(name = "设备")
    private String equipmentId;

    public Object getEquipmentIdArr() {
        return equipmentIdArr;
    }

    public void setEquipmentIdArr(Object equipmentIdArr) {
        this.equipmentIdArr = equipmentIdArr;
    }

    private Object equipmentIdArr;

    public String getEquipmentName() {
        return equipmentName;
    }

    public void setEquipmentName(String equipmentName) {
        this.equipmentName = equipmentName;
    }

    public String getMfName() {
        return mfName;
    }

    public void setMfName(String mfName) {
        this.mfName = mfName;
    }

    /** 分组名称 */
    @Excel(name = "分组名称")
    private String groupName;

    private String equipmentName;

    private String mfName;

    /** 结构物ID */
    @Excel(name = "结构物ID")
    private Long structureId;

    /** 级别 */
    @Excel(name = "级别")
    private Long lev;


    public Long getNodeId() {
        return nodeId;
    }

    public void setNodeId(Long nodeId) {
        this.nodeId = nodeId;
    }

    /** 父级ID */
    @Excel(name = "父级ID")
    private Long parentId;

    private Long nodeId;

    private Object nodeInfo;

    public Object getNodeInfo() {
        return nodeInfo;
    }

    public void setNodeInfo(Object nodeInfo) {
        this.nodeInfo = nodeInfo;
    }

    private ArrayList<TreeSelect> alTreeSelect;

    public ArrayList<TreeSelect> getTreeSelect() {
        return alTreeSelect;
    }

    public void setTreeSelect(ArrayList<TreeSelect> treeSelect) {
        this.alTreeSelect = treeSelect;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }

    public String getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(String equipmentId) {
        this.equipmentId = equipmentId;
    }

    public void setMfId(String mfId)
    {
        this.mfId = mfId;
    }

    public String getMfId() 
    {
        return mfId;
    }
    public void setDataSourceTypeId(Long dataSourceTypeId) 
    {
        this.dataSourceTypeId = dataSourceTypeId;
    }

    public Long getDataSourceTypeId() 
    {
        return dataSourceTypeId;
    }

    public void setGroupName(String groupName) 
    {
        this.groupName = groupName;
    }

    public String getGroupName() 
    {
        return groupName;
    }
    public void setStructureId(Long structureId) 
    {
        this.structureId = structureId;
    }

    public Long getStructureId() 
    {
        return structureId;
    }
    public void setLev(Long lev) 
    {
        this.lev = lev;
    }

    public Long getLev() 
    {
        return lev;
    }
    public void setParentId(Long parentId) 
    {
        this.parentId = parentId;
    }

    public Long getParentId() 
    {
        return parentId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("name", getName())
            .append("mfId", getMfId())
            .append("dataSourceTypeId", getDataSourceTypeId())
            .append("equipmentId", getEquipmentId())
            .append("groupName", getGroupName())
            .append("structureId", getStructureId())
            .append("lev", getLev())
            .append("parentId", getParentId())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
