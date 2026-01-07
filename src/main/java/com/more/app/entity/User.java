package com.more.app.entity;

import com.more.app.annotations.UIAction;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.persistence.UniqueConstraint;

/**
 * Entity class for users. This class keeps information about registered users.
 * 
 * @author Kim
 * 
 */
@Entity
@NamedQuery(name = "User.findAllOrderedDesc", query = "SELECT p FROM User p order by p.id desc")
@Table(name = "appuser", uniqueConstraints = { @UniqueConstraint(columnNames = { "username" }) })
public class User extends AbstractPojo {

    private static final long serialVersionUID = 4417119399127203109L;

    //name staffNo email username password

	@UIAction(label = "User Name",errorlabel="User Name is required")
	@Column(nullable = false, length = 35)
    protected String username;
	
	@UIAction(label = "Password",errorlabel="Password is required")
	@Column(nullable = true)
    protected String password;

	@UIAction(label = "Full Name",errorlabel="Full Name is required")
	@Column(nullable = false, length = 50)
    private String name;
    

	@UIAction(label = "Staff No",errorlabel="Staff No is required")
	@Column(nullable = true, length = 10)
    private String staffNo;

	@UIAction(label = "Email",errorlabel="Email is required")
	@Column(nullable = false, length = 35)
    private String email;

    private int failedLoginAttempts = 0;

    @UIAction(label = "Account Locked",errorlabel="Account Locked is required")
    private boolean accountLocked = false;

    private String reasonForLockedAccount;

    @Transient
    private int failedPasswordChanges = 0;
    
    private Long roleId;
    
    @Transient
    private AppRole role;

    public User() {

    }

    /**3
     * Get the username of the user
     * 
     * @return User's username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Set the username for the user
     * 
     * @param username
     *            New username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Get the hashed password of the user
     * 
     * @return Hashed password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Set the hashed password of the user
     * 
     * @param password
     *            New hHashed password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Set the actual name of the user
     * 
     * @param name
     *            New name for the user
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the actual name of the user
     * 
     * @return Name of the user
     */
    public String getName() {
        return name;
    }

    /**
     * Set an email address for the user
     * 
     * @param email
     *            New email address
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Get the user's email address
     * 
     * @return User's email address
     */
    public String getEmail() {
        return email;
    }

    /**
     * Increments the amount of failed login attempts by one
     */
    public void incrementFailedLoginAttempts() {
        failedLoginAttempts++;
    }

    /**
     * Define if the account should be locked
     * 
     * @param accountLocked
     *            true if account should be locked, false if account should be
     *            activated
     */
    public void setAccountLocked(boolean accountLocked) {
        this.accountLocked = accountLocked;
    }

    /**
     * Is the current user account locked
     * 
     * @return true if account is locked, otherwise false
     */
    public boolean isAccountLocked() {
        return accountLocked;
    }

    /**
     * Resets the number of failed login attempts back to zero
     */
    public void clearFailedLoginAttempts() {
        failedLoginAttempts = 0;
    }

    /**
     * Get the number of failed login attempts
     * 
     * @return The number of failed login attempts
     */
    public int getFailedLoginAttempts() {
        return failedLoginAttempts;
    }

    /**
     * Sets the reason why the account has been locked
     * 
     * @param reasonForLockedAccount
     *            Reason for account being locked
     */
    public void setReasonForLockedAccount(String reasonForLockedAccount) {
        this.reasonForLockedAccount = reasonForLockedAccount;
    }

    /**
     * Get the reason why the account has been locked
     * 
     * @return Reason for account being locked
     */
    public String getReasonForLockedAccount() {
        return reasonForLockedAccount;
    }

    /**
     * Increments the number of failed password change attempts. This value is
     * not persisted.
     */
    public void incrementFailedPasswordChangeAttempts() {
        failedPasswordChanges++;
    }

    /**
     * Clears the number of failed password change attempts
     */
    public void clearFailedPasswordChangeAttempts() {
        failedPasswordChanges = 0;
    }

    /**
     * Returns the number of failed password change attempts. This value is not
     * persisted.
     * 
     * @return Number of failed password change attempts for this object
     *         instance
     */
    public int getFailedPasswordChangeAttemps() {
        return failedPasswordChanges;
    }

	public String getStaffNo()
	{
		return staffNo;
	}

	public void setStaffNo(String staffNo)
	{
		this.staffNo = staffNo;
	}

	/**
	 * @return the role
	 */
	public AppRole getRole()
	{
		return role;
	}

	/**
	 * @param role the role to set
	 */
	public void setRole(AppRole role)
	{
		this.role = role;
	}

	/**
	 * @return the roleId
	 */
	public Long getRoleId()
	{
		return roleId;
	}

	/**
	 * @param roleId the roleId to set
	 */
	public void setRoleId(Long roleId)
	{
		this.roleId = roleId;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return "User [username=" + username + ", password=" + password + ", name=" + name + ", staffNo=" + staffNo
				+ ", email=" + email + ", failedLoginAttempts=" + failedLoginAttempts + ", accountLocked="
				+ accountLocked + ", reasonForLockedAccount=" + reasonForLockedAccount + ", failedPasswordChanges="
				+ failedPasswordChanges + ", roleId=" + roleId + ", role=" + role + "]";
	}

	
}
