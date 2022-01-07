package com.leo.amsflag;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

/**
 * 测试 Intent.FLAG_ACTIVITY_FORWARD_RESULT 和 ActivityManager.START_FORWARD_AND_REQUEST_CONFLICT
 * 目的：想把CActivity的结果返回给AActivity
 */
//A startActivityForResult 想要从B 拿到C的结果
public class AActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a);

        // 测试 FLAG_ACTIVITY_FORWARD_RESULT 和 startActivityForResult
        findViewById(R.id.btn1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AActivity.this, BActivity.class);
                startActivityForResult(intent, 100);
            }
        });

        // 测试 FLAG_ACTIVITY_NEW_TASK 和 taskAffinity在同app时的情况
        findViewById(R.id.btn2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AActivity.this, DActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("Leo", "AActivity requestCode:" + requestCode + ", resultCode:" + resultCode);
    }
}
