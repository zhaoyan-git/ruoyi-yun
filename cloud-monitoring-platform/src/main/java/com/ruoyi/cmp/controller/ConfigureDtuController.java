package com.ruoyi.business.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;


import com.ruoyi.cmp.domain.ConfigureDtu;
import com.ruoyi.cmp.service.IConfigureDtuService;
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
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 【请填写功能名称】Controller
 *
 * @author zy
 * @date 2022-09-07
 */
@RestController
@RequestMapping("/business/dtu")
public class ConfigureDtuController extends BaseController {
    @Autowired
    private IConfigureDtuService configureDtuService;

    /**
     * 查询【请填写功能名称】列表
     */
    @PreAuthorize("@ss.hasPermi('business:dtu:list')")
    @GetMapping("/list")
    public TableDataInfo list(ConfigureDtu configureDtu) {
        startPage();
        List<ConfigureDtu> list = configureDtuService.selectConfigureDtuList(configureDtu);
        return getDataTable(list);
    }

    /**
     * 查询【请填写功能名称】列表
     */
    @PreAuthorize("@ss.hasPermi('business:dtu:list')")
    @GetMapping("/notPageList")
    public TableDataInfo notPageList(ConfigureDtu configureDtu) {
        List<ConfigureDtu> list = configureDtuService.selectConfigureDtuList(configureDtu);
        return getDataTable(list);
    }

    /**
     * 导出【请填写功能名称】列表
     */
    @PreAuthorize("@ss.hasPermi('business:dtu:export')")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ConfigureDtu configureDtu) {
        List<ConfigureDtu> list = configureDtuService.selectConfigureDtuList(configureDtu);
        ExcelUtil<ConfigureDtu> util = new ExcelUtil<ConfigureDtu>(ConfigureDtu.class);
        util.exportExcel(response, list, "【请填写功能名称】数据");
    }

    /**
     * 获取【请填写功能名称】详细信息
     */
    @PreAuthorize("@ss.hasPermi('business:dtu:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(configureDtuService.selectConfigureDtuById(id));
    }

    /**
     * 新增【请填写功能名称】
     */
    @PreAuthorize("@ss.hasPermi('business:dtu:add')")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ConfigureDtu configureDtu) {
        return toAjax(configureDtuService.insertConfigureDtu(configureDtu));
    }

    /**
     * 修改【请填写功能名称】
     */
    @PreAuthorize("@ss.hasPermi('business:dtu:edit')")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ConfigureDtu configureDtu) {
        return toAjax(configureDtuService.updateConfigureDtu(configureDtu));
    }

    /**
     * 删除【请填写功能名称】
     */
    @PreAuthorize("@ss.hasPermi('business:dtu:remove')")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(configureDtuService.deleteConfigureDtuByIds(ids));
    }
}
