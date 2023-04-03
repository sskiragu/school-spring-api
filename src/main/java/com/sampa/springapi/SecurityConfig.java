package com.sampa.springapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import com.sampa.springapi.repository.UserRepository;
import com.sampa.springapi.service.UserInfoUserDetailsService;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {
	
	@Autowired
	private UserRepository userRepository;
	
	@Bean
	public static PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

   @Bean
   SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	   
	   http.csrf().disable()
	   		.authorizeHttpRequests((authorize) -> authorize
	   				.requestMatchers("/signup", "/roles/new", "/user-roles/new").permitAll()
	   				.anyRequest().authenticated()
	   				).httpBasic(Customizer.withDefaults());
	   		
	   return http.build();
   }
   
   @Bean
   public UserDetailsService userDetailsService() {
//	   UserDetails sam = User.builder()
//			   .username("sam")
//			   .password(passwordEncoder().encode("sam"))
//			   .roles("USER")
//			   .build();
//	   
//	   UserDetails admin = User.builder()
//			   .username("admin")
//			   .password(passwordEncoder().encode("admin"))
//			   .roles("ADMIN")
//			   .build();
//	   
//	   return new InMemoryUserDetailsManager(sam, admin);
	   return new UserInfoUserDetailsService(userRepository);
   }
   
   @Bean
   public AuthenticationProvider authenticationProvider() {
	   DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
	   daoAuthenticationProvider.setUserDetailsService(userDetailsService());
	   daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
	   return daoAuthenticationProvider; 
   }

}

