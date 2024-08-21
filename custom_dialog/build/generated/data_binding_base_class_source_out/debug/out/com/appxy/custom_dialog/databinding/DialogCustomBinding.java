// Generated by view binder compiler. Do not edit!
package com.appxy.custom_dialog.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.appxy.custom_dialog.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class DialogCustomBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final LinearLayout btnLayout1;

  @NonNull
  public final LinearLayout btnLayout2;

  @NonNull
  public final TextView messageTv;

  @NonNull
  public final TextView negativeTv1;

  @NonNull
  public final TextView negativeTv2;

  @NonNull
  public final TextView neutralTv2;

  @NonNull
  public final TextView positiveTv1;

  @NonNull
  public final TextView positiveTv2;

  @NonNull
  public final LinearLayout rootLayout;

  @NonNull
  public final TextView titleTv;

  private DialogCustomBinding(@NonNull LinearLayout rootView, @NonNull LinearLayout btnLayout1,
      @NonNull LinearLayout btnLayout2, @NonNull TextView messageTv, @NonNull TextView negativeTv1,
      @NonNull TextView negativeTv2, @NonNull TextView neutralTv2, @NonNull TextView positiveTv1,
      @NonNull TextView positiveTv2, @NonNull LinearLayout rootLayout, @NonNull TextView titleTv) {
    this.rootView = rootView;
    this.btnLayout1 = btnLayout1;
    this.btnLayout2 = btnLayout2;
    this.messageTv = messageTv;
    this.negativeTv1 = negativeTv1;
    this.negativeTv2 = negativeTv2;
    this.neutralTv2 = neutralTv2;
    this.positiveTv1 = positiveTv1;
    this.positiveTv2 = positiveTv2;
    this.rootLayout = rootLayout;
    this.titleTv = titleTv;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static DialogCustomBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static DialogCustomBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.dialog_custom, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static DialogCustomBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.btn_layout_1;
      LinearLayout btnLayout1 = ViewBindings.findChildViewById(rootView, id);
      if (btnLayout1 == null) {
        break missingId;
      }

      id = R.id.btn_layout_2;
      LinearLayout btnLayout2 = ViewBindings.findChildViewById(rootView, id);
      if (btnLayout2 == null) {
        break missingId;
      }

      id = R.id.message_tv;
      TextView messageTv = ViewBindings.findChildViewById(rootView, id);
      if (messageTv == null) {
        break missingId;
      }

      id = R.id.negative_tv_1;
      TextView negativeTv1 = ViewBindings.findChildViewById(rootView, id);
      if (negativeTv1 == null) {
        break missingId;
      }

      id = R.id.negative_tv_2;
      TextView negativeTv2 = ViewBindings.findChildViewById(rootView, id);
      if (negativeTv2 == null) {
        break missingId;
      }

      id = R.id.neutral_tv_2;
      TextView neutralTv2 = ViewBindings.findChildViewById(rootView, id);
      if (neutralTv2 == null) {
        break missingId;
      }

      id = R.id.positive_tv_1;
      TextView positiveTv1 = ViewBindings.findChildViewById(rootView, id);
      if (positiveTv1 == null) {
        break missingId;
      }

      id = R.id.positive_tv_2;
      TextView positiveTv2 = ViewBindings.findChildViewById(rootView, id);
      if (positiveTv2 == null) {
        break missingId;
      }

      LinearLayout rootLayout = (LinearLayout) rootView;

      id = R.id.title_tv;
      TextView titleTv = ViewBindings.findChildViewById(rootView, id);
      if (titleTv == null) {
        break missingId;
      }

      return new DialogCustomBinding((LinearLayout) rootView, btnLayout1, btnLayout2, messageTv,
          negativeTv1, negativeTv2, neutralTv2, positiveTv1, positiveTv2, rootLayout, titleTv);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
