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
import com.ruoyi.cmp.domain.StructureMonitoringFactors;
import com.ruoyi.cmp.service.IStructureMonitoringFactorsService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 【请填写功能名称】Controller
 *
 * @author zy
 * @date 2022-09-08
 */
@RestController
@RequestMapping("/cmp/factors")
public class StructureMonitoringFactorsController extends BaseController {
    @Autowired
    private IStructureMonitoringFactorsService structureMonitoringFactorsService;

    /**
     * 查询【请填写功能名称】列表
     */
    @PreAuthorize("@ss.hasPermi('cmp:factors:list')")
    @GetMapping("/list")
    public TableDataInfo list(StructureMonitoringFactors structureMonitoringFactors) {
        startPage();
        List<StructureMonitoringFactors> list = structureMonitoringFactorsService.selectStructureMonitoringFactorsList(structureMonitoringFactors);
        return getDataTable(list);
    }

    /**
     * 导出【请填写功能名称】列表
     */
    @PreAuthorize("@ss.hasPermi('cmp:factors:export')")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, StructureMonitoringFactors structureMonitoringFactors) {
        List<StructureMonitoringFactors> list = structureMonitoringFactorsService.selectStructureMonitoringFactorsList(structureMonitoringFactors);
        ExcelUtil<StructureMonitoringFactors> util = new ExcelUtil<StructureMonitoringFactors>(StructureMonitoringFactors.class);
        util.exportExcel(response, list, "【请填写功能名称】数据");
    }

    /**
     * 获取【请填写功能名称】详细信息
     */
    @PreAuthorize("@ss.hasPermi('cmp:factors:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(structureMonitoringFactorsService.selectStructureMonitoringFactorsById(id));
    }

    /**
     * 新增【请填写功能名称】
     */
    @PreAuthorize("@ss.hasPermi('cmp:factors:add')")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody StructureMonitoringFactors structureMonitoringFactors) {
        return toAjax(structureMonitoringFactorsService.insertStructureMonitoringFactors(structureMonitoringFactors));
    }

    /**
     * 修改【请填写功能名称】
     */
    @PreAuthorize("@ss.hasPermi('cmp:factors:edit')")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody StructureMonitoringFactors structureMonitoringFactors) {
        return toAjax(structureMonitoringFactorsService.updateStructureMonitoringFactors(structureMonitoringFactors));
    }

    /**
     * 删除【请填写功能名称】
     */
    @PreAuthorize("@ss.hasPermi('cmp:factors:remove')")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(structureMonitoringFactorsService.deleteStructureMonitoringFactorsByIds(ids));
    }
}
