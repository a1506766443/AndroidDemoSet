package com.leo.amsflag;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

//B 作为过渡页在跳转C时 添加了FLAG_ACTIVITY_FORWARD_RESULT，flag 将会使 C返回的结果不经过B 而直接到A
public class BActivity extends AppCompatActivity {

    private Button mButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b);

        mButton = (Button) findViewById(R.id.btn);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BActivity.this, CActivity.class);
                // 从现有Activity启动新Activity，现有Activity的回复目标将转移到新Activity
                // FLAG_ACTIVITY_FORWARD_RESULT 希望 不接受返回结果，而startActivityForResult 是希望获取结果，相互矛盾
                intent.addFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT);
                startActivity(intent);
//                startActivityForResult(intent, 1000);
//                finish();
            }
        });
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        setResult(resultCode);
//        Log.e("Leo", "AActivity requestCode:" + requestCode + ", resultCode:" + resultCode);
//    }
}
