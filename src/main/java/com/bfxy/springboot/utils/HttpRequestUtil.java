package com.bfxy.springboot.utils;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.Map;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class HttpRequestUtil {
	
	public static String doJsonPost(String urlPath, String Json) {
		 System.out.println("data="+Json);
	        String result = "";
	        BufferedReader reader = null;
	        try {
	            URL url = new URL(urlPath);
	            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	            conn.setRequestMethod("POST");
	            conn.setDoOutput(true);
	            conn.setDoInput(true);
	            conn.setUseCaches(false);
	            conn.setRequestProperty("Connection", "Keep-Alive");
	            conn.setRequestProperty("Charset", "UTF-8");
	            // 设置文件类型:
//	            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
	            conn.setRequestProperty("Content-Type","application/json; charset=UTF-8");
	            // 设置接收类型否则返回415错误
//	            conn.setRequestProperty("accept","*/*");//此处为暴力方法设置接受所有类型，以此来防范返回415;
	            conn.setRequestProperty("accept","application/json");
//	            conn.setRequestProperty("accept","application/x-www-form-urlencoded");
	            
//	            conn.setRequestProperty("accept","multipart/form-data;charset=utf-8");
	            
	            // 往服务器里面发送数据
	            conn.setDoInput(true);
	            if (Json != null &&!"".equals(Json)) {
	                byte[] writebytes = Json.getBytes();
	                // 设置文件长度
	                conn.setRequestProperty("Content-Length", String.valueOf(writebytes.length));
	                conn.setDoOutput(true);
	                OutputStream outwritestream = conn.getOutputStream();
	                outwritestream.write(Json.getBytes());
	                outwritestream.flush();
	                outwritestream.close();
	            }
	            if (conn.getResponseCode() == 200) {
	                reader = new BufferedReader(
	                        new InputStreamReader(conn.getInputStream()));
	                result = reader.readLine();
	                System.out.println("resultSucess="+result);
	            }else{
	            	reader = new BufferedReader(
	                        new InputStreamReader(conn.getInputStream()));
	                result = reader.readLine();
	                System.out.println("resultFailure="+result);
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        } finally {
	            if (reader != null) {
	                try {
	                    reader.close();
	                } catch (IOException e) {
	                    e.printStackTrace();
	                }
	            }
	        }
	        return result;
	}
	public static String doformUrlencodedPost(String urlPath) {
	        String result = "";
	        BufferedReader reader = null;
	        try {
	            URL url = new URL(urlPath);
	            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	            conn.setRequestMethod("POST");
	            conn.setDoOutput(true);
	            conn.setDoInput(true);
	            conn.setUseCaches(false);
	            conn.setRequestProperty("Connection", "Keep-Alive");
	            conn.setRequestProperty("Charset", "UTF-8");
	            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
	            // 设置接收类型否则返回415错误
//	            conn.setRequestProperty("accept","*/*");//此处为暴力方法设置接受所有类型，以此来防范返回415;
	            // 往服务器里面发送数据
	            conn.setDoInput(true);
	            if (conn.getResponseCode() == 200) {
	                reader = new BufferedReader(
	                        new InputStreamReader(conn.getInputStream()));
	                result = reader.readLine();
	                System.out.println("resultSucess="+result);
	            }else{
	            	reader = new BufferedReader(
	                        new InputStreamReader(conn.getInputStream()));
	                result = reader.readLine();
	                System.out.println("resultFailure="+result);
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        } finally {
	            if (reader != null) {
	                try {
	                    reader.close();
	                } catch (IOException e) {
	                    e.printStackTrace();
	                }
	            }
	        }
	        return result;
	}
	/**
	 * 带参数的get请求
	 * @param url
	 * @param param
	 * @return String
	 */
	public static String doGet(String url, Map<String, String> param) {
		// 创建Httpclient对象
		CloseableHttpClient httpclient = HttpClients.createDefault();

		String resultString = "";
		CloseableHttpResponse response = null;
		try {
			// 创建uri
			URIBuilder builder = new URIBuilder(url);
			if (param != null) {
				for (String key : param.keySet()) {
					builder.addParameter(key, param.get(key));
				}
			}
			URI uri = builder.build();
			// 创建http GET请求
			HttpGet httpGet = new HttpGet(uri);
			// 执行请求
			response = httpclient.execute(httpGet);
			// 判断返回状态是否为200
			if (response.getStatusLine().getStatusCode() == 200) {
				resultString = EntityUtils.toString(response.getEntity(), "UTF-8");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (response != null) {
					response.close();
				}
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return resultString;
	}
	
	public static String doPost(String url, Map<String, String> param) {
		// 创建Httpclient对象
		CloseableHttpClient httpclient = HttpClients.createDefault();

		String resultString = "";
		CloseableHttpResponse response = null;
		try {
			// 创建uri
			URIBuilder builder = new URIBuilder(url);
			if (param != null) {
				for (String key : param.keySet()) {
					builder.addParameter(key, param.get(key));
				}
			}
			URI uri = builder.build();
			// 创建http GET请求
			HttpPost httpGet = new HttpPost(uri);
			// 执行请求
			response = httpclient.execute(httpGet);
			// 判断返回状态是否为200
			if (response.getStatusLine().getStatusCode() == 200) {
				resultString = EntityUtils.toString(response.getEntity(), "UTF-8");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (response != null) {
					response.close();
				}
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return resultString;
	}
	
	
	private void httpURLGETCase(String methodUrl) {
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        String line = null;
        try {
            URL url = new URL(methodUrl + "?text=15334567890&name=zhansan");
            connection = (HttpURLConnection) url.openConnection();// 根据URL生成HttpURLConnection
            connection.setRequestMethod("GET");// 默认GET请求
            connection.connect();// 建立TCP连接
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));// 发送http请求
                StringBuilder result = new StringBuilder();
                // 循环读取流
                while ((line = reader.readLine()) != null) {
                    result.append(line).append(System.getProperty("line.separator"));// "\n"
                }
                System.out.println(result.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            connection.disconnect();
        }
    }
	
	public static String httpURLPOSTCase(String methodUrl) {
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        String line = null;
        try {
            URL url = new URL(methodUrl);
            connection = (HttpURLConnection) url.openConnection();// 根据URL生成HttpURLConnection
            connection.setRequestMethod("POST");// 默认GET请求
            connection.connect();// 建立TCP连接
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));// 发送http请求
                StringBuilder result = new StringBuilder();
                // 循环读取流
                while ((line = reader.readLine()) != null) {
                    result.append(line).append(System.getProperty("line.separator"));// "\n"
                }
                return result.toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            connection.disconnect();
        }
        return null;
    }
}
