package com.ruoyi.cmp.service.impl;

import java.util.List;

import com.ruoyi.cmp.domain.ConfigureCollector;
import com.ruoyi.cmp.mapper.ConfigureCollectorMapper;
import com.ruoyi.cmp.service.IConfigureCollectorService;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 【请填写功能名称】Service业务层处理
 *
 * @author zy
 * @date 2022-09-06
 */
@Service
public class ConfigureCollectorServiceImpl implements IConfigureCollectorService {
    @Autowired
    private ConfigureCollectorMapper configureCollectorMapper;

    /**
     * 查询【请填写功能名称】
     *
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    @Override
    public ConfigureCollector selectConfigureCollectorById(Long id) {
        return configureCollectorMapper.selectConfigureCollectorById(id);
    }

    /**
     * 查询【请填写功能名称】列表
     *
     * @param configureCollector 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<ConfigureCollector> selectConfigureCollectorList(ConfigureCollector configureCollector) {
        return configureCollectorMapper.selectConfigureCollectorList(configureCollector);
    }

    /**
     * 新增【请填写功能名称】
     *
     * @param configureCollector 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertConfigureCollector(ConfigureCollector configureCollector) {
        configureCollector.setCreateTime(DateUtils.getNowDate());
        return configureCollectorMapper.insertConfigureCollector(configureCollector);
    }

    /**
     * 修改【请填写功能名称】
     *
     * @param configureCollector 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateConfigureCollector(ConfigureCollector configureCollector) {
        configureCollector.setUpdateTime(DateUtils.getNowDate());
        return configureCollectorMapper.updateConfigureCollector(configureCollector);
    }

    /**
     * 批量删除【请填写功能名称】
     *
     * @param ids 需要删除的【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteConfigureCollectorByIds(Long[] ids) {
        return configureCollectorMapper.deleteConfigureCollectorByIds(ids);
    }

    /**
     * 删除【请填写功能名称】信息
     *
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteConfigureCollectorById(Long id) {
        return configureCollectorMapper.deleteConfigureCollectorById(id);
    }
}
