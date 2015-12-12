package it.er.util;

import java.io.Serializable;

public class CustomException extends Exception implements Serializable{

	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 0;
	
	public CustomException(){
		super();
	}
	
	public CustomException(Throwable t){
		super(t);
	}
	
			
	public CustomException(String fromcustommessage){
		super(fromcustommessage);
	}
	
	
	
	public CustomException(Throwable th, String personalmessage){
		super(personalmessage,th);
	}
	
	public CustomException(Throwable th, String personalmessage, boolean enableSuppress, boolean writeableStack){
		super(personalmessage,th,enableSuppress,writeableStack);
	}
	
}
