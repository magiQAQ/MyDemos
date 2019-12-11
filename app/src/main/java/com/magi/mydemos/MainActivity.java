package com.magi.mydemos;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.magi.mydemos.udp.UdpClientBiz;

public class MainActivity extends AppCompatActivity {

    private EditText editText;
    private Button button;
    private TextView textView;

    private UdpClientBiz udpClientBiz = new UdpClientBiz();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initEvent();
    }

    private void initView() {
        editText = findViewById(R.id.editText);
        button = findViewById(R.id.button);
        textView = findViewById(R.id.textView);
    }

    private void initEvent() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = editText.getText().toString();
                if (TextUtils.isEmpty(message)) {
                    return;
                }

                textView.append("client:" + message + "\n");

                udpClientBiz.sendMessage(message, new UdpClientBiz.OnMessageReturnedListener() {
                    @Override
                    public void onMessageReturned(final String message) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                textView.append("server:" + message + "\n");
                            }
                        });
                    }

                    @Override
                    public void onException(Exception exception) {
                        exception.printStackTrace();
                    }
                });

            }
        });
    }

    @Override
    protected void onDestroy() {
        udpClientBiz.onDestory();
        super.onDestroy();
    }
}
