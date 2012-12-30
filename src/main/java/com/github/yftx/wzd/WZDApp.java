package com.github.yftx.wzd;

import android.app.Application;
import com.github.yftx.wzd.engine.WZD;

/**
 * User: yftx
 * Mail: yftx.net@gmail.com
 * Date: 12-8-12
 */
public class WZDApp extends Application {
    public WZD wzd;
    public static final int REQUEST_CODE_PREFERENCES = 100;
    public WZDApp() {
        this.wzd = new WZD(this);
    }
}
