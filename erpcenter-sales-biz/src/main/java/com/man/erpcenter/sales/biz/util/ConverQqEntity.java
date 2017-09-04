package com.man.erpcenter.sales.biz.util;

import java.util.Map;

import com.man.erpcenter.common.utils.ObjectUtil;
import com.man.erpcenter.sales.client.po.QemotComment;
import com.man.erpcenter.sales.client.po.QemotCommentReply;
import com.man.erpcenter.sales.client.po.QemotInfo;
import com.man.erpcenter.sales.client.po.QemotPic;
import com.man.erpcenter.sales.client.po.QmsgInfo;
import com.man.erpcenter.sales.client.po.QmsgInfoReply;

public class ConverQqEntity {

	public static QemotInfo converQemotInfo(Map qqmap){
		QemotInfo info = new QemotInfo();
//		 #{qemotInfo.qemotInfoId},
//	      #{qemotInfo.cmtnum}, 
//	      #{qemotInfo.createTime}, 
//	      #{qemotInfo.created_time}, 
//	      #{qemotInfo.editMask}, 
//	      #{qemotInfo.fwdnum}, 
//	      #{qemotInfo.name},
//	      #{qemotInfo.pictotal},
//	      #{qemotInfo.right}, 
//	      #{qemotInfo.rt_sum},
//	      #{qemotInfo.secret},
//	      #{qemotInfo.tid},
//	      #{qemotInfo.ugc_right},
//	      #{qemotInfo.uin},
//	      #{qemotInfo.wbid}, 
//	      #{qemotInfo.lbs.id},
//	      #{qemotInfo.lbs.idname},
//	      #{qemotInfo.lbs.name}, 
//	      #{qemotInfo.lbs.pos_x},
//	      #{qemotInfo.lbs.pos_y},
//	      #{qemotInfo.source_name}, 
//	      #{qemotInfo.content},
		
		
//		 id,
//	      cmtnum, 
//	      createTime, 
//	      created_time, 
//	      editMask, 
//	      fwdnum, 
//	      name, 
//	      pictotal, 
//	      `right`, 
//	      rt_sum, 
//	      secret, 
//	      tid, 
//	      ugc_right, 
//	      uid, 
//	      wbid, 
//	      lbs_id, 
//	      lbs_idname,
//	      lbs_name, 
//	      lbs_pos_x, 
//	      lbs_pos_y, 
//	      source_name, 
//	      content,
	      
	      
		info.id = ObjectUtil.parseLong(qqmap.get("qemotInfoId"));
		info.cmtnum = ObjectUtil.parseInteger(qqmap.get("cmtnum"));
		info.createtime = ObjectUtil.toString(qqmap.get("createTime"));
		info.createdTime = ObjectUtil.toString(qqmap.get("created_time"));
		
		info.editmask = ObjectUtil.toString(qqmap.get("editMask"));
		
		info.fwdnum = ObjectUtil.parseInteger(qqmap.get("fwdnum"));
		info.name = ObjectUtil.toString(qqmap.get("name"));
		info.pictotal = ObjectUtil.parseInteger(qqmap.get("pictotal"));
		info.right = ObjectUtil.toString(qqmap.get("right"));
		info.rtSum = ObjectUtil.toString(qqmap.get("rt_sum"));
		
		info.secret = ObjectUtil.toString(qqmap.get("secret"));
		info.tid = ObjectUtil.toString(qqmap.get("tid"));
		info.ugcRight = ObjectUtil.toString(qqmap.get("ugc_right"));
		
		info.uid = ObjectUtil.toString(qqmap.get("uin"));
		info.wbid = ObjectUtil.toString(qqmap.get("wbid"));
		
		Map lbsMap = ObjectUtil.castMapObj(qqmap.get("lbs"));
		info.lbsId = ObjectUtil.toString(lbsMap.get("id"));
		info.lbsIdname = ObjectUtil.toString(lbsMap.get("idname"));
		info.lbsName = ObjectUtil.toString(lbsMap.get("name"));
		info.lbsPosX = ObjectUtil.toString(lbsMap.get("pos_x"));
		info.lbsPosY = ObjectUtil.toString(lbsMap.get("pos_y"));
		info.sourceName = ObjectUtil.toString(qqmap.get("source_name"));
		info.content = ObjectUtil.toString(qqmap.get("content"));
		
		return info;
	}
	
	public static QmsgInfo ConverQmsgInfo(Map msgMap){
		QmsgInfo qmsgInfo = new QmsgInfo();
//		#{msgInfo.msgInfoId},
//    	#{msgInfo.id,jdbcType=VARCHAR}, 
//    	#{msgInfo.secret,jdbcType=VARCHAR}, 
//      	#{msgInfo.pasterid,jdbcType=VARCHAR}, 
//      	#{msgInfo.bmp,jdbcType=VARCHAR}, 
//      	#{msgInfo.pubtime,jdbcType=VARCHAR}, 
//        #{msgInfo.modifytime,jdbcType=VARCHAR}, 
//        #{msgInfo.effect,jdbcType=VARCHAR}, 
//        #{msgInfo.type,jdbcType=VARCHAR}, 
//        #{msgInfo.uin,jdbcType=VARCHAR}, 
//        #{msgInfo.nickname,jdbcType=VARCHAR}, 
//        #{msgInfo.capacity,jdbcType=VARCHAR}, 
//      	#{msgInfo.uid,jdbcType=VARCHAR}, 
//      	#{msgInfo.htmlContent,jdbcType=LONGVARCHAR},
//      	now()
		
//		id,
//	      msgid, 
//	      secret, 
//	      pasterid,
//	      bmp,
//	      pubtime, 
//	      modifytime,
//	      effect,
//	      type, 
//	      uin, 
//	      nickname, 
//	      capacity, 
//	      uid, 
//	      html_content,
//	      create_gmt
      	
		qmsgInfo.id = ObjectUtil.parseLong(msgMap.get("msgInfoId"));
		qmsgInfo.msgid = ObjectUtil.toString(msgMap.get("id"));
		qmsgInfo.secret = ObjectUtil.toString(msgMap.get("secret"));
		qmsgInfo.pasterid = ObjectUtil.toString(msgMap.get("pasterid"));
		qmsgInfo.bmp = ObjectUtil.toString(msgMap.get("bmp"));
		qmsgInfo.pubtime = ObjectUtil.toString(msgMap.get("pubtime"));
		qmsgInfo.modifytime = ObjectUtil.toString(msgMap.get("modifytime"));
		qmsgInfo.effect = ObjectUtil.toString(msgMap.get("effect"));
		qmsgInfo.type = ObjectUtil.toString(msgMap.get("type"));
		qmsgInfo.uin = ObjectUtil.toString(msgMap.get("uin"));
		qmsgInfo.nickname = ObjectUtil.toString(msgMap.get("nickname"));
		qmsgInfo.capacity = ObjectUtil.toString(msgMap.get("capacity"));
		qmsgInfo.htmlContent = ObjectUtil.toString(msgMap.get("htmlContent"));
		qmsgInfo.uid = ObjectUtil.toString(msgMap.get("uid"));
		return qmsgInfo;
	}
	
	public static QmsgInfoReply converQmsgInfoReply(Map msgReplyMap){
		QmsgInfoReply reply = new QmsgInfoReply();
//		 insert into qmsg_info_reply (
//			    	time, 
//			    	nick, 
//			        msg_id, 
//			        content,
//			        create_gmt
//			        )
//			    values 
//			    	<foreach collection="replyList" item="reply" separator=",">
//				    	( 
//				    	#{reply.time,jdbcType=VARCHAR}, 
//				    	#{reply.nick,jdbcType=VARCHAR}, 
//				      	#{reply.msgInfoId,jdbcType=BIGINT}, 
//				      	#{reply.content,jdbcType=LONGVARCHAR},
//				      	now()
//				      	)
//			    	</foreach>
		reply.time = ObjectUtil.toString(msgReplyMap.get("time"));
		reply.nick = ObjectUtil.toString(msgReplyMap.get("nick"));
		reply.msgId = ObjectUtil.parseLong(msgReplyMap.get("msgInfoId"));
		reply.content = ObjectUtil.toString(msgReplyMap.get("content"));
		return reply;
	}
	
	public static QemotPic converQemotPic(Map picMap){
//		 insert into qemot_pic (
//			    	tid, 
//			    	emot_id, 
//			      	uid, 
//			      	height, 
//			      	width, 
//			      	url1, 
//			      	url2, 
//			      	url3,
//			      	create_gmt
//			      	)
//			    values 
//			    <foreach collection="picList" item="item" index="index" separator="," >
//			    ( 
//			      #{item.tid}, 
//			      #{item.qemotInfoId}, 
//			      #{item.uin}, 
//			      #{item.height},
//			      #{item.width}, 
//			      #{item.url1}, 
//			      #{item.url2},
//			      #{item.url3},
//			      now()
//			     )
		
		QemotPic pic = new QemotPic();
		pic.tid = ObjectUtil.toString(picMap.get("tid"));
		pic.emotId = ObjectUtil.parseLong(picMap.get("qemotInfoId"));
		pic.uid = ObjectUtil.toString(picMap.get("uin"));
		pic.height = ObjectUtil.toString(picMap.get("height"));
		pic.width = ObjectUtil.toString(picMap.get("width"));
		pic.url1 = ObjectUtil.toString(picMap.get("url1"));
		pic.url2 = ObjectUtil.toString(picMap.get("url2"));
		pic.url3 = ObjectUtil.toString(picMap.get("url3"));
		
		return pic;
	}
	
	public static QemotComment converQemotComment(Map commentMap){
//		 insert into qemot_comment (
//			      id,
//			      emot_id, 
//			      tid, 
//			      uid,
//			      muid,
//			      mname, 
//			      content, 
//			      create_time2, 
//			      create_time,
//			      create_time1, 
//			      reply_num,
//			      create_gmt
//			      )
//			    values 
//			    <foreach collection="commentList" item="comment" separator=",">
//			    		 ( 
//			    		  #{comment.emotCommentId},
//					      #{comment.qemotInfoId}, 
//					      #{comment.tid}, 
//					      #{comment.qemotInfoUin}, 
//					      #{comment.uin}, 
//					      #{comment.name}, 
//					      #{comment.content}, 
//					      #{comment.createtime2},
//					      #{comment.createTime}, 
//					      #{comment.create_time},
//					      #{comment.reply_num},
//					      now()
//			     		 )
		
		QemotComment comment =  new QemotComment();
		comment.id = ObjectUtil.parseLong(commentMap.get("emotCommentId"));
		comment.emotId = ObjectUtil.parseLong(commentMap.get("qemotInfoId"));
		comment.tid = ObjectUtil.toString(commentMap.get("tid"));
		comment.uid = ObjectUtil.toString(commentMap.get("qemotInfoUin"));
		comment.muid = ObjectUtil.toString(commentMap.get("uin"));
		comment.mname = ObjectUtil.toString(commentMap.get("name"));
		comment.content = ObjectUtil.toString(commentMap.get("content"));
		comment.createtime2 = ObjectUtil.toString(commentMap.get("createtime2"));
		comment.createTime = ObjectUtil.toString(commentMap.get("createTime"));
		comment.createTime1 = ObjectUtil.toString(commentMap.get("create_time"));
		comment.replyNum = ObjectUtil.toString(commentMap.get("reply_num"));
		
		return comment;
	}
	
	public static QemotCommentReply converQemotCommentReply(Map replyMap){
//		 insert into qemot_comment_reply (
//			    	comment_id,
//			    	uid, 
//			      	content, 
//			      	create_time0, 
//			      	create_time2, 
//			      	create_time3, 
//			      	name, 
//			      	source_name, 
//			      	tid,
//			      	create_gmt
//			      	)
//			    values 
//			    <foreach collection="replyList" item="reply" index="index" separator=",">
//			      (
//			      #{reply.qemotCommentId},
//			      #{reply.uin}, 
//			      #{reply.content},
//			      #{reply.create_time},
//			      #{reply.createTime2}, 
//			      #{reply.createTime},
//			      #{reply.name},
//			      #{reply.source_name}, 
//			      #{reply.tid},
//			      now()
//			      )
//			      </foreach>
		QemotCommentReply reply = new QemotCommentReply();
		reply.commentId = ObjectUtil.parseLong(replyMap.get("qemotCommentId"));
		reply.uid = ObjectUtil.toString(replyMap.get("uin"));
		reply.content = ObjectUtil.toString(replyMap.get("content"));
		reply.createTime0 = ObjectUtil.toString(replyMap.get("create_time"));
		reply.createTime2 = ObjectUtil.toString(replyMap.get("createTime2"));
		reply.createTime3 = ObjectUtil.toString(replyMap.get("createTime"));
		reply.name = ObjectUtil.toString(replyMap.get("name"));
		reply.sourceName = ObjectUtil.toString(replyMap.get("source_name"));
		reply.tid = ObjectUtil.toString(replyMap.get("tid"));
		return reply;
		
	}
}
