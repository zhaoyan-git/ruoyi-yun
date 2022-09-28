package com.ruoyi.cmp.mapper;

import java.util.List;
import com.ruoyi.cmp.domain.ConfigureDtu;

/**
 * 【请填写功能名称】Mapper接口
 * 
 * @author zy
 * @date 2022-09-07
 */
public interface ConfigureDtuMapper 
{
    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    public ConfigureDtu selectConfigureDtuById(Long id);

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param configureDtu 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<ConfigureDtu> selectConfigureDtuList(ConfigureDtu configureDtu);

    /**
     * 新增【请填写功能名称】
     * 
     * @param configureDtu 【请填写功能名称】
     * @return 结果
     */
    public int insertConfigureDtu(ConfigureDtu configureDtu);

    /**
     * 修改【请填写功能名称】
     * 
     * @param configureDtu 【请填写功能名称】
     * @return 结果
     */
    public int updateConfigureDtu(ConfigureDtu configureDtu);

    /**
     * 删除【请填写功能名称】
     * 
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    public int deleteConfigureDtuById(Long id);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteConfigureDtuByIds(Long[] ids);
}
