package com.appxy.custom_dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.appxy.custom_dialog.databinding.DialogLoadingBinding;
import com.appxy.custom_dialog.utils.AlertParams;
import com.appxy.custom_dialog.utils.Utils;

import androidx.annotation.StringRes;

public class LoadingDialog {

    public static class Builder {

        private AlertParams alertParams;

        public Builder(Context context, int type) {
            alertParams = new AlertParams();
            alertParams.mContext = context;
            alertParams.mViewType = type;
        }

        public Builder setBackgroundColor(int color) {
            alertParams.mBackgroundColor = color;
            return this;
        }

        public Builder setIcon(int id) {
            alertParams.mIcon = id;
            return this;
        }

        public Builder setTitle(CharSequence text) {
            alertParams.mTitle = text;
            if (binding != null) {
                binding.titleTv.setText(text);
            }
            return this;
        }

        public Builder setTitle(@StringRes int textId) {
            alertParams.mTitle = alertParams.mContext.getResources().getText(textId);
            if (binding != null) {
                binding.titleTv.setText(alertParams.mTitle);
            }
            return this;
        }

        public Builder setMessage(CharSequence text) {
            alertParams.mMessage = text;
            return this;
        }

        public Builder setMessage(@StringRes int textId) {
            alertParams.mMessage = alertParams.mContext.getResources().getText(textId);
            return this;
        }

        public Builder setBackgroundTransparent(boolean transparent) {
            alertParams.mBackgroundTransparent = transparent;
            return this;
        }

        /*
        * 点击手机返回按键是否允许对话框消失
        * */
        public Builder setCancelable(boolean cancelable) {
            alertParams.mCancelable = cancelable;
            return this;
        }

        /*
        * 点击对话框外部区域是否允许对话框消失
        * */
        public Builder setCanceledOnTouchOutside(boolean canceledOnTouchOutside) {
            alertParams.mCanceledOnTouchOutside = canceledOnTouchOutside;
            return this;
        }

        private DialogLoadingBinding binding;

        public Dialog create() {
            binding = DialogLoadingBinding.inflate(LayoutInflater.from(alertParams.mContext));
            View view = binding.getRoot();
            Dialog dialog;
            if (alertParams.mBackgroundTransparent) {
                dialog = new Dialog(alertParams.mContext, R.style.LoadingTransparentDialog);
            } else {
                dialog = new Dialog(alertParams.mContext);
            }
            dialog.setContentView(view);
            dialog.setCancelable(alertParams.mCancelable);
            dialog.setCanceledOnTouchOutside(alertParams.mCanceledOnTouchOutside);
            dialog.show();

            if (alertParams.mViewType == 0) {
                int resourceId = R.drawable.loading_icon_anim_sequence;
                binding.iconIv.setBackgroundResource(resourceId);
                AnimationDrawable animation = (AnimationDrawable) binding.iconIv.getBackground();
                animation.start();//开启动画
            } else {
                binding.cycleIv.setBackgroundResource(R.mipmap.loading_cycle);
                Animation animation = AnimationUtils.loadAnimation(alertParams.mContext, R.anim.loading_animation);
                binding.cycleIv.startAnimation(animation);
                binding.iconIv.setImageResource(R.drawable.icon_loading_recycle);
            }
            if (!TextUtils.isEmpty(alertParams.mTitle)) {
                binding.titleTv.setText(alertParams.mTitle);
                binding.titleTv.setVisibility(View.VISIBLE);
            } else {
                binding.titleTv.setVisibility(View.GONE);
            }

            if (!TextUtils.isEmpty(alertParams.mMessage)) {
                binding.msgTv.setText(alertParams.mMessage);
                binding.msgTv.setVisibility(View.VISIBLE);
            } else {
                binding.msgTv.setVisibility(View.GONE);
            }

            int radius = Utils.dip2px(alertParams.mContext, 16);
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) binding.iconLayout.getLayoutParams();
            LinearLayout.LayoutParams params1 = (LinearLayout.LayoutParams) binding.msgTv.getLayoutParams();
            LinearLayout.LayoutParams params2 = (LinearLayout.LayoutParams) binding.titleTv.getLayoutParams();
            if (!TextUtils.isEmpty(alertParams.mTitle) && !TextUtils.isEmpty(alertParams.mMessage)) {//icon + title + message
                params.topMargin = Utils.dip2px(alertParams.mContext, 16);
                params.bottomMargin = Utils.dip2px(alertParams.mContext, 10);

                params1.topMargin = Utils.dip2px(alertParams.mContext, 4);
                params1.bottomMargin = Utils.dip2px(alertParams.mContext, 16);
            } else if (TextUtils.isEmpty(alertParams.mTitle) && TextUtils.isEmpty(alertParams.mMessage)) {//icon
                radius = Utils.dip2px(alertParams.mContext, 6.48F);
            } else {
                if (alertParams.mViewType == 0) {
                    params.topMargin = Utils.dip2px(alertParams.mContext, 16);
                    params.bottomMargin = Utils.dip2px(alertParams.mContext, 10);
                } else {
                    params.topMargin = Utils.dip2px(alertParams.mContext, 19);
                    params.bottomMargin = Utils.dip2px(alertParams.mContext, 13);
                }

                if (!TextUtils.isEmpty(alertParams.mMessage)) {//icon + message
                    params1.bottomMargin = Utils.dip2px(alertParams.mContext, 16);
                } else {//icon + title
                    params2.bottomMargin = Utils.dip2px(alertParams.mContext, 16);
                }
            }
            binding.iconIv.requestLayout();
            binding.msgTv.requestLayout();
            binding.titleTv.requestLayout();

            //自定义背景
            Window window = dialog.getWindow();
            window.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            int bgColor = alertParams.mContext.getResources().getColor(R.color.dialog_background_color);
            if (alertParams.mBackgroundColor != -1) {
                bgColor = alertParams.mBackgroundColor;
            }
            binding.rootLayout.setBackground(Utils.backgroundDrawable(bgColor, 1, radius));

            return dialog;
        }
    }
}

