package com.gestion.credits.entities;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Contribution_Pk implements Serializable {
	
	@Column(name="prof_id")
	private int prof_id;
	
	@Column(name="ChildRubric_id")
	private int ChildRubric_id;

	public Contribution_Pk(int prof_id, int childrubric_id) {
		this.prof_id = prof_id;
		this.ChildRubric_id = childrubric_id;
	}
	
	public int getProf_id() {
		return prof_id;
	}

	public void setProf_id(int prof_id) {
		this.prof_id = prof_id;
	}

	public int getChildRubric_id() {
		return ChildRubric_id;
	}

	public void setChildRubric_id(int childRubric_id) {
		ChildRubric_id = childRubric_id;
	}
	
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
 
        if (o == null || getClass() != o.getClass()) 
            return false;
 
        Contribution_Pk that = (Contribution_Pk) o;
        return Objects.equals(prof_id, that.prof_id) && Objects.equals(ChildRubric_id, that.ChildRubric_id);
    }
 
    @Override
    public int hashCode() {
        return Objects.hash(prof_id, ChildRubric_id);
    }

}
