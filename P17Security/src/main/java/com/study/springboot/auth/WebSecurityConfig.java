package com.study.springboot.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import jakarta.servlet.DispatcherType;

@Configuration
public class WebSecurityConfig {
	
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
			.permitAll();
		http.logout()
			.permitAll();
		
		return http.build();
	}
	@Bean
	public InMemoryUserDetailsManager userDetailService() {
		UserDetails user = User.withUsername("user")
								.password(passwordEncoder().encode("1234"))
								.roles("USER")
								.build();
		UserDetails admin = User.withUsername("admin")
								.password(passwordEncoder().encode("1234"))
								.roles("ADMIN")
								.build();
		UserDetails[] userDetails = new UserDetails[2];
		userDetails[0] = user;
		userDetails[1] = admin;
		System.out.print(">> ");
		System.out.print(passwordEncoder().encode("1234"));
		System.out.println(" << ");
		return new InMemoryUserDetailsManager(userDetails);
	}
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	//StandardPasswordEncoder() - SHA256
	//NoOpPasswordEncoder() - 암호화하지 않은 데이터
	
	
}
