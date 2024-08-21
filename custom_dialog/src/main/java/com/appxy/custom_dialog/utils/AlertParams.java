package com.appxy.custom_dialog.utils;

import android.content.Context;
import android.view.Display;
import android.view.Gravity;
import android.widget.Toast;

import com.appxy.custom_dialog.SettingBottomDialog;
import com.appxy.custom_dialog.impl.NegativeButtonListener;
import com.appxy.custom_dialog.impl.NeutralButtonListener;
import com.appxy.custom_dialog.impl.PositiveButtonListener;


/**
 * 存放可配置项的参数
 */
public class AlertParams {

    // basic
    public Context mContext;
    public CharSequence mTitle;
    public CharSequence mMessage;

    public int mViewType = 0;
    public int mIcon = -1;
    public boolean mBackgroundTransparent = false;
    public int mBackgroundColor = -1;
    // dialog
    public CharSequence mPositiveButtonText;
    public int mPositiveColor = -1;
    public int mPositivePressColor = -1;
    public PositiveButtonListener mPositiveButtonListener;
    public CharSequence mNegativeButtonText;
    public int mPositiveButtonBgColorType;//0-blue 1-red
    public int mNegativeBorderColor = -1;
    public int mNegativeBorderPressColor = -1;
    public NegativeButtonListener mNegativeButtonListener;
    public CharSequence mNeutralButtonText;
    public NeutralButtonListener mNeutralButtonListener;
    public boolean mCancelable = true;
    public boolean mCanceledOnTouchOutside = true;

    // toast
    public int mDuration = Toast.LENGTH_SHORT;
    public int mIconType = 0;//0-无icon 1-msg左侧 2-title左侧 3-msg右侧
    public boolean mFullWidth = false;//true:宽度满屏 false:宽度自适应文字
    public int mHorizontalDistance = 16;
    public int mGravityDisplay = Gravity.BOTTOM;//默认值:Gravity.Bottom、Gravity.CENTER、Gravity.TOP
    public int mDpOffset = 0;//偏移量 默认为70dp 和 88dp

    //setting
    public String[] mListData;
    public int mSelectedIndex;
    public SettingBottomDialog.ItemClickCallback mItemCallBack;
    public CharSequence mDarkBlueText;
    public CharSequence mWhiteText;
    public SettingBottomDialog.ThemeClickCallback mThemeCallBack;


    public AlertParams() { }
}