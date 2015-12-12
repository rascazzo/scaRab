package it.er.basic;

import it.er.dao.MdbServiceNSParam;
import it.er.object.Metatag;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.bson.Document;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;

public abstract class BaseNoSql extends Basic implements InitializingBean{

	private static final Logger log = LogManager.getLogger(BaseNoSql.class);
	
	private MongoClient mdbClient = null;
	
	private MongoDatabase mdbDatabase = null;
	
	private String mdbName = null;
	
	private MdbServiceNSParam mdbServiceParam = null;

	public MongoDatabase getMdbDatabase() {
		return mdbDatabase;
	}

	public void setMdbDatabase(MongoDatabase mdbDatabase) {
		this.mdbDatabase = mdbDatabase;
	}

	public String getMdbName() {
		return mdbName;
	}

	
	public void setMdbName(String mdbName) {
		this.mdbName = mdbName;
	}
	

	public MdbServiceNSParam getMdbServiceParam() {
		return mdbServiceParam;
	}

	@Autowired
	public void setMdbServiceParam(MdbServiceNSParam mdbServiceParam) {
		this.mdbServiceParam = mdbServiceParam;
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		/**
		 * implemented only ipv4
		 */
		String ip = mdbServiceParam.getMdbServiceNSParamMap().get("mdbIP");
		String[] ipArr = ip.split("\\.");
		/*InetAddress inet = InetAddress.getByAddress(new byte[]{((byte) ((Integer.parseInt(ipArr[0]) & 0xFF000000) >> 24)),
				((byte) ((Integer.parseInt(ipArr[1]) & 0x00FF0000) >> 16)),
				((byte) ((Integer.parseInt(ipArr[2]) & 0x0000FF00) >> 8)),
				((byte) ((Integer.parseInt(ipArr[3]) & 0x000000FF) >> 0))});*/
		long asLong = 0;
		byte[] b = new byte[ipArr.length];
		for (int i = 0; i < ipArr.length; i++){
			asLong = (asLong << 8) | Integer.parseInt(ipArr[i]);
			b[i] = (byte) asLong;
		}
		
		InetAddress inet = InetAddress.getByAddress(b);
		
		MongoCredential mongoCred = MongoCredential.createCredential(this.mdbServiceParam.getMdbServiceNSParamMap().get("mdbUser"),
				this.mdbServiceParam.getMdbServiceNSParamMap().get("mdbName"),
				this.mdbServiceParam.getMdbServiceNSParamMap().get("mdbPassword").toCharArray());
		List<MongoCredential> lCredMongo = new LinkedList<MongoCredential>();
		lCredMongo.add(mongoCred);
		List<ServerAddress> seed = new ArrayList<ServerAddress>();
		seed.add(new ServerAddress(ip, Integer.parseInt(this.mdbServiceParam.getMdbServiceNSParamMap().get("mdbPort"))));
		this.mdbClient = new MongoClient(seed,
				lCredMongo);

		this.mdbDatabase = this.mdbClient.getDatabase(this.mdbServiceParam.getMdbServiceNSParamMap().get("mdbName"));
		this.mdbName = this.mdbServiceParam.getMdbServiceNSParamMap().get("mdbName");
	}
	
	private static final String dateScarabDBPattern = "yyyy-MM-dd'T'HH:mm:ss'Z'";

	public static String getDatescarabdbpattern() {
		return BaseNoSql.dateScarabDBPattern;
	}
	
	protected static final String textCollection = "text_";
	protected static final String metatagCollection = "metatag_";
	
	protected String getSpecificColllection(String base,ArrayList<String> l, String lang){
		if ((base == null || base.isEmpty()) && (lang == null || lang.isEmpty()))
			return null;
		String r = new String();
		r = r.concat(base);
		if (l != null)
			for(String s:l){
				r = r.concat(s);
				r = r.concat("_");
			}
		r = r.concat(lang);
		return r;
	}
	
	protected int insertMetatag(ArrayList<String> a,String collection,String lang,Metatag meta,String relId){
		
		Map<String, String> in = null;
		int r = 0;
		if ((in = Metatag.checkExistMetatags(meta)) == null 
				|| (meta.getKey() == null || meta.getKey().size() == 0)){
			r = 1;
		} else if (in != null && in.size() > 0 && (meta.getKey() == null || meta.getKey().size() == 0)){	
			collection = getSpecificColllection(BaseNoSql.metatagCollection, a, lang);
			Document metaWithOutKey = new Document();
			metaWithOutKey.putAll(in);
			metaWithOutKey.put("relId", relId);
			this.getMdbDatabase().getCollection(collection).insertOne(metaWithOutKey);
			r = 2;
		} else if (meta.getKey() != null && meta.getKey().size() > 0 && in != null && in.size() != 0){
			collection = getSpecificColllection(BaseNoSql.metatagCollection, a, lang);
			Document metaWithKey = new Document();
			metaWithKey.putAll(in);
			
				metaWithKey.put("what", meta.getKey());
			
			metaWithKey.put("relId", relId);
			this.getMdbDatabase().getCollection(collection).insertOne(metaWithKey);
			r = 3;
		}
		return r;
	} 
}
