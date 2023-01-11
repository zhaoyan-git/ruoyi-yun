package com.ruoyi.cmp.service.impl;

import java.util.List;

import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.cmp.mapper.ProjectAlarmRecordMapper;
import com.ruoyi.cmp.domain.ProjectAlarmRecord;
import com.ruoyi.cmp.service.IProjectAlarmRecordService;

/**
 * 告警记录Service业务层处理
 *
 * @author zy
 * @date 2022-09-19
 */
@Service
public class ProjectAlarmRecordServiceImpl implements IProjectAlarmRecordService {
    @Autowired
    private ProjectAlarmRecordMapper projectAlarmRecordMapper;

    /**
     * 查询告警记录
     *
     * @param id 告警记录主键
     * @return 告警记录
     */
    @Override
    public ProjectAlarmRecord selectProjectAlarmRecordById(Long id) {
        return projectAlarmRecordMapper.selectProjectAlarmRecordById(id);
    }

    /**
     * 查询告警记录列表
     *
     * @param projectAlarmRecord 告警记录
     * @return 告警记录
     */
    @Override
    public List<ProjectAlarmRecord> selectProjectAlarmRecordList(ProjectAlarmRecord projectAlarmRecord) {
        projectAlarmRecord.setIsDelete("0");
        return projectAlarmRecordMapper.selectProjectAlarmRecordList(projectAlarmRecord);
    }

    /**
     * 新增告警记录
     *
     * @param projectAlarmRecord 告警记录
     * @return 结果
     */
    @Override
    public int insertProjectAlarmRecord(ProjectAlarmRecord projectAlarmRecord) {
        projectAlarmRecord.setCreateTime(DateUtils.getNowDate());
        return projectAlarmRecordMapper.insertProjectAlarmRecord(projectAlarmRecord);
    }

    /**
     * 修改告警记录
     *
     * @param projectAlarmRecord 告警记录
     * @return 结果
     */
    @Override
    public int updateProjectAlarmRecord(ProjectAlarmRecord projectAlarmRecord) {
        projectAlarmRecord.setUpdateTime(DateUtils.getNowDate());
        return projectAlarmRecordMapper.updateProjectAlarmRecord(projectAlarmRecord);
    }

    /**
     * 批量删除告警记录
     *
     * @param ids 需要删除的告警记录主键
     * @return 结果
     */
    @Override
    public int deleteProjectAlarmRecordByIds(Long[] ids) {
        return projectAlarmRecordMapper.deleteProjectAlarmRecordByIds(ids);
    }

    @Override
    public int updateProjectAlarmRecordIsDeleteByIds(Long[] ids) {
        return projectAlarmRecordMapper.updateProjectAlarmRecordIsDeleteByIds(ids);
    }

    /**
     * 删除告警记录信息
     *
     * @param id 告警记录主键
     * @return 结果
     */
    @Override
    public int deleteProjectAlarmRecordById(Long id) {
        return projectAlarmRecordMapper.deleteProjectAlarmRecordById(id);
    }
}
