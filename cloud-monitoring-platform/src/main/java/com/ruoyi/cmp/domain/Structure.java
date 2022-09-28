package com.ruoyi.cmp.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

import java.util.List;

/**
 * 【请填写功能名称】对象 structure
 * 
 * @author zy
 * @date 2022-08-31
 */
public class Structure extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 名称 */
    @Excel(name = "名称")
    private String name;

    /** 结构物类型 */
    @Excel(name = "结构物类型")
    private Long typeId;

    @Excel(name = "结构物类型")
    private Long monitoringFactors;

    /** 图片 */
    @Excel(name = "图片")
    private String photoFile;

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    /** 所属项目 */
    @Excel(name = "所属项目")
    private Long projectId;

    /** 警报标志（0表示未告警 1表示告警） */
    @Excel(name = "警报标志", readConverterExp = "0=表示未告警,1=表示告警")
    private String alarmFlag;
    //项目名称
    private String projectName;
    //精度
    private String positionLat;

    public Object getMf() {
        return mf;
    }

    public void setMf(Object mf) {
        this.mf = mf;
    }

    //纬度
    private String positionLng;
    //地址
    private String location;

    private Object mf;

    public List<Equipment> getEquipmentList() {
        return equipmentList;
    }

    public void setEquipmentList(List<Equipment> equipmentList) {
        this.equipmentList = equipmentList;
    }

    //监测目的
    private String monitoringPurpose;

    private List<Equipment> equipmentList;


    public String getPositionLat() {
        return positionLat;
    }

    public void setPositionLat(String positionLat) {
        this.positionLat = positionLat;
    }

    public String getPositionLng() {
        return positionLng;
    }

    public void setPositionLng(String positionLng) {
        this.positionLng = positionLng;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getMonitoringPurpose() {
        return monitoringPurpose;
    }

    public void setMonitoringPurpose(String monitoringPurpose) {
        this.monitoringPurpose = monitoringPurpose;
    }

    public String getMonitoringBasis() {
        return monitoringBasis;
    }

    public void setMonitoringBasis(String monitoringBasis) {
        this.monitoringBasis = monitoringBasis;
    }

    public String getDedc() {
        return dedc;
    }

    public void setDedc(String dedc) {
        this.dedc = dedc;
    }

    public String getConstructionUnit() {
        return constructionUnit;
    }

    public void setConstructionUnit(String constructionUnit) {
        this.constructionUnit = constructionUnit;
    }

    public String getConstructionPserson() {
        return constructionPserson;
    }

    public void setConstructionPserson(String constructionPserson) {
        this.constructionPserson = constructionPserson;
    }

    public String getConstructionPhone() {
        return constructionPhone;
    }

    public void setConstructionPhone(String constructionPhone) {
        this.constructionPhone = constructionPhone;
    }

    public String getManageUnit() {
        return manageUnit;
    }

    public void setManageUnit(String manageUnit) {
        this.manageUnit = manageUnit;
    }

    public String getManagePerson() {
        return managePerson;
    }

    public void setManagePerson(String managePerson) {
        this.managePerson = managePerson;
    }

    public String getManagePhone() {
        return managePhone;
    }

    public void setManagePhone(String managePhone) {
        this.managePhone = managePhone;
    }

    //监测依据
    private String monitoringBasis;
    //描述
    private String dedc;
    //施工单位
    private String constructionUnit;
    //施工单位联系人
    private String constructionPserson;
    //施工单位联系电话
    private String constructionPhone;
    //监理单位
    private String manageUnit;
    //监理单位联系人
    private String managePerson;
    //监理单位联系电话
    private String managePhone;

    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;

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
    public void setTypeId(Long typeId) 
    {
        this.typeId = typeId;
    }

    public Long getTypeId() 
    {
        return typeId;
    }
    public void setPhotoFile(String photoFile) 
    {
        this.photoFile = photoFile;
    }

    public String getPhotoFile() 
    {
        return photoFile;
    }
    public void setProjectId(Long projectId) 
    {
        this.projectId = projectId;
    }

    public Long getProjectId() 
    {
        return projectId;
    }
    public void setAlarmFlag(String alarmFlag) 
    {
        this.alarmFlag = alarmFlag;
    }

    public String getAlarmFlag() 
    {
        return alarmFlag;
    }
    public void setDelFlag(String delFlag) 
    {
        this.delFlag = delFlag;
    }

    public String getDelFlag() 
    {
        return delFlag;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("name", getName())
            .append("typeId", getTypeId())
            .append("photoFile", getPhotoFile())
            .append("projectId", getProjectId())
            .append("alarmFlag", getAlarmFlag())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
