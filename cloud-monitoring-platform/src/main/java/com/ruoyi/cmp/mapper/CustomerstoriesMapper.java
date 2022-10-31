package com.ruoyi.cmp.mapper;

import java.util.List;
import com.ruoyi.cmp.domain.Customerstories;

/**
 * 客户案例Mapper接口
 * 
 * @author zy
 * @date 2022-10-27
 */
public interface CustomerstoriesMapper 
{
    /**
     * 查询客户案例
     * 
     * @param id 客户案例主键
     * @return 客户案例
     */
    public Customerstories selectCustomerstoriesById(Long id);

    /**
     * 查询客户案例列表
     * 
     * @param customerstories 客户案例
     * @return 客户案例集合
     */
    public List<Customerstories> selectCustomerstoriesList(Customerstories customerstories);

    /**
     * 新增客户案例
     * 
     * @param customerstories 客户案例
     * @return 结果
     */
    public int insertCustomerstories(Customerstories customerstories);

    /**
     * 修改客户案例
     * 
     * @param customerstories 客户案例
     * @return 结果
     */
    public int updateCustomerstories(Customerstories customerstories);

    /**
     * 删除客户案例
     * 
     * @param id 客户案例主键
     * @return 结果
     */
    public int deleteCustomerstoriesById(Long id);

    /**
     * 批量删除客户案例
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteCustomerstoriesByIds(Long[] ids);
}
