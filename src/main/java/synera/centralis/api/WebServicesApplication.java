package synera.centralis.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class WebServicesApplication {

	public static void main(String[] args) {

        SpringApplication.run(WebServicesApplication.class, args);
        System.out.println("\n----------------------------------------------------------");
        System.out.println("Swagger UI available at: http://localhost:8080/swagger-ui/index.html");
        System.out.println("----------------------------------------------------------\n");
	}

}
