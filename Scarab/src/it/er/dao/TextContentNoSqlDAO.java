package it.er.dao;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.bson.Document;
import org.springframework.stereotype.Repository;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;

import it.er.basic.BaseNoSql;
import it.er.object.Metatag;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;


@Repository("textContent")
public class TextContentNoSqlDAO extends BaseNoSql implements TextContent,InitializingBean{
	
	private static final int noLimit = -1;
	
	
	
	public static int getNolimit() {
		return noLimit;
	}

	private static final Logger log = LogManager.getLogger(TextContentNoSqlDAO.class);

	@Autowired
        public void setMdbServiceParam(MdbServiceNSParam mdbServiceParam) {
                this.mdbServiceParam = mdbServiceParam;
        }

        @Override
        public void afterPropertiesSet() throws Exception {
		super.propSet();
	}

	@Override
	public int insert(Text t,String lang, Metatag meta) throws Exception {
		DateFormat format = new SimpleDateFormat(BaseNoSql.getDatescarabdbpattern(), Locale.ITALIAN);
		int r = 0;
		try {
			String idText = null;
			if (t.getTitle() != null){
				idText = t.getTitle().toLowerCase();
				idText = idText.replaceAll("([^\\x20^\\x2D^A-z0-9])+","");
				idText = idText.replaceAll("(\\x20)+", "-");
			}
			ArrayList<String> a = new ArrayList<String>();
			String collection = getSpecificColllection(BaseNoSql.textCollection, a, lang);
			this.getMdbDatabase().getCollection(collection)
			.insertOne(new Document().append("body", t.getBody())
						.append("archive",t.isArchive())
						.append("textIdUser",t.getTextIdUser())
						.append("title",t.getTitle())
						.append("order", t.getOrder())
						.append("idtext",idText)
						.append("sitename",t.getSitename())
						.append("tagname",t.getTagname()));
			List<String> inserted = this.getIdsViewByTitle(t.getTagname(), t.getTitle(), lang);
			String relId = "0";
			if (inserted == null || inserted.size() == 0)
				return 0;
			else if (inserted.size() >= 1){
				relId = idText;
			} 
			/*
			 * tmp multi id 
			 * //TO DO
			 */
			r = ++r + this.insertMetatag(a, collection, lang, meta, relId);
			
		} catch (Exception e){
			log.error(e.getMessage(), e);
			r = 0;
		}
		return r;
	}
	
	@Override
	public int insertSeries(TextSeries s,String colname,String lang, Metatag meta) throws Exception {
		int r = 0;
		try {
			ArrayList<String> a = new ArrayList<String>();
			a.add(colname);
			String collection = getSpecificColllection(BaseNoSql.textCollection, a, lang);
			
			this.getMdbDatabase().getCollection(collection)
				.insertOne(new Document().append("title", s.getTitle())
						.append("subtitle", s.getSubtitle()));
			Iterator<Text> i = s.getSerie().iterator();
			while (i.hasNext()){
				Text t = i.next();
				String idText = null;
				if (t.getTitle() != null){
					idText = t.getTitle().toLowerCase();
					idText = idText.replaceAll("([^\\x20^\\x2D^A-z0-9])+","");
					idText = idText.replaceAll("(\\x20)+", "-");
				}
				this.getMdbDatabase().getCollection(collection).insertOne(
					new Document().append("body", t.getBody())
							.append("archive",t.isArchive())
							.append("textIdUser",t.getTextIdUser())
							.append("title",t.getTitle())
							.append("order", t.getOrder())
							.append("idtext",idText)
							.append("sitename",t.getSitename())
							.append("tagname",t.getTagname())
							.append("argument", colname));
				r++;
				List<String> inserted = this.getIdsSeriesViewByTitle(t.getTagname(),colname, t.getTitle(), lang);
				String relId = "0";
				if (inserted == null || inserted.size() == 0)
					return 0;
				else if (inserted.size() >= 1){
					relId = idText;
				} 
				/*
				 * tmp multi id 
				 * //TO DO
				 */
				r = ++r + this.insertMetatag(a, collection, lang, meta,relId);
				
			}
			
		} catch (Exception e){
			log.error(e.getMessage(), e);
			r = 0;
		}
		return r;
	}

	@Override
	public int[] insert(List<Text> t,String lang) throws Exception{
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public List<String> getIdsView(String tagname,String lang) throws Exception{
		
		String collection = getSpecificColllection(BaseNoSql.textCollection, null, lang);
		FindIterable<Document> fid = this.getMdbDatabase().getCollection(collection)
				.find(new Document("tagname",tagname));
		List<String> l = new ArrayList<String>();
		MongoCursor<Document> c = fid.iterator();
		while (c.hasNext()){
			Document d = c.next();
			l.add(d.getString("idtext"));
		}
		return l;
	}
	
	@Override
	public Deque<Text> getTextListShort(int limit, int start,String tagname,String argument,String lang) throws Exception{
		ArrayList<String> a = new ArrayList<String>();
		if (argument != null && !argument.isEmpty())
			a.add(argument);
		String collection = getSpecificColllection(BaseNoSql.textCollection, a, lang);
		FindIterable<Document> fid = null;
		if (limit == TextContentNoSqlDAO.getNolimit())
			fid = this.getMdbDatabase().getCollection(collection)
					.find(new Document("tagname",tagname)).skip(start);
		else	
			fid = this.getMdbDatabase().getCollection(collection)
					.find(new Document("tagname",tagname)).limit(limit).skip(start);
		MongoCursor<Document> c = fid.iterator();
		List<Text> lN = new LinkedList<Text>(); 
		while (c.hasNext()){
			Document d = c.next();
			Text t = new Text();
			
			t.setArchive(d.containsKey("archive")?d.getBoolean("archive"):null);
			t.setBody(d.containsKey("body")?d.getString("body"):null);
			t.setCreateDate(d.containsKey("_id")?d.getObjectId("_id").getTimestamp():null);
			t.setIdText(d.containsKey("idtext")?d.getString("idtext"):null);
			t.setSitename(d.containsKey("sitename")?d.getString("sitename"):null);
			t.setTagname(d.containsKey("tagname")?d.getString("tagname"):null);
			t.setTextIdUser(d.containsKey("textIdUser")?d.getString("textIdUser"):null);
			t.setTitle(d.containsKey("title")?d.getString("title"):null);
			t.setOrder(d.containsKey("order")?d.getString("order"):null);
			lN.add(t);
		}
		Deque<Text> dN = new LinkedList<Text>();
		Iterator<Text> i = lN.iterator();
		while (i.hasNext()){
			dN.offer(i.next());
		}
		return dN;
	}
	
	@Override
	public List<Text> readTextList(int limit, int start,String tagname,String argument,String lang) throws Exception{
		ArrayList<String> a = new ArrayList<String>();
		if (argument != null && !argument.isEmpty())
			a.add(argument);
		String collection = getSpecificColllection(BaseNoSql.textCollection, a, lang);
		FindIterable<Document> fid = null; 
		if (limit == TextContentNoSqlDAO.getNolimit())	
			fid = this.getMdbDatabase().getCollection(collection)
					.find(new Document("tagname",tagname)).skip(start);
		else	
			fid = this.getMdbDatabase().getCollection(collection)
				.find(new Document("tagname",tagname)).limit(limit).skip(start);
		MongoCursor<Document> c = fid.iterator();
		List<Text> lN = new LinkedList<Text>(); 
		while (c.hasNext()){
			Document d = c.next();
			Text t = new Text();
			t.setArchive(d.containsKey("archive")?d.getBoolean("archive"):null);
			t.setBody(d.containsKey("body")?d.getString("body"):null);
			t.setCreateDate(d.containsKey("_id")?d.getObjectId("_id").getTimestamp():null);
			t.setIdText(d.containsKey("idtext")?d.getString("idtext"):null);
			t.setSitename(d.containsKey("sitename")?d.getString("sitename"):null);
			t.setTagname(d.containsKey("tagname")?d.getString("tagname"):null);
			t.setTextIdUser(d.containsKey("textIdUser")?d.getString("textIdUser"):null);
			t.setTitle(d.containsKey("title")?d.getString("title"):null);
			t.setOrder(d.containsKey("order")?d.getString("order"):null);
			lN.add(t);
		}
		return lN;
	}
	
	@Override
	public List<Text> readTextSeriesList(int limit, int start,String tagname,String colname,String lang) throws Exception{
		ArrayList<String> a = new ArrayList<String>();
		if (colname != null && !colname.isEmpty())
			a.add(colname);
		String collection = getSpecificColllection(BaseNoSql.textCollection, a, lang);
		FindIterable<Document> fid = null;
		if (limit == TextContentNoSqlDAO.getNolimit())
			fid = this.getMdbDatabase().getCollection(collection)
					.find(new Document("tagname",tagname).append("argument", colname)).skip(start);
		else	
			fid = this.getMdbDatabase().getCollection(collection)
					.find(new Document("tagname",tagname).append("argument", colname)).limit(limit).skip(start);
		MongoCursor<Document> c = fid.iterator();
		List<Text> lN = new LinkedList<Text>(); 
		while (c.hasNext()){
			Document d = c.next();
			Text t = new Text();
			t.setArchive(d.containsKey("archive")?d.getBoolean("archive"):null);
			t.setBody(d.containsKey("body")?d.getString("body"):null);
			t.setCreateDate(d.containsKey("_id")?d.getObjectId("_id").getTimestamp():null);
			t.setIdText(d.containsKey("idtext")?d.getString("idtext"):null);
			t.setSitename(d.containsKey("sitename")?d.getString("sitename"):null);
			t.setTagname(d.containsKey("tagname")?d.getString("tagname"):null);
			t.setTextIdUser(d.containsKey("textIdUser")?d.getString("textIdUser"):null);
			t.setTitle(d.containsKey("title")?d.getString("title"):null);
			t.setOrder(d.containsKey("order")?d.getString("order"):null);
			lN.add(t);
		}
		return lN;
	}

	@Override
	public List<String> getIdsSeriesView(String tagname, String colname,
			String lang) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getIdsViewByTitle(String tagname,String title, String lang)
			throws Exception {
		
		String collection = getSpecificColllection(BaseNoSql.textCollection, null, lang);
		String idText = null;
		if (title != null){
			idText = title.toLowerCase();
			idText = idText.replaceAll("([^\\x20^\\x2D^A-z0-9])+","");
			idText = idText.replaceAll("(\\x20)+", "-");
		}
		FindIterable<Document> fid = this.getMdbDatabase().getCollection(collection)
				.find(new Document("tagname",tagname)
				.append("idtext", idText));
		List<String> l = new ArrayList<String>();
		MongoCursor<Document> c = fid.iterator();
		while (c.hasNext()){
			Document d = c.next();
			l.add(d.getString("idtext"));
		}
		return l;
	}

	@Override
	public List<String> getIdsSeriesViewByTitle(String tagname, String colname,
			String title,String lang) throws Exception {
		ArrayList<String> a = new ArrayList<String>();
		if (colname != null && !colname.isEmpty())
			a.add(colname);
		String idText = null;
		if (title != null){
			idText = title.toLowerCase();
			idText = idText.replaceAll("([^\\x20^\\x2D^A-z0-9])+","");
			idText = idText.replaceAll("(\\x20)+", "-");
		}
		String collection = getSpecificColllection(BaseNoSql.textCollection, a, lang);
		FindIterable<Document> fid = this.getMdbDatabase().getCollection(collection)
				.find(new Document("tagname",tagname)
				.append("argument", colname)
				.append("idtext", idText));
		List<String> l = new ArrayList<String>();
		MongoCursor<Document> c = fid.iterator();
		while (c.hasNext()){
			Document d = c.next();
			l.add(d.getString("idtext"));
		}
		return l;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Metatag> readMetatagByTitle(String colname,
			String title, String lang) throws Exception {
		ArrayList<String> a = new ArrayList<String>();
		FindIterable<Document> fid = null;
		String idText = null;
		if (title != null){
			idText = title.toLowerCase();
			idText = idText.replaceAll("([^\\x20^\\x2D^A-z0-9])+","");
			idText = idText.replaceAll("(\\x20)+", "-");
		}
		if (colname != null && !colname.isEmpty())
			a.add(colname);
		String collection = getSpecificColllection(BaseNoSql.metatagCollection, a, lang);
		fid = this.getMdbDatabase().getCollection(collection)
				.find(new Document("relId", idText));
		MongoCursor<Document> c = fid.iterator();
		List<Metatag> lM = new LinkedList<Metatag>(); 
		while (c.hasNext()){
			Document d = c.next();
			Metatag m = new Metatag();
			m.setTitle(d.containsKey("title")?d.getString("title"):null);
			m.setType(d.containsKey("type")?d.getString("type"):null);
			m.setURL(d.containsKey("url")?d.getString("url"):null);
			m.setId(d.containsKey("relId")?d.getString("relId"):null);
			m.setImage(d.containsKey("image")?d.getString("image"):null);
			m.setSitename(d.containsKey("sitename")?d.getString("sitename"):null);
			m.setAdmin(d.containsKey("admin")?d.getString("admin"):null);
			m.setDescription(d.containsKey("description")?d.getString("description"):null);
			m.setAuthor(d.containsKey("author")?d.getString("author"):null);
			m.setLang(d.containsKey("lang")?d.getString("lang"):null);
			
			if (d.containsKey("what")){
				Document k = new Document("what",d.get("what"));
 				m.setKey((ArrayList<String>) k.get("what", ArrayList.class));
			}
			
			lM.add(m);
		}
		return lM;
	}

	@Override
	public List<Text> readTextList(int limit, int start, String tagname, String argument,
			String title, String lang) throws Exception {
		ArrayList<String> a = new ArrayList<String>();
		if (argument != null && !argument.isEmpty())
			a.add(argument);
		String idText = null;
		if (title != null){
			idText = title.toLowerCase();
			idText = idText.replaceAll("([^\\x20^\\x2D^A-z0-9])+","");
			idText = idText.replaceAll("(\\x20)+", "-");
		}
		String collection = getSpecificColllection(BaseNoSql.textCollection, a, lang);
		FindIterable<Document> fid = null;
		if (limit == TextContentNoSqlDAO.getNolimit())
			fid = this.getMdbDatabase().getCollection(collection)
					.find(new Document("tagname",tagname).append("idtext", idText)).skip(start);
		else	
			fid = this.getMdbDatabase().getCollection(collection)
					.find(new Document("tagname",tagname).append("idtext", idText)).limit(limit).skip(start);
		MongoCursor<Document> c = fid.iterator();
		List<Text> lN = new LinkedList<Text>(); 
		while (c.hasNext()){
			Document d = c.next();
			Text t = new Text();
			t.setArchive(d.containsKey("archive")?d.getBoolean("archive"):null);
			t.setBody(d.containsKey("body")?d.getString("body"):null);
			t.setCreateDate(d.containsKey("_id")?d.getObjectId("_id").getTimestamp():null);
			t.setIdText(d.containsKey("idtext")?d.getString("idtext"):null);
			t.setSitename(d.containsKey("sitename")?d.getString("sitename"):null);
			t.setTagname(d.containsKey("tagname")?d.getString("tagname"):null);
			t.setTextIdUser(d.containsKey("textIdUser")?d.getString("textIdUser"):null);
			t.setTitle(d.containsKey("title")?d.getString("title"):null);
			t.setOrder(d.containsKey("order")?d.getString("order"):null);
			lN.add(t);
		}
		return lN;
	}

}
