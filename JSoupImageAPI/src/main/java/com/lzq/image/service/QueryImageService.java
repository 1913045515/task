package com.lzq.image.service;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lzq.image.api.QueryImageAPI;
import com.lzq.image.utils.FileUtils;
import com.lzq.image.utils.JsoupUtils;
import com.lzq.image.vo.JsoupImageVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Random;

/**
 * Created by lzq on 2017/11/28.
 */

@Component
public class QueryImageService implements QueryImageAPI {
    private static final Logger log = LoggerFactory.getLogger(QueryImageService.class);
    @Override
    public String queryImageByBaidu(String hotelName,String hotelId) {
        int page=1;
        List<JsoupImageVO> result=JsoupUtils.findImage(hotelName,hotelId,page);
        Gson gson = new Gson();
        String json = gson.toJson(result);
        return json;
    }

    @Override
    public boolean downloadImage(String imageJson,String locationPath) {
        Gson gson = new Gson();
        List<JsoupImageVO> jsoupImageVos = gson.fromJson(imageJson, new TypeToken<List<JsoupImageVO>>() {
        }.getType());
        Random random=new Random();
        if(jsoupImageVos.size()>0){
            int length=jsoupImageVos.size();
            int index=random.nextInt(length);
            return FileUtils.download(jsoupImageVos.get(index).getUrl(),jsoupImageVos.get(index).getName(),locationPath);
        }
        return false;
    }

    @Override
    public boolean queryImageAndDownload(String hotelName,String hotelId,String downloadPath) {
        String jsonResult = queryImageByBaidu(hotelName,hotelId);
        String[] choiceKey=new String[]{"酒店","房型","公寓",""};
        int choiceIndex=0;
        while("[]".equals(jsonResult)){
            //关键词没搜索到酒店，添加新的关键词
            hotelName=hotelName.split(" ")[0];
            jsonResult = queryImageByBaidu(hotelName+choiceKey[choiceIndex++],hotelId);
            if(choiceIndex>3){
                //关键词都搜索不到酒店的时候，将关键字改为酒店
                jsonResult = queryImageByBaidu("酒店",hotelId);
                break;
            }
        }
        boolean result=downloadImage(jsonResult, downloadPath);
        int count=0;
        while (result==false){
            result=downloadImage(jsonResult, downloadPath);
            count++;
            if(count>10){
                break;
            }
        }
        return result;
    }

    public static void main(String[] args) {
        Random random=new Random();
        for(int i=0;i<100;i++){
            int index=random.nextInt(3);
            System.out.println(index);
        }
    }
}
