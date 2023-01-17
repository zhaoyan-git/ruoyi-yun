package com.ruoyi.cmp.utils;

import com.ruoyi.cmp.domain.*;
import com.ruoyi.cmp.mapper.*;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @author zy
 * @date 2023年01月11日 9:33
 */
@Component
public class CreateAlarmRecordUtils {
    @Autowired
    private ProjectAlarmRecordMapper projectAlarmRecordMapper;
    @Autowired
    private ConfigureSensorDataMapper configureSensorDataMapper;
    @Autowired
    private ConfigureSensorMapper configureSensorMapper;
    @Autowired
    private StructureMapper structureMapper;
    @Autowired
    private StructureMonitoringFactorsMapper structureMonitoringFactorsMapper;
    @Autowired
    private MeasuringPointMapper measuringPointMapper;
    @Autowired
    private ThresholdConfigurationMapper thresholdConfigurationMapper;

    public static CreateAlarmRecordUtils createAlarmRecordUtils;

    @PostConstruct
    public void init() {
        createAlarmRecordUtils = this;
        createAlarmRecordUtils.projectAlarmRecordMapper = this.projectAlarmRecordMapper;
        createAlarmRecordUtils.configureSensorDataMapper = this.configureSensorDataMapper;
        createAlarmRecordUtils.configureSensorMapper = this.configureSensorMapper;
        createAlarmRecordUtils.structureMapper = this.structureMapper;
        createAlarmRecordUtils.structureMonitoringFactorsMapper = this.structureMonitoringFactorsMapper;
        createAlarmRecordUtils.thresholdConfigurationMapper = this.thresholdConfigurationMapper;
        createAlarmRecordUtils.measuringPointMapper = this.measuringPointMapper;
    }

    /**
     * 通过传感器创建告警记录
     * 告警记录列表是按照结构物进行展示的
     * 结构物 告警源 等级 告警信息 产生次数 产生时间 更新时间
     * 1.传感器-->设备-->测点-->结构物
     * 2.告警源都是[数据异常]
     * 3.等级根据阈值配置里面的测量值(位移)进行判断
     * 4.告警信息 展示: A测点 位移:100  温度 100
     * 5.产生次数: 比对阈值配置里面的数据,产生多少次异常直接累加
     */
    public static String createAlarmRecordForTemp(ConfigureSensorData configureSensorData) {
        String returnMsg = "新增成功";
        ConfigureSensor configureSensor = createAlarmRecordUtils.configureSensorMapper.selectConfigureSensorById(configureSensorData.getSensorId());
        if (configureSensor == null) {
            return "传感器对象为空";
        }
        Structure structure = createAlarmRecordUtils.structureMapper.selectStructureById(configureSensor.getBusinessId());
        if (structure == null) {
            return "结构物为空";
        }
        MeasuringPoint measuringPointParam = new MeasuringPoint();
        //结构物,设备 确定唯一测点信息
        measuringPointParam.setStructureId(structure.getId());
        measuringPointParam.setLev(3L);
        measuringPointParam.setEquipmentId(configureSensor.getEquipmentId() + "");
        List<MeasuringPoint> measuringPoints = createAlarmRecordUtils.measuringPointMapper.selectMeasuringPointList(measuringPointParam);
        MeasuringPoint measuringPoint = new MeasuringPoint();
        if (measuringPoints != null) {
            measuringPoint = measuringPoints.get(0);
        }
        if (measuringPoint == null) {
            return "测点为空";
        }
        ProjectAlarmRecord projectAlarmRecordParam = new ProjectAlarmRecord();
        //结构物,测点, 类型 确定唯一记录
        projectAlarmRecordParam.setSid(structure.getId());
        projectAlarmRecordParam.setMid(measuringPoint.getId());
        projectAlarmRecordParam.setType("1"); //1 温度
        int prodNum = 1;
        ProjectAlarmRecord projectAlarmRecord = new ProjectAlarmRecord();
        List<ProjectAlarmRecord> projectAlarmRecords = createAlarmRecordUtils.projectAlarmRecordMapper.selectProjectAlarmRecordList(projectAlarmRecordParam);
        if (projectAlarmRecords != null && projectAlarmRecords.size() > 0) {
            projectAlarmRecord = projectAlarmRecords.get(0);
        }
        if (projectAlarmRecord != null && projectAlarmRecord.getId() != null) {
            prodNum += Integer.parseInt(projectAlarmRecord.getProdNum());
        }
        projectAlarmRecord.setSid(structure.getId());
        projectAlarmRecord.setSname(structure.getName());
        projectAlarmRecord.setAlarmSource("数据异常");
        String lev = createAlarmRecordUtils.getLev(configureSensorData, structure, configureSensor, measuringPoint);
        projectAlarmRecord.setLev(Long.parseLong(lev));
        String alarmInfoMsg = "";
        alarmInfoMsg += "测点：" + measuringPoint.getMfName() + "  温度：" + configureSensorData.getTempData();
        projectAlarmRecord.setAlarmInfo(alarmInfoMsg);
        projectAlarmRecord.setIsDelete("0");
        projectAlarmRecord.setCreateTime(DateUtils.getNowDate());
        projectAlarmRecord.setProdDate(configureSensorData.getCreateTime());
        projectAlarmRecord.setProdNum(prodNum + "");
        projectAlarmRecord.setMid(measuringPoint.getId());
        projectAlarmRecord.setMname(measuringPoint.getName());
        projectAlarmRecord.setType("1");
        if (projectAlarmRecord != null && projectAlarmRecord.getId() != null) {
            createAlarmRecordUtils.projectAlarmRecordMapper.updateProjectAlarmRecord(projectAlarmRecord);
        } else {
            createAlarmRecordUtils.projectAlarmRecordMapper.insertProjectAlarmRecord(projectAlarmRecord);
        }

        return returnMsg;


    }

    /**
     * 创建 位移告警信息
     */
    public static String createAlarmRecordForDisplacement(ConfigureSensorData configureSensorData) {
        String returnMsg = "新增成功";
        ConfigureSensor configureSensor = createAlarmRecordUtils.configureSensorMapper.selectConfigureSensorById(configureSensorData.getSensorId());
        if (configureSensor == null) {
            return "传感器对象为空";
        }
        Structure structure = createAlarmRecordUtils.structureMapper.selectStructureById(configureSensor.getBusinessId());
        if (structure == null) {
            return "结构物为空";
        }
        //根据结构物获取结构物下,基准测点信息
        MeasuringPoint measuringPointParam = new MeasuringPoint();
        measuringPointParam.setStructureId(structure.getId());
        measuringPointParam.setLev(3L);
        createAlarmRecordUtils.measuringPointMapper.selectMeasuringPointList(measuringPointParam);
        List<MeasuringPoint> measuringPoints = createAlarmRecordUtils.measuringPointMapper.selectMeasuringPointList(measuringPointParam);
        MeasuringPoint basePoint = new MeasuringPoint();
        for (MeasuringPoint point : measuringPoints) {
            if (StringUtils.isNotEmpty(point.getIsBenchmark()) && point.getIsBenchmark().equals("Y")) {
                basePoint = point;
                break;
            }
        }
        //结构物,设备 确定唯一测点信息
        measuringPointParam.setEquipmentId(configureSensor.getEquipmentId() + "");
        measuringPoints = createAlarmRecordUtils.measuringPointMapper.selectMeasuringPointList(measuringPointParam);
        MeasuringPoint measuringPoint = new MeasuringPoint();
        if (measuringPoints != null) {
            measuringPoint = measuringPoints.get(0);
        }
        if (measuringPoint == null) {
            return "测点为空";
        }
        ProjectAlarmRecord projectAlarmRecordParam = new ProjectAlarmRecord();
        //结构物,测点, 类型 确定唯一记录
        projectAlarmRecordParam.setSid(structure.getId());
        projectAlarmRecordParam.setMid(measuringPoint.getId());
        projectAlarmRecordParam.setType("2"); //1 温度 2 位移
        int prodNum = 1;
        ProjectAlarmRecord projectAlarmRecord = new ProjectAlarmRecord();
        List<ProjectAlarmRecord> projectAlarmRecords = createAlarmRecordUtils.projectAlarmRecordMapper.selectProjectAlarmRecordList(projectAlarmRecordParam);
        if (projectAlarmRecords != null && projectAlarmRecords.size() > 0) {
            projectAlarmRecord = projectAlarmRecords.get(0);
        }
        if (projectAlarmRecord != null && projectAlarmRecord.getId() != null) {
            prodNum += Integer.parseInt(projectAlarmRecord.getProdNum());

        }
        projectAlarmRecord.setSid(structure.getId());
        projectAlarmRecord.setSname(structure.getName());
        projectAlarmRecord.setAlarmSource("数据异常");
        String driftData = configureSensorData.getDriftData(); //位移值.比较差值
        if (basePoint != null && basePoint.getBenchmarkValue() != null) { //基准点,位移值
            Double v = Double.parseDouble(driftData) - basePoint.getBenchmarkValue();
            configureSensorData.setDriftData(v + "");
        }

        String lev = createAlarmRecordUtils.getLev(configureSensorData, structure, configureSensor, measuringPoint);
        projectAlarmRecord.setLev(Long.parseLong(lev));
        String alarmInfoMsg = "";
        alarmInfoMsg += "测点：" + measuringPoint.getMfName() + "  沉降位移值：" + configureSensorData.getDipSensorTemp() + " 倾角Y轴数据：" + configureSensorData.getDipX() +
                " 倾角Y轴数据：" + configureSensorData.getDipY() + " 倾角Z轴数据：" + configureSensorData.getDipZ() + "  ";
        projectAlarmRecord.setAlarmInfo(alarmInfoMsg);
        projectAlarmRecord.setIsDelete("0");
        projectAlarmRecord.setCreateTime(DateUtils.getNowDate());
        projectAlarmRecord.setProdDate(configureSensorData.getCreateTime());
        projectAlarmRecord.setProdNum(prodNum + "");
        projectAlarmRecord.setMid(measuringPoint.getId());
        projectAlarmRecord.setMname(measuringPoint.getName());
        projectAlarmRecord.setType("2");
        if (projectAlarmRecord != null && projectAlarmRecord.getId() != null) {
            createAlarmRecordUtils.projectAlarmRecordMapper.updateProjectAlarmRecord(projectAlarmRecord);
        } else {
            createAlarmRecordUtils.projectAlarmRecordMapper.insertProjectAlarmRecord(projectAlarmRecord);
        }

        return returnMsg;


    }

    /**
     * 阈值等级   测量值                 阈值配置
     * 一级      v<=100或 v>100        (-.-100);(100,+)
     * 二级      -100<v< -55.5         (-100,-55.5)
     * 三级      -55.5 <v<-20          (-55.5,-20)
     */
    private String getLev(ConfigureSensorData configureSensorData, Structure structure, ConfigureSensor configureSensor, MeasuringPoint measuringPoint) {
        String lev = "";
        //查询阈值配置;
        ThresholdConfiguration thresholdConfigurationParam = new ThresholdConfiguration();
        thresholdConfigurationParam.setSid(structure.getId());
        thresholdConfigurationParam.setType("1"); //测量值
        thresholdConfigurationParam.setMpid(measuringPoint.getId()); //测点id
        List<ThresholdConfiguration> thresholdConfigurationList = createAlarmRecordUtils.thresholdConfigurationMapper.selectThresholdConfigurationList(thresholdConfigurationParam);
        String driftData = configureSensorData.getDriftData(); //位移数据
        double v = Double.parseDouble(driftData);
        if (thresholdConfigurationList != null && thresholdConfigurationList.size() > 0) {
            for (ThresholdConfiguration thresholdConfiguration : thresholdConfigurationList) {
                String lev1value = thresholdConfiguration.getLev1value(); //如果为空用默认配置  v<=100或 v>100
                String lev2value = thresholdConfiguration.getLev2value(); // -100<v< -55.5
                String lev3value = thresholdConfiguration.getLev3value(); // -55.5 <v<-20
                String lev1Left = lev1value.split(";")[0].replace("(", "").replace(")", "").split(",")[1];
                String lev1Right = lev1value.split(";")[1].replace("(", "").replace(")", "").split(",")[0];
                double v1Left = Double.parseDouble(lev1Left);
                double v1Right = Double.parseDouble(lev1Right);
                String lev2Left = lev2value.replace("(", "").replace(")", "").split(",")[0];
                String lev2Right = lev2value.replace("(", "").replace(")", "").split(",")[1];
                double v2Left = Double.parseDouble(lev2Left);
                double v2Right = Double.parseDouble(lev2Right);
                String lev3Left = lev3value.replace("(", "").replace(")", "").split(",")[0];
                String lev3Right = lev3value.replace("(", "").replace(")", "").split(",")[1];
                double v3Left = Double.parseDouble(lev3Left);
                double v3Right = Double.parseDouble(lev3Right);
                if (v <= v1Left || v > v1Right) return "1";
                if (v > v2Left && v < v2Right) return "2";
                if (v > v3Left && v < v3Right) return "3";
            }
        } else {
            if (v <= 100 || v > 100) return "1";
            if (v > -100 && v < -55.5) return "2";
            if (v > -55.5 && v < -20) return "3";
        }
        return lev;
    }


}
