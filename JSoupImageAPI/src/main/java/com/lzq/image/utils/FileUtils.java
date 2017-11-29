package com.lzq.image.utils;
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
    public static void download(String url,String name,String path){
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
        }catch(NullPointerException nullPointerE){
            LogUtils.writeLog("找不到该网络图片....");
        }catch(IOException ioE){
            LogUtils.writeLog("产生IO异常.....");
        }catch (Exception e) {
            e.printStackTrace();
        }finally{
            try {
                fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void initDirectory(String fileLocation){
        File f = new File(fileLocation);
        if (f.exists()) {
            f.delete();
        }
        f.mkdirs();
    }
}
