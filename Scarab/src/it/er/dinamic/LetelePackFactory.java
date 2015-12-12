package it.er.dinamic;


import it.er.account.AccountDAO;
import it.er.presentation.webresource.BaseLayerContent;
import it.er.util.CustomException;

import java.lang.ref.Reference;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.LinkedBlockingDeque;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;

import com.sun.jndi.url.corbaname.corbanameURLContextFactory;
import com.sun.jndi.url.dns.dnsURLContext;







/** 1 fase - dev/ - 
 * @Author Rascazzo Emilio/Io
 * Semplificazione bean LetelediEry
 * Ottimizzazione RAM
 * emilio.rascazzo@gmail.com
 * 12-02-13 abbozzo di find
 * inTest : "instance" --- if !--- .
 */
public class LetelePackFactory extends Thread implements CreaMovEntity,Deque<RepresentedHappyService>{
	
	/*
	 * Iniettare jdcbTemplate nei dao
	 */
	private JdbcTemplate jdbcTemplate = null;
	
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate){
		this.jdbcTemplate = jdbcTemplate;
	}
	
	private JdbcTemplate getJdbcTemplate() {
		return this.jdbcTemplate;
	}
	
	private static int numInstancing = 0;
	
	private static int numInstanced = 0;
	
	private static String instance = null;
	
	private static final String representShift = "goShift";
	
	private static String getRepresentshift() {
		return representShift;
	}

		
	/*
	public QuadroDAO quadroDAO = null;
	
	public AcasatuaDAO acasatuaDAO = null;
	
	public CavallettoDAO cavallettoDAO = null;
	
	public CitazioneDAO citazioneDAO = null;
	
	public CommentoDAO commentoDAO = null;
	
	public HeaderImgDAO headerImgDAO = null;
	
	public MotivoDAO motivoDAO = null;
	
	public NovitaDAO novitaDAO = null;
	
	public ParamDAO paramDAO = null;
	
	public StatDAO statDAO = null;
	
	public AccountDAO account = null;
	*/
	
	
	private Deque<RepresentedHappyService> daoInPersistence = new LinkedBlockingDeque<RepresentedHappyService>();
	
	
	public void close(){
		log.info("Destroy LetelePackFactory bean");
	}
	
	public void start(){
		log.info("Init letelePackFactory bean");
	}
	
	private static Logger log = LogManager.getLogger(LetelePackFactory.class);


	public static String getInstance() {
		return instance;
	}
	
	private static void incrementInstancing(){
		numInstancing ++;
	}
	
	private static void incrementInstanced(){
		numInstanced ++;
	}
	
	private static void decrementInstancing(){
		numInstancing --;
	}
	
	private static void decrementInstanced(){
		numInstanced --;
	}

	private static void syncDecrementInstance(){
		numInstancing --;
		numInstanced --;
	}
	
	private static void syncIncrementInstance(){
		numInstanced ++;
		numInstancing ++;
	}
	
	private static void syncDifferenceInstance(int diff){
		numInstanced += diff;
		numInstancing += diff;
	}
	
	private String[] inRealSrvDao = null;
	
	
	/* 
	 * proprietà duplice/ per ora cannot getted 
	 */
	private List<RepresentedHappyService> backMirror = null;
	
	
	private void clearMirror(){
		inRealSrvDao = null;
	}
	
	@Override
	public String getClassType(String daoName) throws CustomException{
		//return type!=null?type.getName().substring(0, 1).toLowerCase():new String(representShift);
		return null; /*provv-*/
	}
	
	@Override
	public String getDaoName(Class<?> type) throws CustomException{
		return type!=null?type.getName().substring(0, 1).toLowerCase():new String(representShift);
	}
	
	@Override
	public void createEntity(Class<?> type) throws CustomException{
		LetelePackFactory.incrementInstancing(); /* attivo in anticipo*/
		String daoName = this.getDaoName(type);
		try {
			this.manageNewOccorrence(type, daoName); 
		} catch (SecurityException e) {
			log.error("Cannot creaty Entity");
			throw new CustomException(e,e.getMessage());
		} catch (InstantiationException e) {
			log.error("Cannot creaty Entity");
			throw new CustomException(e,e.getMessage());
		} catch (IllegalAccessException e) {
			log.error("Cannot creaty Entity");
			throw new CustomException(e,e.getMessage());
		} catch (IllegalArgumentException e) {
			log.error("Cannot creaty Entity");
			throw new CustomException(e,e.getMessage());
		} catch (InvocationTargetException e) {
			log.error("Cannot creaty Entity");
			throw new CustomException(e,e.getMessage());
		} catch (CustomException e) {
			log.error("Cannot creaty Entity #"+e.getMessage(),e);
		}
	}
	
	private Constructor<?> getDefaultConstruct(Class<?> type) throws SecurityException,CustomException {
		Constructor<?>[] constrs= type.getConstructors();
		if (constrs!=null && constrs.length>0){
			for (Constructor<?> c: constrs){
				if (!c.isVarArgs()){
					return c;
				}
			}
			return null;
		} else 
			throw new CustomException("No contructors in class type for factory!");
	}
	
	private Method getInjectionJdbcMethod(Class<?> type) throws CustomException{
		try {
			return type.getMethod("setJdbcTemplate", JdbcTemplate.class);
		} catch (NoSuchMethodException e) {
			throw new CustomException("No jdbcMethod in class type for factory!");
		} catch (SecurityException e) {
			throw new CustomException("No jdbcMethod in class type for factory!");
		}
	}
	
	private Object injectDao(Constructor<?> c,Method m) throws CustomException{
		Object o = null;
		try {
			o = c.newInstance();
			m.invoke(o, this.getJdbcTemplate());
		} catch (InstantiationException e) {
			throw new CustomException(e.getCause(),"No injection for factory!");
		} catch (IllegalAccessException e) {
			throw new CustomException(e.getCause(),"No injection for factory!");
		} catch (IllegalArgumentException e) {
			throw new CustomException(e.getCause(),"No injection for factory!");
		} catch (InvocationTargetException e) {
			throw new CustomException(e.getCause(),"No injection for factory!");
		}
		
		return o;
	}

	private void manageNewOccorrence(Class<?> type, String daoName) throws SecurityException, CustomException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		if (!this.existEntity(type)){
			LetelePackFactory.instance = daoName;
			Constructor<?> c = this.getDefaultConstruct(type);
			Method m = this.getInjectionJdbcMethod(type); 
			
			RepresentedHappyService reHappySrv = null;
			if (c!=null){
				synchronized (this.daoInPersistence){
					/*
					 * Inietto jdbc
					 */
					try{
						reHappySrv = (RepresentedHappyService) this.injectDao(c, m);
						if (type.isInstance(reHappySrv)){
							LetelePackFactory.incrementInstanced();
							this.daoInPersistence.push(reHappySrv);
						} else {
							LetelePackFactory.decrementInstancing();
						}
						this.daoInPersistence.notify();
					} catch (CustomException e) {
						LetelePackFactory.decrementInstancing();
						this.daoInPersistence.notify();
						throw new CustomException(e);
					}
				}
			} else {
				LetelePackFactory.decrementInstancing();
				throw new CustomException("No default contructors in class type for factory!");
			}
		} else {
			LetelePackFactory.decrementInstancing();
		}
	}

	@Override
	public void destroy() {
			this.daoInPersistence = null;
			this.daoInPersistence = new LinkedBlockingDeque<RepresentedHappyService>();
	}

	
	public boolean existEntity(Class<?> type) throws CustomException {
		Iterator<RepresentedHappyService> i = this.daoInPersistence.iterator();
		while (i.hasNext()){
			if (type.isInstance(i.next()))  
				return true;
		}
		return false;
	} 

	
	/*
	@SuppressWarnings("serial")
	@Override
	
	public Object retriveEntity(Class<?> type) throws CustomException{
		/*
		if (!this.existEntity(type)) throw new CustomException("RepresentedHappySrv", 
				new CustomException("NullPointerException"));
		*/
		/*
		if (!this.existEntity(type)) return (Object) new Boolean(false);
		synchronized (this.daoInPersistence) {
			this.createMirror();
			for (RepresentedHappyService r : this.daoInPersistence){
				for (int i=0;i<this.inRealSrvDao.length;i++)
					//if (r.getClass().getName().equals(type.getClass().getName()) && 
					//		type.getClass().getName().equals(this.getDaoName(type)))
					if (type.isInstance(r) ){//&& type.getClass().getName().equalsIgnoreCase(this.getDaoName(type))){
						this.daoInPersistence.notify();
						return (Object) r;
					}
			}
				
			
		}
		return (Object) new Boolean(false);
	}*/
	
	@SuppressWarnings("serial")
	@Override
	public Object retriveEntity(String daoName) throws CustomException {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
	/*
	 * ritorna false se non c'è in corso una nuova istanza
	 * altrimenti true
	 */
	@Override
	public boolean aroundDaoPush() throws CustomException{
		if (numInstanced==this.daoInPersistence.size() && numInstancing==numInstanced) /* caso di riposo */
			return false;
		else if (numInstanced==this.daoInPersistence.size() && numInstanced<numInstancing) /* non c'è l'istanza */
			return false;
		else 
			return true;
	}
	
	/*
	 * poll di struttura dao in mirror and reOffer (sync)
	 * return : null se realmirror è diverso da  (lenght==numInstanced?lenght:0)
	 * 			OR backMirror è capiente
	 */
	public Integer createMirror(List<RepresentedHappyService> backmirror) throws CustomException{
		if (backmirror!=null && backmirror.size()==0)
			synchronized (this.daoInPersistence){
				this.inRealSrvDao[numInstanced] = new String();
				this.clearMirror();
				for (int i=0;i<this.daoInPersistence.size();i++){
					/*variazione*/
					RepresentedHappyService source = null;
					if (i==0)
						source = this.daoInPersistence.peekFirst();
					else 
						source = this.daoInPersistence.peek();
					inRealSrvDao[i] = new String(this.getDaoName((source==null?null:source).getClass()));
					//this.daoInPersistence.offerFirst(source);
				}
			}
		return this.inRealSrvDao.length==numInstanced?new Integer(this.inRealSrvDao.length):new Integer(0);
	}
	
	
	/*
	 * poll di struttura dao in mirror and reOffer (sync)
	 * return : null se realmirror è diverso da  (lenght==numInstanced?lenght:0)
	 */
	public Integer createMirror() throws CustomException {
			synchronized (this.daoInPersistence){
				this.clearMirror();
				this.inRealSrvDao = new String[numInstanced];
				for (int i=0;i<this.daoInPersistence.size();i++){
					/*variazione*/
					RepresentedHappyService source = null;
					if (i==0)
						source = this.daoInPersistence.peekFirst();
					else 
						source = this.daoInPersistence.peek();
					inRealSrvDao[i] = new String(this.getDaoName((source==null?null:source).getClass()));
					//this.daoInPersistence.offerFirst(source);
				}
			}
		return this.inRealSrvDao.length==numInstanced?new Integer(this.inRealSrvDao.length):new Integer(0);
	}
	
	/*
	 * poll di struttura dao in mirror (
	 * return : null se realmirror è diverso da  (lenght==numInstanced?lenght:0)
	 */
	public Integer Mirror() throws CustomException{
			synchronized (this.daoInPersistence){
				this.clearMirror();
				this.inRealSrvDao = new String[numInstanced];
				for (int i=0;i<this.daoInPersistence.size();i++){
					/*variazione*/
					RepresentedHappyService source = null;
					if (i==0)
						source = this.daoInPersistence.peekFirst();
					else 
						source = this.daoInPersistence.peek();
					inRealSrvDao[i] = new String(this.getDaoName((source==null?null:source).getClass()));
					//this.daoInPersistence.offerFirst(source);
				}
			}
		return this.inRealSrvDao.length==numInstanced?new Integer(this.inRealSrvDao.length):new Integer(0);
	}
	
	public void run(RepresentedHappyService srv){
		while (!isInterrupted()){
			try {
				//if (this.aroundDaoPush())
					synchronized (this.daoInPersistence) {
						this.daoInPersistence.wait();
					}
			} catch (InterruptedException e) {
				log.warn("Error in Run", e.getCause());
//			} catch (CustomException c) {
//				log.warn("Error in Run", c.getCause());
			} 
		}
	}
	
	@Override
	public int size() {
		int size = 0;
		if (numInstanced == numInstancing)
			return numInstanced;
		else {
			synchronized (this.daoInPersistence) {
				size =  numInstanced;
				this.daoInPersistence.notify();
				if (log.isDebugEnabled())
					log.info("Size #"+numInstancing+"/"+numInstanced);
			}
			return size;
		}
	}

	@Override
	/*
	 * (non-Javadoc)
	 * @see java.util.Deque#removeFirst()
	 * peekFirst
	 */
	public RepresentedHappyService removeFirst() throws NoSuchElementException{
		RepresentedHappyService r = null;
		synchronized (this.daoInPersistence) {
			try {
				this.createMirror((this.backMirror==null?new ArrayList<RepresentedHappyService>():null));
			} catch (CustomException c){
				throw new NoSuchElementException(c.getMessage());
			}
			if (backMirror.size()==0){
				throw new NoSuchElementException("Empty deque");
			}
			r = this.daoInPersistence.peekFirst();
			if (r!=null){
				LetelePackFactory.syncDecrementInstance();
				if (log.isDebugEnabled())
					log.info("Remove #"+r.getClass().getName());
			}
			this.daoInPersistence.notify();
		}
		return r;
	}

	
	
	

	@Override
	/*
	 * (non-Javadoc)
	 * @see java.util.Collection#addAll(java.util.Collection)
	 * OfferLast
	 */
	public boolean addAll(Collection<? extends RepresentedHappyService> arg0) throws UnsupportedOperationException,
												ClassCastException,NullPointerException,IllegalArgumentException,
												IllegalStateException{
		boolean r = false;
		/*if (arg0 instanceof)*/
		synchronized (this.daoInPersistence) {
			Iterator<?> i = arg0.iterator();
			while (i.hasNext()){
				Object inCollection = i.next();
				if (inCollection==null)
					continue;
				if (!(inCollection instanceof Object))
					continue;
				if (!(inCollection instanceof it.er.basic.Basic)){
					throw new ClassCastException("ClassDefOutBound");
				}
				try {
					this.daoInPersistence.offerLast((RepresentedHappyService) inCollection);
					r = true;
				} catch (ClassCastException e) {
					log.info("Unsupported Operation",e.getCause());
					throw new UnsupportedOperationException(e.getCause());
				}
				
			}
			if (r)
				LetelePackFactory.syncDifferenceInstance(arg0.size());
			this.daoInPersistence.notify();
		}
		return r;
	}

	@Override
	/*
	 * (non-Javadoc)
	 * @see java.util.Collection#clear()
	 * destroy
	 */
	public void clear() throws UnsupportedOperationException{
		int s = this.daoInPersistence.size(); 
		synchronized (this.daoInPersistence) {
			try {
				this.createMirror();
			} catch (CustomException c){
				throw new UnsupportedOperationException(c);
			}
			this.destroy();
			LetelePackFactory.syncDifferenceInstance(-1*s);
			this.daoInPersistence.notifyAll();
		}
		
	}

	@Override
	public boolean containsAll(Collection<?> arg0) throws ClassCastException,NullPointerException{
		return this.containsAll(arg0);
	}

	@Override
	public boolean isEmpty() {
		return false;
	}

	@Override
	public boolean removeAll(Collection<?> arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean retainAll(Collection<?> arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Object[] toArray() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T[] toArray(T[] arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean add(RepresentedHappyService e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void addFirst(RepresentedHappyService e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addLast(RepresentedHappyService e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean contains(Object o) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Iterator<RepresentedHappyService> descendingIterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RepresentedHappyService element() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RepresentedHappyService getFirst() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RepresentedHappyService getLast() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterator<RepresentedHappyService> iterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean offer(RepresentedHappyService e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean offerFirst(RepresentedHappyService e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean offerLast(RepresentedHappyService e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public RepresentedHappyService peek() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RepresentedHappyService peekFirst() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RepresentedHappyService peekLast() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RepresentedHappyService poll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RepresentedHappyService pollFirst() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RepresentedHappyService pollLast() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RepresentedHappyService pop() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void push(RepresentedHappyService e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public RepresentedHappyService remove() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean remove(Object o) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeFirstOccurrence(Object o) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public RepresentedHappyService removeLast() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean removeLastOccurrence(Object o) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Object retriveEntity() throws CustomException {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * getRepresentedSrvEntity by CreaMovEntity
	 *
	@Override
	public RepresentedHappyService getRepresentedHappySrvEntity(CreaMovEntity cme,RepresentedHappyService reprHappySrv,
													Class<?> type,Logger log){
		//boolean r = false;
		//if (reprHappySrv==null)
		try{
			Object feed = cme.retriveEntity(type);
			if (feed instanceof Boolean && !(Boolean)feed){
				cme.createEntity(type);
				reprHappySrv = (RepresentedHappyService) cme.retriveEntity(type);
			} else
				reprHappySrv = (RepresentedHappyService) feed;
		} catch (CustomException e) {
			log.warn(LayerBasic.getNoretrivedao(),new CustomException(LayerBasic.getNoretrivedao()));
			reprHappySrv = null;
			//r = false;
		}
		return reprHappySrv;
	}
	*/

	
	
	
}
