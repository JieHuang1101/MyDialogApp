package com.appxy.dialog_app.other;

import android.app.Activity;
import android.os.Bundle;

import com.appxy.dialog_app.R;

import androidx.annotation.Nullable;

public class SecondActivity extends Activity {
   @Override
   protected void onCreate(@Nullable Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);
   }
}
