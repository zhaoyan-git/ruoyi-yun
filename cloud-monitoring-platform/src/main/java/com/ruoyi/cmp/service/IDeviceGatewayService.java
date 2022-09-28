package com.ruoyi.cmp.service;

import java.util.List;
import com.ruoyi.cmp.domain.DeviceGateway;

/**
 * 【请填写功能名称】Service接口
 * 
 * @author zy
 * @date 2022-09-15
 */
public interface IDeviceGatewayService 
{
    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    public DeviceGateway selectDeviceGatewayById(Long id);

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param deviceGateway 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<DeviceGateway> selectDeviceGatewayList(DeviceGateway deviceGateway);

    /**
     * 新增【请填写功能名称】
     * 
     * @param deviceGateway 【请填写功能名称】
     * @return 结果
     */
    public int insertDeviceGateway(DeviceGateway deviceGateway);

    /**
     * 修改【请填写功能名称】
     * 
     * @param deviceGateway 【请填写功能名称】
     * @return 结果
     */
    public int updateDeviceGateway(DeviceGateway deviceGateway);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的【请填写功能名称】主键集合
     * @return 结果
     */
    public int deleteDeviceGatewayByIds(Long[] ids);

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    public int deleteDeviceGatewayById(Long id);
}
