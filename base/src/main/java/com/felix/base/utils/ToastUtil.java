package com.felix.base.utils;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.SizeUtils;
import com.felix.base.R;


/**
 * Created by hzp on 2017/11/28.
 * 统一管理Toast
 */
public class ToastUtil {

    public static final String ExceptionRemind = "You have to call static method init() first in Applicaiton";

    public static Context mContext;
    private static Toast mCustomToast;
    private static Toast mSystemToast;

    private static int CENTER = Gravity.CENTER;
    private static int CENTER_TOP = Gravity.CENTER_HORIZONTAL | Gravity.TOP;
    private static int CENTER_BOTTOM = Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM;
    private static int TOP_LEFT = Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM;

    public static final String MATERIAL_RED = "#F44336";
    public static final String MATERIAL_GREEN = "#4caf50";
    public static final String MATERIAL_YELLOW = "#FFC107";
    public static final String MATERIAL_DEEP_ORANGE = "#FF5722";
    public static final String MATERIAL_BLUE_GREY = "#607D8B";
    public static final String DIALOG_BG_COLOR = "#5F6D76";

    public static void init(Context context) {
        mContext = context;
    }

    /**
     * 显示系统默认Toast
     *
     * @param content
     */
    public static void showSystemToast(String content) {
        if (TextUtils.isEmpty(content)) {
            return;
        }
        if (mContext == null) {
            throw new NullPointerException(ExceptionRemind);
        }

        int duration = Toast.LENGTH_SHORT;
        if (content.length() > 10) {
            duration = Toast.LENGTH_LONG;
        }

        if (mSystemToast == null) {
            mSystemToast = Toast.makeText(mContext, content, duration);
        } else {
            mSystemToast.setText(content);
            mSystemToast.setDuration(duration);
        }
        mSystemToast.show();
    }


    private static void show(int icon, int bgColor, int gravity, int xOffset, int yOffiset, String content) {
        if (mContext == null) {
            throw new NullPointerException(ExceptionRemind);
        }
        if (TextUtils.isEmpty(content)) {
            return;
        }
        int duration = content.length() > 10 ? Toast.LENGTH_SHORT : Toast.LENGTH_SHORT;
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.basic_toast_layout, null);
        TextView tvToast = view.findViewById(R.id.tv_toast_content);
        ImageView ivIcon = view.findViewById(R.id.iv_toast_icon);
        if (icon != -1) {
            ivIcon.setImageResource(icon);
        } else {
            ivIcon.setVisibility(View.GONE);
        }
        tvToast.setText(content);
        Toast toast = new Toast(mContext);
//        if (mCustomToast == null) {
//            mCustomToast = new Toast(mContext);
//        } else {
//            mCustomToast.cancel();
//        }
        toast.setGravity(gravity, xOffset, yOffiset);
        toast.setView(view);
        toast.setDuration(duration);
        toast.show();
    }


    /**
     * 在屏幕中间显示toast
     *
     * @param icon    toast图标
     * @param bgColor toast背景色
     * @param content toast内容
     */
    public static void showToastAtCenter(int icon, int bgColor, String content) {
        show(icon, bgColor, CENTER, 0, 0, content);
    }

    /**
     * 在屏幕底部中间显示toast
     *
     * @param icon    toast图标
     * @param bgColor toast背景色
     * @param content toast内容
     */
    public static void showToastAtCenterBottom(int icon, int bgColor, String content) {
        show(icon, bgColor, CENTER_BOTTOM, 0, SizeUtils.dp2px(96), content);
    }

    /**
     * 在屏幕中间顶部显示toast
     *
     * @param icon    toast图标
     * @param bgColor toast背景色
     * @param content toast内容
     */
    public static void showToastAtCenterTop(int icon, int bgColor, String content) {
        show(icon, bgColor, CENTER_TOP, 0, -SizeUtils.dp2px(96), content);
    }

    /**
     * 在指定位置显示toast
     *
     * @param icon     toast图标
     * @param bgColor  toast背景色
     * @param xOffset  屏幕x坐标
     * @param yOffiset 屏幕y坐标
     * @param content  toast内容
     */
    public static void showToastAtPosition(int icon,
                                           int bgColor,
                                           int xOffset,
                                           int yOffiset,
                                           String content) {
        show(icon, bgColor, TOP_LEFT, xOffset, yOffiset, content);
    }

    /**
     * 显示错误
     *
     * @param content
     */
    public static void showErrorToast(String content) {
//        showToastAtCenterBottom(R.mipmap.icon_toast_fail, Color.parseColor(MATERIAL_RED), content);
    }

    /**
     * 显示警告
     *
     * @param content
     */
    public static void warn(String content) {
//        showToastAtCenterBottom(R.mipmap.icon_toast_warn,
//                Color.parseColor(MATERIAL_YELLOW),
//                content);
    }

    /**
     * 显示提示
     *
     * @param content
     */
    public static void remind(String content) {
//        showToastAtCenterBottom(R.mipmap.icon_toast_remind,
//                Color.parseColor(MATERIAL_DEEP_ORANGE),
//                content);
    }
    public static void show(String content) {
        showToastAtCenterNoIcon(-1, Color.parseColor(DIALOG_BG_COLOR), content);
    }
    /**
     * 在屏幕中间显示toast,无icon
     *
     * @param icon    toast图标
     * @param bgColor toast背景色
     * @param content toast内容
     */
    public static void showToastAtCenterNoIcon(int icon, int bgColor, String content) {
        show(-1, bgColor, CENTER, 0, 0, content);
    }
}
