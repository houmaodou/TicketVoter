/**
 * 
 */
package com.hhx;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.codec.binary.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

/**
 * @author andy_hou
 *
 */
public class MyHttpClient {
	
	private static Logger log = Logger.getLogger(MyHttpClient.class);  
    
    public static String post(String url, Map<String, Object> params,List<Header> headers) {  
    	CloseableHttpClient httpclient = HttpClientBuilder.create().build();  
        String body = null;  
          
        log.info("create httppost:" + url);  
        HttpPost post = postForm(url, params,headers);  
          
        body = invoke(httpclient, post);  
          
        httpclient.getConnectionManager().shutdown();  
          
        return body;  
    }  
      
    public static String get(String url) {  
        DefaultHttpClient httpclient = new DefaultHttpClient();  
        String body = null;  
          
        log.info("create httppost:" + url);  
        HttpGet get = new HttpGet(url);  
        body = invoke(httpclient, get);  
          
        httpclient.getConnectionManager().shutdown();  
          
        return body;  
    }  
          
      
    private static String invoke(HttpClient httpclient,  
            HttpUriRequest httpost) {  
          
        HttpResponse response = sendRequest(httpclient, httpost);  
        String body = paseResponse(response);  
          
        return body;  
    }  
  
    private static String paseResponse(HttpResponse response) {  
        log.info("get response from http server..");  
        HttpEntity entity = response.getEntity();  
          
        log.info("response status: " + response.getStatusLine());  
        String charset = EntityUtils.getContentCharSet(entity);  
        log.info(charset);  
          
        String body = null;  
        try {  
            body = EntityUtils.toString(entity);  
            log.info(body);  
        } catch (ParseException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
          
        return body;  
    }  
  
    private static HttpResponse sendRequest(HttpClient httpclient,  
            HttpUriRequest httpost) {  
        log.info("execute post...");  
        HttpResponse response = null;  
          
        try {  
            response = httpclient.execute(httpost);  
        } catch (ClientProtocolException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        return response;  
    }  
  
    private static HttpPost postForm(String url, Map<String, Object> params,List<Header> headers){  
          
        HttpPost httpost = new HttpPost(url);  
        List<NameValuePair> nvps = new ArrayList <NameValuePair>();  
          
        Set<String> keySet = params.keySet();  
        for(String key : keySet) {
        	
        	   Object value = params.get(key);
        	   if(value instanceof String){
        		   nvps.add(new BasicNameValuePair(key, (String)value));  
        	   }else if(value instanceof List){
              	   
        		   for(Object temp:(List)value){
        			   if(temp!=null){
        				   nvps.add(new BasicNameValuePair(key, temp.toString())); 
        			   } 
        	   }
        	   
        	}   
        }  
        if(headers!=null){
        	for(Header header:headers){
        		httpost.addHeader(header);
        	}
        }
        
        System.out.println(nvps.toString());
          
        try {  
            log.info("set utf-8 form entity to httppost");
            httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));  
        } catch (UnsupportedEncodingException e) {  
            e.printStackTrace();  
        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
          
        return httpost;  
    }  

}
