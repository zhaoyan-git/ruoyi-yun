package com.ruoyi.cmp.utils;

import com.ruoyi.cmp.domain.ConfigureOrder;
import com.ruoyi.cmp.domain.ConfigureSensor;
import com.ruoyi.cmp.mapper.ConfigureOrderMapper;
import com.ruoyi.cmp.mapper.ConfigureSensorMapper;
import com.ruoyi.cmp.netty.BootNettyChannelInboundHandlerAdapter;
import com.ruoyi.common.core.redis.RedisCache;
import io.netty.channel.Channel;
import io.netty.channel.ChannelId;

import java.util.List;

public class SendDataUtil {
    // 发送请求
    public static void sendRequest(Long dtuId) {
        ConfigureSensorMapper configureSensorMapper = (ConfigureSensorMapper) SpringContextUtil.getBean(ConfigureSensorMapper.class);
        RedisCache redisCache = (RedisCache) SpringContextUtil.getBean(RedisCache.class);

        if (null != dtuId) {
            // 根据网关ID获取从站地址列表
            ConfigureSensor configureSensorCondition = new ConfigureSensor();
            configureSensorCondition.setDtuId（dtuId）;
            List<ConfigureSensor> configureSensorList = configureSensorMapper.selectConfigureSensorList(configureSensorCondition);

            if (null != configureSensorList) {
                ConfigureOrderMapper ConfigureOrderMapper = (ConfigureOrderMapper) SpringContextUtil.getBean(ConfigureOrderMapper.class);
                for(ConfigureSensor configureSensor : configureSensorList){
                    ConfigureOrder configureOrderCondition = new ConfigureOrder();
                    configureOrderCondition.setOrderType("03");//查询03  下发06
                    configureOrderCondition.setSensorId(configureSensor.getId());
                    List<ConfigureOrder> configureOrderList = ConfigureOrderMapper.selectConfigureOrderList(configureOrderCondition);
                    if(null != configureOrderList){
                        ConfigureOrder configureOrder = configureOrderList.get(0);
                        for (Channel channel : BootNettyChannelInboundHandlerAdapter.users) {
                           ChannelId channelId = new CustomChannelId(redisCache.getCacheObject(dtuId.toString()));//获取与dtu绑定的通道id
                            if (channel.id().asLongText().equals(channelId.asLongText())) {//通道一致则发送报文
                                String str = "";

                                str += configureOrder.getSlaveAddress();//从站地址
                                str += configureOrder.getOrderType();
                                str += configureOrder.getAddressHi();
                                str += configureOrder.getAddressLo();
                                str += configureOrder.getAmountHi();
                                str += configureOrder.getAmountLo();
                                str += CRCUtils.getCRC(HexUtil.hexToByte(str));

                                channel.writeAndFlush(HexUtil.hexToByte(str));
                                System.out.println("          send Ok! content:" + str);
                            }
                        }
                    }
                }
            }
        }

    }
}
