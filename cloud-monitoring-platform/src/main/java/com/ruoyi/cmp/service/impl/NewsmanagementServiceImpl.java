package com.ruoyi.cmp.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.cmp.mapper.NewsmanagementMapper;
import com.ruoyi.cmp.domain.Newsmanagement;
import com.ruoyi.cmp.service.INewsmanagementService;

/**
 * 新闻管理Service业务层处理
 * 
 * @author zy
 * @date 2022-10-27
 */
@Service
public class NewsmanagementServiceImpl implements INewsmanagementService 
{
    @Autowired
    private NewsmanagementMapper newsmanagementMapper;

    /**
     * 查询新闻管理
     * 
     * @param id 新闻管理主键
     * @return 新闻管理
     */
    @Override
    public Newsmanagement selectNewsmanagementById(Long id)
    {
        return newsmanagementMapper.selectNewsmanagementById(id);
    }

    /**
     * 查询新闻管理列表
     * 
     * @param newsmanagement 新闻管理
     * @return 新闻管理
     */
    @Override
    public List<Newsmanagement> selectNewsmanagementList(Newsmanagement newsmanagement)
    {
        return newsmanagementMapper.selectNewsmanagementList(newsmanagement);
    }

    /**
     * 新增新闻管理
     * 
     * @param newsmanagement 新闻管理
     * @return 结果
     */
    @Override
    public int insertNewsmanagement(Newsmanagement newsmanagement)
    {
        newsmanagement.setCreateTime(DateUtils.getNowDate());
        return newsmanagementMapper.insertNewsmanagement(newsmanagement);
    }

    /**
     * 修改新闻管理
     * 
     * @param newsmanagement 新闻管理
     * @return 结果
     */
    @Override
    public int updateNewsmanagement(Newsmanagement newsmanagement)
    {
        newsmanagement.setUpdateTime(DateUtils.getNowDate());
        return newsmanagementMapper.updateNewsmanagement(newsmanagement);
    }

    /**
     * 批量删除新闻管理
     * 
     * @param ids 需要删除的新闻管理主键
     * @return 结果
     */
    @Override
    public int deleteNewsmanagementByIds(Long[] ids)
    {
        return newsmanagementMapper.deleteNewsmanagementByIds(ids);
    }

    /**
     * 删除新闻管理信息
     * 
     * @param id 新闻管理主键
     * @return 结果
     */
    @Override
    public int deleteNewsmanagementById(Long id)
    {
        return newsmanagementMapper.deleteNewsmanagementById(id);
    }
}
