package com.ruoyi.cmp.service;

import com.ruoyi.cmp.domain.Structure;

import java.util.List;

/**
 * 【请填写功能名称】Service接口
 * 
 * @author zy
 * @date 2022-08-31
 */
public interface IStructureService 
{
    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    public Structure selectStructureById(Long id);

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param structure 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<Structure> selectStructureList(Structure structure);

    /**
     * 新增【请填写功能名称】
     * 
     * @param structure 【请填写功能名称】
     * @return 结果
     */
    public int insertStructure(Structure structure);

    public int insertMF(Structure structure);

    /**
     * 修改【请填写功能名称】
     * 
     * @param structure 【请填写功能名称】
     * @return 结果
     */
    public int updateStructure(Structure structure);

    public int updateStructureForEquipment(Structure structure);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的【请填写功能名称】主键集合
     * @return 结果
     */
    public int deleteStructureByIds(Long[] ids);

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    public int deleteStructureById(Long id);

    public Structure getMFInfo(Long id);
}
