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
			
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
        	.authorizeHttpRequests()
        		.antMatchers("/cidades/nova").hasRole("CADASTRAR_CIDADE")
        		.antMatchers("/usuarios/**").hasRole("CADASTRAR_USUARIO")
        		.anyRequest().authenticated()
        		.and()
        	.formLogin()
        		.loginPage("/login").permitAll()
        		.and()
        	.exceptionHandling()
        		.accessDeniedPage("/403")
        		
        	.and()        		
        	.csrf().disable();
		return http.build();
	}

	@Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().antMatchers("/layout/**", "/css/**", "/images/**", "/js/**");
    }	
	
}
