package com.appxy.custom_dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;

import com.appxy.custom_dialog.databinding.DialogCustomBinding;
import com.appxy.custom_dialog.impl.NegativeButtonListener;
import com.appxy.custom_dialog.impl.NeutralButtonListener;
import com.appxy.custom_dialog.impl.PositiveButtonListener;
import com.appxy.custom_dialog.utils.AlertParams;
import com.appxy.custom_dialog.utils.Utils;

import androidx.annotation.ColorRes;
import androidx.annotation.StringRes;

public class CustomDialog {

    public static class Builder {

        private AlertParams alertParams;

        public Builder(Context context) {
            alertParams = new AlertParams();
            alertParams.mContext = context;
            alertParams.mHorizontalDistance = Utils.dip2px(context, 52);
        }

        public Builder setViewType(int viewType) {
            alertParams.mViewType = viewType;
            return this;
        }

        public Builder setTitle(CharSequence title) {
            alertParams.mTitle = title;
            return this;
        }

        public Builder setTitle(@StringRes int textId) {
            alertParams.mTitle = alertParams.mContext.getResources().getText(textId);
            return this;
        }

        public Builder setMessage(CharSequence message) {
            alertParams.mMessage = message;
            return this;
        }

        public Builder setMessage(@StringRes int textId) {
            alertParams.mMessage = alertParams.mContext.getResources().getText(textId);
            return this;
        }

        /*
        * 设置窗口与屏幕左右边缘的间距效果图默认为52dp
        * */
        public Builder setHorizontalDistance(int horizontalDistance) {
            alertParams.mHorizontalDistance = Utils.dip2px(alertParams.mContext, horizontalDistance);
            return this;
        }

        /**
         * @param text        btn文本
         * @param normalColor btn默认背景颜色
         * @param pressColor  btn按下时的背景颜色
         * @param listener    btn事件响应回调
         */
        public Builder setPositiveButton(CharSequence text, int normalColor, int pressColor, PositiveButtonListener listener) {
            alertParams.mPositiveButtonText = text;
            alertParams.mPositiveColor = normalColor;
            alertParams.mPositivePressColor = pressColor;
            alertParams.mPositiveButtonListener = listener;
            return this;
        }

        public Builder setPositiveButton(@StringRes int textId, @ColorRes int normal_id, @ColorRes int press_id, PositiveButtonListener listener) {
            alertParams.mPositiveButtonText = alertParams.mContext.getResources().getText(textId);
            alertParams.mPositiveColor = alertParams.mContext.getResources().getColor(normal_id);
            alertParams.mPositivePressColor = alertParams.mContext.getResources().getColor(press_id);
            alertParams.mPositiveButtonListener = listener;
            return this;
        }

        public Builder setPositiveButton(CharSequence text, int type, PositiveButtonListener listener) {
            alertParams.mPositiveButtonText = text;
            alertParams.mPositiveButtonBgColorType = type;
            alertParams.mPositiveButtonListener = listener;
            return this;
        }

        public Builder setPositiveButton(int textId, int type, PositiveButtonListener listener) {
            alertParams.mPositiveButtonText = alertParams.mContext.getResources().getText(textId);
            alertParams.mPositiveButtonBgColorType = type;
            alertParams.mPositiveButtonListener = listener;
            return this;
        }

        public Builder setPositiveButton(CharSequence text, PositiveButtonListener listener) {
            alertParams.mPositiveButtonText = text;
            alertParams.mPositiveButtonListener = listener;
            return this;
        }

        public Builder setPositiveButton(@StringRes int textId, PositiveButtonListener listener) {
            alertParams.mPositiveButtonText = alertParams.mContext.getResources().getText(textId);
            alertParams.mPositiveButtonListener = listener;
            return this;
        }

        public Builder setNegativeButton(CharSequence text, int borderColor, int pressColor, NegativeButtonListener listener) {
            alertParams.mNegativeButtonText = text;
            alertParams.mNegativeBorderColor = borderColor;
            alertParams.mNegativeBorderPressColor = pressColor;
            alertParams.mNegativeButtonListener = listener;
            return this;
        }

        public Builder setNegativeButton(@StringRes int textId, @ColorRes int border_id, @ColorRes int press_id, NegativeButtonListener listener) {
            alertParams.mNegativeButtonText = alertParams.mContext.getResources().getText(textId);
            alertParams.mNegativeBorderColor = alertParams.mContext.getResources().getColor(border_id);
            alertParams.mNegativeBorderPressColor = alertParams.mContext.getResources().getColor(press_id);
            alertParams.mNegativeButtonListener = listener;
            return this;
        }

        public Builder setNegativeButton(CharSequence text, NegativeButtonListener listener) {
            alertParams.mNegativeButtonText = text;
            alertParams.mNegativeButtonListener = listener;
            return this;
        }

        public Builder setNegativeButton(@StringRes int textId, NegativeButtonListener listener) {
            alertParams.mNegativeButtonText = alertParams.mContext.getResources().getText(textId);
            alertParams.mNegativeButtonListener = listener;
            return this;
        }

        public Builder setNeutralButton(CharSequence text, NeutralButtonListener listener) {
            alertParams.mNeutralButtonText = text;
            alertParams.mNeutralButtonListener = listener;
            return this;
        }

        public Builder setNeutralButton(@StringRes int textId, NeutralButtonListener listener) {
            alertParams.mNeutralButtonText = alertParams.mContext.getResources().getText(textId);
            alertParams.mNeutralButtonListener = listener;
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

        public AlertDialog create() {
            DialogCustomBinding binding = DialogCustomBinding.inflate(LayoutInflater.from(alertParams.mContext));
            View view = binding.getRoot();
            AlertDialog dialog = new AlertDialog.Builder(alertParams.mContext).setView(view).create();
            dialog.setCancelable(alertParams.mCancelable);
            dialog.setCanceledOnTouchOutside(alertParams.mCanceledOnTouchOutside);
            dialog.show();

            if (alertParams.mPositiveColor == -1) {
                if (alertParams.mPositiveButtonBgColorType == 0) {
                    alertParams.mPositiveColor = alertParams.mContext.getResources().getColor(R.color.blue_color);
                    alertParams.mPositivePressColor = alertParams.mContext.getResources().getColor(R.color.blue_press_color);
                } else {
                    alertParams.mPositiveColor = alertParams.mContext.getResources().getColor(R.color.red_color);
                    alertParams.mPositivePressColor = alertParams.mContext.getResources().getColor(R.color.red_press_color);
                }
            }
            if (alertParams.mNegativeBorderColor == -1) {
                alertParams.mNegativeBorderColor = alertParams.mContext.getResources().getColor(R.color.negative_btn_border_color);
                alertParams.mNegativeBorderPressColor = alertParams.mContext.getResources().getColor(R.color.negative_btn_border_press_color);
            }
            binding.negativeTv1.setBackground(Utils.setButtonStateDrawable(alertParams.mContext, 1, alertParams.mNegativeBorderPressColor, alertParams.mNegativeBorderColor, Utils.dip2px(alertParams.mContext, 8)));
            binding.negativeTv2.setBackground(Utils.setButtonStateDrawable(alertParams.mContext, 1, alertParams.mNegativeBorderPressColor, alertParams.mNegativeBorderColor, Utils.dip2px(alertParams.mContext, 8)));

            //自定义背景
            Window window = dialog.getWindow();
            window.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            int bgColor = alertParams.mContext.getResources().getColor(R.color.dialog_background_color);
            binding.rootLayout.setBackground(Utils.backgroundDrawable(bgColor, Utils.dip2px(alertParams.mContext, 1), Utils.dip2px(alertParams.mContext, 12)));
            //设置弹出窗与屏幕左右边缘的边距
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) binding.rootLayout.getLayoutParams();
            params.leftMargin = alertParams.mHorizontalDistance;
            params.rightMargin = alertParams.mHorizontalDistance;
            binding.rootLayout.requestLayout();

            if (!TextUtils.isEmpty(alertParams.mTitle)) {
                binding.titleTv.setVisibility(View.VISIBLE);
                binding.titleTv.setText(alertParams.mTitle);
            } else {
                binding.titleTv.setVisibility(View.GONE);
            }

            if (!TextUtils.isEmpty(alertParams.mMessage)) {
                binding.messageTv.setText(alertParams.mMessage);
            }

            if (alertParams.mViewType == 0) {
                binding.rootLayout.setPadding(Utils.dip2px(alertParams.mContext, 24), Utils.dip2px(alertParams.mContext, 32), Utils.dip2px(alertParams.mContext, 24), Utils.dip2px(alertParams.mContext, 32));
                binding.btnLayout2.setVisibility(View.GONE);
                binding.btnLayout1.setVisibility(View.VISIBLE);

                if (!TextUtils.isEmpty(alertParams.mNegativeButtonText)) {
                    binding.negativeTv1.setText(alertParams.mNegativeButtonText);
                }
                binding.negativeTv1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (alertParams.mNegativeButtonListener != null) {
                            alertParams.mNegativeButtonListener.onNegativeClick(dialog);
                        } else {
                            dialog.dismiss();
                        }
                    }
                });
                binding.positiveTv1.setText(alertParams.mPositiveButtonText);
                binding.positiveTv1.setBackground(Utils.setButtonStateDrawable(alertParams.mContext, 0, alertParams.mPositivePressColor, alertParams.mPositiveColor, Utils.dip2px(alertParams.mContext, 8)));
                binding.positiveTv1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (alertParams.mPositiveButtonListener != null) {
                            alertParams.mPositiveButtonListener.onPositiveClick(dialog);
                        } else {
                            dialog.dismiss();
                        }
                    }
                });
            } else {
                binding.rootLayout.setPadding(Utils.dip2px(alertParams.mContext, 16), Utils.dip2px(alertParams.mContext, 32), Utils.dip2px(alertParams.mContext, 16), Utils.dip2px(alertParams.mContext, 32));
                binding.btnLayout1.setVisibility(View.GONE);
                binding.btnLayout2.setVisibility(View.VISIBLE);

                binding.positiveTv2.setText(alertParams.mPositiveButtonText);
                binding.positiveTv2.setBackground(Utils.setButtonStateDrawable(alertParams.mContext, 0, alertParams.mPositivePressColor, alertParams.mPositiveColor, Utils.dip2px(alertParams.mContext, 8)));
                binding.positiveTv2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (alertParams.mPositiveButtonListener != null) {
                            alertParams.mPositiveButtonListener.onPositiveClick(dialog);
                        } else {
                            dialog.dismiss();
                        }
                    }
                });
                if (!TextUtils.isEmpty(alertParams.mNegativeButtonText)) {
                    binding.negativeTv2.setVisibility(View.VISIBLE);
                    binding.negativeTv2.setText(alertParams.mNegativeButtonText);
                } else {
                    binding.negativeTv2.setVisibility(View.GONE);
                }
                binding.negativeTv2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (alertParams.mNegativeButtonListener != null) {
                            alertParams.mNegativeButtonListener.onNegativeClick(dialog);
                        } else {
                            dialog.dismiss();
                        }
                    }
                });
                if (!TextUtils.isEmpty(alertParams.mNeutralButtonText)) {
                    binding.neutralTv2.setVisibility(View.VISIBLE);
                    binding.neutralTv2.setText(alertParams.mNeutralButtonText);
                } else {
                    binding.neutralTv2.setVisibility(View.GONE);
                }
                binding.neutralTv2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (alertParams.mNeutralButtonListener != null) {
                            alertParams.mNeutralButtonListener.onNeutralClick(dialog);
                        } else {
                            dialog.dismiss();
                        }
                    }
                });
            }

            return dialog;
        }

    }
}
