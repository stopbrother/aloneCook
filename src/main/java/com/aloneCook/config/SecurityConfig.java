package com.aloneCook.config;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import lombok.RequiredArgsConstructor;

@Configuration // 1개이상의 bean을 명시
@EnableWebSecurity // 직접 시큐리티 설정
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	private final UserDetailsService userDetailService;
	private final DataSource dataSource;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.mvcMatchers("/", "/login", "/join", "/check-email", "/check-email-token", "/email-login",
						"/check-email-login", "/login-link", "/search/recipe", "/recipe-list").permitAll() //mvcMatchers로 해당 링크는 모두 허용
				.mvcMatchers(HttpMethod.GET, "/profile/*").permitAll() //profile링크는 GET만 허용
				.anyRequest().authenticated(); //그외는 인증후

		http.formLogin() // 스프링 시큐리티가 가지고 있는 loginform이 있음, loginPage를 설정해 커스텀하게 표현할 수 있다.
				.loginPage("/login").permitAll(); // 로그인 안해도 접근, 해도 접근할 수 있도록 permitAll !

		http.logout().logoutSuccessUrl("/");
		
		http.rememberMe()
			.userDetailsService(userDetailService)
			.tokenRepository(tokenRepository());
	}
	
	@Bean
	public PersistentTokenRepository tokenRepository() {
		JdbcTokenRepositoryImpl jdbTokenRepository = new JdbcTokenRepositoryImpl();
		jdbTokenRepository.setDataSource(dataSource);
		return jdbTokenRepository;
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().mvcMatchers("/node_modules/**")
				.antMatchers("/favicon.ico", "/resources/**", "/uploads/**", "/error")
				.requestMatchers(PathRequest.toStaticResources().atCommonLocations());
		// ↑ static resources는 스프링 시큐리티 필터 적용 X
	}

}
