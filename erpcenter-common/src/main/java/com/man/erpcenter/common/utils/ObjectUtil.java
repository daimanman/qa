package com.man.erpcenter.common.utils;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 工具操作类
 * @author daixiaoman
 * @date 2016年11月30日
 */
public class ObjectUtil {

	public static Integer parseInteger(Object src)
	{
		Integer num = null;
		if(null != src )
		{
			try{
				num = Integer.parseInt(src.toString());
			}catch(Exception e){
				e.printStackTrace();
				num = null;
			}
			
		}
		return num;
	}
	
	public static int parseInt(Object src){
		Integer i = parseInteger(src);
		return i != null ? i : 0;
	}
	public static Double parseDouble(Object src){
			return Double.parseDouble(ObjectUtil.toString(src,"0"));
	}
	
	public static long parseLong(Object src){
		long num = 0L;
		try{
		num = Long.parseLong(ObjectUtil.toString(src,"0"));
		}catch(Exception e){
			num = 0L;
		}
		return num;
	}
	public static Integer parseInteger(Object src,int defauleNum){
		Integer num = parseInteger(src);
		return num != null ? num : defauleNum;
	}
	public static  boolean isNull(Object src){
		return src == null || "".equals(src.toString().trim());
	}
	public static String toString(Object src,String ... planStr){
		if(!isNull(src)){
			return src.toString();
		}else{
			if( planStr != null && planStr.length > 0){
				for(String str:planStr){
					if(!isNull(str)){
						return str;
					}
				}
			}
		}
		return "";
	}
	
	public static int getSize(Object obj){
		if(isNull(obj)){
			return 0;
		}
		if(obj instanceof Collection){
			Collection col = (Collection)obj;
			return col.size();
		}
		if(obj instanceof Map){
			Map map = (Map)obj;
			return map.size();
		}
		if(obj instanceof Object[]){
			Object[] objs = (Object[])obj;
			return objs.length;
		}
		return 0;
	}
	
	public static Map castMapObj(Object obj){
		if(isNull(obj)){
			return null;
		}
		if(obj instanceof Map){
			return (Map)obj;
		}
		return new HashMap();
	}
	
	public static List castListObj(Object obj){
		if(isNull(obj)){
			return null;
		}
		if(obj instanceof List){
			return (List)obj;
		}
		
		return null;
	}
	
	public static int multiply(Object ... nums){
		int o = 1;
		if(null != nums && nums.length > 0){
			for(Object no:nums){
				Integer inum = parseInteger(no);
				if(null != inum){
					o *= inum;
				}
			}
		}
		return o;
	}
	
	
}
