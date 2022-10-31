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
import com.ruoyi.cmp.domain.Newsmanagement;
import com.ruoyi.cmp.service.INewsmanagementService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 新闻管理Controller
 * 
 * @author zy
 * @date 2022-10-27
 */
@RestController
@RequestMapping("/cmp/newsmanagement")
public class NewsmanagementController extends BaseController
{
    @Autowired
    private INewsmanagementService newsmanagementService;

    /**
     * 查询新闻管理列表
     */
    @PreAuthorize("@ss.hasPermi('cmp:newsmanagement:list')")
    @GetMapping("/list")
    public TableDataInfo list(Newsmanagement newsmanagement)
    {
        startPage();
        List<Newsmanagement> list = newsmanagementService.selectNewsmanagementList(newsmanagement);
        return getDataTable(list);
    }

    /**
     * 导出新闻管理列表
     */
    @PreAuthorize("@ss.hasPermi('cmp:newsmanagement:export')")
    @Log(title = "新闻管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Newsmanagement newsmanagement)
    {
        List<Newsmanagement> list = newsmanagementService.selectNewsmanagementList(newsmanagement);
        ExcelUtil<Newsmanagement> util = new ExcelUtil<Newsmanagement>(Newsmanagement.class);
        util.exportExcel(response, list, "新闻管理数据");
    }

    /**
     * 获取新闻管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('cmp:newsmanagement:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(newsmanagementService.selectNewsmanagementById(id));
    }

    /**
     * 新增新闻管理
     */
    @PreAuthorize("@ss.hasPermi('cmp:newsmanagement:add')")
    @Log(title = "新闻管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Newsmanagement newsmanagement)
    {
        return toAjax(newsmanagementService.insertNewsmanagement(newsmanagement));
    }

    /**
     * 修改新闻管理
     */
    @PreAuthorize("@ss.hasPermi('cmp:newsmanagement:edit')")
    @Log(title = "新闻管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Newsmanagement newsmanagement)
    {
        return toAjax(newsmanagementService.updateNewsmanagement(newsmanagement));
    }

    /**
     * 删除新闻管理
     */
    @PreAuthorize("@ss.hasPermi('cmp:newsmanagement:remove')")
    @Log(title = "新闻管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(newsmanagementService.deleteNewsmanagementByIds(ids));
    }
}
