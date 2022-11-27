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
import com.ruoyi.cmp.domain.ConfigureSensorData;
import com.ruoyi.cmp.service.IConfigureSensorDataService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 传感器数据Controller
 * 
 * @author xjs
 * @date 2022-11-26
 */
@RestController
@RequestMapping("/cmp/data")
public class ConfigureSensorDataController extends BaseController
{
    @Autowired
    private IConfigureSensorDataService configureSensorDataService;

    /**
     * 查询传感器数据列表
     */
    @PreAuthorize("@ss.hasPermi('cmp:data:list')")
    @GetMapping("/list")
    public TableDataInfo list(ConfigureSensorData configureSensorData)
    {
        startPage();
        List<ConfigureSensorData> list = configureSensorDataService.selectConfigureSensorDataList(configureSensorData);
        return getDataTable(list);
    }

    /**
     * 导出传感器数据列表
     */
    @PreAuthorize("@ss.hasPermi('cmp:data:export')")
    @Log(title = "传感器数据", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ConfigureSensorData configureSensorData)
    {
        List<ConfigureSensorData> list = configureSensorDataService.selectConfigureSensorDataList(configureSensorData);
        ExcelUtil<ConfigureSensorData> util = new ExcelUtil<ConfigureSensorData>(ConfigureSensorData.class);
        util.exportExcel(response, list, "传感器数据数据");
    }

    /**
     * 获取传感器数据详细信息
     */
    @PreAuthorize("@ss.hasPermi('cmp:data:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(configureSensorDataService.selectConfigureSensorDataById(id));
    }

    /**
     * 新增传感器数据
     */
    @PreAuthorize("@ss.hasPermi('cmp:data:add')")
    @Log(title = "传感器数据", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ConfigureSensorData configureSensorData)
    {
        return toAjax(configureSensorDataService.insertConfigureSensorData(configureSensorData));
    }

    /**
     * 修改传感器数据
     */
    @PreAuthorize("@ss.hasPermi('cmp:data:edit')")
    @Log(title = "传感器数据", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ConfigureSensorData configureSensorData)
    {
        return toAjax(configureSensorDataService.updateConfigureSensorData(configureSensorData));
    }

    /**
     * 删除传感器数据
     */
    @PreAuthorize("@ss.hasPermi('cmp:data:remove')")
    @Log(title = "传感器数据", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(configureSensorDataService.deleteConfigureSensorDataByIds(ids));
    }
}
