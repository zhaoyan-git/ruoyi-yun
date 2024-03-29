package com.ruoyi.cmp.netty;


import com.ruoyi.cmp.domain.*;
import com.ruoyi.cmp.service.*;
import com.ruoyi.cmp.utils.CreateAlarmRecordUtils;
import com.ruoyi.cmp.utils.HexUtil;
import com.ruoyi.cmp.utils.SpringContextUtil;
import com.ruoyi.common.core.redis.RedisCache;
//import com.ruoyi.wxcxc.domain.*;
//import com.ruoyi.wxcxc.mapper.WxcxcDeviceSensorMapper;
//import com.ruoyi.wxcxc.mapper.WxcxcProjectDeviceSensorDataMapper;
//import com.ruoyi.wxcxc.service.IWxcxcDeviceGatewayService;
//import com.ruoyi.wxcxc.service.IWxcxcDeviceSensorService;
//import com.ruoyi.wxcxc.service.IWxcxcDeviceService;
//import com.ruoyi.wxcxc.util.*;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.math.BigInteger;
import java.net.InetSocketAddress;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class BootNettyChannelInboundHandlerAdapter extends ChannelInboundHandlerAdapter {
    // 用于记录和管理所有客户端的channel
    public static ChannelGroup users = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    @Autowired
    private IDeviceGatewayService deviceGatewayService;
    @Autowired
    private IConfigureSensorService configureSensorService;
    @Autowired
    private IConfigureOrderService configureOrderService;
    @Autowired
    private IConfigureSensorDataService configureSensorDataService;
    @Autowired
    private IConfigureDtuService configureDtuService;

//    private IDeviceService deviceService;

    /**
     * 从客户端收到新的数据时，这个方法会在收到消息时被调用
     *
     * @param ctx
     * @param msg
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception, IOException {
        // 读取数据
        ByteBuf buf = (ByteBuf) msg;
        String subside = buf.toString(CharsetUtil.UTF_8);
        byte[] msgByte = new byte[buf.readableBytes()];
        buf.readBytes(msgByte);
        System.out.println("收到：" + HexUtil.bytes2HexString(msgByte)); //字节转换成16进制
        System.out.println("收到：" + HexUtil.byteToHex(msgByte));
        // 获取IP
        RedisCache redisCache = (RedisCache) SpringContextUtil.getBean(RedisCache.class);

        InetSocketAddress insocket = (InetSocketAddress) ctx.channel().remoteAddress();
        String clientIp = insocket.getAddress().getHostAddress();
        System.out.println("clientIp收到：" + clientIp);

        // DTU 首次登录包
        // 长度21B
        if (21 == msgByte.length) {
            // DTU ID
            String dtuId = "";
            // 反转得到正确DTU ID
            dtuId += HexUtil.byteToHex(Arrays.copyOfRange(msgByte, 3, 4));
            dtuId += HexUtil.byteToHex(Arrays.copyOfRange(msgByte, 2, 3));
            dtuId += HexUtil.byteToHex(Arrays.copyOfRange(msgByte, 1, 2));
            dtuId += HexUtil.byteToHex(Arrays.copyOfRange(msgByte, 0, 1));
            // PHONE_NUMBER
            String phoneNumber = new String(Arrays.copyOfRange(msgByte, 4, 15), "gb2312");
            // BLANK
            String blank = HexUtil.byteToHex(Arrays.copyOfRange(msgByte, 15, 16));
            // IP_ADD
            String ipAdd = "";
            ipAdd += Integer.parseInt(HexUtil.byteToHex(Arrays.copyOfRange(msgByte, 16, 17)), 16) + ".";
            ipAdd += Integer.parseInt(HexUtil.byteToHex(Arrays.copyOfRange(msgByte, 17, 18)), 16) + ".";
            ipAdd += Integer.parseInt(HexUtil.byteToHex(Arrays.copyOfRange(msgByte, 18, 19)), 16) + ".";
            ipAdd += Integer.parseInt(HexUtil.byteToHex(Arrays.copyOfRange(msgByte, 19, 20)), 16);
            // ETX
            String etx = HexUtil.byteToHex(Arrays.copyOfRange(msgByte, 20, 21));

            System.out.println(dtuId);
            System.out.println(phoneNumber);
            System.out.println(ipAdd);

            if (!"00".equals(blank) || !"00".equals(etx)) {
                // 不符合结构
                ctx.close();
                return;
            }

            DeviceGateway deviceGatewayCondition = new DeviceGateway();
            deviceGatewayCondition.setDtuId(dtuId);
            deviceGatewayCondition.setSimNumber(phoneNumber);
            deviceGatewayService = (IDeviceGatewayService) SpringContextUtil.getBean(IDeviceGatewayService.class);
            List<DeviceGateway> deviceGatewayList = deviceGatewayService.selectDeviceGatewayList(deviceGatewayCondition);

            // 在系统中查询是否有该DTU
            if (null != deviceGatewayList) {
                DeviceGateway deviceGateway = null;

                for (DeviceGateway item : deviceGatewayList) {
                    deviceGateway = item;
                }

                if (null != deviceGateway) {
                    deviceGateway.setIp(clientIp);
                    deviceGateway.setOnlineFlag("1");
                    deviceGatewayService.updateDeviceGateway(deviceGateway);
                    System.out.println("channelActive:" + clientIp + ctx.name());
//                    users.add(ctx.channel());
                    return;
                } else {
                    // 不符合结构
                    ctx.close();
                    return;
                }
            } else {
                // 不符合结构
                ctx.close();
                return;
            }

        } else if (655 == msgByte.length) {
            System.out.println("read:" + HexUtil.byteToHex(msgByte));
            // 接收
            // 根据IP判断网关
            // 在系统中查询是否有该DTU
            DeviceGateway deviceGatewayCondition = new DeviceGateway();
            //ip
            deviceGatewayCondition.setIp(clientIp);
            //在线设备状态
            deviceGatewayCondition.setOnlineFlag("Y");
            deviceGatewayService = (IDeviceGatewayService) SpringContextUtil.getBean(IDeviceGatewayService.class);
            configureDtuService = (IConfigureDtuService) SpringContextUtil.getBean(IConfigureDtuService.class);
            List<DeviceGateway> deviceGatewayList = deviceGatewayService.selectDeviceGatewayList(deviceGatewayCondition);
            if (null != deviceGatewayList && deviceGatewayList.size() > 0) {
                for (DeviceGateway deviceGateway : deviceGatewayList) {
                    String slaveUnitId = HexUtil.byteToHex(Arrays.copyOfRange(msgByte, 0, 1));
                    String slaveCode = HexUtil.byteToHex(Arrays.copyOfRange(msgByte, 1, 2));
                    String dataLength = HexUtil.byteToHex(Arrays.copyOfRange(msgByte, 2, 3));

                    System.out.println("地址：" + slaveUnitId);
                    System.out.println("类型：" + slaveCode);
                    System.out.println("长度：" + dataLength);

                    ConfigureSensorData configureSensorDataCondition = new ConfigureSensorData();
                    //通过网关--> DTU--> 设备 --> 传感器
                    ConfigureDtu dtu = configureDtuService.selectConfigureDtuById(Long.parseLong(deviceGateway.getDtuId()));
                    ConfigureSensor configureSensorCondition = new ConfigureSensor();
                    /*configureSensorCondition.setBusinessId(deviceGateway.getId());*/
                    configureSensorCondition.setNo(slaveUnitId);
                    configureSensorCondition.setEquipmentId(dtu.getEquipmentId());
                    configureSensorService = (IConfigureSensorService) SpringContextUtil.getBean(IConfigureSensorService.class);

                    List<ConfigureSensor> configureSensorList = configureSensorService.selectConfigureSensorList(configureSensorCondition);
                    for (ConfigureSensor configureSensor : configureSensorList) {
                        ConfigureOrder configureOrderCondition = new ConfigureOrder();
                        configureOrderCondition.setSensorId(configureSensor.getId());
                        configureOrderCondition.setOrderType(slaveCode);
                        configureOrderService = (IConfigureOrderService) SpringContextUtil.getBean(IConfigureOrderService.class);

                        List<ConfigureOrder> configureOrderList = configureOrderService.selectConfigureOrderList(configureOrderCondition);
                        if (null != configureOrderList && configureOrderList.size() > 0) {
                            ConfigureOrder configureOrder = configureOrderList.get(0);
//                            Integer address = Integer.parseInt(configureOrder.getAddressHi()+configureOrder.getAddressLo(), 16);
//                            Integer amount = Integer.parseInt(configureOrder.getAmountHi()+configureOrder.getAmountLo(), 16);
                            subside = subside.substring(439, 597);  //todo:不知道咋解析,演示先写上
                            subside = subside.replaceAll(" ", "");


                            BigInteger dipXInter = new BigInteger(subside.substring(6, 14), 16);
                            String dipX = Float.intBitsToFloat(dipXInter.intValue()) + "";

                            BigInteger dipYInter = new BigInteger(subside.substring(14, 22), 16);
                            String dipY = Float.intBitsToFloat(dipYInter.intValue()) + "";

                            BigInteger dipZInter = new BigInteger(subside.substring(22, 30), 16);
                            String dipZ = Float.intBitsToFloat(dipZInter.intValue()) + "";

                            BigInteger dipSensorTempInt = new BigInteger(subside.substring(30, 34), 16);
                            String dipSensorTemp = Float.intBitsToFloat(dipSensorTempInt.intValue()) + "";

                            BigInteger vibrationXInt = new BigInteger(subside.substring(38, 46), 16);
                            String vibrationX = Float.intBitsToFloat(vibrationXInt.intValue()) + "";

                            BigInteger vibrationYInt = new BigInteger(subside.substring(46, 54), 16);
                            String vibrationY = Float.intBitsToFloat(vibrationYInt.intValue()) + "";

                            BigInteger vibrationZInt = new BigInteger(subside.substring(54, 62), 16);
                            String vibrationZ = Float.intBitsToFloat(vibrationZInt.intValue()) + "";

                            BigInteger subsideDispValueInt = new BigInteger(subside.substring(94, 102), 16);
                            String subsideDispValue = Float.intBitsToFloat(subsideDispValueInt.intValue()) + "";
                            configureSensorDataCondition.setDriftData(subsideDispValue);
                            configureSensorDataCondition.setSensorId(configureSensor.getId());
                            configureSensorDataCondition.setCreateTime(new Date());
                            configureSensorDataCondition.setCreateBy("ceshi");
                            configureSensorDataCondition.setDipX(dipX);
                            configureSensorDataCondition.setDipY(dipY);
                            configureSensorDataCondition.setDipZ(dipZ);
                            configureSensorDataCondition.setDipSensorTemp(dipSensorTemp);
                            configureSensorDataCondition.setVibrationX(vibrationX);
                            configureSensorDataCondition.setVibrationY(vibrationY);
                            configureSensorDataCondition.setVibrationZ(vibrationZ);
                            configureSensorDataCondition.setSubsideDispValue(subsideDispValue);
                            configureSensorDataService = (IConfigureSensorDataService) SpringContextUtil.getBean(IConfigureSensorDataService.class);

                            configureSensorDataService.insertConfigureSensorData(configureSensorDataCondition);
                            try {
                                String alarmRecordForSensor = CreateAlarmRecordUtils.createAlarmRecordForDisplacement(configureSensorDataCondition);
                                System.out.println(alarmRecordForSensor);
                                ctx.close();
                            } catch (Exception e) {
                                System.out.println("新增位移告警记录失败" + e);
                                ctx.close();
                            }
                        } else {
                            // 不符合结构
                            ctx.close();
                            return;
                        }
                    }
                }
            }


        }
        // DTU 心跳包
        else if (1 == msgByte.length) {
            //收到心跳包更新心跳状态
            if ("fe".equals(HexUtil.byteToHex(Arrays.copyOfRange(msgByte, 0, 1)))) {
                IDeviceGatewayService deviceGatewayService = (IDeviceGatewayService) SpringContextUtil.getBean(IDeviceGatewayService.class);

                // 根据Ip获取DTU
                DeviceGateway deviceGatewayCondition = new DeviceGateway();
                deviceGatewayCondition.setIp(clientIp);
                List<DeviceGateway> deviceGatewayList = deviceGatewayService.selectDeviceGatewayList(deviceGatewayCondition);
                if (null != deviceGatewayList) {
                    DeviceGateway deviceGateway = null;

                    for (DeviceGateway item : deviceGatewayList) {
                        deviceGateway = item;
                    }

                    if (null != deviceGateway) {
                        deviceGateway.setOnlineFlag("1");
                        deviceGatewayService.updateDeviceGateway(deviceGateway);
                        //保存通道id key dtuid value 通道id
                        redisCache.setCacheObject(deviceGateway.getDtuId(),ctx.channel().id().asLongText());
                        return;
                    } else {
                        // 不符合结构
                        ctx.close();
                        return;
                    }
                } else {
                    // 不符合结构
                    ctx.close();
                    return;
                }

//                // 查询该DTU下连接的全部设备
//                WxcxcDevice wxcxcDeviceCondition = new WxcxcDevice();
//                wxcxcDeviceCondition.setGatewayId(wxcxcDeviceGateway.getId());
//                List<WxcxcDevice> wxcxcDeviceList = wxcxcDeviceService.selectWxcxcDeviceList(wxcxcDeviceCondition);
//
//                if (null != wxcxcDeviceList) {
//                    for (WxcxcDevice deviceItem : wxcxcDeviceList) {
//                        // 获取该设备全部传感器
//                        WxcxcDeviceSensor wxcxcDeviceSensorCondition = new WxcxcDeviceSensor();
//                        wxcxcDeviceSensorCondition.setDeviceId(deviceItem.getId());
//                        List<WxcxcDeviceSensor> wxcxcDeviceSensorList = wxcxcDeviceSensorService.selectWxcxcDeviceSensorList(wxcxcDeviceSensorCondition);
//
//                        if (null != wxcxcDeviceSensorList) {
//                            //待发送的指令
//                            ModbusRtuOrderList modbusRtuOrderList = new ModbusRtuOrderList();
//
//                            for (WxcxcDeviceSensor deviceSensorItem : wxcxcDeviceSensorList) {
//                                // 添加指令
//                                modbusRtuOrderList.addOrder(deviceSensorItem.getSlaveUnitId(), deviceSensorItem.getSlaveCode(),
//                                        deviceSensorItem.getSlaveAddressHi(), deviceSensorItem.getSlaveAddressLo(),
//                                        deviceSensorItem.getSlaveAmountHi(), deviceSensorItem.getSlaveAmountLo());
//
////                                String requestByte = "";
////                                // 设备地址
////                                requestByte += deviceSensorItem.getSlaveUnitId();
////                                // 功能码
////                                requestByte += deviceSensorItem.getSlaveCode();
////                                // 起始寄存器地址
////                                requestByte += deviceSensorItem.getSlaveAddressHi() + deviceSensorItem.getSlaveAddressLo();
////                                // 读取寄存器个数
////                                requestByte += deviceSensorItem.getSlaveAmountHi() + deviceSensorItem.getSlaveAmountLo();
////
////                                requestByte += CRCUtils.getCRC(HexUtil.hexToByte(requestByte));
//                            }
//
//                            for (ModbusRtuOrderDto item : modbusRtuOrderList.getModbusRtuOrderDtoList()) {
//                                System.out.println("send:" + item.toString());
//                                ctx.write(item.toString());
//                                ctx.flush();
//                            }
//                        }
//
//                        return;
//                    }
//                }
            }
        } else {
            System.out.println("read:" + HexUtil.byteToHex(msgByte));
            // 接收
            // 根据IP判断网关
            // 在系统中查询是否有该DTU
            DeviceGateway deviceGatewayCondition = new DeviceGateway();
            //ip
            deviceGatewayCondition.setIp(clientIp);
            //在线设备状态
            deviceGatewayCondition.setOnlineFlag("1");
            deviceGatewayService = (IDeviceGatewayService) SpringContextUtil.getBean(IDeviceGatewayService.class);
            configureDtuService = (IConfigureDtuService) SpringContextUtil.getBean(IConfigureDtuService.class);
            List<DeviceGateway> deviceGatewayList = deviceGatewayService.selectDeviceGatewayList(deviceGatewayCondition);

            if (null != deviceGatewayList && deviceGatewayList.size() > 0) {
                for (DeviceGateway deviceGateway : deviceGatewayList) {

                    String slaveUnitId = HexUtil.byteToHex(Arrays.copyOfRange(msgByte, 0, 1));
                    String slaveCode = HexUtil.byteToHex(Arrays.copyOfRange(msgByte, 1, 2));
                    String dataLength = HexUtil.byteToHex(Arrays.copyOfRange(msgByte, 2, 3));

                    System.out.println("地址：" + slaveUnitId);
                    System.out.println("类型：" + slaveCode);
                    System.out.println("长度：" + dataLength);

                    ConfigureSensorData configureSensorDataCondition = new ConfigureSensorData();
                    //通过网关--> DTU--> 设备 --> 传感器
//                    ConfigureDtu dtu = configureDtuService.selectConfigureDtuById(Long.parseLong(deviceGateway.getDtuId()));
                    ConfigureSensor configureSensorCondition = new ConfigureSensor();
                    /*configureSensorCondition.setBusinessId(deviceGateway.getId());*/
                    configureSensorCondition.setNo(slaveUnitId);
                    configureSensorCondition.setDtuId(Long.parseLong(deviceGateway.getDtuId()));
                    configureSensorService = (IConfigureSensorService) SpringContextUtil.getBean(IConfigureSensorService.class);

                    List<ConfigureSensor> configureSensorList = configureSensorService.selectConfigureSensorList(configureSensorCondition);
                    for (ConfigureSensor configureSensor : configureSensorList) {
                        ConfigureOrder configureOrderCondition = new ConfigureOrder();
                        configureOrderCondition.setSensorId(configureSensor.getId());
                        configureOrderCondition.setOrderType(slaveCode);
                        configureOrderService = (IConfigureOrderService) SpringContextUtil.getBean(IConfigureOrderService.class);

                        List<ConfigureOrder> configureOrderList = configureOrderService.selectConfigureOrderList(configureOrderCondition);
                        if (null != configureOrderList && configureOrderList.size() > 0) {
                            ConfigureOrder configureOrder = configureOrderList.get(0);
//                            Integer address = Integer.parseInt(configureOrder.getAddressHi()+configureOrder.getAddressLo(), 16);
//                            Integer amount = Integer.parseInt(configureOrder.getAmountHi()+configureOrder.getAmountLo(), 16);
                            String orgdata = HexUtil.byteToHex(msgByte);
                            String tmepdata = orgdata.substring(6, 14);
                            String driftdata = orgdata.substring(14, 22);

                            String tmep = String.valueOf(Float.intBitsToFloat(Integer.parseInt(tmepdata, 16)));
                            String drift = String.valueOf(Float.intBitsToFloat(Integer.parseInt(driftdata, 16)));

                            configureSensorDataCondition.setOriginalData(orgdata);
                            configureSensorDataCondition.setTempData(tmep);
                            configureSensorDataCondition.setDriftData(drift);
                            configureSensorDataCondition.setSensorId(configureSensor.getId());
                            configureSensorDataCondition.setCreateTime(new Date());
                            configureSensorDataCondition.setCreateBy("ceshi");
                            configureSensorDataService = (IConfigureSensorDataService) SpringContextUtil.getBean(IConfigureSensorDataService.class);

                            configureSensorDataService.insertConfigureSensorData(configureSensorDataCondition);
                            try {
                                String alarmRecordForSensor = CreateAlarmRecordUtils.createAlarmRecordForTemp(configureSensorDataCondition);
                                System.out.println(alarmRecordForSensor);
                                ctx.close();
                            } catch (Exception e) {
                                System.out.println("新增温度告警记录失败" + e);
                                ctx.close();
                            }
                        } else {
                            // 不符合结构
                            ctx.close();
                            return;
                        }
                    }
                }
            }


//
//            if (null != wxcxcDeviceGatewayList) {
//                WxcxcDeviceGateway wxcxcDeviceGateway = null;
//
//                for (WxcxcDeviceGateway item : wxcxcDeviceGatewayList) {
//                    wxcxcDeviceGateway = item;
//                }
//
//                if (null != wxcxcDeviceGateway) {
//                    // 根据【设备地址】及【功能码】判断传感器
//                    String slaveUnitId = HexUtil.byteToHex(Arrays.copyOfRange(msgByte, 0, 1));
//                    String slaveCode = HexUtil.byteToHex(Arrays.copyOfRange(msgByte, 1, 2));
//                    String dataLength = HexUtil.byteToHex(Arrays.copyOfRange(msgByte, 2, 3));
//
//                    // ModBus 功能码0x01
//                    // ModBus 功能码0x02
//
//                    // ModBus 功能码0x03
//                    if ("03".equals(slaveCode)) {
//                        // 根据【设备地址】【DTU】获取传感器列表
//                        List<WxcxcDeviceSensor> wxcxcDeviceSensorList = wxcxcDeviceSensorMapper.selectWxcxcDeviceSensorByDtuAndSlaveUnitId(slaveUnitId, wxcxcDeviceGateway.getId());
//
//                        // 根据寄存器地址和数量获取信息
//                        if (null != wxcxcDeviceSensorList) {
//                            for (WxcxcDeviceSensor item : wxcxcDeviceSensorList) {
//                                Integer address = Integer.parseInt(item.getSlaveAddressHi() + item.getSlaveAddressLo(), 16);
//                                Integer amount = Integer.parseInt(item.getSlaveAmountHi() + item.getSlaveAmountLo(), 16);
//
//                                // 判断长度是否有误
//                                if (6 + Integer.valueOf(dataLength) < address + amount) {
//                                    System.out.println("数据异常");
//                                    continue;
//                                }
//
//                                String data = HexUtil.byteToHex(Arrays.copyOfRange(msgByte, 3 + (2 * address), 3 + (2 * (address + amount))));
//
//                                // 插入数据
//                                WxcxcProjectDeviceSensorData wxcxcProjectDeviceSensorData = new WxcxcProjectDeviceSensorData();
//
//                                wxcxcProjectDeviceSensorData.setSensorId(item.getId());
//                                wxcxcProjectDeviceSensorData.setOriginalData(data);
//                                wxcxcProjectDeviceSensorData.setCreateTime(new Date());
//                                try {
//                                    wxcxcProjectDeviceSensorData.setData(String.valueOf(Float.intBitsToFloat(Integer.parseInt(data, 16))));
//                                } catch (Exception e) {
//
//                                }
//
//                                wxcxcProjectDeviceSensorDataMapper.insertWxcxcProjectDeviceSensorData(item.getDeviceId(), wxcxcProjectDeviceSensorData);
//                            }
//                        }
//                    }
//
//                    // ModBus 功能码0x04
//                    // ModBus 功能码0x05
//                    // ModBus 功能码0x06
//
//
//                    return;
//                } else {
//                    // 不符合结构
////                    ctx.close();
//                    return;
//                }
//            } else {
//                // 不符合结构
////                ctx.close();
            return;
//            }


        }

    }

    /**
     * 从客户端收到新的数据、读取完成时调用
     *
     * @param ctx
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws IOException {
        ctx.flush();
    }

    /**
     * 当出现 Throwable 对象才会被调用，即当 Netty 由于 IO 错误或者处理器在处理事件时抛出的异常时
     *
     * @param ctx
     * @param cause
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws IOException {
        InetSocketAddress insocket = (InetSocketAddress) ctx.channel().remoteAddress();
        String clientIp = insocket.getAddress().getHostAddress();
        cause.printStackTrace();
        ctx.close();//抛出异常，断开与客户端的连接
        System.out.println("exceptionCaught:" + clientIp + ctx.name());
        users.remove(ctx.channel());

        IDeviceGatewayService deviceGatewayService = (IDeviceGatewayService) SpringContextUtil.getBean(IDeviceGatewayService.class);
        DeviceGateway deviceGatewayCondition = new DeviceGateway();
        deviceGatewayCondition.setIp(clientIp);
        List<DeviceGateway> deviceGatewayList = deviceGatewayService.selectDeviceGatewayList(deviceGatewayCondition);
        if (null != deviceGatewayList) {
            DeviceGateway deviceGateway = null;

            for (DeviceGateway item : deviceGatewayList) {
                deviceGateway = item;
            }

            if (null != deviceGateway) {
                deviceGateway.setOnlineFlag("0");
                deviceGatewayService.updateDeviceGateway(deviceGateway);
                //保存通道id key dtuid value 通道id



                // 将channelid储存到缓存
                RedisCache redisCache = (RedisCache) SpringContextUtil.getBean(RedisCache.class);
                redisCache.setCacheObject(deviceGateway.getDtuId().toString(), ctx.channel().id().asLongText());
            } else {

            }
        } else {

        }
//        // 根据IP获取DTU信息
//        IWxcxcDeviceGatewayService wxcxcDeviceGatewayService = (IWxcxcDeviceGatewayService) SpringContextUtil.getBean(IWxcxcDeviceGatewayService.class);
//        WxcxcDeviceGateway wxcxcDeviceGateway = getDeviceGatewayByIp(clientIp);
//        wxcxcDeviceGateway.setOnlineFlag("0");
//        wxcxcDeviceGatewayService.updateWxcxcDeviceGateway(wxcxcDeviceGateway);
//
//        System.out.println("exceptionCaught:" + clientIp + ctx.name());
//
//        users.remove(ctx.channel());
//        RedisCache redisCache = (RedisCache) SpringContextUtil.getBean(RedisCache.class);
//        redisCache.deleteObject(wxcxcDeviceGateway.getId().toString());
    }

    /**
     * 客户端与服务端第一次建立连接时 执行
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception, IOException {
        super.channelActive(ctx);
        ctx.channel().read();
        InetSocketAddress insocket = (InetSocketAddress) ctx.channel().remoteAddress();
        String clientIp = insocket.getAddress().getHostAddress();
        System.out.println("channelActive:" + clientIp + ctx.name());
        users.add(ctx.channel());
        IDeviceGatewayService deviceGatewayService = (IDeviceGatewayService) SpringContextUtil.getBean(IDeviceGatewayService.class);
        DeviceGateway deviceGatewayCondition = new DeviceGateway();
        deviceGatewayCondition.setIp(clientIp);
        List<DeviceGateway> deviceGatewayList = deviceGatewayService.selectDeviceGatewayList(deviceGatewayCondition);
        if (null != deviceGatewayList) {
            DeviceGateway deviceGateway = null;

            for (DeviceGateway item : deviceGatewayList) {
                deviceGateway = item;
            }
            if (null != deviceGateway) {
                deviceGateway.setOnlineFlag("1");
                deviceGatewayService.updateDeviceGateway(deviceGateway);
                //保存通道id key dtuid value 通道id

                //此处不能使用ctx.close()，否则客户端始终无法与服务端建立连接

                // 将channelid储存到缓存
                RedisCache redisCache = (RedisCache) SpringContextUtil.getBean(RedisCache.class);
                redisCache.setCacheObject(deviceGateway.getDtuId().toString(), ctx.channel().id().asLongText());
            } else {

            }
        } else {

        }

    }

    /**
     * 客户端与服务端 断连时 执行
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception, IOException {
        super.channelInactive(ctx);
        InetSocketAddress insocket = (InetSocketAddress) ctx.channel().remoteAddress();
        String clientIp = insocket.getAddress().getHostAddress();
        ctx.close(); //断开连接时，必须关闭，否则造成资源浪费，并发量很大情况下可能造成宕机
        System.out.println("channelInactive:" + clientIp + ctx.name());
        //此处不能使用ctx.close()，否则客户端始终无法与服务端建立连接
        users.remove(ctx.channel());
        IDeviceGatewayService deviceGatewayService = (IDeviceGatewayService) SpringContextUtil.getBean(IDeviceGatewayService.class);
        DeviceGateway deviceGatewayCondition = new DeviceGateway();
        deviceGatewayCondition.setIp(clientIp);
        List<DeviceGateway> deviceGatewayList = deviceGatewayService.selectDeviceGatewayList(deviceGatewayCondition);
        if (null != deviceGatewayList) {
            DeviceGateway deviceGateway = null;

            for (DeviceGateway item : deviceGatewayList) {
                deviceGateway = item;
            }

            if (null != deviceGateway) {
                deviceGateway.setOnlineFlag("0");
                deviceGatewayService.updateDeviceGateway(deviceGateway);
                //保存通道id key dtuid value 通道id



                // 将channelid储存到缓存
                RedisCache redisCache = (RedisCache) SpringContextUtil.getBean(RedisCache.class);
                redisCache.setCacheObject(deviceGateway.getDtuId().toString(), ctx.channel().id().asLongText());
            } else {

            }
        } else {

        }
//        // 根据IP获取DTU信息
//        IWxcxcDeviceGatewayService wxcxcDeviceGatewayService = (IWxcxcDeviceGatewayService) SpringContextUtil.getBean(IWxcxcDeviceGatewayService.class);
//        WxcxcDeviceGateway wxcxcDeviceGateway = getDeviceGatewayByIp(clientIp);
//        wxcxcDeviceGateway.setOnlineFlag("0");
//        wxcxcDeviceGatewayService.updateWxcxcDeviceGateway(wxcxcDeviceGateway);
//
//        users.remove(ctx.channel());
//        RedisCache redisCache = (RedisCache) SpringContextUtil.getBean(RedisCache.class);
//        redisCache.deleteObject(wxcxcDeviceGateway.getId().toString());
    }

    /**
     * 服务端当read超时, 会调用这个方法
     *
     * @param ctx
     * @param evt
     * @throws Exception
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception, IOException {
        super.userEventTriggered(ctx, evt);
        InetSocketAddress insocket = (InetSocketAddress) ctx.channel().remoteAddress();
        String clientIp = insocket.getAddress().getHostAddress();
        ctx.close();//超时时断开连接
        System.out.println("userEventTriggered:" + clientIp + ctx.name());
        users.remove(ctx.channel());

        IDeviceGatewayService deviceGatewayService = (IDeviceGatewayService) SpringContextUtil.getBean(IDeviceGatewayService.class);
        DeviceGateway deviceGatewayCondition = new DeviceGateway();
        deviceGatewayCondition.setIp(clientIp);
        List<DeviceGateway> deviceGatewayList = deviceGatewayService.selectDeviceGatewayList(deviceGatewayCondition);
        if (null != deviceGatewayList) {
            DeviceGateway deviceGateway = null;

            for (DeviceGateway item : deviceGatewayList) {
                deviceGateway = item;
            }

            if (null != deviceGateway) {
                deviceGateway.setOnlineFlag("0");
                deviceGatewayService.updateDeviceGateway(deviceGateway);
                //保存通道id key dtuid value 通道id



                // 将channelid储存到缓存
                RedisCache redisCache = (RedisCache) SpringContextUtil.getBean(RedisCache.class);
                redisCache.setCacheObject(deviceGateway.getDtuId().toString(), ctx.channel().id().asLongText());
            } else {

            }
        } else {

        }
//
//        // 根据IP获取DTU信息
//        IWxcxcDeviceGatewayService wxcxcDeviceGatewayService = (IWxcxcDeviceGatewayService) SpringContextUtil.getBean(IWxcxcDeviceGatewayService.class);
//        WxcxcDeviceGateway wxcxcDeviceGateway = getDeviceGatewayByIp(clientIp);
//        wxcxcDeviceGateway.setOnlineFlag("0");
//        wxcxcDeviceGatewayService.updateWxcxcDeviceGateway(wxcxcDeviceGateway);
//
//        users.remove(ctx.channel());
//        RedisCache redisCache = (RedisCache) SpringContextUtil.getBean(RedisCache.class);
//        redisCache.deleteObject(wxcxcDeviceGateway.getId().toString());
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelRegistered");
        users.remove(ctx.channel());
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelUnregistered");
        users.remove(ctx.channel());
    }

    @Override
    public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelWritabilityChanged");
        users.remove(ctx.channel());
    }

//    public WxcxcDeviceGateway getDeviceGatewayByIp(String ip) {
//        // 根据IP获取DTU信息
//        IWxcxcDeviceGatewayService wxcxcDeviceGatewayService = (IWxcxcDeviceGatewayService) SpringContextUtil.getBean(IWxcxcDeviceGatewayService.class);
//        WxcxcDeviceGateway wxcxcDeviceGatewayCondition = new WxcxcDeviceGateway();
//        wxcxcDeviceGatewayCondition.setIp(ip);
//        List<WxcxcDeviceGateway> wxcxcDeviceGatewayList = wxcxcDeviceGatewayService.selectWxcxcDeviceGatewayList(wxcxcDeviceGatewayCondition);
//
//        if (null != wxcxcDeviceGatewayList) {
//            for (WxcxcDeviceGateway item : wxcxcDeviceGatewayList) {
//                return item;
//            }
//        }
//
//        return null;
//    }
}
