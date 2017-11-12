package com.example.wangweijun.eventbustest;

import android.util.Log;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by wangweijun on 2017/11/13.
 */

public class MySubscriber {

    public static final String TAG = MainActivity.TAG;

    // This method will be called when a MessageEvent is posted (in the UI thread for Toast)
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void mainThreadMode(MessageEvent event) {
        Log.e(TAG, "MySubscriber mainThreadMode event:"+event.message+", tid:"+Thread.currentThread().getId());
    }

    // This method will be called when a SomeOtherEvent is posted
    @Subscribe
    public void defaultThreadMode(MessageEvent event) {
        Log.e(TAG, "MySubscriber defaultThreadMode event:"+event.message+", tid:"+Thread.currentThread().getId());
    }

    // Called in the same thread (default)
    @Subscribe(threadMode = ThreadMode.POSTING)
    public void postingThreadMode(MessageEvent event) {
        Log.e(TAG, "MySubscriber postingThreadMode event:"+event.message+", tid:"+Thread.currentThread().getId());
    }
}
