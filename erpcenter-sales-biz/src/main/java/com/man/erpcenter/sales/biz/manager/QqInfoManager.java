package com.man.erpcenter.sales.biz.manager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jboss.netty.util.internal.ConcurrentHashMap;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.alibaba.rocketmq.client.exception.MQBrokerException;
import com.dxm.mqservice.mq.MsgSenderService;
import com.man.erpcenter.sales.biz.mapper.QemotCommentMapper;
import com.man.erpcenter.sales.biz.mapper.QemotCommentReplyMapper;
import com.man.erpcenter.sales.biz.mapper.QemotInfoMapper;
import com.man.erpcenter.sales.biz.mapper.QemotPicMapper;
import com.man.erpcenter.sales.biz.mapper.QmsgInfoMapper;
import com.man.erpcenter.sales.biz.mapper.QmsgInfoReplyMapper;
import com.man.erpcenter.sales.biz.mapper.QphotoImgMapper;
import com.man.erpcenter.sales.biz.mapper.QphotoInfoMapper;
import com.man.erpcenter.sales.biz.mapper.QuserInfoPoMapper;
import com.man.erpcenter.sales.biz.util.ConverQqEntity;
import com.man.erpcenter.sales.biz.util.ObjectUtil;
import com.man.erpcenter.sales.client.constant.MqMsgInfoEnum;
import com.man.erpcenter.sales.client.mqvo.QemotInfoMqVo;
import com.man.erpcenter.sales.client.mqvo.UserInfoVo;
import com.man.erpcenter.sales.client.po.QuserInfoPo;
import com.man.erpcenter.sales.client.po.QuserInfoRetry;

public class QqInfoManager extends BaseQqManager {

	@Autowired
	private QemotCommentMapper qemotCommentMapper;

	@Autowired
	private QemotCommentReplyMapper qemotCommentReplyMapper;

	@Autowired
	private QemotInfoMapper qemotInfoMapper;

	@Autowired
	private QemotPicMapper qemotPicMapper;

	@Autowired
	private QuserInfoPoMapper quserInfoPoMapper;

	@Autowired
	private QmsgInfoMapper qmsgInfoMapper;

	@Autowired
	private QmsgInfoReplyMapper qmsgInfoReplyMapper;

	@Autowired
	private QphotoInfoMapper qphotoInfoMapper;

	@Autowired
	private QphotoImgMapper qphotoImgMapper;

	@Autowired
	private MsgSenderService msgSenderService;

	@Autowired
	private QqPrimaryKeyManager primaryKeyManager;
	
	public List<QuserInfoRetry> quserRetryList;

	private static ConcurrentHashMap<String, Object> lockMap = new ConcurrentHashMap<String, Object>();

	public static SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public static Set<String> nuidsSet = new HashSet<String>();

	public void addQuserInfoN(Map<String, Object> params) {
		quserInfoPoMapper.addQuserInfoN(params);
	}

	public void addUidN(String uid) {
		synchronized (lockMap) {
			nuidsSet.add(uid);
			if (nuidsSet.size() >= 300) {
				System.out.println("nuids -------------size --" + nuidsSet.size());
				quserInfoPoMapper.batchInsertUidsN(nuidsSet);
				nuidsSet = new HashSet<String>();
			}
		}
	}

	// 新增对象
	public void addQuserInfo(UserInfoVo userInfoVo) {
		Map<String, Object> params = userInfoVo.getUserInfoMap();
		quserInfoPoMapper.addQuserInfo(params);
		QemotInfoMqVo qemotInfoMqVo = new QemotInfoMqVo();
		qemotInfoMqVo.setUid(userInfoVo.uid);
		try {
			qemotInfoMqVo.setQuerInfoId((Long) params.get("id"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// 异步通知Msg
		if (msgUids.size() > 0) {
			try{
			msgSenderService.sendMessage(qemotInfoMqVo, MqMsgInfoEnum.ADD_MSG.topic, MqMsgInfoEnum.ADD_MSG.tags);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
	}
	
	public void addQuserVisitor(String uid){
		System.out.println("--add visit--- start ---"+uid);
		Map<String,Object> contentMap = getQzoneVisitInfoContent(uid);
		if(null == contentMap){
			System.out.println("getVisist is nul -"+uid);
			return;
		}
		if(ObjectUtil.toString(contentMap.get("message")).indexOf("没有权限访问") >= 0){
			System.out.println("--add visit--- end ---"+uid+"-----"+JSON.toJSONString(contentMap));
			QemotInfoMqVo qemotInfoMqVo = new QemotInfoMqVo();
			qemotInfoMqVo.uid = uid;
			
			msgSenderService.sendMessage(qemotInfoMqVo, MqMsgInfoEnum.ADD_PHOTO.topic,
					MqMsgInfoEnum.ADD_PHOTO.tags);
			return;
		}
		Map<String,Object> dataMap = ObjectUtil.castMapObj(contentMap.get("data"));
		int size = ObjectUtil.getSize(dataMap.get("items"));
		
		System.out.println(uid+"--visit-size--"+size+"----visituidsSize---"+visitUids.size());
		if(size >= 10){
			QemotInfoMqVo qemotInfoMqVo = new QemotInfoMqVo();
			qemotInfoMqVo.uid = uid;
			
			msgSenderService.sendMessage(qemotInfoMqVo, MqMsgInfoEnum.ADD_PHOTO.topic,
					MqMsgInfoEnum.ADD_PHOTO.tags);
		}
		if(null != dataMap &&   size > 0){
			//批量插入
			try{
				dataMap.put("muid",uid);
			quserInfoPoMapper.addUserVistorBatch(dataMap);
			}catch(Exception e ){
				e.printStackTrace();
			}
		}
	}

	// 处理单个
	public Map<String, Object> dealEmotinfos(long querInfoId, String uid) {

		Map<String, Object> emotMap = getQzoneEmotInfoContent(uid, 0);

		return emotMap;
	}

	public void insertEmotIntoDB(QemotInfoMqVo qemotInfoMqVo) {

		if (null == qemotInfoMqVo) {
			return;
		}

		String uid = qemotInfoMqVo.uid;
		long quserInfoId = qemotInfoMqVo.querInfoId;

		Map<String, Object> emotParam = qemotInfoMqVo.qemotInfoMap;
		if (null == emotParam) {
			return;
		}
		List msgList = (List) emotParam.get("msglist");

		if (ObjectUtil.getSize(msgList) > 0) {
			for (Object msgInfo : msgList) {
				// Main Info
				Map<String, Object> msgInfoMap = ObjectUtil.castMapObj(msgInfo);
				if (ObjectUtil.toString(msgInfoMap.get("content")).trim().length() == 0) {
					continue;
				}
				try {
					qemotInfoMapper.insertQemotInfo(msgInfoMap);
				} catch (Exception e) {
					System.out.println("uid -------------" + uid + "-----" + msgInfoMap.get("content"));
					e.printStackTrace();
					continue;
				}

				// id
				long emotId = (Long) msgInfoMap.get("qemotInfoId");

				// imgs信息
				int pictotal = ObjectUtil.parseInteger(msgInfoMap.get("pictotal"));
				if (pictotal > 0) {
					try {
						if (ObjectUtil.getSize(msgInfoMap.get("pic")) > 0) {
							qemotPicMapper.insertBatchPics(msgInfoMap);
						}
					} catch (Exception e) {
						e.printStackTrace();
						continue;
					}
				}

				// comment List
				List commentlist = ObjectUtil.castListObj(msgInfoMap.get("commentlist"));
				if (ObjectUtil.getSize(commentlist) > 0) {
					for (Object comment : commentlist) {
						Map<String, Object> commentMap = ObjectUtil.castMapObj(comment);
						commentMap.put("qemotInfoUin", uid);
						commentMap.put("qemotInfoId", emotId);
						try {
							qemotCommentMapper.insertQemotComment(commentMap);
						} catch (Exception e) {
							e.printStackTrace();
							continue;
						}

						// commentReply dos
						List list_3 = ObjectUtil.castListObj(commentMap.get("list_3"));
						if (ObjectUtil.getSize(list_3) > 0) {
							try {
								qemotCommentReplyMapper.insertBatchCommentReply(commentMap);
							} catch (Exception e) {
								e.printStackTrace();
								continue;
							}
						}
					}
				}

			}
		}

	}

	public void insertEmotIntoDBBatch(QemotInfoMqVo qemotInfoMqVo) {

		if (null == qemotInfoMqVo) {
			return;
		}

		String uid = qemotInfoMqVo.uid;
		long quserInfoId = qemotInfoMqVo.querInfoId;

		Map<String, Object> emotParam = qemotInfoMqVo.qemotInfoMap;
		if (null == emotParam) {
			return;
		}
		// 列表信息
		List msgList = (List) emotParam.get("msglist");
		
		List msgListBatch = new ArrayList();

		// pic
		List picListBatch = new ArrayList();

		// comment
		List commentListBatch = new ArrayList();

		// comment reply
		List commentReplyListBatch = new ArrayList();

		if (ObjectUtil.getSize(msgList) > 0) {
			for (Object msgInfo : msgList) {
				// Main Info
				Map<String, Object> msgInfoMap = ObjectUtil.castMapObj(msgInfo);

				if (ObjectUtil.toString(msgInfoMap.get("content")).trim().length() == 0) {
					continue;
				}
				
				// 手动设置主键
				long emotId = primaryKeyManager.getNextEmotId();
				msgInfoMap.put("qemotInfoId", emotId);
				
				msgListBatch.add(ConverQqEntity.converQemotInfo(msgInfoMap));

				// imgs信息
				int pictotal = ObjectUtil.parseInteger(msgInfoMap.get("pictotal"),0);

				if (pictotal > 0) {
					List picList = ObjectUtil.castListObj(msgInfoMap.get("pic"));
					if (ObjectUtil.getSize(picList) > 0) {
						for (Object picObj : picList) {
							Map picObjMap = ObjectUtil.castMapObj(picObj);
							picObjMap.put("tid", msgInfoMap.get("tid"));
							picObjMap.put("uin", msgInfoMap.get("uin"));
							picObjMap.put("qemotInfoId", emotId);
							picListBatch.add(ConverQqEntity.converQemotPic(picObjMap));
						}
					}
				}

				// comment List
				List commentlist = ObjectUtil.castListObj(msgInfoMap.get("commentlist"));
				if (ObjectUtil.getSize(commentlist) > 0) {
					for (Object comment : commentlist) {
						long emotCommentId = primaryKeyManager.getNextEmotCommentId();
						Map<String, Object> commentMap = ObjectUtil.castMapObj(comment);
						commentMap.put("qemotInfoUin", uid);
						commentMap.put("qemotInfoId", emotId);
						commentMap.put("emotCommentId", emotCommentId);
						commentListBatch.add(ConverQqEntity.converQemotComment(commentMap));
						List list_3 = ObjectUtil.castListObj(commentMap.get("list_3"));
						if (ObjectUtil.getSize(list_3) > 0) {
							for (Object replyObj : list_3) {
								Map replyObjMap = ObjectUtil.castMapObj(replyObj);
								replyObjMap.put("qemotCommentId", emotCommentId);
								commentReplyListBatch.add(ConverQqEntity.converQemotCommentReply(replyObjMap));
							}
						}
					}
				}

			}
		}

		// 批量写入DB
		try {
			if (ObjectUtil.getSize(msgListBatch) > 0) {
				qemotInfoMapper.insertQemotInfoBatch(msgListBatch);
			}

			if (ObjectUtil.getSize(picListBatch) > 0) {
				qemotPicMapper.insertBatchPicsByList(picListBatch);
			}

			if (ObjectUtil.getSize(commentListBatch) > 0) {
				qemotCommentMapper.insertQemotCommentBatch(commentListBatch);
			}

			if (ObjectUtil.getSize(commentReplyListBatch) > 0) {
				qemotCommentReplyMapper.insertBatchCommentReplyByList(commentReplyListBatch);
			}
		} catch (Exception e) {
			System.out.println(JSON.toJSONString(msgListBatch));
			e.printStackTrace();
		}

	}

	public void insertMsgIntoDB(QemotInfoMqVo qemotInfoMqVo) {
		if (qemotInfoMqVo == null) {
			return;
		}
		String uid = qemotInfoMqVo.uid;
		Map<String, Object> infoMap = qemotInfoMqVo.qemotInfoMap;
		if (null == infoMap) {
			return;
		}
		List msgList = ObjectUtil.castListObj(infoMap.get("commentList"));
		if (ObjectUtil.getSize(msgList) == 0) {
			return;
		}
		for (Object msgObj : msgList) {
			Map<String, Object> msgMap = ObjectUtil.castMapObj(msgObj);
			if (null == msgMap) {
				continue;
			}
			if ("".equals(ObjectUtil.toString(msgMap.get("htmlContent")))) {
				continue;
			}
			try {
				msgMap.put("uid", uid);
				qmsgInfoMapper.insertMsgInfo(msgMap);
				List replyList = ObjectUtil.castListObj(msgMap.get("replyList"));
				if (ObjectUtil.getSize(replyList) == 0) {
					continue;
				}
				qmsgInfoReplyMapper.insertBatchReplys(msgMap);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}

	public void insertMsgIntoDBBatch(QemotInfoMqVo qemotInfoMqVo) {
		if (qemotInfoMqVo == null) {
			return;
		}
		String uid = qemotInfoMqVo.uid;
		Map<String, Object> infoMap = qemotInfoMqVo.qemotInfoMap;
		if (null == infoMap) {
			return;
		}
		//msg List
		List msgList = ObjectUtil.castListObj(infoMap.get("commentList"));

		// 存储回复的列表
		List replyBatchList = new ArrayList();
		
		List msgInfoList = new ArrayList();
		
		if (ObjectUtil.getSize(msgList) == 0) {
			return;
		}
		for (Object msgObj : msgList) {
			Map<String, Object> msgMap = ObjectUtil.castMapObj(msgObj);
			if (null == msgMap) {
				continue;
			}
			if ("".equals(ObjectUtil.toString(msgMap.get("htmlContent")))) {
				continue;
			}
			// uid信息
			msgMap.put("uid", uid);
			
			// 手动获取主键信息
			long msgInfoId = primaryKeyManager.getNextMsgId();
			msgMap.put("msgInfoId", msgInfoId);
			
			msgInfoList.add(ConverQqEntity.ConverQmsgInfo(msgMap));
			List replyList = ObjectUtil.castListObj(msgMap.get("replyList"));
			if (ObjectUtil.getSize(replyList) == 0) {
				continue;
			}

			// 获取replyList
			for (Object replyObj : replyList) {
				Map replyMap = ObjectUtil.castMapObj(replyObj);
				// 关联主键信息
				replyMap.put("msgInfoId", msgInfoId);
				replyBatchList.add(ConverQqEntity.converQmsgInfoReply(replyMap));
			}
		}

		// 批量写入DB
		try {
			if(ObjectUtil.getSize(replyBatchList) > 0){
				qmsgInfoReplyMapper.insertBatchReplysByList(replyBatchList);
			}
			
			if(ObjectUtil.getSize(msgInfoList) > 0){
				qmsgInfoMapper.insertMsgInfoBatch(msgInfoList);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// 开始处理Emot
	public void startScratchEmotOne(QemotInfoMqVo qemotInfoMqVo) {
		if (null == qemotInfoMqVo) {
			return;
		}
		Map<String, Object> firstResultMap = getQzoneEmotInfoContent(qemotInfoMqVo.getUid(), 0);
		if (null != firstResultMap) {
			qemotInfoMqVo.setQemotInfoMap(firstResultMap);
			msgSenderService.sendMessage(qemotInfoMqVo, MqMsgInfoEnum.ADD_EMOT_DB.topic,
					MqMsgInfoEnum.ADD_EMOT_DB.tags);
			// 分页处理
			int totalNum = (Integer) firstResultMap.get("total");
			qemotInfoMqVo.totalNum = totalNum;
			//qemotInfoMapper.updateEmotTotalNum(qemotInfoMqVo);

			if (totalNum > 0) {
				System.out.println(qemotInfoMqVo.uid + "-- emot has 【" + totalNum + "】");
			}

			// 处理主表更细
			// 计算页数
			int pageNum = (totalNum + DEFAULT_NUM - 1) / DEFAULT_NUM;
			// 计算startPos
			if (pageNum > 1) {
				int startPos = DEFAULT_NUM;
				for (int startPage = 2; startPage <= pageNum; startPage++) {
					startPos = (startPage - 1) * DEFAULT_NUM;
					firstResultMap = getQzoneEmotInfoContent(qemotInfoMqVo.uid, startPos);
					if (firstResultMap != null) {
						qemotInfoMqVo.qemotInfoMap = firstResultMap;
						System.out.println(qemotInfoMqVo.uid+"--totalPage--"+pageNum+"--get--"+startPos+"--emotUIds.size--"+emotUids.size());
						try {
							msgSenderService.sendMessage(qemotInfoMqVo, MqMsgInfoEnum.ADD_EMOT_DB.topic,
									MqMsgInfoEnum.ADD_EMOT_DB.tags);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
	}
	
	public void startOneAllScratchEmotByUid(QemotInfoMqVo qemotInfoMqVo) {
		if (null == qemotInfoMqVo) {
			return;
		}
		Map<String, Object> firstResultMap = getQzoneEmotInfoContent(qemotInfoMqVo.getUid(), 0);
		if (null != firstResultMap) {
			qemotInfoMqVo.setQemotInfoMap(firstResultMap);
			insertEmotIntoDBBatch(qemotInfoMqVo);
			// 分页处理
			int totalNum = ObjectUtil.parseInteger(firstResultMap.get("total"), 0);
			qemotInfoMqVo.totalNum = totalNum;
			qemotInfoMapper.updateEmotTotalNum(qemotInfoMqVo);

			if (totalNum > 0) {
				System.out.println(qemotInfoMqVo.uid + "-- emot has 【" + totalNum + "】");
			}

			// 处理主表更细
			// 计算页数
			int pageNum = (totalNum + DEFAULT_NUM - 1) / DEFAULT_NUM;
			// 计算startPos
			if (pageNum > 1) {
				int startPos = DEFAULT_NUM;
				for (int startPage = 2; startPage <= pageNum; startPage++) {
					startPos = (startPage - 1) * DEFAULT_NUM;
					firstResultMap = getQzoneEmotInfoContent(qemotInfoMqVo.uid, startPos);
					if (firstResultMap != null) {
						qemotInfoMqVo.qemotInfoMap = firstResultMap;
						insertEmotIntoDBBatch(qemotInfoMqVo);
					}
					System.out.println(qemotInfoMqVo.uid+"--totalPage--"+pageNum+"--get--"+startPos);
				}
			}
		}
	}

	// 开始处理Emot
	public Map<String, Object> startScratchMsgOne(QemotInfoMqVo qemotInfoMqVo) {
		if (null == qemotInfoMqVo) {
			return null;
		}
		Map<String, Object> firstResultMap = getQzoneMsgInfoContent(qemotInfoMqVo.getUid(), 0);
		if (null != firstResultMap) {
			Map<String, Object> dataMap = ObjectUtil.castMapObj(firstResultMap.get("data"));
			qemotInfoMqVo.setQemotInfoMap(dataMap);

			msgSenderService.sendMessage(qemotInfoMqVo, MqMsgInfoEnum.ADD_MSG_DB.topic, MqMsgInfoEnum.ADD_MSG_DB.tags);
			// 分页处理
			int totalNum = dataMap.get("total") != null ? (Integer) dataMap.get("total") : 0;
			qemotInfoMqVo.msgNum = totalNum;
			//qmsgInfoMapper.updateMsgTotalNum(qemotInfoMqVo);


			if(totalNum >= 30){
				msgSenderService.sendMessage(qemotInfoMqVo, MqMsgInfoEnum.ADD_EMOT.topic, MqMsgInfoEnum.ADD_EMOT.tags);
				msgSenderService.sendMessage(qemotInfoMqVo, MqMsgInfoEnum.ADD_PHOTO.topic, MqMsgInfoEnum.ADD_PHOTO.tags);
			}
			
			// 处理主表更细
			// 计算页数
			int pageNum = (totalNum + DEFAULT_MSG_NUM - 1) / DEFAULT_MSG_NUM;

			if (totalNum > 0) {
				System.out.println(SDF.format(new Date()) + "----" + qemotInfoMqVo.uid + "--msg has 【" + totalNum
						+ "】 msgUids size = " + msgUids.size() + "pageNum -- " + pageNum);
			}
			// 计算startPos
			if (pageNum > 1) {
				int startPos = DEFAULT_MSG_NUM;
				for (int startPage = 2; startPage <= pageNum; startPage++) {
					startPos = (startPage - 1) * DEFAULT_MSG_NUM;
					firstResultMap = getQzoneMsgInfoContent(qemotInfoMqVo.uid, startPos);
					if (firstResultMap != null) {
						System.out.println(qemotInfoMqVo.uid + "--totalPage---" + pageNum + "---get--" + startPage+"--startPos--"+startPos+"msgUids.size = "+msgUids.size());
						qemotInfoMqVo.qemotInfoMap = ObjectUtil.castMapObj(firstResultMap.get("data"));
						msgSenderService.sendMessage(qemotInfoMqVo, MqMsgInfoEnum.ADD_MSG_DB.topic,
								MqMsgInfoEnum.ADD_MSG_DB.tags);
					}
				}
			}
		}
		return firstResultMap;
	}
	
	/**
	 * 获取单个uid的全部msg
	 * @param qemotInfoMqVo
	 */
	public void startOneAllScratchMsgByUid(QemotInfoMqVo qemotInfoMqVo){
		if (null == qemotInfoMqVo) {
			return;
		}
		Map<String, Object> firstResultMap = getQzoneMsgInfoContent(qemotInfoMqVo.getUid(), 0);
		if (null != firstResultMap) {
			Map<String, Object> dataMap = ObjectUtil.castMapObj(firstResultMap.get("data"));
			qemotInfoMqVo.setQemotInfoMap(dataMap);
			//不走消息 firstPage
			insertMsgIntoDBBatch(qemotInfoMqVo);
			// 分页处理
			int totalNum = dataMap.get("total") != null ? (Integer) dataMap.get("total") : 0;
			qemotInfoMqVo.msgNum = totalNum;
			qmsgInfoMapper.updateMsgTotalNum(qemotInfoMqVo);

			//重试时不需要
			if (totalNum >= 90 && false) {
				msgSenderService.sendMessage(qemotInfoMqVo, MqMsgInfoEnum.ADD_PHOTO.topic,
						MqMsgInfoEnum.ADD_PHOTO.tags);
				msgSenderService.sendMessage(qemotInfoMqVo, MqMsgInfoEnum.ADD_EMOT.topic, MqMsgInfoEnum.ADD_EMOT.tags);
			}

			// 处理主表更细
			// 计算页数
			int pageNum = (totalNum + DEFAULT_MSG_NUM - 1) / DEFAULT_MSG_NUM;

			if (totalNum > 0) {
				System.out.println(SDF.format(new Date()) + "----" + qemotInfoMqVo.uid + "--msg has 【" + totalNum
						+ "】 msgUids size = " + msgUids.size() + "pageNum -- " + pageNum);
			}
			// 计算startPos
			if (pageNum > 1) {
				int startPos = DEFAULT_MSG_NUM;
				for (int startPage = 2; startPage <= pageNum; startPage++) {
					startPos = (startPage - 1) * DEFAULT_MSG_NUM;
					firstResultMap = getQzoneMsgInfoContent(qemotInfoMqVo.uid, startPos);
					if (firstResultMap != null) {
						System.out.println(SDF.format(new Date()) +"--【"+qemotInfoMqVo.uid + "】--totalPage---" + pageNum + "---get--" + startPage+"--startPos--"+startPos);
						qemotInfoMqVo.qemotInfoMap = ObjectUtil.castMapObj(firstResultMap.get("data"));
						//不走消息
						insertMsgIntoDBBatch(qemotInfoMqVo);
					}
				}
			}
		}
	}

	public List parsePhotoList(Map photoDataMap) {
		List photoList = new ArrayList();
		if (null == photoDataMap) {
			return photoList;
		}
		List albumListModeSortList = ObjectUtil.castListObj(photoDataMap.get("albumListModeSort"));
		if (ObjectUtil.getSize(albumListModeSortList) > 0) {
			photoList.addAll(albumListModeSortList);
			return photoList;
		}
		List albumListModeClassList = ObjectUtil.castListObj(photoDataMap.get("albumListModeClass"));
		if (ObjectUtil.getSize(albumListModeClassList) > 0) {
			for (Object obj : albumListModeClassList) {
				Map photoMap = ObjectUtil.castMapObj(obj);
				List pList = ObjectUtil.castListObj(photoMap.get("albumList"));
				if (ObjectUtil.getSize(pList) > 0) {
					photoList.addAll(pList);
				}
			}
		}
		return photoList;
	}

	// 开始photo
	public void startScratchPhoto(QemotInfoMqVo qemotInfoMqVo) {
		if (null == qemotInfoMqVo) {
			return;
		}
		// photo
		Map<String, Object> photoMap = getQzonePhotoInfo(qemotInfoMqVo.uid);
		if (null == photoMap) {
			return;
		}
		Map<String, Object> photoDataMap = ObjectUtil.castMapObj(photoMap.get("data"));
		List photoList = parsePhotoList(photoDataMap);

		// ObjectUtil.castListObj(photoDataMap.get("albumListModeSort"));
		int photoNum = ObjectUtil.getSize(photoList);
		int imgNum = 0;
		System.out.println(qemotInfoMqVo.uid + " photoNum = " + photoNum + "---size---" + photoUids.size());
		if (photoNum == 0) {
			return;
		}
		qemotInfoMqVo.photoNum = photoNum;
		// img
		for (Object photoObj : photoList) {
			Map photoOneMap = ObjectUtil.castMapObj(photoObj);
			// photo img num one
			int oneImgNum = ObjectUtil.parseInteger(photoOneMap.get("total"), 0);
			// 累计
			imgNum += oneImgNum;
			// topicid
			String topicId = ObjectUtil.toString(photoOneMap.get("id"));
			// 获取img
			String allowAccess = ObjectUtil.toString(photoOneMap.get("allowAccess"));

			// 入库操作
			try {
				photoOneMap.put("uid", qemotInfoMqVo.uid);
				qphotoInfoMapper.insertPhoto(photoOneMap);
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println(
					qemotInfoMqVo.uid + "-----------oneImgNum--------" + oneImgNum + "---topicId----" + topicId);
			try {
				if (ALLOW_ACCESS.equals(allowAccess) && oneImgNum > 0) {
					// 获取img
					Map<String, Object> firstResultMap = getQzoneImgInfo(qemotInfoMqVo.uid, 0, topicId);
					if (null == firstResultMap) {
						continue;
					}
					Map<String, Object> imgDataMap = ObjectUtil.castMapObj(firstResultMap.get("data"));
					if (null == imgDataMap) {
						continue;
					}
					imgDataMap.put("photoId", photoOneMap.get("photoId"));
					imgDataMap.put("uid", qemotInfoMqVo.uid);
					qemotInfoMqVo.qemotInfoMap = imgDataMap;
					msgSenderService.sendMessage(qemotInfoMqVo, MqMsgInfoEnum.ADD_PHOTO_DB.topic,
							MqMsgInfoEnum.ADD_PHOTO_DB.tags);

					Integer totalNum = ObjectUtil.parseInteger(imgDataMap.get("totalInAlbum"));
					if (totalNum == null) {
						continue;
					}

					int startPos = 0;
					int totalPage = (totalNum + DEFAULT_IMG_NUM - 1) / DEFAULT_IMG_NUM;
					for (int startPage = 2; startPage <= totalPage; startPage++) {
						startPos = (startPage - 1) * DEFAULT_IMG_NUM;
						firstResultMap = getQzoneImgInfo(qemotInfoMqVo.uid, startPos, topicId);
						imgDataMap = ObjectUtil.castMapObj(firstResultMap.get("data"));
						imgDataMap.put("photoId", photoOneMap.get("photoId"));
						imgDataMap.put("uid", qemotInfoMqVo.uid);
						qemotInfoMqVo.qemotInfoMap = imgDataMap;
						msgSenderService.sendMessage(qemotInfoMqVo, MqMsgInfoEnum.ADD_PHOTO_DB.topic,
								MqMsgInfoEnum.ADD_PHOTO_DB.tags);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		// img num 更新num
		//qemotInfoMqVo.imgNum = imgNum;
		//quserInfoPoMapper.updateImgPhotoNum(qemotInfoMqVo);

	}

	public void insertPhotoImgDB(QemotInfoMqVo qemotInfoMqVo) {
		if (null == qemotInfoMqVo) {
			return;
		}
		try {
			Map<String, Object> dataMap = qemotInfoMqVo.qemotInfoMap;
			int size = ObjectUtil.getSize(ObjectUtil.castListObj(dataMap.get("photoList")));
			if (size > 0) {
				qphotoImgMapper.insertBatchImgs(qemotInfoMqVo.qemotInfoMap);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void initQParamCookieMapUids(List<String> uids, Map<String, Map<String, Object>> paramsMap,
			Map<String, Map<String, String>> cookiesMap) {
		this.paramsMap = paramsMap;
		this.cookiesMap = cookiesMap;
		countQMap = new HashMap<String, Integer>();
		countQMap.put(EMOT_KEY, 0);
		countQMap.put(INFO_KEY, 0);
		if (uids.size() >= 1) {
			this.uids.addAll(uids);
			this.msgUids.addAll(uids);
			this.photoUids.addAll(uids);
			this.emotUids.addAll(uids);
			this.visitUids.addAll(uids);
		}
		// 启动之后进行操作
		startWebFlag = 1;
		
	}

	// 开始操作 递增
	public void startScratch() {
		while (StartDoJob.START_FLAG == 0) {
			System.out.println("startScratch  sleep 3 s");
			try {
				Thread.sleep(1000 * 3);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		long startUid = quserInfoPoMapper.getMaxUid() + 1;
		long num = 0;
		long success = 0;
		long fail = 0;
		String ncode = "-4009";
		System.out.println("max  uid ----" + startUid);
		UserInfoVo userInfoVo = new UserInfoVo();
		while (getUids() != null && getUids().size() > 0) {
			num++;
			Map<String, Object> contentMap = getQzoneBaseInfoContent(startUid + "");
			if (null == contentMap) {
				break;
			}
			if (contentMap.get("gateWay") != null) {
				continue;
			}
			Object code = contentMap.get("code");
			String codeStr = code != null ? code.toString() : "";
			userInfoVo.uid = startUid + "";
			try {
				if (null != code && "0".equals(codeStr)) {
					userInfoVo.userInfoMap = contentMap;
					msgSenderService.sendMessage(userInfoVo, MqMsgInfoEnum.ADD_USER_INFO.topic,
							MqMsgInfoEnum.ADD_USER_INFO.tags);
							
					
					//获取visitor
					//msgSenderService.sendMessage(userInfoVo.uid, MqMsgInfoEnum.ADD_VISIT_DB.topic, MqMsgInfoEnum.ADD_VISIT_DB.tags);
					
					success++;
				}
			} catch (Exception e) {
				e.printStackTrace();
				if (e instanceof MQBrokerException) {
					try {
						System.out.println("ok sleep 10 s");
						Thread.sleep(1000 * 10);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
					continue;
				}
			}

			// 无访问权限
			if (ncode.equals(codeStr)) {
				fail++;
				try {
					msgSenderService.sendMessage(userInfoVo, MqMsgInfoEnum.ADD_USER_INFO_N.topic,
							MqMsgInfoEnum.ADD_USER_INFO_N.tags);
				} catch (Exception e) {
					e.printStackTrace();
					if (e instanceof MQBrokerException) {
						try {
							System.out.println("N sleep 10 s");
							Thread.sleep(1000 * 10);
						} catch (InterruptedException e1) {
							e1.printStackTrace();
						}
						continue;
					}
				}
			}
			System.out.println(SDF.format(new Date()) + " -- " + startUid + " -- num = " + num + ", success = "
					+ success + " , fail = " + fail + "---baseInfoSize----" + uids.size() + "----emotInfoSize--"
					+ emotUids.size() + "---msgUIdsSize--" + msgUids.size());
			startUid++;

			if (ObjectUtil.getSize(msgUids) == 0) {
				System.out.println("msgUids is over Sleep 15 min ");
				try {
					Thread.sleep(1000 * 60 * 15);
					msgUids.addAll(uids);
					if(emotUids.size() == 0){
						emotUids.addAll(uids);
					}
					if(visitUids.size() == 0){
						visitUids.addAll(uids);
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	// 开始操作 递增
	public void startScratchMinus() {
		while (StartDoJob.START_FLAG == 0) {
			System.out.println("sleep 3 s");
			try {
				Thread.sleep(1000 * 3);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		long startUid = quserInfoPoMapper.getMinUid() - 1;
		long num = 0;
		long success = 0;
		long fail = 0;
		String ncode = "-4009";
		System.out.println("max  uid ----" + startUid);
		UserInfoVo userInfoVo = new UserInfoVo();
		while (getUids() != null && getUids().size() > 0) {
			num++;
			Map<String, Object> contentMap = getQzoneBaseInfoContent(startUid + "");
			if (null == contentMap) {
				break;
			}
			if (contentMap.get("gateWay") != null) {
				continue;
			}
			Object code = contentMap.get("code");
			String codeStr = code != null ? code.toString() : "";
			userInfoVo.uid = startUid + "";
			try {
				if (null != code && "0".equals(codeStr)) {
					userInfoVo.userInfoMap = contentMap;
					msgSenderService.sendMessage(userInfoVo, MqMsgInfoEnum.ADD_USER_INFO.topic,
							MqMsgInfoEnum.ADD_USER_INFO.tags);
					success++;
				}
			} catch (Exception e) {
				e.printStackTrace();
				if (e instanceof MQBrokerException) {
					try {
						System.out.println("ok sleep 10 s");
						Thread.sleep(1000 * 10);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
					continue;
				}
			}

			// 无访问权限
			if (ncode.equals(codeStr)) {
				fail++;
				try {
					msgSenderService.sendMessage(userInfoVo, MqMsgInfoEnum.ADD_USER_INFO_N.topic,
							MqMsgInfoEnum.ADD_USER_INFO_N.tags);
				} catch (Exception e) {
					e.printStackTrace();
					if (e instanceof MQBrokerException) {
						try {
							System.out.println("N sleep 10 s");
							Thread.sleep(1000 * 10);
						} catch (InterruptedException e1) {
							e1.printStackTrace();
						}
						continue;
					}
				}
			}
			System.out.println(SDF.format(new Date()) + " -- " + startUid + " -- num = " + num + ", success = "
					+ success + " , fail = " + fail + "---baseInfoSize----" + uids.size() + "----emotInfoSize--"
					+ emotUids.size() + "---msgUIdsSize--" + msgUids.size());
			startUid--;

			if (ObjectUtil.getSize(msgUids) == 0) {
				System.out.println("msgUids is over Sleep 15 min ");
				try {
					Thread.sleep(1000 * 60 * 15);
					msgUids.addAll(uids);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	
	private void crawMsg(List<QuserInfoRetry> msgUsers){
		if(null != msgUsers && msgUsers.size() > 0){
			QemotInfoMqVo msgVo  = new QemotInfoMqVo();
			int flag = 0;
			for(QuserInfoRetry userInfo:msgUsers){
				System.out.println("start retry ----"+JSON.toJSONString(userInfo));
				if(msgUids.size() == 0){
					try {
						System.out.println("retry msg sleep 15 min");
						Thread.sleep(1000*60*15);
						flag = 1;
						msgUids.addAll(uids);
						if(emotUids.size() == 0){
							emotUids.addAll(uids);
						}
						if(visitUids.size() == 0){
							visitUids.addAll(uids);
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				
				if(emotUids.size() == 0){
					try {
						System.out.println("retry emot sleep 15 min");
						Thread.sleep(1000*60*15);
						emotUids.addAll(uids);
						if(msgUids.size() == 0){
							msgUids.addAll(uids);
						}
						if(visitUids.size() == 0){
							visitUids.addAll(uids);
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				
				if(visitUids.size() == 0){
					try {
						System.out.println("retry visit sleep 15 min");
						Thread.sleep(1000*60*15);
						visitUids.addAll(uids);
						if(msgUids.size() == 0){
							msgUids.addAll(uids);
						}
						if(emotUids.size() == 0){
							emotUids.addAll(uids);
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				
				msgVo.querInfoId = userInfo.userinfoid;
				msgVo.uid = userInfo.uid;
				
				
				
				if(userInfo.getMsgflag() == 0 && msgUids.size() > 0){
					startOneAllScratchMsgByUid(msgVo);
					quserInfoPoMapper.updateMsgFlagQuserInfoRetry(userInfo.id);
				}
				
				
				if(userInfo.getEmotflag() == 0 && emotUids.size() > 0){
					startOneAllScratchEmotByUid(msgVo);
					quserInfoPoMapper.updateEmotFlagQuserInfoRetry(userInfo.id);
				}
				
				//获取访客
				//msgSenderService.sendMessage(userInfo.uid,MqMsgInfoEnum.ADD_VISIT_DB_Q.topic,MqMsgInfoEnum.ADD_VISIT_DB_Q.tags);
				if(visitUids.size() > 0){
					Map<String,Object> contentMap = getQzoneVisitInfoContent(userInfo.uid);
					if(contentMap != null ){
						Map<String,Object> dataMap = ObjectUtil.castMapObj(contentMap.get("data"));
						if(dataMap != null ){
							int size = ObjectUtil.getSize(dataMap.get("items"));
							if(null != dataMap &&   size > 0){
								//批量插入
								try{
									dataMap.put("muid",userInfo.uid);
								quserInfoPoMapper.addUserVistorBatch(dataMap);
								}catch(Exception e ){
									e.printStackTrace();
								}
							}
						}
					}
				}
				
				
			}
		}
	}
	public void retryCrawMsg(){
		List<QuserInfoRetry> msgUsers = quserInfoPoMapper.getMsgAllQuserInfoRetry();
		crawMsg(msgUsers);
	}
	
	public void retryCrawMsgWithId(long startId,long endId){
		List<QuserInfoRetry> msgUsers = quserInfoPoMapper.getMsgAllQuserInfoRetryWithId(startId, endId);
		crawMsg(msgUsers);
	}
	
	public void retryCrawEmot(){
		List<QuserInfoRetry> msgUsers = quserInfoPoMapper.getEmotAllQuserInfoRetry();
		if(null != msgUsers && msgUsers.size() > 0){
			QemotInfoMqVo msgVo  = new QemotInfoMqVo();
			for(QuserInfoRetry userInfo:msgUsers){
				if(emotUids.size() == 0){
					try {
						System.out.println("retry emot sleep 15 min");
						Thread.sleep(1000*60*15);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				msgVo.querInfoId = userInfo.userinfoid;
				msgVo.uid = userInfo.uid;
				msgSenderService.sendMessage(msgVo, MqMsgInfoEnum.ADD_EMOT.topic, MqMsgInfoEnum.ADD_EMOT.tags);
				quserInfoPoMapper.updateEmotFlagQuserInfoRetry(userInfo.id);
			}
		}
	}
	
	public void retryEmotMsg(){
		while(StartDoJob.START_FLAG == 0){
			try {
				System.out.println("sleep 10s wait for session");
				Thread.sleep(1000*10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		retryCrawMsg();
		retryCrawEmot();
	}
	
	public List<QuserInfoPo> getAll(int num){
		List<QuserInfoPo> users = new ArrayList<QuserInfoPo>();
		for(int i = 0;i<num;i++){
			QuserInfoPo u = new QuserInfoPo();
			u.setAge("12"+i);
			u.setUid(10187876+i);
			users.add(u);
		}
		return users;
	}
	
	
	/**
	 * get  spec 
	 * @param uids
	 */
	public void getSpecUids(List quids){
		if(null == quids || quids.size() == 0){
			return;
		}
		long num = 0;
		long success = 0;
		long fail = 0;
		String ncode = "-4009";
		
		for(Object uidObj:quids){
			String uid = ObjectUtil.toString(uidObj);
			if("".equals(uid)){
				continue;
			}
			UserInfoVo userInfoVo = new UserInfoVo();
			num++;
			Map<String, Object> contentMap = getQzoneBaseInfoContent(uid);
			System.out.println(uid+"-------"+JSON.toJSONString(contentMap));
			if (null == contentMap) {
				break;
			}
			if (contentMap.get("gateWay") != null) {
				continue;
			}
			Object code = contentMap.get("code");
			String codeStr = code != null ? code.toString() : "";
			userInfoVo.uid = uid;
			try {
				if (null != code && "0".equals(codeStr)) {
					userInfoVo.userInfoMap = contentMap;
					msgSenderService.sendMessage(userInfoVo, MqMsgInfoEnum.ADD_USER_INFO.topic,
							MqMsgInfoEnum.ADD_USER_INFO.tags);
							
					
					//获取visitor
					msgSenderService.sendMessage(userInfoVo.uid, MqMsgInfoEnum.ADD_VISIT_DB.topic, MqMsgInfoEnum.ADD_VISIT_DB.tags);
					
					success++;
				}
			} catch (Exception e) {
				e.printStackTrace();
				if (e instanceof MQBrokerException) {
					try {
						System.out.println("ok sleep 10 s");
						Thread.sleep(1000 * 10);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
					continue;
				}
			}

			// 无访问权限
			if (ncode.equals(codeStr)) {
				fail++;
				try {
					msgSenderService.sendMessage(userInfoVo, MqMsgInfoEnum.ADD_USER_INFO_N.topic,
							MqMsgInfoEnum.ADD_USER_INFO_N.tags);
				} catch (Exception e) {
					e.printStackTrace();
					if (e instanceof MQBrokerException) {
						try {
							System.out.println("N sleep 10 s");
							Thread.sleep(1000 * 10);
						} catch (InterruptedException e1) {
							e1.printStackTrace();
						}
						continue;
					}
				}
			}
			System.out.println(SDF.format(new Date()) + " -- " + uid + " -- num = " + num + ", success = "
					+ success + " , fail = " + fail + "---baseInfoSize----" + uids.size() + "----emotInfoSize--"
					+ emotUids.size() + "---msgUIdsSize--" + msgUids.size()+"----visitUidSize---"+visitUids.size());

			if (ObjectUtil.getSize(msgUids) == 0) {
				System.out.println("msgUids is over Sleep 15 min ");
				try {
					Thread.sleep(1000 * 60 * 15);
					msgUids.addAll(uids);
					if(emotUids.size() == 0){
						emotUids.addAll(uids);
					}
					if(visitUids.size() == 0){
						visitUids.addAll(uids);
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
		}
	}

}
