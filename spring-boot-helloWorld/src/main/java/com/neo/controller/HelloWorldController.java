package com.neo.controller;

import org.apache.http.protocol.HTTP;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;

import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@RestController
public class HelloWorldController {
    @Autowired
    private HelloWorldService service;

    @Value("${com.dudu.dingtalkURL}")
    private  String dingtalkURL;


    @RequestMapping("/hello")
    public String index() {
        return "Hello World";
    }

    public String doPost(String url, String map, String charset) {
        org.apache.http.client.HttpClient httpClient = null;
        HttpPost httpPost = null;
        String result = null;
        try {
            httpClient = new SSLClient();
            httpPost = new HttpPost(url);
            //设置参数
            httpPost.addHeader("Accept", "application/json");
            httpPost.addHeader("Content-Type", "application/json;charset=UTF-8");

            JSONObject jsonObj = new JSONObject();
            jsonObj.put("msgtype", "text");
            jsonObj.put("text","{\"content\": \""+map+"\"}");
            //jsonObj.put("text","{\"content\": \"我就是我, 是不一样的烟火\"}");

            StringEntity stringEntity = new StringEntity(jsonObj.toString(), HTTP.UTF_8);
            //StringEntity stringEntity = new StringEntity(map);

            stringEntity.setContentEncoding("UTF-8");
            stringEntity.setContentType("application/json");
            httpPost.setEntity(stringEntity);
            HttpResponse response = httpClient.execute(httpPost);
            if (response != null) {
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    result = EntityUtils.toString(resEntity, charset);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

    @RequestMapping(value = "/save",method = RequestMethod.GET)
    public String saveUser(@RequestParam("serach") String xx) {
        doPost(dingtalkURL,xx,"UTF-8");
        service.addMessage(xx);

        System.out.println(dingtalkURL);

        return "保存成功"+xx;
    }


    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public String addMessage(@RequestBody String requestBody){
        //xx=[{"scope":"Service","name":"llll","id0":6,"id1":0,"alarmMessage":"Successful rate of service llll is lower than 80% in 2 minutes of last 10 minutes","startTime":1556616019947}]
//        requestBody="[{/\"scope/\":/\"Service/\",/\"name/\":/\"llll/\",/\"id0/\":6,/\"id1/\":0,/\"alarmMessage/\":/\"Successful rate of service llll is lower than 80% in 2 minutes of last 10 minutes/\",/\"startTime/\":1556614399946}]";
//        try {
//            JSONObject jsonObj = (JSONObject)(new JSONParser().parse(requestBody));
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
        System.out.println(requestBody);
        String daf=requestBody.replace("[", "").replace("]", "").replace("{", "").replace("}", "").replace(":", "--").replace("\"", "");
        System.out.println(daf);
        //System.out.println(dingtalkURL);
        String result = doPost(dingtalkURL,daf,"UTF-8");
        System.out.println(result);
        return result;
    }
}
