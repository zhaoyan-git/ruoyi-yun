package com.ruoyi.cmp.mapper;

import com.ruoyi.cmp.domain.ConfigureCollector;

import java.util.List;

/**
 * 【请填写功能名称】Mapper接口
 * 
 * @author zy
 * @date 2022-09-06
 */
public interface ConfigureCollectorMapper 
{
    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    public ConfigureCollector selectConfigureCollectorById(Long id);

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param configureCollector 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<ConfigureCollector> selectConfigureCollectorList(ConfigureCollector configureCollector);

    /**
     * 新增【请填写功能名称】
     * 
     * @param configureCollector 【请填写功能名称】
     * @return 结果
     */
    public int insertConfigureCollector(ConfigureCollector configureCollector);

    /**
     * 修改【请填写功能名称】
     * 
     * @param configureCollector 【请填写功能名称】
     * @return 结果
     */
    public int updateConfigureCollector(ConfigureCollector configureCollector);

    /**
     * 删除【请填写功能名称】
     * 
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    public int deleteConfigureCollectorById(Long id);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteConfigureCollectorByIds(Long[] ids);
}
