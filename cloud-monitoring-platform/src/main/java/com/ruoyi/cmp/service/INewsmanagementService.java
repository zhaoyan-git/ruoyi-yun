package com.ruoyi.cmp.service;

import java.util.List;
import com.ruoyi.cmp.domain.Newsmanagement;

/**
 * 新闻管理Service接口
 * 
 * @author zy
 * @date 2022-10-27
 */
public interface INewsmanagementService 
{
    /**
     * 查询新闻管理
     * 
     * @param id 新闻管理主键
     * @return 新闻管理
     */
    public Newsmanagement selectNewsmanagementById(Long id);

    /**
     * 查询新闻管理列表
     * 
     * @param newsmanagement 新闻管理
     * @return 新闻管理集合
     */
    public List<Newsmanagement> selectNewsmanagementList(Newsmanagement newsmanagement);

    /**
     * 新增新闻管理
     * 
     * @param newsmanagement 新闻管理
     * @return 结果
     */
    public int insertNewsmanagement(Newsmanagement newsmanagement);

    /**
     * 修改新闻管理
     * 
     * @param newsmanagement 新闻管理
     * @return 结果
     */
    public int updateNewsmanagement(Newsmanagement newsmanagement);

    /**
     * 批量删除新闻管理
     * 
     * @param ids 需要删除的新闻管理主键集合
     * @return 结果
     */
    public int deleteNewsmanagementByIds(Long[] ids);

    /**
     * 删除新闻管理信息
     * 
     * @param id 新闻管理主键
     * @return 结果
     */
    public int deleteNewsmanagementById(Long id);
}
