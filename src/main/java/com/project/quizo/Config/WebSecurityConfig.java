package com.project.quizo.Config;

import com.project.quizo.Web.Security.Filter.CustomAuthenticationFilter;
import com.project.quizo.Web.Security.Handler.RestAuthenticationEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import com.project.quizo.Config.Property.EndpointProperties;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private AccessDeniedHandler accessDeniedHandler;

	@Autowired
	private UserDetailsService service;

	@Autowired
	private PasswordEncoder encoder;

	@Autowired
	private RestAuthenticationEntryPoint authenticationEntryPoint;

	@Autowired
	private LogoutSuccessHandler logoutSuccessHandler;
	
	@Autowired
	private EndpointProperties properties;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.csrf().disable().exceptionHandling().authenticationEntryPoint(authenticationEntryPoint)
				.accessDeniedHandler(accessDeniedHandler);
		
		http.authorizeRequests()
				.antMatchers(properties.getWhiteList())
				.permitAll().anyRequest().authenticated()
				.and().formLogin().loginPage("/login").defaultSuccessUrl("/user/?success").permitAll();

		http.sessionManagement().maximumSessions(1);
		http.logout().logoutUrl("/logout").deleteCookies("SESSION").invalidateHttpSession(true)
				.logoutSuccessHandler(logoutSuccessHandler);
	}
	
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(service).passwordEncoder(encoder);
	}

	@Bean
	@Autowired
	public UsernamePasswordAuthenticationFilter customAuthFilter(AuthenticationSuccessHandler successHandler,
																 AuthenticationFailureHandler failureHandler) throws Exception {

		CustomAuthenticationFilter authenticationFilter = new CustomAuthenticationFilter();

		authenticationFilter.setAuthenticationSuccessHandler(successHandler);
		authenticationFilter.setAuthenticationFailureHandler(failureHandler);
		authenticationFilter.setFilterProcessesUrl("/users/login");
		authenticationFilter.setAuthenticationManager(authenticationManagerBean());

		return authenticationFilter;
	}
}