package com.man.erpcenter.sales.biz.manager;

import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;

import com.man.erpcenter.sales.biz.mapper.QemotCommentMapper;
import com.man.erpcenter.sales.biz.mapper.QemotInfoMapper;
import com.man.erpcenter.sales.biz.mapper.QmsgInfoMapper;

public class QqPrimaryKeyManager {

	@Autowired
	private QmsgInfoMapper qmsgInfoMapper;
	
	@Autowired
	private QemotInfoMapper  qemotInfoMapper;
	
	@Autowired
	private QemotCommentMapper qemotCommentMapper;
	
	public ConcurrentHashMap<String,Long> msgIdLock = new ConcurrentHashMap<String,Long>();
	
	public ConcurrentHashMap<String,Long> emotIdLock = new ConcurrentHashMap<String,Long>();
	
	public ConcurrentHashMap<String,Long> emotCommentIdLock = new ConcurrentHashMap<String,Long>();
	
	private long startMsgId;
	
	private long startEmotId;
	
	private long startEmotCommentId;
	
	public void initPrimaryKey(){
		startMsgId = qmsgInfoMapper.getMaxId();
		startEmotId = qemotInfoMapper.getMaxId();
	}
	
	public long getNextMsgId(){
		synchronized (msgIdLock) {
			if(startMsgId == 0){
				startMsgId = qmsgInfoMapper.getMaxId();
			}
			startMsgId++;
			return startMsgId;
		}
	}
	
	public long getNextEmotCommentId(){
		synchronized(emotCommentIdLock){
			if(startEmotCommentId == 0){
				startEmotCommentId = qemotCommentMapper.getMaxId();
			}
			startEmotCommentId++;
			return startEmotCommentId;
		}
	}
	
	public long getNextEmotId(){
		synchronized(emotIdLock){
			if(startEmotId == 0){
				startEmotId = qemotInfoMapper.getMaxId();
			}
			startEmotId++;
			return startEmotId;
		}
	}

	public long getStartMsgId() {
		return startMsgId;
	}

	public long getStartEmotId() {
		return startEmotId;
	}
	
	
	
	
}
