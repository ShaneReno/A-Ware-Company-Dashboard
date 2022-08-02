package io.shane.awareproject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.RestController;


@SpringBootApplication(scanBasePackages={"io.shane.awareproject",
		"io.shane.models", "io.shane.services", "io.shane.security"})
@EntityScan("io.shane.models")
@EnableJpaRepositories("io.shane.awareproject")
public class AwareprojectApplication {

	public static void main(String[] args) throws IOException, ParseException {
		SpringApplication.run(AwareprojectApplication.class, args);
		
		
		
		
	}
	

}
