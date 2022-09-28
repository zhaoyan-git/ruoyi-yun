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
import com.ruoyi.cmp.domain.MeasuringPoint;
import com.ruoyi.cmp.service.IMeasuringPointService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 测点Controller
 *
 * @author zy
 * @date 2022-09-09
 */
@RestController
@RequestMapping("/cmp/point")
public class MeasuringPointController extends BaseController {
    @Autowired
    private IMeasuringPointService measuringPointService;

    /**
     * 查询测点列表
     */
    @PreAuthorize("@ss.hasPermi('cmp:point:list')")
    @GetMapping("/list")
    public TableDataInfo list(MeasuringPoint measuringPoint) {
        List<MeasuringPoint> list = measuringPointService.selectMeasuringPointList(measuringPoint);
        return getDataTable(list);
    }

    /**
     * 导出测点列表
     */
    @PreAuthorize("@ss.hasPermi('cmp:point:export')")
    @Log(title = "测点", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, MeasuringPoint measuringPoint) {
        List<MeasuringPoint> list = measuringPointService.selectMeasuringPointList(measuringPoint);
        ExcelUtil<MeasuringPoint> util = new ExcelUtil<MeasuringPoint>(MeasuringPoint.class);
        util.exportExcel(response, list, "测点数据");
    }

    /**
     * 获取测点详细信息
     */
    @PreAuthorize("@ss.hasPermi('cmp:point:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(measuringPointService.selectMeasuringPointById(id));
    }

    /**
     * 新增测点
     */
    @PreAuthorize("@ss.hasPermi('cmp:point:add')")
    @Log(title = "测点", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MeasuringPoint measuringPoint) {
        return toAjax(measuringPointService.insertMeasuringPoint(measuringPoint));
    }

    /**
     * 修改测点
     */
    @PreAuthorize("@ss.hasPermi('cmp:point:edit')")
    @Log(title = "测点", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MeasuringPoint measuringPoint) {
        return toAjax(measuringPointService.updateMeasuringPoint(measuringPoint));
    }

    /**
     * 删除测点
     */
    @PreAuthorize("@ss.hasPermi('cmp:point:remove')")
    @Log(title = "测点", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(measuringPointService.deleteMeasuringPointByIds(ids));
    }

    /**
     * 获取根据结构物ID获取测点详细信息
     */
    @GetMapping(value = "/getMeasuringPointTree")
    public AjaxResult getMeasuringPointTree(MeasuringPoint measuringPoint) {
        return AjaxResult.success(measuringPointService.getMeasuringPointTree(measuringPoint));
    }

    /**
     * 获取根据结构物ID获取测点详细信息
     */
    @PostMapping(value = "/saveNodeInfo")
    public AjaxResult saveNodeInfo(@RequestBody MeasuringPoint measuringPoint) {
        return AjaxResult.success(measuringPointService.saveNodeInfo(measuringPoint));
    }

    /**
     * 获取根据结构物ID获取测点详细信息
     */
    @PostMapping(value = "/deleteNodeInfo")
    public AjaxResult deleteNodeInfo(@RequestBody MeasuringPoint measuringPoint) {
        return AjaxResult.success(measuringPointService.deleteNodeInfo(measuringPoint));
    }
}
