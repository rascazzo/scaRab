package it.er.presentation.admin.object.fecomponent;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="selectItem")
public class SelectItem {
	
	private static final String defaultLabel = "--";
	
	private static final String defaultValue = null;

	private String itemLabel;
	
	private String itemValue;
	
	private boolean selected = false;
	
	public SelectItem(String itemLabel,String itemValue){
		this.itemLabel = itemLabel;
		this.itemValue = itemValue;
	}
	
	public SelectItem(){}

	@XmlElement
	public String getItemLabel() {
		return itemLabel;
	}

	public void setItemLabel(String itemLabel) {
		this.itemLabel = itemLabel;
	}

	@XmlElement
	public String getItemValue() {
		return itemValue;
	}

	@XmlElement
	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public void setItemValue(String itemValue) {
		this.itemValue = itemValue;
	}

	public static String getDefaultlabel() {
		return defaultLabel;
	}

	public static String getDefaultvalue() {
		return defaultValue;
	}
	
	public static  void insertDefaultItem(List<SelectItem> l){
		if (l != null)
			l.add(0, new SelectItem(SelectItem.defaultLabel,SelectItem.defaultValue));
	}
}
