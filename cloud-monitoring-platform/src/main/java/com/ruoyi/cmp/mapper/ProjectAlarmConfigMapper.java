package com.ruoyi.cmp.mapper;

import java.util.List;
import com.ruoyi.cmp.domain.ProjectAlarmConfig;

/**
 * 【请填写功能名称】Mapper接口
 * 
 * @author zy
 * @date 2022-09-15
 */
public interface ProjectAlarmConfigMapper 
{
    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    public ProjectAlarmConfig selectProjectAlarmConfigById(Long id);

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param projectAlarmConfig 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<ProjectAlarmConfig> selectProjectAlarmConfigList(ProjectAlarmConfig projectAlarmConfig);

    /**
     * 新增【请填写功能名称】
     * 
     * @param projectAlarmConfig 【请填写功能名称】
     * @return 结果
     */
    public int insertProjectAlarmConfig(ProjectAlarmConfig projectAlarmConfig);

    /**
     * 修改【请填写功能名称】
     * 
     * @param projectAlarmConfig 【请填写功能名称】
     * @return 结果
     */
    public int updateProjectAlarmConfig(ProjectAlarmConfig projectAlarmConfig);

    /**
     * 删除【请填写功能名称】
     * 
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    public int deleteProjectAlarmConfigById(Long id);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteProjectAlarmConfigByIds(Long[] ids);
}
