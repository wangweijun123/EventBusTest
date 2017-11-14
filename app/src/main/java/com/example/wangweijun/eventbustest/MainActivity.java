package com.example.wangweijun.eventbustest;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends Activity implements View.OnClickListener{
    public static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.test1).setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.test1:
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
                        Log.e(TAG, "post MessageEvent start..."+", tid:"+Thread.currentThread().getId());
//                        EventBus.getDefault().post(new MessageEvent("Hello everyone!"));
                        EventBus.getDefault().postSticky(new MessageEvent("Hello everyone!"));
                        Log.e(TAG, "post MessageEvent finished");
//                    }
//                }).start();
                break;
            default:
                break;
        }
    }

    MySubscriber mySubscriber = new MySubscriber();
    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
        EventBus.getDefault().register(mySubscriber);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        EventBus.getDefault().unregister(mySubscriber);
        super.onStop();
    }

    // This method will be called when a MessageEvent is posted (in the UI thread for Toast)
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void mainThreadMode(MessageEvent event) {
        Log.e(TAG, "mainThreadMode event:"+event.message+", tid:"+Thread.currentThread().getId());
        Toast.makeText(this, event.message, Toast.LENGTH_SHORT).show();
    }

    // This method will be called when a SomeOtherEvent is posted
    @Subscribe
    public void defaultThreadMode(MessageEvent event) {
        Log.e(TAG, "defaultThreadMode event:"+event.message+", tid:"+Thread.currentThread().getId());
    }

    // Called in the same thread (default)
    @Subscribe(threadMode = ThreadMode.POSTING)
    public void postingThreadMode(MessageEvent event) {
        Log.e(TAG, "postingThreadMode event:"+event.message+", tid:"+Thread.currentThread().getId());
    }

    // 如果post 线程在UI线程，订阅者会在后台线程，如果post线程在非UI线程，订阅者之间调用
    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void backgroundThreadMode(MessageEvent event){
        // save to sdcard, access network, access DB
        Log.e(TAG, "backgroundThreadMode event:"+event.message+", tid:"+Thread.currentThread().getId());
    }


    // Called in a separate thread(从线程池中取线程)
    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void asyncThreadMode(MessageEvent event){
        event.message = "hello 我被修改了";
        Log.e(TAG, "asyncThreadMode event:"+event.message+", tid:"+Thread.currentThread().getId());
    }
}
