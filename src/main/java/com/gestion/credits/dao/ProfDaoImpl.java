package com.gestion.credits.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.gestion.credits.entities.ChildRubric;
import com.gestion.credits.entities.Contribution;
import com.gestion.credits.entities.Professor;
import com.gestion.credits.entities.Projects;
import com.gestion.credits.entities.Rubric;
import com.gestion.credits.entities.Team;

public class ProfDaoImpl implements ProfDaoInterface {

	@PersistenceContext
	private EntityManager em;
	
	@Override
	public Professor getProf(String cin, String password) {
		try {
			Query query = em.createQuery(" select p from Professor p where p.cin=:x and p.password=:y ");
			query.setParameter("x", cin);
			query.setParameter("y", password);
			return (Professor) query.getSingleResult();
		} catch (Exception e) {
			System.out.println("Exception: "+e.getMessage());
			return null;
		}
	}

	@Override
	public List<Object> getDashboard(int prof_id) {
		try {
			Query query = em.createQuery(" select team.name as team, p.label as project, p.year as year, sum(cr.credit) as credit, p.id as id_projet from Professor prof join prof.teams team join team.projects p join p.rubrics r join r.childs cr where prof.id=:x group by p.id ");
			query.setParameter("x", prof_id);
			return query.getResultList();
		} catch (Exception e) {
			System.out.println("Exception: "+e.getMessage());
		}
		return null;
	}
	
	@Override
	public List<Object> getRubrics(int project_id) {
		Query query = em.createQuery(" select r.label as rubric, cr.label as child, cr.credit as credit from Rubric r, ChildRubric cr where r.project.id=:x and r.id=cr.rubric.id");
		query.setParameter("x", project_id);
		return query.getResultList();
	}

	@Override
	public List<Object> getProject_Log(int id_project) {
		Query query = em.createQuery(" select p.name, p.lastName, r.label, cr.label, cnt.amount, cnt.date from Professor p, Rubric r, ChildRubric cr, Contribution cnt where r.id=cr.rubric.id and r.project.id=:x and cr.id=cnt.childRubric.id and cnt.prof.id=p.id ");
		query.setParameter("x", id_project);
		return query.getResultList();
	}

	@Override
	public List<Team> getTeams(int prof_id) {
		try {
			Query query = em.createQuery(" select t from Professor p join p.teams t where p.id=:x");
			query.setParameter("x", prof_id);
			return query.getResultList();
		} catch (Exception e) {
			System.out.println("Exception: "+e.getMessage());
		}
		return null;
	}

	@Override
	public List<Projects> getProjects(int prof_id) {
		Query query = em.createQuery(" select p from Professor prof join prof.teams team join team.projects p where prof.id=:x");
		query.setParameter("x", prof_id);
		return query.getResultList();
	}
	
	@Override
	public List<Rubric> getProject_rubrics(int project_id) {
		Query query = em.createQuery(" select r from Rubric r where r.project.id=:x");
		query.setParameter("x", project_id);
		return query.getResultList();
	}

	@Override
	public List<ChildRubric> getChilds(int rubric_id) {
		Query query = em.createQuery(" select cr from ChildRubric cr where cr.rubric.id=:x");
		query.setParameter("x", rubric_id);
		return query.getResultList();
	}

	@Override
	public ChildRubric getchild(int child) {
		Query query = em.createQuery(" select cr from ChildRubric cr where cr.id=:x");
		query.setParameter("x", child);
		return (ChildRubric) query.getSingleResult();
	}

	@Override
	public void saveContribution(Contribution cnt) {
		try {
			em.persist(cnt);
		} catch (Exception e) {
			System.out.println( "exception : "+ e.getMessage());
		}
	}

	@Override
	public Professor getProf(int prof_id) {
		Query query = em.createQuery(" select p from Professor p where p.id=:x");
		query.setParameter("x", prof_id);
		return (Professor) query.getSingleResult();
	}

	@Override
	public void updatePrice(int child_id, double amount) {
		Query query = em.createQuery(" update ChildRubric cr set cr.credit = cr.credit-:x where cr.id=:y ");
		query.setParameter("x", amount);
		query.setParameter("y", child_id);
	}
}
