package com.gestion.credits.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity
public class Team implements Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(name="Code")
	private String code;
	
	@Column(name="Name")
	private String name;
	
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name="Engagement", joinColumns=@JoinColumn(name="team_id"), inverseJoinColumns=@JoinColumn(name="prof_id") )
	private List<Professor> profs;
	
	@OneToMany(mappedBy="team")
	private List<Projects> projects;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Professor> getProfs() {
		return profs;
	}

	public void setProfs(List<Professor> profs) {
		this.profs = profs;
	}

	public List<Projects> getProjects() {
		return projects;
	}

	public void setProjects(List<Projects> projects) {
		this.projects = projects;
	}
	
	
}
