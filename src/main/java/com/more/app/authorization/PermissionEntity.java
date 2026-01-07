package com.more.app.authorization;

import com.more.app.annotations.UIAction;
import com.more.app.entity.AbstractPojo;
import com.more.app.entity.AppResource;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Transient;

/**
 * This entity class is used for persisting permissions used by the
 * {@link JPAPermissionManager}.
 * 
 * @author Kim
 * 
 */
@Entity
public class PermissionEntity extends AbstractPojo {

    private static final long serialVersionUID = 3895345053504819128L;

    @UIAction(label = "Permission Type",errorlabel="Permission Type is required")
    @Enumerated(EnumType.STRING)
    private PermissionType type;

    private String role;

    @Column(name = "perm_resource")
    private String resource;

    private String action;
    
    @JoinColumn(referencedColumnName = "id")
    private AppResource appresource;
    
    @UIAction(label = "Resource",errorlabel="Resource is required")
    @Transient
    private String resourceName;
    
    @UIAction(label = "Description",errorlabel="Description is required")
    @Transient
    private String resourceDescription;
    
    @UIAction(label = "Module",errorlabel="Module is required")
    @Transient
    private String moduleName;

    /**
     * Default constructor.
     */
    public PermissionEntity() {

    }

    /**
     * Alternative constructor, sets the permission type upon initialization.
     * 
     * @param type
     *            The permission's type
     */
    public PermissionEntity(PermissionType type) {
        this.type = type;
    }

    /**
     * Sets the type of the permission
     * 
     * @param type
     *            The permission's type
     */
    public void setType(PermissionType type) {
        this.type = type;
    }

    /**
     * Returns the type of the permission
     * 
     * @return The permission's type
     */
    public PermissionType getType() {
        return type;
    }

    /**
     * Get the identifier of the role for which this permission is being applied
     * on.
     * 
     * @return The role for which the permission is being set
     */
    public String getRole() {
        return role;
    }

    /**
     * Set the identifier of the role for which this permission is being applied
     * on.
     * 
     * @param role
     *            The role for which the permission is being set
     */
    public void setRole(String role) {
        this.role = role;
    }

    /**
     * Get the resource's identifier for which this permission is being applied
     * on.
     * 
     * @return The resource's identifier
     */
    public String getResource() {
        return resource;
    }

    /**
     * Set the resource's identifier for which this permission is being applied
     * on.
     * 
     * @param resource
     *            The resource's identifier
     */
    public void setResource(String resource) {
        this.resource = resource;
    }

    /**
     * Get the action for which this permission is being applied on.
     * 
     * @return The action parameter for this permission
     */
    public String getAction() {
        return action;
    }

    /**
     * Set the action for which this permission is being applied on.
     * 
     * @param action
     *            The action parameter for this permission
     */
    public void setAction(String action) {
        this.action = action;
    }

	/**
	 * @return the appresource
	 */
	public AppResource getAppresource()
	{
		return appresource;
	}

	/**
	 * @param appresource the appresource to set
	 */
	public void setAppresource(AppResource appresource)
	{
		this.appresource = appresource;
	}

	/**
	 * @return the resourceName
	 */
	public String getResourceName()
	{
		if(getAppresource() != null)
			resourceName= getAppresource().getName();
		return resourceName;
	}

	/**
	 * @return the resourceDescription
	 */
	public String getResourceDescription()
	{
		if(getAppresource() != null)
			resourceDescription= getAppresource().getDescription();
		return resourceDescription;
	}

	/**
	 * @return the moduleName
	 */
	public String getModuleName()
	{
		if(getAppresource() != null)
			moduleName= getAppresource().getModuleName().toString();
		return moduleName;
	}

	/**
	 * @param resourceName the resourceName to set
	 */
	public void setResourceName(String resourceName)
	{
		this.resourceName = resourceName;
	}

	/**
	 * @param resourceDescription the resourceDescription to set
	 */
	public void setResourceDescription(String resourceDescription)
	{
		this.resourceDescription = resourceDescription;
	}

	/**
	 * @param moduleName the moduleName to set
	 */
	public void setModuleName(String moduleName)
	{
		this.moduleName = moduleName;
	}
}
