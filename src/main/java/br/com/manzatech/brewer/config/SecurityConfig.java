package br.com.manzatech.brewer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
public class SecurityConfig {
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
//	@Bean
//    public InMemoryUserDetailsManager userDetailsService() {
//		
//        UserDetails user = User.builder()
//            .username("admin")
//            .password(passwordEncoder().encode("admin"))
//            .roles("USER")
//            .build();
//        return new InMemoryUserDetailsManager(user);
//    }
		
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
        	.authorizeHttpRequests()
        		.anyRequest().authenticated()
        		.and()
        	.formLogin()
        		.loginPage("/login").permitAll()
        		.and()
        	.csrf().disable();
		return http.build();
	}

	@Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().antMatchers("/layout/**", "/css/**", "/images/**", "/js/**");
    }	
	
}
