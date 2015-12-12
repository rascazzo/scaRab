package it.er.dinamic;

import it.er.util.CustomException;

import org.apache.log4j.Logger;


public interface CreaMovEntity {

	public void createEntity(Class<?> type) throws CustomException;
	
	public void destroy();
	
	//public Object retriveEntity(Class<?> type) throws CustomException;
	
	public Object retriveEntity(String daoName)  throws CustomException;
	
	public String getClassType(String daoName) throws CustomException;
	
	public Object retriveEntity() throws CustomException;
	
	public boolean existEntity(Class<?> type) throws CustomException;
	
	public String getDaoName(Class<?> type) throws CustomException;
	
	public boolean aroundDaoPush() throws CustomException;
	
	//public RepresentedHappyService getRepresentedHappySrvEntity(CreaMovEntity cme,RepresentedHappyService reprHappySrv,
	//		Class<?> type,Logger log);
}
