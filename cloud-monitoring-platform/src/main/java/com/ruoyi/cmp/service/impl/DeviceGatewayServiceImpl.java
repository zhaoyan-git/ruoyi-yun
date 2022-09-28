package com.ruoyi.cmp.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.cmp.mapper.DeviceGatewayMapper;
import com.ruoyi.cmp.domain.DeviceGateway;
import com.ruoyi.cmp.service.IDeviceGatewayService;

/**
 * 网关管理Service业务层处理
 * 
 * @author zy
 * @date 2022-09-15
 */
@Service
public class DeviceGatewayServiceImpl implements IDeviceGatewayService 
{
    @Autowired
    private DeviceGatewayMapper deviceGatewayMapper;

    /**
     * 查询网关管理
     * 
     * @param id 网关管理主键
     * @return 网关管理
     */
    @Override
    public DeviceGateway selectDeviceGatewayById(Long id)
    {
        return deviceGatewayMapper.selectDeviceGatewayById(id);
    }

    /**
     * 查询网关管理列表
     * 
     * @param deviceGateway 网关管理
     * @return 网关管理
     */
    @Override
    public List<DeviceGateway> selectDeviceGatewayList(DeviceGateway deviceGateway)
    {
        return deviceGatewayMapper.selectDeviceGatewayList(deviceGateway);
    }

    /**
     * 新增网关管理
     * 
     * @param deviceGateway 网关管理
     * @return 结果
     */
    @Override
    public int insertDeviceGateway(DeviceGateway deviceGateway)
    {
        deviceGateway.setCreateTime(DateUtils.getNowDate());
        return deviceGatewayMapper.insertDeviceGateway(deviceGateway);
    }

    /**
     * 修改网关管理
     * 
     * @param deviceGateway 网关管理
     * @return 结果
     */
    @Override
    public int updateDeviceGateway(DeviceGateway deviceGateway)
    {
        deviceGateway.setUpdateTime(DateUtils.getNowDate());
        return deviceGatewayMapper.updateDeviceGateway(deviceGateway);
    }

    /**
     * 批量删除网关管理
     * 
     * @param ids 需要删除的网关管理主键
     * @return 结果
     */
    @Override
    public int deleteDeviceGatewayByIds(Long[] ids)
    {
        return deviceGatewayMapper.deleteDeviceGatewayByIds(ids);
    }

    /**
     * 删除网关管理信息
     * 
     * @param id 网关管理主键
     * @return 结果
     */
    @Override
    public int deleteDeviceGatewayById(Long id)
    {
        return deviceGatewayMapper.deleteDeviceGatewayById(id);
    }
}
