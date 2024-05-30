package com.synovus.mulesoft.dbconnection.models;

import org.springframework.stereotype.Component;
import jakarta.persistence.*;

@Entity
@Component
@Table(name = "employees")
public class EmployeeTb {

	@Id
	private long id;

	@Column(name = "active")
	private Boolean active;

	@Column(name = "name")
	private String name;

	@Column(name = "title")
	private String title;

	public EmployeeTb() {

	}

	public EmployeeTb(long id, Boolean active, String name, String title) {
		this.id = id;
		this.active = active;
		this.name = name;
		this.title = title;
	}

	public long getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getActive() {
		return this.active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public String toString() {
		return "{" + " id='" + getId() + "'" + ", name='" + getName() + "'" + ", active='" + getActive() + "'"
				+ ", title='" + getTitle() + "'" + "}";
	}
}