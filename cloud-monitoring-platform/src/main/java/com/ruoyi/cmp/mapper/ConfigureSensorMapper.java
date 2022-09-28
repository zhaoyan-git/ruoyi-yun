package com.ruoyi.cmp.mapper;

import java.util.List;
import com.ruoyi.cmp.domain.ConfigureSensor;

/**
 * 【请填写功能名称】Mapper接口
 * 
 * @author zy
 * @date 2022-09-07
 */
public interface ConfigureSensorMapper 
{
    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    public ConfigureSensor selectConfigureSensorById(Long id);

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param configureSensor 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<ConfigureSensor> selectConfigureSensorList(ConfigureSensor configureSensor);

    /**
     * 新增【请填写功能名称】
     * 
     * @param configureSensor 【请填写功能名称】
     * @return 结果
     */
    public int insertConfigureSensor(ConfigureSensor configureSensor);

    /**
     * 修改【请填写功能名称】
     * 
     * @param configureSensor 【请填写功能名称】
     * @return 结果
     */
    public int updateConfigureSensor(ConfigureSensor configureSensor);

    /**
     * 删除【请填写功能名称】
     * 
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    public int deleteConfigureSensorById(Long id);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteConfigureSensorByIds(Long[] ids);
}
