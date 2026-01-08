package com.more.app.entity;

import java.util.Set;

import org.apache.catalina.Role;
import org.apache.catalina.UserDatabase;

import com.more.app.util.annotations.Auditable;
import com.more.app.util.annotations.UIAction;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "role", uniqueConstraints =
{ @UniqueConstraint(columnNames =
		{ "code", "name" }) })

public class AppRole extends AbstractPojo implements Role
{
	private static final long serialVersionUID = -1908611640353142985L;

	@UIAction(label = "Role",errorlabel="Role is required")
	@Column(nullable = false, length=35)
	@Auditable(enableAudit=true, fieldNo=4)
	private String name;
	
	@UIAction(label = "Description",errorlabel="Description is required")
	@Column(nullable = false, length=70)
	@Auditable(enableAudit=true, fieldNo=5)
	private String description;

	@UIAction(label = "Status",errorlabel="Status is required")
	@Column(nullable = false, length=12)
	@Auditable(enableAudit=true, fieldNo=6)
	private String status;

	//@JoinTable
	//private User user;

	//public User getUser()
	//{
	//	return this.user;
	//}

	//public void setUser(User user)
	//{
	//	this.user = user;
	//}

	public String getIdentifier()
	{
		return getId().toString();
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getName()
	{
		return this.name;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public String getDescription()
	{
		return this.description;
	}

	@Transient
	public Set<Role> getRoles()
	{
		return null;
	}

	@Transient
	public void setRoles(Set<Role> roles)
	{

	}

	@Transient
	public void addRole(Role role)
	{

	}

	@Transient
	public void removeRole(Role role)
	{

	}

	public String getStatus()
	{
		return status;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}

	@Override
	public String getRolename() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setRolename(String rolename) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public UserDatabase getUserDatabase() {
		// TODO Auto-generated method stub
		return null;
	}
}
