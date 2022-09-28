package com.ruoyi.cmp.service;

import java.util.List;
import com.ruoyi.cmp.domain.Solution;

/**
 * 解决方案Service接口
 * 
 * @author zy
 * @date 2022-09-13
 */
public interface ISolutionService 
{
    /**
     * 查询解决方案
     * 
     * @param id 解决方案主键
     * @return 解决方案
     */
    public Solution selectSolutionById(Long id);

    /**
     * 查询解决方案列表
     * 
     * @param solution 解决方案
     * @return 解决方案集合
     */
    public List<Solution> selectSolutionList(Solution solution);

    /**
     * 新增解决方案
     * 
     * @param solution 解决方案
     * @return 结果
     */
    public int insertSolution(Solution solution);

    /**
     * 修改解决方案
     * 
     * @param solution 解决方案
     * @return 结果
     */
    public int updateSolution(Solution solution);

    /**
     * 批量删除解决方案
     * 
     * @param ids 需要删除的解决方案主键集合
     * @return 结果
     */
    public int deleteSolutionByIds(Long[] ids);

    /**
     * 删除解决方案信息
     * 
     * @param id 解决方案主键
     * @return 结果
     */
    public int deleteSolutionById(Long id);
}
