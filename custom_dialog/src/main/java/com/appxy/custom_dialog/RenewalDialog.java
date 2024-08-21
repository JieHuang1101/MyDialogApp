package com.appxy.custom_dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.appxy.custom_dialog.databinding.DialogRenewBinding;
import com.appxy.custom_dialog.utils.Utils;

import androidx.annotation.StringRes;

public class RenewalDialog {

    public static class Builder {

        private Context mContext;
        private CharSequence mTitle;
        private CharSequence mPositiveButtonText;
        private PositiveButtonListener mPositiveButtonListener;
        private CharSequence mNegativeButtonText;
        private NegativeButtonListener mNegativeButtonListener;
        private boolean mCancelable = true;
        private boolean mCanceledOnTouchOutside = true;

        public Builder(Context context) {
            mContext = context;
        }

        public Builder setTitle(CharSequence text) {
            mTitle = text;
            return this;
        }

        public Builder setTitle(@StringRes int textId) {
            mTitle = mContext.getResources().getText(textId);
            return this;
        }

        public Builder setPositiveButton(CharSequence text, PositiveButtonListener listener) {
            mPositiveButtonText = text;
            mPositiveButtonListener = listener;
            return this;
        }

        public Builder setPositiveButton(@StringRes int textId, PositiveButtonListener listener) {
            mPositiveButtonText = mContext.getResources().getText(textId);
            mPositiveButtonListener = listener;
            return this;
        }

        public Builder setNegativeButton(CharSequence text, NegativeButtonListener listener) {
            mNegativeButtonText = text;
            mNegativeButtonListener = listener;
            return this;
        }

        public Builder setNegativeButton(@StringRes int textId, NegativeButtonListener listener) {
            mNegativeButtonText = mContext.getResources().getText(textId);
            mNegativeButtonListener = listener;
            return this;
        }

        /*
         * 点击手机返回按键是否允许对话框消失
         * */
        public Builder setCancelable(boolean cancelable) {
            mCancelable = cancelable;
            return this;
        }

        /*
         * 点击对话框外部区域是否允许对话框消失
         * */
        public Builder setCanceledOnTouchOutside(boolean canceledOnTouchOutside) {
            mCanceledOnTouchOutside = canceledOnTouchOutside;
            return this;
        }

        public AlertDialog create() {
            DialogRenewBinding binding = DialogRenewBinding.inflate(LayoutInflater.from(mContext));
            View view = binding.getRoot();
            AlertDialog dialog = new AlertDialog.Builder(mContext).setView(view).create();
            dialog.setCancelable(mCancelable);
            dialog.setCanceledOnTouchOutside(mCanceledOnTouchOutside);
            dialog.show();

            //通过比例计算尺寸
            int contentWidth = mContext.getResources().getDisplayMetrics().widthPixels - Utils.dip2px(mContext, 16 * 2) - Utils.dip2px(mContext, 8);
            int width_217 = (int) (contentWidth / 353F * 217F);
            int width_136 = contentWidth - width_217;
            int first_height = (int) (243F * width_217 / 217F);
            int second_height = (int) (118 * width_136 / 136F);
            //第一行
            LinearLayout.LayoutParams params1 = (LinearLayout.LayoutParams) binding.layout1.getLayoutParams();
            LinearLayout.LayoutParams params2 = (LinearLayout.LayoutParams) binding.layout2.getLayoutParams();
            params1.width = width_217;
            params1.height = first_height;
            params2.width = width_136;
            params2.height = first_height;
            binding.layout1.requestLayout();
            binding.layout2.requestLayout();
            //第二行
            LinearLayout.LayoutParams params3 = (LinearLayout.LayoutParams) binding.layout3.getLayoutParams();
            LinearLayout.LayoutParams params4 = (LinearLayout.LayoutParams) binding.layout4.getLayoutParams();
            params3.width = width_136;
            params3.height = second_height;
            params4.width = width_217;
            params4.height = second_height;
            binding.layout3.requestLayout();
            binding.layout4.requestLayout();

            if (Utils.isDarkMode()) {
                binding.oneTapIv.setBackgroundResource(R.mipmap.renew_icon_dark1);
                binding.speedierScansIv.setBackgroundResource(R.mipmap.renew_icon_dark4);
                binding.brandNewIv.setBackgroundResource(R.mipmap.renew_icon_dark5);
            } else {
                binding.oneTapIv.setBackgroundResource(R.mipmap.renew_icon1);
                binding.speedierScansIv.setBackgroundResource(R.mipmap.renew_icon4);
                binding.brandNewIv.setBackgroundResource(R.mipmap.renew_icon5);
            }

            if (!TextUtils.isEmpty(mNegativeButtonText)) {
                binding.negativeTv.setText(mNegativeButtonText);
            }
            int mNegativeBorderColor = mContext.getResources().getColor(R.color.negative_btn_border_color);
            int mNegativeBorderPressColor = mContext.getResources().getColor(R.color.negative_btn_border_press_color);
            binding.negativeTv.setBackground(Utils.setButtonStateDrawable(mContext, 1, mNegativeBorderPressColor, mNegativeBorderColor, Utils.dip2px(mContext, 8)));
            binding.negativeTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mNegativeButtonListener != null) {
                        mNegativeButtonListener.onNegativeClick(dialog);
                    } else {
                        dialog.dismiss();
                        LaunchAppDetail("com.appxy.tinyscanner");
                    }
                }
            });

            if (!TextUtils.isEmpty(mPositiveButtonText)) {
                binding.positiveTv.setText(mPositiveButtonText);
            }
            int mPositiveColor = mContext.getResources().getColor(R.color.blue_color);
            int mPositivePressColor = mContext.getResources().getColor(R.color.blue_press_color);
            binding.positiveTv.setBackground(Utils.setButtonStateDrawable(mContext, 0, mPositivePressColor, mPositiveColor, Utils.dip2px(mContext, 8)));
            binding.positiveTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mPositiveButtonListener != null) {
                        mPositiveButtonListener.onPositiveClick(dialog);
                    } else {
                        dialog.dismiss();
                    }
                }
            });

            //自定义背景
            Window window = dialog.getWindow();
            window.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            window.setGravity(Gravity.BOTTOM);
            int screenWidth = mContext.getResources().getDisplayMetrics().widthPixels;
            WindowManager.LayoutParams attributes = window.getAttributes();
            attributes.width = screenWidth;
            window.setAttributes(attributes);
            int radius = Utils.dip2px(mContext, 16);
            int bgColor = mContext.getResources().getColor(R.color.dialog_background_color);
            binding.rootLayout.setBackground(Utils.backgroundDrawable(bgColor, 0, radius));

            return dialog;
        }

        /**
         * 评分
         * @param appName 应用包名
         * */
        private void LaunchAppDetail(String appName) {
            try {
                if (TextUtils.isEmpty(appName)) return;

                Uri uri = Uri.parse("market://details?id=" + appName);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                intent.setPackage("com.android.vending");
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public interface PositiveButtonListener {
        void onPositiveClick(Dialog dialog);
    }

    public interface NegativeButtonListener {
        void onNegativeClick(Dialog dialog);
    }
}
