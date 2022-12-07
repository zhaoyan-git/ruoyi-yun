package com.ruoyi.cmp.controller;

import com.ruoyi.cmp.service.IStatisticsService;
import com.ruoyi.cmp.service.IStructureService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;


/**
 * 统计信息
 *
 * @author zy
 * @date 2022年9月21日16:08:27
 */
@RestController
@RequestMapping("/business/statistics")
public class StatisticsController extends BaseController {
    @Autowired
    private IStatisticsService statisticsService;

    @GetMapping
    public AjaxResult getInfo() throws ParseException {
        return AjaxResult.success(statisticsService.getStatisticsInfo());
    }

}
