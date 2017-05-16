package com.spring.cloud.book.test.util;

import com.spring.cloud.book.test.util.constant.URLType;

import javax.net.ssl.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @Title: HttpUtil.java
 * @Description: 云收银台http工具类
 * @Author: 温亦汝 @Date 2015年9月29日
 * @version V1.0
 */
public class HttpUtil {

	/**
	 * 
	 * 
	 * @Description: http请求工具类，完成HTTP请求
	 * @Author: 温亦汝 @Date: 2015年9月29日
	 * @LastEditTime:
	 * @param urlStr
	 *            请求URL
	 * @param requestMessage
	 *            请求报文
	 * @param urlType
	 *            请求URL的格式，
	 * @return
	 * @throws Exception
	 */
	public static String request(String urlStr, String requestMessage, URLType urlType) throws Exception {
		return request(urlStr, requestMessage, urlType, "UTF-8", null);
	}
	
	public static String request(String urlStr, String requestMessage, URLType urlType, String chartSet, Map<String,String> headers) throws Exception {
		if (URLType.HTTPS.equals(urlType)) {
			return requestHttps(requestMessage, urlStr,chartSet);
		}
		String result = "";
		URL url = new URL(urlStr);
		HttpURLConnection conn = null;
		OutputStream writer = null;
		InputStream inputStream = null;
		try {
			conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(60000);
			conn.setReadTimeout(60000);
			conn.setUseCaches(false);
			
			//消息头  
			if(headers==null){
				conn.setRequestProperty("Content-Type", "text/xml");
			}else{
				for (Entry<String, String> entry : headers.entrySet()) {
					conn.setRequestProperty(entry.getKey(), entry.getValue());
				}
			}
			
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			writer = conn.getOutputStream();
			
			if(chartSet==null ||chartSet.trim().length()==0){
				chartSet = "UTF-8";
			}
			writer.write(requestMessage.getBytes(chartSet));
			System.out.println("------------------向外发送" + "请求------------------");
			System.out.println("请求URL:" + url);

			System.out.println("请求报文体：" + requestMessage);
			System.out.println("-------------------------------------------------------------");
			String responseStr = "", sCurrentLine = "";
			if (conn.getResponseCode() == 200) {
				inputStream = conn.getInputStream();
				BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, chartSet));

				while ((sCurrentLine = reader.readLine()) != null) {
					responseStr = responseStr + sCurrentLine;
				}
			}
			System.out.println("响应内容：" + responseStr);
			if (!"".equals(responseStr)) {
				result = responseStr;
			}
		} catch (IOException e) {
			throw e;
		} finally {
			if (conn != null) {
				conn.disconnect();
			}
			try {
				if (writer != null) {
					writer.flush();
					writer.close();
				}
				if (inputStream != null) {
					inputStream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	
	public static void main(String[] args) throws Exception {
		String urlStr = "http://127.0.0.1:8080/jcloudpay-web/bankCallback/k4U9dR";
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("content-type", "application/x-www-form-urlencoded; charset=GBK");
		request(urlStr,"user=李刚&pass=abc",URLType.HTTP,"GBK",headers);
	}
	
	
	private static String requestHttps(String message, String urlStr, String charSet) {
		String result = "";
		try {
			TrustManager tm = new X509TrustManager() {
				public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
				}

				public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
				}

				public X509Certificate[] getAcceptedIssuers() {
					return null;
				}
			};
			 HostnameVerifier hv = new HostnameVerifier() {
			 public boolean verify(String urlHostName, SSLSession session) {
			 return urlHostName.equals(session.getPeerHost());
			 }
			 };
			SSLContext sslContext = SSLContext.getInstance("TLS");
			sslContext.init(null, new TrustManager[] { tm }, null);

			HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
			 HttpsURLConnection.setDefaultHostnameVerifier(hv);
			HttpsURLConnection conn = (HttpsURLConnection) (new URL(urlStr)).openConnection();
			conn.setRequestProperty("Charset", charSet);
			conn.setRequestProperty("Content-Type", "text/xml");
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			OutputStream urlOutputStream = conn.getOutputStream();
//			String params = "params="+message;
			urlOutputStream.write(message.getBytes(charSet));
			urlOutputStream.close();
			System.out.println("请求报文体：" + message);
			System.out.println("-------------------------------------------------------------");
			String responseStr = "", sCurrentLine = "";
			if (conn.getResponseCode() == 200) {
				InputStream inputStream = conn.getInputStream();
				BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, charSet));

				while ((sCurrentLine = reader.readLine()) != null) {
					responseStr = responseStr + sCurrentLine;
				}
				result = responseStr;
			}
			System.out.println("响应内容：" + responseStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
