package com.appxy.dialog_app;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Handler;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.appxy.dialog_app.databinding.DialogToastCustomBinding;
import com.appxy.dialog_app.other.Utils;

import androidx.annotation.StringRes;
import androidx.core.content.res.ResourcesCompat;

public class CustomToastDialog {

    public static final int LENGTH_SHORT = 2000;
    public static final int LENGTH_LONG = 3500;

    public static class makeText {

        private Activity activity;
        private boolean isWhiteTheme;
        private CharSequence message;

        private CharSequence title;

        private int icon = -1;
        private int type = 0;//0-无icon 1-msg左侧 2-msg右侧 3-title左侧

        private CharSequence actionText;
        private ActionClickListener actionClickListener;

        private Dialog dialog;
        private Handler mHandler;
        private int mDuration;

        private boolean fullWidth;

        public makeText(Context context, boolean isWhiteTheme, String message, int duration) {
            this.activity = (Activity) context;
            this.isWhiteTheme = isWhiteTheme;
            this.message = message;
            this.mDuration = duration;

            mHandler = new Handler();
        }

        public makeText(Context context, boolean isWhiteTheme, @StringRes int textId, int duration) {
            this.activity = (Activity) context;
            this.isWhiteTheme = isWhiteTheme;
            this.message = context.getResources().getText(textId);
            this.mDuration = duration;

            mHandler = new Handler();
        }

        public makeText setTitle(@StringRes int textId) {
            this.title = activity.getResources().getText(textId);
            return this;
        }

        public makeText setTitle(String title) {
            this.title = title;
            return this;
        }

        public makeText setToastIcon(int icon, int type) {
            this.icon = icon;
            this.type = type;
            return this;
        }

        public makeText setAction(String actionText, ActionClickListener listener) {
            this.actionText = actionText;
            this.actionClickListener = listener;
            return this;
        }

        public makeText setAction(@StringRes int textId, ActionClickListener listener) {
            this.actionText = activity.getResources().getText(textId);
            this.actionClickListener = listener;
            return this;
        }

        public makeText setToastFullWidth(boolean fullWidth) {
            this.fullWidth = fullWidth;
            return this;
        }

        /*
         * @param isTopDisplay 是否顶部显示
         * */
        public makeText create(boolean isTopDisplay) {
            dialog = new Dialog(activity, R.style.ToastDialogStyle);
            DialogToastCustomBinding binding = DialogToastCustomBinding.inflate(activity.getLayoutInflater());
            dialog.setContentView(binding.getRoot());
            int themeColor = activity.getResources().getColor(R.color.toast_background_color);
            binding.toastLayout.setBackground(bgDrawable(themeColor));
            binding.toastMessageTv.setText(message);
            if (TextUtils.isEmpty(title)) {
                binding.toastTitleTv.setVisibility(View.GONE);
            } else {
                binding.toastTitleTv.setVisibility(View.VISIBLE);
                binding.toastTitleTv.setText(title);
            }
            if (type != 0 && icon != -1) {
                Drawable drawable = ResourcesCompat.getDrawable(activity.getResources(), icon, null);
                drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
                if (type == 1) {
                    binding.toastMessageTv.setCompoundDrawables(drawable, null, null, null);
                    binding.toastMessageTv.setCompoundDrawablePadding(Utils.dip2px(activity, 16));
                } else if (type == 2) {
                    binding.toastMessageTv.setCompoundDrawables(null, null, drawable, null);
                    binding.toastMessageTv.setCompoundDrawablePadding(Utils.dip2px(activity, 46));
                } else if (type == 3) {
                    binding.toastTitleTv.setCompoundDrawables(drawable, null, null, null);
                    binding.toastTitleTv.setCompoundDrawablePadding(Utils.dip2px(activity, 16));
                }
            }
            if (!TextUtils.isEmpty(title) && !TextUtils.isEmpty(message)) {
                LinearLayout.LayoutParams params0 = (LinearLayout.LayoutParams) binding.toastMessageTv.getLayoutParams();
                params0.topMargin = Utils.dip2px(activity, 8);
                binding.toastMessageTv.requestLayout();
            }
            if (!TextUtils.isEmpty(actionText)) {
                binding.toastActionTv.setVisibility(View.VISIBLE);
                binding.toastActionTv.setText(actionText);
            } else {
                binding.toastActionTv.setVisibility(View.GONE);
            }
            binding.toastActionTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (actionClickListener != null) {
                        actionClickListener.ActionClick(dialog);
                    }
                }
            });

            Window window = dialog.getWindow();
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            window.getDecorView().setPadding(0, 0, 0, 0);
            WindowManager.LayoutParams layoutParams = window.getAttributes();
            if (fullWidth) {
                layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
                layoutParams.horizontalMargin = Utils.dip2px(activity, 16);
                binding.toastLayout.setPadding(Utils.dip2px(activity, 16), Utils.dip2px(activity, 14), Utils.dip2px(activity, 16), Utils.dip2px(activity, 14));
            } else {
                binding.toastLayout.setPadding(Utils.dip2px(activity, 16), Utils.dip2px(activity, 8), Utils.dip2px(activity, 16), Utils.dip2px(activity, 8));
            }
            window.setAttributes(layoutParams);

            //设置显示位置
            FrameLayout.LayoutParams params0 = (FrameLayout.LayoutParams) binding.rootLayout.getLayoutParams();
            params0.height = activity.getResources().getDisplayMetrics().heightPixels;
            RelativeLayout.LayoutParams params1 = (RelativeLayout.LayoutParams) binding.toastLayout.getLayoutParams();
            if (isTopDisplay) {
                binding.rootLayout.setGravity(Gravity.TOP);
                params1.topMargin = Utils.dip2px(activity, 70);
            } else {
                binding.rootLayout.setGravity(Gravity.BOTTOM);
                params1.bottomMargin = Utils.dip2px(activity, 88);
            }
            params1.leftMargin = Utils.dip2px(activity, 16);
            params1.rightMargin = Utils.dip2px(activity, 16);
            binding.rootLayout.requestLayout();
            binding.toastLayout.requestLayout();

            return this;
        }

        public void show() {
            try {
                dialog.show();
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                    }
                }, mDuration);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private GradientDrawable bgDrawable(int theme_color) {
            int radius = Utils.dip2px(activity, 8);
            GradientDrawable back = new GradientDrawable();
            back.setShape(GradientDrawable.RECTANGLE);
            back.setColor(theme_color);
            back.setCornerRadii(new float[]{radius, radius, radius, radius, radius, radius, radius, radius});

            return back;
        }
    }

    public interface ActionClickListener {
        void ActionClick(Dialog dialog);
    }
}
