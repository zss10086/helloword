package cn.nsyr.hello.test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author javarice
 * @Mail: zss10086@126.com
 * @date:2017/4/10 下午3:50
 * @version: 1.0
 **/
public class TEst {
    public static void main(String[] args) {
        String[] str = new String[]{"a","b"};
        List<String> list = Arrays.asList(str);
//        list.add("c");

        str[0] = "c";
        for (String s : list) {
            System.out.println(s);
        }
        System.out.println(df.get().format(new Date()));
        synchronized (str) {
            if (str == null) synchronized (str) {
                if (str == null) {
                    str = new String[]{};
                }
            }
        }
    }


    private static final ThreadLocal<DateFormat> df = new ThreadLocal<DateFormat>(){
        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd");
        }
    };


}
