package com.suraj.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	//for User details service this is interface create your own class and implements this
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private JWTFilter jwtFilter;

	@Bean
	public SecurityFilterChain sercurityFilterChain(HttpSecurity http) throws Exception{
//		http.csrf(customzer -> customzer.disable());
		
		//this will be enable for the authentication 
//		http.authorizeHttpRequests(request -> request.anyRequest().authenticated());

		//it will be show one login form and it will give one html page direct but when you say on path logout it will direct logout 
//		http.formLogin(Customizer.withDefaults());
		
		//when you don't want to give direct html login page then you should do like this it will give only what ever you show
//		http.httpBasic(Customizer.withDefaults());
		
		//it will be give every request new session 
//		http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
//		return http.build();
		
		//if you want this code is more readble hten you do one think
		
		return http
				.csrf(customizer -> customizer.disable())
				.authorizeHttpRequests(request -> request
						.requestMatchers("register", "login")
						.permitAll()
						.anyRequest().authenticated())
				.httpBasic(Customizer.withDefaults())
				.sessionManagement(session -> 
						session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
				.build();
		
	}
	
//	@Bean
//	public UserDetailsService userDetailsService() {
//		
//		UserDetails user1 = User
//				.withDefaultPasswordEncoder()
//				.username("suraj")
//				.password("suraj123")
//				.roles("admin")
//				.build();
//		
//		UserDetails user2 = User
//				.withDefaultPasswordEncoder()
//				.username("banti")
//				.password("banti123")
//				.roles("admin")
//				.build();
//		
//		return new InMemoryUserDetailsManager(user1, user2);
//	}
	
	//when you want your own id password witch is get from the database the you should use this steps
	
	@Bean
	public AuthenticationProvider authenticationProvider() {
		
		//without database its nothing
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		
		//default encoder
		provider.setPasswordEncoder(new BCryptPasswordEncoder(12));
		
		//here you can use own user details service
		provider.setUserDetailsService(userDetailsService);
		return provider;
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}
}
