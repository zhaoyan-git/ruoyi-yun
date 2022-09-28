package com.ruoyi.cmp.service.impl;

import java.util.List;

import com.ruoyi.cmp.constant.BusinessConstant;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.cmp.mapper.ConfigureSensorMapper;
import com.ruoyi.cmp.domain.ConfigureSensor;
import com.ruoyi.cmp.service.IConfigureSensorService;

/**
 * 【请填写功能名称】Service业务层处理
 *
 * @author zy
 * @date 2022-09-07
 */
@Service
public class ConfigureSensorServiceImpl implements IConfigureSensorService {
    @Autowired
    private ConfigureSensorMapper configureSensorMapper;

    /**
     * 查询【请填写功能名称】
     *
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    @Override
    public ConfigureSensor selectConfigureSensorById(Long id) {
        return configureSensorMapper.selectConfigureSensorById(id);
    }

    /**
     * 查询【请填写功能名称】列表
     *
     * @param configureSensor 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<ConfigureSensor> selectConfigureSensorList(ConfigureSensor configureSensor) {
        return configureSensorMapper.selectConfigureSensorList(configureSensor);
    }

    /**
     * 新增【请填写功能名称】
     *
     * @param configureSensor 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertConfigureSensor(ConfigureSensor configureSensor) {
        configureSensor.setCreateTime(DateUtils.getNowDate());
        configureSensor.setCreateBy(SecurityUtils.getLoginUser().getUsername());
        return configureSensorMapper.insertConfigureSensor(configureSensor);
    }

    /**
     * 修改【请填写功能名称】
     *
     * @param configureSensor 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateConfigureSensor(ConfigureSensor configureSensor) {
        configureSensor.setUpdateTime(DateUtils.getNowDate());
        configureSensor.setUpdateBy(SecurityUtils.getUsername());
        return configureSensorMapper.updateConfigureSensor(configureSensor);
    }

    /**
     * 批量删除【请填写功能名称】
     *
     * @param ids 需要删除的【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteConfigureSensorByIds(Long[] ids) {
        return configureSensorMapper.deleteConfigureSensorByIds(ids);
    }

    /**
     * 删除【请填写功能名称】信息
     *
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteConfigureSensorById(Long id) {
        return configureSensorMapper.deleteConfigureSensorById(id);
    }
}
