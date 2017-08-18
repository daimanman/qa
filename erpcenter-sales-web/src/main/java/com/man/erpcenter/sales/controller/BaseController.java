package com.man.erpcenter.sales.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.man.erpcenter.sales.biz.util.ConstaintsUtil;
import com.man.erpcenter.sales.biz.util.ObjectUtil;

public class BaseController {
	/**
	 * 向前端发送json数据
	 * 
	 * @param response
	 * @param targetObj
	 * @throws IOException
	 */
	protected void sendJson(HttpServletResponse response, Object targetObj, SerializerFeature ...  sf) throws IOException {
		response.setContentType("application/json;charset=utf-8");
		OutputStream os = response.getOutputStream();
		IOUtils.write(JSON.toJSONString(targetObj,sf), os, "utf-8");
		IOUtils.closeQuietly(os);
	}
	
	protected void sendJsonWithDateFormat(HttpServletResponse response, Object targetObj,String dateFormat, SerializerFeature ...  sf) throws IOException {
		response.setContentType("application/json;charset=utf-8");
		OutputStream os = response.getOutputStream();
		IOUtils.write(JSON.toJSONStringWithDateFormat(targetObj,dateFormat,sf), os, "utf-8");
		IOUtils.closeQuietly(os);
	}
	
	protected Map<String,Object> getReqParams(HttpServletRequest request1){
		
			Map<String, Object> paramMap = new HashMap<String, Object>();

			/**
			 * key-value 参数
			 */
			Map<String, String[]> requestParam = request1.getParameterMap();
			Set<String> keys = requestParam.keySet();
			for (String key : keys) {
				String[] ps = requestParam.get(key);
				
				if (ps.length > 1 || key.endsWith("[]")) {
					key = key.substring(0,key.length()-2);
					List<String> listStr = new ArrayList<String>();
					for(String p:ps){
						listStr.add(ObjectUtil.toString(p,"").trim());
					}
					paramMap.put(key, listStr);
				} else {
					paramMap.put(key, ObjectUtil.toString(ps[0],"").trim());
				}
			}

			/**
			 * 解析json 格式参数 application/json
			 */
			try {
				InputStream is = request1.getInputStream();
				if (null != is) {
					String jsonString = IOUtils.toString(is, "utf-8");
					if(null != jsonString && !"".equals(jsonString.trim())){
						Map<String, Object> jsonMapParam = JSON.parseObject(jsonString, Map.class);
						paramMap.putAll(jsonMapParam);
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}finally{
				return paramMap;
			}

			
		}
}
