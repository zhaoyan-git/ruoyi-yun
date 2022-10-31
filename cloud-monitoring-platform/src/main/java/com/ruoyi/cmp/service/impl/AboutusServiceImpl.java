package com.ruoyi.cmp.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.cmp.mapper.AboutusMapper;
import com.ruoyi.cmp.domain.Aboutus;
import com.ruoyi.cmp.service.IAboutusService;

/**
 * 关于我们Service业务层处理
 * 
 * @author zy
 * @date 2022-10-27
 */
@Service
public class AboutusServiceImpl implements IAboutusService 
{
    @Autowired
    private AboutusMapper aboutusMapper;

    /**
     * 查询关于我们
     * 
     * @param id 关于我们主键
     * @return 关于我们
     */
    @Override
    public Aboutus selectAboutusById(Long id)
    {
        return aboutusMapper.selectAboutusById(id);
    }

    /**
     * 查询关于我们列表
     * 
     * @param aboutus 关于我们
     * @return 关于我们
     */
    @Override
    public List<Aboutus> selectAboutusList(Aboutus aboutus)
    {
        return aboutusMapper.selectAboutusList(aboutus);
    }

    /**
     * 新增关于我们
     * 
     * @param aboutus 关于我们
     * @return 结果
     */
    @Override
    public int insertAboutus(Aboutus aboutus)
    {
        aboutus.setCreateTime(DateUtils.getNowDate());
        return aboutusMapper.insertAboutus(aboutus);
    }

    /**
     * 修改关于我们
     * 
     * @param aboutus 关于我们
     * @return 结果
     */
    @Override
    public int updateAboutus(Aboutus aboutus)
    {
        aboutus.setUpdateTime(DateUtils.getNowDate());
        return aboutusMapper.updateAboutus(aboutus);
    }

    /**
     * 批量删除关于我们
     * 
     * @param ids 需要删除的关于我们主键
     * @return 结果
     */
    @Override
    public int deleteAboutusByIds(Long[] ids)
    {
        return aboutusMapper.deleteAboutusByIds(ids);
    }

    /**
     * 删除关于我们信息
     * 
     * @param id 关于我们主键
     * @return 结果
     */
    @Override
    public int deleteAboutusById(Long id)
    {
        return aboutusMapper.deleteAboutusById(id);
    }
}
