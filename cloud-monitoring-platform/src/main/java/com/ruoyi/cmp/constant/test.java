package com.ruoyi.cmp.constant;

import com.ruoyi.cmp.utils.HexUtil;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * @author zy
 * @date 2022年09月08日 16:14
 */
public class test {
    public static void main(String[] args){
//        List<String> list1 = new ArrayList<String>();
//
//        list1.add("2");
//        list1.add("3");
//
//
//        List<String> list2 = new ArrayList<String>();
//        list2.add("2");
//        list2.add("3");
//        list2.add("1");
//
//        // 交集
//        List<String> intersection = list1.stream().filter(item -> list2.contains(item)).collect(toList());
//        System.out.println("---交集 intersection---");
//        intersection.parallelStream().forEach(System.out :: println);
//
//        // 差集 (list1 - list2)
//        List<String> reduce1 = list1.stream().filter(item -> !list2.contains(item)).collect(toList());
//        System.out.println("---差集 reduce1 (list1 - list2)---");
//        reduce1.parallelStream().forEach(System.out :: println);
//
//        // 差集 (list2 - list1)
//        List<String> reduce2 = list2.stream().filter(item -> !list1.contains(item)).collect(toList());
//        System.out.println("---差集 reduce2 (list2 - list1)---");
//        reduce2.parallelStream().forEach(System.out :: println);
//
//        // 并集
//        List<String> listAll = list1.parallelStream().collect(toList());
//        List<String> listAll2 = list2.parallelStream().collect(toList());
//        listAll.addAll(listAll2);
//        System.out.println("---并集 listAll---");
//        listAll.parallelStream().forEachOrdered(System.out :: println);
//
//        // 去重并集
//        List<String> listAllDistinct = listAll.stream().distinct().collect(toList());
//        System.out.println("---得到去重并集 listAllDistinct---");
//        listAllDistinct.parallelStream().forEachOrdered(System.out :: println);
//
//        System.out.println("---原来的List1---");
//        list1.parallelStream().forEachOrdered(System.out :: println);
//        System.out.println("---原来的List2---");
//        list2.parallelStream().forEachOrdered(System.out :: println);
//        byte[] msgByte = "32343431393231000a2206ee00".getBytes();
       // 11 11 11 11 31 37 36 31 32 34 34 31 39 32 31 00 0A 22 06 EE 00
        String a = "111111113137363132343431393231000a2206ee00";

//        int[] HEART_BYTES = {0x11, 0x11, 0x11, 0x11, 0x31, 0x37, 0x36, 0x31,0x32,0x34,0x34,0x31,0x39,0x32,0x31,0x00,0x0A,0x22,0x06,0xEE,0x00};
//        byte[] MSG_ZHAN_TING_INVERTER_HEART = new byte[8];
//        int index = 0;
//        for (int i : HEART_BYTES) {
//            MSG_ZHAN_TING_INVERTER_HEART[index] = ByteUtil.intToByte(i);
//            index++;
//        }

        System.out.println(HexUtil.hexStringToByteArray(a).length);
    }
}
