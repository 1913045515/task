package com.lzq.image.utils;

import com.lzq.image.vo.JsoupImageVO;
import org.apache.commons.lang3.StringEscapeUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by lzq on 2017/11/28.
 */

public class JsoupUtils {
    private static final Logger log = LoggerFactory.getLogger(JsoupUtils.class);
    public static List<JsoupImageVO> findImage(String hotelName, String hotelId, int page) {
        int number=8;
        String url = "http://image.baidu.com/search/avatarjson?tn=resultjsonavatarnew&ie=utf-8&word=" + hotelName + "&cg=star&pn=" + page * 30 + "&rn="+number+"&itg=0&z=2&cl=2&fr=&width=&height=&lm=7&ic=0&s=0&st=-1&gsm=" + Integer.toHexString(page * 30);
        int timeOut = 5000;
        return findImageNoURl(hotelId, url, timeOut);
    }

    private static List<JsoupImageVO> findImageNoURl(String hotelId, String url, int timeOut) {
        List<JsoupImageVO> result = new ArrayList<JsoupImageVO>();
        Document document = null;
        try {
            document = Jsoup.connect(url).data("query", "Java")//请求参数
                    .userAgent("Mozilla/4.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0)")//设置urer-agent  get();
                    .timeout(timeOut)
                    .get();
            String xmlSource = document.toString();
            result = dealResult(xmlSource, hotelId);
        } catch (Exception e) {
            String defaultURL = "http://qnimg.zowoyoo.com/img/15463/1509533934407.jpg";
            result = dealResult(defaultURL,hotelId);
        }
        return result;
    }

    private static List<JsoupImageVO> dealDeaultResult(String imageURL, String hotelId) {
        List<JsoupImageVO> result = new ArrayList<JsoupImageVO>();
        JsoupImageVO jsoupImageVO = new JsoupImageVO();
        jsoupImageVO.setUrl(imageURL);
        jsoupImageVO.setName(hotelId);
        result.add(jsoupImageVO);
        return result;
    }

    private static List<JsoupImageVO> dealResult(String xmlSource, String hotelId) {
        List<JsoupImageVO> result = new ArrayList<JsoupImageVO>();
        xmlSource = StringEscapeUtils.unescapeHtml3(xmlSource);
        String reg = "objURL\":\"http://.+?\\.(gif|jpeg|png|jpg|bmp)";
        Pattern pattern = Pattern.compile(reg);
        Matcher m = pattern.matcher(xmlSource);
        while (m.find()) {
            JsoupImageVO jsoupImageVO = new JsoupImageVO();
            String imageURL = m.group().substring(9);
            if(imageURL==null || "".equals(imageURL)){
                String defaultURL = "http://qnimg.zowoyoo.com/img/15463/1509533934407.jpg";
                jsoupImageVO.setUrl(defaultURL);
            }else{
                jsoupImageVO.setUrl(imageURL);
            }
            jsoupImageVO.setName(hotelId);
            result.add(jsoupImageVO);
        }
        return result;
    }
}
