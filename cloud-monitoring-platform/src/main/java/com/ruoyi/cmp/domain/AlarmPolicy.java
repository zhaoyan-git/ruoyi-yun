package com.ruoyi.cmp.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 告警策略配置对象 alarm_policy
 * 
 * @author zy
 * @date 2022-11-24
 */
public class AlarmPolicy extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 项目id */
    @Excel(name = "项目id")
    private Long pid;

    /** 项目名称 */
    @Excel(name = "项目名称")
    private String pname;

    /** 结构物id */
    @Excel(name = "结构物id")
    private Long sid;

    /** 结构物名称 */
    @Excel(name = "结构物名称")
    private String sname;

    /** 告警接收人 */
    @Excel(name = "告警接收人")
    private String receiverperson;

    public Object getAlarmTypeArr() {
        return alarmTypeArr;
    }

    public void setAlarmTypeArr(Object alarmTypeArr) {
        this.alarmTypeArr = alarmTypeArr;
    }

    public Object getReceiverpersonArr() {
        return receiverpersonArr;
    }

    public void setReceiverpersonArr(Object receiverpersonArr) {
        this.receiverpersonArr = receiverpersonArr;
    }

    private Object alarmTypeArr;
    private Object receiverpersonArr;




    /** 接收告警类型 */
    @Excel(name = "接收告警类型")
    private String alarmtype;

    /** 接收类型 */
    @Excel(name = "接收类型")
    private String receivertype;

    /** 短信告警级别 */
    @Excel(name = "短信告警级别")
    private String notealarmlev;

    /** 邮件告警级别 */
    @Excel(name = "邮件告警级别")
    private String emailalarmlev;

    /** 微信告警级别 */
    @Excel(name = "微信告警级别")
    private String wechatalarmlev;

    /** 启用状态 */
    @Excel(name = "启用状态")
    private String status;

    /** 备用字段1 */
    @Excel(name = "备用字段1")
    private String reserve1;

    /** 备用字段2 */
    @Excel(name = "备用字段2")
    private String reserve2;

    /** 备用字段3 */
    @Excel(name = "备用字段3")
    private String reserve3;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setPid(Long pid) 
    {
        this.pid = pid;
    }

    public Long getPid() 
    {
        return pid;
    }
    public void setPname(String pname) 
    {
        this.pname = pname;
    }

    public String getPname()
    {
        return pname;
    }
    public void setSid(Long sid) 
    {
        this.sid = sid;
    }

    public Long getSid() 
    {
        return sid;
    }
    public void setSname(String sname) 
    {
        this.sname = sname;
    }

    public String getSname() 
    {
        return sname;
    }
    public void setReceiverperson(String receiverperson) 
    {
        this.receiverperson = receiverperson;
    }

    public String getReceiverperson()
    {
        return receiverperson;
    }
    public void setAlarmtype(String alarmtype)
    {
        this.alarmtype = alarmtype;
    }

    public String getAlarmtype()
    {
        return alarmtype;
    }
    public void setReceivertype(String receivertype)
    {
        this.receivertype = receivertype;
    }

    public String getReceivertype() 
    {
        return receivertype;
    }
    public void setNotealarmlev(String notealarmlev) 
    {
        this.notealarmlev = notealarmlev;
    }

    public String getNotealarmlev() 
    {
        return notealarmlev;
    }
    public void setEmailalarmlev(String emailalarmlev) 
    {
        this.emailalarmlev = emailalarmlev;
    }

    public String getEmailalarmlev() 
    {
        return emailalarmlev;
    }
    public void setWechatalarmlev(String wechatalarmlev) 
    {
        this.wechatalarmlev = wechatalarmlev;
    }

    public String getWechatalarmlev() 
    {
        return wechatalarmlev;
    }
    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }
    public void setReserve1(String reserve1) 
    {
        this.reserve1 = reserve1;
    }

    public String getReserve1() 
    {
        return reserve1;
    }
    public void setReserve2(String reserve2) 
    {
        this.reserve2 = reserve2;
    }

    public String getReserve2() 
    {
        return reserve2;
    }
    public void setReserve3(String reserve3) 
    {
        this.reserve3 = reserve3;
    }

    public String getReserve3() 
    {
        return reserve3;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("pid", getPid())
            .append("pname", getPname())
            .append("sid", getSid())
            .append("sname", getSname())
            .append("receiverperson", getReceiverperson())
            .append("alarmtype", getAlarmtype())
            .append("receivertype", getReceivertype())
            .append("notealarmlev", getNotealarmlev())
            .append("emailalarmlev", getEmailalarmlev())
            .append("wechatalarmlev", getWechatalarmlev())
            .append("status", getStatus())
            .append("reserve1", getReserve1())
            .append("reserve2", getReserve2())
            .append("reserve3", getReserve3())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
