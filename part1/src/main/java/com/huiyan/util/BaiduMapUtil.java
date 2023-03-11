package com.huiyan.util;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.InetAddress;
import java.net.URL;
import java.nio.charset.Charset;

public class BaiduMapUtil {

    public static String baidumap(){
        {
            InetAddress ia=null;
            try {
                ia=ia.getLocalHost();

                String localip=ia.getHostAddress();
//                61.191.143.255   安徽省芜湖市
//                61.191.146.255   安徽省阜阳市
//                61.191.126.255   安徽省宿州市
//                61.175.126.255   浙江省湖州市
//                61.175.147.255   浙江省衢州市
//                JSONObject json = readJsonFromUrl("http://api.map.baidu.com/location/ip?ak=fILVUFb5XGQn9xVqTY1Dou4lEgcampa9&ip="+localip+"&coor=bd09ll");
                JSONObject json = readJsonFromUrl("http://api.map.baidu.com/location/ip?ak=fILVUFb5XGQn9xVqTY1Dou4lEgcampa9&ip=101.69.7.40&coor=bd09ll");
                //    System.out.println(json.toString());
                System.out.println(((JSONObject) json.get("content")).get("address"));
                return (String) ((JSONObject) json.get("content")).get("address");
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }

        return null;
    }




    public static String readAll(Reader rd) throws IOException {

        StringBuilder sb = new StringBuilder();

        int cp;

        while ((cp = rd.read()) != -1) {

            sb.append((char) cp);
        }
        return sb.toString();

    }

    public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {

        InputStream is = new URL(url).openStream();

        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject json = new JSONObject(jsonText);
            return json;

        } finally {
            is.close();
        }

    }




}
