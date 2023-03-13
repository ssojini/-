package com.example.demo.security;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.example.demo.service.UserService;

@Configuration
@EnableWebSecurity
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

	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		return (web) -> web.ignoring().requestMatchers("/resources/**", "/ignore","/error");
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
		return httpSecurity
				// 권한에 따른 인가
				.authorizeHttpRequests()
				.requestMatchers(
						// Guest
						
						// 
						"/js/**",
						"/css/**",
						"/img/**",
						
						// AdminBoardController.java
						// post
						"/admin/search_notice",
						"/admin/search_faq",
						"/admin/search_qna",
						// CalendarController.java
						// get
						"/calen/getCalendar",
						// ConnectController.java
						// post
						"/connect/chatGPT",
						"/connect/prod_recommend",
						// FileController.java
						// FreeboardController.java
						// HealthController.java
						// get
						"/health/main", // g
						"/health/bmi", // g
						"/health/center_search", // u
						// post
						"/health/cal", // g
						"/health/getloc", // g
						// ImageController.java
						// get
						"/images/**", // g
						"/goodsimg/**", // g
						// JoinController.java
						// get
						"/team/rules", // g
						"/team/joinForm", // g
						"/team/", // g
						"/team/login", // g
						"/team/findLoginInfo", // g
						"/team/logon", // g
						// post
						"/team/add", // g
						"/team/idcheck", // g
						"/team/nickcheck", // g
						"/team/join", // g
						"/team/sendemail", // g
						"/team/check", // g
						"/team/reset", // g
						"/team/find", // g
						"/team/auth/**", // g
						"/team/authEmail", // g
						"/team/loginsuccess", // g
						"/team/login-error", // g
						// ManagerController.java
						// SecurityController.java
						// get
						"/sec/denied", // g
						// ShopController.java
						// get
						"/shop/detail/**", // g
						"/shop/detail/review", // g
						"/shop/rec_test", // g
						"/shop/imgtest", // g
						"/shop/ShopMainPage", // g
						"/shop/summer/**", // 
						"/shop/searchgoods", // g
						"/shop/category1", // g
						"/shop/category2", // g
						"/shop/category3", // g
						"/shop/main", // g
						// SummernoteImageController.java
						// get
						"/summernoteImage/**"
				).permitAll()
				.requestMatchers(
						// User
						
						// AdminBoardController.java
						// CalendarController.java
						// get
						"/calen/showCalen",
						"/calen/add",
						"/calen/detail/**",
						"/calen/edit/**",
						// post
						"/calen/editCal/**",
						"/calen/delimg",
						"/calen/delete",
						// ConnectController.java
						// FileController.java
						// get
						"/file/download",
						// post
						"/file/upload",
						"/file/delete",
						"/file/list",
						// FreeboardController.java
						// get
						"/freeboard",
						"/freeboard/",
						"/freeboard/add",
						"/freeboard/detail",
						"/freeboard/edit",
						// post
						"/freeboard/add",
						"/freeboard/updateContents",
						"/freeboard/delete",
						"/freeboard/edit",
						"/freeboard/addReply",
						"/freeboard/deleteReply",
						"/freeboard/getReply",
						"/freeboard/listFreeboard",
						"/freeboard/likeCount",
						// HealthController.java
						// get
						"/health/myboard/**", // u
						"/health/useredit/**", // u
						"/health/deleteuser/**", // u 
						"/health/deleteuser_check/**", // u
						"/health/user_addinfo/**", // u
						"/health/findpwd/**", // u
						"/health/qna", // u
						"/health/writeQueB", // u
						"/health/detailByQnum/**", // u
						"/health/edit_q/**", // u
						"/health/faq/**", // u
						"/health/detail_faq/**", // u
						"/health/edit_faq/**", // u
						"/health/notice/**", // u
						"/health/detail_notice/**", // u
						"/health/edit_notice/**", // u
						// post
						"/health/userEdit", // u
						"/health/deleteuser", // u
						"/health/changepwd/**", // u
						"/health/writeQueB", // u
						// post
						"/health/edit_q/**", // u
						"/health/delIndiv", // u
						"/health/deleteQueB", // u
						"/health/edit_faq/**", // u
						"/health/edit_notice/**", // u
						// ImageController.java
						// JoinController.java
						// ManagerController.java
						// SecurityController.java
						// ShopController.java
						// get
						"/shop/cart", // u
						"/shop/payment", // u
						"/shop/mypage/**", // u
						"/shop/mypage/itemdetail/**", // u
						// post
						"/shop/cart", // u
						"/shop/cnt_change", // u
						"/shop/delAll", // u
						"/shop/delSel", // u
						"/shop/buynow", // u
						"/shop/buycart" // u
						).hasAnyRole("USER","ADMIN")
				.requestMatchers(
						// Admin
						
						// AdminBoardController.java
						// get
						"/admin/qaList/**",
						"/admin/add",
						"/admin/detailByQnum/**",
						"/admin/edit_q/**",
						"/admin/notice/**",
						"/admin/noticeMain/**",
						"/admin/detail_notice/**",
						"/admin/edit_notice/**",
						"/admin/faq/**",
						"/admin/detail_faq_admin/**",
						"/admin/edit_faq/**",
						"/admin/**",
						// post
						"/admin/add",
						"/admin/edit_q/**",
						"/admin/edit_notice/**",
						"/admin/delAdminIndiv",
						"/admin/delAdminB",
						"/admin/edit_faq/**",
						"/admin/**",
						// CalendarController.java
						// ConnectController.java
						// FileController.java
						// FreeboardController.java
						// HealthController.java
						// get
						"/health/reply/**", // a
						// post
						"/health/reply", // a
						// ImageController.java
						// JoinController.java
						// ManagerController.java
						// get
						"/manager/",
						"/manager/main",
						"/manager/userlist",
						"/manager/userdetail/**",
						"/manager/shop",
						"/manager/shoplist",
						"/manager/shop/detail",
						"/manager/board",
						"/manager/board/detail",
						"/manager/shopitem",
						"/manager/item/edit/**",
						"/manager/editgoods/**",
						// post
						"/manager/calchart",
						"/manager/userchart",
						"/manager/DelAccount",
						"/manager/shop/edit",
						"/manager/board/delete",
						"/manager/deletegoods/**",
						// SecurityController.java
						// ShopController.java
						// get
						"/shop/addgoods/**", // a
						// post
						"/shop/addgoods", // a
						"/shop/editgoods", // a
						"/shop/summer_image.do" //a
						).hasAnyRole("ADMIN")
				.anyRequest().authenticated() // 그 외 모든 요청은 인증된 사용자만 접근 가능

				// csrf
				.and()
				.csrf()
				.disable()
				/*
				.ignoringRequestMatchers(
						// csrf
						
						//
						"/doLogin",
						"/doLogout",
						
						// AdminBoardController.java
						// post
						"/admin/add",
						"/admin/edit_q/**",
						"/admin/edit_notice/**",
						"/admin/delAdminIndiv",
						"/admin/delAdminB",
						"/admin/edit_faq/**",
						"/admin/search_notice",
						"/admin/search_faq",
						"/admin/search_qna",
						// CalendarController.java
						// post
						"/calen/editCal/**",
						"/calen/delimg",
						"/calen/delete",
						// ConnectController.java
						// post
						"/connect/chatGPT",
						"/connect/prod_recommend",
						// FileController.java
						// post
						"/file/upload",
						"/file/delete",
						"/file/list",
						// FreeboardController.java
						// post
						"/freeboard/add",
						"/freeboard/updateContents",
						"/freeboard/delete",
						"/freeboard/edit",
						"/freeboard/addReply",
						"/freeboard/deleteReply",
						"/freeboard/getReply",
						"/freeboard/listFreeboard",
						"/freeboard/likeCount",
						// HealthController.java
						// post
						"/health/cal", // g
						"/health/userEdit", // u
						"/health/deleteuser", // u
						"/health/changepwd/**", // u
						"/health/getloc", // g
						"/health/writeQueB", // u
						"/health/reply", // a
						"/health/edit_q/**", // u
						"/health/delIndiv", // u
						"/health/deleteQueB", // u
						"/health/edit_faq/**", // u
						"/health/edit_notice/**", // u
						// ImageController.java
						// post
						"/images/**", // g
						"/goodsimg/**", // g
						// JoinController.java
						// post
						"/team/add", // g
						"/team/idcheck", // g
						"/team/nickcheck", // g
						"/team/join", // g
						"/team/sendemail", // g
						"/team/check", // g
						"/team/reset", // g
						"/team/find", // g
						"/team/auth/**", // g
						"/team/authEmail", // g
						"/team/loginsuccess", // g
						"/team/login-error", // g
						// ManagerController.java
						// post
						"/manager/calchart",
						"/manager/userchart",
						"/manager/DelAccount",
						"/manager/shop/edit",
						"/manager/board/delete",
						"/manager/deletegoods/**",
						// SecurityController.java
						// ShopController.java
						// post
						"/shop/cart", // u
						"/shop/cnt_change", // u
						"/shop/delAll", // u
						"/shop/delSel", // u
						"/shop/buynow", // u
						"/shop/buycart", // u
						"/shop/addgoods", // a
						"/shop/editgoods", // a
						"/shop/summer_image.do",
						
						"/**"
				)
				.and()
				 */

				// 로그인
				.formLogin().loginPage("/team/login") // 접속 차단시 로그인 페이지로 가게 하기
				.loginProcessingUrl("/doLogin") // post 로그인
				.defaultSuccessUrl("/team/loginsuccess") // 로그인 성공시 URL
				.failureUrl("/team/login-error") // 로그인 실패시 URL
				.usernameParameter("userid")  // 로그인 폼에서 이용자 ID 필드 이름, 디폴트는 username
				.passwordParameter("pwd")  // 로그인 폼에서 이용자 암호 필트 이름, 디폴트는 password
				.permitAll()

				// 로그아웃
				.and()   // 디폴트 로그아웃 URL = /logout
				.logout().logoutRequestMatcher(new AntPathRequestMatcher("/doLogout")) //로그아웃 요청시 URL
				.logoutSuccessUrl("/team/login") // 로그아웃 성공시 URL
				.invalidateHttpSession(true)
				.deleteCookies("JSESSIONID") // 세션 삭제
				.permitAll()

				// 에러 페이지 구현하기
				.and()
				.exceptionHandling().accessDeniedPage("/sec/denied")

				.and()
				.build();
	}
}