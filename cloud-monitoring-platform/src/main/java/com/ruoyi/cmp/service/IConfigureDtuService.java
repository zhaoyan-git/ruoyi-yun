package com.ruoyi.cmp.service;

import java.util.List;
import com.ruoyi.cmp.domain.ConfigureDtu;

/**
 * 【请填写功能名称】Service接口
 * 
 * @author zy
 * @date 2022-09-07
 */
public interface IConfigureDtuService 
{
    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    public ConfigureDtu selectConfigureDtuById(Long id);

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param configureDtu 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<ConfigureDtu> selectConfigureDtuList(ConfigureDtu configureDtu);

    /**
     * 新增【请填写功能名称】
     * 
     * @param configureDtu 【请填写功能名称】
     * @return 结果
     */
    public int insertConfigureDtu(ConfigureDtu configureDtu);

    /**
     * 修改【请填写功能名称】
     * 
     * @param configureDtu 【请填写功能名称】
     * @return 结果
     */
    public int updateConfigureDtu(ConfigureDtu configureDtu);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的【请填写功能名称】主键集合
     * @return 结果
     */
    public int deleteConfigureDtuByIds(Long[] ids);

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    public int deleteConfigureDtuById(Long id);
}
