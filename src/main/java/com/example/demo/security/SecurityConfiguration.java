package com.example.demo.security;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.example.demo.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class SecurityConfiguration {
	@Autowired
	DataSource dataSource;    // JDBC Authentication에 필요함
	@Autowired
	UserService userService;

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
	      BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	      auth.userDetailsService(userService).passwordEncoder(passwordEncoder);
	}
	
	/*
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) 
			throws Exception {
		auth.jdbcAuthentication()
		.dataSource(dataSource)
		.usersByUsernameQuery("select userid as username, pwd as password, enabled "
				+ "from userjoin "
				+ "where userid = ?")
		.authoritiesByUsernameQuery("select userid, authority "
				+ "from authorities "
				+ "where userid = ?");
	}
	*/

	/*
	@Bean
	public PasswordEncoder passwordEncoder() {
		BCryptPasswordEncoder encode = new BCryptPasswordEncoder();
		log.info("smith->"+encode.encode("1234qwer"));
		log.info("asdf->"+encode.encode("1234"));
		return encode;
	}
	*/

	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		return (web) -> web.ignoring().requestMatchers("/resources/**", "/ignore2");
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
		return httpSecurity
				// 권한에 따른 인가
				.authorizeHttpRequests()
				.requestMatchers( 
						// get 방식 허용
						"/css/**",
						"/js/**",
						"/images/**",
						"/img/**",
						"/health/main",
						"/team/login",
						"/team/findLoginInfo",
						"/team/rules",
						"/team/joinForm",
						"/team/auth/**",
						"/team/add",

						// post 방식 허용 시에는 반드시 csrf에서도 똑같이 ignoring 해줘야 한다
						"/team/sendemail",
						"/team/authEmail",
						"/team/join",
						"/doLogin"
						
						).permitAll()
				.anyRequest().authenticated() // 그 외 모든 요청은 인증된 사용자만 접근 가능

				// csrf
				.and()
				.csrf()
				.ignoringRequestMatchers(
						// post 방식 허용 시에는 반드시 csrf에서도 똑같이 ignoring 해줘야 한다
						"/team/sendemail",
						"/team/authEmail",
						"/team/join",
						"/doLogin"
						)

				// 로그인
				.and()
				.formLogin().loginPage("/team/login") // 접속 차단시 로그인 페이지로 가게 하기
				.loginProcessingUrl("/doLogin") // post 로그인
				.defaultSuccessUrl("/team/loginsuccess") // 로그인 성공시 URL
				.failureUrl("/team/login-error") // 로그인 실패시 URL
				.usernameParameter("userid")  // 로그인 폼에서 이용자 ID 필드 이름, 디폴트는 username
				.passwordParameter("pwd")  // 로그인 폼에서 이용자 암호 필트 이름, 디폴트는 password
				.permitAll()

				// 로그아웃
				.and()   // 디폴트 로그아웃 URL = /logout
				.logout().logoutRequestMatcher(new AntPathRequestMatcher("/team/logout")) //로그아웃 요청시 URL
				.logoutSuccessUrl("/team/login") // 로그아웃 성공시 URL
				.invalidateHttpSession(true)
				.deleteCookies("JSESSIONID") // 세션 삭제
				.permitAll()

				// 에러 페이지 구현하기
				.and()
				.exceptionHandling().accessDeniedPage("/team/denied")

				.and()
				.build();
	}
}