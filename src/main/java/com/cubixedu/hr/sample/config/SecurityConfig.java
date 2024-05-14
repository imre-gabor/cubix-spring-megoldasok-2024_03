package com.cubixedu.hr.sample.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.cubixedu.hr.sample.security.JwtAuthFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
	
	@Autowired
	private JwtAuthFilter jwtAuthFilter;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
//	@Bean
//	public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
//		UserBuilder users = User.builder();
//		UserDetails user = users
//			.username("user")
//			.password(passwordEncoder.encode("pass"))
//			.authorities("user")
//			.build();
//		UserDetails admin = users
//			.username("admin")
//			.password(passwordEncoder.encode("pass"))
//			.authorities("user", "admin")
//			.build();
//		
//		return new InMemoryUserDetailsManager(user, admin);
//	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
		return http
//				.httpBasic(
//					Customizer.withDefaults()
//				)
				.csrf(csrf ->
					csrf.disable()
				)
				.authorizeHttpRequests(auth ->
					auth
					.requestMatchers(HttpMethod.POST, "/api/login").permitAll()
					.requestMatchers("/api/holidayrequests/**").authenticated()
					.requestMatchers("/error").permitAll()
					.anyRequest().authenticated()
				)
				.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
				.build();
		
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}
}
