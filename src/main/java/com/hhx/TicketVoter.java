package com.hhx;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.http.Header;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class TicketVoter {
	
	public static void main(String[] args) {
     
		List<String> list = getLast9Person();
		votePersion(list);
	}
	
	
	public static List<String> getLast9Person(){
		String url="http://vote.e23.cn/zhuanti/jjrw15/zonghegr.jsp?group=1";
		List<String> retList = new ArrayList<String>();
		retList.add("58001");
		retList.add("58002");
		retList.add("58003");
		retList.add("58004");
		retList.add("58005");
		retList.add("58006");
		retList.add("58007");
		retList.add("58008");
		retList.add("58009");
		retList.add("58010");
		
//		Document doc = Jsoup.connect(url).get();
//        Elements contents = doc.getElementsByClass("arukone");
//        Elements datas = contents.get(0).getElementsByTag("table");
//        for (Element data : datas) 
//        {
//            Elements trs=data.getElementsByTag("tr");
//            for (int i = 0; i<trs.size(); i++) 
//            {
//                Elements tds = trs.get(i).getElementsByTag("td");
//                for(int j = 0; j<tds.size(); j++){
//                    if(!"".equals(tds.get(j).text())){
//                        System.out.println(tds.get(j).text()+","+i+","+j);
//                    }
//                }
//            }
//        }
			
		return retList;
		
	}
    
	public static void votePersion(List<String> votees){
		Map<String, Object> params = new ConcurrentHashMap<String, Object>();
		
		params.put("UserNo", votees);
		params.put("ServiceId", "ndjjrw58");
		params.put("act", "do");
		//params.put("x", "36");
		//params.put("y", "19");
		String ip="144.125.115.153";
		List<Header> headers = new ArrayList<Header>();
		headers.add(new BasicHeader("Host","vote.e23.cn"));
		headers.add(new BasicHeader("Origin","http://vote.e23.cn"));
		headers.add(new BasicHeader("Referer","http://vote.e23.cn/zhuanti/jjrw15/"));
		headers.add(new BasicHeader("REMOTE_ADDR",ip));
		headers.add(new BasicHeader("x-forwarded-for","210.125.115.153"));
		headers.add(new BasicHeader("Proxy-Client-IP",ip));
		headers.add(new BasicHeader("WL-Proxy-Client-IP",ip));
		
		String body = MyHttpClient.post("http://vote.e23.cn/zhuanti/jjrw15/vote.jsp", params,headers);
		
		System.out.println(body);
		
		
	
		
	}
	
}
