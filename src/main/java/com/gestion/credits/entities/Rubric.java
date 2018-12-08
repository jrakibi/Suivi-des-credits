package com.gestion.credits.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Rubric implements Serializable {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(name="Label")
	private String label;
	
	@ManyToOne
	@JoinColumn(name="Project_id")
	private Projects project;
	
	@OneToMany(mappedBy="rubric")
	private List<ChildRubric> childs;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Projects getProject() {
		return project;
	}

	public void setProject(Projects project) {
		this.project = project;
	}

	public List<ChildRubric> getChilds() {
		return childs;
	}

	public void setChilds(List<ChildRubric> childs) {
		this.childs = childs;
	}
	
	

}
