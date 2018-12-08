package com.gestion.credits.dao;

import java.util.List;

import com.gestion.credits.beans.Dashboard;
import com.gestion.credits.entities.ChildRubric;
import com.gestion.credits.entities.Professor;
import com.gestion.credits.entities.Projects;
import com.gestion.credits.entities.Rubric;
import com.gestion.credits.entities.Team;

public interface AdminDaoInterface {
	
	public List<Object> getDashBoard(int year); 
	public void saveProf(Professor p);
	public List<Professor> getProfs();
	public Professor getProf(String name);
	public void saveTeam(Team t);
	public List<Object> getRubrics(int project_id);
	public List<Team> getTeams();
	public Projects saveProject(Projects p);
	public Team getTeam(int team_id);
	public Rubric saveRubric(Rubric r);
	public ChildRubric saveChild(ChildRubric cr);
	public List<Projects> getProjects();
	public Projects getProject(int id);
	public List<Rubric> getProject_rubrics(int project_id);
	public Rubric getRubric(int rubric_id);
	public List<Object> getProject_Log(int id_project);
}
