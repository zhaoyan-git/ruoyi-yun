package com.ruoyi.cmp.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.cmp.mapper.CustomerstoriesMapper;
import com.ruoyi.cmp.domain.Customerstories;
import com.ruoyi.cmp.service.ICustomerstoriesService;

/**
 * 客户案例Service业务层处理
 * 
 * @author zy
 * @date 2022-10-27
 */
@Service
public class CustomerstoriesServiceImpl implements ICustomerstoriesService 
{
    @Autowired
    private CustomerstoriesMapper customerstoriesMapper;

    /**
     * 查询客户案例
     * 
     * @param id 客户案例主键
     * @return 客户案例
     */
    @Override
    public Customerstories selectCustomerstoriesById(Long id)
    {
        return customerstoriesMapper.selectCustomerstoriesById(id);
    }

    /**
     * 查询客户案例列表
     * 
     * @param customerstories 客户案例
     * @return 客户案例
     */
    @Override
    public List<Customerstories> selectCustomerstoriesList(Customerstories customerstories)
    {
        return customerstoriesMapper.selectCustomerstoriesList(customerstories);
    }

    /**
     * 新增客户案例
     * 
     * @param customerstories 客户案例
     * @return 结果
     */
    @Override
    public int insertCustomerstories(Customerstories customerstories)
    {
        customerstories.setCreateTime(DateUtils.getNowDate());
        return customerstoriesMapper.insertCustomerstories(customerstories);
    }

    /**
     * 修改客户案例
     * 
     * @param customerstories 客户案例
     * @return 结果
     */
    @Override
    public int updateCustomerstories(Customerstories customerstories)
    {
        customerstories.setUpdateTime(DateUtils.getNowDate());
        return customerstoriesMapper.updateCustomerstories(customerstories);
    }

    /**
     * 批量删除客户案例
     * 
     * @param ids 需要删除的客户案例主键
     * @return 结果
     */
    @Override
    public int deleteCustomerstoriesByIds(Long[] ids)
    {
        return customerstoriesMapper.deleteCustomerstoriesByIds(ids);
    }

    /**
     * 删除客户案例信息
     * 
     * @param id 客户案例主键
     * @return 结果
     */
    @Override
    public int deleteCustomerstoriesById(Long id)
    {
        return customerstoriesMapper.deleteCustomerstoriesById(id);
    }
}
