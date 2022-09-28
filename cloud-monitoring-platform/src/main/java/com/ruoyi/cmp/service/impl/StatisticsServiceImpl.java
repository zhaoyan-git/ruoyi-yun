package com.ruoyi.cmp.service.impl;


import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.cmp.domain.Equipment;
import com.ruoyi.cmp.domain.Project;
import com.ruoyi.cmp.domain.Statistics;
import com.ruoyi.cmp.domain.Structure;
import com.ruoyi.cmp.mapper.EquipmentMapper;
import com.ruoyi.cmp.mapper.ProjectMapper;
import com.ruoyi.cmp.mapper.StructureMapper;
import com.ruoyi.cmp.mapper.StructureMonitoringFactorsMapper;
import com.ruoyi.cmp.service.IStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 结构物管理Service业务层处理
 *
 * @author zy
 * @date 2022-08-31
 */
@Service
public class StatisticsServiceImpl implements IStatisticsService {
    @Autowired
    private StructureMapper structureMapper;
    @Autowired
    private EquipmentMapper equipmentMapper;
    @Autowired
    private ProjectMapper projectMapper;


    public Statistics getStatisticsInfo() {
        //获取总数
        Statistics statistics = new Statistics();
        statistics.setTotalNum("0");
        //发布项目数
        List<Project> projectList = projectMapper.selectProjectList(new Project());
        statistics.setProjectNum(projectList.size() + "");
        //结构为数量
        List<Structure> structureList = structureMapper.selectStructureList(new Structure());
        statistics.setsNum(structureList.size() + "");
        //设备总数
        List<Equipment> equipmentList = equipmentMapper.selectEquipmentList(new Equipment());
        statistics.seteNum(equipmentList.size() + "");
        //项目地图
        JSONArray projectArr = new JSONArray();
        for (Project project : projectList) {
            project.getName();
            JSONObject jo = new JSONObject();
            jo.put("name", project.getName());
            JSONArray ja = new JSONArray();
            ja.add(project.getPositionLng());
            ja.add(project.getPositionLat());
            jo.put("value", ja);
            projectArr.add(jo);
        }
        statistics.setProjectArr(projectArr);
        return statistics;
    }
}
