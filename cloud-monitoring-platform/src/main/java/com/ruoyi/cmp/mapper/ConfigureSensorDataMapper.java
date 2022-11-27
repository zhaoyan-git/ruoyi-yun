package com.ruoyi.cmp.mapper;

import java.util.List;
import com.ruoyi.cmp.domain.ConfigureSensorData;

/**
 * 传感器数据Mapper接口
 * 
 * @author xjs
 * @date 2022-11-26
 */
public interface ConfigureSensorDataMapper 
{
    /**
     * 查询传感器数据
     * 
     * @param id 传感器数据主键
     * @return 传感器数据
     */
    public ConfigureSensorData selectConfigureSensorDataById(Long id);

    /**
     * 查询传感器数据列表
     * 
     * @param configureSensorData 传感器数据
     * @return 传感器数据集合
     */
    public List<ConfigureSensorData> selectConfigureSensorDataList(ConfigureSensorData configureSensorData);

    /**
     * 新增传感器数据
     * 
     * @param configureSensorData 传感器数据
     * @return 结果
     */
    public int insertConfigureSensorData(ConfigureSensorData configureSensorData);

    /**
     * 修改传感器数据
     * 
     * @param configureSensorData 传感器数据
     * @return 结果
     */
    public int updateConfigureSensorData(ConfigureSensorData configureSensorData);

    /**
     * 删除传感器数据
     * 
     * @param id 传感器数据主键
     * @return 结果
     */
    public int deleteConfigureSensorDataById(Long id);

    /**
     * 批量删除传感器数据
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteConfigureSensorDataByIds(Long[] ids);
}
