package com.study.regexp;

import com.alibaba.fastjson.JSONArray;
import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * java正则表达式使用
 * https://blog.csdn.net/dansam/article/details/88840858
 * https://blog.csdn.net/m0_62618110/article/details/123704869
 */
public class regexpApp {
    public static void main( String[] args ) throws ParseException {
        //simpleRegexp();
        //System.out.println(RegexpToString("name =='$operationName'","one"));
        //System.out.println(RegexpToPhysical("体温:36.2 ℃     脉搏：78 次/分     呼吸 "));
        //System.out.println(RegexpToPhysical("T:36.2 ℃     P：78 次/分     R   20 次/分     BP 110/65  mmHg").toString());
        //System.out.println(RegexpCheckSpecial( "  "));
        System.out.println(RegexpGetDate("2021年9月1号开始")); //1923年12月31日,2021-2-1开始,23年12月31日开始


    }

    private static void simpleRegexp() {
        //编译正则表达式，这样子可以重用模式。
        Pattern p = Pattern.compile("a*b");
        // 用模式检查字符串
        Matcher m = p.matcher("aaaaab");
        //检查匹配结果
        boolean b1 = m.matches();
        System.out.println(b1);
        boolean b2 = Pattern.matches("a*b", "aaaaab");
        System.out.println(b2);
        boolean b3 = "aaaaab".matches("a*b");
        System.out.println(b3);
    }

    private static String RegexpToString(String msg, String param) {

        //name =='$operationName'
        String pattern = "'(?=\\$).*?'";

        // "(?<=@\\{).*?(?=})"
        // "\\$\\{.*?\\}"   // name =='${operationName}
        //String pattern = "\\$\\{.*?\\}";

        Matcher matcher = Pattern.compile(pattern).matcher(msg);
        while (matcher.find()) {
            System.out.println(matcher.group().toString());
            //msg = msg.replace("$"+ matcher.group(), param);
            msg = msg.replace(matcher.group(), param);
        }
        return msg;
    }

    private static Set<String> RegexpToPhysical(String msg) {

        Set<String> physicalSet = new HashSet<>();
        String pattern = "(体温|T|脉搏|P|呼吸|R|血压|BP)(:|：|\\s)*(\\d*\\.?\\d*/?\\d*)\\s*(℃|次/分|mmHg)";
        Matcher matcher = Pattern.compile(pattern).matcher(msg);
        while (matcher.find()) {
            System.out.println("groupCount:"+matcher.groupCount());
            System.out.println(matcher.group().toString());
            System.out.println(matcher.group(1).toString());
            System.out.println(matcher.group(2).toString());
            System.out.println(matcher.group(3).toString());
            System.out.println(matcher.group(4).toString());
            switch(matcher.group(1)){
                case "体温":
                case "T":
                    physicalSet.add("体温T");
                    break;
                case "脉搏":
                case "P":
                    physicalSet.add("脉搏P");
                    break;
                case "呼吸":
                case "R":
                    physicalSet.add("呼吸R");
                    break;
                case "血压":
                case "BP":
                    physicalSet.add("血压BP");
                    break;
            }
        }
        return physicalSet;
    }

    private static boolean RegexpCheckSpecial(String msg){
        boolean checkRusult = false;
        String pattern = "[\\u4E00-\\u9FA5A-Za-z0-9]";
        Matcher matcher = Pattern.compile(pattern).matcher(msg);
        while (matcher.find()) {
            System.out.println("groupCount:"+matcher.groupCount());
            System.out.println(matcher.group().toString());
            checkRusult = true;
        }
        return checkRusult;
    }
    private static String RegexpGetDate(String msg) throws ParseException {
        String dateStr = null;
        String pattern = "((\\d{2})年(\\d{1,2})月(\\d{1,2})(日|号))|\\d{4}-\\d{1,2}-\\d{1,2}";
        Matcher matcher = Pattern.compile(pattern).matcher(msg);
        while (matcher.find()) {
            System.out.println("groupCount:"+matcher.groupCount());
            System.out.println("matcherGroup:"+matcher.group().toString());
            if(matcher.group(1)!=null){ //**年**月**日格式
                int year = Integer.valueOf(matcher.group(2).toString());
                String yearString;
                if(year<50){
                    yearString = "20"+year;
                }else{
                    yearString = "19"+year;
                }
                dateStr = yearString+"-"+matcher.group(3).toString()+"-"+matcher.group(4).toString();
            }else{ //yyyy-mm-dd 格式
                dateStr = matcher.group().toString();
            }
            break;
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println(format.parse(dateStr));
        return dateStr;
    }

}

