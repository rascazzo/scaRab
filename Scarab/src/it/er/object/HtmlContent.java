package it.er.object;

import it.er.generic.GenericTag;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

public class HtmlContent {
	
	private String body;
	private Vector<String> v_body = null;
	private static int bodylen;
	private StringBuffer buffer = null;
	
	public HtmlContent(){}
	
	public HtmlContent(String s){
		this.body = s;
		HtmlContent.bodylen = this.body.length();
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
		HtmlContent.bodylen = this.body.length();
	}

	public static int getBodylen() {
		return bodylen;
	}

	public static void setBodylen(int bodylen) {
		HtmlContent.bodylen = bodylen;
	}
	
	
	public Vector<String> getV_body() {
		return v_body;
	}

	public void setV_body(Vector<String> v_body) {
		this.v_body = v_body;
	}

	public void cleanBody(){
		this.v_body = null;
		this.body = null;
		this.buffer = null;
		HtmlContent.bodylen = 0;
	}
	
	public void reAssignBody(String s){
		this.cleanBody();
		this.setBody(s);
		HtmlContent.bodylen = this.body.length();
	}
	
	public int splitBody(String regex){
		this.v_body = new Vector<String>();
		String[] vs = this.body.split(regex);
		for (int i=0;i<vs.length;i++){
			this.v_body.add(i, vs[i]);
		}
		HtmlContent.bodylen = this.body.length();
		return this.v_body.size();
	}
	
	public int splitAndReplaceBody(String regex,String out){
		this.v_body = new Vector<String>();
		this.buffer = new StringBuffer();
		String[] vs = this.body.split(regex);
		for (int i=0;i<vs.length;i++){
			this.v_body.add(i, vs[i]);
			this.buffer.append(vs[i]);
			if (i<(vs.length-1))
				this.buffer.append(out);
		}
		HtmlContent.bodylen = this.body.length();
		return this.v_body.size();
	}
	
	private int splitAndReplaceBody(String regex, String out1, String out2){
		this.v_body = new Vector<String>();
		this.buffer = new StringBuffer();
		String[] vs = this.body.split(regex);
		int c = 0;
		for (int i=0;i<vs.length;i++){
			this.v_body.add(i, vs[i]);
			if (i>0 && !vs[i].matches(".*[<.*>]+"))
				this.buffer.append(out1);
			this.buffer.append(vs[i]);
			if (i>0 && !vs[i].matches(".*[<.*>]+"))
				this.buffer.append(out2);
			/*
				if (c%2==1 && (vs[i].length()>0 || i == vs.length-1) ){
					this.buffer.append(out1);
					c++;
				} else if (vs[i].length()>0 || i == vs.length-1){
					this.buffer.append(out2);
					c++;
				}
			*/
		}
		
		HtmlContent.bodylen = this.body.length();
		return this.v_body.size();
	}
	
	public String toString(){
		if (this.buffer!=null)
			return this.buffer.toString();
		else if (this.v_body!=null){
			this.buffer = new StringBuffer();
			for (int i=0;i<this.v_body.size();i++)
				this.buffer.append(this.v_body.get(i));
			return this.buffer.toString();	
		} else if (this.body!=null)
			return this.body.toString();
		else
			return toString();
		
	}
	
	
}
