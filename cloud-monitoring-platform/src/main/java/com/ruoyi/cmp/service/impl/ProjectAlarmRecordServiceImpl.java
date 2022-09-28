package com.ruoyi.cmp.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.cmp.mapper.ProjectAlarmRecordMapper;
import com.ruoyi.cmp.domain.ProjectAlarmRecord;
import com.ruoyi.cmp.service.IProjectAlarmRecordService;

/**
 * 【请填写功能名称】Service业务层处理
 * 
 * @author zy
 * @date 2022-09-19
 */
@Service
public class ProjectAlarmRecordServiceImpl implements IProjectAlarmRecordService 
{
    @Autowired
    private ProjectAlarmRecordMapper projectAlarmRecordMapper;

    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    @Override
    public ProjectAlarmRecord selectProjectAlarmRecordById(Long id)
    {
        return projectAlarmRecordMapper.selectProjectAlarmRecordById(id);
    }

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param projectAlarmRecord 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<ProjectAlarmRecord> selectProjectAlarmRecordList(ProjectAlarmRecord projectAlarmRecord)
    {
        return projectAlarmRecordMapper.selectProjectAlarmRecordList(projectAlarmRecord);
    }

    /**
     * 新增【请填写功能名称】
     * 
     * @param projectAlarmRecord 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertProjectAlarmRecord(ProjectAlarmRecord projectAlarmRecord)
    {
        projectAlarmRecord.setCreateTime(DateUtils.getNowDate());
        return projectAlarmRecordMapper.insertProjectAlarmRecord(projectAlarmRecord);
    }

    /**
     * 修改【请填写功能名称】
     * 
     * @param projectAlarmRecord 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateProjectAlarmRecord(ProjectAlarmRecord projectAlarmRecord)
    {
        projectAlarmRecord.setUpdateTime(DateUtils.getNowDate());
        return projectAlarmRecordMapper.updateProjectAlarmRecord(projectAlarmRecord);
    }

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteProjectAlarmRecordByIds(Long[] ids)
    {
        return projectAlarmRecordMapper.deleteProjectAlarmRecordByIds(ids);
    }

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteProjectAlarmRecordById(Long id)
    {
        return projectAlarmRecordMapper.deleteProjectAlarmRecordById(id);
    }
}
