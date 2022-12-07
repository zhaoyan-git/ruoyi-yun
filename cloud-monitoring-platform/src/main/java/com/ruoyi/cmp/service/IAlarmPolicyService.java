package com.ruoyi.cmp.service;

import java.util.List;
import com.ruoyi.cmp.domain.AlarmPolicy;

/**
 * 告警策略配置Service接口
 * 
 * @author zy
 * @date 2022-11-24
 */
public interface IAlarmPolicyService 
{
    /**
     * 查询告警策略配置
     * 
     * @param id 告警策略配置主键
     * @return 告警策略配置
     */
    public AlarmPolicy selectAlarmPolicyById(Long id);

    /**
     * 查询告警策略配置列表
     * 
     * @param alarmPolicy 告警策略配置
     * @return 告警策略配置集合
     */
    public List<AlarmPolicy> selectAlarmPolicyList(AlarmPolicy alarmPolicy);

    /**
     * 新增告警策略配置
     * 
     * @param alarmPolicy 告警策略配置
     * @return 结果
     */
    public int insertAlarmPolicy(AlarmPolicy alarmPolicy);

    /**
     * 修改告警策略配置
     * 
     * @param alarmPolicy 告警策略配置
     * @return 结果
     */
    public int updateAlarmPolicy(AlarmPolicy alarmPolicy);

    public int updateStatus(AlarmPolicy alarmPolicy);

    /**
     * 批量删除告警策略配置
     * 
     * @param ids 需要删除的告警策略配置主键集合
     * @return 结果
     */
    public int deleteAlarmPolicyByIds(Long[] ids);

    /**
     * 删除告警策略配置信息
     * 
     * @param id 告警策略配置主键
     * @return 结果
     */
    public int deleteAlarmPolicyById(Long id);
}
