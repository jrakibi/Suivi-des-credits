package com.gestion.credits.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

@Entity
public class Contribution implements Serializable {

	@EmbeddedId
	private Contribution_Pk id;
	
	@ManyToOne
	@MapsId("prof_id")
	private Professor prof;
	
	@ManyToOne
	@MapsId("ChildRubric_id")
	private ChildRubric childRubric;
	
	@Column(name="Amount")
	private double amount;
	
	private Date date;

	public Contribution_Pk getId() {
		return id;
	}

	public void setId(Contribution_Pk id) {
		this.id = id;
	}

	public Professor getProf() {
		return prof;
	}

	public void setProf(Professor prof) {
		this.prof = prof;
	}

	public ChildRubric getChildRubric() {
		return childRubric;
	}

	public void setChildRubric(ChildRubric childRubric) {
		this.childRubric = childRubric;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	
}
