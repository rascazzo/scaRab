package it.er.tag;

import it.er.basic.Basic;
import it.er.dao.Site;
import it.er.dao.sidebar.SBTagOrder;
import it.er.dao.sidebar.SBTagname;
import it.er.dao.sidebar.TagNameMapper;
import it.er.dao.sidebar.TagOrderMapper;
import it.er.object.Attribute;
import it.er.object.Branch;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.transaction.PlatformTransactionManager;

public class TagDAOBean extends Basic implements XTag{

	private JdbcTemplate jdbcTemplate = null;
	
	private static Logger log = LogManager.getLogger(TagDAOBean.class);
	
	private SiteAccess siteManagement;
	
	
	public SiteAccess getSiteManagement() {
		return siteManagement;
	}
	@Autowired
	public void setSiteManagement(SiteAccess siteManagement) {
		this.siteManagement = siteManagement;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate){
		this.jdbcTemplate = jdbcTemplate;
	}
	
	private JdbcTemplate getJdbcTemplate() {
		return this.jdbcTemplate;
	}
	
	@Autowired
	private PlatformTransactionManager transactionManager;
	
	public PlatformTransactionManager getTransactionManager() {
		return transactionManager;
	}

	
	public void setTransactionManager(PlatformTransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}

	
	public void close(){
		log.info("Destroy tagDao bean");
		this.jdbcTemplate = null;
	}
	
	public void start(){
		log.info("Init tagDao bean");
	}
	
	@Override
	public Map<Site,List<Attribute>> getAttributes(String tagname) throws SQLException,Exception{
		String sql = "SELECT * from tag_attribut a inner join tag_name_attr na on" +
				" a.id=na.idattribut inner join tag_name n on na.idtagname=n.id inner join site  s "
				+ " on n.tag_siteid=s.idsite WHERE n.name=? AND s.idsite=?";
		RowMapper<Attribute> rm = new AttributeRowMapper();
		Map<Site,List<Attribute>> map = new HashMap<Site, List<Attribute>>();
		Iterator<Site> s = siteManagement.getAllSite().iterator();
		Site i = null;
		
			while (s.hasNext()){
				i = s.next();
				List<Attribute> l = getJdbcTemplate().query(sql, new Object[]{tagname,i.getIdsite()}, rm);
				if (l != null)
					map.put(i, l);
			}
		return map;
	}

	@Override
	public Integer getOrder(String tagname,Site s) throws SQLException,Exception{
		String sql = "SELECT o.* from tag_order o inner join tag_name n on o.idtagname=n.id inner join site s " +
				" on n.tag_siteid=s.idsite WHERE n.name=? AND s.idsite=?";
		RowMapper<SBTagOrder> rm = new TagOrderMapper();
		SBTagOrder io = null;
		try{
			io = getJdbcTemplate().queryForObject(sql, new Object[]{tagname,s.getIdsite()}, rm);
		} catch (EmptyResultDataAccessException e){
			io = new SBTagOrder();
			io.setNumorder(-1);
		} 
		return io.getNumorder();
	}

	@Override
	public Boolean hasAttributes(String tagname, Attribute attribute,Site s) throws SQLException,Exception{
		String sql = "SELECT count(na.idattribut) from tag_attribut a inner join tag_name_attr na on " +
				"a.id=na.idattribut inner join tag_name n on na.idtagname=n.id inner join site  s on n.tag_siteid=s.idsite "
				+ "WHERE n.name=? AND s.idsite=?";
		Boolean has = null;
		try{
			Integer c = getJdbcTemplate().queryForObject(sql, new Object[]{tagname,s.getIdsite()}, Integer.class);
			boolean is = false;
			if (c.intValue()>0)
				is = true;
			has = new Boolean(is);
		} catch (EmptyResultDataAccessException e){
			has = new Boolean(false);
		} 
			
		return has;
	}

	@Override
	public Branch getBranch(String tagname,Site s) throws SQLException,Exception{
		String sql = "SELECT m.branch,n.id from tag_map m inner join tag_name n on m.idtagnameFK=n.id " +
				" inner join site s on n.tag_siteid=s.idsite WHERE n.name=? AND s.idsite=?";
		List<Map<String,Object>> l = getJdbcTemplate().queryForList(sql, new Object[]{tagname,s.getIdsite()});
		Branch b = new Branch();
		b.setIdtagname((Integer) l.get(0).get("id"));
		Iterator<Map<String,Object>> i = l.iterator();
		while (i.hasNext()){
			b.getBranches().add((String) i.next().get("branch"));
		}
		return b;
	}

	@Override
	public Map<Site,List<Tag>> getTags(String tagnamefather, Boolean active)  throws SQLException,Exception{
		String ac = "";
		if (active!= null && active)
			ac = ac+"and n2.active";
		else if (active != null && !active)
			ac = ac+"and not n2.active";
		String sql = "SELECT n.* from tag_name n inner join tag_son s on n.id=s.idtagsonFK inner join tag_name "
				+ "n2 on s.idtagnameFK=n2.id inner join site st on st.idsite=n2.tag_siteid WHERE n2.name=? "+ac+" AND st.idsite=?";
		Map<Site,List<Tag>> map = new HashMap<Site, List<Tag>>();
		Iterator<Site> s = siteManagement.getAllSite().iterator();
		Site d = null;
		
			while (s.hasNext()){
					d = s.next();
					RowMapper<Tag> rm = new TagRowMapper();
					List<Tag> l = getJdbcTemplate().query(sql, new Object[]{tagnamefather,d.getIdsite()}, rm);
					map.put(d, l);
			}
			
		return map;
	}
	
	@Override
	public List<Tag> getTags(String tagnamefather, Boolean active, String key, String value,String lang,Site s) throws SQLException,Exception{
		String ac = "";
		if (active!= null && active)
			ac = ac+"and n2.active";
		else if (active != null && !active)
			ac = ac+"and not n2.active";
		String sql = "SELECT n.*,vl.name_value from tag_name_value vl inner join from tag_name n on n.id=vl.id_tag_name  inner join tag_son s on n.id=s.idtagsonFK inner join tag_name "
				+ "n2 on s.idtagnameFK=n2.id inner join site st on st.idsite=n2.tag_siteid inner join tag_name_attr na on na.idtagname=n2.id " +
				"inner join tag_attribut a on a.id=na.idattribut WHERE n2.name=? "+ac+" and a.name=? and a.value=? AND st.idsite=? AND vl.lang=?";
		List<Tag> l = new LinkedList<Tag>(); 
		RowMapper<Tag> rm = new TagRowMapper();
		l = getJdbcTemplate().query(sql, new Object[]{tagnamefather,key,value,s.getIdsite(),lang}, rm);
		return l;
	}
	
	@Override
	public Boolean hasSons(String tagname,Site s) throws SQLException,Exception{
		String sql = "SELECT count(s.idtagsonFK) from tag_son s inner join tag_name n on " +
				"s.idtagnameFK=n.id inner join site st on st.idsite=n.tag_siteid WHERE n.name=? AND st.idsite=?";
		Boolean has = new Boolean(false);
		try{
			Integer c = getJdbcTemplate().queryForObject(sql, new Object[]{tagname,s.getIdsite()}, Integer.class);
			boolean is = false;
			if (c.intValue()>0)
				is = true;
			has = new Boolean(is);
		} catch (EmptyResultDataAccessException e){
			has = new Boolean(false);
		} 
		
		return has;
	}

	@Override
	public Tag getRootTag(Integer id,Site s) throws SQLException,Exception{
		String sql = "SELECT * from tag_name_value vl inner join from tag_name n2 on n2.id=vl.id_tag_name inner join tag_son s on n2.id=s.idtagnameFK " +
				" inner join tag_name n on s.idtagsonFK=n.id inner join site st on st.idsite=n.tag_siteid WHERE n.id=? AND st.idsite=?";
		Tag rId = null;
		try{
			rId = getJdbcTemplate().queryForObject(sql, new Object[]{id,s.getIdsite()}, Tag.class);
		} catch (EmptyResultDataAccessException e){
			rId = null;
		} 	
		return rId;
	}
	
	@Override
	public Map<Site,List<Tag>> getAllRootTag() throws SQLException,Exception{
		String sql = "SELECT n2.* from tag_name n2 left join tag_son s on n2.id=s.idtagnameFK " +
				" inner join site st on st.idsite=n2.tag_siteid WHERE n2.id not in (select idtagsonFK from tag_son s2) AND st.idsite=? group by n2.name";
		Map<Site,List<Tag>> map = new HashMap<Site, List<Tag>>();
		Iterator<Site> s = siteManagement.getAllSite().iterator();
		Site d = null;
		
			while (s.hasNext()){
					d = s.next();
					RowMapper<SBTagname> rm = new TagNameMapper();
					List<SBTagname> sta = getJdbcTemplate().query(sql,  rm, new Object[]{d.getIdsite()});
					Iterator<SBTagname> sb = sta.iterator();
					List<Tag> exitL = new ArrayList<Tag>();
					while (sb.hasNext()){
						SBTagname t = sb.next(); 
						if (t.getActive()){
							Tag g = new Tag();
							g.setTagname(t.getName());
							g.setTextvalue(t.getTextValue());
							g.setId(t.getId());
							g.setActive(t.getActive());
							exitL.add(g);
						}
					}
					map.put(d, exitL);
			}
			
		return map;
		
	}

	@Override
	public Boolean isAttribute(Attribute a,Site s) throws SQLException,Exception{
		String sql = "SELECT count(*) from tag_attribut t inner join tag_name_attr a on a.idattribut=t.id "
				+ "inner join tag_name n on n.id=a.idtagname inner join site st on st.idsite=n.tag_siteid WHERE t.name=? AND t.value=? AND st.idsite=?";
		Boolean isA = new Boolean(false);
				try{
					Integer c = getJdbcTemplate().queryForObject(sql, new Object[]{a.getName(),a.getValue(),s.getIdsite()}, Integer.class);
				
					boolean is = false;
					if (c.intValue()>0)
						is = true;
					isA = new Boolean(is);
				} catch (EmptyResultDataAccessException e){
					isA = new Boolean(false);
				} 
		return isA;
	}
	
	@Override
	public Integer getIdTagname(String tagname,Site s) throws SQLException{
		String fksql = "SELECT n.id from tag_name n inner join site s on n.tag_siteid=s.idsite WHERE name=? AND s.idsite=?";
		Integer id = null;
		try {
			id = getJdbcTemplate().queryForObject(fksql, new Object[]{tagname,s.getIdsite()}, Integer.class);
		} catch (EmptyResultDataAccessException e){
			id = null;
		} catch (Exception e) {
			throw new SQLException(e);
		}
		return id;
	}
	
	@Override
	public int[] insertAttributes(final List<Attribute> l, String tagname,Site s) throws SQLException{
		
		SimpleJdbcInsert sJdbc = new SimpleJdbcInsert(getJdbcTemplate().getDataSource());
		sJdbc.withTableName("tag_attribut").usingColumns("name","value");
		final List<Integer> key = new LinkedList<Integer>();
		Iterator<Attribute> i = l.iterator();
		while (i.hasNext()){
			Map<String,Object> attr = new HashMap<String, Object>();
			Attribute a = i.next();
			attr.put("name", a.getName());
			attr.put("value", a.getValue());
			sJdbc.usingGeneratedKeyColumns("id");
			Number u = sJdbc.executeAndReturnKey(attr);
			if (u!=null && u.intValue()!=0){
				key.add(u.intValue());
			}
		}
		
		sJdbc = null;
		
		final Integer id = getIdTagname(tagname,s);
		String sql = "INSERT into tag_name_attr (idtagname,idattribut) values (?,?)"; 
		BatchPreparedStatementSetter batch = new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				ps.setInt(1, id.intValue());
				ps.setInt(2, key.get(i));
			}
			
			@Override
			public int getBatchSize() {
				return key.size();
			}
		};
		
		return getJdbcTemplate().batchUpdate(sql, batch);
		
	}

	@Override
	public Integer insertOrder(Integer numorder, String tagname,Site s) throws SQLException{
		Integer id = getIdTagname(tagname,s);
		String sql = "INSERT into tag_order (idtagname,numorder) values (?,?)";
		return getJdbcTemplate().update(sql, id.intValue(), numorder);
	}

	@Override
	public int[] insertBranch(final List<String> branch, String tagname,Site s) throws SQLException{
		final Integer id = getIdTagname(tagname,s);
		String sql = "INSERT into tag_map (idtagnameFK,branch) values (?,?)"; 
		BatchPreparedStatementSetter batch = new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				String b = branch.get(i);
				ps.setInt(1, id.intValue());
				ps.setString(2, b);
			}
			
			@Override
			public int getBatchSize() {
				return branch.size();
			}
		};
		
		return getJdbcTemplate().batchUpdate(sql, batch);
		
	}

	@Override
	public int[] insertTags(final Map<String,String> tag, String tagnamefather,final Map<String,Integer> numorder,final String lang,final Site s) throws SQLException{
		Set<String> keys = tag.keySet();
		Iterator<String> t = keys.iterator();
		final List<String> tagnames = new LinkedList<String>();
		while(t.hasNext()){
			tagnames.add(t.next());
		}
		
		class TagFather{
			private final Map<String,Integer> insertTagFather(){
				final Map<String,Integer> key = new HashMap<String,Integer>();
				SimpleJdbcInsert sJdbc = new SimpleJdbcInsert(getJdbcTemplate().getDataSource());
				sJdbc.withTableName("tag_name").usingColumns("name","active","textvalue","tag_siteid");
				sJdbc.setGeneratedKeyName("id");
				Iterator<String> i = tagnames.iterator(); 
				while (i.hasNext()){
					Map<String,Object> tagins = new HashMap<String, Object>();
					String tagname = i.next();
					tagins.put("name", tagname);
					tagins.put("active", true);
					tagins.put("textvalue", tag.get(tagname));
					tagins.put("tag_siteid", s.getIdsite());
					Number u = sJdbc.executeAndReturnKey(tagins);
					if (u!=null && u.intValue()!=0){
						key.put(tagname,u.intValue());
					}
				}
				sJdbc = null;
				return key;
			}
		}
		
		
		if (tagnamefather!=null){
			final Map<String,Integer> key = new TagFather().insertTagFather();
			final Integer id = getIdTagname(tagnamefather,s);
			if (id==null || id.intValue()==0) return null;
			BatchPreparedStatementSetter batch = new BatchPreparedStatementSetter() {
				
				@Override
				public void setValues(PreparedStatement ps, int i) throws SQLException {
					ps.setInt(1, id.intValue());
				}
				
				@Override
				public int getBatchSize() {
					return tag.size();
				}
			};
			
			
			int j = 0;
			while (j < tagnames.size()){
				this.insertOrder(numorder.get(tagnames.get(j)), tagnames.get(j), s);
				j++;
			}
			
			j = 0;
			while (j < tagnames.size()){
				String sqls = "INSERT into tag_son (idtagnameFK,idtagsonFK) values (?,"+key.get(tagnames.get(j)).intValue()+")"; 
				getJdbcTemplate().batchUpdate(sqls, batch);
				String sqlsl = "INSERT into tag_name_value (id_tag_name,name_value,lang) values (?,?,?)";
				getJdbcTemplate().update(sqlsl, new Object[]{key.get(tagnames.get(j)).intValue(),tag.get(tagnames.get(j)),lang});
				j++;
			}
			int[] r = new int[key.size()];
			j = 0;
			while (j < tagnames.size()){
				r[j] = key.get(tagnames.get(j));
				j++;
			}
			return r;
		} else {
				
			final Map<String,Integer> key = new TagFather().insertTagFather();
			int j = 0;
			while (j < tagnames.size()){
				this.insertOrder(numorder.get(tagnames.get(j)), tagnames.get(j), s);
				String sqlsl = "INSERT into tag_name_value (id_tag_name,name_value,lang) values (?,?,?)";
				getJdbcTemplate().update(sqlsl, new Object[]{key.get(tagnames.get(j)).intValue(),tag.get(tagnames.get(j)),lang});
				j++;
			}
			int[] r = new int[key.size()];
			for (int i=0;i<key.size();i++)
				r[i] = key.get(tagnames.get(i)).intValue();
			return r;
		}
	}

	@Override
	public Integer deleteTagname(String tagname,Site s) throws SQLException{
		String sql = "DELETE from tag_name WHERE name=? AND tag_siteid=?";
		return getJdbcTemplate().update(sql, tagname,s.getIdsite());
	}
	
	@Override
	public Integer deleteTagname(Integer id,Site s) throws SQLException{
		String sql = "DELETE from tag_name WHERE id=? AND tag_siteid=?";
		return getJdbcTemplate().update(sql,id,s.getIdsite());
	}
	
	@Override
	public Integer simpleUpdateTagname(Integer id, boolean active, String textvalue,String lang,Site s) throws SQLException{
		String sql = "UPDATE tag_name SET active=?, textvalue=? WHERE id=? AND tag_siteid=?";
		return getJdbcTemplate().update(sql, active,textvalue,id,s.getIdsite());
		
	}

	@Override
	public List<String> getxTagFromEntity(String container) throws SQLException {
		String sql = "SELECT tagname from "+container+" group by tagname";
		List<String> l = null;
		l = getJdbcTemplate().queryForList(sql,String.class);
		log.info(sql+"{"+container+"}");
		return l;
	}
	
	@Override
	public List<Tag> getxTagFromTagNameEnt() throws SQLException {
		String sql = "SELECT * from tag_name_value vl inner join tag_name n";
		List<Tag> l = null;
		l = getJdbcTemplate().queryForList(sql,Tag.class);
		log.info(sql);
		return l;
	}
	
	
	
	@Override
	public List<SBTagOrder> getAllOrder(Site s,String tagnamerel) throws SQLException, Exception {
		String sql = null;
		List<SBTagOrder> lo = null;
		RowMapper<SBTagOrder> rm = new TagOrderMapper();
		if (tagnamerel != null){
			sql = "Select o.* from tag_order o inner join tag_son so on o.idtagname=so.idtagsonFK inner join tag_name n on so.idtagnameFK=n.id inner join site s on n.tag_siteid=s.idsite WHERE name=? AND s.idsite=? order by o.numorder";
			lo = (List<SBTagOrder>) getJdbcTemplate().query(sql, new Object[]{tagnamerel,s.getIdsite()},rm);
		} else {
			sql = "SELECT o.* from tag_order o inner join tag_name n2 on o.idtagname= n2.id left join tag_son s on n2.id=s.idtagnameFK " +
					" inner join site st on st.idsite=n2.tag_siteid WHERE n2.id not in (select idtagsonFK from tag_son s2) AND st.idsite=?  group by n2.name order by o.numorder";
			lo = (List<SBTagOrder>) getJdbcTemplate().query(sql, new Object[]{s.getIdsite()},rm);
		}
		
		return lo;
	}
	@Override
	public int[] updateOrder(List<SBTagOrder> sb)
			throws SQLException {
		int[] r = new int[sb.size()];
		String sql = "Update tag_order set numorder=? WHERE idtagname=?";
		for (int i = 0; i<sb.size();i++){
			r[i] = getJdbcTemplate().update(sql, new Object[]{sb.get(i).getNumorder(),sb.get(i).getIdtagname()});
		}
		return r;
	}
	@Override
	public SBTagname getTagname(String tagname, String lang,Site s) throws SQLException,
			Exception {
		RowMapper<SBTagname> rm = new TagNameMapper();
		SBTagname tgn = null;
		if (tagname != null){
			String sql ="Select * from tag_name_value vl inner join tag_name inner join site s on n.tag_siteid=s.idsite WHERE name=? and st.idsite=? AND vl.lang=?";
			try {
				tgn = getJdbcTemplate().queryForObject(sql, new Object[]{tagname,s.getIdsite(),lang},rm);
			} catch (EmptyResultDataAccessException e){
				tgn = null;
			}
		} 
		return tgn;
		
	}
	@Override
	public List<Attribute> getAttributes(String tagname, Site s) throws SQLException,Exception{
		String sql = "SELECT * from tag_attribut a inner join tag_name_attr na on" +
				" a.id=na.idattribut inner join tag_name n on na.idtagname=n.id inner join site  s "
				+ " on n.tag_siteid=s.idsite WHERE n.name=? AND s.idsite=?";
		RowMapper<Attribute> rm = new AttributeRowMapper();
		List<Attribute> lAtt = new LinkedList<Attribute>();
		lAtt = getJdbcTemplate().query(sql, new Object[]{tagname,s.getIdsite()}, rm);
		return lAtt;
	}
	@Override
	public List<Tag> getTags(String tagnamefather, Boolean active, Site s)
			throws SQLException, Exception {
		String ac = "";
		if (active!= null && active)
			ac = ac+"and n2.active";
		else if (active != null && !active)
			ac = ac+"and not n2.active";
		String sql = "SELECT n.*,vl.name_value from tag_name_value vl inner join tag_name n on n.id=vl.id_tag_name inner join tag_son s on n.id=s.idtagsonFK inner join tag_name "
				+ "n2 on s.idtagnameFK=n2.id inner join site st on st.idsite=n2.tag_siteid WHERE n2.name=? "+ac+" AND st.idsite=?";
		List<Tag> tBS = new LinkedList<Tag>();
					RowMapper<Tag> rm = new TagRowMapper();
					tBS = getJdbcTemplate().query(sql, new Object[]{tagnamefather,s.getIdsite()}, rm);
			
		return tBS;
	}
	@Override
	public List<Tag> getAllRootTag(Site s) throws SQLException, Exception {
		String sql = "SELECT n2.* from tag_name n2 left join tag_son s on n2.id=s.idtagnameFK " +
				" inner join site st on st.idsite=n2.tag_siteid WHERE n2.id not in (select idtagsonFK from tag_son s2) AND st.idsite=? group by n2.name";
		List<Tag> aRtS = new LinkedList<Tag>();
					RowMapper<SBTagname> rm = new TagNameMapper();
					List<SBTagname> sta = getJdbcTemplate().query(sql,  rm, new Object[]{s.getIdsite()});
					Iterator<SBTagname> sb = sta.iterator();
					while (sb.hasNext()){
						SBTagname t = sb.next(); 
						if (t.getActive()){
							Tag g = new Tag();
							g.setTagname(t.getName());
							g.setTextvalue(t.getTextValue());
							g.setId(t.getId());
							g.setActive(t.getActive());
							aRtS.add(g);
						}
					}
			
		return aRtS;
	}
	@Override
	public String getTagnameValueByLang(String tagname,
			String lang, Site s) throws SQLException {
		String tgv = null;
		if (tagname != null && lang != null){
			String sql ="Select vl.name_value from tag_name_value vl inner join tag_name n on "
					+ "n.id=vl.id_tag_name inner join site st on n.tag_siteid=st.idsite WHERE name=? and st.idsite=? AND vl.lang=?";
			try {
				tgv = getJdbcTemplate().queryForObject(sql, new Object[]{tagname,s.getIdsite(),lang},String.class);
			} catch (EmptyResultDataAccessException e){
				tgv = null;
			}
		} 
		return tgv;
	}
	@Override
	public int insertTagnameValueByLang(String tagname, String inLangvalue,
			String lang, Site s) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}
	

}
