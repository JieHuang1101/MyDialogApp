package com.appxy.dialog_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.appxy.custom_dialog.CustomDialog;
import com.appxy.custom_dialog.CustomToast;
import com.appxy.custom_dialog.LoadingDialog;
import com.appxy.custom_dialog.SettingBottomDialog;
import com.appxy.custom_dialog.impl.PositiveButtonListener;
import com.appxy.custom_dialog.RenewalDialog;
import com.appxy.dialog_app.databinding.ActivityMainBinding;
import com.appxy.dialog_app.other.Utils;
import com.appxy.dialog_app.other.XToast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private Activity activity;
    private boolean isWhiteTheme = true;
    private boolean showTitle;
    private boolean showMessage;
    private int changeType;
    private boolean isDefaultColor;
    private boolean showNegative;
    private boolean showNeutral;

    private int showIconType = 0;
    private boolean showFullWidth = true;
    private boolean showTop = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        activity = this;


        Calendar calendar = Calendar.getInstance();
        int twoDigitYear = calendar.get(Calendar.YEAR) % 100;
        Log.e("m_test","取出年份中的后两位: "+twoDigitYear);

        Calendar c1 = Calendar.getInstance();
        c1.set(2024, 7, 10, 9, 30);
        Log.i("m_test","=========> "+Utils.getDayOffsets(c1)+"  "+Utils.getSubDays(c1));

        /*
        binding.btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new BottomDialog.Builder(activity).setShowkeyboard(true)
                        .setHint("Hint Text")
                        .setTitle("Rename")
                        .setPositiveButton(null, new BottomDialog.SaveEditListener() {
                            @Override
                            public void savetext(Dialog dialog, String text) {
                                if (dialog != null) {
                                    dialog.dismiss();
                                }
                                Toast.makeText(activity, "点击事件回调:" + text, Toast.LENGTH_SHORT).show();
                            }
                        }).create();
            }
        });
        * */

        initShowState(binding);

        binding.btn2.setOnClickListener(v -> {
            CustomDialog.Builder builder = new CustomDialog.Builder(activity)
                    .setViewType(changeType)
                    .setHorizontalDistance(52)
                    .setMessage(R.string.custom_message1);
            if (showTitle) {
                builder.setTitle(R.string.custom_title1);
            }
            if (!isDefaultColor) {
                builder.setPositiveButton(R.string.delete, R.color.red_color, R.color.red_press_color, new PositiveButtonListener() {
                    @Override
                    public void onPositiveClick(Dialog dialog) {
                        if (dialog != null) {
                            dialog.dismiss();
                        }
                        Toast.makeText(activity, "点击Delete事件回调", Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                builder.setPositiveButton("OK", new PositiveButtonListener() {
                    @Override
                    public void onPositiveClick(Dialog dialog) {
                        if (dialog != null) {
                            dialog.dismiss();
                        }
                        Toast.makeText(activity, "点击OK事件回调", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            if (showNegative) {
                builder.setNegativeButton(R.string.cancel, null);
            }
            if (showNeutral) {
                builder.setNeutralButton(R.string.toast_action, null);
            }
            builder.create();
        });

        /*
        binding.btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CustomToastDialog.makeText(activity, false, R.string.toast_light_message, CustomToastDialog.LENGTH_SHORT)
                        .setTitle("页面弹出时无法点击其他按钮")
                        .setToastIcon(R.mipmap.qsq_dark_setting_support, 1)
                        .setAction(R.string.toast_action, new CustomToastDialog.ActionClickListener() {
                            @Override
                            public void ActionClick(Dialog dialog) {
                                dialog.dismiss();
                            }
                        })
                        .setToastFullWidth(false)
                        .create(false).show();
            }
        });
        * */

        binding.btn4.setOnClickListener(v -> {
            CustomToast.makeText makeText = new CustomToast.makeText(activity, R.string.toast_light_weight, Toast.LENGTH_LONG);
            if (showTitle) {
                makeText.setTitle(R.string.toast_title);
            }
            makeText.setIcon(R.mipmap.qsq_dark_setting_support, showIconType);
            makeText.setFullWidth(showFullWidth);
            makeText.setGravityDisplay(Gravity.CENTER);
            makeText.show();
        });

        binding.btn5.setOnClickListener(v -> {
            RenewalDialog.Builder builder = new RenewalDialog.Builder(activity)
                    .setTitle(R.string.renewal_title_text);
            builder.setNegativeButton("Love it", null);
            builder.setPositiveButton("Start Now", null);
            builder.create();
        });

        binding.btn6.setOnClickListener(v -> {
            builder = new LoadingDialog.Builder(activity, 1);
            if (showTitle) {
                SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
                builder.setTitle(sdf.format(new Date()));
            }
            if (showMessage) {
                 builder.setMessage(R.string.toast_title);
            }
            builder.setBackgroundTransparent(showTop);
            loading = builder.create();

            //模拟实时更新进度
            mHandler.sendEmptyMessageDelayed(0, 1000);
        });

        binding.btn7.setOnClickListener(v -> {
            SettingBottomDialog.Builder builder = new SettingBottomDialog.Builder(activity, changeType);
            if (changeType == 0) {
                builder.setTitle(R.string.page_size);
                String[] list = activity.getResources().getStringArray(R.array.setting_page_size);
                builder.setDataList(list, selectedIndex);
                builder.setListItemClick(new SettingBottomDialog.ItemClickCallback() {
                    @Override
                    public void onItemClick(Dialog dialog, int position) {
                        dialog.dismiss();
                        selectedIndex = position;
                        Toast.makeText(activity, "Item点击:" + position+" "+list[position], Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                builder.setTitle(R.string.theme);
                builder.setButtonText(R.string.dark_blue, R.string.white);
                builder.setThemeClick(new SettingBottomDialog.ThemeClickCallback() {
                    @Override
                    public void onThemeClick(Dialog dialog, boolean isWhite) {
                        dialog.dismiss();
                        if (isWhite) {
                            Utils.applyDayMode();
                        } else {
                            Utils.applyNightMode();
                        }
                    }
                });
            }
            builder.create();
        });

        binding.btn8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TestWebView.class);
                startActivity(intent);
                /*
                String url = "file:///android_asset/my_test.html";
                new RateNpsDialog.Builder(MainActivity.this, url).create().show();
                */
            }
        });
    }

    private int selectedIndex = 2;

    private Dialog loading;
    private LoadingDialog.Builder builder;

    //通过handler定时更新
    private final Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            if (msg.what == 0) {//update progress
                if (loading != null) {
                    SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
                    builder.setTitle(sdf.format(new Date()));

                    if (loading.isShowing()) {
                        mHandler.sendEmptyMessageDelayed(0, 1000);
                    }
                }
            }
            return false;
        }
    });

    /**
     * @param appName 应用包名
     * @param marketName GooglePlay的包名
     * */
    private void LaunchAppDetail(String appName, String marketName) {
        try {
            if (TextUtils.isEmpty(appName)) return;

            Uri uri = Uri.parse("market://details?id=" + appName);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            if (!TextUtils.isEmpty(marketName)) {
                intent.setPackage(marketName);
            }
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initShowState(ActivityMainBinding binding) {
        binding.themeCb.setOnCheckedChangeListener((buttonView, isChecked) -> {
            isWhiteTheme = isChecked;
            if (isChecked) {
                Utils.applyDayMode();
            } else {
                Utils.applyNightMode();
            }
        });

        binding.titleCb.setOnCheckedChangeListener((buttonView, isChecked) -> showTitle = isChecked);

        binding.typeCb.setOnCheckedChangeListener((buttonView, isChecked) -> {
            showMessage = isChecked;
            if (isChecked) {
                changeType = 1;
            } else {
                changeType = 0;
            }
        });

        binding.colorCb.setOnCheckedChangeListener((buttonView, isChecked) -> isDefaultColor = isChecked);

        binding.negativeCb.setOnCheckedChangeListener((buttonView, isChecked) -> showNegative = isChecked);

        binding.neutralCb.setOnCheckedChangeListener((buttonView, isChecked) -> showNeutral = isChecked);

        binding.radioBtn1.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                showIconType = 0;
            }
        });
        binding.radioBtn2.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                showIconType = 1;
            }
        });
        binding.radioBtn3.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                showIconType = 2;
            }
        });
        binding.radioBtn4.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                showIconType = 3;
            }
        });

        binding.fullWidthCb.setOnCheckedChangeListener((buttonView, isChecked) -> showFullWidth = isChecked);

        binding.topCb.setOnCheckedChangeListener((buttonView, isChecked) -> {
            showTop = isChecked;
            XToast.makeText(activity, "Test Toast", XToast.LENGTH_LONG).show();
        });
    }
}