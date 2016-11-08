package com.escribo.conf;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.session.SessionManagementFilter;

import com.escribo.common.util.JsfRedirectStrategy;


 
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
 
	@Autowired(required=true)
	@Qualifier("userDetailsService")
	UserDetailsService userDetailsService;

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}
     
    @Override
    protected void configure(HttpSecurity http) throws Exception {
  
    	http.csrf().disable();
        http.exceptionHandling().and().authorizeRequests().and()
        .exceptionHandling().accessDeniedPage("/access-denied.xhtml").and()
        .addFilterBefore(sessionManagementFilter(), BasicAuthenticationFilter.class)
        .authorizeRequests().antMatchers("/javax.faces.resource/**").permitAll().and()
        .authorizeRequests().antMatchers("/**").permitAll().and()
        .authorizeRequests().antMatchers("/pages/**").hasRole("LOGAR_SIST").and()
        .authorizeRequests().antMatchers("/templates/**").permitAll()
        .anyRequest().authenticated()
        .and().logout().logoutSuccessUrl("/login.xhtml").permitAll()
        .and().formLogin().loginPage("/login.xhtml").loginProcessingUrl("/login")
        .failureUrl("/login.xhtml").permitAll();
         
    }
    
    @Bean
    public SessionManagementFilter sessionManagementFilter(){
    	SessionManagementFilter sessionManagementFilter = new SessionManagementFilter(httpSessionSecurityContextRepository());
    	sessionManagementFilter.setInvalidSessionStrategy(new JsfRedirectStrategy());
    	return sessionManagementFilter;
    }
    
    @Bean
    public HttpSessionSecurityContextRepository httpSessionSecurityContextRepository(){
    	return new HttpSessionSecurityContextRepository();
    }
    
	@Bean
	public PasswordEncoder passwordEncoder(){
		PasswordEncoder encoder = new BCryptPasswordEncoder();
	//	PasswordEncoder encoder = new Md5PasswordEncoder();
		return encoder;
	}
	
	@Bean(name="authenticationManager")
    @Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	
	public static void main(String[] args) {
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		System.out.println(encoder.encode("abcd1234"));
	}

}