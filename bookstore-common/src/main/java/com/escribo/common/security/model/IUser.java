package com.escribo.common.security.model;


import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;

import com.escribo.common.foundation.model.IModel;



public interface IUser extends IModel, UserDetails {
	public static final String CREATE 		= "CREATE_USER";
	public static final String UPDATE 		= "UPDATE_USER"; 
	public static final String VIEW 		= "VIEW_USER";  
	public static final String DELETE 		= "DELETE_USER"; 
	
	public String getEmail();

	public void setEmail(String email);

	public String getPassword();

	public void setPassword(String password);
	
	public String getConfirmPassword();

	public void setConfirmPassword(String confirmPassword);

	public String getPasswordToken();

	public void setPasswordToken(String passwordToken);

	public boolean isChangePassword();

	public void setChangePassword(boolean changePassword);
	
	public String getName();

	public void setName(String name);

	public void setUsername(String username);

	public IRole getRole();

	public void setRole(IRole role);

	public List<ISecurityLevel> getSecurityLevels();
	
	public void addSecurityLevels(ISecurityLevel securityLevel);
	
	public void addAllSecurityLevels(List<ISecurityLevel> securityLevels);

}
