package com.ruoyi.cmp.service;

import java.util.List;
import com.ruoyi.cmp.domain.MeasuringPoint;

/**
 * 【请填写功能名称】Service接口
 * 
 * @author zy
 * @date 2022-09-09
 */
public interface IMeasuringPointService 
{
    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    public MeasuringPoint selectMeasuringPointById(Long id);

    public MeasuringPoint getMeasuringPointTree(MeasuringPoint measuringPoint);

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

    public int saveNodeInfo(MeasuringPoint measuringPoint);
    public int deleteNodeInfo(MeasuringPoint measuringPoint);

    /**
     * 修改【请填写功能名称】
     * 
     * @param measuringPoint 【请填写功能名称】
     * @return 结果
     */
    public int updateMeasuringPoint(MeasuringPoint measuringPoint);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的【请填写功能名称】主键集合
     * @return 结果
     */
    public int deleteMeasuringPointByIds(Long[] ids);

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    public int deleteMeasuringPointById(Long id);
}
