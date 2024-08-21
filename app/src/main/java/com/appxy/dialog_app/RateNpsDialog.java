package com.appxy.dialog_app;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.appxy.dialog_app.databinding.DialogRateNpsBinding;
import com.appxy.dialog_app.other.Utils;

import androidx.core.content.ContextCompat;

public class RateNpsDialog {

   private static AlertDialog dialog;

   public static class Builder {
      private Context mContext;
      private String mUrl;

      public Builder(Context context, String url) {
         this.mContext = context;
         this.mUrl = url;
      }

      @SuppressLint("SetJavaScriptEnabled")
      public AlertDialog create() {
         DialogRateNpsBinding binding = DialogRateNpsBinding.inflate(LayoutInflater.from(mContext));
         View view = binding.getRoot();
         dialog = new AlertDialog.Builder(mContext).setView(view).create();
         dialog.setCancelable(false);
         dialog.setCanceledOnTouchOutside(false);
         if (mContext instanceof Activity) {
            Activity activity = (Activity) mContext;
            if (!activity.isFinishing() && !activity.isDestroyed()) {
               dialog.show();
            }
         }

         WebSettings webSettings = binding.webView.getSettings();
         webSettings.setJavaScriptEnabled(true);//是否开启JS支持
         webSettings.setDomStorageEnabled(true);//开启本地缓存，适应免责声明中的web
         binding.webView.requestFocus();// 获取焦点
         binding.webView.setEnabled(true);// 这里如果设置false, 则点击h5页面中的输入框时不能唤起软键盘
         //使得打开网页时不调用系统浏览器, 而是在本WebView中显示
         binding.webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
               super.onPageFinished(view, url);
               //Android发送消息给JS[JSON格式的字符串]
               /*
               String result = "{'platform':'Android', 'action':'Cancel'}";
               binding.webView.loadUrl("javascript:receiveDataFromAndroid(\"" + result + "\")");
               * */
            }
         });
         //调用loadUrl()方法进行加载内容
         binding.webView.loadUrl(mUrl);
         binding.webView.addJavascriptInterface(new RateNpsDialog()/*调用方法的类名*/, "androidHandler"/*别名*/); //Android方法接口，html中调用使用别名

         //自定义背景
         Window window = dialog.getWindow();
         window.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
         window.setGravity(Gravity.BOTTOM);
         int screenWidth = mContext.getResources().getDisplayMetrics().widthPixels;
         WindowManager.LayoutParams attributes = window.getAttributes();
         attributes.width = screenWidth;
         window.setAttributes(attributes);
         hideNavigationBar(dialog);//隐藏底部NavigationBar
         int radius = Utils.dip2px(mContext, 10);
         int bgColor = ContextCompat.getColor(mContext, R.color.dialog_background_color);
         binding.rootLayout.setBackground(backgroundDrawable(bgColor, radius));

         return dialog;
      }
   }

   private static void hideNavigationBar(Dialog dialog) {
      int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
              | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
              | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
              | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
              | View.SYSTEM_UI_FLAG_IMMERSIVE
              | View.SYSTEM_UI_FLAG_FULLSCREEN;
      dialog.getWindow().getDecorView().setSystemUiVisibility(uiOptions);
   }

   /**
    * @param bg_color 背景颜色
    * @param radius 圆角大小
    */
   public static Drawable backgroundDrawable(int bg_color, float radius) {

      GradientDrawable back = new GradientDrawable();
      back.setShape(GradientDrawable.RECTANGLE);
      back.setColor(bg_color);
      back.setCornerRadii(new float[]{radius, radius, radius, radius, 0, 0, 0, 0});

      return back;
   }

   // 被JS调用的方法必须加入@JavascriptInterface注解
   @JavascriptInterface
   public void postMessage(String msg) {
      /*[点击网页上的X关闭时]JS给Android发送消息:msg为JSON格式的字符串*/
      Log.i("AndroidToJsUtils", msg);
      if (dialog != null) {
         dialog.dismiss();
      }
   }
}
