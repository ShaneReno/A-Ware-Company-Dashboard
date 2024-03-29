//The AWARE Company dashboard web application project
//by @author Shane Reynolds


package io.shane.awareproject;

import java.io.IOException;
import org.json.simple.parser.ParseException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;



//Allow the application to access all the relevant packages
@SpringBootApplication(scanBasePackages={"io.shane.awareproject",
		"io.shane.models", "io.shane.services", "io.shane.security"})
@EntityScan("io.shane.models")
@EnableJpaRepositories("io.shane.awareproject")
public class AwareprojectApplication {

	public static void main(String[] args) throws IOException, ParseException {
		SpringApplication.run(AwareprojectApplication.class, args); //Execute project
	}
	

}
