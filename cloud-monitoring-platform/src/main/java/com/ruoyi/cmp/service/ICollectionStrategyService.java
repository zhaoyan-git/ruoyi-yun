package com.ruoyi.cmp.service;

import com.ruoyi.cmp.domain.CollectionStrategy;

import java.util.List;

/**
 * 采集策略Service接口
 *
 * @author zy
 * @date 2022-09-06
 */
public interface ICollectionStrategyService {
    /**
     * 查询采集策略
     *
     * @param id 采集策略主键
     * @return 采集策略
     */
    public CollectionStrategy selectCollectionStrategyById(Long id);

    /**
     * 查询采集策略列表
     *
     * @param collectionStrategy 采集策略
     * @return 采集策略集合
     */
    public List<CollectionStrategy> selectCollectionStrategyList(CollectionStrategy collectionStrategy);

    /**
     * 新增采集策略
     *
     * @param collectionStrategy 采集策略
     * @return 结果
     */
    public int insertCollectionStrategy(CollectionStrategy collectionStrategy);

    /**
     * 修改采集策略
     *
     * @param collectionStrategy 采集策略
     * @return 结果
     */
    public int updateCollectionStrategy(CollectionStrategy collectionStrategy);

    /**
     * 批量删除采集策略
     *
     * @param ids 需要删除的采集策略主键集合
     * @return 结果
     */
    public int deleteCollectionStrategyByIds(Long[] ids);

    /**
     * 删除采集策略信息
     *
     * @param id 采集策略主键
     * @return 结果
     */
    public int deleteCollectionStrategyById(Long id);
}
