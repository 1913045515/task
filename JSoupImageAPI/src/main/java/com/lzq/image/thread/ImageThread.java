package com.lzq.image.thread;

import com.lzq.image.api.QueryImageAPI;
import com.lzq.image.service.QueryImageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * Created by lzq on 2017/12/1.
 */
public class ImageThread implements Runnable {
    private static final Logger log = LoggerFactory.getLogger(ImageThread.class);
    //    @Autowired
    private QueryImageAPI queryImageAPI;
    private List<Map<String, Object>> parameList;
    private String downloadPath;

    public ImageThread(List<Map<String, Object>> parameList, String downloadPath) {
        this.parameList = parameList;
        this.downloadPath = downloadPath;
        queryImageAPI = new QueryImageService();
    }

    @Override
    public void run() {
        try {
            if (parameList != null) {
                for (Map<String, Object> paramMap : parameList) {
                    String hotelName = paramMap.get("hotelName") + "";
                    String hotelId = paramMap.get("hotelId") + "";
                    queryImageAPI.queryImageAndDownload(hotelName, hotelId, downloadPath);
                    Thread.sleep(10);
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
}
