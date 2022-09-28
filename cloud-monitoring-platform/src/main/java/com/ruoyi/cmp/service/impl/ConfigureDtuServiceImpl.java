package com.ruoyi.cmp.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.cmp.mapper.ConfigureDtuMapper;
import com.ruoyi.cmp.domain.ConfigureDtu;
import com.ruoyi.cmp.service.IConfigureDtuService;

/**
 * 【请填写功能名称】Service业务层处理
 * 
 * @author zy
 * @date 2022-09-07
 */
@Service
public class ConfigureDtuServiceImpl implements IConfigureDtuService 
{
    @Autowired
    private ConfigureDtuMapper configureDtuMapper;

    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    @Override
    public ConfigureDtu selectConfigureDtuById(Long id)
    {
        return configureDtuMapper.selectConfigureDtuById(id);
    }

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param configureDtu 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<ConfigureDtu> selectConfigureDtuList(ConfigureDtu configureDtu)
    {
        return configureDtuMapper.selectConfigureDtuList(configureDtu);
    }

    /**
     * 新增【请填写功能名称】
     * 
     * @param configureDtu 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertConfigureDtu(ConfigureDtu configureDtu)
    {
        configureDtu.setCreateTime(DateUtils.getNowDate());
        return configureDtuMapper.insertConfigureDtu(configureDtu);
    }

    /**
     * 修改【请填写功能名称】
     * 
     * @param configureDtu 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateConfigureDtu(ConfigureDtu configureDtu)
    {
        configureDtu.setUpdateTime(DateUtils.getNowDate());
        return configureDtuMapper.updateConfigureDtu(configureDtu);
    }

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteConfigureDtuByIds(Long[] ids)
    {
        return configureDtuMapper.deleteConfigureDtuByIds(ids);
    }

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteConfigureDtuById(Long id)
    {
        return configureDtuMapper.deleteConfigureDtuById(id);
    }
}
