

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.junit.Test;

public class TestSolr {

	String url = "http://localhost:9090/solr/userinfo";
	@Test
	public void testQuery() throws Exception{
		HttpSolrClient solrClient = new HttpSolrClient(url);
		SolrQuery solrQuery = new SolrQuery();
		solrQuery.set("q","uid:1018793422 AND sex:1");
		solrQuery.set("qt","/select");
		solrQuery.set("wt","json");
		solrQuery.set("start",0);
		solrQuery.set("rows",100);
		QueryResponse resp = solrClient.query(solrQuery);  
		SolrDocumentList results =  resp.getResults();
		System.out.println("----"+results.getNumFound());
		System.out.println(results.getStart());
		for(SolrDocument doc : results){
			System.out.println(doc.get("uid"));
		}
	}
}
