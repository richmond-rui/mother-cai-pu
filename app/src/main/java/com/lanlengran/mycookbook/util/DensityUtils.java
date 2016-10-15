package com.lanlengran.mycookbook.util;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Point;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.WindowManager;

/**
 * Created by root on 16-6-14.
 */

public class DensityUtils {

    /*
    * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
    */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        //px/sp =
        return (int) ((spValue-0.5f) * fontScale);
    }


    /**
     * 获取屏幕分辨率
     * Created by Administrator
     * on 2016/5/25.
     */



        public static DisplayMetrics getDisplayMetrics(Context context) {
            WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            DisplayMetrics metrics = new DisplayMetrics();
            windowManager.getDefaultDisplay().getMetrics(metrics);
            return metrics;
        }

        /**
         * 获取屏幕分辨率-宽
         *
         * @param context
         * @return
         */
        public static int getScreenWidth(Context context) {
            DisplayMetrics metrics = getDisplayMetrics(context);
            return metrics.widthPixels;
        }

        /**
         * 获取屏幕分辨率-高
         *
         * @param context
         * @return
         */
        public static int getScreenHeight(Context context) {
            DisplayMetrics metrics = getDisplayMetrics(context);
            return metrics.heightPixels;
        }
//    public static DisplayImageOptions.Builder getDisplayImageBuilder() {
//        return new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true)
//                .bitmapConfig(Bitmap.Config.RGB_565).imageScaleType(ImageScaleType.IN_SAMPLE_INT)
//                .displayer(new FadeInBitmapDisplayer(500));
//    }

    public static int getStatusBarHeightPixel(Context context) {
        int result = 0;
        int resourceId =
                context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public static int getActionBarHeightPixel(Context context) {
        TypedValue tv = new TypedValue();
        if (context.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            return TypedValue.complexToDimensionPixelSize(tv.data,
                    context.getResources().getDisplayMetrics());
        } else if (context.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            return TypedValue.complexToDimensionPixelSize(tv.data,
                    context.getResources().getDisplayMetrics());
        } else {
            return 0;
        }
    }



    public static Point getDisplayDimen(Context context) {
        Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE))
                .getDefaultDisplay();
        Point size = new Point();
        if (Build.VERSION.SDK_INT >= 13) {
            display.getSize(size);
        } else {
            size.x = display.getWidth();
            size.y = display.getHeight();
        }
        return size;
    }

    public static boolean isLand(Context context) {
        return context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }


    }
