package io.shane.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

//This class maps all the HTML pages to the users that can access them.
//It uses some deprecated methods, however still works.
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	@Autowired
	DataSource dataSource;
	
	//Determine what datasource, user and role to authenticate:
	@Override
	protected void configure(AuthenticationManagerBuilder authent) throws Exception{
		authent.jdbcAuthentication().dataSource(dataSource).usersByUsernameQuery("select username,password,enabled " + "from users " + "where username = ?") 
		.authoritiesByUsernameQuery("select username,authority " + "from authorities " + "where username = ?");
	}
	
	//Authorisation
		@Override
		//Using a HTTP request, map the different users to their relevant pages:
		protected void configure(HttpSecurity http) throws Exception{ //Error handling
			http.authorizeRequests()
			//.antMatchers( "/favicon.ico").permitAll() //favicon image
			//Admin users authorisation for pages
			.antMatchers("/admin-dashboard").hasRole("ADMIN") //Example: if the role is equal to 'ADMIN', grant them access to the admin dashboard. And so on..
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
			.antMatchers("/admin-dashboard/admin-add-new-employee-hire-confirm-role").hasRole("ADMIN")
			
			///Employee users authorisation for pages
			.antMatchers("/employee-dashboard").hasRole("USER")
			.antMatchers("/employee-dashboard/employee-roster").hasRole("USER")
			.antMatchers("/employee-dashboard/employee-holidays").hasRole("USER")
			.antMatchers("/employee-dashboard/employee-request-shift-swap").hasRole("USER")
			.antMatchers("/employee-dashboard/employee-view-weather").hasRole("USER")
			.antMatchers("/employee-dashboard/employee-view-payslip").hasRole("USER")
			.antMatchers("/employee-dashboard/employee-shift-swap-answer").hasRole("USER")
			.antMatchers("/employee-dashboard/employee-request-shift-swap").hasRole("USER")
			.antMatchers("/employee-dashboard/save-employee-record").hasRole("USER")
			.antMatchers("/employee-dashboard/employee-request-shift-swap-response").hasRole("USER")
			.antMatchers("/employee-dashboard/employee-shift-swap-response").hasRole("USER")
			.antMatchers("/").permitAll()
			.and().formLogin().defaultSuccessUrl("/default");
		}
		
		//Encode the password so that it cannot be readable from an external process.
		//Uses a working deprecated method and library import.
		@Bean
		public PasswordEncoder getPasswordEncoder() {
			return NoOpPasswordEncoder.getInstance();
		}
	
	

}
