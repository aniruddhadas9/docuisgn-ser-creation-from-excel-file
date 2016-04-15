package com.etouch.docusign.users;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

//import com.etouch.auth.TokenBySAMLAsserertion;

public class DocusignUser {
	
	
	
	public JSONObject createUser(String xml) {
		
		JSONObject responseJson = null;
		//JSONObject samlTokenJson = new TokenBySAMLAsserertion().getToken();
		InputStream inputStream = null;

		try {
			HttpClient httpclient = new DefaultHttpClient();
			String url = HtppResponseHelper.createUserUrl;
			HttpPost httpPost = new HttpPost(url);
			httpPost.addHeader("Accept", "application/json");
			httpPost.addHeader("Content-Type", "application/xml");
			httpPost.addHeader("Authorization", HtppResponseHelper.token);
			//httpPost.addHeader("Authorization", samlTokenJson.getString("token_type")+" "+samlTokenJson.getString("access_token"));
			
			HttpEntity entity = new ByteArrayEntity(xml.getBytes("UTF-8"));
			httpPost.setEntity(entity);

			HttpResponse httpResponse = httpclient.execute(httpPost);
			
			System.out.println("----------HTTP Request headers start--------------");
			for(int i = 0; i < httpPost.getAllHeaders().length; i++){
				System.out.println(httpPost.getAllHeaders()[i].getName()+"-->"+httpPost.getAllHeaders()[i].getValue());
			}
			System.out.println("----------HTTP Request headers end--------------");
			System.out.println("----------HTTP Response headers start--------------");
			for(int i = 0; i < httpResponse.getAllHeaders().length; i++){
				System.out.println(httpResponse.getAllHeaders()[i].getName()+"-->"+httpResponse.getAllHeaders()[i].getValue());
			}
			System.out.println("----------HTTP Response headers end--------------");

			// 9. receive response as inputStream
			inputStream = httpResponse.getEntity().getContent();
			String response = HtppResponseHelper.getResponseBody(inputStream);
			System.out.println("response: "+response);
			
			responseJson = (JSONObject) new JSONObject(response);
			

		} catch (ClientProtocolException e) {
			System.out.println("ClientProtocolException : " + e.getLocalizedMessage());
		} catch (IOException e) {
			System.out.println("IOException:" + e.getLocalizedMessage());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception:" + e.getLocalizedMessage());
		}

		return responseJson;
	}

	public JSONObject deleteUser(String userIdJson) {
		
		JSONObject responseJson = null;
		//JSONObject samlTokenJson = new TokenBySAMLAsserertion().getToken();
		InputStream inputStream = null;

		try {
			HttpClient httpclient = new DefaultHttpClient();
			String url = HtppResponseHelper.createUserUrl;
			MyHttpDelete httpPost = new MyHttpDelete(url);
			//HttpPost httpPost = new HttpPost(url);
			httpPost.addHeader("Accept", "application/json");
			httpPost.addHeader("Content-Type", "application/json");
			httpPost.addHeader("Authorization", HtppResponseHelper.token);
			//httpPost.addHeader("Authorization", samlTokenJson.getString("token_type")+" "+samlTokenJson.getString("access_token"));
			
			HttpEntity entity = new ByteArrayEntity(userIdJson.getBytes("UTF-8"));
			httpPost.setEntity(entity);
			
			System.out.println("requestbody::"+httpPost.getEntity().getContent());

			HttpResponse httpResponse = httpclient.execute(httpPost);
			
			System.out.println("----------HTTP Request headers start--------------");
			for(int i = 0; i < httpPost.getAllHeaders().length; i++){
				System.out.println(httpPost.getAllHeaders()[i].getName()+"-->"+httpPost.getAllHeaders()[i].getValue());
			}
			System.out.println("----------HTTP Request headers end--------------");
			System.out.println("----------HTTP Response headers start--------------");
			for(int i = 0; i < httpResponse.getAllHeaders().length; i++){
				System.out.println(httpResponse.getAllHeaders()[i].getName()+"-->"+httpResponse.getAllHeaders()[i].getValue());
			}
			System.out.println("----------HTTP Response headers end--------------");
			
			

			// 9. receive response as inputStream
			inputStream = httpResponse.getEntity().getContent();
			String response = HtppResponseHelper.getResponseBody(inputStream);
			System.out.println("response: "+response);
			
			responseJson = (JSONObject) new JSONObject(response);
			

		} catch (ClientProtocolException e) {
			System.out.println("ClientProtocolException : " + e.getLocalizedMessage());
		} catch (IOException e) {
			System.out.println("IOException:" + e.getLocalizedMessage());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception:" + e.getLocalizedMessage());
		}

		return responseJson;
	}
	
	class MyHttpDelete extends HttpPost {
		public MyHttpDelete (String url){
	        super(url);
	    }
	    @Override
	    public String getMethod() {
	        return "DELETE";
	    }
	}
	

}
