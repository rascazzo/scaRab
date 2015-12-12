package it.er.dinamic;


public interface DinamicLink {
	public String resolvLink(String servername,String plus, String realname);
	public String resolvLink(String servername,String plus, String lang,String realname);
	public String resolvLeftLink(String servername,String plus);
	public String resolvLeftLink(String plus);
	public String resolvRightLink(String realname);
}
