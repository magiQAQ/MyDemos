package com.magi.mydemos;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.magi.mydemos.http.HttpUtils;
import com.magi.mydemos.tcp.TcpClientBiz;

public class HttpActivity extends AppCompatActivity {

    private EditText editText;
    private Button button;
    private TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        initEvents();

    }

    private void initViews() {
        editText = findViewById(R.id.editText);
        button = findViewById(R.id.button);
        textView = findViewById(R.id.textView);
    }

    private void initEvents() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = editText.getText().toString();
                if (TextUtils.isEmpty(url)) {
                    return;
                }

                HttpUtils.doGet(HttpActivity.this, url, new HttpUtils.OnResponseListener() {
                    @Override
                    public void onSuccess(String content) {
                        textView.setText(content);
                    }

                    @Override
                    public void onFail(Exception e) {
                        e.printStackTrace();
                    }
                });

            }
        });


    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
