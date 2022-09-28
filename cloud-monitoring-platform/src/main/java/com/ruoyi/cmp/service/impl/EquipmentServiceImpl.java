package com.ruoyi.cmp.service.impl;

import java.util.List;

import com.ruoyi.cmp.domain.Equipment;
import com.ruoyi.cmp.mapper.EquipmentMapper;
import com.ruoyi.cmp.service.IEquipmentService;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * 设备管理Service业务层处理
 *
 * @author zy
 * @date 2022-09-04
 */
@Service
public class EquipmentServiceImpl implements IEquipmentService {
    @Autowired
    private EquipmentMapper equipmentMapper;

    /**
     * 查询设备管理
     *
     * @param id 设备管理主键
     * @return 设备管理
     */
    @Override
    public Equipment selectEquipmentById(Long id) {
        return equipmentMapper.selectEquipmentById(id);
    }

    /**
     * 查询设备管理列表
     *
     * @param equipment 设备管理
     * @return 设备管理
     */
    @Override
    public List<Equipment> selectEquipmentList(Equipment equipment) {
        return equipmentMapper.selectEquipmentList(equipment);
    }

    /**
     * 新增设备管理
     *
     * @param equipment 设备管理
     * @return 结果
     */
    @Transactional(rollbackFor = Exception.class)
    public int insertEquipment(Equipment equipment) {
        equipment.setCreateTime(DateUtils.getNowDate());
        return equipmentMapper.insertEquipment(equipment);
    }

    /**
     * 修改设备管理
     *
     * @param equipment 设备管理
     * @return 结果
     */
    @Transactional(rollbackFor = Exception.class)
    public int updateEquipment(Equipment equipment) {
        equipment.setUpdateTime(DateUtils.getNowDate());
        return equipmentMapper.updateEquipment(equipment);
    }

   @Transactional(rollbackFor = Exception.class)
    public int deleteStructureForEquipment(Equipment equipment) {
        return this.equipmentMapper.deleteStructureForEquipment(equipment);
    }

    /**
     * 批量删除设备管理
     *
     * @param ids 需要删除的设备管理主键
     * @return 结果
     */
    @Override
    public int deleteEquipmentByIds(Long[] ids) {
        return equipmentMapper.deleteEquipmentByIds(ids);
    }

    /**
     * 删除设备管理信息
     *
     * @param id 设备管理主键
     * @return 结果
     */
    @Override
    public int deleteEquipmentById(Long id) {
        return equipmentMapper.deleteEquipmentById(id);
    }
}
