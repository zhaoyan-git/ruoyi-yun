package com.ruoyi.cmp.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.core.domain.entity.SysUser;
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
import com.ruoyi.cmp.domain.AlarmPolicy;
import com.ruoyi.cmp.service.IAlarmPolicyService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 告警策略配置Controller
 *
 * @author zy
 * @date 2022-11-24
 */
@RestController
@RequestMapping("/cmp/policy")
public class AlarmPolicyController extends BaseController {
    @Autowired
    private IAlarmPolicyService alarmPolicyService;

    /**
     * 查询告警策略配置列表
     */
    //@PreAuthorize("@ss.hasPermi('cmp:policy:list')")
    @GetMapping("/list")
    public TableDataInfo list(AlarmPolicy alarmPolicy) {
        startPage();
        List<AlarmPolicy> list = alarmPolicyService.selectAlarmPolicyList(alarmPolicy);
        return getDataTable(list);
    }

    /**
     * 导出告警策略配置列表
     */
    //@PreAuthorize("@ss.hasPermi('cmp:policy:export')")
    @Log(title = "告警策略配置", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, AlarmPolicy alarmPolicy) {
        List<AlarmPolicy> list = alarmPolicyService.selectAlarmPolicyList(alarmPolicy);
        ExcelUtil<AlarmPolicy> util = new ExcelUtil<AlarmPolicy>(AlarmPolicy.class);
        util.exportExcel(response, list, "告警策略配置数据");
    }

    /**
     * 获取告警策略配置详细信息
     */
    //@PreAuthorize("@ss.hasPermi('cmp:policy:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(alarmPolicyService.selectAlarmPolicyById(id));
    }

    /**
     * 新增告警策略配置
     */
    //@PreAuthorize("@ss.hasPermi('cmp:policy:add')")
    @Log(title = "告警策略配置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody AlarmPolicy alarmPolicy) {
        return toAjax(alarmPolicyService.insertAlarmPolicy(alarmPolicy));
    }

    /**
     * 修改告警策略配置
     */
    //@PreAuthorize("@ss.hasPermi('cmp:policy:edit')")
    @Log(title = "告警策略配置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody AlarmPolicy alarmPolicy) {
        return toAjax(alarmPolicyService.updateAlarmPolicy(alarmPolicy));
    }

    /**
     * 删除告警策略配置
     */
    //@PreAuthorize("@ss.hasPermi('cmp:policy:remove')")
    @Log(title = "告警策略配置", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(alarmPolicyService.deleteAlarmPolicyByIds(ids));
    }

    @PutMapping("/changeStatus")
    public AjaxResult changeStatus(@RequestBody AlarmPolicy alarmPolicy) {

        return toAjax(alarmPolicyService.updateStatus(alarmPolicy));
    }

}
