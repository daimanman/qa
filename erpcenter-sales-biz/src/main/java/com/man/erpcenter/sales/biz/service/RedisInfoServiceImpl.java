package com.man.erpcenter.sales.biz.service;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;

import com.man.erpcenter.sales.client.service.RedisInfoService;

public class RedisInfoServiceImpl implements RedisInfoService {

	private static final Logger logger = LoggerFactory.getLogger(RedisInfoServiceImpl.class);

	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	/**
	 * reids get by key
	 * 
	 * @param key
	 * @return
	 */
	public String get(String key) {
		try {
			BoundValueOperations<String, String> operations = stringRedisTemplate.boundValueOps(key);
			return operations.get();
		} catch (Exception e) {
			logger.error("redis key={},", key, e);
		}
		return null;
	}

	/**
	 * reids set
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean set(String key, String value) {
		try {
			BoundValueOperations<String, String> operations = stringRedisTemplate.boundValueOps(key);
			operations.set(value);
			return true;
		} catch (Exception e) {
			logger.error("redis key={}", key, e);
		}finally {
			
		}
		return false;
	}

	/**
	 * set 设置过期时间,支持事物控制
	 * 
	 * @param key
	 * @param value
	 * @param timeout
	 * @return
	 */
	public boolean set(final String key, final String value, final long timeout) {
		try {
			boolean result = stringRedisTemplate.execute(new RedisCallback<Boolean>() {
				public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
					Boolean result = connection.setNX(key.getBytes(), value.getBytes());
					if (result == false)
						return result;
					if (timeout > 0)
						connection.expire(key.getBytes(), timeout);
					return result;
				}
			});
			return result;
		} catch (Exception e) {
			logger.error("redis key={}", key, e);
		}
		return false;

	}

	/**
	 * 删除
	 * 
	 * @param key
	 * @return
	 */
	public boolean del(String key) {
		try {
			stringRedisTemplate.delete(key);
			return true;
		} catch (Exception e) {
			logger.error("redis key={}", key, e);
		}
		return false;
	}

	/**
	 * 计数器
	 * 
	 * @param key
	 * @param value
	 *            long
	 * @return
	 */
	public Long incr(String key, long value) {
		try {
			return stringRedisTemplate.opsForValue().increment(key, value);
		} catch (Exception e) {
			logger.error("redis key={}", key, e);
		}
		return (long) 0;
	}

	/**
	 * 队列 压栈
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public Long leftPush(String key, String value) {
		try {
			return stringRedisTemplate.opsForList().leftPush(key, value);
		} catch (Exception e) {
			logger.error("redis key={}", key, e);
		}
		return null;
	}

	/**
	 * 出队
	 * 
	 * @param key
	 * @return
	 */
	public String rightPop(String key) {
		try {
			return stringRedisTemplate.opsForList().rightPop(key);
		} catch (Exception e) {
			logger.error("redis key={}", key, e);
		}
		return null;
	}

	/**
	 * 队列size
	 * 
	 * @param key
	 * @return
	 */
	public Long listSize(String key) {
		try {
			return stringRedisTemplate.opsForList().size(key);
		} catch (Exception e) {
			logger.error("redis key={}", key, e);
		}
		return null;
	}

	/**
	 * 设置过期时间
	 * 
	 * @param key
	 * @param time
	 *            默认是秒
	 * @return
	 */
	public boolean expire(String key, long time) {
		try {
			Boolean result = stringRedisTemplate.expire(key, time, TimeUnit.SECONDS);
			if (result != null) {
				return result.booleanValue();
			}
		} catch (Exception e) {
			logger.error("redis key={}", key, e);

		}
		return false;
	}

	/**
	 * 设置set集合操作
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public Long addSet(String key, String value) {
		try {
			return stringRedisTemplate.opsForSet().add(key, value);
		} catch (Exception e) {
			logger.error("redis key={}", key, e);
		}
		return null;
	}

	/**
	 * 获取
	 * 
	 * @param key
	 * @return
	 */
	public Set<String> members(String key) {
		try {
			return stringRedisTemplate.opsForSet().members(key);
		} catch (Exception e) {
			logger.error("redis key={},", key, e);
		}
		return null;
	}

}
