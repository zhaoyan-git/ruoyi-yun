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
import com.ruoyi.cmp.domain.ConfigureOrder;
import com.ruoyi.cmp.service.IConfigureOrderService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 指令信息配置Controller
 * 
 * @author xjs
 * @date 2022-11-26
 */
@RestController
@RequestMapping("/cmp/order")
public class ConfigureOrderController extends BaseController
{
    @Autowired
    private IConfigureOrderService configureOrderService;

    /**
     * 查询指令信息配置列表
     */
    @PreAuthorize("@ss.hasPermi('cmp:order:list')")
    @GetMapping("/list")
    public TableDataInfo list(ConfigureOrder configureOrder)
    {
        startPage();
        List<ConfigureOrder> list = configureOrderService.selectConfigureOrderList(configureOrder);
        return getDataTable(list);
    }

    /**
     * 导出指令信息配置列表
     */
    @PreAuthorize("@ss.hasPermi('cmp:order:export')")
    @Log(title = "指令信息配置", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ConfigureOrder configureOrder)
    {
        List<ConfigureOrder> list = configureOrderService.selectConfigureOrderList(configureOrder);
        ExcelUtil<ConfigureOrder> util = new ExcelUtil<ConfigureOrder>(ConfigureOrder.class);
        util.exportExcel(response, list, "指令信息配置数据");
    }

    /**
     * 获取指令信息配置详细信息
     */
    @PreAuthorize("@ss.hasPermi('cmp:order:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(configureOrderService.selectConfigureOrderById(id));
    }

    /**
     * 新增指令信息配置
     */
    @PreAuthorize("@ss.hasPermi('cmp:order:add')")
    @Log(title = "指令信息配置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ConfigureOrder configureOrder)
    {
        return toAjax(configureOrderService.insertConfigureOrder(configureOrder));
    }

    /**
     * 修改指令信息配置
     */
    @PreAuthorize("@ss.hasPermi('cmp:order:edit')")
    @Log(title = "指令信息配置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ConfigureOrder configureOrder)
    {
        return toAjax(configureOrderService.updateConfigureOrder(configureOrder));
    }

    /**
     * 删除指令信息配置
     */
    @PreAuthorize("@ss.hasPermi('cmp:order:remove')")
    @Log(title = "指令信息配置", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(configureOrderService.deleteConfigureOrderByIds(ids));
    }
}
