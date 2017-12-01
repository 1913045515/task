package com.lzq.image.utils;
import com.lzq.image.start.StartProject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lzq on 2017/11/28.
 */
public class FileUtils {
    private static final Logger log = LoggerFactory.getLogger(FileUtils.class);
    public static void delMultyFile(String path){
        File file = new File(path);
        if(!file.exists())
            throw new RuntimeException("File \""+path+"\" NotFound when excute the method of delMultyFile()....");
        File[] fileList = file.listFiles();
        File tempFile=null;
        for(File f : fileList){
            if(f.isDirectory()){
                delMultyFile(f.getAbsolutePath());
            }else{
                if(f.length()==0)
                    LogUtils.writeLog(f.delete()+"---"+f.getName());
            }
        }
    }

    //根据图片网络地址下载图片
    public static boolean download(String url,String name,String path){
        File file= null;
        File dirFile=null;
        FileOutputStream fos=null;
        HttpURLConnection httpCon = null;
        URLConnection con = null;
        URL urlObj=null;
        InputStream in =null;
        byte[] size = new byte[1024];
        int num=0;
        try {
            dirFile = new File(path);
            if(dirFile.exists()){
                dirFile.delete();
            }
            dirFile.mkdir();
            file = new File(path+"//"+name+".jpg");
            fos = new FileOutputStream(file);
            if(url.startsWith("http")){
                urlObj = new URL(url);
                con = urlObj.openConnection();
                httpCon =(HttpURLConnection) con;
                in = httpCon.getInputStream();
                while((num=in.read(size)) != -1){
                    for(int i=0;i<num;i++)
                        fos.write(size[i]);
                }
            }
        }catch (FileNotFoundException notFoundE) {
            LogUtils.writeLog("找不到该网络图片....");
            return false;
        }catch(NullPointerException nullPointerE){
            LogUtils.writeLog("找不到该网络图片....");
        }catch(IOException ioE){
            LogUtils.writeLog("产生IO异常.....");
            return false;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }finally{
            try {
                fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    public static List<String> statisticsHotel(String nativePath){
        List<String> arrayHotelID=new ArrayList<String>();
        File fileDirectory = new File(nativePath);
        if(!fileDirectory.exists())
            throw new RuntimeException(nativePath+"is NotFound");
        File[] fileList = fileDirectory.listFiles();
        for(File file : fileList){
            if(!file.isDirectory()){
                if(file.length()==0){
                    file.delete();
                }else{
                    String fileName=file.getName();
                    fileName=fileName.split("[.]")[0];
                    arrayHotelID.add(fileName);
                }
            }
        }
        return arrayHotelID;
    }

    public static void initDirectory(String fileLocation){
        File f = new File(fileLocation);
        if (f.exists()) {
            f.delete();
        }
        f.mkdirs();
    }

    public static void main(String[] args) {
        String downloadPath = "F:\\image\\";
        downloadPath+="test";
        int size=FileUtils.statisticsHotel(downloadPath).size();
        System.out.println(size);
    }
}
