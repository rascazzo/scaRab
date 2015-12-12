package it.er.object;

import javax.xml.bind.annotation.XmlElement;

public class URLSiteMap {

		private String loc;
		private String lastmod;
		private String changefreq;
		private String priority;
		
		public URLSiteMap(){}
		
		public URLSiteMap(String loc,String lastmod,String changefreq,String priority){
			this.changefreq = changefreq;
			this.lastmod = lastmod;
			this.loc = loc;
			this.priority = priority;
		}
		
		@XmlElement
		public String getLoc() {
			return loc;
		}
		public void setLoc(String loc) {
			this.loc = loc;
		}
		@XmlElement
		public String getLastmod() {
			return lastmod;
		}
		
		public void setLastmod(String lastmod) {
			this.lastmod = lastmod;
		}
		@XmlElement
		public String getChangefreq() {
			return changefreq;
		}
		public void setChangefreq(String changefreq) {
			this.changefreq = changefreq;
		}
		@XmlElement
		public String getPriority() {
			return priority;
		}
		public void setPriority(String priority) {
			this.priority = priority;
		}
		
		
}
