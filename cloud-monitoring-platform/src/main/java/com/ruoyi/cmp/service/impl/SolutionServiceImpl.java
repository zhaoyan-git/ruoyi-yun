package com.ruoyi.cmp.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.cmp.mapper.SolutionMapper;
import com.ruoyi.cmp.domain.Solution;
import com.ruoyi.cmp.service.ISolutionService;

/**
 * 解决方案Service业务层处理
 * 
 * @author zy
 * @date 2022-09-13
 */
@Service
public class SolutionServiceImpl implements ISolutionService 
{
    @Autowired
    private SolutionMapper solutionMapper;

    /**
     * 查询解决方案
     * 
     * @param id 解决方案主键
     * @return 解决方案
     */
    @Override
    public Solution selectSolutionById(Long id)
    {
        return solutionMapper.selectSolutionById(id);
    }

    /**
     * 查询解决方案列表
     * 
     * @param solution 解决方案
     * @return 解决方案
     */
    @Override
    public List<Solution> selectSolutionList(Solution solution)
    {
        return solutionMapper.selectSolutionList(solution);
    }

    /**
     * 新增解决方案
     * 
     * @param solution 解决方案
     * @return 结果
     */
    @Override
    public int insertSolution(Solution solution)
    {
        solution.setCreateTime(DateUtils.getNowDate());
        return solutionMapper.insertSolution(solution);
    }

    /**
     * 修改解决方案
     * 
     * @param solution 解决方案
     * @return 结果
     */
    @Override
    public int updateSolution(Solution solution)
    {
        solution.setUpdateTime(DateUtils.getNowDate());
        return solutionMapper.updateSolution(solution);
    }

    /**
     * 批量删除解决方案
     * 
     * @param ids 需要删除的解决方案主键
     * @return 结果
     */
    @Override
    public int deleteSolutionByIds(Long[] ids)
    {
        return solutionMapper.deleteSolutionByIds(ids);
    }

    /**
     * 删除解决方案信息
     * 
     * @param id 解决方案主键
     * @return 结果
     */
    @Override
    public int deleteSolutionById(Long id)
    {
        return solutionMapper.deleteSolutionById(id);
    }
}
