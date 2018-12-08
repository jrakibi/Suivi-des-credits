package com.gestion.credits.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.gestion.credits.beans.Dashboard;
import com.gestion.credits.entities.ChildRubric;
import com.gestion.credits.entities.Professor;
import com.gestion.credits.entities.Projects;
import com.gestion.credits.entities.Rubric;
import com.gestion.credits.entities.Team;

public class AdminDaoImpl implements AdminDaoInterface {

	@PersistenceContext
	private EntityManager em;
	
	@Override
	public List<Object> getDashBoard(int year) {
		Query query = em.createQuery(" select t.name as team, p.label as project, p.year as year, sum(cr.credit) as credit, p.id as id_projet from Team t, Projects p, Rubric r, ChildRubric cr where t.id = p.team.id and p.year=:x and p.id = r.project.id and r.id = cr.rubric.id group by p.id");
		query.setParameter("x", year);
		return query.getResultList();
	}

	@Override
	public void saveProf(Professor p) {
		em.persist(p);
	}

	@Override
	public List<Professor> getProfs() {
		Query query = em.createQuery("select p from Professor p");
		return query.getResultList();
	}

	@Override
	public Professor getProf(String name) {
		Query query = em.createQuery(" select p from Professor p where p.name=:x ");
		query.setParameter("x", name);
		return (Professor) query.getSingleResult();
	}

	@Override
	public void saveTeam(Team t) {
		try {
			em.persist(t);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public List<Object> getRubrics(int project_id) {
		Query query = em.createQuery(" select r.label as rubric, cr.label as child, cr.credit as credit from Rubric r, ChildRubric cr where r.project.id=:x and r.id=cr.rubric.id");
		query.setParameter("x", project_id);
		return query.getResultList();
	}

	@Override
	public List<Team> getTeams() {
		Query query = em.createQuery(" select t from Team t ");
		return query.getResultList();
	}

	@Override
	public Projects saveProject(Projects p) {
		em.persist(p);
		return p;
	}

	@Override
	public Team getTeam(int team_id) {
		Query query = em.createQuery(" select t from Team t where t.id=:x ");
		query.setParameter("x", team_id);
		return (Team) query.getSingleResult();
	}

	@Override
	public Rubric saveRubric(Rubric r) {
		em.persist(r);
		return r;
	}

	@Override
	public ChildRubric saveChild(ChildRubric cr) {
		em.persist(cr);
		return cr;
	}

	@Override
	public List<Projects> getProjects() {
		Query query = em.createQuery(" select p from Projects p ");
		return query.getResultList();
	}

	@Override
	public Projects getProject(int id) {
		Query query = em.createQuery(" select p from Projects p where p.id=:x ");
		query.setParameter("x", id);
		return (Projects) query.getSingleResult();
	}

	@Override
	public List<Rubric> getProject_rubrics(int project_id) {
		Query query = em.createQuery(" select r from Rubric r where r.project.id=:x");
		query.setParameter("x", project_id);
		return query.getResultList();
	}

	@Override
	public Rubric getRubric(int rubric_id) {
		Query query = em.createQuery(" select r from Rubric r where r.id=:x ");
		query.setParameter("x", rubric_id);
		return (Rubric) query.getSingleResult();
	}

	@Override
	public List<Object> getProject_Log(int id_project) {
		Query query = em.createQuery(" select p.name, p.lastName, r.label, cr.label, cnt.amount, cnt.date from Professor p, Rubric r, ChildRubric cr, Contribution cnt where r.id=cr.rubric.id and r.project.id=:x and cr.id=cnt.childRubric.id and cnt.prof.id=p.id ");
		query.setParameter("x", id_project);
		return query.getResultList();
	}

}
