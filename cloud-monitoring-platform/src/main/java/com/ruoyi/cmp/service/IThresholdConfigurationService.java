package com.ruoyi.cmp.service;

import java.util.List;

import com.ruoyi.cmp.domain.ThresholdConfiguration;

/**
 * 阈值配置Service接口
 *
 * @author zy
 * @date 2022-11-23
 */
public interface IThresholdConfigurationService {
    /**
     * 查询阈值配置
     *
     * @param id 阈值配置主键
     * @return 阈值配置
     */
    public ThresholdConfiguration selectThresholdConfigurationById(Long id);

    /**
     * 查询阈值配置列表
     *
     * @param thresholdConfiguration 阈值配置
     * @return 阈值配置集合
     */
    public List<ThresholdConfiguration> selectThresholdConfigurationList(ThresholdConfiguration thresholdConfiguration);

    /**
     * 新增阈值配置
     *
     * @param thresholdConfiguration 阈值配置
     * @return 结果
     */
    public int insertThresholdConfiguration(ThresholdConfiguration thresholdConfiguration);

    /**
     * 修改阈值配置
     *
     * @param thresholdConfiguration 阈值配置
     * @return 结果
     */
    public int updateThresholdConfiguration(ThresholdConfiguration thresholdConfiguration);

    /**
     * 批量删除阈值配置
     *
     * @param ids 需要删除的阈值配置主键集合
     * @return 结果
     */
    public int deleteThresholdConfigurationByIds(Long[] ids);

    /**
     * 删除阈值配置信息
     *
     * @param id 阈值配置主键
     * @return 结果
     */
    public int deleteThresholdConfigurationById(Long id);
}
