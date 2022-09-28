package com.ruoyi.cmp.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
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
import com.ruoyi.cmp.domain.Solution;
import com.ruoyi.cmp.service.ISolutionService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 解决方案Controller
 * 
 * @author zy
 * @date 2022-09-13
 */
@RestController
@RequestMapping("/cmp/solution")
public class SolutionController extends BaseController
{
    @Autowired
    private ISolutionService solutionService;

    /**
     * 查询解决方案列表
     */
    @PreAuthorize("@ss.hasPermi('cmp:solution:list')")
    @GetMapping("/list")
    public TableDataInfo list(Solution solution)
    {
        startPage();
        List<Solution> list = solutionService.selectSolutionList(solution);
        return getDataTable(list);
    }

    /**
     * 导出解决方案列表
     */
    @PreAuthorize("@ss.hasPermi('cmp:solution:export')")
    @Log(title = "解决方案", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Solution solution)
    {
        List<Solution> list = solutionService.selectSolutionList(solution);
        ExcelUtil<Solution> util = new ExcelUtil<Solution>(Solution.class);
        util.exportExcel(response, list, "解决方案数据");
    }

    /**
     * 获取解决方案详细信息
     */
    @PreAuthorize("@ss.hasPermi('cmp:solution:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(solutionService.selectSolutionById(id));
    }

    /**
     * 新增解决方案
     */
    @PreAuthorize("@ss.hasPermi('cmp:solution:add')")
    @Log(title = "解决方案", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Solution solution)
    {
        return toAjax(solutionService.insertSolution(solution));
    }

    /**
     * 修改解决方案
     */
    @PreAuthorize("@ss.hasPermi('cmp:solution:edit')")
    @Log(title = "解决方案", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Solution solution)
    {
        return toAjax(solutionService.updateSolution(solution));
    }

    /**
     * 删除解决方案
     */
    @PreAuthorize("@ss.hasPermi('cmp:solution:remove')")
    @Log(title = "解决方案", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(solutionService.deleteSolutionByIds(ids));
    }
}
