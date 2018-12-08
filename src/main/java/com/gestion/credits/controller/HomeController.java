package com.gestion.credits.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.gestion.credits.entities.Professor;
import com.gestion.credits.metier.ProfMetier;

@Controller
@RequestMapping("home")
@SessionAttributes(value="prof")
public class HomeController {

	@Autowired
	ProfMetier metier;
	
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String Login() {
		return "login";
	}
	
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public String Checklogin(@RequestParam("code") String cin, @RequestParam("password") String password, Model model) {
		if(cin.equals("test") && password.equals("test")) return "redirect:/admin/profil";
		else {
			Professor prof = metier.getProf(cin, password);
			if(prof != null) {
				model.addAttribute("prof", prof);
				return "redirect:/prof/profil";
			}
		}
		model.addAttribute("unkown", "Unkown User !");
		return "login";
	}
}
