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
import com.ruoyi.cmp.domain.Aboutus;
import com.ruoyi.cmp.service.IAboutusService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 关于我们Controller
 * 
 * @author zy
 * @date 2022-10-27
 */
@RestController
@RequestMapping("/cmp/aboutus")
public class AboutusController extends BaseController
{
    @Autowired
    private IAboutusService aboutusService;

    /**
     * 查询关于我们列表
     */
    @PreAuthorize("@ss.hasPermi('cmp:aboutus:list')")
    @GetMapping("/list")
    public TableDataInfo list(Aboutus aboutus)
    {
        startPage();
        List<Aboutus> list = aboutusService.selectAboutusList(aboutus);
        return getDataTable(list);
    }

    /**
     * 导出关于我们列表
     */
    @PreAuthorize("@ss.hasPermi('cmp:aboutus:export')")
    @Log(title = "关于我们", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Aboutus aboutus)
    {
        List<Aboutus> list = aboutusService.selectAboutusList(aboutus);
        ExcelUtil<Aboutus> util = new ExcelUtil<Aboutus>(Aboutus.class);
        util.exportExcel(response, list, "关于我们数据");
    }

    /**
     * 获取关于我们详细信息
     */
    @PreAuthorize("@ss.hasPermi('cmp:aboutus:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(aboutusService.selectAboutusById(id));
    }

    /**
     * 新增关于我们
     */
    @PreAuthorize("@ss.hasPermi('cmp:aboutus:add')")
    @Log(title = "关于我们", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Aboutus aboutus)
    {
        return toAjax(aboutusService.insertAboutus(aboutus));
    }

    /**
     * 修改关于我们
     */
    @PreAuthorize("@ss.hasPermi('cmp:aboutus:edit')")
    @Log(title = "关于我们", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Aboutus aboutus)
    {
        return toAjax(aboutusService.updateAboutus(aboutus));
    }

    /**
     * 删除关于我们
     */
    @PreAuthorize("@ss.hasPermi('cmp:aboutus:remove')")
    @Log(title = "关于我们", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(aboutusService.deleteAboutusByIds(ids));
    }
}
