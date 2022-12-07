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
import com.ruoyi.cmp.domain.ThresholdConfiguration;
import com.ruoyi.cmp.service.IThresholdConfigurationService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 阈值配置Controller
 *
 * @author zy
 * @date 2022-11-23
 */
@RestController
@RequestMapping("/cmp/configuration")
public class ThresholdConfigurationController extends BaseController {
    @Autowired
    private IThresholdConfigurationService thresholdConfigurationService;

    /**
     * 查询阈值配置列表
     */
    @PreAuthorize("@ss.hasPermi('cmp:configuration:list')")
    @GetMapping("/list")
    public TableDataInfo list(ThresholdConfiguration thresholdConfiguration) {
        startPage();
        List<ThresholdConfiguration> list = thresholdConfigurationService.selectThresholdConfigurationList(thresholdConfiguration);
        return getDataTable(list);
    }

    /**
     * 导出阈值配置列表
     */
    @PreAuthorize("@ss.hasPermi('cmp:configuration:export')")
    @Log(title = "阈值配置", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ThresholdConfiguration thresholdConfiguration) {
        List<ThresholdConfiguration> list = thresholdConfigurationService.selectThresholdConfigurationList(thresholdConfiguration);
        ExcelUtil<ThresholdConfiguration> util = new ExcelUtil<ThresholdConfiguration>(ThresholdConfiguration.class);
        util.exportExcel(response, list, "阈值配置数据");
    }

    /**
     * 获取阈值配置详细信息
     */
    @PreAuthorize("@ss.hasPermi('cmp:configuration:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(thresholdConfigurationService.selectThresholdConfigurationById(id));
    }

    /**
     * 新增阈值配置
     */
    @PreAuthorize("@ss.hasPermi('cmp:configuration:add')")
    @Log(title = "阈值配置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ThresholdConfiguration thresholdConfiguration) {
        return toAjax(thresholdConfigurationService.insertThresholdConfiguration(thresholdConfiguration));
    }

    /**
     * 修改阈值配置
     */
    @PreAuthorize("@ss.hasPermi('cmp:configuration:edit')")
    @Log(title = "阈值配置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ThresholdConfiguration thresholdConfiguration) {
        return toAjax(thresholdConfigurationService.updateThresholdConfiguration(thresholdConfiguration));
    }

    /**
     * 删除阈值配置
     */
    @PreAuthorize("@ss.hasPermi('cmp:configuration:remove')")
    @Log(title = "阈值配置", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(thresholdConfigurationService.deleteThresholdConfigurationByIds(ids));
    }
}
