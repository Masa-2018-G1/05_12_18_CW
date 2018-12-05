package com.sheygam.masa_2018_g1_05_12_18_cw;

import android.os.Handler;
import android.os.Message;

public class Worker extends Thread {
    private Handler handler;

    public Worker(Handler handler) {
        this.handler = handler;
    }

    @Override
    public void run() {
        handler.sendEmptyMessage(1);
        for (int i = 0; i < 100; i++) {
            Message msg = handler.obtainMessage(3);
            msg.arg1 = i+1;
            handler.sendMessage(msg);
            try {
                sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        handler.sendEmptyMessage(2);
    }
}
