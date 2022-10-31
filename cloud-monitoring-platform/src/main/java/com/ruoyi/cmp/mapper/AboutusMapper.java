package com.ruoyi.cmp.mapper;

import java.util.List;
import com.ruoyi.cmp.domain.Aboutus;

/**
 * 关于我们Mapper接口
 * 
 * @author zy
 * @date 2022-10-27
 */
public interface AboutusMapper 
{
    /**
     * 查询关于我们
     * 
     * @param id 关于我们主键
     * @return 关于我们
     */
    public Aboutus selectAboutusById(Long id);

    /**
     * 查询关于我们列表
     * 
     * @param aboutus 关于我们
     * @return 关于我们集合
     */
    public List<Aboutus> selectAboutusList(Aboutus aboutus);

    /**
     * 新增关于我们
     * 
     * @param aboutus 关于我们
     * @return 结果
     */
    public int insertAboutus(Aboutus aboutus);

    /**
     * 修改关于我们
     * 
     * @param aboutus 关于我们
     * @return 结果
     */
    public int updateAboutus(Aboutus aboutus);

    /**
     * 删除关于我们
     * 
     * @param id 关于我们主键
     * @return 结果
     */
    public int deleteAboutusById(Long id);

    /**
     * 批量删除关于我们
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteAboutusByIds(Long[] ids);
}
