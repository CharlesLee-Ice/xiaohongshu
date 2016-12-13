package com.learning.xiaohongshu;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;

/**
 * Created by liqilin on 2016/12/12.
 */

public class DisplayUtils {

    private static int DisplayWidthPixels = 0;
    private static int DisplayHeightPixels = 0;

    private static void getDisplayMetrics(Activity activity) {
        if (activity == null) {
            throw new IllegalArgumentException("getDisplayMetrics invalid argument");
        }

        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        DisplayWidthPixels = metrics.widthPixels;
        DisplayHeightPixels = metrics.heightPixels;
    }

    public static int getDisplayWidthPixels(Activity activity) {
        if (DisplayWidthPixels == 0) {
            getDisplayMetrics(activity);
        }
        return DisplayWidthPixels;
    }

    public static int getDisplayHeightPixels(Activity activity) {
        if (DisplayHeightPixels == 0) {
            getDisplayMetrics(activity);
        }
        return DisplayHeightPixels;
    }

    public static int dip2px(Context context, float dip) {
        final float ratio = context.getResources().getDisplayMetrics().density;
        return (int)(dip * ratio + 0.5f);
    }
}
