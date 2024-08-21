package com.appxy.custom_dialog.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.util.Log;

import androidx.annotation.ColorInt;
import androidx.annotation.IntRange;
import androidx.appcompat.app.AppCompatDelegate;

public class Utils {

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * @param bg_color 背景颜色
     * @param type 0-上部圆角 1-全圆角
     * @param radius 圆角大小
     */
    public static Drawable backgroundDrawable(int bg_color, int type, float radius) {

        GradientDrawable back = new GradientDrawable();
        back.setShape(GradientDrawable.RECTANGLE);
        back.setColor(bg_color);
        if (type == 0) {
            back.setCornerRadii(new float[]{radius, radius, radius, radius, 0, 0, 0, 0});
        } else {
            back.setCornerRadii(new float[]{radius, radius, radius, radius, radius, radius, radius, radius});
        }
        return back;
    }

    /**
     * @param border_color 边框颜色
     * @param stroke 边框粗细
     * @param radius 边框圆角
     * */
    public static Drawable borderColorDrawable(int border_color, int stroke, float radius) {

        GradientDrawable back = new GradientDrawable();
        back.setShape(GradientDrawable.RECTANGLE);
        back.setStroke(stroke, border_color);
        back.setCornerRadii(new float[]{radius, radius, radius, radius, radius, radius, radius, radius});

        return back;
    }

    /**
     * 设置button各种状态的颜色。
     *
     * @param type        0-背景 1-边框
     * @param pressColor  按下的颜色
     * @param normalColor 默认状态下颜色
     * @param radius      圆角的值
     */
    public static StateListDrawable setButtonStateDrawable(Context context, int type, int pressColor, int normalColor, float radius) {
        StateListDrawable stateListDrawable = new StateListDrawable();
        GradientDrawable buttonPress;
        if (type == 0) {
            buttonPress = (GradientDrawable) backgroundDrawable(pressColor, 1, radius);
        } else {
            buttonPress = (GradientDrawable) borderColorDrawable(pressColor, dip2px(context, 1), radius);
        }
        stateListDrawable.addState(new int[]{android.R.attr.state_pressed}, buttonPress);

        GradientDrawable buttonNormal;
        if (type == 0) {
            buttonNormal = (GradientDrawable) backgroundDrawable(normalColor, 1, radius);
        } else {
            buttonNormal = (GradientDrawable) borderColorDrawable(normalColor, dip2px(context, 1), radius);
        }
        stateListDrawable.addState(new int[]{}, buttonNormal);

        return stateListDrawable;
    }


    @ColorInt
    public static int setAlphaComponent(@ColorInt int color, @IntRange(from = 0L, to = 255L) int alpha) {
        if (alpha >= 0 && alpha <= 255) {
            return color & 16777215 | alpha << 24;
        } else {
            throw new IllegalArgumentException("alpha must be between 0 and 255.");
        }
    }

    /**
     * 判断App当前是否处于暗黑模式状态
     */
    public static boolean isDarkMode() {
        int ui = AppCompatDelegate.getDefaultNightMode();
        Log.i("m_test","==========> "+ui);
        if (ui == AppCompatDelegate.MODE_NIGHT_NO){
            return false;
        }else if (ui == AppCompatDelegate.MODE_NIGHT_YES){
            return true;
        }
        return false;
    }
}