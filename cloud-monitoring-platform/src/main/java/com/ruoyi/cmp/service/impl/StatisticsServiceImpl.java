package com.ruoyi.cmp.service.impl;


import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.cmp.domain.*;
import com.ruoyi.cmp.mapper.*;
import com.ruoyi.cmp.service.IStatisticsService;
import com.ruoyi.common.core.domain.entity.SysDictData;
import com.ruoyi.common.utils.DictUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

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
    @Autowired
    private ProjectAlarmRecordMapper projectAlarmRecordMapper;

    public Statistics getStatisticsInfo() throws ParseException {
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
        //获取本周,周一到周日的 数据异常,DTU信息
        ProjectAlarmRecord projectAlarmRecordParam = new ProjectAlarmRecord();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
        Calendar cld = Calendar.getInstance(Locale.CHINA);
        cld.setFirstDayOfWeek(Calendar.MONDAY);//以周一为首日
        cld.setTimeInMillis(System.currentTimeMillis());//当前时间
        cld.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);//周一
        projectAlarmRecordParam.setCreateTime_start(cld.getTime());
        String day1 = df.format(cld.getTime());
        cld.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);//周二
        String day2 = df.format(cld.getTime());
        cld.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);//周3
        String day3 = df.format(cld.getTime());
        cld.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);//周4
        String day4 = df.format(cld.getTime());
        cld.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);//周5
        String day5 = df.format(cld.getTime());
        cld.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);//周6
        String day6 = df.format(cld.getTime());
        cld.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);//周7
        String day7 = df.format(cld.getTime());


        cld.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);//周日
        System.out.println(df.format(cld.getTime()));
        projectAlarmRecordParam.setCreateTime_end(cld.getTime());
        List<ProjectAlarmRecord> projectAlarmRecordList = this.projectAlarmRecordMapper.selectProjectAlarmRecordList(projectAlarmRecordParam);
        //先按照类型分组,然后按照日期分组
        HashMap<String,List<ProjectAlarmRecord>> hm = new HashMap<>();
        for (int i = 0; i < projectAlarmRecordList.size(); i++) {
            if(projectAlarmRecordList.get(i).getAlarmSource().equals("DTU异常") || projectAlarmRecordList.get(i).getAlarmSource().equals("数据异常")){
                if(hm.containsKey(projectAlarmRecordList.get(i).getAlarmSource())) {
                    hm.get(projectAlarmRecordList.get(i).getAlarmSource()).add(projectAlarmRecordList.get(i));
                }else{
                    List<ProjectAlarmRecord> al = new ArrayList<>();
                    al.add(projectAlarmRecordList.get(i));
                    hm.put(projectAlarmRecordList.get(i).getAlarmSource(),al);
                }
            }
        }
        //按照日期分组
        ArrayList<Integer> dtuDataList = new ArrayList<>();
        ArrayList<Integer> exceptionDataList = new ArrayList<>();
        for (Map.Entry<String, List<ProjectAlarmRecord>> entry : hm.entrySet()){
            if(entry.getKey().equals("DTU异常")){
                List<ProjectAlarmRecord> recordList = entry.getValue();
                //获取周一的数据; monday
                long l1 = recordList.stream().filter(item -> df.format(item.getProdDate()).contains(day1)).count();
                long l2 = recordList.stream().filter(item -> df.format(item.getProdDate()).contains(day2)).count();
                long l3 = recordList.stream().filter(item -> df.format(item.getProdDate()).contains(day3)).count();
                long l4 = recordList.stream().filter(item -> df.format(item.getProdDate()).contains(day4)).count();
                long l5 = recordList.stream().filter(item -> df.format(item.getProdDate()).contains(day5)).count();
                long l6 = recordList.stream().filter(item -> df.format(item.getProdDate()).contains(day6)).count();
                long l7 = recordList.stream().filter(item -> df.format(item.getProdDate()).contains(day7)).count();
                dtuDataList.add(Integer.parseInt(l1+""));
                dtuDataList.add(Integer.parseInt(l2+""));
                dtuDataList.add(Integer.parseInt(l3+""));
                dtuDataList.add(Integer.parseInt(l4+""));
                dtuDataList.add(Integer.parseInt(l5+""));
                dtuDataList.add(Integer.parseInt(l6+""));
                dtuDataList.add(Integer.parseInt(l7+""));
            }
            if(entry.getKey().equals("数据异常")){
                List<ProjectAlarmRecord> recordList = entry.getValue();
                //获取周一的数据; monday
                long l1 = recordList.stream().filter(item -> df.format(item.getProdDate()).contains(day1)).count();
                long l2 = recordList.stream().filter(item -> df.format(item.getProdDate()).contains(day2)).count();
                long l3 = recordList.stream().filter(item -> df.format(item.getProdDate()).contains(day3)).count();
                long l4 = recordList.stream().filter(item -> df.format(item.getProdDate()).contains(day4)).count();
                long l5 = recordList.stream().filter(item -> df.format(item.getProdDate()).contains(day5)).count();
                long l6 = recordList.stream().filter(item -> df.format(item.getProdDate()).contains(day6)).count();
                long l7 = recordList.stream().filter(item -> df.format(item.getProdDate()).contains(day7)).count();
                exceptionDataList.add(Integer.parseInt(l1+""));
                exceptionDataList.add(Integer.parseInt(l2+""));
                exceptionDataList.add(Integer.parseInt(l3+""));
                exceptionDataList.add(Integer.parseInt(l4+""));
                exceptionDataList.add(Integer.parseInt(l5+""));
                exceptionDataList.add(Integer.parseInt(l6+""));
                exceptionDataList.add(Integer.parseInt(l7+""));
            }
        }
        statistics.setDtuDataList(dtuDataList);
        statistics.setExceptionDataList(exceptionDataList);
        return statistics;
    }
}
