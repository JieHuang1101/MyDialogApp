package com.appxy.custom_dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appxy.custom_dialog.databinding.DialogSettingBottomBinding;
import com.appxy.custom_dialog.utils.AlertParams;
import com.appxy.custom_dialog.utils.Utils;

import androidx.annotation.ArrayRes;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SettingBottomDialog {

    public static class Builder {

        private AlertParams alertParams;

        /*type 0-PageSize / Process    1-Theme*/
        public Builder(Context context, int type) {
            alertParams = new AlertParams();
            alertParams.mContext = context;
            alertParams.mViewType = type;
        }

        public Builder setTitle(String text) {
            alertParams.mTitle = text;
            return this;
        }

        public Builder setTitle(@StringRes int textId) {
            alertParams.mTitle = alertParams.mContext.getResources().getText(textId);
            return this;
        }

        public Builder setDataList(String[] dataList, int selectedIndex) {
            alertParams.mListData = dataList;
            alertParams.mSelectedIndex = selectedIndex;
            return this;
        }

        public Builder setDataList(@ArrayRes int id, int selectedIndex) {
            alertParams.mListData = alertParams.mContext.getResources().getStringArray(id);
            alertParams.mSelectedIndex = selectedIndex;
            return this;
        }

        public Builder setListItemClick(ItemClickCallback callback) {
            alertParams.mItemCallBack = callback;
            return this;
        }

        public Builder setButtonText(String blueTxt, String whiteTxt) {
            alertParams.mDarkBlueText = blueTxt;
            alertParams.mWhiteText = whiteTxt;
            return this;
        }

        public Builder setButtonText(@StringRes int blueTextId, @StringRes int whiteTextId) {
            alertParams.mDarkBlueText = alertParams.mContext.getResources().getText(blueTextId);
            alertParams.mWhiteText = alertParams.mContext.getResources().getText(whiteTextId);
            return this;
        }

        public Builder setThemeClick(ThemeClickCallback callback) {
            alertParams.mThemeCallBack = callback;
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
            DialogSettingBottomBinding binding = DialogSettingBottomBinding.inflate(LayoutInflater.from(alertParams.mContext));
            View view = binding.getRoot();
            AlertDialog dialog = new AlertDialog.Builder(alertParams.mContext, R.style.MyDialog).setView(view).create();
            dialog.setCancelable(alertParams.mCancelable);
            dialog.setCanceledOnTouchOutside(alertParams.mCanceledOnTouchOutside);
            dialog.show();


            Typeface typeface = Typeface.createFromAsset(alertParams.mContext.getAssets(), "Roboto-Medium.ttf");
            binding.titleTv.setText(alertParams.mTitle);
            binding.titleTv.setTypeface(typeface);
            binding.closeIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            if (alertParams.mViewType == 0) {
                binding.recycler.setVisibility(View.VISIBLE);
                binding.themeLayout.setVisibility(View.GONE);

                SettingBottomAdapter adapter = new SettingBottomAdapter(alertParams.mContext, alertParams.mListData, alertParams.mSelectedIndex, dialog);
                binding.recycler.setLayoutManager(new LinearLayoutManager(alertParams.mContext, LinearLayoutManager.VERTICAL, false));
                adapter.setCallback(alertParams.mItemCallBack);
                binding.recycler.setAdapter(adapter);
            } else {
                binding.themeLayout.setVisibility(View.VISIBLE);
                binding.recycler.setVisibility(View.GONE);

                binding.blueThemeTv.setText(alertParams.mDarkBlueText);
                binding.whiteThemeTv.setText(alertParams.mWhiteText);
                if (Utils.isDarkMode()) {
                    binding.blueThemeIv.setImageResource(R.drawable.icon_setting_mode_select_sel);
                    binding.whiteThemeIv.setImageResource(R.drawable.icon_setting_mode_select);
                } else {
                    binding.blueThemeIv.setImageResource(R.drawable.icon_setting_mode_select);
                    binding.whiteThemeIv.setImageResource(R.drawable.icon_setting_mode_select_sel);
                }
                binding.darkBlueLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (alertParams.mThemeCallBack != null) {
                            alertParams.mThemeCallBack.onThemeClick(dialog, false);
                        }
                    }
                });
                binding.whiteLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (alertParams.mThemeCallBack != null) {
                            alertParams.mThemeCallBack.onThemeClick(dialog, true);
                        }
                    }
                });
            }

            //自定义背景
            Window window = dialog.getWindow();
            window.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            window.setGravity(Gravity.BOTTOM);
            int screenWidth = alertParams.mContext.getResources().getDisplayMetrics().widthPixels;
            WindowManager.LayoutParams attributes = window.getAttributes();
            attributes.width = screenWidth;
            window.setAttributes(attributes);
            int bgColor = alertParams.mContext.getResources().getColor(R.color.dialog_background_color);
            binding.rootLayout.setBackground(Utils.backgroundDrawable(bgColor, 0, Utils.dip2px(alertParams.mContext, 10)));

            return dialog;
        }
    }

    public interface ItemClickCallback {
        void onItemClick(Dialog dialog, int position);
    }

    public interface ThemeClickCallback {
        void onThemeClick(Dialog dialog, boolean isWhite);
    }

    public static class SettingBottomAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private Context mContext;
        private String[] mDataList;
        private int selectedIndex;
        private Dialog mDialog;

        private ItemClickCallback callback;

        public SettingBottomAdapter(Context context, String[] dataList, int selectedIndex, Dialog dialog) {
            this.mContext = context;
            this.mDataList = dataList;
            this.selectedIndex = selectedIndex;
            this.mDialog = dialog;
        }

        public void setCallback(ItemClickCallback callback) {
            this.callback = callback;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_setting_bottom_item, parent, false);
            return new ItemViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof ItemViewHolder) {
                ItemViewHolder viewHolder = (ItemViewHolder) holder;
                int itemPosition = viewHolder.getAdapterPosition();

                String text = mDataList[position];
                SpannableString content = new SpannableString(text);
                int index = text.indexOf("(");
                int fColor = mContext.getResources().getColor(R.color.secondly_text_color);
                int sColor = mContext.getResources().getColor(R.color.thirdly_text_color);
                int itemBgColor = mContext.getResources().getColor(R.color.item_background_color);
                if (index != -1) {
                    content.setSpan(new ForegroundColorSpan(fColor), 0, index, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    content.setSpan(new ForegroundColorSpan(sColor), index, text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                } else {
                    content.setSpan(new ForegroundColorSpan(fColor), 0, text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
                viewHolder.tv.setText(content);
                if (position == selectedIndex) {//选中项
                    viewHolder.iv.setVisibility(View.VISIBLE);
                } else {
                    viewHolder.iv.setVisibility(View.GONE);
                }
                viewHolder.layout.setBackground(Utils.backgroundDrawable(itemBgColor, 1, Utils.dip2px(mContext, 10)));

                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (callback != null) {
                            callback.onItemClick(mDialog, itemPosition);
                        }
                    }
                });
            }
        }

        @Override
        public int getItemCount() {
            return mDataList.length;
        }

        private static class ItemViewHolder extends RecyclerView.ViewHolder {

            LinearLayout layout;
            TextView tv;
            ImageView iv;

            public ItemViewHolder(@NonNull View itemView) {
                super(itemView);
                layout = itemView.findViewById(R.id.layout);
                tv = itemView.findViewById(R.id.tv);
                iv = itemView.findViewById(R.id.iv);
            }
        }
    }
}
