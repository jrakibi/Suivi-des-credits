package com.gestion.credits.metier;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.gestion.credits.beans.ApplicationContextProvider;
import com.gestion.credits.beans.Childrubric;
import com.gestion.credits.beans.Dashboard;
import com.gestion.credits.beans.Log;
import com.gestion.credits.beans.Rubric;
import com.gestion.credits.dao.AdminDaoInterface;
import com.gestion.credits.entities.ChildRubric;
import com.gestion.credits.entities.Professor;
import com.gestion.credits.entities.Projects;
import com.gestion.credits.entities.Team;

@Transactional
public class AdminMetier {
	
	@Autowired
	private ApplicationContextProvider provider;
	
	private AdminDaoInterface dao;
	
	public AdminDaoInterface getDao() {
		return dao;
	}

	public void setDao(AdminDaoInterface dao) {
		this.dao = dao;
	}
	
	public List<Dashboard> getDashBoard(int year){
		List<Object> objects = dao.getDashBoard(year);
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

	public String importProfs(String path) {
		
		FileInputStream input;
		Professor p;
		try {
			input = new FileInputStream(path);
	        XSSFWorkbook my_xls_workbook = new XSSFWorkbook(input);    
	        XSSFSheet my_worksheet = my_xls_workbook.getSheetAt(0); 
	        Iterator<Row> rowIterator = my_worksheet.iterator();
	        
	        while(rowIterator.hasNext()) {
	            Row row = rowIterator.next();   
	            Iterator<Cell> cellIterator = row.cellIterator();
	            p = (Professor) provider.getApplicationContext().getBean("professor", Professor.class);
	            p.setCin(cellIterator.next().getStringCellValue());
	            p.setName(cellIterator.next().getStringCellValue());
	            p.setLastName(cellIterator.next().getStringCellValue());
	            p.setEmail(cellIterator.next().getStringCellValue());
	            p.setPassword(cellIterator.next().getStringCellValue());
	            dao.saveProf(p);
	        }
	        input.close(); 
	        my_xls_workbook.close();
		} catch (Exception e) {
			return "Exception: "+e.getMessage();
		}
 
		return "done";
	}

	public List<Professor> getProfs(){
		return dao.getProfs();
	}
	
	public String saveTeam(String name, String code, String[] members) {
		try {
			List<Professor> profs = new ArrayList<Professor>() ;
			Professor p;
			Team t = (Team) provider.getApplicationContext().getBean("team", Team.class);
			t.setCode(code);
			t.setName(name);
		
			for(String namen: members) {
				p = dao.getProf(namen);
				profs.add(p);
			}
			t.setProfs(profs);
			dao.saveTeam(t);
		} catch (Exception e) {
			return "Exception: "+e.getMessage();
		}
		return "done";
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
	
	public List<Team> getTeams(){
		return dao.getTeams();
	}
	
	public String saveProject(String label, String desc, int duration, int year, int team_id, String rubric, String childrubric, double price) {
		try {
			Projects p = (Projects) provider.getApplicationContext().getBean("project", Projects.class);
			p.setLabel(label);
			p.setDescription(desc);
			p.setDuration(duration);
			p.setYear(year);
			
			Team t = dao.getTeam(team_id);
			p.setTeam(t);
			p = dao.saveProject(p);
			
			com.gestion.credits.entities.Rubric r = (com.gestion.credits.entities.Rubric) provider.getApplicationContext().getBean("r", com.gestion.credits.entities.Rubric.class);
			r.setLabel(rubric);
			r.setProject(p);
			r = dao.saveRubric(r);
			
			ChildRubric cr = (ChildRubric) provider.getApplicationContext().getBean("childrub", ChildRubric.class);
			cr.setLabel(childrubric);
			cr.setCredit(price);
			cr.setRubric(r);
			dao.saveChild(cr);
		} catch (Exception e) {
			return "Exception: "+e.getMessage();
		}
		return "done";
	}
	
	public List<Projects> getProjects(){
		List<Projects> projects = dao.getProjects();
		return projects;
	}
	
	public String saveRubric(int project_id, String rubric, String child, double price) {
		try {
			Projects p = dao.getProject(project_id);
			com.gestion.credits.entities.Rubric r = (com.gestion.credits.entities.Rubric) provider.getApplicationContext().getBean("r", com.gestion.credits.entities.Rubric.class);
			r.setLabel(rubric);
			r.setProject(p);
			r = dao.saveRubric(r);
			
			ChildRubric cr = (ChildRubric) provider.getApplicationContext().getBean("childrub", ChildRubric.class);
			cr.setLabel(child);
			cr.setCredit(price);
			cr.setRubric(r);
			dao.saveChild(cr);
		} catch (Exception e) {
			return "Exception: "+e.getMessage();
		}
		return "done";
	}
	
	public List<com.gestion.credits.entities.Rubric> getProject_Rubrics(int project_id){
		List<com.gestion.credits.entities.Rubric> rubrics = dao.getProject_rubrics(project_id);
		return rubrics;
	}
	
	public String saveChild(int rubric_id, String label, double price) {
		try {
			com.gestion.credits.entities.Rubric r = dao.getRubric(rubric_id);
			ChildRubric cr = (ChildRubric) provider.getApplicationContext().getBean("childrub", ChildRubric.class);
			cr.setLabel(label);
			cr.setCredit(price);
			cr.setRubric(r);
			dao.saveChild(cr);
		} catch (Exception e) {
			return "Exception: "+e.getMessage();
		}
		return "done";
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
}
