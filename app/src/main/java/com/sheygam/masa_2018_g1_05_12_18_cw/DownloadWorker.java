package com.sheygam.masa_2018_g1_05_12_18_cw;

import android.os.Handler;
import android.os.Message;

import java.util.Random;

public class DownloadWorker extends Thread {
    private Handler handler;
    public static final int START = 0x01;
    public static final int END = 0x02;
    public static final int TOTAL = 0x03;
    public static final int CURRENT = 0x04;
    public static final int PROGRESS = 0x05;

    public DownloadWorker(Handler handler) {
        this.handler = handler;
    }

    @Override
    public void run() {
        handler.sendEmptyMessage(START);
        int count = new Random().nextInt(15);
        Message msg = handler.obtainMessage(TOTAL);
        msg.arg1 = count+1;
        handler.sendMessage(msg);
        for (int i = 0; i < count; i++) {
            msg = handler.obtainMessage(CURRENT);
            msg.arg1 = i+1;
            handler.sendMessage(msg);
            for (int j = 0; j < 100; j++){
                msg = handler.obtainMessage(PROGRESS);
                msg.arg1 = j+1;
                handler.sendMessage(msg);
                try {
                    sleep(30);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        handler.sendEmptyMessage(END);
    }
}
