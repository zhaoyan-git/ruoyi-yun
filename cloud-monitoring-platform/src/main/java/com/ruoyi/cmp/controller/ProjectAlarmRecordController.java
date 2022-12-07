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
import com.ruoyi.cmp.domain.ProjectAlarmRecord;
import com.ruoyi.cmp.service.IProjectAlarmRecordService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 告警记录Controller
 *
 * @author zy
 * @date 2022-09-19
 */
@RestController
@RequestMapping("/cmp/record")
public class ProjectAlarmRecordController extends BaseController {
    @Autowired
    private IProjectAlarmRecordService projectAlarmRecordService;

    /**
     * 查询告警记录列表
     */
    @PreAuthorize("@ss.hasPermi('cmp:record:list')")
    @GetMapping("/list")
    public TableDataInfo list(ProjectAlarmRecord projectAlarmRecord) {
        startPage();
        List<ProjectAlarmRecord> list = projectAlarmRecordService.selectProjectAlarmRecordList(projectAlarmRecord);
        return getDataTable(list);
    }

    /**
     * 导出告警记录列表
     */
    @PreAuthorize("@ss.hasPermi('cmp:record:export')")
    @Log(title = "告警记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ProjectAlarmRecord projectAlarmRecord) {
        List<ProjectAlarmRecord> list = projectAlarmRecordService.selectProjectAlarmRecordList(projectAlarmRecord);
        ExcelUtil<ProjectAlarmRecord> util = new ExcelUtil<ProjectAlarmRecord>(ProjectAlarmRecord.class);
        util.exportExcel(response, list, "告警记录数据");
    }

    /**
     * 获取告警记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('cmp:record:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(projectAlarmRecordService.selectProjectAlarmRecordById(id));
    }

    /**
     * 新增告警记录
     */
    @PreAuthorize("@ss.hasPermi('cmp:record:add')")
    @Log(title = "告警记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ProjectAlarmRecord projectAlarmRecord) {
        return toAjax(projectAlarmRecordService.insertProjectAlarmRecord(projectAlarmRecord));
    }

    /**
     * 修改告警记录
     */
    @PreAuthorize("@ss.hasPermi('cmp:record:edit')")
    @Log(title = "告警记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ProjectAlarmRecord projectAlarmRecord) {
        return toAjax(projectAlarmRecordService.updateProjectAlarmRecord(projectAlarmRecord));
    }

    /**
     * 删除告警记录
     */
    @PreAuthorize("@ss.hasPermi('cmp:record:remove')")
    @Log(title = "告警记录", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(projectAlarmRecordService.deleteProjectAlarmRecordByIds(ids));
    }
}
