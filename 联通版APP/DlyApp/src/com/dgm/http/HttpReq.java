package com.dgm.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.util.Log;

@SuppressWarnings("deprecation")
public class HttpReq {

	/**
	 * get 和 post 请求
	 */

	/**
	 * get请求数据
	 * @param name
	 * @param pasd
	 * @return
	 */
	public static Object toGetData(String url) {

		String str = "";

		HttpClient client = new DefaultHttpClient();
		HttpGet get = new HttpGet(url);

		try {
			HttpResponse response = client.execute(get);
			if (response.getStatusLine().getStatusCode() == 200) {
                str=EntityUtils.toString(response.getEntity(),"UTF-8");
                return str;
			}else{
				return "failed";
			}
			

		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			return "toGetData "+e.getMessage();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return "toGetData "+e.getMessage();
		}

	}
	/**
	 * 网络数据请求
	 * @return
	 */
	public static String RequestData(String url) {
		HttpGet get = new HttpGet(url);
		HttpClient client = new DefaultHttpClient();
		 client.getParams().setIntParameter(HttpConnectionParams.SO_TIMEOUT, 35000);//请求超时
		 client.getParams().setIntParameter(HttpConnectionParams.CONNECTION_TIMEOUT, 5000);//连接超时
		StringBuilder builder = null;
		try {
			HttpResponse response = client.execute(get);
			if (response.getStatusLine().getStatusCode() == 200) {
				InputStream inputStream = response.getEntity().getContent();
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(inputStream));
				builder = new StringBuilder();
				String s = null;
				for (s = reader.readLine(); s != null; s = reader.readLine()) {
					builder.append(s);
				}
			}
		}  catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			Log.e("Error", "Exception"+e.getMessage()); 
			return "{\"msg\":\"failed\",\"code\":404,\"data\":\"\"}";
		} catch (Exception e) {
			e.printStackTrace();
			 //result = "NetError";  
	        Log.e("Error", "Exception"+e.getMessage()); 
	         return "{\"msg\":\"failed\",\"code\":404,\"data\":\"\"}";
		}
		return builder.toString();
	}
	/**
	 * post 提交数据
	 * @param pairs
	 * @return
	 */
	public static Object topostData(String url,List<BasicNameValuePair> pairs) {
		String str = "";
		try {
			HttpClient client = new DefaultHttpClient();
			HttpPost httpPost=new HttpPost(url);
			 client.getParams().setIntParameter(HttpConnectionParams.SO_TIMEOUT, 35000);//请求超时
			 client.getParams().setIntParameter(HttpConnectionParams.CONNECTION_TIMEOUT, 5000);//连接超时
			UrlEncodedFormEntity entity=new UrlEncodedFormEntity(pairs,HTTP.UTF_8);
			httpPost.setEntity(entity);
			HttpResponse response = client.execute(httpPost);

			if (response.getStatusLine().getStatusCode() == 200) {
                str=EntityUtils.toString(response.getEntity(),"UTF-8");
			}else{
				return "{\"msg\":\"failed\",\"code\":404,\"data\":\"\"}";
			}
			return str;

		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			return "{\"msg\":\"failed\",\"code\":404,\"data\":\"\"}";
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return "{\"msg\":\"failed\",\"code\":404,\"data\":\"\"}";
		}

	}
	

}
