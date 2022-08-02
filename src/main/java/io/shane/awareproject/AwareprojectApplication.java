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
		URL url;
		try {
			url = new URL ("http://dataservice.accuweather.com/currentconditions/v1/207931?apikey=0GGcQmQJgi39KotcvRr6OsAjLZ5eaJ4z&details=true");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.connect();
			int responseCode = connection.getResponseCode();

			if(responseCode != 200) {
				throw new RuntimeException("HTTP Response code: " + responseCode);
			}
			else {
				StringBuilder detailString = new StringBuilder();
				Scanner scan = new Scanner(url.openStream());
				
				while(scan.hasNext()) {
					detailString.append(scan.nextLine());
					
				}
				scan.close();
				System.out.println(detailString);
				
				JSONParser parser = new JSONParser();
				JSONArray dataObject = (JSONArray) parser.parse(String.valueOf(detailString));
				
				System.out.println(dataObject.get(0));
				
				JSONObject dataCountry = (JSONObject) dataObject.get(0);
				JSONObject dataCountry2 = (JSONObject) dataCountry.get("Temperature");
				JSONObject dataCountry3 = (JSONObject) dataCountry2.get("Metric");
				JSONObject dataCountry4 = (JSONObject) dataCountry3.get("Value");
				
				System.out.println("Temperature in Dublin is: " + dataCountry4 + "C");
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		
		
	}
	

}
