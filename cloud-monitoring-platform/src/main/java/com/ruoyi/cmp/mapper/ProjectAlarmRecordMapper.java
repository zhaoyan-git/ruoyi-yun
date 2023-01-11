package com.ruoyi.cmp.mapper;

import java.util.List;
import com.ruoyi.cmp.domain.ProjectAlarmRecord;

/**
 * 【请填写功能名称】Mapper接口
 * 
 * @author zy
 * @date 2022-09-19
 */
public interface ProjectAlarmRecordMapper 
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
     * 删除【请填写功能名称】
     * 
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    public int deleteProjectAlarmRecordById(Long id);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteProjectAlarmRecordByIds(Long[] ids);

    public int updateProjectAlarmRecordIsDeleteByIds(Long[] ids);
}
