package com.ruoyi.cmp.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.ruoyi.cmp.constant.BusinessConstant;
import com.ruoyi.cmp.domain.Equipment;
import com.ruoyi.cmp.domain.Structure;
import com.ruoyi.cmp.domain.StructureMonitoringFactors;
import com.ruoyi.cmp.mapper.EquipmentMapper;
import com.ruoyi.cmp.mapper.StructureMapper;
import com.ruoyi.cmp.mapper.StructureMonitoringFactorsMapper;
import com.ruoyi.cmp.service.IStructureService;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static java.util.stream.Collectors.toList;


/**
 * 结构物管理Service业务层处理
 *
 * @author zy
 * @date 2022-08-31
 */
@Service
public class StructureServiceImpl implements IStructureService {
    @Autowired
    private StructureMapper structureMapper;
    @Autowired
    private EquipmentMapper equipmentMapper;
    @Autowired
    private StructureMonitoringFactorsMapper structureMonitoringFactorsMapper;

    /**
     * 查询结构物管理
     *
     * @param id 结构物管理主键
     * @return 结构物管理
     */
    @Override
    public Structure selectStructureById(Long id) {
        return structureMapper.selectStructureById(id);
    }

    /**
     * 查询结构物管理列表
     *
     * @param structure 结构物管理
     * @return 结构物管理
     */
    @Override
    public List<Structure> selectStructureList(Structure structure) {
        return structureMapper.selectStructureList(structure);
    }

    /**
     * 新增结构物管理
     *
     * @param structure 结构物管理
     * @return 结果
     */
    @Override
    public int insertStructure(Structure structure) {
        String username = SecurityUtils.getLoginUser().getUsername();
        structure.setCreateTime(DateUtils.getNowDate());
        structure.setCreateBy(username);
        structure.setDelFlag(BusinessConstant.DELFLAG_WSC);
        int i = structureMapper.insertStructure(structure);
        return i;
    }

    @Override
    public int insertMF(Structure structure) {
        if (structure.getMf() != null) {
            StructureMonitoringFactors smfParam = new StructureMonitoringFactors();
            smfParam.setStructureId(structure.getId());
            List<StructureMonitoringFactors> structureMonitoringFactorsList = this.structureMonitoringFactorsMapper.selectStructureMonitoringFactorsList(smfParam);
            ArrayList<String> alExtis = new ArrayList<>();
            for (StructureMonitoringFactors structureMonitoringFactors : structureMonitoringFactorsList) {
                alExtis.add(structureMonitoringFactors.getMfId());
            }
            ArrayList<String> all = (ArrayList<String>) structure.getMf();
            //alExtis - all   需要删除的数据
            List<StructureMonitoringFactors> collect = structureMonitoringFactorsList.stream().filter(item -> !all.contains(item.getMfId())).collect(toList());
            for (int i = 0; i < collect.size(); i++) {
                this.structureMonitoringFactorsMapper.deleteStructureMonitoringFactorsById(collect.get(i).getId());
                //todo: 后续需要删除关联的测点信息 代码带补充
            }
            for (int i = 0; i < all.size(); i++) {
                if (alExtis.contains(all.get(i))) {
                    continue;
                }
                StructureMonitoringFactors smf = new StructureMonitoringFactors();
                smf.setStructureId(structure.getId());
                smf.setMfId(all.get(i));
                this.structureMonitoringFactorsMapper.insertStructureMonitoringFactors(smf);
            }
        }
        return 1;
    }

    /**
     * 修改结构物管理
     *
     * @param structure 结构物管理
     * @return 结果
     */
    @Override
    public int updateStructure(Structure structure) {
        String username = SecurityUtils.getLoginUser().getUsername();
        structure.setUpdateTime(DateUtils.getNowDate());
        structure.setUpdateBy(username);
        return structureMapper.updateStructure(structure);
    }

    @Override
    public int updateStructureForEquipment(Structure structure) {
        String username = SecurityUtils.getLoginUser().getUsername();
        List<Equipment> equipmentList = structure.getEquipmentList();
        for (Equipment equipment : equipmentList) {
            equipment.setBusinessId(structure.getId());
            equipment.setUpdateTime(DateUtils.getNowDate());
            equipment.setUpdateBy(username);
            this.equipmentMapper.updateEquipment(equipment);
        }
        return equipmentList.size();
    }

    /**
     * 批量删除结构物管理
     *
     * @param ids 需要删除的结构物管理主键
     * @return 结果
     */
    @Override
    public int deleteStructureByIds(Long[] ids) {
        return structureMapper.deleteStructureByIds(ids);
    }

    /**
     * 删除结构物管理信息
     *
     * @param id 结构物管理主键
     * @return 结果
     */
    @Override
    public int deleteStructureById(Long id) {
        return structureMapper.deleteStructureById(id);
    }

    @Override
    public Structure getMFInfo(Long id) {
        Structure structure = new Structure();
        StructureMonitoringFactors smfParam = new StructureMonitoringFactors();
        smfParam.setStructureId(id);
        List<StructureMonitoringFactors> structureMonitoringFactorsList = this.structureMonitoringFactorsMapper.selectStructureMonitoringFactorsList(smfParam);
        ArrayList<String> al = new ArrayList<>();
        for (StructureMonitoringFactors structureMonitoringFactors:structureMonitoringFactorsList) {
            al.add(structureMonitoringFactors.getMfId());
        }
        structure.setMf(al);
        return structure;
    }
}
