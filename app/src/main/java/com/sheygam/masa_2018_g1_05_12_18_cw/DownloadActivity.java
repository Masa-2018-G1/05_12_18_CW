package com.sheygam.masa_2018_g1_05_12_18_cw;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class DownloadActivity extends AppCompatActivity implements View.OnClickListener {
    private ProgressBar myProgress, horProgress;
    private TextView downloadTxt, totalCountTxt, downloadStatusTxt, doneTxt;
    private Button startBtn;
    private Handler handler;
    private int total;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handler = new Handler(callback);
        setContentView(R.layout.activity_download);
        myProgress = findViewById(R.id.myProgress);
        horProgress = findViewById(R.id.horProgress);
        downloadTxt = findViewById(R.id.downloadTxt);
        totalCountTxt = findViewById(R.id.totalCountTxt);
        downloadStatusTxt = findViewById(R.id.downloadStatusTxt);
        doneTxt = findViewById(R.id.doneTxt);
        startBtn = findViewById(R.id.startBtn);
        startBtn.setOnClickListener(this);
        horProgress.setVisibility(View.INVISIBLE);
        downloadTxt.setVisibility(View.INVISIBLE);
        downloadStatusTxt.setVisibility(View.INVISIBLE);
        myProgress.setVisibility(View.INVISIBLE);
        startBtn.setEnabled(true);
        doneTxt.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.startBtn) {
            new DownloadWorker(handler).start();
        }
    }

    private Handler.Callback callback = new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            boolean res = false;
            switch (msg.what) {
                case DownloadWorker.START:
                    myProgress.setVisibility(View.VISIBLE);
                    doneTxt.setVisibility(View.INVISIBLE);
                    startBtn.setEnabled(false);
                    horProgress.setVisibility(View.VISIBLE);
                    downloadTxt.setVisibility(View.VISIBLE);
                    downloadStatusTxt.setVisibility(View.VISIBLE);
                    downloadStatusTxt.setText("");
                    res = true;
                    break;
                case DownloadWorker.TOTAL:
                    total = msg.arg1;
                    totalCountTxt.setText(String.valueOf(total));
                    res = true;
                    break;
                case DownloadWorker.CURRENT:
                    downloadStatusTxt.setText(msg.arg1 + " / " + total);
                    res = true;
                    break;
                case DownloadWorker.PROGRESS:
                    horProgress.setProgress(msg.arg1);
                    res = true;
                    break;
                case DownloadWorker.END:
                    horProgress.setVisibility(View.INVISIBLE);
                    downloadTxt.setVisibility(View.INVISIBLE);
                    downloadStatusTxt.setVisibility(View.INVISIBLE);
                    myProgress.setVisibility(View.INVISIBLE);
                    startBtn.setEnabled(true);
                    doneTxt.setVisibility(View.VISIBLE);
                    res = true;
                    break;
            }
            return res;
        }
    };
}
