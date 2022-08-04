package io.shane.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	@Autowired
	DataSource dataSource;
	
	@Override
	protected void configure(AuthenticationManagerBuilder authent) throws Exception{
		authent.jdbcAuthentication().dataSource(dataSource).usersByUsernameQuery("select username,password,enabled " + "from users " + "where username = ?") 
		.authoritiesByUsernameQuery("select username,authority " + "from authorities " + "where username = ?");
	}
	
	//Authorisation
		@Override
		protected void configure(HttpSecurity http) throws Exception{
			http.authorizeRequests()
			//Admin users authorisation for pages
			.antMatchers("/admin-dashboard").hasRole("ADMIN")
			.antMatchers("/admin-dashboard/admin-view-all-employees").hasRole("ADMIN")
			.antMatchers("/admin-dashboard/admin-create-roster").hasRole("ADMIN")
			.antMatchers("/admin-dashboard/admin-view-create-roster").hasRole("ADMIN")
			.antMatchers("/admin-dashboard/saveCreateEditRoster").hasRole("ADMIN")
			.antMatchers("/admin-dashboard/admin-view-employee-records").hasRole("ADMIN")
			.antMatchers("/admin-dashboard/admin-add-new-employee-hire").hasRole("ADMIN")
			.antMatchers("/admin-dashboard/admin-fire-employee").hasRole("ADMIN")
			.antMatchers("/admin-dashboard/updateEmployee").hasRole("ADMIN")
			.antMatchers("/admin-dashboard/admin-fire-employee").hasRole("ADMIN")
			.antMatchers("/admin-dashboard/deleteEmployee").hasRole("ADMIN")
			.antMatchers("/admin-dashboard/admin-add-employee-to-roster").hasRole("ADMIN")
			
			///Employee users authorisation for pages
			.antMatchers("/employee-dashboard").hasRole("USER")
			.antMatchers("/employee-dashboard/employee-roster").hasRole("USER")
			.antMatchers("/employee-dashboard/employee-request-holidays").hasRole("USER")
			.antMatchers("/employee-dashboard/employee-request-shift-swap").hasRole("USER")
			.antMatchers("/employee-dashboard/employee-view-weather").hasRole("USER")
			.antMatchers("/employee-dashboard/employee-view-payslip").hasRole("USER")
			.antMatchers("/").permitAll()
			.and().formLogin().defaultSuccessUrl("/default");
		}
		
		@Bean
		public PasswordEncoder getPasswordEncoder() {
			return NoOpPasswordEncoder.getInstance();
		}
	
	

}
