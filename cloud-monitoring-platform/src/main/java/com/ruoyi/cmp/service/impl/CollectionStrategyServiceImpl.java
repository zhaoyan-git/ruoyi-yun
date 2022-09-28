package com.ruoyi.cmp.service.impl;

import java.util.List;

import com.ruoyi.cmp.domain.CollectionStrategy;
import com.ruoyi.cmp.mapper.CollectionStrategyMapper;
import com.ruoyi.cmp.service.ICollectionStrategyService;
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
public class CollectionStrategyServiceImpl implements ICollectionStrategyService {
    @Autowired
    private CollectionStrategyMapper collectionStrategyMapper;

    /**
     * 查询【请填写功能名称】
     *
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    @Override
    public CollectionStrategy selectCollectionStrategyById(Long id) {
        return collectionStrategyMapper.selectCollectionStrategyById(id);
    }

    /**
     * 查询【请填写功能名称】列表
     *
     * @param collectionStrategy 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<CollectionStrategy> selectCollectionStrategyList(CollectionStrategy collectionStrategy) {
        return collectionStrategyMapper.selectCollectionStrategyList(collectionStrategy);
    }

    /**
     * 新增【请填写功能名称】
     *
     * @param collectionStrategy 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertCollectionStrategy(CollectionStrategy collectionStrategy) {
        collectionStrategy.setCreateTime(DateUtils.getNowDate());
        return collectionStrategyMapper.insertCollectionStrategy(collectionStrategy);
    }

    /**
     * 修改【请填写功能名称】
     *
     * @param collectionStrategy 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateCollectionStrategy(CollectionStrategy collectionStrategy) {
        collectionStrategy.setUpdateTime(DateUtils.getNowDate());
        return collectionStrategyMapper.updateCollectionStrategy(collectionStrategy);
    }

    /**
     * 批量删除【请填写功能名称】
     *
     * @param ids 需要删除的【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteCollectionStrategyByIds(Long[] ids) {
        return collectionStrategyMapper.deleteCollectionStrategyByIds(ids);
    }

    /**
     * 删除【请填写功能名称】信息
     *
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteCollectionStrategyById(Long id) {
        return collectionStrategyMapper.deleteCollectionStrategyById(id);
    }
}
