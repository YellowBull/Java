package com.jmev.cn.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class GetNineOfMonth
{
    /**
     * 获取最近九个月的时间
     * @return
     */
    public static List<String> getMonth()
    {
        List<String> months = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        int maxCurrentMonthDay = 0;
        Calendar calendar5 = Calendar.getInstance();
        for (int i = 0; i < 9; i++)
        {
            maxCurrentMonthDay = calendar5.getActualMaximum(Calendar.DAY_OF_MONTH);
            calendar5.add(Calendar.DAY_OF_MONTH, -maxCurrentMonthDay);
            months.add(sdf.format(calendar5.getTime()) + "Z");
        }
        return months;
    }

    public static String getCurrentMonth()
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar5 = Calendar.getInstance();
        return sdf.format(calendar5.getTime()) + "Z";
    }
}
