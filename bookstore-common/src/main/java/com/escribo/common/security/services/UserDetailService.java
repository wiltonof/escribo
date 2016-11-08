package com.escribo.common.security.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.escribo.common.security.facade.ISecurityFacade;
import com.escribo.common.security.model.IUser;


@Service("userDetailsService")
public class UserDetailService implements UserDetailsService {

	@Autowired
	private ISecurityFacade securityFacade;
	
	public UserDetailService() {
		System.out.println(UserDetailService.class.toString());
	}

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		IUser matchingUser = securityFacade.loadUserByUsername(username);

		if (matchingUser == null) {
			throw new UsernameNotFoundException("Wrong username or password");
		}

		return (UserDetails) matchingUser;
	}
}