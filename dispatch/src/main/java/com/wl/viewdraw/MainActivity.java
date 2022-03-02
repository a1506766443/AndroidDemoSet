package com.wl.viewdraw;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.wl.dispatch.R;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "leo";
    private TextView mTextView;

    private static final int OVERLAY_PERMISSION_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_viewdraw);
        mTextView = findViewById(R.id.tv);

        // 1-1.在ViewRootImpl 创建之前调用
        new Thread(new Runnable() {
            @Override
            public void run() {
                mTextView.setText("改一改");
            }
        }).start();

        // 设置权限
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(this)) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, OVERLAY_PERMISSION_CODE);
            }
        }

        // 2-1.height1、height3、height4都获取不到，height2可以获取到
        Log.e(TAG, "height1 = " + mTextView.getMeasuredHeight());
        Log.e(TAG, "height4 = " + mTextView.getHeight());

        // 2-2
        mTextView.post(new Runnable() {
            @Override
            public void run() {
                Log.e(TAG, "height2 = " + mTextView.getMeasuredHeight());
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 2-3.获取不到高度
        Log.e(TAG, "height3 = " + mTextView.getMeasuredHeight());
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == OVERLAY_PERMISSION_CODE) {
            if (Settings.canDrawOverlays(this)) {
                // 1-2.在子线程中创建ViewRootImpl
                childThreadChangeUI();
            } else {
            }
        }
    }

    public void childThreadChangeUI() {
        new Thread() {
            @RequiresApi(api = Build.VERSION_CODES.R)
            @Override
            public void run() {
                Looper.prepare();
                final WindowManager wm = (WindowManager) getApplicationContext().getSystemService(WINDOW_SERVICE);
                final View view = View.inflate(MainActivity.this, R.layout.item_viewdraw, null);
                final TextView tv = view.findViewById(R.id.tv);
                final WindowManager.LayoutParams params = new WindowManager.LayoutParams();
                params.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
                // 设置不拦截焦点
                params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
                params.width = (int) (60 * getResources().getDisplayMetrics().density);
                params.height = (int) (60 * getResources().getDisplayMetrics().density);
                params.gravity = Gravity.LEFT | Gravity.TOP;// 且设置坐标系 左上角
                params.format = PixelFormat.TRANSPARENT;
                final int width = wm.getDefaultDisplay().getWidth();
                final int height = wm.getDefaultDisplay().getHeight();
                params.y = height / 2 - params.height / 2;
                wm.addView(view, params);

                view.setOnTouchListener(new View.OnTouchListener() {
                    private int y;
                    private int x;

                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        switch (event.getAction()) {
                            case MotionEvent.ACTION_DOWN:
                                x = (int) event.getRawX();
                                y = (int) event.getRawY();
                                break;
                            case MotionEvent.ACTION_MOVE:
                                int minX = (int) (event.getRawX() - x);
                                int minY = (int) (event.getRawY() - y);
                                params.x = Math.min(width - params.width, Math.max(0, minX + params.x));
                                params.y = Math.min(height - params.height, Math.max(0, minY + params.y));
                                wm.updateViewLayout(view, params);
                                x = (int) event.getRawX();
                                y = (int) event.getRawY();
                                break;
                            case MotionEvent.ACTION_UP:
                                if (params.x > 0 && params.x < width - params.width) {
                                    int x = params.x;
                                    if (x > (width - params.width) / 2) {
                                        params.x = width - params.width;
                                    } else {
                                        params.x = 0;
                                    }
                                    wm.updateViewLayout(view, params);

                                } else if (params.x == 0 || params.x == (width - params.width)) {
                                    Toast.makeText(MainActivity.this, "被电击了", Toast.LENGTH_SHORT).show();
                                    tv.setText("更改了item的TextView值");
                                }
                                break;
                        }
                        return true;
                    }
                });
                Looper.loop();
            }
        }.start();
    }

}