package com.study.springboot.auth;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import jakarta.servlet.DispatcherType;

@Configuration
public class WebSecurityConfig {
	
	@Autowired
	public AuthenticationFailureHandler authFailureHandler;
	
	@Autowired
	public AuthenticationSuccessHandler authSuccessHandeler;
	
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		http.csrf().disable().cors().disable();
		//csrf - 웹 어플리케이션 보안상의 문제중에 몇가지를 해결하기 위해
		//cors - 크로스 오리진, 인터넷하다보면, 우리가 지금 도메인이 아닌 다른 리소스를 사용할 수 없게
		http.authorizeHttpRequests(request -> request.dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()
			.requestMatchers("/").permitAll()
			.requestMatchers("/css/**","/js/**","/img/**").permitAll()
			.requestMatchers("/guest/**").permitAll()
			.requestMatchers("/member/**").hasAnyRole("USER","ADMIN")
			.requestMatchers("/admin/**").hasRole("ADMIN")
													.anyRequest().authenticated()//모든 요청 인증
									);
		http.formLogin()
			.loginPage("/loginForm")	//default : /login 
			.loginProcessingUrl("/j_spring_security_check")
			//.failureUrl("/loginError")
			//.failureUrl("/loginForm?error")
			.failureHandler(authFailureHandler)
			//.defaultSuccessUrl("/")
			//.successForwardUrl("/forward")
			.successHandler(authSuccessHandeler)
			.usernameParameter("j_username")
			.passwordParameter("j_password")
			.permitAll();
		http.logout()
			.logoutUrl("/logout")
			.logoutSuccessUrl("/loginForm")
			.permitAll();
		
		return http.build();
	}
	
	@Bean
	public UserDetailsManager users(DataSource datasource) {
		System.out.println(passwordEncoder().encode("1234"));
		JdbcUserDetailsManager users = new JdbcUserDetailsManager(datasource);
		users.setUsersByUsernameQuery("SELECT NAME AS USERNAME, PASSWORD, ENABLED FROM USER_LIST WHERE NAME =?");
		users.setAuthoritiesByUsernameQuery("SELECT NAME AS USERNAME, AUTHORITY FROM USER_LIST WHERE NAME =?");
		return users;
	}
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	//StandardPasswordEncoder() - SHA256
	//NoOpPasswordEncoder() - 암호화하지 않은 데이터
	
	
}
