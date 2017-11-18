package com.man.erpcenter.sales.biz.util;

import java.io.IOException;
import java.net.URLEncoder;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.net.ssl.SSLContext;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpRequest;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.man.erpcenter.common.utils.ObjectUtil;

public class HttpRequestUtil  {
	private static PoolingHttpClientConnectionManager cm = null;

	private static RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(15000).setConnectTimeout(15000)
			.setConnectionRequestTimeout(15000).build();
	
	static {
		LayeredConnectionSocketFactory sslsf = null;
		try {
			sslsf = new SSLConnectionSocketFactory(SSLContext.getDefault());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory> create()
				.register("https", sslsf).register("http", new PlainConnectionSocketFactory()).build();
		cm = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
		cm.setMaxTotal(2000*10);
		cm.setDefaultMaxPerRoute(50);
	}
	
	
	
	public static List<NameValuePair> parseParams(Map<String, Object> params) {
		List<NameValuePair> pairList = new ArrayList<NameValuePair>();
		if (null != params && params.size() > 0) {
			Set<String> keys = params.keySet();
			for (String key : keys) {
				Object obj = params.get(key);
				// 一个key对应多个参数
				if (null != obj && (obj instanceof Collection)) {
					Collection colParams = (Collection) obj;
					Iterator iterator = colParams.iterator();
					while (iterator.hasNext()) {
						Object paramObj = iterator.next();
						String val = ObjectUtil.toString(paramObj);
						if (null != paramObj && (paramObj instanceof String) && "null".equals(val)) {
							val = "";
						}
						pairList.add(new BasicNameValuePair(key, val));
					}
				} else {
					String objStr = ObjectUtil.toString(obj);
					if (null != obj && (obj instanceof String) && "null".equals(objStr)) {
						objStr = "";
					}
					pairList.add(new BasicNameValuePair(key, objStr));
				}
			}
		}
		return pairList;
	}
	
	/**
	 * 设置请求头信息
	 * 
	 * @author daixiaoman
	 * @date 2016年12月15日 上午9:32:51
	 */
	public static void setHeaders(HttpRequest request, Map<String, String> headersMap) {
		if (null != request && null != headersMap) {
			Set<String> keys = headersMap.keySet();
			for (String key : keys) {
				request.addHeader(key, headersMap.get(key));
			}
		}
	}
	
	public static void setPostFormParams(HttpPost post, Map<String, Object> params) {
		post.setEntity(new UrlEncodedFormEntity(parseParams(params), Consts.UTF_8));
	}
	
	

	private static CloseableHttpClient getHttpClient() {
		CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(cm).build();
		return httpClient;
	}

	public static String get(String url, String param) {
		// 创建默认的httpClient实例
		CloseableHttpClient httpClient = HttpRequestUtil.getHttpClient();
		CloseableHttpResponse httpResponse = null;
		// 发送get请求
		try {
			// 用get方法发送http请求
			HttpGet get = new HttpGet(url + URLEncoder.encode(param, "UTF-8"));
			httpResponse = httpClient.execute(get);
			// response实体
			HttpEntity entity = httpResponse.getEntity();
			if (null != entity) {
				String response = EntityUtils.toString(entity);
				int statusCode = httpResponse.getStatusLine().getStatusCode();
				if (statusCode == HttpStatus.SC_OK) {
					// 成功
					return response;
				} else {
					return null;
				}
			}
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} finally {
			if (httpResponse != null) {
				try {
					EntityUtils.consume(httpResponse.getEntity());
					httpResponse.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	
	
	public static String sendHttpPost(String url, Map<String, Object> params, Map<String, String> headersMap)
			throws IOException {
		CloseableHttpClient httpClient = null;
		CloseableHttpResponse response = null;
		HttpEntity entity = null;
		String responseContent = null;

		httpClient = getHttpClient();
		HttpPost post = new HttpPost(url);
		post.setConfig(requestConfig);
		// 设置参数
		setPostFormParams(post, params);
		// 设置头信息
		setHeaders(post, headersMap);
		try {
			response = httpClient.execute(post);
			entity = response.getEntity();
			responseContent = EntityUtils.toString(entity, Consts.UTF_8);
			return responseContent;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} finally {
			if (response != null) {
				try {
					EntityUtils.consume(response.getEntity());
					response.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
	
	
	
	
	
	
}
