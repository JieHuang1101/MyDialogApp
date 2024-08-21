package com.appxy.dialog_app;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.appxy.dialog_app.databinding.ActivityTestWebViewBinding;

import java.util.Locale;

import androidx.annotation.Nullable;

public class TestWebView extends Activity {

   private Activity activity;
   private ActivityTestWebViewBinding binding;

   @Override
   protected void onCreate(@Nullable Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      binding = ActivityTestWebViewBinding.inflate(getLayoutInflater());
      setContentView(binding.getRoot());
      activity = this;
      WebSettings webSettings = binding.webView.getSettings();
      webSettings.setJavaScriptEnabled(true);//是否开启JS支持
      webSettings.setDomStorageEnabled(true);//开启本地缓存，适应免责声明中的web
      //使得打开网页时不调用系统浏览器, 而是在本WebView中显示
      binding.webView.setWebViewClient(new WebViewClient() {
         @Override
         public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            //Android发送消息给JS[JSON格式的字符串]
            //String result = "{'platform':'Android', 'action':'Cancel'}";
            //binding.webView.loadUrl("javascript:receiveDataFromAndroid(\"" + result + "\")");
         }
      });
      //调用loadUrl()方法进行加载内容
      binding.webView.loadUrl("file:///android_asset/my_test.html");
      //String url = "http://operational-tools-autopulish.us-east-1.elasticbeanstalk.com/survey/nps/1_0?uid=6653f1370da0fa205c0da7d2&theme=light&language=en&platform=android&device_id=12345";
      //binding.webView.loadUrl(url);
      CallBack callBack = new CallBack() {
         @Override
         public void closeCallback() {
            Log.e("m_test","============> 111 ");
            activity.finish();
         }
      };
      binding.webView.addJavascriptInterface(new AndroidToJsUtils(callBack)/*调用方法的类名*/, "androidHandler"/*别名*/); //Android方法接口，html中调用使用别名
   }

   class AndroidToJsUtils {

      private CallBack callBack;
      public AndroidToJsUtils(CallBack callBack) {
         Log.e("m_test","============> 000 ");
         this.callBack = callBack;
      }

      // 被JS调用的方法必须加入@JavascriptInterface注解
      @JavascriptInterface
      public void postMessage(String msg) {
         /*JS给Android发送消息:msg为JSON格式的字符串*/
         Log.i("m_test", msg);
         Log.e("m_test","============> 222 ");
         if (callBack != null) {
            callBack.closeCallback();
         }
      }
   }

   interface CallBack {
      void closeCallback();
   }
}
