package com.leysoft.app.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.leysoft.app.utilitys.SecurityUtil;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService userDetailsService;
	
	private static final String[] PUBLIC_MATCHERS = {"/", "/css/**", "/js/**", "/img/**"};
	private static final String[] ANONYMOUS_MATCHERS = {"/login", "/add", "/registro", "/reset-password"};
	private static final String[] ADMIN_MATCHERS = {"/admin", "/admin/**"};
	private static final String[] USER_MATCHERS = {"/user/**", "/logout", "/edit"};
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers(PUBLIC_MATCHERS).permitAll()
			.antMatchers(ANONYMOUS_MATCHERS).anonymous()
			.antMatchers(ADMIN_MATCHERS).access("hasRole('ROLE_ADMIN')")
			.antMatchers(USER_MATCHERS).access("hasRole('ROLE_USER')")
			.anyRequest().authenticated();
		http.csrf();
		http.formLogin()
			.loginPage("/login")
			.defaultSuccessUrl("/", true)
			.usernameParameter("username")
			.passwordParameter("password")
			.failureUrl("/login?error");
		http.rememberMe()
			.rememberMeParameter("remember-me")
			.tokenValiditySeconds(60*60*60*24*5)
			.key("key_bookstore");
		http.logout()
			.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
			.logoutSuccessUrl("/?logout")
			.deleteCookies("remember-me");
		http.exceptionHandling()
			.accessDeniedPage("/denied");
	}
	
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(userDetailsService);
		provider.setPasswordEncoder(passwordEncoder());
		return provider;
	}
	
	public PasswordEncoder passwordEncoder() {
		return SecurityUtil.passwordEncoder();
	}
}