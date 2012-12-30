package com.github.yftx.wzd.utils;

import android.content.Context;
import com.gfan.sdk.statitistics.GFAgent;

/**
 * User: yftx
 * Mail: yftx.net@gmail.com
 * Date: 12-8-30
 */
public class Gfan {
    public static void sendEvent(Context context, String msg) {
        if (Logger.isUseGfan)
            GFAgent.onEvent(context, msg);
    }

    public static void onResume(android.app.Activity activity) {
        if (Logger.isUseGfan)
            GFAgent.onResume(activity);
    }

    public static void onPause(android.app.Activity activity) {
        if (Logger.isUseGfan)
            GFAgent.onPause(activity);
    }

}
