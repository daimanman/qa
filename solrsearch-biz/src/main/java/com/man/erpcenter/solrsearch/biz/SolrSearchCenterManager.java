package com.man.erpcenter.solrsearch.biz;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.HttpSolrClient;

import com.man.erpcenter.sales.client.solr.SolrQueryParams;

public abstract class SolrSearchCenterManager {

	public static String solrServer;
	
	public static Map<String,HttpSolrClient> solrClientMap = new HashMap<String,HttpSolrClient>();

	
	public static Set<String> coreSet = new HashSet<String>();
	
	
	private HttpSolrClient getSolrClient(String coreKey) {
		return solrClientMap.get(coreKey);
	}
	
	public Map<String, HttpSolrClient> getSolrClientMap() {
		return solrClientMap;
	}
	public void setSolrClientMap(Map<String, HttpSolrClient> solrClientMap) {
		this.solrClientMap = solrClientMap;
	}
	public Set<String> getCoreSet() {
		return coreSet;
	}
	public void setCoreSet(Set<String> coreSet) {
		this.coreSet = coreSet;
	}
	public String getSolrServer() {
		return solrServer;
	}

	public void setSolrServer(String solrServer) {
		this.solrServer = solrServer;
	}
	
	public void initSolrClient(){
		if(coreSet.size() > 0){
			for(String coreKey:coreSet){
				String url = solrServer+"/"+coreKey;
				HttpSolrClient solrClient = new HttpSolrClient(url);
				solrClientMap.put(coreKey, solrClient);
			}
		}
	}
	
	public abstract String getCoreName();
	
	
	public HttpSolrClient getSolrClient(){
		return getSolrClient(getCoreName());
	}
	protected SolrQuery parseSolrQuery(SolrQueryParams bizParams){
		SolrQuery query = new SolrQuery();
		query.set("q", bizParams.getQ());
		query.set("start",bizParams.getStart()+"");
		query.set("rows",bizParams.getRows()+"");
		return query;
	}
	

	
	
	
}
