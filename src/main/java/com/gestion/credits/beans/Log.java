package com.gestion.credits.beans;

import java.sql.Date;

public class Log {
	
	public String professor;
	public String rubric;
	public String childRubric;
	public double contribution;
	public Date date_contribution;
	
	
	public String getProfessor() {
		return professor;
	}
	public void setProfessor(String professor) {
		this.professor = professor;
	}
	public String getRubric() {
		return rubric;
	}
	public void setRubric(String rubric) {
		this.rubric = rubric;
	}
	public String getChildRubric() {
		return childRubric;
	}
	public void setChildRubric(String childRubric) {
		this.childRubric = childRubric;
	}
	public double getContribution() {
		return contribution;
	}
	public void setContribution(double contribution) {
		this.contribution = contribution;
	}
	public Date getDate_contribution() {
		return date_contribution;
	}
	public void setDate_contribution(Date date_contribution) {
		this.date_contribution = date_contribution;
	}
	
	

}
