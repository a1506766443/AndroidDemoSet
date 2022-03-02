package com.wl.dispatch;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

// 父容器
public class BadViewPager extends ViewPager {

    private int mLastX, mLastY;

    public BadViewPager(@NonNull Context context) {
        super(context);
    }

    public BadViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    // 拦截自己的孩子
//    @Override
//    public boolean onInterceptTouchEvent(MotionEvent event) {
//        // down事件的时候不能拦截，因为这个时候 requestDisallowInterceptTouchEvent 无效
//        if (event.getAction() == MotionEvent.ACTION_DOWN) {
//            super.onInterceptTouchEvent(event);
//            return false;
//        }
//        return true;
//    }

    // 外部拦截法：一般只需要在父容器处理，根据业务需求，返回true或者false
    public boolean onInterceptTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                mLastX = (int) event.getX();
                mLastY = (int) event.getY();
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                int deltaX = x - mLastX;
                int deltaY = y - mLastY;
                if (Math.abs(deltaX) > Math.abs(deltaY)) {
                    return true;
                }
                break;
            }
            case MotionEvent.ACTION_UP: {
                break;
            }
            default:
                break;
        }

        return super.onInterceptTouchEvent(event);
    }
}
