package com.ruoyi.cmp.service;

import java.util.List;
import com.ruoyi.cmp.domain.ConfigureOrder;

/**
 * 指令信息配置Service接口
 * 
 * @author xjs
 * @date 2022-11-26
 */
public interface IConfigureOrderService 
{
    /**
     * 查询指令信息配置
     * 
     * @param id 指令信息配置主键
     * @return 指令信息配置
     */
    public ConfigureOrder selectConfigureOrderById(Long id);

    /**
     * 查询指令信息配置列表
     * 
     * @param configureOrder 指令信息配置
     * @return 指令信息配置集合
     */
    public List<ConfigureOrder> selectConfigureOrderList(ConfigureOrder configureOrder);

    /**
     * 新增指令信息配置
     * 
     * @param configureOrder 指令信息配置
     * @return 结果
     */
    public int insertConfigureOrder(ConfigureOrder configureOrder);

    /**
     * 修改指令信息配置
     * 
     * @param configureOrder 指令信息配置
     * @return 结果
     */
    public int updateConfigureOrder(ConfigureOrder configureOrder);

    /**
     * 批量删除指令信息配置
     * 
     * @param ids 需要删除的指令信息配置主键集合
     * @return 结果
     */
    public int deleteConfigureOrderByIds(Long[] ids);

    /**
     * 删除指令信息配置信息
     * 
     * @param id 指令信息配置主键
     * @return 结果
     */
    public int deleteConfigureOrderById(Long id);
}
