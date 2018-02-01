package com.man.erpcenter.sales.biz.mapper;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.ibatis.annotations.Param;

import com.man.erpcenter.sales.client.mqvo.QemotInfoMqVo;
import com.man.erpcenter.sales.client.po.QuserInfoRetry;

public interface QuserInfoPoMapper {

	void addQuserInfo(Map<String, Object> params);

	void addQuserInfoN(Map<String, Object> params);

	void addQemotInfo(Map<String, Object> params);

	long getMaxUid();

	long getMinUid();

	public void updateImgPhotoNum(QemotInfoMqVo qemotInfoMqVo);

	public void batchInsertUidsN(@Param("uids") Set<String> uids);

	public List<QuserInfoRetry> getMsgAllQuserInfoRetry();

	public List<QuserInfoRetry> getMsgAllQuserInfoRetryWithId(@Param("startId") long startId,
			@Param("endId") long endId);

	public List<QuserInfoRetry> getEmotAllQuserInfoRetry();

	public int updateQuserInfoRetry(@Param("id") long id);

	public int updateEmotFlagQuserInfoRetry(@Param("id") long id);

	public int updateMsgFlagQuserInfoRetry(@Param("id") long id);

	public int addUserVistorBatch(Map<String, Object> params);

	public List<Map<String, Object>> queryList(Map<String, Object> bizParams);

	public int queryListCount(Map<String, Object> bizParams);

}