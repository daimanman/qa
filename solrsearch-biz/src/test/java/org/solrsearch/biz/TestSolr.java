package org.solrsearch.biz;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.junit.Test;

public class TestSolr {

	String url = "localhost:9090/solr/userinfo";
	@Test
	public void testQuery() throws Exception{
		HttpSolrClient solrClient = new HttpSolrClient(url);
		SolrQuery solrQuery = new SolrQuery("*:*");
		solrQuery.set("qt","/select");
		QueryResponse resp = solrClient.query(solrQuery);  
		SolrDocumentList results =  resp.getResults();
		for(SolrDocument doc : results){
			System.out.println(doc.get("uid"));
		}
	}
	
	@Test
	public void testStrRe(){
		String url = "http://b40.photo.store.qq.com/psu?/967238ce-d755-4a9a-ae3d-5dd48a683ac5/nMG2yoePNNG65.lW8Jyu3FSOQs4oSc2RwxXJ*gJzmGk!/b/YfaVRBrwDgAAYmJI5ReVmAAA&a=44&b=40&bo=ngL2AQAAAAABBEg!";
		System.out.println(url.replace("/b/","/m/" ));
	}
}
