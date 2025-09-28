package haodo.dev.vn.moneymanager;

import org.junit.jupiter.api.Test;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;

@SpringBootTest
class MoneymanagerApplicationTests {

	@Test
	void contextLoads() {
	}
	@Bean
	CommandLineRunner testEnv() {
		return args -> {
			System.out.println("BREVO_FROM_EMAIL = " + System.getenv("BREVO_FROM_EMAIL"));
		};
	}
}
