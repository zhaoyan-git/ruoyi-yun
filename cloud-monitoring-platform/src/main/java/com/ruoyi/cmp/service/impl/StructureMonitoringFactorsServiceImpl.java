package com.ruoyi.cmp.service.impl;

import java.util.List;

import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.cmp.mapper.StructureMonitoringFactorsMapper;
import com.ruoyi.cmp.domain.StructureMonitoringFactors;
import com.ruoyi.cmp.service.IStructureMonitoringFactorsService;

/**
 * 【请填写功能名称】Service业务层处理
 *
 * @author zy
 * @date 2022-09-08
 */
@Service
public class StructureMonitoringFactorsServiceImpl implements IStructureMonitoringFactorsService {
    @Autowired
    private StructureMonitoringFactorsMapper structureMonitoringFactorsMapper;

    /**
     * 查询【请填写功能名称】
     *
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    @Override
    public StructureMonitoringFactors selectStructureMonitoringFactorsById(Long id) {
        return structureMonitoringFactorsMapper.selectStructureMonitoringFactorsById(id);
    }

    /**
     * 查询【请填写功能名称】列表
     *
     * @param structureMonitoringFactors 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<StructureMonitoringFactors> selectStructureMonitoringFactorsList(StructureMonitoringFactors structureMonitoringFactors) {
        return structureMonitoringFactorsMapper.selectStructureMonitoringFactorsList(structureMonitoringFactors);
    }

    /**
     * 新增【请填写功能名称】
     *
     * @param structureMonitoringFactors 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertStructureMonitoringFactors(StructureMonitoringFactors structureMonitoringFactors) {
        structureMonitoringFactors.setCreateTime(DateUtils.getNowDate());
        return structureMonitoringFactorsMapper.insertStructureMonitoringFactors(structureMonitoringFactors);
    }

    /**
     * 修改【请填写功能名称】
     *
     * @param structureMonitoringFactors 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateStructureMonitoringFactors(StructureMonitoringFactors structureMonitoringFactors) {
        structureMonitoringFactors.setUpdateTime(DateUtils.getNowDate());
        return structureMonitoringFactorsMapper.updateStructureMonitoringFactors(structureMonitoringFactors);
    }

    /**
     * 批量删除【请填写功能名称】
     *
     * @param ids 需要删除的【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteStructureMonitoringFactorsByIds(Long[] ids) {
        return structureMonitoringFactorsMapper.deleteStructureMonitoringFactorsByIds(ids);
    }

    /**
     * 删除【请填写功能名称】信息
     *
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteStructureMonitoringFactorsById(Long id) {
        return structureMonitoringFactorsMapper.deleteStructureMonitoringFactorsById(id);
    }
}
