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
import com.ruoyi.cmp.domain.DeviceGateway;
import com.ruoyi.cmp.service.IDeviceGatewayService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 网关管理Controller
 * 
 * @author zy
 * @date 2022-09-15
 */
@RestController
@RequestMapping("/cmp/gateway")
public class DeviceGatewayController extends BaseController
{
    @Autowired
    private IDeviceGatewayService deviceGatewayService;

    /**
     * 查询网关管理列表
     */
    @PreAuthorize("@ss.hasPermi('cmp:gateway:list')")
    @GetMapping("/list")
    public TableDataInfo list(DeviceGateway deviceGateway)
    {
        startPage();
        List<DeviceGateway> list = deviceGatewayService.selectDeviceGatewayList(deviceGateway);
        return getDataTable(list);
    }

    /**
     * 导出网关管理列表
     */
    @PreAuthorize("@ss.hasPermi('cmp:gateway:export')")
    @Log(title = "网关管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, DeviceGateway deviceGateway)
    {
        List<DeviceGateway> list = deviceGatewayService.selectDeviceGatewayList(deviceGateway);
        ExcelUtil<DeviceGateway> util = new ExcelUtil<DeviceGateway>(DeviceGateway.class);
        util.exportExcel(response, list, "网关管理数据");
    }

    /**
     * 获取网关管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('cmp:gateway:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(deviceGatewayService.selectDeviceGatewayById(id));
    }

    /**
     * 新增网关管理
     */
    @PreAuthorize("@ss.hasPermi('cmp:gateway:add')")
    @Log(title = "网关管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DeviceGateway deviceGateway)
    {
        return toAjax(deviceGatewayService.insertDeviceGateway(deviceGateway));
    }

    /**
     * 修改网关管理
     */
    @PreAuthorize("@ss.hasPermi('cmp:gateway:edit')")
    @Log(title = "网关管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DeviceGateway deviceGateway)
    {
        return toAjax(deviceGatewayService.updateDeviceGateway(deviceGateway));
    }

    /**
     * 删除网关管理
     */
    @PreAuthorize("@ss.hasPermi('cmp:gateway:remove')")
    @Log(title = "网关管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(deviceGatewayService.deleteDeviceGatewayByIds(ids));
    }
}
