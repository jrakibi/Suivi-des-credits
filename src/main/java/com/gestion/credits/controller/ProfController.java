package com.gestion.credits.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;

import com.gestion.credits.beans.Dashboard;
import com.gestion.credits.beans.Log;
import com.gestion.credits.beans.Rubric;
import com.gestion.credits.entities.ChildRubric;
import com.gestion.credits.entities.Professor;
import com.gestion.credits.entities.Projects;
import com.gestion.credits.entities.Team;
import com.gestion.credits.metier.ProfMetier;

@Controller
@RequestMapping(value="prof")
public class ProfController {
	
	@Autowired
	ProfMetier metier;
	
	@RequestMapping(value="/profil", method=RequestMethod.GET)
	public String profil(Model model, HttpSession session) {
		Professor prof = (Professor) session.getAttribute("prof");
		List<Dashboard> dsh = metier.getDashboard(prof.getId());
		model.addAttribute("dashboard", dsh);
		return "prof/profil";
	}
	
	@RequestMapping(value="/detail_project", method=RequestMethod.GET)
	public String getRubrics(@RequestParam("id_project") int project_id, Model model) {
		List<Rubric> rubrics = metier.getRubrics(project_id);
		model.addAttribute("rubrics", rubrics);
		return "prof/detail_project";
	}

	@RequestMapping(value="/log_project", method=RequestMethod.GET)
	public String projetLog(@RequestParam("id_project") int project_id, Model model) {
		List<Log> logs = metier.getProject_Log(project_id);
		model.addAttribute("logs", logs);
		return "prof/project_log";
	}
	
	@RequestMapping(value="/mng_teams", method=RequestMethod.GET)
	public String mngTeams(Model model, HttpSession session) {
		Professor prof = (Professor) session.getAttribute("prof");
		List<Team> teams = metier.getTeams(prof.getId());
		model.addAttribute("teams", teams);
		return "prof/teams";
	}
	
	@RequestMapping(value="/fact", method=RequestMethod.GET)
	public String contibution(Model model, HttpSession session) {
		Professor prof = (Professor) session.getAttribute("prof");
		List<Projects> projects = metier.getProjects(prof.getId());
		model.addAttribute("projects", projects);
		return "prof/contribution";
	}
	
	//AJAX Call
	@RequestMapping(value="/loadRubrics" ,method=RequestMethod.POST)
	public @ResponseBody String loadRubrics(@RequestBody Map<String, String> data){
		List<com.gestion.credits.entities.Rubric> rubrics = metier.getProject_rubrics(Integer.valueOf(data.get("project_id")));
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
	
	//AJAX Call
	@RequestMapping(value="/loadChilds" ,method=RequestMethod.POST)
	public @ResponseBody String loadChilds(@RequestBody Map<String, String> data) {
		String result = "[";
		List<ChildRubric> childs = metier.getChilds(Integer.valueOf(data.get("rubric_id")));
		for(ChildRubric cr:childs) {
			result = result + "{";
				result = result + " \"id\" :  \""+cr.getId()+"\", ";
				result = result + " \"name\" :  \""+cr.getLabel()+"\", ";
				result = result + " \"credit\" :  \""+cr.getCredit()+"\" ";
			result = result + "}";
			if( childs.indexOf(cr) != childs.size() -1 ) result = result + ",";
		}
		result = result + "]";
		System.out.println(result);
		return result;
	}
	
	@RequestMapping(value="/contribution" ,method=RequestMethod.POST)
	public String addContribution(@RequestParam("project") int project_id, @RequestParam("rubric") int rubric_id, @RequestParam("child") int child_id, @RequestParam("contribution") double money, HttpSession session, Model model) {
		Professor prof = (Professor) session.getAttribute("prof");
		String result = metier.saveContribution(prof.getId(), child_id, money);
		model.addAttribute("result", result);
		return "prof/contribution";
	}
	
	@RequestMapping(value="/logout", method=RequestMethod.GET)
	public String logOut(HttpSession session) {
		session.invalidate();
		return "redirect:/home/login";
	}
}
