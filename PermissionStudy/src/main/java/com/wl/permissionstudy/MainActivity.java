package com.wl.permissionstudy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
/**
 * @param null
 * @method:
 * @description: <p>
 * <p>
 * <p>
 *
 * ##### 危险权限
 *  <!-- 权限组：CALENDAR == 日历读取的权限申请 -->
 *     <uses-permission android:name="android.permission.READ_CALENDAR" />
 *     <uses-permission android:name="android.permission.WRITE_CALENDAR" />
 *
 *     <!-- 权限组：CAMERA == 相机打开的权限申请 -->
 *     <uses-permission android:name="android.permission.CAMERA" />
 *
 *     <!-- 权限组：CONTACTS == 联系人通讯录信息获取/写入的权限申请 -->
 *     <uses-permission android:name="android.permission.READ_CONTACTS" />
 *     <uses-permission android:name="android.permission.WRITE_CONTACTS" />
 *
 *     <!-- 权限组：LOCATION == 位置相关的权限申请 -->
 *     <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
 *     <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
 *
 *     <!-- 权限组：PHONE == 拨号相关的权限申请 -->
 *     <uses-permission android:name="android.permission.CALL_PHONE" />
 *     <uses-permission android:name="android.permission.READ_PHONE_STATE" />
 *
 *     <!-- 权限组：SMS == 短信相关的权限申请 -->
 *     <uses-permission android:name="android.permission.SEND_SMS" />
 *     <uses-permission android:name="android.permission.READ_SMS" />
 *
 *     <!-- 权限组：STORAGE == 读取存储相关的权限申请 -->
 *     <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
 *     <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
 *
 *
 * ##### 核心函数介绍
 * ```
 * ContextCompat.checkSelfPermission
 * 检查应用是否具有某个危险权限。如果应用具有此权限，方法将返回 PackageManager.PERMISSION_GRANTED，并且应用可以继续操作。
 * 如果应用不具有此权限，方法将返回 PackageManager.PERMISSION_DENIED，且应用必须明确向用户要求权限。
 * ```
 * <p>
 * ```
 * ActivityCompat.requestPermissions
 * 应用可以通过这个方法动态申请权限，调用后会弹出一个对话框提示用户授权所申请的权限。
 * ```
 * <p>
 * ```
 * ActivityCompat.shouldShowRequestPermissionRationale
 * 如果应用之前请求过此权限但用户拒绝了请求，此方法将返回 true。如果用户在过去拒绝了权限请求，
 * 并在权限请求系统对话框中选择了 Don't ask again 选项，此方法将返回 false。如果设备规范禁止应用具有该权限，此方法也会返回 false。
 * ```
 * <p>
 * ```
 * onRequestPermissionsResult
 * 当应用请求权限时，系统将向用户显示一个对话框。当用户响应时，系统将调用应用的 onRequestPermissionsResult() 方法，向其传递用户响应，处理对应的场景。
 * ```
 * </p>
 *
 * ##### 动态权限实例演示demo
 *  第一步：在AndroidManifest.xml中添加所需权限。
 *  第二步：封装了一个requestPermission方法来动态检查和申请权限
 *  第三步：重写onRequestPermissionsResult方法根据用户的不同选择做出响应。
 *
 * @return
 * @date: 2022-02-23 13:31
 * @author: wangli
 */

public class MainActivity extends AppCompatActivity {
    private static String TAG = "PermissionStudy";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestPermission();
    }

    private void requestPermission() {
        Log.i(TAG, "requestPermission");
       int permissionflag = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS);
       if(permissionflag != PackageManager.PERMISSION_GRANTED){  //还没有权限
          boolean canRequest = ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_CONTACTS);
          if(canRequest){
              ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS},1000);
          }else{
              ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS},1000);
          }

       }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 1000: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.i(TAG,"onRequestPermissionsResult granted");
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {
                    Log.i(TAG,"onRequestPermissionsResult denied");
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    showWaringDialog();
                }
                return;
            }

        }
    }
    // 如果点击 拒绝，就会弹出这个
    private void showWaringDialog() {
        new AlertDialog.Builder(this)
                .setTitle("警告！")
                .setMessage("请前往设置->应用->PermissionDemo->权限中打开相关权限，否则功能无法正常运行！")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 一般情况下如果用户不授权的话，功能是无法运行的，做退出处理
                        finish();
                    }
                }).show();
    }
}
