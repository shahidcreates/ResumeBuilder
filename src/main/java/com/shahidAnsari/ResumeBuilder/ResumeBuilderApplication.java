package com.shahidAnsari.ResumeBuilder;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(
        info = @Info(
                title = "Resume Builder REST API documentation",
                description = "Resume Builder REST API",
                version = "v1",
                contact = @Contact(
                        name = "Shahid Ansari",
                        email ="shahid.ans3001@gamil.com"
                )

        )
)
@SpringBootApplication
public class ResumeBuilderApplication {

	public static void main(String[] args) {
		SpringApplication.run(ResumeBuilderApplication.class, args);
	}

}
