package com.appxy.dialog_app.other;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import com.appxy.dialog_app.R;

public class XToast {

    public static final int LENGTH_SHORT = 2000;
    public static final int LENGTH_LONG = 3500;

    private Context mContext;
    private Handler mHandler;
    private TextView mTextView;
    private int mDuration;
    private Dialog dialog;

    public XToast(Context context) {
        try {
            mContext = context;
            mHandler = new Handler();
            dialog = new Dialog(mContext, R.style.ToastDialogStyle);
            dialog.setContentView(R.layout.xtoast_layout);
            mTextView = (TextView) dialog.findViewById(R.id.mbMessage);
            mTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static XToast makeText(Context context, CharSequence message, int duration) {
        XToast toastUtils = new XToast(context);
        try {
            toastUtils.mDuration = duration;
            toastUtils.mTextView.setText(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return toastUtils;
    }

    public static XToast makeText(Context context, int resId, int duration) {
        String mes = "";
        try {
            mes = context.getResources().getString(resId);
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
        return makeText(context, mes, duration);
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
}