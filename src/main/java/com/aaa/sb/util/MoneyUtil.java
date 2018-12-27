package com.aaa.sb.util;

/**
 * className:MoneyUtil
 * discription:
 * author:wuyanle
 * createTime:2018-12-26 18:26
 */
public class MoneyUtil {

    /**
     * 待还金额
     * @param biddeadline
     * @param bidrate
     * @param bidCurrentAmount
     * @return
     */
    public static double getBenXi(int biddeadline,double bidrate,int bidCurrentAmount){
        System.out.println(biddeadline);
        System.out.println(bidrate);
        System.out.println(bidCurrentAmount);
        //期数 利率 本金           bidrate 0.1
        int p = bidCurrentAmount;
        double r = bidrate;
        double r1 = bidrate+1;
        int n = biddeadline;
        //(1+0.1)的期数次幂
        double a1 = Math.pow(r1,n);
        System.out.println(a1);
        //保留三位小数
        //double a2 = Math.round(a1*1000)/1000;
       // System.out.println(a2);
        double a3 = (p*r*a1*n)/(a1-1);
        System.out.println(a3);
       // double a4 = Math.round(a3*1000)/1000;
        //System.out.println(a4);
        return a3;
    }

    /**
     * 总还款利息
     * @param biddeadline
     * @param bidrate
     * @param bidCurrentAmount
     * @return
     */
    public static Double getInterestTotle(int biddeadline, double bidrate,int bidCurrentAmount){

        //期数 利率 本金           bidrate 0.1
        int p = bidCurrentAmount;
        double r = bidrate;
        double r1 = bidrate+1;
        int n = biddeadline;
        //(1+0.1)的期数次幂
        double a1 = Math.pow(r1,n);
        //保留三位小数
       // double a2 = Math.round(a1*1000)/1000;
        double a3 = (p*r*a1*n)/(a1-1);
        //double a4 = Math.round(a3*1000)/1000;
        //double a5 = a4 - p;
        return a3;
    }


    /**
     * 每月应还数额1
     * @param biddeadline
     * @param bidrate
     * @param bidCurrentAmount
     * @return
     */
    public static double getLiXiMon(int biddeadline,double bidrate,int bidCurrentAmount){
        //期数 利率 本金           bidrate 0.1
        int p = bidCurrentAmount;
        double r = bidrate;
        double r1 = bidrate+1;
        int n = biddeadline;
        //(1+0.1)的期数次幂
        double a1 = Math.pow(r1,n);
        //保留三位小数
        //double a2 = Math.round(a1*1000)/1000;
        double a3 = ((p*r*a1*n)/(a1-1))/n;
        return a3;
    }



}
