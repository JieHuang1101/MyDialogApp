package com.appxy.dialog_app.other;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.appxy.dialog_app.R;

import java.lang.reflect.Field;


public class ClickToast{

   private static Toast mToast;
   private static Button btn;

   public static void showToast (final Context context, int duration){

      if(mToast == null){
         LayoutInflater inflater = (LayoutInflater)context.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
         //自定义布局
         View view = inflater.inflate(R.layout.click_toast, null);
         btn= view.findViewById(R.id.btn);
         btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               //这里可以做点击操作
               Log.i("m_test", "Button点击事件");
            }
         });
         mToast = Toast.makeText(context, "", duration);
         //这里可以指定显示位置
         mToast.setView(view);
      }

      try {
         Object mTN ;
         mTN = getField(mToast, "mTN");
         if (mTN != null) {
            Object mParams = getField(mTN, "mParams");
            if (mParams != null && mParams instanceof WindowManager.LayoutParams) {
               WindowManager.LayoutParams params = (WindowManager.LayoutParams) mParams;
               //Toast可点击
               params.flags = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                       | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;

               //设置viewgroup宽高
               params.width = WindowManager.LayoutParams.MATCH_PARENT; //设置Toast宽度为屏幕宽度
               params.height = WindowManager.LayoutParams.WRAP_CONTENT; //设置高度
            }
         }
      } catch (Exception e) {
         e.printStackTrace();
      }

      mToast.show();
   }

   /**
    * 反射字段
    * @param object 要反射的对象
    * @param fieldName 要反射的字段名称
    */
   private static Object getField(Object object, String fieldName)
           throws NoSuchFieldException, IllegalAccessException {
      Field field = object.getClass().getDeclaredField(fieldName);
      if (field != null) {
         field.setAccessible(true);
         return field.get(object);
      }
      return null;
   }

}