package com.magi.mydemos;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.magi.mydemos.tcp.TcpClientBiz;

public class TcpActivity extends AppCompatActivity {

    private EditText editText;
    private Button button;
    private TextView textView;

    private TcpClientBiz tcpClientBiz = new TcpClientBiz();

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
                String message = editText.getText().toString();
                if (TextUtils.isEmpty(message)) {
                    return;
                }

                textView.append("client:" + message + "\n");

                tcpClientBiz.sendMessage(message);
            }
        });

        tcpClientBiz.setOnMsgComingListener(new TcpClientBiz.OnMessageComingListener() {
            @Override
            public void onMsgComing(final String message) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        textView.append(message+"\n");
                    }
                });
            }

            @Override
            public void onError(Exception ex) {
                ex.printStackTrace();
            }
        });
    }


    @Override
    protected void onDestroy() {
        tcpClientBiz.onDestroy();
        super.onDestroy();
    }
}
