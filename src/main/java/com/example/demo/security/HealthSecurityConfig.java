package com.example.demo.security;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@EnableWebSecurity
public class HealthSecurityConfig {
	@Autowired
	DataSource dataSource;    // JDBC Authentication에 필요함

	@Bean
	public BCryptPasswordEncoder  passwordEncoder() {
		// 60자로 암호화 해준다
		BCryptPasswordEncoder enc = new BCryptPasswordEncoder();
		log.info("smith->" + enc.encode("smith")); // ROLE_ADMIN
		log.info("blake->" + enc.encode("blake")); // ROLE_USER
		log.info("jones->" + enc.encode("jones")); // ROLE_GUEST
		return enc;
	}
	/*
	 * smith->$2a$10$kqk98pSUNsPwdnzED3IC5./1Qw/MukfF33WhdQjRhvhzygmiISriu
	 * blake->$2a$10$peWBE7KRBkxj3h9WHjiTQuplVc.homrK.MumRdV3gfc6hC4qShcYG
	 * jones->$2a$10$x1bf..r/ZOewaq9xPLw2nuYRMFZwBbgHBAiEOvuNhQEYuIPzHz/SC
	 */

	//Enable jdbc authentication
	// SimpleSecurityConfig.java와 다른 부분
	@Autowired
	public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
		log.info("데이터소스 설정");
		auth.jdbcAuthentication().dataSource(dataSource);
	}

	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		return (web) -> web.ignoring().requestMatchers("/resources/**", "/ignore2");
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		log.info("접근제한 설정");
		return http.authorizeHttpRequests()/* 권한에 따른 인가(Authorization) */
				.requestMatchers("/", "/sec/", "/sec/loginForm", "/sec/denied", "/logout").permitAll()
				.requestMatchers("/sec/hello").hasAnyRole("USER", "ADMIN")
				.requestMatchers("/sec/getemps").hasAnyRole("USER", "ADMIN")
				.requestMatchers("/sec/addemp").hasAnyRole("ADMIN")
				.requestMatchers("/sec/menu").hasAnyRole("USER","GUEST","ADMIN")
				.requestMatchers("/sec/sample").hasAnyRole("GUEST", "ADMIN")
				//.anyRequest().authenticated()  // 위의 설정 이외의 모든 요청은 인증을 거쳐야 한다
				.anyRequest().permitAll()        // 위의 설정 이외의 모든 요청은 인증 요구하지 않음

				.and()
				//.csrf().disable()    //csrf 기능을 사용하지 않을 때
				//.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()) //csrf 활성화
				.csrf()
				.ignoringRequestMatchers("/sec/")
				.ignoringRequestMatchers("/sec/hello")
				.ignoringRequestMatchers("/sec/loginForm")
				//.ignoringAntMatchers("/csrf/score")
				//.ignoringAntMatchers("/doLogin")
				
				//.csrf().ignoringAntMatchers("/logout") //요청시 'POST' not supported 에러 방지
				//.ignoringAntMatchers("/sec/loginForm")
				//.ignoringAntMatchers("/doLogin")

				.and()
				.formLogin().loginPage("/sec/loginForm")   // 지정된 위치에 로그인 폼이 준비되어야 함
				.loginProcessingUrl("/doLogin")            // 컨트롤러 메소드 불필요, 폼 action과 일치해야 함
				.failureUrl("/sec/login-error")      // 로그인 실패시 다시 로그인 폼으로
				//.failureForwardUrl("/login?error=Y")  //실패시 다른 곳으로 forward
				.defaultSuccessUrl("/sec/menu", true)
				.usernameParameter("id")  // 로그인 폼에서 이용자 ID 필드 이름, 디폴트는 username
				.passwordParameter("pw")  // 로그인 폼에서 이용자 암호 필트 이름, 디폴트는 password
				.permitAll()

				.and()   // 디폴트 로그아웃 URL = /logout
				.logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")) //로그아웃 요청시 URL
				.logoutSuccessUrl("/sec/loginForm?logout=T")
				.invalidateHttpSession(true) 
				.deleteCookies("JSESSIONID")
				.permitAll()

				.and()
				.exceptionHandling().accessDeniedPage("/sec/denied")
				.and().build();
	}

	/*  아래의 내용은 In-Memory Authentication에 사용되므로 JDBC Authentication에는 불필요
	@Autowired
    public void configureGlobal(AuthenticationManagerBuilder authenticationMgr) throws Exception {
        authenticationMgr.inMemoryAuthentication().withUser("employee").password("$2a$10$MZ2ANCUXIj5mrAVbytojruvzrPv9B3v9CXh8qI9qP13kU8E.mq7yO")
            .authorities("ROLE_USER").and().withUser("imadmin").password("$2a$10$FA8kEOhdRwE7OOxnsJXx0uYQGKaS8nsHzOXuqYCFggtwOSGRCwbcK")
            .authorities("ROLE_USER", "ROLE_ADMIN").and().withUser("guest").password("$2a$10$ABxeHaOiDbdnLaWLPZuAVuPzU3rpZ30fl3IKfNXybkOG2uZM4fCPq")
            .authorities("ROLE_GUEST");
    }*/
}