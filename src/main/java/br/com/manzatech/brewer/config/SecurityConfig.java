package br.com.manzatech.brewer.config;

import javax.servlet.Filter;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.filter.CharacterEncodingFilter;

import br.com.manzatech.brewer.security.AppUserDetailsService;

@EnableWebSecurity
public class SecurityConfig {
	
	@Bean
	public UserDetailsService userDetailsService() {
		return new AppUserDetailsService();
	}
	
	@Bean
	public HttpSessionEventPublisher httpSessionEventPublisher() {
	    return new HttpSessionEventPublisher();
	}
	
	@Bean
	public HttpFirewall getHttpFirewall() {
	    StrictHttpFirewall strictHttpFirewall = new StrictHttpFirewall();
	    strictHttpFirewall.setAllowSemicolon(true);
	    return strictHttpFirewall;
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
			
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.
			addFilterBefore(getEncodingFilter(), CsrfFilter.class)			
			.authorizeHttpRequests().antMatchers("/login**")
				.permitAll()
				.and()
        	.authorizeHttpRequests()
        		.antMatchers("/cidades/nova").hasRole("CADASTRAR_CIDADE")
        		.antMatchers("/usuarios/**").hasRole("CADASTRAR_USUARIO")
        		.anyRequest().authenticated()
        		.and()        	
        	.formLogin()
        		.loginPage("/login").permitAll()
        		.and()
    		.logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
	    			.logoutSuccessUrl("/login?logout")
	    			.invalidateHttpSession(false)
	    			.deleteCookies("JSESSIONID")
    			.and()
        	.exceptionHandling()
        		.accessDeniedPage("/403")
        		.and()
			.sessionManagement(session -> 
				session
					.maximumSessions(1).expiredUrl("/login?expired")
					.and()
					
					.invalidSessionUrl("/login?timeout")
			);
		return http.build();
	}


	private Filter getEncodingFilter() {
		CharacterEncodingFilter encodingFilter = new CharacterEncodingFilter();
		encodingFilter.setEncoding("UTF-8");
		encodingFilter.setForceEncoding(true);
		return encodingFilter;
	}

	@Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().antMatchers("/layout/**", "/css/**", "/images/**", "/js/**");
    }	
	
}
