package com.ruoyi.cmp.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.cmp.mapper.ProjectAlarmConfigMapper;
import com.ruoyi.cmp.domain.ProjectAlarmConfig;
import com.ruoyi.cmp.service.IProjectAlarmConfigService;

/**
 * 【请填写功能名称】Service业务层处理
 * 
 * @author zy
 * @date 2022-09-15
 */
@Service
public class ProjectAlarmConfigServiceImpl implements IProjectAlarmConfigService 
{
    @Autowired
    private ProjectAlarmConfigMapper projectAlarmConfigMapper;

    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    @Override
    public ProjectAlarmConfig selectProjectAlarmConfigById(Long id)
    {
        return projectAlarmConfigMapper.selectProjectAlarmConfigById(id);
    }

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param projectAlarmConfig 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<ProjectAlarmConfig> selectProjectAlarmConfigList(ProjectAlarmConfig projectAlarmConfig)
    {
        return projectAlarmConfigMapper.selectProjectAlarmConfigList(projectAlarmConfig);
    }

    /**
     * 新增【请填写功能名称】
     * 
     * @param projectAlarmConfig 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertProjectAlarmConfig(ProjectAlarmConfig projectAlarmConfig)
    {
        projectAlarmConfig.setCreateTime(DateUtils.getNowDate());
        return projectAlarmConfigMapper.insertProjectAlarmConfig(projectAlarmConfig);
    }

    /**
     * 修改【请填写功能名称】
     * 
     * @param projectAlarmConfig 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateProjectAlarmConfig(ProjectAlarmConfig projectAlarmConfig)
    {
        projectAlarmConfig.setUpdateTime(DateUtils.getNowDate());
        return projectAlarmConfigMapper.updateProjectAlarmConfig(projectAlarmConfig);
    }

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteProjectAlarmConfigByIds(Long[] ids)
    {
        return projectAlarmConfigMapper.deleteProjectAlarmConfigByIds(ids);
    }

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteProjectAlarmConfigById(Long id)
    {
        return projectAlarmConfigMapper.deleteProjectAlarmConfigById(id);
    }
}
