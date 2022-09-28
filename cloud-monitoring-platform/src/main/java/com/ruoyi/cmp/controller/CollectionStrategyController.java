package com.ruoyi.cmp.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.cmp.domain.CollectionStrategy;
import com.ruoyi.cmp.service.ICollectionStrategyService;
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
 * 采集策略Controller
 *
 * @author zy
 * @date 2022-09-06
 */
@RestController
@RequestMapping("/business/strategy")
public class CollectionStrategyController extends BaseController {
    @Autowired
    private ICollectionStrategyService collectionStrategyService;

    /**
     * 查询采集策略列表
     */
    @PreAuthorize("@ss.hasPermi('business:strategy:list')")
    @GetMapping("/list")
    public TableDataInfo list(CollectionStrategy collectionStrategy) {
        startPage();
        List<CollectionStrategy> list = collectionStrategyService.selectCollectionStrategyList(collectionStrategy);
        return getDataTable(list);
    }

    /**
     * 导出采集策略列表
     */
    @PreAuthorize("@ss.hasPermi('business:strategy:export')")
    @Log(title = "采集策略", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CollectionStrategy collectionStrategy) {
        List<CollectionStrategy> list = collectionStrategyService.selectCollectionStrategyList(collectionStrategy);
        ExcelUtil<CollectionStrategy> util = new ExcelUtil<CollectionStrategy>(CollectionStrategy.class);
        util.exportExcel(response, list, "采集策略数据");
    }

    /**
     * 获取采集策略详细信息
     */
    @PreAuthorize("@ss.hasPermi('business:strategy:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(collectionStrategyService.selectCollectionStrategyById(id));
    }

    /**
     * 新增采集策略
     */
    @PreAuthorize("@ss.hasPermi('business:strategy:add')")
    @Log(title = "采集策略", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CollectionStrategy collectionStrategy) {
        return toAjax(collectionStrategyService.insertCollectionStrategy(collectionStrategy));
    }

    /**
     * 修改采集策略
     */
    @PreAuthorize("@ss.hasPermi('business:strategy:edit')")
    @Log(title = "采集策略", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CollectionStrategy collectionStrategy) {
        return toAjax(collectionStrategyService.updateCollectionStrategy(collectionStrategy));
    }

    /**
     * 删除采集策略
     */
    @PreAuthorize("@ss.hasPermi('business:strategy:remove')")
    @Log(title = "采集策略", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(collectionStrategyService.deleteCollectionStrategyByIds(ids));
    }

    /**
     * 获取采集策略详细信息
     */
    @GetMapping(value = "/getStrategyByBusinessId")
    public AjaxResult getStrategyByBusinessId(CollectionStrategy collectionStrategy) {
        CollectionStrategy collectionStrategyParam = new CollectionStrategy();
        collectionStrategyParam.setBusinessId(collectionStrategy.getBusinessId());
        collectionStrategyParam.setEquipmentId(collectionStrategy.getEquipmentId());
        List<CollectionStrategy> collectionStrategies = this.collectionStrategyService.selectCollectionStrategyList(collectionStrategyParam);
        CollectionStrategy collectionStrategy_return = new CollectionStrategy();
        if (collectionStrategies != null && collectionStrategies.size() > 0)
            collectionStrategy_return = collectionStrategies.get(0);
        return AjaxResult.success(collectionStrategy_return);
    }
}
