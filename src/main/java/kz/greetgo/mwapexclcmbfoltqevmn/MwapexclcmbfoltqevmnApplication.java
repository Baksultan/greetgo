package kz.greetgo.mwapexclcmbfoltqevmn;

import kz.greetgo.mwapexclcmbfoltqevmn.noSql.service.MongoDBDataInitializer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MwapexclcmbfoltqevmnApplication {

	public static void main(String[] args) {
		SpringApplication.run(MwapexclcmbfoltqevmnApplication.class, args);
	}

	@Bean
	public CommandLineRunner initializeData(MongoDBDataInitializer initializer) {
		return args -> initializer.initializeData();
	}

}
