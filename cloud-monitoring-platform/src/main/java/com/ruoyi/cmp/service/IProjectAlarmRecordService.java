package com.ruoyi.cmp.service;

import java.util.List;
import com.ruoyi.cmp.domain.ProjectAlarmRecord;

/**
 * 【请填写功能名称】Service接口
 * 
 * @author zy
 * @date 2022-09-19
 */
public interface IProjectAlarmRecordService 
{
    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    public ProjectAlarmRecord selectProjectAlarmRecordById(Long id);

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param projectAlarmRecord 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<ProjectAlarmRecord> selectProjectAlarmRecordList(ProjectAlarmRecord projectAlarmRecord);

    /**
     * 新增【请填写功能名称】
     * 
     * @param projectAlarmRecord 【请填写功能名称】
     * @return 结果
     */
    public int insertProjectAlarmRecord(ProjectAlarmRecord projectAlarmRecord);

    /**
     * 修改【请填写功能名称】
     * 
     * @param projectAlarmRecord 【请填写功能名称】
     * @return 结果
     */
    public int updateProjectAlarmRecord(ProjectAlarmRecord projectAlarmRecord);


    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的【请填写功能名称】主键集合
     * @return 结果
     */
    public int deleteProjectAlarmRecordByIds(Long[] ids);

    public int updateProjectAlarmRecordIsDeleteByIds(Long[] ids);

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    public int deleteProjectAlarmRecordById(Long id);
}
