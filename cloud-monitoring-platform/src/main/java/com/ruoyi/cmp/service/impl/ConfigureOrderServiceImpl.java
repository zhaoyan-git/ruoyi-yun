package com.ruoyi.cmp.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.cmp.mapper.ConfigureOrderMapper;
import com.ruoyi.cmp.domain.ConfigureOrder;
import com.ruoyi.cmp.service.IConfigureOrderService;

/**
 * 指令信息配置Service业务层处理
 * 
 * @author xjs
 * @date 2022-11-26
 */
@Service
public class ConfigureOrderServiceImpl implements IConfigureOrderService 
{
    @Autowired
    private ConfigureOrderMapper configureOrderMapper;

    /**
     * 查询指令信息配置
     * 
     * @param id 指令信息配置主键
     * @return 指令信息配置
     */
    @Override
    public ConfigureOrder selectConfigureOrderById(Long id)
    {
        return configureOrderMapper.selectConfigureOrderById(id);
    }

    /**
     * 查询指令信息配置列表
     * 
     * @param configureOrder 指令信息配置
     * @return 指令信息配置
     */
    @Override
    public List<ConfigureOrder> selectConfigureOrderList(ConfigureOrder configureOrder)
    {
        return configureOrderMapper.selectConfigureOrderList(configureOrder);
    }

    /**
     * 新增指令信息配置
     * 
     * @param configureOrder 指令信息配置
     * @return 结果
     */
    @Override
    public int insertConfigureOrder(ConfigureOrder configureOrder)
    {
        configureOrder.setCreateTime(DateUtils.getNowDate());
        return configureOrderMapper.insertConfigureOrder(configureOrder);
    }

    /**
     * 修改指令信息配置
     * 
     * @param configureOrder 指令信息配置
     * @return 结果
     */
    @Override
    public int updateConfigureOrder(ConfigureOrder configureOrder)
    {
        configureOrder.setUpdateTime(DateUtils.getNowDate());
        return configureOrderMapper.updateConfigureOrder(configureOrder);
    }

    /**
     * 批量删除指令信息配置
     * 
     * @param ids 需要删除的指令信息配置主键
     * @return 结果
     */
    @Override
    public int deleteConfigureOrderByIds(Long[] ids)
    {
        return configureOrderMapper.deleteConfigureOrderByIds(ids);
    }

    /**
     * 删除指令信息配置信息
     * 
     * @param id 指令信息配置主键
     * @return 结果
     */
    @Override
    public int deleteConfigureOrderById(Long id)
    {
        return configureOrderMapper.deleteConfigureOrderById(id);
    }
}
