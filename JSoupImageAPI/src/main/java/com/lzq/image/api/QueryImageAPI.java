package com.lzq.image.api;

import java.util.List;

/**
 * Created by lzq on 2017/11/28.
 */
public interface QueryImageAPI {
    public String queryImageByBaidu(String hotelName,String hotelId) ;
    public void downloadImage(String imageJson,String locationPath);
}
