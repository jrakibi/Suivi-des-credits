package com.gestion.credits.metier;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.gestion.credits.beans.ApplicationContextProvider;
import com.gestion.credits.beans.Childrubric;
import com.gestion.credits.beans.Dashboard;
import com.gestion.credits.beans.Log;
import com.gestion.credits.beans.Rubric;
import com.gestion.credits.dao.ProfDaoInterface;
import com.gestion.credits.entities.ChildRubric;
import com.gestion.credits.entities.Contribution;
import com.gestion.credits.entities.Contribution_Pk;
import com.gestion.credits.entities.Professor;
import com.gestion.credits.entities.Projects;
import com.gestion.credits.entities.Team;

@Transactional
public class ProfMetier {
	
	@Autowired
	private ApplicationContextProvider provider;
	
	private ProfDaoInterface dao;
	
	public ProfDaoInterface getDao() {
		return dao;
	}

	public void setDao(ProfDaoInterface dao) {
		this.dao = dao;
	}

	public Professor getProf(String cin, String password) {
		return dao.getProf(cin, password);
	}
	
	public List<Dashboard> getDashboard(int prof_id){
		List<Object> objects = dao.getDashboard(prof_id);
		if(objects == null) return null;
		
		List<Dashboard> dsh = new ArrayList<Dashboard>();
		Dashboard dashboard;
		Iterator itr = objects.iterator();
		
		while(itr.hasNext()) {
			Object[] obj = (Object[]) itr.next();
			dashboard = (Dashboard) provider.getApplicationContext().getBean("dashboard", Dashboard.class);
			dashboard.setTeam(String.valueOf(obj[0]));
			dashboard.setProject(String.valueOf(obj[1]));
			dashboard.setYear(String.valueOf(obj[2]));
			dashboard.setAmount(Double.valueOf(String.valueOf(obj[3])));
			dashboard.setId_projet(Integer.valueOf(String.valueOf(obj[4])));
			dsh.add(dashboard);
		}
		return dsh;
	}
	
	public List<Rubric> getRubrics(int project_id){
		
		List<Object> objects = dao.getRubrics(project_id);
		if(objects == null) return null;
		Iterator itr = objects.iterator();
		List<Rubric> rubrics = new ArrayList<Rubric>();
		Rubric r;
		Childrubric cr;
		
		while(itr.hasNext()) {
			Object[] obj = (Object[]) itr.next();
			r = this.getRubric(String.valueOf(obj[0]), rubrics);
			if(r == null) {
				r = (Rubric) provider.getApplicationContext().getBean("rubric", Rubric.class);
				r.setLabel(String.valueOf(obj[0]));
				cr = (Childrubric) provider.getApplicationContext().getBean("childrubric", Childrubric.class);
				cr.setLabel(String.valueOf(obj[1]));
				cr.setAmount(Double.valueOf(String.valueOf(obj[2])));
				r.getChilds().add(cr);
				r.updateTotal();
				rubrics.add(r);
			}else {
				cr = (Childrubric) provider.getApplicationContext().getBean("childrubric", Childrubric.class);
				cr.setLabel(String.valueOf(obj[1]));
				cr.setAmount(Double.valueOf(String.valueOf(obj[2])));
				r.getChilds().add(cr);
				r.updateTotal();
			}
		}
		
		return rubrics;
	}
	
	private Rubric getRubric(String label, List<Rubric> rubrics) {
		for(Rubric r:rubrics) {
			if(r.getLabel().equals(label)) return r;
		}
		return null;
	}
	
	public List<Log> getProject_Log(int project_id){
		try {
			List<Log> log = new ArrayList<Log>();
			List<Object> objects = dao.getProject_Log(project_id);
			if(objects == null) return null;
			Iterator itr = objects.iterator();
			SimpleDateFormat ndf = new SimpleDateFormat("yyyy-MM-dd");
			Log l;
			
			while(itr.hasNext()) {
				Object[] obj = (Object[]) itr.next();
				l = (Log) provider.getApplicationContext().getBean("log", Log.class);
				l.setProfessor(String.valueOf(obj[0])+" "+String.valueOf(obj[1]));
				l.setRubric(String.valueOf(obj[2]));
				l.setChildRubric(String.valueOf(obj[3]));
				l.setContribution(Double.valueOf(String.valueOf(obj[4])));
				l.setDate_contribution(java.sql.Date.valueOf(String.valueOf(obj[5]).substring(0, 10)));
				
				log.add(l);
			}
			return log;
		} catch (Exception e) {
			System.out.println("Exception: "+e.getMessage());
			return null;
		}
	}
	
	public List<Team> getTeams(int prof_id){
		return dao.getTeams(prof_id);
	}
	
	public List<Projects> getProjects(int prof_id){
		return dao.getProjects(prof_id);
	}
	
	public List<com.gestion.credits.entities.Rubric> getProject_rubrics(int project_id){
		return dao.getProject_rubrics(project_id);
	}
	
	public List<ChildRubric> getChilds(int rubric_id){
		return dao.getChilds(rubric_id);
	}
	
	public String saveContribution(int prof_id, int child_id, double amount) {
		try {
			ChildRubric child = dao.getchild(child_id);
			Professor prof = dao.getProf(prof_id);
			Contribution cnt = (Contribution) provider.getApplicationContext().getBean("cnt", Contribution.class);
			cnt.setId(new Contribution_Pk(prof.getId(), child.getId()));
			cnt.setProf(prof);
			cnt.setChildRubric(child);
			cnt.setAmount(amount);
			cnt.setDate(new Date());
			dao.saveContribution(cnt);
			dao.updatePrice(child_id, amount);
			return "done";
		} catch (Exception e) {
			return "Exception: "+e.getMessage();
		}
	}

}
