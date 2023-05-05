package com.main.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.main.service.AppUserService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private AppUserService custService;
	
	/*
	 * @Autowired private AuthenticationManager manager;
	 */
	
	@Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(custService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
    	http
        .authorizeRequests()
        .antMatchers("/admin/**","/products/add","/products/update/**","/products/delete/**").hasAnyRole("ADMIN","SELLER")
        .and().authorizeRequests()
        .antMatchers("/resorces/**","/css/**","/js/**","/images/**").permitAll()
        .and().authorizeRequests()
        .antMatchers("/login*","/rest/api/**","/signUp"
        		,"/validate","/products/**").permitAll()
        .and().authorizeRequests()
        .antMatchers(HttpMethod.POST,"/rest/api/cart/update").permitAll()
        .and().authorizeRequests().anyRequest().authenticated()
        .and().formLogin().loginPage("/login")
        .loginProcessingUrl("/login").defaultSuccessUrl("/products/all")
        .usernameParameter("email").and()
        .logout().logoutUrl("/logout").logoutSuccessUrl("/products/all")
        .and().csrf().disable();
    }
	

   
	/*
	 * 
	 @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            .antMatchers("/products/**")
            .permitAll()
            .and()
            .authorizeRequests()
            .antMatchers("/customers/**")
            .hasRole("ADMIN")
            .anyRequest()
            .authenticated()
            .and()
            .httpBasic();
        return http.build();
    }
	 * @Bean WebSecurityCustomizer webSecurityCustomizer() { return (web) ->
	 * web.ignoring().antMatchers("/css/**", "/js/**", "/images/**"); }
	 * 
	 * @Bean SecurityFilterChain securityFilterChain(HttpSecurity http) throws
	 * Exception { AuthenticationManagerBuilder authBuilder =
	 * http.getSharedObject(AuthenticationManagerBuilder.class);
	 * AuthenticationManager auth = configureGlobal(authBuilder);
	 * http.authenticationManager(auth);
	 * http.authorizeHttpRequests().antMatchers("/products/all").permitAll();
	 * http.antMatcher("/products/add").authorizeHttpRequests().anyRequest().
	 * hasAuthority("ADMIN").and().formLogin()
	 * .loginPage("/login").loginProcessingUrl("/login").usernameParameter("email");
	 * 
	 * return http.build(); }
	 * 
	 * 
	 * @Bean AuthenticationManager configureGlobal(AuthenticationManagerBuilder
	 * auth) throws Exception { return
	 * auth.userDetailsService(userService).passwordEncoder(new
	 * BCryptPasswordEncoder()).and().build(); }
	 */
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	/*
	 * @Bean public DaoAuthenticationProvider authenticationProvider() {
	 * DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
	 * authProvider.setUserDetailsService(userService);
	 * authProvider.setPasswordEncoder(passwordEncoder);
	 * 
	 * return authProvider; }
	 */
	 

}
