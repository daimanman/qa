package com.man.erpcenter.sales.client.service;

import java.util.Set;

public interface RedisInfoService {

	public String get(String key);

	public boolean set(String key, String value);
	
	public boolean set(final String key, final String value, final long timeout);
	
	public boolean del(String key);
	
	public Long incr(String key, long value);
	
	public Long leftPush(String key, String value);
	
	public String rightPop(String key);
	
	public Long listSize(String key);
	
	public boolean expire(String key, long time);
	
	public Long addSet(String key, String value);
	
	public Set<String> members(String key);
	
	
	
	
}
