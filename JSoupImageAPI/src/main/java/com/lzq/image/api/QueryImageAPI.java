package com.lzq.image.api;

import java.util.List;
import java.util.Map;

/**
 * Created by lzq on 2017/11/28.
 */
public interface QueryImageAPI {
    public String queryImageByBaidu(String hotelName,String hotelId) ;
    public boolean downloadImage(String imageJson,String locationPath);
    public boolean queryImageAndDownload(String hotelName,String hotelId,String downloadPath);
}
