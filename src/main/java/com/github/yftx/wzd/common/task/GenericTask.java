package com.github.yftx.wzd.common.task;

import android.os.AsyncTask;

import java.util.Observable;
import java.util.Observer;

public abstract class GenericTask extends
        AsyncTask<TaskParams, Object, TaskResult> implements Observer {
    private static final String TAG = "TaskManager";

    private TaskListener mListener = null;
    private Feedback mFeedback = null;
    private boolean isCancelable = true;

    abstract protected TaskResult _doInBackground(TaskParams... params);

    public void setListener(TaskListener taskListener) {
        mListener = taskListener;
    }

    public TaskListener getListener() {
        return mListener;
    }

    public void doPublishProgress(Object... values) {
        super.publishProgress(values);
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();

        if (mListener != null) {
            mListener.onCancelled(this);
        }
    }

    @Override
    protected void onPostExecute(TaskResult result) {
        super.onPostExecute(result);

        if (mListener != null) {
            mListener.onPostExecute(this, result);
        }

        if (mFeedback != null) {
            mFeedback.success("");
        }

        /*
           * Toast.makeText(TwitterApplication.mContext, mListener.getName() +
           * " completed", Toast.LENGTH_SHORT);
           */
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        if (mListener != null) {
            mListener.onPreExecute(this);
        }

        if (mFeedback != null) {
            mFeedback.start("");
        }
    }

    @Override
    protected void onProgressUpdate(Object... values) {
        super.onProgressUpdate(values);

        if (mListener != null) {
            if (values != null && values.length > 0) {
                mListener.onProgressUpdate(this, values[0]);
            }
        }

        if (mFeedback != null) {
            mFeedback.update(values[0]);
        }
    }

    @Override
    protected TaskResult doInBackground(TaskParams... params) {
        TaskResult result = _doInBackground(params);
        if (mFeedback != null) {
            mFeedback.update(99);
        }
        return result;
    }

    public void update(Observable o, Object arg) {
        if (TaskManager.CANCEL_ALL == (Integer) arg && isCancelable) {
            if (getStatus() == Status.RUNNING) {
                cancel(true);
            }
        }
    }

    public void setCancelable(boolean flag) {
        isCancelable = flag;
    }

    public void setFeedback(Feedback feedback) {
        mFeedback = feedback;
    }
}
