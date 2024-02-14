package radius.voice.assistant.Services;



import org.json.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Random;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import radius.voice.assistant.Utility.Utility;




public class SwitchCases {
	@Autowired
	private static SwitchCases instance;
	private static final String encoding = "UTF-8";
	public static SwitchCases getInstance() {
		
		/**
		 * Check for the null
		 */
		
		if (instance == null) {
			instance = new SwitchCases();
		}
		return instance;
	
	    }  
	
	
	
	public String functions(int func_no , String key ) {
		
		String ans ;
		 
		switch(func_no) {
			case 1 :
				
				ans = "no logic" ;
				 return ans ;
					
		
			case 2 : 
				ans = "testing " ;
				return ans ;
					

			case 3 : 
//				 Timestamp timestamp = new Timestamp(System.currentTimeMillis());
//				ans = String.valueOf(timestamp.getTime())  ;
				ans=  "Time is "+ LocalDateTime.now().getHour()+" hours "+LocalDateTime.now().getMinute()+" minutes";
				
					System.out.println("answer is" + ans);
				return ans ;
				
			case 4 : 
				LocalDate currentdate = LocalDate.now();
				ans = "Date is "+ currentdate.getDayOfMonth() +" "+ currentdate.getMonth() + " " +currentdate.getYear()  ;
				return ans ;
					
					
		}
		
		return " no answer function available " ;
		
	}

	public String callingApi(int func_no , String key , String url ) {
		
		RestTemplate rest = new RestTemplate() ; 
		
		String ans ;
		switch(func_no) {
			case 1 :
				try {
				System.out.println("calliingg thiss ") ; 
				String uri = "https://www.twilio.com/blog/5-ways-to-make-http-requests-in-java";	
				
				Object[] a = rest.getForObject(uri, Object[].class) ;
				
				}catch (Exception e) { System.out.println(e); } 
				 ans = "no logic api" ;
				 return ans ;
					
		
			case 2 : 
				ans = "testing api" ;
				 return ans ;
				
				
				
	}
	
	return " no answer from api function available " ;
		
		
		
	}
	
	
public String gSearch(String query) throws UnsupportedEncodingException, IOException {
	long s1 = System.currentTimeMillis();
	System.out.println(query);
    URL url = new URL("https://en.wikipedia.org/w/api.php?action=query&list=search&srsearch="+query.replace(" ","%20")+"&format=json");
    System.out.println(url) ;
    HttpURLConnection conn = (HttpURLConnection)url.openConnection(); 
    conn.setRequestMethod("GET") ;
    conn.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
    conn.setRequestProperty("Content-Type", "application/json");

    conn.connect() ; 
    
    BufferedReader in = new BufferedReader(
            new InputStreamReader(conn.getInputStream()));
    String inputLine;
    StringBuffer response = new StringBuffer();
    while ((inputLine = in.readLine()) != null) {
    	response.append(inputLine);
    }
     
    in.close() ;
    System.out.println(response.toString());
    JSONObject myResponse = new JSONObject(response.toString());
    System.out.println(myResponse) ;
    
//    Random ran = new Random() ; 
    try {
    String xml = myResponse.getJSONObject("query").getJSONArray("search").getJSONObject(1).getString("snippet") ; 
    String textt= xml.replaceAll("<[^>]*>", "");
    System.out.println(textt);
    textt=textt.replaceAll("&quot"," ") ;
	System.out.println(textt) ;
	
//	try {
//		OkHttpClient client = new OkHttpClient().newBuilder()
//				  .build();
//				Request request = new Request.Builder()
//				  .url("https://api.duckduckgo.com/?q=what is cricket&format=json&pretty=1&no_html=1&skip_disambig=1")
//				  .method("GET", null)
//				  .build();
//				Response response = client.newCall(request).execute();
//			System.out.println(response);
//		    JSONObject myResponse = new JSONObject(response.toString());
//		    System.out.println(myResponse) ;
	 System.out.println("++++++"+Utility.apiMill(s1,  System.currentTimeMillis()));  
    return textt;
    }
    catch(Exception e) {  
    	System.out.println(e) ;
    }
	return "Sorry unable to assist you , could you please ask again " ;
	
    
	}	
	
}
