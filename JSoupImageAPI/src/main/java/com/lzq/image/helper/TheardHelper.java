package com.lzq.image.helper;

import com.lzq.image.thread.ImageThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * Created by lzq on 2017/12/1.
 */
public class TheardHelper {
    private static final Logger log = LoggerFactory.getLogger(TheardHelper.class);
    public static void createThreadAndStart(List<Map<String, Object>> parameList, String downloadPath) {
        Runnable imageRunnable = new ImageThread(parameList, downloadPath);
        Thread thread = new Thread(imageRunnable);
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        thread.start();
    }

    public static void main(String[] args) {

    }
}
