package com.wl.dispatch;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;

// 子View -- 容器 -- ViewGroup.dispatchTouchEvent --> super.dispatchTouchEvent
public class MyListView extends ListView {

    public MyListView(Context context) {
        super(context);
    }

    public MyListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private int mLastX, mLastY;

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();


        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                getParent().requestDisallowInterceptTouchEvent(true);
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                int deltaX = x - mLastX;
                int deltaY = y - mLastY;
                // 这个条件由业务逻辑决定，看什么时候 子View将事件让出去
                if (Math.abs(deltaX) > Math.abs(deltaY)) {
                    getParent().requestDisallowInterceptTouchEvent(false);
                }
                break;
            }
            case MotionEvent.ACTION_UP: {
                break;

            }
            default:
                break;
        }

        mLastX = x;
        mLastY = y;
        return super.dispatchTouchEvent(event);
    }
}
