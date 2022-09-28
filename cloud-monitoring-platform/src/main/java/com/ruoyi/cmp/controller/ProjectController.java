package com.ruoyi.cmp.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.cmp.constant.BusinessConstant;
import com.ruoyi.cmp.service.IProjectService;
import com.ruoyi.common.utils.StringUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.cmp.domain.Project;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 【项目管理】Controller
 *
 * @author zy
 * @date 2022-08-30
 */
@RestController
@RequestMapping("/business/project")
public class ProjectController extends BaseController {
    @Autowired
    private IProjectService projectService;

    /**
     * 查询项目列表
     */
    //@PreAuthorize("@ss.hasPermi('business:project:list')")
    @GetMapping("/list")
    public TableDataInfo list(Project project) {
        if(StringUtils.isEmpty(project.getIsPage()))
        startPage();
        List<Project> list = projectService.selectProjectList(project);
        return getDataTable(list);
    }

    /**
     * 导出项目列表
     */
    //@PreAuthorize("@ss.hasPermi('business:project:export')")
    @Log(title = "项目", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Project project) {
        List<Project> list = projectService.selectProjectList(project);
        ExcelUtil<Project> util = new ExcelUtil<Project>(Project.class);
        util.exportExcel(response, list, "项目数据");
    }

    /**
     * 获取项目详细信息
     */
    //@PreAuthorize("@ss.hasPermi('business:project:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(projectService.selectProjectById(id));
    }

    /**
     * 新增项目
     */
    //@PreAuthorize("@ss.hasPermi('business:project:add')")
    @Log(title = "项目", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Project project) {
        return toAjax(projectService.insertProject(project));
    }

    /**
     * 修改项目
     */
    //@PreAuthorize("@ss.hasPermi('business:project:edit')")
    @Log(title = "项目", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Project project) {
        return toAjax(projectService.updateProject(project));
    }

    /**
     * 删除项目
     */
    //@PreAuthorize("@ss.hasPermi('business:project:remove')")
    @Log(title = "项目", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(projectService.deleteProjectByIds(ids));
    }

    /**
     * 查询项目列表
     */
    //@PreAuthorize("@ss.hasPermi('business:project:list')")
    @GetMapping("/listProjectForNotPage")
    public TableDataInfo listProjectForNotPage(Project project) {
        project.setDelFlag(BusinessConstant.DELFLAG_WSC);
        List<Project> list = projectService.selectProjectList(project);
        return getDataTable(list);
    }
}
