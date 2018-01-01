package com.sheygam.masa_2018_g1_05_12_18_cw;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ProgressBar myProgress, horProgress;
    private Button clickBtn, secondBtn;
    private Handler handler, h1;
    private TextView resultTxt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        handler = new Handler(callback);
        h1 = new Handler();
        myProgress = findViewById(R.id.myProgress);
        horProgress = findViewById(R.id.horProgress);
        resultTxt = findViewById(R.id.resultTxt);
        clickBtn = findViewById(R.id.clickBtn);
        secondBtn = findViewById(R.id.secondBtn);
        secondBtn.setOnClickListener(this);
        clickBtn.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.download_item){
            Intent intent = new Intent(this,DownloadActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.clickBtn){
            new Worker(handler).start();
        }else if(v.getId() == R.id.secondBtn){
            myProgress.setVisibility(View.VISIBLE);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
//                    handler.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            myProgress.setVisibility(View.INVISIBLE);
//                        }
//                    });
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            myProgress.setVisibility(View.INVISIBLE);
                        }
                    });
                }
            }).start();
        }
    }

    private Handler.Callback callback = new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            boolean res = false;
            switch (msg.what){
                case 1:
                    myProgress.setVisibility(View.VISIBLE);
                    clickBtn.setEnabled(false);
                    res = true;
                    break;
                case 2:
                    myProgress.setVisibility(View.INVISIBLE);
                    clickBtn.setEnabled(true);
                    res = true;
                    break;
                case 3:
                    resultTxt.setText(String.valueOf(msg.arg1));
                    horProgress.setProgress(msg.arg1);
                    res = true;
                    break;
            }
            return res;
        }
    };
}
