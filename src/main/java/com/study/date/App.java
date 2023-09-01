package com.study.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class App {
    public static void main( String[] args ) throws ParseException {
        //common();
        //Duration.between(timeOne,timeTwo);
        //System.out.println(transformDate("1992-01-21"));
        //System.out.println(transformDate("1992-1-3"));
        System.out.println(transformDate("1992年1月3号"));
    }

    private static void common() throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date timeOne = df.parse("2023-04-08 20:20:20");
        Date timeTwo = df.parse("2023-04-07 21:00:00");
        System.out.println(timeOne);
        System.out.println(timeTwo);
        long time = timeOne.getTime() - timeTwo.getTime()  + df.parse("1970-01-01 00:00:00").getTime();
        System.out.println((timeOne.getTime() - timeTwo.getTime())/(1000*60*60*24));
        Date result = new Date(time);
        System.out.println(df.format(result));
    }

    /**
     * 判断字符串是否为合法的日期格式,是转化为日期
     * @param dateStr 待判断的字符串
     * @return
     */
    public static Date transformDate(String dateStr){
        //判断结果 默认为true
        boolean judgeresult=true;
        Date date = null;
        //1、首先使用SimpleDateFormat初步进行判断，过滤掉注入 yyyy-01-32 或yyyy-00-0x等格式
        //此处可根据实际需求进行调整，如需判断yyyy/MM/dd格式将参数改掉即可
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try{
            //增加强判断条件，否则 诸如2022-02-29也可判断出去
            format.setLenient(false);
            date =format.parse(dateStr);
        }catch(Exception e){
            judgeresult=false;
        }
        //由于上述方法只能验证正常的日期格式，像诸如 0001-01-01、11-01-01，10001-01-01等无法校验，此处再添加校验年费是否合法
        String yearStr=dateStr.split("-")[0];
        if(yearStr.startsWith("0")||yearStr.length()!=4){
            judgeresult=false;
            date = null;
        }
        return date;
    }

}
