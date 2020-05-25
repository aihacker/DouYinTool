package com.yf.douyintool;

import java.util.Random;

public class DeviceUtil {
    public static String getMac(){
        StringBuilder sb=new StringBuilder();
        for (int i = 0; i <6; i++) {
            String s1=Integer.toHexString(new Random().nextInt(16));
            String s2=Integer.toHexString(new Random().nextInt(16));
            sb.append(s1+s2);
            if (i!=5){
                sb.append(":");
            }
        }
        return sb.toString();
    }


    public static String getRanHex(int i){
        StringBuilder sb=new StringBuilder();
        for (int j = 0; j <i ; j++) {
            String s=Integer.toHexString(new Random().nextInt(16));
            sb.append(s);
        }
        return sb.toString();
    }


    public static String getRanInt(int i){
        StringBuilder sb=new StringBuilder();
        for (int j = 0; j <i ; j++) {
            String s=Integer.toHexString(new Random().nextInt(10));
            sb.append(s);
        }
        return sb.toString();
    }




}
