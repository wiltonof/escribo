package com.escribo.security.view;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.escribo.common.foundation.view.AbstractController;
import com.escribo.common.security.facade.ISecurityFacade;
import com.escribo.common.security.model.IUser;
import com.escribo.common.security.model.IUserAccessData;
import com.escribo.common.security.model.impl.UserAccessData;
import com.escribo.common.util.MsgFactory;


@SuppressWarnings("deprecation")
@ManagedBean(name = "securityMB")
@SessionScoped
@Configurable
public class SecurityController extends AbstractController<IUser> {

	private String userName = null;

	private String password = null;
	
	private String userRealName;
	
	private String userRoleName;
	
	private String userEmail;
	
	private IUserAccessData prevUserAccessData;
	
	private Boolean passChange = true;
	
	private Boolean accept = false;
	
	private int loginAttempts;
	
	@Value("${custom.task_role_cod}")
	private Integer taskRoleCod;
	
	@Value("${custom.root_role_cod}")
	private Long rootRoleCod;
	
	private AuthenticationManager authenticationManager;
	
	public SecurityController() {
		// TODO Auto-generated constructor stub
	}
	
	public String login() {
		try {
			List<GrantedAuthority> grantedAuthorities =  new  ArrayList<GrantedAuthority>(); 
			grantedAuthorities.add(new GrantedAuthorityImpl("LOGAR_SIST"));
			
			Authentication request = new UsernamePasswordAuthenticationToken(this.getUserName(), this.getPassword());
			Authentication result = getAuthenticationManager().authenticate(request);
			if (result.isAuthenticated()) {
				IUserAccessData userAccessData = new UserAccessData();
				userAccessData.setUserId(((IUser) result.getPrincipal()).getId());
				userAccessData.setIp(((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getRemoteAddr());
				getSecurityFacade().saveUserAccessData(userAccessData);
				SecurityContextHolder.getContext().setAuthentication(result);
				this.setPassword(null);
				this.setPrevUserAccessData(getSecurityFacade().loadPrevUserAccessData());
				this.setUserRealName(((IUser) result.getPrincipal()).getName());
				this.setUserRoleName(((IUser) result.getPrincipal()).getRole().getSecurityLevel().getDescription());
				this.setUserEmail((((IUser) result.getPrincipal()).getEmail()));
				this.loginAttempts = 0;
				return "dashboard?faces-redirect=true";
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.loginAttempts++;
			FacesContext.getCurrentInstance().addMessage(null,MsgFactory.getMessage("message_login_failed",	FacesMessage.SEVERITY_WARN, ""));
			// workaround to update primefaces captcha component
			if (this.loginAttempts == 3) {
				return "login?faces-redirect=true";
			}
		}

		return null;
	}
	
	public ISecurityFacade getSecurityFacade() {
		return (ISecurityFacade) getBean("securityFacade");
	}
	
	public void acceptValueChangeListener(ValueChangeEvent event){
		System.out.println("Test" + event.getNewValue());
	}

	public IUser getAuthenticatedUser() {
		SecurityContext context = SecurityContextHolder.getContext();
		if (context == null)
			return null;
		Authentication authentication = context.getAuthentication();
		if (authentication == null)
			return null;
		if (authentication.getPrincipal() != null) {
			return (IUser) authentication.getPrincipal();
		} else {
			return null;
		}
	}
	
	public String lastAcces(){
		IUserAccessData access = getSecurityFacade().loadPrevUserAccessData();
		return new SimpleDateFormat("dd-MM-yyyy").format(access.getCreatedAt());
	}
	
	public String lastAccesAndHoures(){
		IUserAccessData access = getSecurityFacade().loadPrevUserAccessData();
		return new SimpleDateFormat("dd-MM-yyyy, HH:mm").format(access.getCreatedAt());
	}
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserRealName() {
		if (userRealName == null) {
			SecurityContext context = SecurityContextHolder.getContext();
			if (context == null)
				return null;

			Authentication authentication = context.getAuthentication();
			if (authentication == null)
				return null;

			if (authentication.getPrincipal() != null) {
				this.setPrevUserAccessData(getSecurityFacade().loadPrevUserAccessData());
				this.setUserRealName(((IUser) authentication.getPrincipal()).getName());
				this.setUserRoleName(((IUser) authentication.getPrincipal()).getRole().getSecurityLevel().getDescription());
				this.setUserEmail((((IUser) authentication.getPrincipal()).getEmail()));

				return userRealName;
			} else {
				return null;
			}
		} else {
			return userRealName;
		}	
	}
	
	public void setUserRealName(String userRealName) {
		this.userRealName = userRealName;
	}

	public String getUserRoleName() {
		return userRoleName;
	}

	public void setUserRoleName(String userRoleName) {
		this.userRoleName = userRoleName;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public IUserAccessData getPrevUserAccessData() {
		return prevUserAccessData;
	}

	public void setPrevUserAccessData(IUserAccessData prevUserAccessData) {
		this.prevUserAccessData = prevUserAccessData;
	}

	public Boolean getPassChange() {
		return passChange;
	}

	public void setPassChange(Boolean passChange) {
		this.passChange = passChange;
	}

	public int getLoginAttempts() {
		return loginAttempts;
	}

	public void setLoginAttempts(int loginAttempts) {
		this.loginAttempts = loginAttempts;
	}

	public Integer getTaskRoleCod() {
		return taskRoleCod;
	}

	public void setTaskRoleCod(Integer taskRoleCod) {
		this.taskRoleCod = taskRoleCod;
	}

	public Long getRootRoleCod() {
		return rootRoleCod;
	}

	public void setRootRoleCod(Long rootRoleCod) {
		this.rootRoleCod = rootRoleCod;
	}

	public AuthenticationManager getAuthenticationManager() {
		setAuthenticationManager((AuthenticationManager) getBean("authenticationManager"));
		return authenticationManager;
	}

	public void setAuthenticationManager(AuthenticationManager authenticationManager) {
		
		this.authenticationManager = authenticationManager;
	}
	
	public void changeAcceptField(){
		this.accept = !this.accept;
	}

	public Boolean getAccept() {
		return accept;
	}

	public void setAccept(Boolean accept) {
		this.accept = accept;
	}	
	public static void main(String[] args) {
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		System.out.println(encoder.encode("abcd1234"));
	}
}
