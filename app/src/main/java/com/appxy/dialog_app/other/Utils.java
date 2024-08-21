package com.appxy.dialog_app.other;

import android.content.Context;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import androidx.appcompat.app.AppCompatDelegate;

public class Utils {

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int dip2sp(Context context, float dipValue) {
        int pxValue = dip2px(context, dipValue);//先将dip转px
        //再将px转sp
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }


    /**
     * 应用夜间模式
     */
    public static void applyNightMode() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
    }

    /**
     * 应用日间模式
     */
    public static void applyDayMode() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
    }

    public static long getDayOffsets(Calendar calendar) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        long subDays = 0;
        try {
            String page = format.format(calendar.getTime());
            Date pageDate = format.parse(page);
            String current = format.format(new Date());
            Date currentDate = format.parse(current);

            subDays = (currentDate.getTime() - pageDate.getTime()) / (24 * 3600 * 1000);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return Math.abs(subDays);
    }

    public static int getSubDays(Calendar pageCalendar) {
        int subDays = 0;//日期间的时间差
        GregorianCalendar nextDay = (GregorianCalendar) pageCalendar.clone();
        Calendar todayCalendar = Calendar.getInstance();
        while (nextDay.before(todayCalendar)) {//当下一天不在结束时间之前是终止循环
            //获得下一天日期
            nextDay.add(Calendar.DATE, 1);
            subDays++;
        }
        return subDays;
    }
}
