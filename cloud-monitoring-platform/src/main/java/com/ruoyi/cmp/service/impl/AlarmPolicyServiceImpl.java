package com.ruoyi.cmp.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ruoyi.cmp.constant.BusinessConstant;
import com.ruoyi.cmp.domain.Project;
import com.ruoyi.cmp.domain.Structure;
import com.ruoyi.cmp.mapper.ProjectMapper;
import com.ruoyi.cmp.mapper.StructureMapper;
import com.ruoyi.common.core.domain.entity.SysDictData;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.mapper.SysDictDataMapper;
import com.ruoyi.system.mapper.SysUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.cmp.mapper.AlarmPolicyMapper;
import com.ruoyi.cmp.domain.AlarmPolicy;
import com.ruoyi.cmp.service.IAlarmPolicyService;

/**
 * 告警策略配置Service业务层处理
 *
 * @author zy
 * @date 2022-11-24
 */
@Service
public class AlarmPolicyServiceImpl implements IAlarmPolicyService {
    @Autowired
    private AlarmPolicyMapper alarmPolicyMapper;
    @Autowired
    private ProjectMapper projectMapper;
    @Autowired
    private StructureMapper structureMapper;
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private SysDictDataMapper sysDictDataMapper;

    /**
     * 查询告警策略配置
     *
     * @param id 告警策略配置主键
     * @return 告警策略配置
     */
    @Override
    public AlarmPolicy selectAlarmPolicyById(Long id) {
        return alarmPolicyMapper.selectAlarmPolicyById(id);
    }

    /**
     * 查询告警策略配置列表
     *
     * @param alarmPolicy 告警策略配置
     * @return 告警策略配置
     */
    @Override
    public List<AlarmPolicy> selectAlarmPolicyList(AlarmPolicy alarmPolicy) {
        return alarmPolicyMapper.selectAlarmPolicyList(alarmPolicy);
    }

    /**
     * 新增告警策略配置
     *
     * @param alarmPolicy 告警策略配置
     * @return 结果
     */
    @Override
    public int insertAlarmPolicy(AlarmPolicy alarmPolicy) {
        alarmPolicy.setCreateTime(DateUtils.getNowDate());
        alarmPolicy.setStatus(BusinessConstant.STATUS_ON);
        alarmPolicy.setCreateBy(SecurityUtils.getLoginUser().getUser().getUserId() + "");
        ArrayList<Integer> alarmTypeArr = (ArrayList<Integer>) alarmPolicy.getAlarmTypeArr();
        ArrayList<Integer> alarmPolicyReceiverpersonArr = (ArrayList<Integer>) alarmPolicy.getReceiverpersonArr();
        if (alarmPolicyReceiverpersonArr != null) {
            String userName = "";
            for (Integer id : alarmPolicyReceiverpersonArr) {
                SysUser sysUser = this.sysUserMapper.selectUserById(Long.parseLong(id + ""));
                if (StringUtils.isNotEmpty(userName)) {
                    userName += "," + sysUser.getNickName();
                } else {
                    userName = sysUser.getNickName();
                }
            }
            alarmPolicy.setReserve1(userName);
            alarmPolicy.setReceiverperson(alarmTypeArr.toString());
        }
        if (alarmTypeArr != null) {
            String person = "";
            List<SysDictData> reviveAlarmType = this.sysDictDataMapper.selectDictDataByType("revive_alarm_type");
            HashMap<String, String> hmAlarmTypeIdToName = new HashMap<>();
            for (SysDictData sysDictData : reviveAlarmType) {
                hmAlarmTypeIdToName.put(sysDictData.getDictValue(), sysDictData.getDictLabel());
            }

            for (Integer id : alarmPolicyReceiverpersonArr) {
                if (StringUtils.isNotEmpty(person)) {
                    person += "," + hmAlarmTypeIdToName.get(String.valueOf(id));
                } else {
                    person = hmAlarmTypeIdToName.get(String.valueOf(id));
                }
            }
            alarmPolicy.setReserve2(person);
            alarmPolicy.setAlarmtype(alarmPolicyReceiverpersonArr.toString());
        }

        Project project = this.projectMapper.selectProjectById(alarmPolicy.getPid());
        alarmPolicy.setPname(project.getName());
        Structure structure = this.structureMapper.selectStructureById(alarmPolicy.getSid());
        alarmPolicy.setSname(structure.getName());

        return alarmPolicyMapper.insertAlarmPolicy(alarmPolicy);
    }

    /**
     * 修改告警策略配置
     *
     * @param alarmPolicy 告警策略配置
     * @return 结果
     */
    @Override
    public int updateAlarmPolicy(AlarmPolicy alarmPolicy) {
        alarmPolicy.setUpdateTime(DateUtils.getNowDate());
        alarmPolicy.setUpdateBy(SecurityUtils.getLoginUser().getUserId() + "");
        ArrayList<Integer> alarmTypeArr = (ArrayList<Integer>) alarmPolicy.getAlarmTypeArr();
        ArrayList<Integer> alarmPolicyReceiverpersonArr = (ArrayList<Integer>) alarmPolicy.getReceiverpersonArr();
        if (alarmPolicyReceiverpersonArr != null) {
            String userName = "";
            for (Integer id : alarmPolicyReceiverpersonArr) {
                SysUser sysUser = this.sysUserMapper.selectUserById(Long.parseLong(id + ""));
                if (StringUtils.isNotEmpty(userName)) {
                    userName += "," + sysUser.getNickName();
                } else {
                    userName = sysUser.getNickName();
                }
            }
            alarmPolicy.setReserve1(userName);
            alarmPolicy.setAlarmtype(alarmTypeArr.toString());
        }
        if (alarmTypeArr != null) {
            String person = "";
            List<SysDictData> reviveAlarmType = this.sysDictDataMapper.selectDictDataByType("revive_alarm_type");
            HashMap<String, String> hmAlarmTypeIdToName = new HashMap<>();
            for (SysDictData sysDictData : reviveAlarmType) {
                hmAlarmTypeIdToName.put(sysDictData.getDictValue(), sysDictData.getDictLabel());
            }

            for (Integer id : alarmTypeArr) {
                if (StringUtils.isNotEmpty(person)) {
                    person += "," + hmAlarmTypeIdToName.get(String.valueOf(id));
                } else {
                    person = hmAlarmTypeIdToName.get(String.valueOf(id));
                }
            }
            alarmPolicy.setReserve2(person);
            alarmPolicy.setReceiverperson(alarmPolicyReceiverpersonArr.toString());
        }

        Project project = this.projectMapper.selectProjectById(alarmPolicy.getPid());
        alarmPolicy.setPname(project.getName());
        Structure structure = this.structureMapper.selectStructureById(alarmPolicy.getSid());
        alarmPolicy.setSname(structure.getName());
        return alarmPolicyMapper.updateAlarmPolicy(alarmPolicy);
    }

    @Override
    public int updateStatus(AlarmPolicy alarmPolicy) {
        return this.alarmPolicyMapper.updateAlarmPolicy(alarmPolicy);
    }

    /**
     * 批量删除告警策略配置
     *
     * @param ids 需要删除的告警策略配置主键
     * @return 结果
     */
    @Override
    public int deleteAlarmPolicyByIds(Long[] ids) {
        return alarmPolicyMapper.deleteAlarmPolicyByIds(ids);
    }

    /**
     * 删除告警策略配置信息
     *
     * @param id 告警策略配置主键
     * @return 结果
     */
    @Override
    public int deleteAlarmPolicyById(Long id) {
        return alarmPolicyMapper.deleteAlarmPolicyById(id);
    }
}
