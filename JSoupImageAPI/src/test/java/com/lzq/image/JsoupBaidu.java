package com.lzq.image;

import com.lzq.image.api.QueryImageAPI;
import com.lzq.image.service.QueryImageService;
import com.lzq.image.utils.JsoupUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// 爬取百度图片
public class JsoupBaidu {
    public static void main(String[] args) throws Exception {
        String downloadPath = "F:\\image\\1234";
        QueryImageAPI queryImageAPI = new QueryImageService();
        String jsonResult = queryImageAPI.queryImageByBaidu("去呼呼大酒店","123452");
        System.out.println(jsonResult);
        queryImageAPI.downloadImage(jsonResult,downloadPath);
    }
}