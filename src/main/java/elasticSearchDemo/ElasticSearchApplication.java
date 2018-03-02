package elasticSearchDemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class ElasticSearchApplication {
	public static void main(String[] args) {
		SpringApplication.run(ElasticSearchApplication.class);
	}
	
	@RequestMapping(value = "/",produces = "text/plain;charset=UTF-8")
	public String in() {
		return "hello";
	}
}
