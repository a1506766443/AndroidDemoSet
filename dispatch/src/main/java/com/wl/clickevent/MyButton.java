package com.wl.clickevent;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Button;

public class MyButton extends Button {
    private static final String TAG = "Leo";

    public MyButton(Context context) {
        super(context);
    }

    public MyButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    // 只要事件来到改View，这个方法肯定执行
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Log.e(TAG, "dispatchTouchEvent: 子View");
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.e(TAG, "onTouchEvent: MotionEvent.ACTION_DOWN = " + MotionEvent.ACTION_DOWN);
                break;
            case MotionEvent.ACTION_MOVE:
                Log.e(TAG, "onTouchEvent: MotionEvent.ACTION_MOVE = " + MotionEvent.ACTION_MOVE);
                break;
            case MotionEvent.ACTION_UP:
                Log.e(TAG, "onTouchEvent: MotionEvent.ACTION_UP = " + MotionEvent.ACTION_UP);
                break;
            case MotionEvent.ACTION_CANCEL:
                Log.e(TAG, "onTouchEvent: MotionEvent.ACTION_CANCEL = " + MotionEvent.ACTION_CANCEL);
                break;
        }
        return super.onTouchEvent(event);
    }

}
