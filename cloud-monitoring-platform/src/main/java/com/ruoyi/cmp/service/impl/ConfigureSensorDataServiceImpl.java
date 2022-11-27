package com.ruoyi.cmp.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.cmp.mapper.ConfigureSensorDataMapper;
import com.ruoyi.cmp.domain.ConfigureSensorData;
import com.ruoyi.cmp.service.IConfigureSensorDataService;

/**
 * 传感器数据Service业务层处理
 * 
 * @author xjs
 * @date 2022-11-26
 */
@Service
public class ConfigureSensorDataServiceImpl implements IConfigureSensorDataService 
{
    @Autowired
    private ConfigureSensorDataMapper configureSensorDataMapper;

    /**
     * 查询传感器数据
     * 
     * @param id 传感器数据主键
     * @return 传感器数据
     */
    @Override
    public ConfigureSensorData selectConfigureSensorDataById(Long id)
    {
        return configureSensorDataMapper.selectConfigureSensorDataById(id);
    }

    /**
     * 查询传感器数据列表
     * 
     * @param configureSensorData 传感器数据
     * @return 传感器数据
     */
    @Override
    public List<ConfigureSensorData> selectConfigureSensorDataList(ConfigureSensorData configureSensorData)
    {
        return configureSensorDataMapper.selectConfigureSensorDataList(configureSensorData);
    }

    /**
     * 新增传感器数据
     * 
     * @param configureSensorData 传感器数据
     * @return 结果
     */
    @Override
    public int insertConfigureSensorData(ConfigureSensorData configureSensorData)
    {
        configureSensorData.setCreateTime(DateUtils.getNowDate());
        return configureSensorDataMapper.insertConfigureSensorData(configureSensorData);
    }

    /**
     * 修改传感器数据
     * 
     * @param configureSensorData 传感器数据
     * @return 结果
     */
    @Override
    public int updateConfigureSensorData(ConfigureSensorData configureSensorData)
    {
        return configureSensorDataMapper.updateConfigureSensorData(configureSensorData);
    }

    /**
     * 批量删除传感器数据
     * 
     * @param ids 需要删除的传感器数据主键
     * @return 结果
     */
    @Override
    public int deleteConfigureSensorDataByIds(Long[] ids)
    {
        return configureSensorDataMapper.deleteConfigureSensorDataByIds(ids);
    }

    /**
     * 删除传感器数据信息
     * 
     * @param id 传感器数据主键
     * @return 结果
     */
    @Override
    public int deleteConfigureSensorDataById(Long id)
    {
        return configureSensorDataMapper.deleteConfigureSensorDataById(id);
    }
}
