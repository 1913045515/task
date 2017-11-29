package com.lzq.image.service;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lzq.image.api.QueryImageAPI;
import com.lzq.image.utils.FileUtils;
import com.lzq.image.utils.JsoupUtils;
import com.lzq.image.utils.LogUtils;
import com.lzq.image.vo.JsoupImageVO;
import org.apache.commons.lang3.StringEscapeUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by lzq on 2017/11/28.
 */

@Component
public class QueryImageService implements QueryImageAPI {
    @Override
    public String queryImageByBaidu(String hotelName,String hotelId) {
        int page=1;
        List<JsoupImageVO> result=JsoupUtils.findImage(hotelName,hotelId,page);
        Gson gson = new Gson();
        String json = gson.toJson(result);
        return json;
    }

    @Override
    public void downloadImage(String imageJson,String locationPath) {
        Gson gson = new Gson();
        List<JsoupImageVO> jsoupImageVos = gson.fromJson(imageJson, new TypeToken<List<JsoupImageVO>>() {
        }.getType());
        for (JsoupImageVO jsoupImageVO : jsoupImageVos) {
            FileUtils.download(jsoupImageVO.getUrl(),jsoupImageVO.getName(),locationPath);
            break;
        }
    }
}
