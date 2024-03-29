/**
 * See the file "LICENSE" for the full license governing this code.
 */
package com.github.yftx.wzd.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;

/**
 * Singleton class to manage current application context
 * b
 * @author Tim Su <tim@todoroo.com>
 *
 */
public final class ContextManager {

    /**
     * Global application context
     */
    private static Context context = null;

    /**
     * Sets the global context
     *
     * @param context
     */
    public static void setContext(Context context) {
        if(context == null || context.getApplicationContext() == null)
            return;
        if(ContextManager.context != null && !(context instanceof Activity))
            return;
        ContextManager.context = context;
    }

    /**
     * Gets the global context
     */
    public static Context getContext() {
        return context;
    }

    /**
     * Convenience method to read a string from the resources
     *
     * @param resId resource
     * @param formatArgs % arguments
     * @return resource string
     */
    public static String getString(int resId, Object... formatArgs) {
        return context.getString(resId, formatArgs);
    }

    /**
     * Convenience method to read resources
     *
     * @return resources object
     */
    public static Resources getResources() {
        return context.getResources();
    }

}
