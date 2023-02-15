package com.example.demo.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@EnableWebSecurity
public class HealthSecurityConfig {
	@Autowired
	DataSource dataSource;
	
	@Autowired
	public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
		log.info("데이터소스 설정");
		auth.jdbcAuthentication().dataSource(dataSource);
	}
	
	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		return (web) -> web.ignoring().requestMatchers("/resources/**","/ignore2");
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		log.info("접근 제한 설정");
		return http.authorizeHttpRequests()
				.requestMatchers("/sec/sample").hasAnyRole("GUEST", "ADMIN")
				.anyRequest().authenticated() // 위의 설정 이외의 모든 요청은 인증을 거쳐야 한다
				//.anyRequest().permitAll() // 위의 설정 이외의 모든 요청은 인증 요구하지 않음
				
				.and()
				.formLogin().loginPage("/team/login")
				.permitAll()
				
				.and()
				.exceptionHandling().accessDeniedPage("/sec/denied")
				
				.and()
				.build();
	}
}
