package com.gestion.credits.controller;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.gestion.credits.beans.Childrubric;
import com.gestion.credits.beans.Dashboard;
import com.gestion.credits.beans.Log;
import com.gestion.credits.beans.Rubric;
import com.gestion.credits.entities.Professor;
import com.gestion.credits.entities.Projects;
import com.gestion.credits.entities.Team;
import com.gestion.credits.metier.AdminMetier;

@Controller
@RequestMapping("admin")
public class AdminController {
	
	@Autowired
	private AdminMetier metier;

	@RequestMapping(value="/profil", method=RequestMethod.GET)
	public String profil(Model model) {
		List<Dashboard> dsh = metier.getDashBoard(2018);
		for(Dashboard b:dsh) {
			System.out.println(b.getProject()+" : "+b.getTeam()+" : "+b.getYear()+" : "+b.getAmount());
		}
		model.addAttribute("dashboard", dsh);
		return "admin/profil";
	}
	
	@RequestMapping(value="/mng_profs", method=RequestMethod.GET)
	public String MngProfs() {
		return "admin/profs_management";
	}
	
	@RequestMapping(value="/mng_profs", method=RequestMethod.POST)
	public String profsFile(@RequestParam("profs") MultipartFile file, HttpSession session, Model model) {
		String path=session.getServletContext().getRealPath("/");
		String filename = file.getOriginalFilename();
		
		System.out.println(path+ " " +filename);
		
		try {
	        byte barr[]=file.getBytes();  
	        
	        BufferedOutputStream bout=new BufferedOutputStream(  
	                 new FileOutputStream(path+"/"+filename));  
	        bout.write(barr);  
	        bout.flush();  
	        bout.close(); 
	        String result = metier.importProfs(path+"/"+filename);
	        System.out.println(result);
	        model.addAttribute("result", result);
		}catch(Exception e) {
			System.out.println("Exception" + e.getMessage());
		}
		
		return "admin/profs_management";
	}
	
	@RequestMapping(value="/mng_teams", method=RequestMethod.GET)
	public String mngTeams(Model model) {
		List<Professor> profs = metier.getProfs();
		model.addAttribute("profs", profs);
		return "admin/team_management";
	}
	
	@RequestMapping(value="/mng_teams", method=RequestMethod.POST)
	public String addTeam(@RequestParam("code") String code, @RequestParam("name") String name, @RequestParam("members") String[] members, Model model) {
		List<Professor> profs = metier.getProfs();
		model.addAttribute("profs", profs);
		String result = metier.saveTeam(name, code, members);
		model.addAttribute("result", result);
		return "admin/team_management";
	}
	
	@RequestMapping(value="/detail_project", method=RequestMethod.GET)
	public String detailProject(@RequestParam("id_project") int id_project, Model model) {
//		System.out.println(id_project);
		List<Rubric> rubrics = metier.getRubrics(id_project);
//		for(Rubric r:rubrics) {
//			System.out.println(r.getLabel());
//			for(Childrubric cr : r.getChilds()) System.out.println("child: "+cr.getLabel()+" money:"+cr.getAmount());
//			System.out.println("TOTAL IS: "+r.getTotal());
//			System.out.println("------------------------------------------------");
//		}
		model.addAttribute("rubrics", rubrics);
		return "admin/detail_project";
	}
	
	@RequestMapping(value="/mng_projects", method=RequestMethod.GET)
	public String mngProjects(Model model) {
		List<Team> teams = metier.getTeams();
		model.addAttribute("teams", teams);
		return "admin/projects_management";
	}
	
	@RequestMapping(value="/mng_projects", method=RequestMethod.POST)
	public String addProject(@RequestParam("name") String label, @RequestParam("desc") String desc, @RequestParam("duration") int duration, @RequestParam("year") int year, @RequestParam("team") int team_id, @RequestParam("rubric") String rubric, @RequestParam("childrubric") String childrubric, @RequestParam("money") double price, Model model) {
		String result = metier.saveProject(label, desc, duration, year, team_id, rubric, childrubric, price);
		model.addAttribute("result", result);
		return "admin/projects_management";
	}
	
	@RequestMapping(value="/mng_rubrics", method=RequestMethod.GET)
	public String mngRubrics(Model model) {
		List<Projects> projects = metier.getProjects();
		model.addAttribute("projects", projects);
		return "admin/rubrics_management";
	}
	
	@RequestMapping(value="/mng_rubrics", method=RequestMethod.POST)
	public String addRubric(@RequestParam("project") int project_id, @RequestParam("rubric") String rubric, @RequestParam("childrubric") String child, @RequestParam("money") double price, Model model) {
		String result = metier.saveRubric(project_id, rubric, child, price);
		model.addAttribute("result", result);
		return "admin/rubrics_management";
	}
	
	@RequestMapping(value="/mng_childs", method=RequestMethod.GET)
	public String mngChilds(Model model) {
		List<Projects> projects = metier.getProjects();
		model.addAttribute("projects", projects);
		return "admin/childs_management";
	}
	
	//AJAX Call
	@RequestMapping(value="/loadRubrics" ,method=RequestMethod.POST)
	public @ResponseBody String loadRubrics(@RequestBody Map<String, String> data){
		List<com.gestion.credits.entities.Rubric> rubrics = metier.getProject_Rubrics(Integer.valueOf(data.get("project_id")));
		String result;
		result = "[";
			for(com.gestion.credits.entities.Rubric r:rubrics) {
				result = result + "{";
				result = result + " \"id\" :  \""+r.getId()+"\", ";
				result = result + " \"name\" :  \""+r.getLabel()+"\" ";
				result = result + "}";
				if( rubrics.indexOf(r) != rubrics.size() -1 ) result = result + ",";
			}
		result = result + "]";
		System.out.println(result);
		return result;
	}
	
	@RequestMapping(value="/mng_childs", method=RequestMethod.POST)
	public String addChild(@RequestParam("project") int project_id, @RequestParam("rubric") int rubric_id, @RequestParam("childrubric") String label, @RequestParam("money") double price, Model model) {
		String result = metier.saveChild(rubric_id, label, price);
		model.addAttribute("result", result);
		return "admin/childs_management";
	}
	
	@RequestMapping(value="/log_project", method=RequestMethod.GET)
	public String projetLog(@RequestParam("id_project") int project_id, Model model) {
		List<Log> logs = metier.getProject_Log(project_id);
		model.addAttribute("logs", logs);
		return "admin/project_log";
	}
	
}
