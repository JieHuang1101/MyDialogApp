package com.appxy.custom_dialog;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.appxy.custom_dialog.databinding.ToastCustomBinding;
import com.appxy.custom_dialog.utils.AlertParams;
import com.appxy.custom_dialog.utils.Utils;

import androidx.annotation.StringRes;
import androidx.core.content.res.ResourcesCompat;

public class CustomToast {

    public static class makeText {

        private AlertParams alertParams;

        public makeText(Context context, CharSequence message) {
            alertParams = new AlertParams();
            alertParams.mContext = context;
            alertParams.mMessage = message;
        }

        public makeText(Context context, CharSequence message, int duration) {
            alertParams = new AlertParams();
            alertParams.mContext = context;
            alertParams.mMessage = message;
            alertParams.mDuration = duration;
        }

        public makeText(Context context, @StringRes int textId) {
            alertParams = new AlertParams();
            alertParams.mContext = context;
            alertParams.mMessage = context.getResources().getText(textId);
        }

        public makeText(Context context, @StringRes int textId, int duration) {
            alertParams = new AlertParams();
            alertParams.mContext = context;
            alertParams.mMessage = context.getResources().getText(textId);
            alertParams.mDuration = duration;
        }

        public makeText setTitle(@StringRes int textId) {
            alertParams.mTitle = alertParams.mContext.getResources().getText(textId);
            return this;
        }

        public makeText setTitle(CharSequence title) {
            alertParams.mTitle = title;
            return this;
        }

        public makeText setIcon(int icon, int iconType) {
            alertParams.mIcon = icon;
            alertParams.mIconType = iconType;
            return this;
        }

        public makeText setFullWidth(boolean fullWidth) {
            alertParams.mFullWidth = fullWidth;
            return this;
        }

        public makeText setFullWidth(boolean fullWidth, int horizontalDistance) {
            alertParams.mFullWidth = fullWidth;
            alertParams.mHorizontalDistance = horizontalDistance;
            return this;
        }

        public makeText setGravityDisplay(int gravityDisplay) {
            alertParams.mGravityDisplay = gravityDisplay;
            return this;
        }

        public makeText setGravityDisplay(int gravityDisplay, int dpOffset) {
            alertParams.mGravityDisplay = gravityDisplay;
            alertParams.mDpOffset = dpOffset;
            return this;
        }

        public Toast show() {
            ToastCustomBinding binding = ToastCustomBinding.inflate(LayoutInflater.from(alertParams.mContext));
            View view = binding.getRoot();

            int bgColor = alertParams.mContext.getResources().getColor(R.color.toast_background_color);
            binding.toastLayout.setBackground(Utils.backgroundDrawable(bgColor, 1, Utils.dip2px(alertParams.mContext, 8)));

            if (TextUtils.isEmpty(alertParams.mTitle)) {
                binding.toastTitleTv.setVisibility(View.GONE);
            } else {
                binding.toastTitleTv.setVisibility(View.VISIBLE);
                binding.toastTitleTv.setText(alertParams.mTitle);
            }
            binding.toastMessageTv.setText(alertParams.mMessage);
            if (!TextUtils.isEmpty(alertParams.mTitle) && !TextUtils.isEmpty(alertParams.mMessage)) {
                LinearLayout.LayoutParams params0 = (LinearLayout.LayoutParams) binding.toastMessageTv.getLayoutParams();
                params0.topMargin = Utils.dip2px(alertParams.mContext, 8);
                binding.toastMessageTv.requestLayout();
            }

            //图标的显示位置
            if (alertParams.mIconType != 0 && alertParams.mIcon != -1) {
                Drawable drawable = ResourcesCompat.getDrawable(alertParams.mContext.getResources(), alertParams.mIcon, null);
                drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());

                if (alertParams.mIconType == 1) {
                    binding.toastMessageTv.setCompoundDrawables(drawable, null, null, null);
                    binding.toastMessageTv.setCompoundDrawablePadding(Utils.dip2px(alertParams.mContext, 16));
                } else if (alertParams.mIconType == 2) {
                    binding.toastTitleTv.setCompoundDrawables(drawable, null, null, null);
                    binding.toastTitleTv.setCompoundDrawablePadding(Utils.dip2px(alertParams.mContext, 8));
                } else if (alertParams.mIconType == 3) {
                    binding.toastRightIv.setVisibility(View.VISIBLE);
                    binding.toastRightIv.setImageDrawable(drawable);
                }
            }

            //设置显示区域的宽度、显示位置、偏移量
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) binding.toastLayout.getLayoutParams();
            if (alertParams.mFullWidth) {
                params.width = alertParams.mContext.getResources().getDisplayMetrics().widthPixels - Utils.dip2px(alertParams.mContext, alertParams.mHorizontalDistance * 2);
                params.leftMargin = Utils.dip2px(alertParams.mContext, alertParams.mHorizontalDistance);
                params.rightMargin = Utils.dip2px(alertParams.mContext, alertParams.mHorizontalDistance);

                binding.toastLayout.setPadding(Utils.dip2px(alertParams.mContext, 16), Utils.dip2px(alertParams.mContext, 14), Utils.dip2px(alertParams.mContext, 16), Utils.dip2px(alertParams.mContext, 14));
            } else {
                binding.toastLayout.setPadding(Utils.dip2px(alertParams.mContext, 16), Utils.dip2px(alertParams.mContext, 8), Utils.dip2px(alertParams.mContext, 16), Utils.dip2px(alertParams.mContext, 8));
            }
            if (alertParams.mGravityDisplay == Gravity.TOP) {//顶部显示
                binding.bottomEmptyView.setVisibility(View.VISIBLE);
                binding.topEmptyView.setVisibility(View.GONE);
                if (alertParams.mDpOffset != 0) {
                    params.topMargin = Utils.dip2px(alertParams.mContext, alertParams.mDpOffset);
                } else {
                    params.topMargin = Utils.dip2px(alertParams.mContext, 70);
                }
            } else if (alertParams.mGravityDisplay == Gravity.CENTER) {
                binding.topEmptyView.setVisibility(View.VISIBLE);
                binding.bottomEmptyView.setVisibility(View.VISIBLE);
            } else {//底部显示
                binding.topEmptyView.setVisibility(View.VISIBLE);
                binding.bottomEmptyView.setVisibility(View.GONE);
                if (alertParams.mDpOffset != 0) {
                    params.bottomMargin = Utils.dip2px(alertParams.mContext, alertParams.mDpOffset);
                } else {
                    params.bottomMargin = Utils.dip2px(alertParams.mContext, 88);
                }
            }
            binding.toastLayout.requestLayout();

            Toast toast = Toast.makeText(alertParams.mContext, "", alertParams.mDuration);
            toast.setView(view);
            toast.show();

            return toast;
        }
    }

}
