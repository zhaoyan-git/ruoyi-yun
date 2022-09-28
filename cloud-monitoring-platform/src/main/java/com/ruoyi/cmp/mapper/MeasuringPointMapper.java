package com.ruoyi.cmp.mapper;

import java.util.List;
import com.ruoyi.cmp.domain.MeasuringPoint;

/**
 * 【请填写功能名称】Mapper接口
 * 
 * @author zy
 * @date 2022-09-09
 */
public interface MeasuringPointMapper 
{
    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    public MeasuringPoint selectMeasuringPointById(Long id);

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param measuringPoint 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<MeasuringPoint> selectMeasuringPointList(MeasuringPoint measuringPoint);

    /**
     * 新增【请填写功能名称】
     * 
     * @param measuringPoint 【请填写功能名称】
     * @return 结果
     */
    public int insertMeasuringPoint(MeasuringPoint measuringPoint);

    /**
     * 修改【请填写功能名称】
     * 
     * @param measuringPoint 【请填写功能名称】
     * @return 结果
     */
    public int updateMeasuringPoint(MeasuringPoint measuringPoint);

    /**
     * 删除【请填写功能名称】
     * 
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    public int deleteMeasuringPointById(Long id);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteMeasuringPointByIds(Long[] ids);
}
