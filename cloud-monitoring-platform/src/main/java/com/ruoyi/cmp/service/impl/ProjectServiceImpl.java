package com.ruoyi.cmp.service.impl;

import java.util.List;

import com.ruoyi.cmp.constant.BusinessConstant;
import com.ruoyi.cmp.service.IProjectService;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.cmp.mapper.ProjectMapper;
import com.ruoyi.cmp.domain.Project;

/**
 * 【请填写功能名称】Service业务层处理
 *
 * @author zy
 * @date 2022-08-30
 */
@Service
public class ProjectServiceImpl implements IProjectService {
    @Autowired
    private ProjectMapper projectMapper;

    /**
     * 查询【请填写功能名称】
     *
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    @Override
    public Project selectProjectById(Long id) {
        return projectMapper.selectProjectById(id);
    }

    /**
     * 查询【请填写功能名称】列表
     *
     * @param project 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<Project> selectProjectList(Project project) {
        return projectMapper.selectProjectList(project);
    }

    /**
     * 新增【请填写功能名称】
     *
     * @param project 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertProject(Project project) {
        project.setCreateTime(DateUtils.getNowDate());
        project.setCreateBy(SecurityUtils.getLoginUser().getUsername());
        project.setDelFlag(BusinessConstant.DELFLAG_WSC);
        return projectMapper.insertProject(project);
    }

    /**
     * 修改【请填写功能名称】
     *
     * @param project 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateProject(Project project) {
        project.setUpdateTime(DateUtils.getNowDate());
        return projectMapper.updateProject(project);
    }

    /**
     * 批量删除【请填写功能名称】
     *
     * @param ids 需要删除的【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteProjectByIds(Long[] ids) {
        return projectMapper.deleteProjectByIds(ids);
    }

    /**
     * 删除【请填写功能名称】信息
     *
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteProjectById(Long id) {
        return projectMapper.deleteProjectById(id);
    }
}
