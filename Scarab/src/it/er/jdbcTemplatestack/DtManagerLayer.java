package it.er.jdbcTemplatestack;


import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;

public class DtManagerLayer implements DtOperation{
	
	private static Logger log = LogManager.getLogger(DtManagerLayer.class);
	private Map<String, JdbcTemplate> dtLayerMap;
	
	public Map<String, JdbcTemplate> getDtLayerMap() {
		return dtLayerMap;
	}

	public void setDtLayerMap(Map<String, JdbcTemplate> dtLayerMap) {
		this.dtLayerMap = dtLayerMap;
	}
	
	private Map<String, String> domainLayerMap;
	
	
	public Map<String, String> getDomainLayerMap() {
		return domainLayerMap;
	}

	public void setDomainLayerMap(Map<String, String> domainLayerMap) {
		this.domainLayerMap = domainLayerMap;
	}

	public void close(){
		log.info("Destroy DtManagerLayer bean");
		this.dtLayerMap = null;
	}
	
	public void start(){
		log.info("Init DtManagerLayer bean");
	}

	@Override
	public JdbcTemplate getJdbcMyTemplate(String s) {
		if (this.domainLayerMap.containsValue(s)){
			Set<String> sk = this.domainLayerMap.keySet();
			Iterator<String> i = sk.iterator();
			while (i.hasNext()){
				String key = i.next();
				if (this.domainLayerMap.get(key).equals(s))
					return this.dtLayerMap.get(key);
			}
		}
		return null;
	}

}
