package com.ruoyi.cmp.mapper;

import com.ruoyi.cmp.domain.Structure;

import java.util.List;

/**
 * 结构物Mapper接口
 * 
 * @author zy
 * @date 2022-08-31
 */
public interface StructureMapper 
{
    /**
     * 查询结构物
     * 
     * @param id 结构物主键
     * @return 结构物
     */
    public Structure selectStructureById(Long id);

    /**
     * 查询结构物列表
     * 
     * @param structure 结构物
     * @return 结构物集合
     */
    public List<Structure> selectStructureList(Structure structure);

    /**
     * 新增结构物
     * 
     * @param structure 结构物
     * @return 结果
     */
    public int insertStructure(Structure structure);

    /**
     * 修改结构物
     * 
     * @param structure 结构物
     * @return 结果
     */
    public int updateStructure(Structure structure);

    /**
     * 删除结构物
     * 
     * @param id 结构物主键
     * @return 结果
     */
    public int deleteStructureById(Long id);

    /**
     * 批量删除结构物
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteStructureByIds(Long[] ids);
}
