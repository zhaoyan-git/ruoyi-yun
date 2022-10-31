package com.ruoyi.cmp.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 【请填写功能名称】对象 project_alarm_config
 * 
 * @author zy
 * @date 2022-09-15
 */
public class ProjectAlarmConfig extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 所属测点 */
    @Excel(name = "所属测点")
    private Long pointId;

    private Long projectId;

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Long getStructureId() {
        return structureId;
    }

    public void setStructureId(Long structureId) {
        this.structureId = structureId;
    }

    public String getPointName() {
        return pointName;
    }

    public void setPointName(String pointName) {
        this.pointName = pointName;
    }

    private Long structureId;

    /** 报警等级 */
    @Excel(name = "报警等级")
    private String alarmLevel;

    private String pointName;

    /** 阈值 */
    @Excel(name = "阈值")
    private Double threshold;

    /** 计算方式 */
    @Excel(name = "计算方式")
    private String computeType;

    /** 报警标题 */
    @Excel(name = "报警标题")
    private String title;

    /** 报警内容 */
    @Excel(name = "报警内容")
    private String content;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setPointId(Long pointId) 
    {
        this.pointId = pointId;
    }

    public Long getPointId() 
    {
        return pointId;
    }
    public void setAlarmLevel(String alarmLevel) 
    {
        this.alarmLevel = alarmLevel;
    }

    public Double getThreshold() {
        return threshold;
    }

    public void setThreshold(Double threshold) {
        this.threshold = threshold;
    }

    public String getAlarmLevel()
    {
        return alarmLevel;
    }

    public void setComputeType(String computeType) 
    {
        this.computeType = computeType;
    }

    public String getComputeType() 
    {
        return computeType;
    }
    public void setTitle(String title) 
    {
        this.title = title;
    }

    public String getTitle() 
    {
        return title;
    }
    public void setContent(String content) 
    {
        this.content = content;
    }

    public String getContent() 
    {
        return content;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("pointId", getPointId())
            .append("alarmLevel", getAlarmLevel())
            .append("threshold", getThreshold())
            .append("computeType", getComputeType())
            .append("title", getTitle())
            .append("content", getContent())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
