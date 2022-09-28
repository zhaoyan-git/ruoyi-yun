package com.ruoyi.cmp.mapper;

import java.util.List;

import com.ruoyi.cmp.domain.StructureMonitoringFactors;

/**
 * 【请填写功能名称】Mapper接口
 *
 * @author zy
 * @date 2022-09-08
 */
public interface StructureMonitoringFactorsMapper {
    /**
     * 查询【请填写功能名称】
     *
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    public StructureMonitoringFactors selectStructureMonitoringFactorsById(Long id);

    /**
     * 查询【请填写功能名称】列表
     *
     * @param structureMonitoringFactors 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<StructureMonitoringFactors> selectStructureMonitoringFactorsList(StructureMonitoringFactors structureMonitoringFactors);

    /**
     * 新增【请填写功能名称】
     *
     * @param structureMonitoringFactors 【请填写功能名称】
     * @return 结果
     */
    public int insertStructureMonitoringFactors(StructureMonitoringFactors structureMonitoringFactors);

    /**
     * 修改【请填写功能名称】
     *
     * @param structureMonitoringFactors 【请填写功能名称】
     * @return 结果
     */
    public int updateStructureMonitoringFactors(StructureMonitoringFactors structureMonitoringFactors);

    /**
     * 删除【请填写功能名称】
     *
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    public int deleteStructureMonitoringFactorsById(Long id);

    /**
     * 批量删除【请填写功能名称】
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteStructureMonitoringFactorsByIds(Long[] ids);
}
