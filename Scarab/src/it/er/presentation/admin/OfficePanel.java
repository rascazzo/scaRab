/*package it.er.presentation.admin;


import it.er.account.AccountBreve;
import it.er.account.IAccount;
import it.er.basic.Basic;
import it.er.dao.IParamDAO;
import it.er.dao.preversion.Acasatua;
import it.er.dao.preversion.AcasatuaMin;
import it.er.dao.preversion.Cavalletto;
import it.er.dao.preversion.Commento;
import it.er.dao.preversion.CommentoDAO;
import it.er.dao.preversion.HtmlInterpreter;
import it.er.dao.preversion.IAcasatuaDAO;
import it.er.dao.preversion.ICavallettoDAO;
import it.er.dao.preversion.ICitazioneDAO;
import it.er.dao.preversion.ICommentoDAO;
import it.er.dao.preversion.IMotivoDAO;
import it.er.dao.preversion.INovitaDAO;
import it.er.dao.preversion.IQuadroDAO;
import it.er.dao.preversion.IStatDAO;
import it.er.dao.preversion.Motivo;
import it.er.dao.preversion.News;
import it.er.dao.preversion.Quadro;
import it.er.dao.preversion.QuadroMin;
import it.er.generic.GenericTag;
import it.er.layerintercept.AbstractIdentified;
import it.er.object.Citazione;
import it.er.object.HtmlContent;
import it.er.preResource.ResponseList;
import it.er.todelete.AccountToAdmin;
import it.er.util.ImageFile;
import it.er.util.MD5;
import it.er.util.SingletonLookup;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.dao.EmptyResultDataAccessException;





















import com.oreilly.servlet.MultipartRequest;




*//**
 * @author fabry
 *
 *//*
@Path("/panelAdmin")
public class OfficePanel extends AbstractIdentified{
	
	private static Logger log = LogManager.getLogger(OfficePanel.class);
	
	private INovitaDAO novitaDAO = null;
	private IAccount account = null;
	private IQuadroDAO quadroDao = null;

	private IStatDAO statDAO = null;
	private IParamDAO paramDAO = null;
	
	private void imageResize(ImageFile img, int mis, String name){
		log.info("Altezza : " + img.getHeight() + 
				"Larghezza : " + img.getWidth());
		if (img.getHeight()<mis && img.getWidth()<mis)
			log.info(name + ": non necessita di ridimensionamento");
		else if (img.getHeight()>img.getWidth()){
			img.resize(0, mis);
			log.info(name + ": ridimensionato y");
			}else if (img.getHeight()<img.getWidth()){
				img.resize(mis, 0);
				log.info(name + ": ridimensionato x");
				}else {
					img.resize(0, mis);
					log.info(name + "square: ridimensionato y");
					}
	}
	
	
	@POST
	@Path("login")
	@Consumes("application/x-www-form-urlencoded; charset=UTF-8")
	@Produces("application/json")
	public Response makeLogin(@FormParam("eMail") String email,
			@FormParam("password") String password,
			@Context ServletContext cx,
			@Context HttpServletRequest req,
			@Context HttpServletResponse res){
		String r = "{\"success\": false, \"msg\": \"general\"}";

		String passToMD5 = null;
		try {
			passToMD5 = MD5.getHash(password);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			log.warn("Unable to get hash",e);
		}
		account = SingletonLookup.getAccountDAO(cx);

				AccountBreve ad = new AccountBreve();
				try {
					ad = account.loginWithRole(email, passToMD5,"admin"); 
					if (ad==null){
						r = "{\"success\": false, \"msg\": \"noLoad\"}";
					} else {
				
							r = "{\"success\": true}";
							//req.getSession().setAttribute("admin", ad.getVis);
							Cookie mycookie = new Cookie("ericaAd","admin");
							mycookie.setMaxAge(3600);
							mycookie.setPath("/");
							res.addCookie(mycookie);
							log.info("Enter admin");
					
					}
				} catch (EmptyResultDataAccessException em){
					r = "{\"success\": false, \"msg\": \"new user\"}";
				} catch (SQLException sq){
					r = "{\"success\": false, \"msg\": \"error\"}";
				}

				
				account = null;

		return Response.ok(r,"application/json").build();
		
	}
	
	
	
//	@POST
//	@Path("newUser")
//	@Produces("application/json")
//	public Response makeUser(@Context ServletContext cx){
//		String r = "";
//		
//		return Response.ok(r,"application/json").build();
//	}
	
	@GET
	@Path("isAdmin")
	public Response isAdmin(@Context HttpServletRequest req){
		String r = "";
		r = "{\"status\": false }";
		
		
		Cookie[] cookies = req.getCookies();
		if (cookies!=null){
			for (int i=0;i<cookies.length;i++)
				if(cookies[i].getName().equals("ericaAd"))				
					if (cookies[i].getValue().equals("admin")){
						r = "{\"status\": true }";
						break;
					} 
		}
		
		return Response.ok(r,"application/json").build();
	}
	
	@POST
	@Path("logout")
	public Response logOutAdmin(@Context HttpServletRequest req,
			@Context HttpServletResponse res){
		String r = "";
		r = "{\"status\": false }";
		Cookie[] cookies = req.getCookies();
		if (cookies!=null){
			for (int i=0;i<cookies.length;i++)
				if(cookies[i].getName().equals("ericaAd"))				
					if (cookies[i].getValue().equals("admin")){
						cookies[i].setMaxAge(0);
						res.addCookie(cookies[i]);
						req.getSession().removeAttribute("admin");
						r = "{\"status\": true }";
						break;
					} 
		}
		return Response.ok(r,"application/json").build();
	}
	
	@GET
	@Path("loadAllQuadri")
	@Produces("application/json")
	public ResponseList<Quadro> loadAllQ(@Context ServletContext cx,
			@Context HttpServletRequest req,
			@DefaultValue("-1") @QueryParam("start") int start,
			@DefaultValue("-1") @QueryParam("limit") int limit){
		ResponseList<Quadro> resp = new ResponseList<Quadro>();
			List<Quadro> r = null;
			//this.quadroDao = SingletonLookup.getQuadroDao(cx);
			try {
				if (limit!=-1 && start==-1){
					start = 0;
					r = this.quadroDao.getQuadri(limit, start);
				} else 
					r = this.quadroDao.getQuadri(limit, start);
			} catch (Exception sql){
				log.error("List null", sql.getCause());
			}
			//this.statDAO = SingletonLookup.getStatDAO(cx);
				
			if (r!=null){
				resp.setLista(r);
				int total = this.statDAO.numeroQuadri();
				resp.setTotal(total);
				resp.setMsg("true");
			} else {
				resp.setMsg("false");
				log.warn(quadroDao.getClass().getName()+" : List null");
			}
			this.statDAO = null;
			this.quadroDao = null;
		return resp;
	}
	
	@POST
	@Path("dammiQuadro")
	@Consumes("application/x-www-form-urlencoded; charset=UTF-8")
	public Response dammiQuadro(@Context ServletContext cx,@FormParam("idQuadro") int id,@Context HttpServletRequest req){
		QuadroMin q = null;
		String res = "{\"success\":";
		paramDAO = SingletonLookup.getParamDAO(cx);
		//quadroDao = SingletonLookup.getQuadroDao(cx);
		try {
			q = quadroDao.getSingleQuadroMin(id);
			res += "true, \"id\":\""+q.getId()+"\",\"image\":\"/"+paramDAO.getParam(getQuadrithumbimagepath()).getValue()
					+"/"+q.getImage()+"\"}";
		}catch (Exception sql){
			log.error("Unable to select quadroMin", sql.getCause());
		}
		
		quadroDao = null;
		paramDAO = null;
		return Response.ok(res,"application/json").build();
	}
	
	@PUT
	@Path("updateQuadro/{id}")
	@Consumes("application/x-www-form-urlencoded;charset=UTF-8")
	public Response updateQuadro(@Context ServletContext cx,
									@Context HttpServletRequest req,
									@PathParam("id") int id,@FormParam("nome") String nome,
									@FormParam("descrizione") String descrizione,
									@FormParam("dataFine") String dataFine,
									@FormParam("dimensione") String dimensione,
									@FormParam("tela") String tela){
		String res = "{\"success\":false}";
		//quadroDao = SingletonLookup.getQuadroDao(cx);
				Quadro q = new Quadro();
				q.setNome(nome);
				q.setDescrizione(descrizione);
				q.setDataFine(Date.valueOf(dataFine.substring(0, 10)));
				q.setDimensione(dimensione);
				q.setTela(tela);
				q.setId(id);
				int r = 0;
				try{
					r = quadroDao.updateQuadro(q);
				} catch (Exception sql){
					log.error("Unable to update quadro",sql.getCause());
				}
				if (r==1){
					res = "{\"success\":true}";
				} else {
					log.warn("update quadro return 0 o more 1 row update");
				}
				quadroDao = null;
		return Response.ok(res,"application/json").build();
	}
	
	@POST
	@Path("caricaQuadro")
	@Consumes("multipart/form-data")
	public Response addQuadro(@Context ServletContext cx,
			@Context HttpServletRequest req){
		String res = "{\"success\":false, \"msg\":\"\"}";
		boolean okUpload = true;
		
			Quadro q = new Quadro();
			Map<String,String> m = new HashMap<String,String>();
			List<String> mNameFile = new ArrayList<String>();
			paramDAO = SingletonLookup.getParamDAO(cx);
			boolean xy = false;
			try {
				
				int size = 4;
				String wcp = paramDAO.getParam(getWCP()).getValue();
				String path = wcp +"/"+paramDAO.getParam(getQuadriimagepath()).getValue();
				//FileRenamePolicy policy = new LeteleFileRename();
				
				MultipartRequest mpr = new MultipartRequest(req,path,size * 1024 * 1024);	
				@SuppressWarnings("unchecked")
				Enumeration<String> param = mpr.getParameterNames();
				@SuppressWarnings("unchecked")
				Enumeration<String> fileParam = mpr.getFileNames();
				
				while (param.hasMoreElements()){
					String key = param.nextElement();
					String value = mpr.getParameter(key);
					if (key.equals("fineData"))
						value.replace('/','-');
					m.put(key, value);
				}
				
								
				while (fileParam.hasMoreElements()){
					String name = fileParam.nextElement();
					name = mpr.getFilesystemName(name);
					
					String pattern = "yyyy_MM_dd";
					SimpleDateFormat format = new SimpleDateFormat(pattern);
					
					mNameFile.add(name);
					//ridimensiono
					//path = cx.getRealPath("/quadriMin");
					int mis = getNumberspacethumbnail();
					ImageFile img = new ImageFile();
					String pathMin = wcp + "/" + paramDAO.getParam(getQuadrithumbimagepath()).getValue();
					img.setFile(path,name,pathMin);
					if (img.getHeight()>img.getWidth())
						xy = true;
					imageResize(img, mis, name);
						
					
						
					
				}
				
				
			} catch (IOException io){
				log.warn("Errore io file upload"+ io.getMessage());
				okUpload = false;
				res = "{\"success\":false, \"msg\":\"noUpload\"}";
			} catch (Exception e) {
				log.warn("Errore io file upload"+ e.getMessage());
				okUpload = false;
				res = "{\"success\":false, \"msg\":\"noUpload\"}";
			}
			
			if (okUpload){

				try {
					//quadroDao = SingletonLookup.getQuadroDao(cx);
					
					q.setNome(m.get("nome")!=null?m.get("nome"):"");
					q.setDimensione(m.get("dimensione"));
					q.setDescrizione(m.get("descrizione")!=null?m.get("nome"):"");
					q.setTela(m.get("tela")!=null?m.get("nome"):"");
					q.setDataFine(m.get("fineData")!=null && m.get("fineData").length() >0 ?Date.valueOf(m.get("fineData")):null);
					q.setQuadroIdUtente(1);
					q.setTagname(m.get("entity"));
					q.setXy(xy);
					if (mNameFile.size()==1)
						q.setImage(mNameFile.get(0));
					
					//Update db
					int r = 0;
					try{
						r = quadroDao.insertQuadro(q);
					} catch (Exception sql){
						log.error("Unable to insert quadro", sql.getCause());
					}
					
					if (r==1){
						res = "{\"success\":true}";
						log.info("Insert new work");
					} else {
						res = "{\"success\":false, \"msg\":\"noInsertSql\"}";
						log.warn("Insert image but row update 0");
					}
				} catch (Exception e){
					log.warn("Errore insert upload"+ e.getMessage());
					okUpload = false;
					res = "{\"success\":false, \"msg\":\"noUpload\"}";
				}
			}
			quadroDao = null;
			paramDAO = null;
			
		return Response.ok(res,"text/html").build();
		
	}
	
	@DELETE
	@Path("deleteQuadro/{id}")
	public Response deleteQuadro(@PathParam("id") int id,@Context ServletContext cx,@Context HttpServletRequest req){
		String res = "{\"success\":false}";
		boolean flag = false;
		try {
			String image = "";
			paramDAO = SingletonLookup.getParamDAO(cx);
			//quadroDao = SingletonLookup.getQuadroDao(cx);
				QuadroMin q = null;
				try{
					q = quadroDao.getSingleQuadroMin(id);
				} catch(Exception sql){
					log.error("Unable to select quadro min", sql.getCause());
				}
				if (q!=null){
					image = q.getImage();
				} else
					log.warn("Unable to select work. object null");
				if (quadroDao.deleteQuadro(id) == 1){
					res = "{\"success\":true}";
					flag = true;
				} else {
					log.warn("Unable to delete work.");
				}
			
			if (flag){
				String wcp = paramDAO.getParam(getWCP()).getValue();
				String path = paramDAO.getParam(getQuadriimagepath()).getValue();
				File f = new File(wcp+"/"+path+"/"+image);
				if (f.delete())
					log.info("Delete from "+path);
				path = paramDAO.getParam(getQuadrithumbimagepath()).getValue();
				File f2 = new File(wcp+"/"+path+"/"+image);
				if (f2.delete())
					log.info("Delete from "+path);
				
			}
		} catch (Exception e){
			log.warn("Errore delete"+ e.getMessage());
		}
			quadroDao = null;
		return Response.ok(res,"application/json").build();
	}
	
	private ICavallettoDAO cavallettoDao = null;
	
	@GET
	@Path("loadAllSkizzi")
	@Produces("application/json")
	public ResponseList<Cavalletto> loadAllC(@Context ServletContext cx,
			@Context HttpServletRequest req,
			@DefaultValue("-1") @QueryParam("start") int start,
			@DefaultValue("-1") @QueryParam("limit") int limit){
		
		ResponseList<Cavalletto> resp = new ResponseList<Cavalletto>();
			List<Cavalletto> r = null;
			//cavallettoDao = SingletonLookup.getCavallettoDAO(cx);
				try{
					if (limit!=-1 && start==-1){
						start = 0;
						r = cavallettoDao.getAllSkizzi(limit, start);
					} else 
						r = cavallettoDao.getAllSkizzi(limit, start);
				}catch (Exception sql){
					log.error("Unable to select all skizzi", sql.getCause());
				}
						
			if (r!=null){
				resp.setLista(r);
				//statDAO = SingletonLookup.getStatDAO(cx);
				int total = statDAO.numeroSchizzi();
				resp.setTotal(total);
				resp.setMsg("true");
			} else {
				resp.setMsg("false");
				log.warn("List provini is null");
			}
			statDAO = null;
			cavallettoDao = null;
		return resp;
	}
	
	@POST
	@Path("dammiSkizzo")
	@Consumes("application/x-www-form-urlencoded; charset=UTF-8")
	public Response dammiSkizzo(@Context ServletContext cx,
			@Context HttpServletRequest req, 
			@FormParam("idSkizzo") int id){
		Cavalletto q = null;
		String res = "{\"success\":";
		paramDAO = SingletonLookup.getParamDAO(cx);
		//cavallettoDao = SingletonLookup.getCavallettoDAO(cx);
		try{
			q = cavallettoDao.getSingleSkizzo(id);
		
		res += "true, \"id\":\""+q.getIdProvino()+"\",\"image\":\"/"+paramDAO.getParam(getSkizzithumbimagepath()).getValue()
				+"/"+q.getImage()+"\"}";
		} catch (Exception sql){
			log.error("Unable to select single skizzo", sql.getCause());
		}
//		cavallettoDao = null;
//		paramDAO = null;
		return Response.ok(res,"application/json").build();
	}
	
	@POST
	@Path("caricaSkizzo")
	@Consumes("multipart/form-data")
	public Response addSkizzo(@Context ServletContext cx,
			@Context HttpServletRequest req){
		String res = "{\"success\":false, \"msg\":\"\"}";
		int r = 0;
		boolean okUpload = true;
		
			Cavalletto q = new Cavalletto();
			Map<String,String> m = new HashMap<String,String>();
			List<String> mNameFile = new ArrayList<String>();
			paramDAO = SingletonLookup.getParamDAO(cx);
			boolean xy = false;
			try {
				
				int size = 4;
				
				String wcp = paramDAO.getParam(getWCP()).getValue();
				String path = wcp +"/"+paramDAO.getParam(getSkizziimagepath()).getValue();
				//FileRenamePolicy policy = new LeteleFileRename();
				
				MultipartRequest mpr = new MultipartRequest(req,path,size * 1024 * 1024);	
				@SuppressWarnings("unchecked")
				Enumeration<String> param = mpr.getParameterNames();
				@SuppressWarnings("unchecked")
				Enumeration<String> fileParam = mpr.getFileNames();
				
				while (param.hasMoreElements()){
					String key = param.nextElement();
					String value = mpr.getParameter(key);
					if (key.equals("data"))
						value.replace('/','-');
					m.put(key, value);
				}
				
								
				while (fileParam.hasMoreElements()){
					String name = fileParam.nextElement();
					name = mpr.getFilesystemName(name);
					
					String pattern = "yyyy_MM_dd";
					SimpleDateFormat format = new SimpleDateFormat(pattern);
					
					mNameFile.add(name);
					//ridimensiono
					//path = cx.getRealPath("/quadriMin");
					int mis = getNumberspacethumbnail();
					ImageFile img = new ImageFile();
					String pathMin = wcp + "/" + paramDAO.getParam(getSkizzithumbimagepath()).getValue();
					img.setFile(path,name,pathMin);
					if (img.getHeight()>img.getWidth())
						xy = true;
					imageResize(img, mis, name);
					
				}
				
				
			} catch (IOException | SQLException io){
				log.warn("Errore io file upload"+ io.getMessage());
				okUpload = false;
				res = "{\"success\":false, \"msg\":\"noUpload\"}";
			}
			
			if (okUpload){
			
				//cavallettoDao = SingletonLookup.getCavallettoDAO(cx);
					
					q.setTesto(m.get("testo"));
					if (m.get("data")!=null && m.get("data").length()>0)
						q.setData(Date.valueOf(m.get("data")));
					q.setTagname(m.get("entity"));
					
					q.setXy(xy);
					if (mNameFile.size()==1)
						q.setImage(mNameFile.get(0));
					
					String testIdQuadro = m.get("idQuadro");
					//Update db
					try {
						if (testIdQuadro!=null && !testIdQuadro.equals(""))
							r = cavallettoDao.insertSkizzo(q, Integer.parseInt(testIdQuadro));
						else 
							r = cavallettoDao.insertSkizzo(q, null);
					} catch (Exception sql){
						log.error("Unable to insert skizzo", sql.getCause());
					}
					if (r==1){
						res = "{\"success\":true}";
						log.info("Insert new skizzo");
					} else {
						res = "{\"success\":false, \"msg\":\"noInsertSql\"}";
						log.warn("Insert image but row update 0");
					}
				
			}
			cavallettoDao = null;
			paramDAO = null;
		return Response.ok(res,"text/html").build();
		
	}
	
	@PUT
	@Path("updateSkizzo/{id}")
	@Consumes("application/x-www-form-urlencoded")
	public Response updateSkizzo(@Context ServletContext cx,
									@Context HttpServletRequest req,
									@PathParam("id") int id,@FormParam("testo") String testo,
									@FormParam("idQuadro") int idQuadro,
									@FormParam("data") String data){
		String res = "{\"success\":";
		int r = 0;
		//cavallettoDao = SingletonLookup.getCavallettoDAO(cx);
				try {
					r = cavallettoDao.updateSkizzo(testo, data, idQuadro, id);
				} catch (Exception sql){
					log.error("Unable to update skizzo",sql.getCause());
				}
				if (r == 1){
					res += "true}";
				} else {
					res += "true, \"msg\":\"potrebbe già esistere la relazione\"}";
					log.warn("ispira: row 0 oppure potrebbe già esistere la relazione");
				}
		cavallettoDao = null;
		return Response.ok(res,"application/json").build();
	}
	
	@DELETE
	@Path("deleteSkizzo/{id}")
	@Produces("application/json")
	public Response deleteSkizzo(@PathParam("id") int id,
			@Context HttpServletRequest req,
			@Context ServletContext cx){
		String res = "{\"success\":false}";
		boolean flag = false;
		try {
			String image = "";
			//cavallettoDao = SingletonLookup.getCavallettoDAO(cx);
			paramDAO = SingletonLookup.getParamDAO(cx);
				Cavalletto q = null;
				try{
					q = cavallettoDao.getSingleSkizzo(id);
				} catch (Exception sql){
					log.error("Unable to select skizzo for delete",sql.getCause());
				}
				if (q!=null){
					image = q.getImage();
				} else
					log.warn("Unable to select provino. object null");
				try{
					if (cavallettoDao.deleteSkizzo(id) == 1){
						res = "{\"success\":true}";
						flag = true;
					}
				} catch (SQLException sql){
					log.error("Unable to delete skizzo",sql.getCause());
				}
			
			if (flag){
				String wcp = paramDAO.getParam(getWCP()).getValue();
				String path = paramDAO.getParam(getSkizziimagepath()).getValue();
				File f = new File(wcp+"/"+path+"/"+image);
				if (f.delete())
					log.info("Delete from "+path);
				path = paramDAO.getParam(getSkizzithumbimagepath()).getValue();
				File f2 = new File(wcp+"/"+path+"/"+image);
				if (f2.delete())
					log.info("Delete from "+path);
				
			}
		} catch (Exception e){
			log.error("Unable to delete single skizzo", e.getCause());
		}
			cavallettoDao = null;
			paramDAO = null;
		return Response.ok(res,"application/json").build();
	}
	
	
	private ICommentoDAO commentoDAO = null;
	
	@GET
	@Path("commenti")
	@Produces("application/json;charset=UTF-8")
	public ResponseList<Commento> getAllCommentiForQ(@Context ServletContext cx,
			@Context HttpServletRequest req,
			@DefaultValue("-1") @QueryParam("start") int start,
			@DefaultValue("-1") @QueryParam("limit") int limit,
			@DefaultValue("n") @QueryParam("type") String type){
		ResponseList<Commento> resp = new ResponseList<Commento>();
			List<Commento> r = null;
			//commentoDAO = SingletonLookup.getCommentoDAO(cx);
			if (!type.equals("n")){
			try{
				if (start == -1 && limit != -1){
					if (type.equals("Q"))
						r = commentoDAO.prendiAllCom(false, false, true, 0, limit);
					else if (type.equals("S"))
						r = commentoDAO.prendiAllCom(false, true, true, 0, limit);
						else if (type.equals("N"))
							r = commentoDAO.prendiAllCom(true, false, true, 0, limit);
				}else if (start != -1 && limit != -1){
					if (type.equals("Q"))
						r = commentoDAO.prendiAllCom(false, false, true, start, limit);
					else if (type.equals("S"))
						r = commentoDAO.prendiAllCom(false, true, true, start, limit);
						else if (type.equals("N"))
							r = commentoDAO.prendiAllCom(true, false, true, start, limit);
				}
			}catch (SQLException sql){
				log.error("Error in select comment type :"+type, sql.getCause());
			}
				if (r!=null){
					Iterator<Commento> i = r.iterator();
					while (i.hasNext())
						resp.getLista().add(i.next());
					int total = r.size();
					resp.setTotal(total);
					resp.setMsg("true");
				} else{
					resp.setMsg("errore.");
				}
			} else{
				resp.setMsg("errore. Verifica il type!");
			}
				commentoDAO = null;
		return resp;
	}
	
	@PUT
	@Path("updateCommento/{id}")
	@Consumes("application/x-www-form-urlencoded")
	public Response updateCommento(@Context ServletContext cx,
									@PathParam("id") int id,
									@FormParam("eliminato") boolean eliminato,
									@Context HttpServletRequest req){
		String res = "{\"success\":false}";
		int r = 0;
		//commentoDAO = SingletonLookup.getCommentoDAO(cx);
			try {
				r = commentoDAO.bannaCommento(id, eliminato);
			}catch (SQLException sql){
				log.error("Error in update comment", sql.getCause());
			}
				if (r==1){
					res = "{\"success\":true}";
					log.info("Update commento id: #"+id);
				}
		commentoDAO = null;
		return Response.ok(res,"application/json").build();
	}
	
	private ICitazioneDAO citazioneDao = null;
	
	@GET
	@Path("loadAllCitazioni")
	@Produces("application/json;charset=UTF-8")
	public ResponseList<Citazione> getAllCitazioni(@Context ServletContext cx,
			@DefaultValue("-1") @QueryParam("start") int start,
			@DefaultValue("-1") @QueryParam("limit") int limit,
			@Context HttpServletRequest req){
		ResponseList<Citazione> resp = new ResponseList<Citazione>();
			List<Citazione> r = null;
			//citazioneDao = SingletonLookup.getCitazioneDAO(cx);
			try{
				if (start == -1 && limit != -1)
				r = citazioneDao.getAllCit(0, limit);
				else if (start != -1 && limit != -1)
				r = citazioneDao.getAllCit(start, limit);
			} catch(Exception sql){
				log.error("Error in select citazioni", sql.getCause());
			}
				if (r!=null){
					resp.setLista(r);
					int total = r.size();
					resp.setTotal(total);
					resp.setMsg("true");
				} else {
					resp.setMsg("false");
					log.warn("List of cit is null");
				}
				citazioneDao = null;
		return resp;
	}
	
	@DELETE
	@Path("deleteCitazione/{id}")
	public Response deleteCitazione(@PathParam("id") int id,@Context ServletContext cx,
			@Context HttpServletRequest req){
		String res = "{\"success\":false}";
		boolean flag = false;
			try{
				
			String image = "";
			//citazioneDao = SingletonLookup.getCitazioneDAO(cx);
				Citazione q = null;
				try{
					q = citazioneDao.getCit(id);
				} catch (Exception sql){
					log.error("Error in select cit", sql.getCause());
				}
				if (q!=null){
					image = q.getImage();
				} 
				if (citazioneDao.eliminaCit(id) == 1){
					res = "{\"success\":true}";
					flag = true;
				}
			if (flag){
				paramDAO = SingletonLookup.getParamDAO(cx);
				String wcp = paramDAO.getParam(getWCP()).getValue();
				String path = wcp + "/" + paramDAO.getParam(getCitaimagepath()).getValue();
				File f = new File(path+"/"+image);
				if (f.delete())
					log.info("Delete from image citazione");
				else 
					log.warn("Delete data cit but not image");
				File ft = new File(path+"/"+"thumb_"+image);
				if (ft.delete())
					log.info("Delete from citazione image_thumb");
				else 
					log.warn("Delete data cit but not image_thumb");
				paramDAO = null;
			}
			} catch (Exception sql){
				log.error("Unable to delete cit", sql.getCause());
			}
			citazioneDao = null;
			
		return Response.ok(res,"application/json").build();
	}
	
	@PUT
	@Path("updateCitazione/{id}")
	@Consumes("application/x-www-form-urlencoded;charset=UTF-8")
	public Response updateCitazione(@Context ServletContext cx,
									@Context HttpServletRequest req,
									@PathParam("id") int id,
									@FormParam("autore") String autore,
									@FormParam("attiva") boolean attiva,
									@FormParam("testo") String testo) {
		String res = "{\"success\":false}";
		int r = 0;
		//citazioneDao = SingletonLookup.getCitazioneDAO(cx);
			try{
				r = citazioneDao.updateCitazione(autore, testo, id);
				if (r == 1){
					res = "{\"success\":true}";
					if (attiva){
						r = citazioneDao.setCitAttiva(id);
					}
				}
			} catch(Exception sql){
				log.error("Fall in update cit", sql.getCause());
			}
				citazioneDao = null;
		return Response.ok(res,"application/json").build();
	}
	
	@POST
	@Path("caricaCitazione")
	@Consumes("multipart/form-data;charset=UTF-8")
	public Response addCitazione(@Context ServletContext cx,
			@Context HttpServletRequest req){
		String res = "{\"success\":false, \"msg\":\"\"}";
		int r = 0;
		boolean okUpload = true;
		
			Citazione q = new Citazione();
			Map<String,String> m = new HashMap<String,String>();
			List<String> mNameFile = new ArrayList<String>();
			try {
				
				int size = 4;
				
				paramDAO = SingletonLookup.getParamDAO(cx);
				String wcp = paramDAO.getParam(getWCP()).getValue();
				String path = wcp + "/" + paramDAO.getParam(getCitaimagepath()).getValue();
				//FileRenamePolicy policy = new LeteleFileRename();
				
				MultipartRequest mpr = new MultipartRequest(req,path,size * 1024 * 1024);	
				@SuppressWarnings("unchecked")
				Enumeration<String> param = mpr.getParameterNames();
				@SuppressWarnings("unchecked")
				Enumeration<String> fileParam = mpr.getFileNames();
				
				while (param.hasMoreElements()){
					String key = param.nextElement();
					String value = mpr.getParameter(key);
					if (key.equals("data"))
						value.replace('/','-');
					m.put(key, value);
				}
				
								
				while (fileParam.hasMoreElements()){
					String name = fileParam.nextElement();
					name = mpr.getFilesystemName(name);
					
					String pattern = "yyyy_MM_dd";
					SimpleDateFormat format = new SimpleDateFormat(pattern);
					
					mNameFile.add(name);
					//ridimensiono
					//path = cx.getRealPath("/quadriMin");
					int mis = getNumberspacethumbnail();
					ImageFile img = new ImageFile();
					img.setFile(path,name,path,q.getThumb()+name);
					imageResize(img, mis, q.getThumb()+name);
				}
				
				
			} catch (SQLException | IOException io){
						log.error("Errore io file upload", io);
				okUpload = false;
				res = "{\"success\":false, \"msg\":\"noUpload\"}";
			}
			
			if (okUpload){
			
				//citazioneDao = SingletonLookup.getCitazioneDAO(cx);
					
					q.setTesto(m.get("testo"));
					q.setAutore(m.get("autore"));
					q.setCitIdUtente(1);
					if (mNameFile.size()==1)
						q.setImage(mNameFile.get(0));
					q.setAttiva(false);
										
					
					//Update db
					try{
						r = citazioneDao.insertCit(q);
					} catch (Exception sql){
						log.error("Error in insert cit", sql.getCause());
					}
					if (r == 1){
						res = "{\"success\":true}";
						log.info("Insert new citazione");
					} else {
						res = "{\"success\":false, \"msg\":\"noInsertSql\"}";
						log.warn("Insert new image but error in insert data sql");
					}
				
			}
			citazioneDao = null;
			paramDAO = null;
		return Response.ok(res,"text/html").build();
		
	}
	
//	@Path("caricaTestiXml")
//	@POST
//	@Consumes("application/x-www-form-urlencoded;charset=UTF-8")
//	public Response caricaTestiXml(@FormParam("codice") String codice,
//			@Context ServletContext cx){
//		String rtrn = "{\"success\":";
//		String path = "/xml";
//		path = cx.getRealPath(path);
//		File f = new File(path+"/"+"testi.xml");
//		try {
//			FileOutputStream out = new FileOutputStream(f);
//			out.write(codice.getBytes());
//			out.flush();
//			out.close();
//			cx.log("File testi.xml caricato con successo");
//			rtrn += "true}";
//		} catch (FileNotFoundException e) {
//			cx.log("File testi.xml non trovato");
//			e.printStackTrace();
//			rtrn += "false}";
//		} catch (IOException io){
//			cx.log("Impossibile scrivere su testi.xml");
//			io.printStackTrace();
//			rtrn += "false}";
//		}
//		return Response.ok(rtrn, "application/json").build();
//	}
	
	
	private IAcasatuaDAO acasatuaDao = null;
	
	@GET
	@Path("loadAllAcasatua")
	@Produces("application/json;charset=UTF-8")
	public ResponseList<Acasatua> getAllAcasatua(@Context ServletContext cx,
			@Context HttpServletRequest req,
			@DefaultValue("-1") @QueryParam("start") int start,
			@DefaultValue("-1") @QueryParam("limit") int limit){
		ResponseList<Acasatua> resp = new ResponseList<Acasatua>();
			List<Acasatua> r = null;
			//acasatuaDao = SingletonLookup.getAcasatuaDAO(cx);
			try{
				if (start == -1 && limit != -1)
					r = acasatuaDao.getAllAcasatua(0, limit,false);
				else if (start != -1 && limit != -1)
					r = acasatuaDao.getAllAcasatua(start, limit,false);
			} catch (Exception sql){
				log.error("Error in select acasatua", sql.getCause());
			}
				if (r!=null){
					resp.setLista(r);
					int total = r.size();
					resp.setTotal(total);
					resp.setMsg("true");
				} else {
					resp.setMsg("false");
					log.warn("List acasatua is null");
				}
				acasatuaDao = null;
		return resp;
	}
	
	@POST
	@Path("dammiAcasatua")
	@Consumes("application/x-www-form-urlencoded; charset=UTF-8")
	public Response dammiAcasatua(@Context ServletContext cx,@FormParam("idAcasatua") int id,
			@Context HttpServletRequest req){
		AcasatuaMin q = null;
		String res = "{\"success\":";
		//acasatuaDao = SingletonLookup.getAcasatuaDAO(cx);
			try{
				q = acasatuaDao.getSingleAcasatuaMin(id);
			} catch (Exception sql){
				log.error("Error in select single acasatua min", sql.getCause());
			}
		res += "true, \"id\":\""+q.getId()+"\",\"image\":\""+q.getImage()+"\"}";
		acasatuaDao = null;
		return Response.ok(res,"application/json").build();
	}
	
	@POST
	@Path("caricaAcasatua")
	@Consumes("multipart/form-data")
	public Response addAcasatua(@Context ServletContext cx,
			@Context HttpServletRequest req){
		String res = "{\"success\":false, \"msg\":\"\"}";
		int r = 0;
		boolean okUpload = true;
		
			Acasatua q = new Acasatua();
			Map<String,String> m = new HashMap<String,String>();
			List<String> mNameFile = new ArrayList<String>();
			paramDAO = SingletonLookup.getParamDAO(cx);
			boolean xy = false;
			try {
				
				int size = 4;
				
				String wcp = paramDAO.getParam(getWCP()).getValue();
				String path = wcp +"/"+paramDAO.getParam(getAcasaimagepath()).getValue();
				//FileRenamePolicy policy = new LeteleFileRename();
				
				MultipartRequest mpr = new MultipartRequest(req,path,size * 1024 * 1024);	
				@SuppressWarnings("unchecked")
				Enumeration<String> param = mpr.getParameterNames();
				@SuppressWarnings("unchecked")
				Enumeration<String> fileParam = mpr.getFileNames();
				
				while (param.hasMoreElements()){
					String key = param.nextElement();
					String value = mpr.getParameter(key);
					if (key.equals("data"))
						value.replace('/','-');
					m.put(key, value);
				}
				
								
				while (fileParam.hasMoreElements()){
					String name = fileParam.nextElement();
					name = mpr.getFilesystemName(name);
					
					String pattern = "yyyy_MM_dd";
					SimpleDateFormat format = new SimpleDateFormat(pattern);
					
					mNameFile.add(name);
					//ridimensiono
					//path = cx.getRealPath("/quadriMin");
					int mis = getNumberspacethumbnail();
					ImageFile img = new ImageFile();
					String pathMin = wcp + "/" + paramDAO.getParam(getAcasathumbimagepath()).getValue();
					img.setFile(path,name,pathMin);
					if (img.getHeight()>img.getWidth())
						xy = true;
					imageResize(img, mis, name);
					
				}
				
				
			} catch (SQLException | IOException io){
				log.warn("Errore io file upload"+ io.getMessage());
				okUpload = false;
				res = "{\"success\":false, \"msg\":\"noUpload\"}";
			}
			
			if (okUpload){
			
				//acasatuaDao = SingletonLookup.getAcasatuaDAO(cx);	
					q.setCommento(m.get("commento"));
					q.setEmail(m.get("email"));
					if (m.get("quadroId")!=null && m.get("quadroId").length()>0)
						q.setQuadroId(Integer.parseInt(m.get("quadroId")));
					else 
						q.setQuadroId(null);
					if (m.get("data")!=null && m.get("data").length()>0)
						q.setData(m.get("data"));
					q.setTagname(m.get("entity"));
					q.setXy(xy);
					if (mNameFile.size()==1)
						q.setImage(mNameFile.get(0));
					
					//Update db
					try {
						r = acasatuaDao.insertAcasatuaImage(q);
						
						
					} catch (Exception sql){
						log.error("Unable to insert acasatua", sql.getCause());
					}
					if (r==1){
						res = "{\"success\":true}";
						log.info("Insert new acasatua");
					} else {
						res = "{\"success\":false, \"msg\":\"noInsertSql\"}";
						log.warn("Insert image but row update 0");
					}
				
			}
			acasatuaDao = null;
			paramDAO = null;
		return Response.ok(res,"text/html").build();
		
	}
	
	@DELETE
	@Path("deleteAcasatua/{id}")
	public Response deleteAcasatua(@PathParam("id") int id,@Context ServletContext cx,
			@Context HttpServletRequest req){
		String res = "{\"success\":false}";
		//acasatuaDao = SingletonLookup.getAcasatuaDAO(cx);
		boolean flag = false;
			String image = "";
				AcasatuaMin q = null;
				try{
					q = acasatuaDao.getSingleAcasatuaMin(id);
					if (q!=null){
						image = q.getImage();
						flag = true;
					} else
						log.warn("acasatua single is null");
					if (flag && acasatuaDao.deleteAcasatua(id)==1){
						res = "{\"success\":true}";
						flag = true;
					} else 
						flag = false;
				
			if (flag){
				paramDAO = SingletonLookup.getParamDAO(cx);
				String wcp = paramDAO.getParam(getWCP()).getValue();
				String path = wcp +"/"+paramDAO.getParam(getAcasaimagepath()).getValue();
				File f = new File(path+"/"+image);
				if (f.delete())
					log.info("Delete image from acasatua");
				path =  wcp +"/"+paramDAO.getParam(getAcasathumbimagepath()).getValue();
				File f2 = new File(path+"/"+image);
				if (f2.delete())
					log.info("Delete image from acasatuathumb");
				paramDAO = null;
			}
				} catch (Exception sql){
					log.error("Error in delete acasatua", sql.getCause());
				}
			acasatuaDao = null;
		return Response.ok(res,"application/json").build();
	}
	
	@PUT
	@Path("updateAcasatua/{id}")
	@Consumes("application/x-www-form-urlencoded")
	public Response updateAcasatua(@Context ServletContext cx,
									@PathParam("id") int id,@FormParam("approvato") boolean approvato,
									@Context HttpServletRequest req){
		String res = "{\"success\":false}";
		int r = 0;
		//acasatuaDao = SingletonLookup.getAcasatuaDAO(cx);
				try{
					r = acasatuaDao.approvaAcasatua(id, approvato);
				} catch (Exception sql){
					log.error("Error in set approvato acasatua", sql.getCause());
				}
				if (r == 1){
					res = "{\"success\":true}";
					log.warn("in set approvato acasatua row 0");
				} 
			acasatuaDao = null;
		return Response.ok(res,"application/json").build();
	}
	
	
//	 * Articoli
//	 * 
//	 * 
//	 
//	@POST
//	@Path("dammiArticolo")
//	@Consumes("application/x-www-form-urlencoded; charset=UTF-8")
//	public Response dammiArticolo(@FormParam("idQuadro") int id,
//			@FormParam("action") String action){
//		
//		ApplicationContext appCx = Utils.Singleton.getDalBean();
//		String res = "";
//		if (action.equals("mod")){
//			
//		}
//		
//		return Response.ok(res,"text/html").build();
//	}
	
	private IMotivoDAO motivoDAO = null;
	
	
	 * 
	 * Manca il delete motivo
	 
	@POST
	@Path("caricaMotivo")
	@Consumes("multipart/form-data")
	public Response addMotivo(
			@Context HttpServletRequest req,
			@Context ServletContext cx){
		String res = "{\"success\":false, \"msg\":\"\"}";
		int r = 0;
		boolean okUpload = true;
		
			Motivo mo = new Motivo();
			Map<String,String> m = new HashMap<String,String>();
			List<String> mNameFile = new ArrayList<String>();
			String path = "";
			try {
				
				int size = 4;
				
				 Non più valido 
				String path = cx.getRealPath("/motivi");
				
				paramDAO = SingletonLookup.getParamDAO(cx);
				
				path = paramDAO.getParam(getWCP()).getValue() + getBUpload() + "motivi";
				
				MultipartRequest mpr = new MultipartRequest(req,path,size * 1024 * 1024);	
				 
				@SuppressWarnings("unchecked")
				Enumeration<String> fileParam = mpr.getFileNames();
				
								
				while (fileParam.hasMoreElements()){
					String name = fileParam.nextElement();
					name = mpr.getFilesystemName(name);
					
					mNameFile.add(name);
					
				}
				
				
			} catch (IOException io){
				log.warn("Errore io file upload");
				io.printStackTrace();
				okUpload = false;
				res = "{\"success\":false, \"msg\":\"noUpload\"}";
			} catch (Exception e){
				log.warn("Errore io file upload. path");
				e.printStackTrace();
				okUpload = false;
				res = "{\"success\":false, \"msg\":\"noUpload\"}";
			}
			
			if (okUpload){
			
				//motivoDAO = SingletonLookup.getMotivoDAO(cx);
					Motivo mot = new Motivo();
					if (mNameFile.size()==1)
						mot.setImage(mNameFile.get(0));
					//Update db
					try {
						r = motivoDAO.insertMotivo(mot);
					} catch (Exception e) {
						log.warn("noInsertSql motivo :"+e.getMessage());
						e.printStackTrace();
					}
														
					if (r == 1){
						account = SingletonLookup.getAccountDAO(cx);
						String httpPathImage = "http://"+req.getServerName()+getBUpload()+"motivi/"+mNameFile.get(0);
						res = "{\"success\":true,\"file\":\""+httpPathImage+"\"}";
						log.info("Insert new motiv");
					} else {
						log.warn("noInsertSql motivo");
						res = "{\"success\":false,\"msg\":\"noInsertSql\"}";
					}
			}
			account = null;
			motivoDAO = null;
			paramDAO = null;
		return Response.ok(res,"text/html").build();
		
	}
	
	
	
	@POST
	@Consumes("application/x-www-form-urlencoded; charset=UTF-8")
	@Path("caricaArticolo")
	public Response insertNovita(@FormParam("titoloAr") String titolo,
			@FormParam("htmlArticolo") String novita,
			@FormParam("entity") String tagname,

			
			 * cmIntR interpreta se true i codici oggetto inseriti
			 * lato editor e, se contenuti in param, salva il relativo value
			 * 
			 
			@FormParam("cmIntR") String cmIntR,
			@Context ServletContext cx){
		if (cmIntR != null && cmIntR.equals("on")){
			HtmlContent content = new HtmlContent(novita);	
			//HtmlInterpreter htmlIN = SingletonLookup.getHtmlInterpreterDAO(cx);
			try {
				content.htmlArticleInterpreter(content, htmlIN);
			} catch (JSONException e) {
				log.error(e);
			}
			novita = content.toString();
			htmlIN = null;
		}
		

		String res = "{\"success\":";
		// tmp novitaDAO = SingletonLookup.getNovitaDAO(cx);
		News n = new News();
		n.setBody(novita);
		n.setTitle(titolo);
		n.setTagname(tagname);
		try {
			if (novitaDAO.insertNovita(n) == 1)
				res += "true}";
			else
				res += "false}";
		} catch (Exception sq){
			log.error("Error in insert novita", sq.getCause());
			res += "false}";
		}
		novitaDAO = null;
		return Response.ok(res,"application/json").build();
	}
	
	@GET
	@Produces("application/json")
	@Path("dammiarticolowithoutbody/{idArticolo}")
	public News getOtherArticoloData(@PathParam("idArticolo") Integer idArticolo,
									@Context ServletContext cx,
									@Context HttpServletRequest req){
		News res = new News();
		// tmp novitaDAO = SingletonLookup.getNovitaDAO(cx);
		try {
			res = novitaDAO.getSingleNovitaWithOutBody(idArticolo);
		} catch (Exception e) {
			log.error("Unable to select getSingleNovitaWithOutBody",e.getCause());
			e.printStackTrace();
			res = null;
		}
		novitaDAO = null;
		return res;
	}
	
	@GET
	@Path("dammibodyarticoloy/{idArticolo}")
	public Response getBodyArticle(@PathParam("idArticolo") Integer idArticolo,
			@Context ServletContext cx,
			@Context HttpServletRequest req){
		String body = "";
		// tmpnovitaDAO = SingletonLookup.getNovitaDAO(cx);
		try {
			body = novitaDAO.getBodyArticolo(idArticolo);
			HtmlContent content = new HtmlContent(body);	
			//HtmlInterpreter htmlIN = SingletonLookup.getHtmlInterpreterDAO(cx);
			//content.htmlArticleInterpreterReverse(content, htmlIN);
			body = content.toString();
			//htmlIN = null;
		} catch (Exception e) {
			log.error("Unable to select getBodyArticle",e.getCause());
			e.printStackTrace();
			body = null;
		}
		novitaDAO = null;
		return Response.ok(body,"text/html").build();
	}
	
	@PUT
	@Path("caricaArticolo")
	@Consumes("application/x-www-form-urlencoded; charset=UTF-8")
	public Response updateNovita(@FormParam("titoloAr") String titolo,
			@FormParam("htmlArticolo") String novita,
			@FormParam("entity") String tagname,
			@FormParam("idArticolo") Integer idArticolo,

			@FormParam("cmIntR") String cmIntR,
			@Context ServletContext cx){
		if (cmIntR != null && cmIntR.equals("on")){
			HtmlContent content = new HtmlContent(novita);	
			//HtmlInterpreter htmlIN = SingletonLookup.getHtmlInterpreterDAO(cx);
			try {
				content.htmlArticleInterpreter(content, htmlIN);
			} catch (JSONException e) {
				log.error(e);
			}
			novita = content.toString();
			htmlIN = null;
			
		}

		String res = "{\"success\":";
		if (idArticolo > 0){
			// tmpnovitaDAO = SingletonLookup.getNovitaDAO(cx);
			try {
				if (novitaDAO.updateNovita(novita, titolo, idArticolo, tagname) == 1)
					res += "true}";
				else 
					res += "false}";
			} catch (Exception sq){
				log.error("Unable to update Article", sq.getCause());
				res += "false, \"msg\":\""+sq.getMessage()+"\"}";
			}
		} else {
			res += "false, \"msg\":\"No id article\"}";
		}
		novitaDAO = null;
		return Response.ok(res,"application/json").build();
	}
	
	@DELETE
	@Path("deletearticolo/{idWork}")
	@Produces("application/json")
	public Response deleteArticolo(@Context ServletContext cx,
			@Context HttpServletRequest req, 
			@DefaultValue("-1") @PathParam("idWork") int idWork){
		String resp = "{\"success\":";
		// tmpnovitaDAO = SingletonLookup.getNovitaDAO(cx);
		try {
			if (idWork>0)
				if (novitaDAO.deleteArticolo(idWork))
					resp += "true}";
				else 
					resp += "false}";
				
		} catch (Exception e){
			resp += "false}";
			log.error("Fail in delete articolo", e.getCause());
		}
		novitaDAO = null;	
		return Response.ok(resp,"application/json").build();
	}
	
	
	@PUT
	@Path("articolosetarchivio/{idWork}")
	@Consumes("application/x-www-form-urlencoded; charset=UTF-8")
	@Produces("application/json")
	public Response setArchivio(@Context ServletContext cx,
			@Context HttpServletRequest req,
			@DefaultValue("-1") @PathParam("idWork") int idWork,
			@FormParam("archivio") boolean archivio,
			@FormParam("tagname") String tagname){
		String resp = "{\"success\":";
		// tmp novitaDAO =SingletonLookup.getNovitaDAO(cx);
		try {
			if (idWork>0)
				if (novitaDAO.setArchivio(idWork, archivio, tagname))
					resp += "true}";
				else 
					resp += "false}";
				
		} catch (Exception e){
			resp += "false}";
			log.error("Error in set archivo", e.getCause());
		}
		novitaDAO = null;	
		return Response.ok(resp,"application/json").build();
	}
	
	
}
*/