package com.ruoyi.cmp.service.impl;

import java.util.*;
import java.util.stream.Collectors;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.cmp.domain.StructureMonitoringFactors;
import com.ruoyi.cmp.mapper.StructureMonitoringFactorsMapper;
import com.ruoyi.common.core.domain.TreeSelect;
import com.ruoyi.common.core.domain.entity.SysDictData;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.mapper.SysDictDataMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.cmp.mapper.MeasuringPointMapper;
import com.ruoyi.cmp.domain.MeasuringPoint;
import com.ruoyi.cmp.service.IMeasuringPointService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 测点Service业务层处理
 *
 * @author zy
 * @date 2022-09-09
 */
@Service
public class MeasuringPointServiceImpl implements IMeasuringPointService {
    @Autowired
    private MeasuringPointMapper measuringPointMapper;
    @Autowired
    private StructureMonitoringFactorsMapper structureMonitoringFactorsMapper;
    @Autowired
    private SysDictDataMapper sysDictDataMapper;

    /**
     * 查询测点
     *
     * @param id 测点主键
     * @return 测点
     */
    @Transactional(rollbackFor = Exception.class)
    public MeasuringPoint selectMeasuringPointById(Long id) {
        MeasuringPoint measuringPoint = measuringPointMapper.selectMeasuringPointById(id);
        if (measuringPoint != null) {
            String mfId = this.getMfId(measuringPoint);
            measuringPoint.setMfId(mfId);
        }
        return measuringPoint;
    }

    private String getMfId(MeasuringPoint measuringPoint) {
        if (StringUtils.isEmpty(measuringPoint.getMfId())) {
            MeasuringPoint point = this.measuringPointMapper.selectMeasuringPointById(measuringPoint.getParentId());
            return this.getMfId(point);
        } else {
            return measuringPoint.getMfId();
        }
    }

    //获取测点树信息
    public MeasuringPoint getMeasuringPointTree(MeasuringPoint measuringPoint) {
        MeasuringPoint returnMP = new MeasuringPoint();
        //获取结构物对应的检测因素信息
        MeasuringPoint measuringPointParam = new MeasuringPoint();
        measuringPointParam.setStructureId(measuringPoint.getStructureId());
        List<MeasuringPoint> measuringPointList = this.measuringPointMapper.selectMeasuringPointList(measuringPointParam);
        List<SysDictData> monitoringFactors = this.sysDictDataMapper.selectDictDataByType("monitoring_factors"); //监测因素
        HashMap<String, String> hmDictValueToLabel = new HashMap<>();
        for (SysDictData sysDictData : monitoringFactors) {
            hmDictValueToLabel.put(sysDictData.getDictValue(), sysDictData.getDictLabel());
        }
        ArrayList<TreeSelect> alTreeSelect = new ArrayList<>();
        StructureMonitoringFactors smfParam = new StructureMonitoringFactors();
        smfParam.setStructureId(measuringPoint.getStructureId());
        //选择的监测因素集合
        List<StructureMonitoringFactors> structureMonitoringFactorsList = this.structureMonitoringFactorsMapper.selectStructureMonitoringFactorsList(smfParam);
        List<MeasuringPoint> lev1List = measuringPointList.stream().filter(a -> a.getLev() == 1).collect(Collectors.toList());
        List<MeasuringPoint> finalLev1List = lev1List;
        //新增的数据集合  parallel 相同的
        List<StructureMonitoringFactors> addSMFList = structureMonitoringFactorsList.stream().parallel().filter(f -> finalLev1List.stream().noneMatch(g -> f.getMfId().equals(g.getMfId()))).collect(Collectors.toList());
        // 删除的数据集合
        List<MeasuringPoint> deleteSMFList = finalLev1List.stream().distinct().filter(d -> structureMonitoringFactorsList.stream().noneMatch(e -> d.getMfId().equals(e.getMfId()))).collect(Collectors.toList());
        if (addSMFList.size() > 0) {
            //根据结构物获取指定的监测因素信息
            if (measuringPoint.getStructureId() != null) {
                for (StructureMonitoringFactors smf : addSMFList) {
                    MeasuringPoint mpSave = new MeasuringPoint();
                    mpSave.setName(hmDictValueToLabel.get(smf.getMfId()));
                    mpSave.setStructureId(measuringPoint.getStructureId());
                    mpSave.setLev(1L);
                    mpSave.setCreateBy(SecurityUtils.getLoginUser().getUsername());
                    mpSave.setCreateTime(DateUtils.getNowDate());
                    mpSave.setMfId(smf.getMfId()); //监测因素ID
                    this.measuringPointMapper.insertMeasuringPoint(mpSave);
                    measuringPointList.add(mpSave);
                }
            }
        }
        if (deleteSMFList != null && deleteSMFList.size() > 0) {
            for (MeasuringPoint mp : deleteSMFList) {
                this.measuringPointMapper.deleteMeasuringPointById(mp.getId());
            }
        }
        measuringPointList = measuringPointList.stream().parallel().filter(a -> deleteSMFList.stream().noneMatch(b -> a.getMfId().equals(b.getMfId()))).collect(Collectors.toList());
        lev1List = measuringPointList.stream().filter(a -> a.getLev() == 1).collect(Collectors.toList());
        for (MeasuringPoint mpLev1 : lev1List) {
            TreeSelect treeLev1 = new TreeSelect();
            treeLev1.setId(mpLev1.getId());
            treeLev1.setLabel(mpLev1.getName());
            //二级
            List<MeasuringPoint> lev2List = measuringPointList.stream().filter(b -> (b.getLev() == 2 && b.getParentId().equals(mpLev1.getId()))).collect(Collectors.toList());
            ArrayList<TreeSelect> returnLev2List = new ArrayList<>();
            for (MeasuringPoint mpLev2 : lev2List) {
                TreeSelect treeSelectLev2 = new TreeSelect();
                treeSelectLev2.setId(mpLev2.getId());
                treeSelectLev2.setLabel(mpLev2.getName());
                returnLev2List.add(treeSelectLev2);
                //三级
                List<MeasuringPoint> lev3List = measuringPointList.stream().filter(c -> (c.getLev() == 3 && c.getParentId().equals(mpLev2.getId()))).collect(Collectors.toList());
                ArrayList<TreeSelect> returnLev3List = new ArrayList<>();
                for (MeasuringPoint mpLev3 : lev3List) {
                    TreeSelect treeSelectLev3 = new TreeSelect();
                    treeSelectLev3.setId(mpLev3.getId());
                    treeSelectLev3.setLabel(mpLev3.getName());
                    returnLev3List.add(treeSelectLev3);
                }
                treeSelectLev2.setChildren(returnLev3List);
            }
            treeLev1.setChildren(returnLev2List);
            alTreeSelect.add(treeLev1);
        }
        returnMP.setTreeSelect(alTreeSelect);
        return returnMP;
    }

    /**
     * 查询测点列表
     *
     * @param measuringPoint 测点
     * @return 测点
     */
    @Override
    public List<MeasuringPoint> selectMeasuringPointList(MeasuringPoint measuringPoint) {
        return measuringPointMapper.selectMeasuringPointList(measuringPoint);
    }

    /**
     * 新增测点
     *
     * @param measuringPoint 测点
     * @return 结果
     */
    @Transactional(rollbackFor = Exception.class)
    public int insertMeasuringPoint(MeasuringPoint measuringPoint) {
        measuringPoint.setCreateTime(DateUtils.getNowDate());
        return measuringPointMapper.insertMeasuringPoint(measuringPoint);
    }

    @Transactional(rollbackFor = Exception.class)
    public int saveNodeInfo(MeasuringPoint measuringPoint) {
        LinkedHashMap<String, Object> currentNode = (LinkedHashMap<String, Object>) measuringPoint.getNodeInfo();
        Long currentNodeId = Long.parseLong(String.valueOf(currentNode.get("id")));
        String currentNodeName = (String) currentNode.get("name");
        Long currentNodeLev = Long.parseLong(String.valueOf(currentNode.get("lev")));
        LinkedHashMap<String, Object> parentNode = (LinkedHashMap<String, Object>) currentNode.get("parentJson");
        Long parentNodeId = Long.parseLong(String.valueOf(parentNode.get("id")));
        MeasuringPoint parentNodePoint = this.measuringPointMapper.selectMeasuringPointById(parentNodeId);
        MeasuringPoint currNodePoint = this.measuringPointMapper.selectMeasuringPointById(currentNodeId);
        if (currNodePoint == null) {
            currNodePoint = new MeasuringPoint();
            currNodePoint.setName(currentNodeName);
            currNodePoint.setLev(currentNodeLev);
            currNodePoint.setParentId(parentNodeId);
            currNodePoint.setMfId(parentNodePoint.getMfId());
            currNodePoint.setStructureId(measuringPoint.getStructureId()); //结构物ID
            currNodePoint.setCreateBy(SecurityUtils.getUsername());
            currNodePoint.setCreateTime(DateUtils.getNowDate());

            this.measuringPointMapper.insertMeasuringPoint(currNodePoint);
        } else {
            currNodePoint.setName(currentNodeName);
            currNodePoint.setUpdateBy(SecurityUtils.getUsername());
            currNodePoint.setUpdateTime(DateUtils.getNowDate());
            this.measuringPointMapper.updateMeasuringPoint(currNodePoint);
        }
        return 0;
    }

    @Transactional(rollbackFor = Exception.class)
    public int deleteNodeInfo(MeasuringPoint measuringPoint) {
        MeasuringPoint point = this.measuringPointMapper.selectMeasuringPointById(measuringPoint.getNodeId());
        MeasuringPoint mpParam = new MeasuringPoint();
        mpParam.setParentId(point.getId());
        List<MeasuringPoint> measuringPointList = this.measuringPointMapper.selectMeasuringPointList(mpParam);
        for (MeasuringPoint mp : measuringPointList) {
            this.measuringPointMapper.deleteMeasuringPointById(mp.getId());
        }
        return this.measuringPointMapper.deleteMeasuringPointById(point.getId());
    }

    /**
     * 修改测点
     *
     * @param measuringPoint 测点
     * @return 结果
     */
    @Override
    public int updateMeasuringPoint(MeasuringPoint measuringPoint) {
        measuringPoint.setUpdateTime(DateUtils.getNowDate());
        return measuringPointMapper.updateMeasuringPoint(measuringPoint);
    }

    /**
     * 批量删除测点
     *
     * @param ids 需要删除的测点主键
     * @return 结果
     */
    @Override
    public int deleteMeasuringPointByIds(Long[] ids) {
        return measuringPointMapper.deleteMeasuringPointByIds(ids);
    }

    /**
     * 删除测点信息
     *
     * @param id 测点主键
     * @return 结果
     */
    @Override
    public int deleteMeasuringPointById(Long id) {
        return measuringPointMapper.deleteMeasuringPointById(id);
    }
}
