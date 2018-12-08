package com.gestion.credits.dao;

import java.util.List;

import com.gestion.credits.entities.ChildRubric;
import com.gestion.credits.entities.Contribution;
import com.gestion.credits.entities.Professor;
import com.gestion.credits.entities.Projects;
import com.gestion.credits.entities.Rubric;
import com.gestion.credits.entities.Team;

public interface ProfDaoInterface {

	public Professor getProf(String cin, String password);
	public List<Object> getDashboard(int prof_id);
	public List<Object> getRubrics(int project_id);
	public List<Object> getProject_Log(int id_project);
	public List<Team> getTeams(int prof_id);
	public List<Projects> getProjects(int prof_id);
	public List<Rubric> getProject_rubrics(int project_id);
	public List<ChildRubric> getChilds(int rubric_id);
	public ChildRubric getchild(int child);
	public void saveContribution(Contribution cnt);
	public Professor getProf(int prof_id);
	public void updatePrice(int child_id, double amount);
}
