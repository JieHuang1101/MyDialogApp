package com.appxy.dialog_app.other;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.appxy.dialog_app.R;
import com.appxy.dialog_app.databinding.DialogBottomRenameBinding;

import java.util.Timer;
import java.util.TimerTask;

public class BottomDialog {


    public static class Builder {

        BottomDialogParams params;

        public Builder(Context context) {
            params = new BottomDialogParams();
            params.context = (Activity) context;
        }

        public Builder setShowkeyboard(boolean showkeyboard) {
            params.showkeyboard = showkeyboard;
            return this;
        }

        public Builder setView(View view) {
            params.view = view;
            return this;
        }

        public Builder setPositiveButton(String text, final SaveEditListener listener) {
            params.mPositiveButtonText = text;
            params.saveEditListener = listener;
            return this;
        }

        public Builder setHint(String hint) {
            params.hint = hint;
            return this;
        }

        public Builder setTitle(String title) {
            params.title = title;
            return this;
        }

        public AlertDialog create() {

            View view;
            DialogBottomRenameBinding binding = null;
            if (params.view == null) {
                binding = DialogBottomRenameBinding.inflate(params.context.getLayoutInflater());
                view = binding.getRoot();
            } else {
                view = params.view;
            }
            AlertDialog dialog = new AlertDialog.Builder(params.context).setView(view)
                    .create();
            dialog.show();

            Window window = dialog.getWindow();
            window.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            window.setGravity(Gravity.BOTTOM);
            DisplayMetrics outMetrics = new DisplayMetrics();
            params.context.getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
            int screenWidth = outMetrics.widthPixels;
            WindowManager.LayoutParams attributes = window.getAttributes();
            attributes.width = screenWidth;
            window.setAttributes(attributes);

            if (binding != null) {
                binding.allLin.setBackground(backdrawable());
                binding.editbackRl.setBackground(editbackdrawable());

                if (!TextUtils.isEmpty(params.hint)) {
                    binding.edittext.setSelectAllOnFocus(true);
                    binding.edittext.setText(params.hint);
                }
                binding.edittext.requestFocus();
                if (!TextUtils.isEmpty(params.title)) {
                    binding.titleTv.setText(params.title);
                }

                DialogBottomRenameBinding finalBinding = binding;
                binding.doneTv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (params.saveEditListener != null) {
                            params.saveEditListener.savetext(dialog, finalBinding.edittext.getText().toString());
                        }
                    }
                });
                binding.cancelTv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            }

            if (params.showkeyboard) {
                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        InputMethodManager m = (InputMethodManager) params.context.getSystemService(Context.INPUT_METHOD_SERVICE);
                        m.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                    }
                }, 300);
            }

            return dialog;
        }

        private Drawable backdrawable() {
            int raduis = Utils.dip2px(params.context, 8);
            int backcolor = params.context.getResources().getColor(R.color.white);
            GradientDrawable back = new GradientDrawable();
            back.setShape(GradientDrawable.RECTANGLE);
            back.setColor(backcolor);
            back.setCornerRadii(new float[]{raduis, raduis, raduis, raduis, 0, 0, 0, 0});
            return back;
        }

        private Drawable editbackdrawable() {
            int raduis = Utils.dip2px(params.context, 8);
            int backcolor = params.context.getResources().getColor(R.color.whitecornerback);
            GradientDrawable back = new GradientDrawable();
            back.setShape(GradientDrawable.RECTANGLE);
            back.setColor(backcolor);
            back.setCornerRadii(new float[]{raduis, raduis, raduis, raduis, raduis, raduis, raduis, raduis});
            return back;
        }

    }

    public interface SaveEditListener {
        void savetext(Dialog dialog, String text);
    }
}
