package io.shane.awareproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.web.bind.annotation.RestController;


@SpringBootApplication(scanBasePackages={"io.shane.awareproject",
		"io.shane.models", "io.shane.services", "io.shane.security"})
@EntityScan("io.shane.models")

public class AwareprojectApplication {

	public static void main(String[] args) {
		SpringApplication.run(AwareprojectApplication.class, args);
	}

}
