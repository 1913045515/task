package com.lzq.image.start;

import com.lzq.image.api.QueryImageAPI;
import com.lzq.image.helper.TheardHelper;
import com.lzq.image.service.QueryImageService;
import com.lzq.image.utils.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uw.dm.DAOFactory;
import uw.dm.DataSet;
import uw.dm.TransactionException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lzq on 2017/12/1.
 */
public class StartProject {
    private static final Logger log = LoggerFactory.getLogger(StartProject.class);
    private final static DAOFactory dao = DAOFactory.getInstance();

    //修改图片状态
    private void updateHotelState(String downloadPath) {
        List<String> arrayHotelID = FileUtils.statisticsHotel(downloadPath);
        System.out.println(arrayHotelID.size());
        for (String hotelId : arrayHotelID) {
            String sql = "UPDATE INTERFACE_HOTEL_IMPORT set STATE=1 " +
                    "where INTERFACE_INFO_ID='10005994' " +
                    "and VIEW_ID is null and STATE=0 and HOTEL_ID=?";
            System.out.println("update-->" + hotelId);
            System.out.println("执行sql-->" + sql);
            try {
                dao.executeCommand("saas", sql, new Object[]{hotelId});
            } catch (TransactionException e) {
                e.printStackTrace();
                log.error(e.getMessage(), e);
            }
        }
    }

    //下载图片到本地
    private void downloadImage(int threadNum,int pageSize,String downloadPath) {
        QueryImageAPI queryImageAPI = new QueryImageService();
        for (int i = 1; i <= threadNum; i++) {
            int start = (i - 1) * pageSize;
            String sql = "select *from INTERFACE_HOTEL_IMPORT where " +
                    "interface_info_id=10005994 and VIEW_ID is null and state=0";
            DataSet ds = null;
            try {
                ds = dao.queryForDataSet("saas", sql, start, pageSize, true);
            } catch (TransactionException e) {
                e.printStackTrace();
                log.error(e.getMessage(), e);
            }
            List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
            while (ds != null && ds.next()) {
                Map<String, Object> resultMap = new HashMap<String, Object>();
                String hotelName = ds.getString("name");
                String hotelId = ds.getString("hotel_id");
                resultMap.put("hotelName", hotelName);
                resultMap.put("hotelId", hotelId);
                resultList.add(resultMap);
            }
            System.out.println(resultList.size());
            if(resultList.size()!=0){
                TheardHelper.createThreadAndStart(resultList, downloadPath);
            }
        }
    }

    public static void main(String[] args) throws TransactionException {
        StartProject start = new StartProject();
        String downloadPath = "F:\\image\\";
        downloadPath += "10005994";
        start.updateHotelState(downloadPath);
        start.downloadImage(1,1000,downloadPath);
    }
}
