package com.man.erpcenter.elasticsearch.manager;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

public class ESTransportClientFactoryBean implements BeanFactoryPostProcessor {

	private String clusterName;

	private String hosts;

	private int port;


	private static int DEFAULT_PORT = 9300;

	public String getClusterName() {
		return clusterName;
	}

	public void setClusterName(String clusterName) {
		this.clusterName = clusterName;
	}

	public String getHosts() {
		return hosts;
	}

	public void setHosts(String hosts) {
		this.hosts = hosts;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	

	private String[] parseHosts() {
		String[] hostsList = new String[] {};
		if (hosts != null) {
			hostsList = hosts.split(",");
		}
		return hostsList;
	}

	private String parseHost(String url) {
		return url.split(":")[0];
	}

	private int parsePort(String url) {
		String[] s = url.split(":");
		if (s.length == 2) {
			return Integer.parseInt(s[1]);
		} else {
			return DEFAULT_PORT;
		}
	}

	

	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		TransportClient client = null;
		Settings settings = Settings.builder().put("cluster.name", this.clusterName).put("client.transport.sniff", true)
				.build();
		client = new PreBuiltTransportClient(settings);
		String[] hs = parseHosts();
		if (hs != null && hs.length > 0) {
			for (String h : hs) {
				
				if (h != null && !"".equals(h.trim())) {
					String ph = parseHost(h);
					int port = parsePort(h);
					System.out.println(ph+"----"+port);
					try {
						client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(ph), port));
					} catch (UnknownHostException e) {
						e.printStackTrace();
					}
				}
			}
		}
		if(client==null){
			throw new RuntimeException("实例化client失败");
		}
		beanFactory.registerSingleton("elasticsearchClient", client); 
		
		
	}

}
