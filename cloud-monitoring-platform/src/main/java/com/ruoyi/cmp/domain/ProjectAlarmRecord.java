package com.ruoyi.cmp.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 【请填写功能名称】对象 project_alarm_record
 * 
 * @author zy
 * @date 2022-09-19
 */
public class ProjectAlarmRecord extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;


    /** 结构物id */
    private Long sid;

    @Excel(name = "结构物名称")
    private String sname;

    /** 内容 */
    @Excel(name = "告警源")
    private String alarmSource;

    /** 所属项目 */
    @Excel(name = "等级")
    private Long lev;

    /** 是否已读 */
    @Excel(name = "告警信息")
    private String alarmInfo;

    @Excel(name = "产生次数")
    private String prodNum;

    private Date createTime_start;

    public Date getCreateTime_start() {
        return createTime_start;
    }

    public void setCreateTime_start(Date createTime_start) {
        this.createTime_start = createTime_start;
    }

    public Date getCreateTime_end() {
        return createTime_end;
    }

    public void setCreateTime_end(Date createTime_end) {
        this.createTime_end = createTime_end;
    }

    private Date createTime_end;


    public String getKeyWork() {
        return keyWork;
    }

    public void setKeyWork(String keyWork) {
        this.keyWork = keyWork;
    }

    //关键词
    private String keyWork;
    private List<String> checkedSources;

    public List<String> getCheckedSources() {
        return checkedSources;
    }

    public void setCheckedSources(List<String> checkedSources) {
        this.checkedSources = checkedSources;
    }

    public Long getSid() {
        return sid;
    }

    public void setSid(Long sid) {
        this.sid = sid;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public String getAlarmSource() {
        return alarmSource;
    }

    public void setAlarmSource(String alarmSource) {
        this.alarmSource = alarmSource;
    }

    public Long getLev() {
        return lev;
    }

    public void setLev(Long lev) {
        this.lev = lev;
    }

    public String getAlarmInfo() {
        return alarmInfo;
    }

    public void setAlarmInfo(String alarmInfo) {
        this.alarmInfo = alarmInfo;
    }

    public String getProdNum() {
        return prodNum;
    }

    public void setProdNum(String prodNum) {
        this.prodNum = prodNum;
    }

    public Date getProdDate() {
        return prodDate;
    }

    public void setProdDate(Date prodDate) {
        this.prodDate = prodDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    @Excel(name = "产生时间")
    private Date prodDate;

    @Excel(name = "更新时间")
    private Date updateDate;





    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())

            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
