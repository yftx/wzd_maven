package com.github.yftx.wzd.service;

import android.app.*;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.IBinder;
import com.github.yftx.wzd.common.task.*;
import com.github.yftx.wzd.utils.ContextManager;
import com.github.yftx.wzd.utils.DateTimeHelper;
import com.github.yftx.wzd.utils.Logger;
import com.github.yftx.wzd.utils.Preferences;


public class WZDService extends Service {
    private NotificationManager mNotificationManager;
    private GenericTask mRetrieveTask;
    static String TAG = "WZDService";

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        Logger.d("-----------------------------------------\n");
        Logger.d("service 创建时间   " + DateTimeHelper.getNowTime());
        boolean autoCheckInfo = Preferences.getBoolean(Preferences.AUTO_CHECK_UPDATES_KEY, false);
        if (!autoCheckInfo) {
            Logger.d("未开启后台推送功能,退出service");
            unScheduleService();
            stopSelf();
            return;
        }


//        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//        if (mRetrieveTask != null &&
//                mRetrieveTask.getStatus() == GenericTask.Status.RUNNING) {
//        } else {
//            mRetrieveTask = new RetrieveTask();
//            mRetrieveTask.setListener(mRetrieveTaskListener);
//            mRetrieveTask.execute((TaskParams[]) null);
//        }
    }

    private void notify(PendingIntent intent, int notificationId,
                        int notifyIconId, String tickerText, String title, String text) {
        Notification notification = new Notification(notifyIconId, tickerText,
                System.currentTimeMillis());

        notification.setLatestEventInfo(this, title, text, intent);

        notification.flags = Notification.FLAG_AUTO_CANCEL
                | Notification.FLAG_ONLY_ALERT_ONCE
                | Notification.FLAG_SHOW_LIGHTS;

        notification.ledARGB = 0xFF84E4FA;
        notification.ledOnMS = 5000;
        notification.ledOffMS = 5000;

        String ringtoneUri = Preferences.getStringValue(Preferences.RINGTONE_KEY);

        if (ringtoneUri == null) {
            notification.defaults |= Notification.DEFAULT_SOUND;
        } else {
            notification.sound = Uri.parse(ringtoneUri);
        }


        if (Preferences.getBoolean(Preferences.VIBRATE_KEY, false)) {
            notification.defaults |= Notification.DEFAULT_VIBRATE;
        }

        mNotificationManager.notify(notificationId, notification);
    }

    private void processNewDms() {
//        int count = mNewDms.size();
//        if (count <= 0) {
//            return;
//        }
//
//        Dm latest = mNewDms.get(0);
//
//        String title;
//        String text;
//
//        if (count == 1) {
//            title = latest.screenName;
//            text = TextHelper.getSimpleTweetText(latest.text);
//        } else {
//            title = getString(R.string.service_new_direct_message_updates);
//            text = getString(R.string.service_x_new_direct_messages);
//            text = MessageFormat.format(text, count);
//        }
//
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
//                DmActivity.createIntent(), 0);
//
//        notify(pendingIntent, DM_NOTIFICATION_ID, R.drawable.notify_dm,
//                TextHelper.getSimpleTweetText(latest.text), title, text);
    }

    @Override
    public void onDestroy() {
        Logger.d("service 结束时间 预期执行 实际结束时间为   " + DateTimeHelper.getNowTime());
        Logger.d("-----------------------------------------\n");
        if (mRetrieveTask != null
                && mRetrieveTask.getStatus() == GenericTask.Status.RUNNING) {
            mRetrieveTask.cancel(true);
        }
        super.onDestroy();
    }

    public static void scheduleService() {
        Logger.d("scheduleService 开始执行");


        Context context = ContextManager.getContext();

        AlarmManager alarm = (AlarmManager) context
                .getSystemService(Context.ALARM_SERVICE);
        PendingIntent pending = PendingIntent.getService(context, 0, new Intent(context, WZDService.class), PendingIntent.FLAG_CANCEL_CURRENT);
//        alarm.cancel(pending);uanshang
        alarm.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 5 * 1000L, pending);

//        boolean needCheck = Preferences.getBoolean(Preferences.AUTO_CHECK_UPDATES_KEY, false);
//        boolean widgetIsEnabled = WZDService.widgetIsEnabled;
//        if (!needCheck && !widgetIsEnabled) {
//            return;
//        }
//
//        //开始处理service
//        String intervalPref = Preferences.getStringValue(Preferences.CHECK_UPDATE_INTERVAL_KEY);
//        int interval = Integer.parseInt(intervalPref);
//        Intent intent = new Intent(context, WZDService.class);
//        PendingIntent pending = PendingIntent.getService(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
//        Calendar c = new GregorianCalendar();
//        //TODO 测试时候每5秒执行一次，因为该方法设置执行时间为下一分钟的5秒执行所以，每1分钟5秒执行一次
//        c.add(Calendar.SECOND, 5);
//        //c.add(Calendar.MINUTE, interval);
//        AlarmManager alarm = (AlarmManager) context
//                .getSystemService(Context.ALARM_SERVICE);
//        if (needCheck) {
//            alarm.cancel(pending);
//            alarm.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 25*1000L,
//                interval, pending);
//        } else {
//            // only for widget
//            alarm.set(AlarmManager.RTC, c.getTimeInMillis(), pending);
//        }
    }

    public static void unScheduleService() {
        Context context = ContextManager.getContext();
        Intent intent = new Intent(context, WZDService.class);
        PendingIntent pending = PendingIntent.getService(context, 0, intent, 0);
        AlarmManager alarm = (AlarmManager) context
                .getSystemService(Context.ALARM_SERVICE);
        alarm.cancel(pending);
    }

    private static boolean widgetIsEnabled = false;

    public static void setWidgetStatus(boolean isEnabled) {
        widgetIsEnabled = isEnabled;
    }

    public static boolean isWidgetEnabled() {
        return widgetIsEnabled;
    }

    private class RetrieveTask extends GenericTask {

        @Override
        protected TaskResult _doInBackground(TaskParams... params) {
            try {
                Thread.sleep(2 * 1000);
            } catch (InterruptedException e) {
            }
            if (isCancelled()) {
                return TaskResult.CANCELLED;
            }
            return TaskResult.OK;
        }
    }

    private TaskListener mRetrieveTaskListener = new TaskAdapter() {
        @Override
        public void onPostExecute(GenericTask task, TaskResult result) {
            if (result == TaskResult.OK) {
            }
            stopSelf();
        }

        @Override
        public String getName() {
            return "ServiceRetrieveTask";
        }
    };
}
