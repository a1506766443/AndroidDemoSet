package com.wl.amsplugin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
//启动没有注册的activity

/**
 * Hook技术 -- 反射、动态代理 -- 改变原有执行流程
 *
 * 插桩
 * 1.尽量找 静态变量 单例
 * 2.public
 *
 * 动态代理
 * msg.obj --> ClientTransaction --> List<ClientTransactionItem> mActivityCallbacks(LaunchActivityItem)
 * --> private Intent mIntent 替换
 *
 * new Callback  ---> 替换系统的 Callback
 *
 *
 *
 * 反射、动态代理、Activity启动流程
 *
 * 逃避AMS检测
 *
 * google限制了
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        HookUtil.hookAMS();
        HookUtil.hookHandler();

        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 启动插件的Activity
                Intent intent = new Intent();
                intent.setComponent(new ComponentName("com.leo.amsplugin",
                        "com.leo.amsplugin.UnregisteredActivity"));

//                intent.setComponent(new ComponentName("com.leo.amsplugin",
//                        "com.leo.amsplugin.ProxyActivity"));
                startActivity(intent);
            }
        });
    }

}
