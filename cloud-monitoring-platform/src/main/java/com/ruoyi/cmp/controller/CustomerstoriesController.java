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
import com.ruoyi.cmp.domain.Customerstories;
import com.ruoyi.cmp.service.ICustomerstoriesService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 客户案例Controller
 * 
 * @author zy
 * @date 2022-10-27
 */
@RestController
@RequestMapping("/cmp/customerstories")
public class CustomerstoriesController extends BaseController
{
    @Autowired
    private ICustomerstoriesService customerstoriesService;

    /**
     * 查询客户案例列表
     */
    @PreAuthorize("@ss.hasPermi('cmp:customerstories:list')")
    @GetMapping("/list")
    public TableDataInfo list(Customerstories customerstories)
    {
        startPage();
        List<Customerstories> list = customerstoriesService.selectCustomerstoriesList(customerstories);
        return getDataTable(list);
    }

    /**
     * 导出客户案例列表
     */
    @PreAuthorize("@ss.hasPermi('cmp:customerstories:export')")
    @Log(title = "客户案例", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Customerstories customerstories)
    {
        List<Customerstories> list = customerstoriesService.selectCustomerstoriesList(customerstories);
        ExcelUtil<Customerstories> util = new ExcelUtil<Customerstories>(Customerstories.class);
        util.exportExcel(response, list, "客户案例数据");
    }

    /**
     * 获取客户案例详细信息
     */
    @PreAuthorize("@ss.hasPermi('cmp:customerstories:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(customerstoriesService.selectCustomerstoriesById(id));
    }

    /**
     * 新增客户案例
     */
    @PreAuthorize("@ss.hasPermi('cmp:customerstories:add')")
    @Log(title = "客户案例", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Customerstories customerstories)
    {
        return toAjax(customerstoriesService.insertCustomerstories(customerstories));
    }

    /**
     * 修改客户案例
     */
    @PreAuthorize("@ss.hasPermi('cmp:customerstories:edit')")
    @Log(title = "客户案例", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Customerstories customerstories)
    {
        return toAjax(customerstoriesService.updateCustomerstories(customerstories));
    }

    /**
     * 删除客户案例
     */
    @PreAuthorize("@ss.hasPermi('cmp:customerstories:remove')")
    @Log(title = "客户案例", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(customerstoriesService.deleteCustomerstoriesByIds(ids));
    }
}
