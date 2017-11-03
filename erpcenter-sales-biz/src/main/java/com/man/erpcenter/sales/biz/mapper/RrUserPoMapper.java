package com.man.erpcenter.sales.biz.mapper;

import java.util.List;
import java.util.Map;

import com.man.erpcenter.sales.client.po.RrUserPo;

public interface RrUserPoMapper {
	public  void  addRrUser(RrUserPo po);
	
	public void addRrUserBatch(List<RrUserPo> datas);
	
	public int addErrLog(Map<String,Object> map);
	
	public int updateLog(Map<String,Object> map);
	
	public int getOffsetBySidAndGender(Map<String,Object> map);
}
