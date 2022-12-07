package com.ruoyi.cmp.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import com.alibaba.fastjson2.JSONArray;
import com.ruoyi.cmp.domain.MeasuringPoint;
import com.ruoyi.cmp.domain.Structure;
import com.ruoyi.cmp.mapper.MeasuringPointMapper;
import com.ruoyi.cmp.mapper.StructureMapper;
import com.ruoyi.common.core.domain.entity.SysDictData;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.mapper.SysDictDataMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.cmp.mapper.ThresholdConfigurationMapper;
import com.ruoyi.cmp.domain.ThresholdConfiguration;
import com.ruoyi.cmp.service.IThresholdConfigurationService;

/**
 * 阈值配置Service业务层处理
 *
 * @author zy
 * @date 2022-11-23
 */
@Service
public class ThresholdConfigurationServiceImpl implements IThresholdConfigurationService {
    @Autowired
    private ThresholdConfigurationMapper thresholdConfigurationMapper;

    @Autowired
    private SysDictDataMapper sysDictDataMapper;

    @Autowired
    private StructureMapper structureMapper;

    @Autowired
    private MeasuringPointMapper measuringPointMapper;

    /**
     * 查询阈值配置
     *
     * @param id 阈值配置主键
     * @return 阈值配置
     */
    @Override
    public ThresholdConfiguration selectThresholdConfigurationById(Long id) {
        ThresholdConfiguration thresholdConfiguration = thresholdConfigurationMapper.selectThresholdConfigurationById(id);
        ArrayList<String> checkList = new ArrayList<>();
        checkList.add(thresholdConfiguration.getMiname());
        thresholdConfiguration.setCheckList(checkList);
        LinkedHashMap<String, ArrayList<LinkedHashMap<String, String>>> dynamicValidateForm = new LinkedHashMap<>();
        ArrayList<LinkedHashMap<String, String>> al = new ArrayList<>();
        LinkedHashMap<String, String> map1 = new LinkedHashMap<>();
        map1.put("lev","1");
        map1.put("value",thresholdConfiguration.getLev1value());
        al.add(map1);
        if(StringUtils.isNotEmpty(thresholdConfiguration.getLev2value())){
            map1 = new LinkedHashMap<>();
            map1.put("lev","2");
            map1.put("value",thresholdConfiguration.getLev2value());
            al.add(map1);
        }
        if(StringUtils.isNotEmpty(thresholdConfiguration.getLev3value())){
            map1 = new LinkedHashMap<>();
            map1.put("lev","3");
            map1.put("value",thresholdConfiguration.getLev3value());
            al.add(map1);
        }
        dynamicValidateForm.put("domains",al);
        thresholdConfiguration.setDynamicValidateForm(dynamicValidateForm);
        if(thresholdConfiguration.getType().equals("2")){
            List<SysDictData> rateChangeType = this.sysDictDataMapper.selectDictDataByType("rate_change_type");
            JSONArray objects = JSONArray.parseArray(thresholdConfiguration.getRateType());
            ArrayList<String> alRateType = new ArrayList<>();
            for (int i = 0; i < objects.size(); i++) {
                String value = String.valueOf(objects.get(i));
                for (SysDictData sysDictData:rateChangeType) {
                    if(value.equals(sysDictData.getDictValue())){
                        alRateType.add(sysDictData.getDictLabel());
                    }
                }
            }
            thresholdConfiguration.setRateTypeList(alRateType);
        }
        return thresholdConfiguration;
    }

    /**
     * 查询阈值配置列表
     *
     * @param thresholdConfiguration 阈值配置
     * @return 阈值配置
     */
    @Override
    public List<ThresholdConfiguration> selectThresholdConfigurationList(ThresholdConfiguration thresholdConfiguration) {
        return thresholdConfigurationMapper.selectThresholdConfigurationList(thresholdConfiguration);
    }

    /**
     * 新增阈值配置
     *
     * @param thresholdConfiguration 阈值配置
     * @return 结果
     */
    @Override
    public int insertThresholdConfiguration(ThresholdConfiguration thresholdConfiguration) {
        if(thresholdConfiguration.getType().equals("1")){
            if (thresholdConfiguration.getMpid() == null) {
                throw new ServiceException("请选择测点！");
            }
            //测点信息
            MeasuringPoint measuringPoint = this.measuringPointMapper.selectMeasuringPointById(thresholdConfiguration.getMpid());
            thresholdConfiguration.setMpid(measuringPoint.getId());
            thresholdConfiguration.setMpname(measuringPoint.getName());
        }else if(thresholdConfiguration.getType().equals("2")){
            thresholdConfiguration.setRateType(thresholdConfiguration.getRateTypeList().toString());
        }
        ArrayList<String> checkList = thresholdConfiguration.getCheckList();
        //解析 配置阈值的监测项 checkList  目前只处理沉降,所以key可以写死,不然需要根据测点的配置关系查询字典表,时间比较急,先用着
        List<SysDictData> settlement = this.sysDictDataMapper.selectDictDataByType("settlement");
        List<SysDictData> jcys = this.sysDictDataMapper.selectDictDataByType("mf_threshold_configuration");
        String miid = "";
        String miname = "";
        for (String jcxVlaue : checkList) {
            for (SysDictData sysDictData : settlement) {
                if (jcxVlaue.equals(sysDictData.getDictLabel())) {
                    miid = sysDictData.getDictValue();
                    miname = jcxVlaue;
                    break;
                }
            }
        }
        thresholdConfiguration.setMiid(miid);
        thresholdConfiguration.setMiname(miname);
        //监测因素
        for (SysDictData sysDictData:jcys) {
            if(sysDictData.getDictValue().equals(thresholdConfiguration.getMfid())){
                thresholdConfiguration.setMfname(sysDictData.getDictLabel());
            }
        }
        //结构物信息
        Structure structure = this.structureMapper.selectStructureById(thresholdConfiguration.getSid());
        thresholdConfiguration.setSid(structure.getId());
        thresholdConfiguration.setSname(structure.getName());

        //级别阈值信息  正常应该根据选中的配置阈值监测来动态拉取,现在时间紧,先写死
        LinkedHashMap<String, ArrayList<LinkedHashMap<String, String>>> dynamicValidateForm = thresholdConfiguration.getDynamicValidateForm();
        //keys : value key lev
        for (LinkedHashMap<String, String> map : dynamicValidateForm.get("domains")) {
            if (map.get("lev").equals("1")) {
                thresholdConfiguration.setLev1value(map.get("value"));
            }
            if (map.get("lev").equals("2")) {
                thresholdConfiguration.setLev2value(map.get("value"));
            }
            if (map.get("lev").equals("3")) {
                thresholdConfiguration.setLev3value(map.get("value"));
            }
        }
        thresholdConfiguration.setCreateTime(DateUtils.getNowDate());
        thresholdConfiguration.setCreateBy(SecurityUtils.getLoginUser().getUserId() + "");
        return thresholdConfigurationMapper.insertThresholdConfiguration(thresholdConfiguration);
    }

    /**
     * 修改阈值配置
     *
     * @param thresholdConfiguration 阈值配置
     * @return 结果
     */
    @Override
    public int updateThresholdConfiguration(ThresholdConfiguration thresholdConfiguration) {
        if(thresholdConfiguration.getType().equals("1")){
            if (thresholdConfiguration.getMpid() == null) {
                throw new ServiceException("请选择测点！");
            }
            //测点信息
            MeasuringPoint measuringPoint = this.measuringPointMapper.selectMeasuringPointById(thresholdConfiguration.getMpid());
            thresholdConfiguration.setMpid(measuringPoint.getId());
            thresholdConfiguration.setMpname(measuringPoint.getName());
        }else if(thresholdConfiguration.getType().equals("2")){
            thresholdConfiguration.setRateType(thresholdConfiguration.getRateTypeList().toString());
        }
        ArrayList<String> checkList = thresholdConfiguration.getCheckList();
        //解析 配置阈值的监测项 checkList  目前只处理沉降,所以key可以写死,不然需要根据测点的配置关系查询字典表,时间比较急,先用着
        List<SysDictData> settlement = this.sysDictDataMapper.selectDictDataByType("settlement");
        String miid = "";
        String miname = "";
        for (String jcxVlaue : checkList) {
            for (SysDictData sysDictData : settlement) {
                if (jcxVlaue.equals(sysDictData.getDictLabel())) {
                    miid = sysDictData.getDictValue();
                    miname = jcxVlaue;
                    break;
                }
            }
        }
        thresholdConfiguration.setMiid(miid);
        thresholdConfiguration.setMiname(miname);
        //结构物信息
        Structure structure = this.structureMapper.selectStructureById(thresholdConfiguration.getSid());
        thresholdConfiguration.setSid(structure.getId());
        thresholdConfiguration.setSname(structure.getName());
        //级别阈值信息  正常应该根据选中的配置阈值监测来动态拉取,现在时间紧,先写死
        LinkedHashMap<String, ArrayList<LinkedHashMap<String, String>>> dynamicValidateForm = thresholdConfiguration.getDynamicValidateForm();
        thresholdConfiguration.setLev1value("");
        thresholdConfiguration.setLev2value("");
        thresholdConfiguration.setLev3value("");
        //keys : value key lev
        for (LinkedHashMap<String, String> map : dynamicValidateForm.get("domains")) {
            if (map.get("lev").equals("1")) {
                thresholdConfiguration.setLev1value(map.get("value"));
            }
            if (map.get("lev").equals("2")) {
                thresholdConfiguration.setLev2value(map.get("value"));
            }
            if (map.get("lev").equals("3")) {
                thresholdConfiguration.setLev3value(map.get("value"));
            }
        }
        thresholdConfiguration.setUpdateBy(SecurityUtils.getLoginUser().getUserId() + "");
        thresholdConfiguration.setUpdateTime(DateUtils.getNowDate());
        return thresholdConfigurationMapper.updateThresholdConfiguration(thresholdConfiguration);
    }

    /**
     * 批量删除阈值配置
     *
     * @param ids 需要删除的阈值配置主键
     * @return 结果
     */
    @Override
    public int deleteThresholdConfigurationByIds(Long[] ids) {
        return thresholdConfigurationMapper.deleteThresholdConfigurationByIds(ids);
    }

    /**
     * 删除阈值配置信息
     *
     * @param id 阈值配置主键
     * @return 结果
     */
    @Override
    public int deleteThresholdConfigurationById(Long id) {
        return thresholdConfigurationMapper.deleteThresholdConfigurationById(id);
    }
}
