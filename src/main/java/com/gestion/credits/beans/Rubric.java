package com.gestion.credits.beans;

import java.util.ArrayList;
import java.util.List;

public class Rubric {

	private String label;
	private List<Childrubric> childs = new ArrayList<Childrubric>();
	private double total=0.0;
	
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public List<Childrubric> getChilds() {
		return childs;
	}
	public void setChilds(List<Childrubric> childs) {
		this.childs = childs;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	
	public void updateTotal() {
		total = 0.0;
		for(Childrubric cr:childs) total = total + cr.getAmount();
	}
	
}
